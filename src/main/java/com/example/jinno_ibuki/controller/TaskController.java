package com.example.jinno_ibuki.controller;

import com.example.jinno_ibuki.controller.Form.ReportForm;
import com.example.jinno_ibuki.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.util.List;

@Controller
public class TaskController {
    @Autowired
    ReportService reportService;

    //タスク表示処理
    @GetMapping
    public ModelAndView top(@RequestParam(name = "start", required=false) String start,
                            @RequestParam(name="end", required = false) String end,
                            @RequestParam(name="taskContent", required = false) String content,
                            @RequestParam(name="status", required = false) Double status) throws ParseException {

        ModelAndView mav = new ModelAndView();
        //タスク全件取得処理
        List<ReportForm> tasksData = reportService.findAllTasks(start, end, content, status);
        mav.setViewName("/top");
        //取得してきたタスク情報保管
        mav.addObject("tasks", tasksData);
        return mav;
    }

    @GetMapping("/new")
    public ModelAndView newTask() {
        ModelAndView mav = new ModelAndView();
        ReportForm reportForm = new ReportForm();
        // 画面遷移先を指定
        mav.setViewName("/new");
        // 準備した空のFormを保管
        // オブジェクトreportFormを名前をformModelにして保管
        mav.addObject("formModel", reportForm);
        return mav;
    }

    //タスク削除処理
    @DeleteMapping("/delete/{id}")
    public ModelAndView deleteContent(@PathVariable Integer id) {
        // 投稿をテーブルに格納
        reportService.deleteReport(id);
        // Top画面へリダイレクト
        return new ModelAndView("redirect:/");
    }
    //ステータス変更処理
    @PutMapping("/update/{id}")
    public ModelAndView updateStatus(@RequestParam(name="status") Double status, @PathVariable Integer id, @ModelAttribute ReportForm form){
        ModelAndView mav = new ModelAndView();
        form.setId(id);
        form.setStatus(status);
        reportService.saveReport(form);

        return new ModelAndView("redirect:/");
    }
}
