package com.example.quanlychuongtrinhdaotao.entity;

import java.util.List;

public class ThongKePhanCongDTO {
    private GiangVien giangVien;
    private List<PhanCongGiangDay> phanCongList;
    private int tongTietGiangDay;
    private int tongTietCongTacKhac;
    private int tongSoTietCongTac;

    public ThongKePhanCongDTO(GiangVien giangVien, List<PhanCongGiangDay> phanCongList, int tongTietGiangDay,
            int tongTietCongTacKhac) {
        this.giangVien = giangVien;
        this.phanCongList = phanCongList;
        this.tongTietGiangDay = tongTietGiangDay;
        this.tongTietCongTacKhac = tongTietCongTacKhac;
        this.tongSoTietCongTac = tongTietGiangDay + tongTietCongTacKhac;
    }

    // Getter, Setter
    public GiangVien getGiangVien() {
        return giangVien;
    }

    public List<PhanCongGiangDay> getPhanCongList() {
        return phanCongList;
    }

    public int getTongTietGiangDay() {
        return tongTietGiangDay;
    }

    public int getTongTietCongTacKhac() {
        return tongTietCongTacKhac;
    }

    public int getTongSoTietCongTac() {
        return tongSoTietCongTac;
    }
}