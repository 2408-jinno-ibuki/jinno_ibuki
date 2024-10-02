package com.example.jinno_ibuki.service;

import com.example.jinno_ibuki.controller.Form.ReportForm;
import com.example.jinno_ibuki.repository.ReportEntity;
import com.example.jinno_ibuki.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.data.domain.Limit;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReportService {
    @Autowired
    ReportRepository reportRepository;

    //タスク情報取得処理
    public List<ReportForm> findAllTasks() {
        List<ReportEntity> results = reportRepository.findAllByOrderByLimitDateAsc(Limit.of(1000));
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
}

