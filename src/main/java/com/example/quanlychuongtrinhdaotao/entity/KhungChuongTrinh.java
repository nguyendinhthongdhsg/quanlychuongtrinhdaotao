package com.example.quanlychuongtrinhdaotao.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ctdt_khungchuongtrinh")
public class KhungChuongTrinh {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ctdt_id", nullable = false)
    private ThongTinChung thongTinChung;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nhom_kien_thuc_id", nullable = false)
    private NhomKienThuc nhomKienThuc;

    @Column(name = "so_tin_chi_bat_buoc_toi_thieu", nullable = false)
    private Integer soTinChiBatBuocToiThieu;

    @Column(name = "so_tin_chi_tu_chon_toi_thieu", nullable = false)
    private Integer soTinChiTuChonToiThieu;

    // Constructors
    public KhungChuongTrinh() {}
}
