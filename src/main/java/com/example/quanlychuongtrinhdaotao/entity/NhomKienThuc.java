package com.example.quanlychuongtrinhdaotao.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ctdt_nhomkienthuc")
public class NhomKienThuc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "manhom", length = 50, nullable = false)
    private String maNhom;

    @Column(name = "ten_nhom", length = 255, nullable = false)
    private String tenNhom;

    @Column(name = "trangthai")
    private Integer trangThai;

    // Constructors
    public NhomKienThuc() {
    }

    public NhomKienThuc(String maNhom, String tenNhom, Integer trangThai) {
        this.maNhom = maNhom;
        this.tenNhom = tenNhom;
        this.trangThai = trangThai;
    }
}
