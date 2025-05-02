package com.example.quanlychuongtrinhdaotao.repository;

import com.example.quanlychuongtrinhdaotao.entity.HocPhanTrongKeHoach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HocPhanTrongKeHoachRepository extends JpaRepository<HocPhanTrongKeHoach, Long> {
    List<HocPhanTrongKeHoach> findAllByKeHoachDayHoc_Id(Long keHoachId);

    @Query("SELECT hp FROM HocPhanTrongKeHoach hp WHERE " +
            "(hp.maHocPhan LIKE %:keyword% OR hp.tenHocPhan LIKE %:keyword%) " +
            "AND hp.keHoachDayHoc.id = :keHoachId")
    List<HocPhanTrongKeHoach> searchInKeHoach(
            @Param("keyword") String keyword,
            @Param("keHoachId") Long keHoachId
    );
}


