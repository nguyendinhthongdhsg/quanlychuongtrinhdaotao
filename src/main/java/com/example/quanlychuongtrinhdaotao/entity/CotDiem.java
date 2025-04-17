package com.example.quanlychuongtrinhdaotao.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ctdt_cotdiem")
@Data
public class CotDiem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "decuong_id", referencedColumnName = "id")
    private DeCuongChiTiet deCuongChiTiet;

    @Column(name = "ten_cot_diem", length = 100)
    private String tenCotDiem;

    @Column(name = "ty_le_phan_tram", precision = 5)
    private Double tyLePhanTram;

    @Column(name = "hinh_thuc", length = 100)
    private String hinhThuc;
}
