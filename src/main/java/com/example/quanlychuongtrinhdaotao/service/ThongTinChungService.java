package com.example.quanlychuongtrinhdaotao.service;


import com.example.quanlychuongtrinhdaotao.entity.*;
import com.example.quanlychuongtrinhdaotao.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ThongTinChungService {

    @Autowired
    private ThongTinChungRepository thongTinChungRepository;

    @Autowired
    private KhungChuongTrinhRepository khungChuongTrinhRepository;

    @Autowired
    private HocPhanChuongTrinhRepository hocPhanChuongTrinhRepository;

    public Optional<ThongTinChung> getThongTinChungById(Integer id) {
        return thongTinChungRepository.findById(id);
    }

    public List<KhungChuongTrinh> getKhungChuongTrinhByThongTinChungId(Integer id) {
        return khungChuongTrinhRepository.findByThongTinChung_Id(id);
    }

    public List<HocPhan_ChuongTrinh> getHocPhanChuongTrinhByThongTinChungId(Integer id) {
        return hocPhanChuongTrinhRepository.findHocPhan_ChuongTrinhsByThongTinChung_Id(id);
    }

    public List<ThongTinChung> getAllThongTinChung() {
        return thongTinChungRepository.findAll();
    }

}

