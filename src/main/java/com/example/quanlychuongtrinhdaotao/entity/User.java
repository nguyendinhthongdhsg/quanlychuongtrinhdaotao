package com.example.quanlychuongtrinhdaotao.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ctdt_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, columnDefinition = "INT UNSIGNED")
    private int id;

    @Column(name = "username", nullable = false, length = 50)
    private String username;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "ho_ten", nullable = true, length = 100)
    private String hoTen;

    @Column(name = "email", nullable = true, length = 100)
    private String email;

    @Column(name = "so_dien_thoai", nullable = true, length = 20)
    private String soDienThoai;

    @Column(name = "vai_tro", nullable = true, length = 30)
    private String vaiTro;

    @Column(name = "nam_sinh", nullable = true)
    private Integer namSinh;

    @Column(name = "trang_thai", nullable = false)
    private Boolean trangThai;

    public User() {
    }

    public User(String username, String password, String hoTen, String email, String soDienThoai, String vaiTro, Integer namSinh, Boolean trangThai) {
        this.username = username;
        this.password = password;
        this.hoTen = hoTen;
        this.email = email;
        this.soDienThoai = soDienThoai;
        this.vaiTro = vaiTro;
        this.namSinh = namSinh;
        this.trangThai = trangThai;
    }
}