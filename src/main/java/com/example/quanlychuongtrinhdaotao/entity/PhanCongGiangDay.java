package com.example.quanlychuongtrinhdaotao.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
public class PhanCongGiangDay {

    @Id
    @GeneratedValue
    private Long id;

    private int soTietThucHien;

    private int nhom;

    @ManyToOne
    @ToString.Exclude
    private HocPhanTrongKeHoach hocPhanTrongKeHoach;

    @ManyToOne
    private GiangVien giangVien;
}
