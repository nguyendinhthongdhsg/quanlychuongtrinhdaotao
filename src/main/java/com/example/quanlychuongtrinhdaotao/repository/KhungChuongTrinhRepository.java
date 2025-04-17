package com.example.quanlychuongtrinhdaotao.repository;

import com.example.quanlychuongtrinhdaotao.entity.KhungChuongTrinh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KhungChuongTrinhRepository extends JpaRepository<KhungChuongTrinh, Integer> {
    // Bạn có thể thêm các truy vấn tùy chỉnh ở đây nếu cần
    // Lấy danh sách khung chương trình theo chương trình đào tạo
    List<KhungChuongTrinh> findByThongTinChung_Id(Integer ctdtId);
}