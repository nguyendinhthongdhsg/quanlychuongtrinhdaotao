package com.example.quanlychuongtrinhdaotao.entity;

import jakarta.persistence.*;
import lombok.*;


@Data
@Entity
@Table(name = "ctdt_hocphan_chuongtrinhdaotao")
public class HocPhan_ChuongTrinh {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "hocphan_id", referencedColumnName = "id")
    private HocPhan hocPhan;

    @ManyToOne
    @JoinColumn(name = "ctdt_id", referencedColumnName = "id")
    private ThongTinChung thongTinChung;

    @ManyToOne
    @JoinColumn(name = "khung_id", referencedColumnName = "id")
    private KhungChuongTrinh khungChuongTrinh;

    @Column(name = "batbuoc")
    private Integer batBuoc;
}
