package com.example.quanlychuongtrinhdaotao.repository;

import com.example.quanlychuongtrinhdaotao.entity.PhanCongGiangDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhanCongGiangDayRepository extends JpaRepository<PhanCongGiangDay, Long> {

    /**
     * Tìm danh sách phân công giảng dạy theo ID học phần trong kế hoạch.
     * @param hocPhanId ID của học phần
     * @return danh sách phân công giảng dạy
     */
    @Query("SELECT p FROM PhanCongGiangDay p WHERE p.hocPhanTrongKeHoach.id = :hocPhanId")
    List<PhanCongGiangDay> findByHocPhanTrongKeHoachId(Long hocPhanId);

    /**
     * Kiểm tra xem phân công có tồn tại theo ID hay không.
     * @param id ID của phân công
     * @return true nếu tồn tại, false nếu không
     */
    boolean existsById(Long id);
}