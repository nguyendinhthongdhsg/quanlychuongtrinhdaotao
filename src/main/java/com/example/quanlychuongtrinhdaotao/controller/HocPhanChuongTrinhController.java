package com.example.quanlychuongtrinhdaotao.controller;

import com.example.quanlychuongtrinhdaotao.entity.HocPhan_ChuongTrinh;
import com.example.quanlychuongtrinhdaotao.entity.HocPhan;
import com.example.quanlychuongtrinhdaotao.entity.ThongTinChung;
import com.example.quanlychuongtrinhdaotao.entity.KhungChuongTrinh;
import com.example.quanlychuongtrinhdaotao.repository.HocPhanChuongTrinhRepository;
import com.example.quanlychuongtrinhdaotao.repository.HocPhanRepository;
import com.example.quanlychuongtrinhdaotao.repository.ThongTinChungRepository;
import com.example.quanlychuongtrinhdaotao.repository.KhungChuongTrinhRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("hocPhanChuongTrinh", new HocPhan_ChuongTrinh());
        model.addAttribute("dsHocPhan", hocPhanRepository.findHocPhanChuaThemVaoChuongTrinh());
        model.addAttribute("dsCTDT", thongTinChungRepository.findAll());
        model.addAttribute("dsKhung", khungChuongTrinhRepository.findAll());
        return "hoc_phan_ctdt_add";
    }

    // Xử lý thêm học phần vào chương trình đào tạo
    @PostMapping("/add")
    public String addHocPhanToCTDT(@ModelAttribute("hocPhanChuongTrinh") HocPhan_ChuongTrinh hocPhanChuongTrinh) {
        hocPhanChuongTrinhRepository.save(hocPhanChuongTrinh);
        return "redirect:/thong-tin-chung";
    }
}
