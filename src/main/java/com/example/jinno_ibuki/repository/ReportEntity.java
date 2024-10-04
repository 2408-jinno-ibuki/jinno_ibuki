package com.example.jinno_ibuki.repository;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name= "tasks")
@Getter
@Setter
public class ReportEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;

    @Column
    private String content;

    @Column
    private Integer status;

    @Column(name="limit_date", insertable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date limitDate;

    @Column(name="created_date", insertable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name="updated_date", insertable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;

    @Transient
    private String strLimitDate;
}
