package com.example.jinno_ibuki.controller;

import com.example.jinno_ibuki.controller.Form.ReportForm;
import com.example.jinno_ibuki.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class TaskController {
    @Autowired
    ReportService reportService;

    //タスク表示処理
    @GetMapping
    public ModelAndView top() {

        ModelAndView mav = new ModelAndView();
        //タスク全件取得処理
        List<ReportForm> tasksData = reportService.findAllTasks();
        mav.setViewName("/top");
        //取得してきたタスク情報保管
        mav.addObject("tasks", tasksData);
        return mav;

    }

}
