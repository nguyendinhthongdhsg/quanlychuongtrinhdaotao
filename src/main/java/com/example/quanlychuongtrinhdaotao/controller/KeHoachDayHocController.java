
package com.example.quanlychuongtrinhdaotao.controller;

import com.example.quanlychuongtrinhdaotao.entity.KeHoachDayHoc;
import com.example.quanlychuongtrinhdaotao.service.KeHoachDayHocService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/ke-hoach-day-hoc")
public class KeHoachDayHocController {

    private final KeHoachDayHocService keHoachDayHocService;

    public KeHoachDayHocController(KeHoachDayHocService keHoachDayHocService) {
        this.keHoachDayHocService = keHoachDayHocService;
    }

    // Hiển thị danh sách kế hoạch dạy học
    @GetMapping
    public String getAll(Model model) {
        try {
            List<KeHoachDayHoc> keHoachList = keHoachDayHocService.getAllKeHoach();
            model.addAttribute("keHoachList", keHoachList);
        } catch (Exception e) {
            model.addAttribute("keHoachList", List.of());
            model.addAttribute("errorMessage", "Không thể lấy danh sách kế hoạch: " + e.getMessage());
        }
        return "kehoach_list";
    }

    // Hiển thị form thêm kế hoạch
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("keHoach", new KeHoachDayHoc());
        return "kehoach_form";
    }

    // Xử lý thêm kế hoạch
    @PostMapping("/add")
    public String add(@ModelAttribute KeHoachDayHoc keHoach, RedirectAttributes redirectAttributes) {
        try {
            keHoachDayHocService.saveKeHoach(keHoach);
            redirectAttributes.addFlashAttribute("successMessage", "Thêm kế hoạch thành công");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/ke-hoach-day-hoc";
    }

    // Hiển thị form chỉnh sửa kế hoạch
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            KeHoachDayHoc keHoach = keHoachDayHocService.getKeHoachById(id);
            model.addAttribute("keHoach", keHoach);
            return "kehoach_form";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/ke-hoach-day-hoc";
        }
    }

    @GetMapping("/detail/{id}")
    public String showDetail(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            KeHoachDayHoc keHoach = keHoachDayHocService.getKeHoachById(id);
            model.addAttribute("keHoach", keHoach);
            model.addAttribute("hocPhanList", keHoach.getHocPhanTrongKeHoachList());
            return "kehoach_detail";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/ke-hoach-day-hoc";
        }
    }

    // Xử lý cập nhật kế hoạch
    @PostMapping("/edit/{id}")
    public String update(@PathVariable Long id, @ModelAttribute KeHoachDayHoc keHoach, RedirectAttributes redirectAttributes) {
        try {
            keHoach.setId(id);
            keHoachDayHocService.saveKeHoach(keHoach);
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật kế hoạch thành công");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/ke-hoach-day-hoc";
    }

    // Xử lý xóa kế hoạch
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            keHoachDayHocService.deleteKeHoach(id);
            redirectAttributes.addFlashAttribute("successMessage", "Xóa kế hoạch thành công");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/ke-hoach-day-hoc";
    }

    // Xuất phiếu kế hoạch dạy học
    @GetMapping("/xuat-phieu/{id}")
    public String xuatPhieuKeHoach(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            KeHoachDayHoc keHoach = keHoachDayHocService.getKeHoachDetailForExport(id);
            model.addAttribute("keHoach", keHoach);
            model.addAttribute("hocPhanList", keHoach.getHocPhanTrongKeHoachList());

            // Thống kê tổng thể
            model.addAttribute("tongHocPhan", keHoach.getHocPhanTrongKeHoachList().size());
            model.addAttribute("tongGiangVien", keHoachDayHocService.countTotalGiangVienInKeHoach(keHoach));
            model.addAttribute("tongTietGiangDay", keHoachDayHocService.calculateTotalTeachingHours(keHoach));

            // Thống kê theo khoa
            model.addAttribute("getHocPhanByKhoa", (java.util.function.Function<String, List>)
                    khoa -> keHoachDayHocService.getHocPhanListByKhoa(keHoach, khoa));
            model.addAttribute("getTietGiangDayByKhoa", (java.util.function.Function<String, Integer>)
                    khoa -> keHoachDayHocService.calculateTotalTeachingHoursByKhoa(keHoach, khoa));
            model.addAttribute("getGiangVienCountByKhoa", (java.util.function.Function<String, Integer>)
                    khoa -> keHoachDayHocService.countGiangVienByKhoa(keHoach, khoa));

            return "kehoach_export";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/ke-hoach-day-hoc";
        }
    }
}