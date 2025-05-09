package com.example.quanlychuongtrinhdaotao.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Entity
@Table(name = "hocphan_trong_kehoach")
public class HocPhanTrongKeHoach {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kehoach_id", nullable = false)
    @ToString.Exclude
    private KeHoachDayHoc keHoachDayHoc;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hoc_phan_id", nullable = false)
    private HocPhan hocPhan;

    @Column(name = "ma_hoc_phan")
    private String maHocPhan;

    @Column(name = "ten_hoc_phan")
    private String tenHocPhan;

    @Column(name = "khoa")
    private String khoa;

    @Column(name = "sotc")
    private int soTC;

    @Column(name = "so_tietlt")
    private int soTietLT;

    @Column(name = "he_so")
    private Float heSo;

    @Column(name = "so_tietbt")
    private int soTietBT;

    @Column(name = "so_tietth")
    private int soTietTH;

    @Column(name = "tong_so_tiet")
    private int tongSoTiet;

    @Column(name = "so_nhom")
    private int soNhom;

    @Column(name = "slsv_per_nhom")
    private int slsvPerNhom;

    @OneToMany(mappedBy = "hocPhanTrongKeHoach", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<PhanCongGiangDay> phanCongList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hoc_phan_ctdt_id")
    private HocPhan_ChuongTrinh hocPhanChuongTrinh;
}
