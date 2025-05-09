
package com.example.quanlychuongtrinhdaotao.controller;

import com.example.quanlychuongtrinhdaotao.entity.HocPhanTrongKeHoach;
import com.example.quanlychuongtrinhdaotao.entity.KeHoachDayHoc;
import com.example.quanlychuongtrinhdaotao.entity.ThongTinChung;
import com.example.quanlychuongtrinhdaotao.service.HocPhanTrongKeHoachService;
import com.example.quanlychuongtrinhdaotao.service.KeHoachDayHocService;
import com.example.quanlychuongtrinhdaotao.service.ThongTinChungService;
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
    private final HocPhanTrongKeHoachService hocPhanTrongKeHoachService;
    private final ThongTinChungService thongTinChungService;

    public KeHoachDayHocController(KeHoachDayHocService keHoachDayHocService, HocPhanTrongKeHoachService hocPhanTrongKeHoachService, ThongTinChungService thongTinChungService) {
        this.keHoachDayHocService = keHoachDayHocService;
        this.hocPhanTrongKeHoachService = hocPhanTrongKeHoachService;
        this.thongTinChungService = thongTinChungService;
    }

    // Hiển thị danh sách và search kế hoạch dạy học
    @GetMapping
    public String getAll(
            @RequestParam(required = false) String search,
            Model model
    ) {
        try {
            List<KeHoachDayHoc> keHoachList;
            if (search != null && !search.isEmpty()) {
                keHoachList = keHoachDayHocService.searchKeHoach(search);
            } else {
                keHoachList = keHoachDayHocService.getAllKeHoach();
            }
            model.addAttribute("keHoachList", keHoachList);
            model.addAttribute("searchKeyword", search);
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
        model.addAttribute("dsChuongTrinhDaoTao", thongTinChungService.getAllThongTinChung());
        return "kehoach_form";
    }

    // Xử lý thêm kế hoạch
    @PostMapping("/add")
    public String add(@ModelAttribute KeHoachDayHoc keHoach, @RequestParam("thongTinChung.id") Integer thongTinChungId, RedirectAttributes redirectAttributes) {
        try {
            ThongTinChung thongTinChung = thongTinChungService.getThongTinChungById(thongTinChungId)
                    .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy chương trình đào tạo với ID: " + thongTinChungId));

            keHoach.setThongTinChung(thongTinChung);
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
            model.addAttribute("dsChuongTrinhDaoTao", thongTinChungService.getAllThongTinChung());
            return "kehoach_form";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/ke-hoach-day-hoc";
        }
    }

    @GetMapping("/detail/{id}")
    public String showDetail(
            @PathVariable Long id,
            @RequestParam(required = false) String search,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        try {
            KeHoachDayHoc keHoach = keHoachDayHocService.getKeHoachById(id);
            List<HocPhanTrongKeHoach> hocPhanList;
            if (search != null && !search.trim().isEmpty()) {
                // Sử dụng phương thức search đã tạo
                hocPhanList = hocPhanTrongKeHoachService.searchInKeHoach(search, id);
            } else {
                hocPhanList = keHoach.getHocPhanTrongKeHoachList();
            }
            model.addAttribute("keHoach", keHoach);
            model.addAttribute("hocPhanList", hocPhanList);
            model.addAttribute("searchKeyword", search);
            return "kehoach_detail";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/ke-hoach-day-hoc";
        }
    }

    // Xử lý cập nhật kế hoạch
    @PostMapping("/edit/{id}")
    public String update(@PathVariable Long id, @ModelAttribute KeHoachDayHoc keHoach,
                         @RequestParam("thongTinChung.id") Integer thongTinChungId,
                         RedirectAttributes redirectAttributes) {
        try {
            ThongTinChung thongTinChung = thongTinChungService.getThongTinChungById(thongTinChungId)
                    .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy chương trình đào tạo với ID: " + thongTinChungId));

            keHoach.setId(id);
            keHoach.setThongTinChung(thongTinChung);
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

            // Ensure heSo is loaded for each HocPhanTrongKeHoach
            for (HocPhanTrongKeHoach hocPhan : keHoach.getHocPhanTrongKeHoachList()) {
                // Log to verify heSo values are available
                System.out.println("Học phần: " + hocPhan.getTenHocPhan() + " - Hệ số: " + hocPhan.getHeSo());
            }

            model.addAttribute("keHoach", keHoach);
            model.addAttribute("hocPhanList", keHoach.getHocPhanTrongKeHoachList());

            // Thống kê tổng thể
            model.addAttribute("tongHocPhan", keHoach.getHocPhanTrongKeHoachList().size());
            model.addAttribute("tongGiangVien", keHoachDayHocService.countTotalGiangVienInKeHoach(keHoach));
            model.addAttribute("tongTietGiangDay", keHoachDayHocService.calculateTotalTeachingHours(keHoach));

            // Calculate weighted total (with coefficient)
            int tongTietCoHeSo = keHoach.getHocPhanTrongKeHoachList().stream()
                    .mapToInt(hp -> {
                        int tietTotal = hp.getTongSoTiet();
                        float heSo = hp.getHeSo() != null ? hp.getHeSo() : 1.0f; // Default to 1.0 if null
                        return Math.round(tietTotal * heSo);
                    })
                    .sum();
            model.addAttribute("tongTietCoHeSo", tongTietCoHeSo);

            // Thống kê theo khoa
            model.addAttribute("getHocPhanByKhoa", (java.util.function.Function<String, List>)
                    khoa -> keHoachDayHocService.getHocPhanListByKhoa(keHoach, khoa));
            model.addAttribute("getTietGiangDayByKhoa", (java.util.function.Function<String, Integer>)
                    khoa -> keHoachDayHocService.calculateTotalTeachingHoursByKhoa(keHoach, khoa));
            model.addAttribute("getGiangVienCountByKhoa", (java.util.function.Function<String, Integer>)
                    khoa -> keHoachDayHocService.countGiangVienByKhoa(keHoach, khoa));

            // Helper function to calculate weighted hours by khoa
            model.addAttribute("getTietGiangDayCoHeSoByKhoa", (java.util.function.Function<String, Integer>)
                    khoa -> {
                        List<HocPhanTrongKeHoach> hocPhanList = keHoachDayHocService.getHocPhanListByKhoa(keHoach, khoa);
                        return hocPhanList.stream()
                                .mapToInt(hp -> {
                                    int tietTotal = hp.getTongSoTiet();
                                    float heSo = hp.getHeSo() != null ? hp.getHeSo() : 1.0f; // Default to 1.0 if null
                                    return Math.round(tietTotal * heSo);
                                })
                                .sum();
                    });

            return "kehoach_export";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/ke-hoach-day-hoc";
        }
    }
}