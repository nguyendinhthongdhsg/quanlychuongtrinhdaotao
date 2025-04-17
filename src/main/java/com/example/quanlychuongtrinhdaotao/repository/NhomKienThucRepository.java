package com.example.quanlychuongtrinhdaotao.repository;

import com.example.quanlychuongtrinhdaotao.entity.NhomKienThuc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NhomKienThucRepository extends JpaRepository<NhomKienThuc, Integer> {
    // Bạn có thể thêm các truy vấn tùy chỉnh ở đây nếu cần
}