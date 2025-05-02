package com.example.quanlychuongtrinhdaotao.repository;

import com.example.quanlychuongtrinhdaotao.entity.HocPhan_ChuongTrinh;
import com.example.quanlychuongtrinhdaotao.entity.ThongTinChung;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HocPhanChuongTrinhRepository extends JpaRepository<HocPhan_ChuongTrinh, Integer> {

    List<HocPhan_ChuongTrinh> findHocPhan_ChuongTrinhsByThongTinChung_Id(Integer thongTinChungId);

}