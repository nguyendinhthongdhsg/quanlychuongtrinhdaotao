package com.example.quanlychuongtrinhdaotao.repository;

import com.example.quanlychuongtrinhdaotao.entity.DeCuongChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeCuongChiTietRepository extends JpaRepository<DeCuongChiTiet, Integer> {
    // Bạn có thể thêm các truy vấn tùy chỉnh ở đây nếu cần
}