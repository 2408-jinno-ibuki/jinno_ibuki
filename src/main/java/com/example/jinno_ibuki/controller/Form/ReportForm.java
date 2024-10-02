package com.example.jinno_ibuki.controller.Form;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ReportForm {

    private int id;
    private String content;
    private double status;
    private Date limitDate;
    private Date createdDate;
    private Date updatedDate;

}
