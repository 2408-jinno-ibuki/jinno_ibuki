package com.example.jinno_ibuki.controller.Form;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
public class ReportForm {

    private int id;
    @NotBlank(message = "タスクを入力してください")
    @Size(max=140,message="タスクは140字以内で入力してください")
    private String content;
    private Integer status;
    //@Past(message="無効な日付です")
    //@DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date limitDate;
    private Date createdDate;
    private Date updatedDate;

}
