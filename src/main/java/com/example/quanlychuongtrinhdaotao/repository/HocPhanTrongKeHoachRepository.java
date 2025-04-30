package com.example.quanlychuongtrinhdaotao.repository;

import com.example.quanlychuongtrinhdaotao.entity.HocPhanTrongKeHoach;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HocPhanTrongKeHoachRepository extends JpaRepository<HocPhanTrongKeHoach, Long> {
    List<HocPhanTrongKeHoach> findAllByKeHoachDayHoc_Id(Long keHoachId);
}


