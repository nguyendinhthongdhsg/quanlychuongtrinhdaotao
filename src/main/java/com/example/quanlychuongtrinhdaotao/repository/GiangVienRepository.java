package com.example.quanlychuongtrinhdaotao.repository;

import com.example.quanlychuongtrinhdaotao.entity.GiangVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GiangVienRepository extends JpaRepository<GiangVien, Long> {
    @Query("SELECT gv FROM GiangVien gv WHERE gv.maGV LIKE %:keyword% OR gv.hoTen LIKE %:keyword%")
    List<GiangVien> searchGiangVien(@Param("keyword") String keyword);
}
