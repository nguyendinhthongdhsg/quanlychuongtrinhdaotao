package com.example.quanlychuongtrinhdaotao.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Entity
@Table(name = "kehoachdayhoc")
public class KeHoachDayHoc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nam_hoc", nullable = false)
    @NotBlank(message = "Năm học không được để trống")
    private String namHoc;

    @Column(name = "hocky", nullable = false)
    @NotBlank(message = "Học kỳ không được để trống")
    private String hocKy;

    @OneToMany(mappedBy = "keHoachDayHoc", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<HocPhanTrongKeHoach> hocPhanTrongKeHoachList;

}
