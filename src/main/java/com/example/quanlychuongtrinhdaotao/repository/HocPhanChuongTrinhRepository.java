package com.example.quanlychuongtrinhdaotao.repository;

import com.example.quanlychuongtrinhdaotao.entity.HocPhan_ChuongTrinh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HocPhanChuongTrinhRepository extends JpaRepository<HocPhan_ChuongTrinh, Integer> {
    // Lấy danh sách học phần theo chương trình đào tạo
    List<HocPhan_ChuongTrinh> findByThongTinChung_Id(Integer ctdtId);
}