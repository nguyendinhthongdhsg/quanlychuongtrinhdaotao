package com.example.quanlychuongtrinhdaotao.service;

import com.example.quanlychuongtrinhdaotao.entity.GiangVien;
import com.example.quanlychuongtrinhdaotao.repository.GiangVienRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GiangVienService {
    private final GiangVienRepository giangVienRepository;

    public GiangVienService(GiangVienRepository giangVienRepository) {
        this.giangVienRepository = giangVienRepository;
    }

    // Lấy tất cả
    public List<GiangVien> getAllGiangVien() {
        return giangVienRepository.findAll();
    }

    // Lấy theo ID
    public GiangVien getGiangVienById(Long id) {
        Optional<GiangVien> optional = giangVienRepository.findById(id);
        return optional.orElse(null);
    }

    // Thêm mới hoặc cập nhật
    public GiangVien saveGiangVien(GiangVien giangVien) {
        return giangVienRepository.save(giangVien);
    }

    // Cập nhật
    public GiangVien updateGiangVien(GiangVien giangVien) {
        return giangVienRepository.save(giangVien);
    }

    // search
    public List<GiangVien> searchGiangVien(String keyword) {
        return giangVienRepository.searchGiangVien(keyword);
    }

}
