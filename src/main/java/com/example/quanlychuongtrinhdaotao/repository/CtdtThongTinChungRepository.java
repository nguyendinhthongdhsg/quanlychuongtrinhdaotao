package com.example.quanlychuongtrinhdaotao.repository;

import com.example.quanlychuongtrinhdaotao.entity.CtdtThongTinChung;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CtdtThongTinChungRepository extends JpaRepository<CtdtThongTinChung, Long> {
    // Bạn có thể thêm các truy vấn tùy chỉnh ở đây nếu cần
}