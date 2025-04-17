package com.example.quanlychuongtrinhdaotao.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "ctdt_decuongchitiet")
@Data
public class DeCuongChiTiet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "hoc_phan_id", referencedColumnName = "id")
    private HocPhan hocPhan;

    @Column(name = "muc_tieu", columnDefinition = "TEXT")
    private String mucTieu;

    @Column(name = "noi_dung", columnDefinition = "TEXT")
    private String noiDung;

    @Column(name = "phuong_phap_giang_day", columnDefinition = "TEXT")
    private String phuongPhapGiangDay;

    @Column(name = "phuong_phap_danh_gia", columnDefinition = "TEXT")
    private String phuongPhapDanhGia;

    @Column(name = "tai_lieu_tham_khao", columnDefinition = "TEXT")
    private String taiLieuThamKhao;

    // Thêm mối quan hệ với CotDiem
    @OneToMany(mappedBy = "deCuongChiTiet", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CotDiem> cotDiems;

    @Column(name = "trang_thai")
    private Integer trangThai;
}

