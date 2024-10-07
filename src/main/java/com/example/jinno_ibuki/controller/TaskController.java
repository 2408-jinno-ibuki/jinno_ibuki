package com.example.jinno_ibuki.controller;

import com.example.jinno_ibuki.controller.Form.ReportForm;
import com.example.jinno_ibuki.repository.ReportEntity;
import com.example.jinno_ibuki.service.ReportService;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
                            @RequestParam(name="status", required = false) Integer status) throws ParseException {

        ModelAndView mav = new ModelAndView();
        //タスク全件取得処理
        List<ReportForm> tasksData = reportService.findAllTasks(start, end, content, status);
        mav.setViewName("/top");
        //取得してきたタスク情報保管
        mav.addObject("tasks", tasksData);
        return mav;
    }
    //タスク追加画面表示
    @GetMapping("/new")
    public ModelAndView newTask() {
        ModelAndView mav = new ModelAndView();
        ReportForm reportForm = new ReportForm();
        // 画面遷移先を指定
        mav.setViewName("/addition");
        // 準備した空のFormを保管
        // オブジェクトreportFormを名前をformModelにして保管
        mav.addObject("tasks", reportForm);
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
    @PutMapping("/select/{id}")
    public ModelAndView updateStatus(@RequestParam(name="status") int status, @PathVariable Integer id, @ModelAttribute ReportForm form){
        ModelAndView mav = new ModelAndView();
        form.setId(id);
        form.setStatus(status);
        reportService.saveReport(form);

        return new ModelAndView("redirect:/");
    }

    //タスク登録処理
    @PostMapping("/add")
    public ModelAndView addContent(@ModelAttribute("tasks") @Validated ReportForm reportForm, BindingResult error) throws ParseException {

        ModelAndView mav = new ModelAndView();
        if(error.hasErrors()){
            mav.setViewName("/addition");
            return mav;
        }
        //viewから取得してきたString型のタスク期限をDate型に変換
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String limitDate = reportForm.getStrLimitDate();
        Date limit = dateFormat.parse(limitDate);
        reportForm.setLimitDate(limit);
        reportForm.setStatus(1);
        reportService.saveReport(reportForm);
        return new ModelAndView("redirect:/");
    }

    //タスク編集画面表示処理
    @GetMapping({ "/edit", "/edit/{id}" })
    public ModelAndView editContent(@PathVariable(name= "id", required = false) String id) {
        ModelAndView mav = new ModelAndView();
        //idがnull or 数字か確認
        List<String> errorMessages = new ArrayList<String>();
        if (StringUtils.isBlank(id) || !id.matches("^[0-9]*$")) {
            errorMessages.add("不正なパラメーターが入力されました");
            mav.addObject("errorMessages", errorMessages);
            mav.setViewName("/top");
            return mav;
        }
        Integer taskId = Integer.parseInt(id);
        ReportForm reportForm = reportService.editReport(taskId);

        mav.addObject("tasks", reportForm);
        // 画面遷移先を指定
        mav.setViewName("/edit");
        return mav;
    }

    //タスク編集処理
    @PutMapping("/update/{id}")
    public ModelAndView updateContent (@PathVariable Integer id,
                                       @ModelAttribute("tasks") @Validated ReportForm report, BindingResult error) {
        ModelAndView mav = new ModelAndView();


        //Validationチェック
        if(error.hasErrors()){
            mav.setViewName("/edit");
            return mav;
        }
        // UrlParameterのidを更新するentityにセット
        report.setId(id);
        // 編集した投稿を更新
        reportService.saveReport(report);
        // rootへリダイレクト
        return new ModelAndView("redirect:/");
    }
}