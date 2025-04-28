package com.example.quanlychuongtrinhdaotao.controller;

import com.example.quanlychuongtrinhdaotao.entity.HocPhan_ChuongTrinh;
import com.example.quanlychuongtrinhdaotao.entity.ThongTinChung;
import com.example.quanlychuongtrinhdaotao.repository.HocPhanChuongTrinhRepository;
import com.example.quanlychuongtrinhdaotao.repository.HocPhanRepository;
import com.example.quanlychuongtrinhdaotao.repository.ThongTinChungRepository;
import com.example.quanlychuongtrinhdaotao.repository.KhungChuongTrinhRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/hoc-phan-ctdt")
public class HocPhanChuongTrinhController {

    @Autowired
    private HocPhanChuongTrinhRepository hocPhanChuongTrinhRepository;

    @Autowired
    private HocPhanRepository hocPhanRepository;

    @Autowired
    private ThongTinChungRepository thongTinChungRepository;

    @Autowired
    private KhungChuongTrinhRepository khungChuongTrinhRepository;

    // Hiển thị form thêm học phần vào chương trình đào tạo
    @GetMapping("/add/{id}")
    public String showAddForm(@PathVariable Integer id, Model model) {
        Optional<ThongTinChung> thongTinChungOpt = thongTinChungRepository.findById(id);
        if (thongTinChungOpt.isEmpty()) {
            model.addAttribute("error", "Không tìm thấy chương trình đào tạo.");
            return "error";
        }

        model.addAttribute("hocPhanChuongTrinh", new HocPhan_ChuongTrinh());
        model.addAttribute("dsHocPhan", hocPhanRepository.findHocPhanChuaThemVaoChuongTrinh(id));
        model.addAttribute("CTDT", thongTinChungOpt.get());
        model.addAttribute("dsKhung", khungChuongTrinhRepository.findByThongTinChung_Id(id));

        return "hoc_phan_ctdt_add";
    }

    // Xử lý thêm học phần vào chương trình đào tạo
    @PostMapping("/add/{id}")
    public String addHocPhanToCTDT(@ModelAttribute("hocPhanChuongTrinh") HocPhan_ChuongTrinh hocPhanChuongTrinh) {
        hocPhanChuongTrinh.setId(null);
        hocPhanChuongTrinhRepository.save(hocPhanChuongTrinh);
        return "redirect:/thong-tin-chung/" + hocPhanChuongTrinh.getThongTinChung().getId();
    }
}

