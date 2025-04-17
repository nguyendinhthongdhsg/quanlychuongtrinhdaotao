package com.example.quanlychuongtrinhdaotao.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ctdt_hocphan")
public class HocPhan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ma_hp", length = 50, nullable = false)
    private String maHp;

    @Column(name = "ten_hp", length = 255, nullable = false)
    private String tenHp;

    @Column(name = "so_tin_chi")
    private Integer soTinChi;

    @Column(name = "so_tiet_ly_thuyet")
    private Integer soTietLyThuyet;

    @Column(name = "so_tiet_thuc_hanh")
    private Integer soTietThucHanh;

    @Column(name = "so_tiet_thuc_tap")
    private Integer soTietThucTap;

    @Column(name = "hoc_phan_tien_quyet", length = 255)
    private String hocPhanTienQuyet;

    @Column(name = "he_so")
    private Float heSo;
}
