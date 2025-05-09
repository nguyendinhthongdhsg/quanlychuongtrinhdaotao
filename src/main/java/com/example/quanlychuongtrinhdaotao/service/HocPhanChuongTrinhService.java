package com.example.quanlychuongtrinhdaotao.service;

import com.example.quanlychuongtrinhdaotao.entity.HocPhan_ChuongTrinh;
import com.example.quanlychuongtrinhdaotao.repository.HocPhanChuongTrinhRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HocPhanChuongTrinhService {
    @Autowired
    private HocPhanChuongTrinhRepository repository;

    public List<HocPhan_ChuongTrinh> findAll() {
        return repository.findAll();
    }
}
