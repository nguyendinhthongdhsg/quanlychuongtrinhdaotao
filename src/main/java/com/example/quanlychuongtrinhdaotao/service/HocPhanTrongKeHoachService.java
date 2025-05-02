package com.example.quanlychuongtrinhdaotao.service;

import com.example.quanlychuongtrinhdaotao.entity.HocPhanTrongKeHoach;
import com.example.quanlychuongtrinhdaotao.repository.HocPhanTrongKeHoachRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HocPhanTrongKeHoachService {

    private final HocPhanTrongKeHoachRepository repository;

        public HocPhanTrongKeHoachService(HocPhanTrongKeHoachRepository repository) {
        this.repository = repository;
    }

    public List<HocPhanTrongKeHoach> getAll() {
        return repository.findAll();
    }

    public HocPhanTrongKeHoach getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy học phần với ID: " + id));
    }

    public void save(HocPhanTrongKeHoach hocPhan) {
        repository.save(hocPhan);
    }


    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Không tìm thấy học phần với ID: " + id);
        }
        repository.deleteById(id);
    }
    public List<HocPhanTrongKeHoach> getAllByKeHoachId(Long keHoachId) {
        return repository.findAllByKeHoachDayHoc_Id(keHoachId);
    }

    public List<HocPhanTrongKeHoach> searchInKeHoach(String keyword, Long keHoachId) {
        return repository.searchInKeHoach(keyword, keHoachId);
    }
}