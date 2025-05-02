

package com.example.quanlychuongtrinhdaotao.service;

import com.example.quanlychuongtrinhdaotao.entity.PhanCongGiangDay;
import com.example.quanlychuongtrinhdaotao.repository.PhanCongGiangDayRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhanCongGiangDayService {

    private final PhanCongGiangDayRepository phanCongRepository;

    public PhanCongGiangDayService(PhanCongGiangDayRepository phanCongRepository) {
        this.phanCongRepository = phanCongRepository;
    }

    public List<PhanCongGiangDay> getAll() {
        return phanCongRepository.findAll();
    }

    public List<PhanCongGiangDay> getAllByHocPhanId(Long hocPhanId) {
        return phanCongRepository.findByHocPhanTrongKeHoachId(hocPhanId);
    }

    public PhanCongGiangDay getById(Long id) {
        return phanCongRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy phân công với ID: " + id));
    }

    public PhanCongGiangDay save(PhanCongGiangDay phanCong) {
        if (phanCong.getHocPhanTrongKeHoach() == null) {
            throw new IllegalArgumentException("Học phần không được để trống");
        }
        if (phanCong.getGiangVien() == null) {
            throw new IllegalArgumentException("Giảng viên không được để trống");
        }
        if (phanCong.getSoTietThucHien() <= 0) {
            throw new IllegalArgumentException("Số tiết thực hiện phải lớn hơn 0");
        }
        return phanCongRepository.save(phanCong);
    }

    public void delete(Long id) {
        phanCongRepository.deleteById(id);
    }
}