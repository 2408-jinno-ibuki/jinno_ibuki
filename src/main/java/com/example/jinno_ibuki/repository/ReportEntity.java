package com.example.jinno_ibuki.repository;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name= "tasks")
@Getter
@Setter
@NotNull(message="不正なパラメータです")
public class ReportEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;

    @Column
    private String content;

    @Column
    private Integer status;

    @Column(name="limit_date", insertable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date limitDate;

    @Column(name="created_date", insertable = false, updatable = false)
    @Temporal(TemporalType.DATE)
    private Date createdDate;

    @Column(name="updated_date", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;

    @Transient
    private String strLimitDate;
}
