package com.example.quanlychuongtrinhdaotao.repository;

import com.example.quanlychuongtrinhdaotao.entity.KeHoachDayHoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface KeHoachDayHocRepository extends JpaRepository<KeHoachDayHoc, Long> {
    @Query("SELECT kh FROM KeHoachDayHoc kh WHERE kh.namHoc LIKE %:keyword%")
    List<KeHoachDayHoc> searchKeHoach(@Param("keyword") String keyword);
}