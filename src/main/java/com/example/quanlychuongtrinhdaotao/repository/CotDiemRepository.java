package com.example.quanlychuongtrinhdaotao.repository;

import com.example.quanlychuongtrinhdaotao.entity.CotDiem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CotDiemRepository extends JpaRepository<CotDiem, Integer> {
    List<CotDiem> findByDeCuongChiTietId(Integer deCuongChiTietId);
}