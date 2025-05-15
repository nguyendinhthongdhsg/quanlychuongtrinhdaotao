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

    private int soTinChiBatBuocToiThieu;
    private int soTinChiTuChonToiThieu;

    // Constructors
    public KhungChuongTrinh() {}
}
