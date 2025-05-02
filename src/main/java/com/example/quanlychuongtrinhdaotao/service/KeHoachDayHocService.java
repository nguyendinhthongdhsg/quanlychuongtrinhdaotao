

package com.example.quanlychuongtrinhdaotao.service;

import com.example.quanlychuongtrinhdaotao.entity.GiangVien;
import com.example.quanlychuongtrinhdaotao.entity.HocPhanTrongKeHoach;
import com.example.quanlychuongtrinhdaotao.entity.KeHoachDayHoc;
import com.example.quanlychuongtrinhdaotao.entity.PhanCongGiangDay;
import com.example.quanlychuongtrinhdaotao.repository.KeHoachDayHocRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
    //search
    public List<KeHoachDayHoc> searchKeHoach(String keyword) {
        return repository.searchKeHoach(keyword);
    }
    public void deleteKeHoach(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Không tìm thấy kế hoạch với ID: " + id);
        }
        repository.deleteById(id);
    }

    // Phương thức lấy chi tiết kế hoạch cho việc xuất phiếu báo cáo
    public KeHoachDayHoc getKeHoachDetailForExport(Long id) {
        KeHoachDayHoc keHoach = getKeHoachById(id);
        keHoach.getHocPhanTrongKeHoachList().forEach(hp -> {
            hp.getPhanCongList().size();
            // Đảm bảo thông tin giảng viên được tải
            hp.getPhanCongList().forEach(pc -> {
                if (pc.getGiangVien() != null) {
                    pc.getGiangVien().getHoTen(); // Force lazy loading
                    pc.getGiangVien().getEmail(); // Force lazy loading
                }
            });
        });
        return keHoach;
    }
    // Đếm tổng số giảng viên tham gia giảng dạy trong kế hoạch
    public int countTotalGiangVienInKeHoach(KeHoachDayHoc keHoach) {
        Set<Long> uniqueGiangVienIds = new HashSet<>();
        for (HocPhanTrongKeHoach hocPhan : keHoach.getHocPhanTrongKeHoachList()) {
            for (PhanCongGiangDay phanCong : hocPhan.getPhanCongList()) {
                if (phanCong.getGiangVien() != null) {
                    uniqueGiangVienIds.add(phanCong.getGiangVien().getId());
                }
            }
        }
        return uniqueGiangVienIds.size();
    }
    // Tính tổng số tiết giảng dạy trong kế hoạch
    public int calculateTotalTeachingHours(KeHoachDayHoc keHoach) {
        int totalHours = 0;

        for (HocPhanTrongKeHoach hocPhan : keHoach.getHocPhanTrongKeHoachList()) {
            for (PhanCongGiangDay phanCong : hocPhan.getPhanCongList()) {
                totalHours += phanCong.getSoTietThucHien();
            }
        }

        return totalHours;
    }

    // Phương thức lấy các học phần theo khoa
    public List<HocPhanTrongKeHoach> getHocPhanListByKhoa(KeHoachDayHoc keHoach, String khoa) {
        return keHoach.getHocPhanTrongKeHoachList().stream()
                .filter(hp -> khoa.equals(hp.getKhoa()))
                .collect(Collectors.toList());
    }

    // Phương thức tính tổng số tiết của một khoa trong kế hoạch
    public int calculateTotalTeachingHoursByKhoa(KeHoachDayHoc keHoach, String khoa) {
        return keHoach.getHocPhanTrongKeHoachList().stream()
                .filter(hp -> khoa.equals(hp.getKhoa()))
                .flatMap(hp -> hp.getPhanCongList().stream())
                .mapToInt(PhanCongGiangDay::getSoTietThucHien)
                .sum();
    }

    // Phương thức đếm số giảng viên theo khoa trong kế hoạch
    public int countGiangVienByKhoa(KeHoachDayHoc keHoach, String khoa) {
        Set<Long> uniqueGiangVienIds = new HashSet<>();

        keHoach.getHocPhanTrongKeHoachList().stream()
                .filter(hp -> khoa.equals(hp.getKhoa()))
                .flatMap(hp -> hp.getPhanCongList().stream())
                .map(PhanCongGiangDay::getGiangVien)
                .filter(Objects::nonNull)
                .forEach(gv -> uniqueGiangVienIds.add(gv.getId()));

        return uniqueGiangVienIds.size();
    }

}