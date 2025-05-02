package com.example.quanlychuongtrinhdaotao.controller;

import com.example.quanlychuongtrinhdaotao.entity.HocPhan;
import com.example.quanlychuongtrinhdaotao.repository.HocPhanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.List;

@Controller
@RequestMapping("/hoc-phan")
public class HocPhanController {

    @Autowired
    private HocPhanRepository hocPhanRepository;

    // Hiển thị danh sách học phần
    @GetMapping
    public String getAll(Model model) {
        List<HocPhan> hocPhanList = hocPhanRepository.findAll();
        model.addAttribute("hocPhanList", hocPhanList);
        return "hoc_phan_list";
    }

    // Hiển thị form tìm kiếm học phần
    @GetMapping("/search")
    public String searchHocPhan(@RequestParam("query") String query, Model model) {
        List<HocPhan> hocPhanList = hocPhanRepository.findByMaHpContainingOrTenHpContaining(query, query);
        model.addAttribute("hocPhanList", hocPhanList);
        return "hoc_phan_list";
    }

    // Hiển thị form thêm học phần
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("hocPhan", new HocPhan());
        return "hoc_phan_add";
    }

    // Xử lý thêm học phần
    @PostMapping("/add")
    public String addHocPhan(@ModelAttribute("hocPhan") HocPhan hocPhan) {
        hocPhanRepository.save(hocPhan);
        return "redirect:/hoc-phan";
    }

    // Hiển thị form sửa học phần
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        Optional<HocPhan> hocPhan = hocPhanRepository.findById(id);
        hocPhan.ifPresent(hp -> model.addAttribute("hocPhan", hp));
        return "hoc_phan_edit";
    }

    // Xử lý cập nhật học phần
    @PostMapping("/edit/{id}")
    public String editHocPhan(@PathVariable Integer id, @ModelAttribute("hocPhan") HocPhan hocPhan) {
        Optional<HocPhan> existingData = hocPhanRepository.findById(id);
        if (existingData.isPresent()) {
            HocPhan existing = existingData.get();
            existing.setMaHp(hocPhan.getMaHp());
            existing.setTenHp(hocPhan.getTenHp());
            existing.setSoTinChi(hocPhan.getSoTinChi());
            existing.setSoTietLyThuyet(hocPhan.getSoTietLyThuyet());
            existing.setSoTietThucHanh(hocPhan.getSoTietThucHanh());
            existing.setSoTietThucTap(hocPhan.getSoTietThucTap());
            existing.setHocPhanTienQuyet(hocPhan.getHocPhanTienQuyet());
            existing.setHeSo(hocPhan.getHeSo());
            hocPhanRepository.save(existing);
        }
        return "redirect:/hoc-phan";
    }

    // Xóa học phần
    @GetMapping("/delete/{id}")
    public String deleteHocPhan(@PathVariable Integer id) {
        hocPhanRepository.deleteById(id);
        return "redirect:/hoc-phan";
    }

    // Xuất danh sách học phần
    @GetMapping("/xuat-danh-sach")
    public String xuatDanhSachHocPhan(Model model) {
        try {
            List<HocPhan> hocPhanList = hocPhanRepository.findAll();
            model.addAttribute("hocPhanList", hocPhanList);
            // Thống kê tổng thể
            model.addAttribute("tongHocPhan", hocPhanList.size());
            model.addAttribute("tongTinChi", calculateTotalCredits(hocPhanList));
            model.addAttribute("tongTietHoc", calculateTotalLessons(hocPhanList));
            return "hoc_phan_export";
        } catch (Exception e) {
            return "redirect:/hoc-phan";
        }
    }

    // tính tổng số tín chỉ
    private int calculateTotalCredits(List<HocPhan> hocPhanList) {
        return hocPhanList.stream()
                .mapToInt(HocPhan::getSoTinChi)
                .sum();
    }

    //  tính tổng số tiết học
    private int calculateTotalLessons(List<HocPhan> hocPhanList) {
        return hocPhanList.stream()
                .mapToInt(hp -> hp.getSoTietLyThuyet() + hp.getSoTietThucHanh() + hp.getSoTietThucTap())
                .sum();
    }
}
