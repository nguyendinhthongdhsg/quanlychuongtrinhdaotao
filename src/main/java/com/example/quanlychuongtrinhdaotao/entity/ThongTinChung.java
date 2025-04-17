package com.example.quanlychuongtrinhdaotao.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "ctdt_thongtinchung")
public class ThongTinChung {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ma_ctdt", length = 50, nullable = false)
    private String maCtdt;

    @Column(name = "ten_ctdt", length = 255, nullable = false)
    private String tenCtdt;

    @Column(name = "nganh", length = 100)
    private String nganh;

    @Column(name = "ma_nganh", length = 50)
    private String maNganh;

    @Column(name = "khoa_quan_ly", length = 100)
    private String khoaQuanLy;

    @Column(name = "he_dao_tao", length = 50)
    private String heDaoTao;

    @Column(name = "trinh_do", length = 50)
    private String trinhDo;

    @Column(name = "tong_tin_chi")
    private Integer tongTinChi;

    @Column(name = "thoi_gian_dao_tao", length = 50)
    private String thoiGianDaoTao;

    @Column(name = "nam_ban_hanh")
    private Integer namBanHanh;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @Column(name = "gioi_thieu", columnDefinition = "TEXT")
    private String gioiThieu;

    // Thiết lập OneToMany với KhungChuongTrinh
    @OneToMany(mappedBy = "thongTinChung", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<KhungChuongTrinh> khungChuongTrinhList = new ArrayList<>();

    public ThongTinChung() {}

    public ThongTinChung(String maCtdt, String tenCtdt, String nganh, String maNganh, String khoaQuanLy, String heDaoTao, String trinhDo, Integer tongTinChi, String thoiGianDaoTao, Integer namBanHanh, Integer trangThai, String gioiThieu) {
        this.maCtdt = maCtdt;
        this.tenCtdt = tenCtdt;
        this.nganh = nganh;
        this.maNganh = maNganh;
        this.khoaQuanLy = khoaQuanLy;
        this.heDaoTao = heDaoTao;
        this.trinhDo = trinhDo;
        this.tongTinChi = tongTinChi;
        this.thoiGianDaoTao = thoiGianDaoTao;
        this.namBanHanh = namBanHanh;
        this.trangThai = trangThai;
        this.gioiThieu = gioiThieu;
    }
}
