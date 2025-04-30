package com.example.quanlychuongtrinhdaotao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "ctdt_giangvien")
public class GiangVien {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnore
    private User user;

    @Column(name = "ma_gv", length = 50)
    private String maGV;

    @Column(name = "ho_ten", length = 100)
    private String hoTen;

    @Column(name = "bo_mon", length = 100)
    private String boMon;

    @Column(name = "khoa", length = 100)
    private String khoa;

    @Column(name = "trinh_do", length = 100)
    private String trinhDo;

    @Column(name = "chuyen_mon", columnDefinition = "TEXT")
    private String chuyenMon;

    @Column(name = "trang_thai", length = 20)
    private String trangThai;

    @Transient
    private String username;

    @Transient
    private String email;

    public GiangVien() {}

    public GiangVien(User user, String maGV, String hoTen, String boMon, String khoa,
                     String trinhDo, String chuyenMon, String trangThai) {
        this.user = user;
        this.maGV = maGV;
        this.hoTen = hoTen;
        this.boMon = boMon;
        this.khoa = khoa;
        this.trinhDo = trinhDo;
        this.chuyenMon = chuyenMon;
        this.trangThai = trangThai;
        // Ánh xạ thủ công từ User
        if (user != null) {
            this.username = user.getUsername();
            this.email = user.getEmail();
        }
    }

    // Getters và Setters
    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getMaGV() {
        return maGV;
    }

    public String getHoTen() {
        return hoTen;
    }

    public String getBoMon() {
        return boMon;
    }

    public String getKhoa() {
        return khoa;
    }

    public String getTrinhDo() {
        return trinhDo;
    }

    public String getChuyenMon() {
        return chuyenMon;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public String getUsername() {
        return username != null ? username : (user != null ? user.getUsername() : null);
    }

    public String getEmail() {
        return email != null ? email : (user != null ? user.getEmail() : null);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
        if (user != null) {
            this.username = user.getUsername();
            this.email = user.getEmail();
        }
    }

    public void setMaGV(String maGV) {
        this.maGV = maGV;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public void setBoMon(String boMon) {
        this.boMon = boMon;
    }

    public void setKhoa(String khoa) {
        this.khoa = khoa;
    }

    public void setTrinhDo(String trinhDo) {
        this.trinhDo = trinhDo;
    }

    public void setChuyenMon(String chuyenMon) {
        this.chuyenMon = chuyenMon;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}