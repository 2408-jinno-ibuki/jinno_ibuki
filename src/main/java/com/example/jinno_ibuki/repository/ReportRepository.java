package com.example.jinno_ibuki.repository;

import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<ReportEntity, Integer> {
    List<ReportEntity> findByLimitDateBetweenAndContentAndStatusOrderByLimitDateAsc
            (Date start, Date end, String content, Integer status, Limit limit);
    @Query
            (value = "SELECT * FROM tasks", nativeQuery = true)
    List<ReportEntity> task();
}
