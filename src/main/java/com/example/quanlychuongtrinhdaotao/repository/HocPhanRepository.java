package com.example.quanlychuongtrinhdaotao.repository;

import com.example.quanlychuongtrinhdaotao.entity.HocPhan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HocPhanRepository extends JpaRepository<HocPhan, Integer> {
    // Bạn có thể thêm các truy vấn tùy chỉnh ở đây nếu cần
    List<HocPhan> findByMaHpContainingOrTenHpContaining(String maHp, String tenHp);

    @Query("SELECT hp FROM HocPhan hp WHERE hp.id NOT IN (" +
            "SELECT hpc.hocPhan.id FROM HocPhan_ChuongTrinh hpc " +
            "WHERE hpc.thongTinChung.id = :idChuongTrinh)")
    List<HocPhan> findHocPhanChuaThemVaoChuongTrinh(@Param("idChuongTrinh")Integer id);
}