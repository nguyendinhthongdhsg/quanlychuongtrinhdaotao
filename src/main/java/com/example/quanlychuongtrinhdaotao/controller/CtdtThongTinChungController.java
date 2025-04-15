package com.example.quanlychuongtrinhdaotao.controller;

import com.example.quanlychuongtrinhdaotao.entity.CtdtThongTinChung;
import com.example.quanlychuongtrinhdaotao.repository.CtdtThongTinChungRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/thong-tin-chung")
public class CtdtThongTinChungController {

    @Autowired
    private CtdtThongTinChungRepository repository;

    // Hiển thị danh sách
    @GetMapping
    public String getAll(Model model) {
        List<CtdtThongTinChung> dataList = repository.findAll();
        model.addAttribute("dataList", dataList);
        return "thong_tin_chung_list";  // tên file HTML sử dụng Thymeleaf
    }

    // Hiển thị chi tiết theo ID
    @GetMapping("/{id}")
    public String getById(@PathVariable Long id, Model model) {
        Optional<CtdtThongTinChung> data = repository.findById(id);
        data.ifPresent(d -> model.addAttribute("data", d));
        return "thong_tin_chung_detail";  // tên file HTML chi tiết
    }

    // Thêm mới
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("ctdtThongTinChung", new CtdtThongTinChung());
        return "thong_tin_chung_add";  // form thêm mới
    }

    @PostMapping("/add")
    public String addCtdtThongTinChung(@ModelAttribute("ctdtThongTinChung") CtdtThongTinChung ctdtThongTinChung) {
        ctdtThongTinChung.setVersion("1");
        ctdtThongTinChung.setStatus("Đang mở");
        ctdtThongTinChung.setCreatedAt(LocalDateTime.now());
        repository.save(ctdtThongTinChung);
        return "redirect:/thong-tin-chung";  // Redirect về danh sách
    }

    // Cập nhật
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<CtdtThongTinChung> data = repository.findById(id);
        data.ifPresent(d -> model.addAttribute("ctdtThongTinChung", d));
        return "thong_tin_chung_edit";  // form cập nhật
    }

    @PostMapping("/edit/{id}")
    public String editCtdtThongTinChung(@PathVariable Long id, @ModelAttribute("ctdtThongTinChung") CtdtThongTinChung ctdtThongTinChung) {
        ctdtThongTinChung.setId(id);
        repository.save(ctdtThongTinChung);
        return "redirect:/thong-tin-chung";  // Redirect về danh sách
    }

    // Xóa
    @GetMapping("/delete/{id}")
    public String deleteCtdtThongTinChung(@PathVariable Long id) {
        repository.deleteById(id);
        return "redirect:/thong-tin-chung";  // Redirect về danh sách
    }
}
