package com.example.quanlychuongtrinhdaotao.repository;

import com.example.quanlychuongtrinhdaotao.entity.GiangVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GiangVienRepository extends JpaRepository<GiangVien, Long> {

    @Query("SELECT g FROM GiangVien g WHERE g.hoTen LIKE %?1% OR g.maGV LIKE %?1%")
    List<GiangVien> searchGiangVien(String keyword);

    GiangVien findByMaGV(String maGV);
}