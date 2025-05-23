package com.example.quanlychuongtrinhdaotao.repository;

import com.example.quanlychuongtrinhdaotao.entity.PhanCongGiangDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhanCongGiangDayRepository extends JpaRepository<PhanCongGiangDay, Long> {

    @Query("SELECT p FROM PhanCongGiangDay p WHERE p.hocPhanTrongKeHoach.id = :hocPhanId")
    List<PhanCongGiangDay> findByHocPhanTrongKeHoachId(Long hocPhanId);

    boolean existsById(Long id);
}