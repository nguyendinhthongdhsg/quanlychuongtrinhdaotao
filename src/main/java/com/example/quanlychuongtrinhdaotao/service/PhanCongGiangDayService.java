//package com.example.quanlychuongtrinhdaotao.service;
//
//import com.example.quanlychuongtrinhdaotao.entity.PhanCongGiangDay;
//import com.example.quanlychuongtrinhdaotao.repository.PhanCongGiangDayRepository;
//import jakarta.validation.Valid;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class PhanCongGiangDayService {
//
//    private final PhanCongGiangDayRepository repository;
//
//    // Constructor injection
//    public PhanCongGiangDayService(PhanCongGiangDayRepository repository) {
//        this.repository = repository;
//    }
//
//    /**
//     * Lấy danh sách tất cả phân công giảng dạy.
//     * @return danh sách phân công
//     */
//    public List<PhanCongGiangDay> getAll() {
//        return repository.findAll();
//    }
//
//    /**
//     * Lấy phân công giảng dạy theo ID.
//     * @param id ID của phân công
//     * @return phân công nếu tồn tại
//     * @throws IllegalArgumentException nếu không tìm thấy phân công
//     */
//    public PhanCongGiangDay getById(Long id) {
//        return repository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy phân công với ID: " + id));
//    }
//
//    /**
//     * Lưu phân công giảng dạy vào cơ sở dữ liệu.
//     * @param pcgd phân công cần lưu
//     * @return phân công đã được lưu
//     * @throws IllegalArgumentException nếu dữ liệu không hợp lệ
//     */
//    public PhanCongGiangDay save(@Valid PhanCongGiangDay pcgd) {
//        if (pcgd == null) {
//            throw new IllegalArgumentException("Phân công giảng dạy không được null");
//        }
//        // Có thể thêm kiểm tra logic nghiệp vụ, ví dụ: kiểm tra số tiết
//        return repository.save(pcgd);
//    }
//
//    /**
//     * Xóa phân công giảng dạy theo ID.
//     * @param id ID của phân công
//     * @throws IllegalArgumentException nếu phân công không tồn tại
//     */
//    public void delete(Long id) {
//        if (!repository.existsById(id)) {
//            throw new IllegalArgumentException("Không tìm thấy phân công với ID: " + id);
//        }
//        repository.deleteById(id);
//    }
//
//    /**
//     * Lấy danh sách phân công theo ID học phần trong kế hoạch.
//     * @param hocPhanId ID của học phần
//     * @return danh sách phân công
//     */
//    public List<PhanCongGiangDay> findByHocPhanTrongKeHoachId(Long hocPhanId) {
//        return repository.findByHocPhanTrongKeHoachId(hocPhanId);
//    }
//}


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