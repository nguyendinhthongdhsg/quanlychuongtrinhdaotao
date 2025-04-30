package com.example.quanlychuongtrinhdaotao.service;

import com.example.quanlychuongtrinhdaotao.entity.KeHoachDayHoc;
import com.example.quanlychuongtrinhdaotao.repository.KeHoachDayHocRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KeHoachDayHocService {

    private final KeHoachDayHocRepository repository;

    public KeHoachDayHocService(KeHoachDayHocRepository repository) {
        this.repository = repository;
    }

    public List<KeHoachDayHoc> getAllKeHoach() {
        return repository.findAll();
    }

    public KeHoachDayHoc getKeHoachById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy kế hoạch với ID: " + id));
    }

    public void saveKeHoach(KeHoachDayHoc keHoach) {
        repository.save(keHoach);
    }

    public void deleteKeHoach(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Không tìm thấy kế hoạch với ID: " + id);
        }
        repository.deleteById(id);
    }
}