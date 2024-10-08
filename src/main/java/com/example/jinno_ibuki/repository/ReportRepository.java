package com.example.jinno_ibuki.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface ReportRepository extends JpaRepository<ReportEntity, Integer>{
    List<ReportEntity> findByLimitDateBetweenAndContentAndStatusOrderByLimitDateAsc
            (Date start, Date end, String content, Integer status, Limit limit);
    @Query(value = "SELECT * FROM tasks" + " WHERE limit_date BETWEEN :Start AND :End AND " +
            "content = :Content AND status = :Status ORDER BY limit_date ASC limit Limit", nativeQuery = true)
    List<ReportEntity> task(@Param("Start") Date start, @Param("End") Date end, @Param("Content") String content,
                            @Param("Status") Integer status, Limit limit);

    @Transactional
    @Modifying
    @Query(value= "UPDATE tasks SET status = :Status, updated_date = CURRENT_TIMESTAMP" + " WHERE id = :Id", nativeQuery = true)
    void update(@Param("Id")Integer id, @Param("Status")int status);

}
