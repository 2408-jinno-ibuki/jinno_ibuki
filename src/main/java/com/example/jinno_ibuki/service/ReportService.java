package com.example.jinno_ibuki.service;

import com.example.jinno_ibuki.controller.Form.ReportForm;
import com.example.jinno_ibuki.repository.ReportEntity;
import com.example.jinno_ibuki.repository.ReportRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Limit;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ReportService {
    @Autowired
    ReportRepository reportRepository;

    //タスク情報取得処理
    public List<ReportForm> findAllTasks
    (String startDate, String endDate, String content, Integer status) throws ParseException {

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String defaultStart = "2020-01-01 00:00:00";
        final String defaultEnd = "2100-12-31 23:59:59";

        if (!StringUtils.isEmpty(startDate)) {
            startDate = startDate + " 00:00:00";
        } else {
            startDate = defaultStart;
        }
        if (!StringUtils.isEmpty(endDate)) {
            endDate = endDate + " 23:59:59";
        } else {
            endDate = defaultEnd;
        }

        Date start = dateFormat.parse(startDate);
        Date end = dateFormat.parse(endDate);

        List<ReportEntity> results =
                reportRepository.task();
                        //(start, end, content, status, Limit.of(1000));
        List<ReportForm> reports = setReportForm(results);
        return reports;
    }

    //DBから取得した情報をformにセット
    private List<ReportForm> setReportForm(List<ReportEntity> results) {
        List<ReportForm> reports = new ArrayList<>();

        //取得してきたレコードの数だけ回す
        for (int i = 0; i < results.size(); i++) {
            ReportForm report = new ReportForm();
            ReportEntity result = results.get(i);
            report.setId(result.getId());
            report.setContent(result.getContent());
            report.setStatus(result.getStatus());
            report.setLimitDate(result.getLimitDate());
            reports.add(report);
        }
        return reports;
    }

    //タスク削除処理
    public void deleteReport(Integer id) {
        reportRepository.deleteById(id);
    }

    //タスク登録処理
    public void saveReport(ReportForm form){
        ReportEntity saveReport = setEntity(form);
        reportRepository.save(saveReport);
    }

    //formからEntityに詰め替え
    public ReportEntity setEntity(ReportForm form){
        ReportEntity entity = new ReportEntity();
        entity.setId(form.getId());
        entity.setContent(form.getContent());
        entity.setStatus(form.getStatus());
        entity.setLimitDate(form.getLimitDate());
        //entity.setCreatedDate(form.getCreatedDate());
        //entity.setUpdatedDate(form.getUpdatedDate());
        return entity;
    }

    //タスク編集機能
    public ReportForm editReport(Integer id) {
        List<ReportEntity> results = new ArrayList<>();
        results.add((ReportEntity) reportRepository.findById(id).orElse(null));
        List<ReportForm> reports = setReportForm(results);
        return reports.get(0);
    }
}