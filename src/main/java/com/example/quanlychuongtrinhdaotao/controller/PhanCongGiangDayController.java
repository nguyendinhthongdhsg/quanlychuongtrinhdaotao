package com.example.quanlychuongtrinhdaotao.controller;

import com.example.quanlychuongtrinhdaotao.entity.GiangVien;
import com.example.quanlychuongtrinhdaotao.entity.PhanCongGiangDay;
import com.example.quanlychuongtrinhdaotao.service.GiangVienService;
import com.example.quanlychuongtrinhdaotao.service.HocPhanTrongKeHoachService;
import com.example.quanlychuongtrinhdaotao.service.PhanCongGiangDayService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/ke-hoach-day-hoc/phan-cong")
public class PhanCongGiangDayController {

    private final PhanCongGiangDayService pcgdService;
    private final HocPhanTrongKeHoachService hocPhanService;
    private final GiangVienService giangVienService;

    // Constructor injection
    public PhanCongGiangDayController(PhanCongGiangDayService pcgdService,
                                      HocPhanTrongKeHoachService hocPhanService,
                                      GiangVienService giangVienService) {
        this.pcgdService = pcgdService;
        this.hocPhanService = hocPhanService;
        this.giangVienService = giangVienService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_admin') or hasRole('ROLE_giangvien')")
    public String showPhanCongList(Model model) {
        try {
            List<PhanCongGiangDay> phanCongList = pcgdService.getAll();
            model.addAttribute("phanCongList", phanCongList);
        } catch (Exception e) {
            model.addAttribute("phanCongList", List.of());
            model.addAttribute("errorMessage", "Không thể lấy danh sách phân công: " + e.getMessage());
        }
        return "phancong_list";
    }

    @GetMapping("/add/{hocPhanId}")
    @PreAuthorize("hasRole('ROLE_admin')")
    public String form(@PathVariable Long hocPhanId, Model model) {
        try {
            PhanCongGiangDay pcgd = new PhanCongGiangDay();
            var hocPhan = hocPhanService.getById(hocPhanId);
            pcgd.setHocPhanTrongKeHoach(hocPhan);

            // Chỉ lấy những giảng viên đang làm việc
            List<GiangVien> giangVienList = giangVienService.getAllGiangVien().stream()
                    .filter(gv -> "Đang làm việc".equals(gv.getTrangThai()))
                    .toList();

            model.addAttribute("phanCong", pcgd);
            model.addAttribute("giangVienList", giangVienList);
            model.addAttribute("hocPhanId", hocPhanId);
            return "phancong_form";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error_page";
        }
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('ROLE_admin')")
    public String save(@Valid @ModelAttribute PhanCongGiangDay pcgd, BindingResult result,
                       RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Dữ liệu phân công không hợp lệ");
            return "redirect:/ke-hoach-day-hoc/phan-cong/add/" + (pcgd.getHocPhanTrongKeHoach() != null ? pcgd.getHocPhanTrongKeHoach().getId() : "0");
        }
        try {
            pcgdService.save(pcgd);
            redirectAttributes.addFlashAttribute("successMessage", "Thêm phân công thành công");
            return "redirect:/ke-hoach-day-hoc/detail/" + pcgd.getHocPhanTrongKeHoach().getKeHoachDayHoc().getId();
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/ke-hoach-day-hoc/phan-cong/add/" + (pcgd.getHocPhanTrongKeHoach() != null ? pcgd.getHocPhanTrongKeHoach().getId() : "0");
        }
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_admin')")
    public String showEditForm(@PathVariable Long id, Model model) {
        try {
            PhanCongGiangDay pcgd = pcgdService.getById(id);

            // Chỉ lấy những giảng viên đang làm việc
            List<GiangVien> giangVienList = giangVienService.getAllGiangVien().stream()
                    .filter(gv -> "Đang làm việc".equals(gv.getTrangThai()) ||
                            (pcgd.getGiangVien() != null && pcgd.getGiangVien().getId().equals(gv.getId())))
                    .toList();

            model.addAttribute("phanCong", pcgd);
            model.addAttribute("giangVienList", giangVienList);
            model.addAttribute("hocPhanId", pcgd.getHocPhanTrongKeHoach().getId());
            return "phancong_form";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error_page";
        }
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('ROLE_admin')")
    public String update(@Valid @ModelAttribute PhanCongGiangDay pcgd, BindingResult result,
                         RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Dữ liệu phân công không hợp lệ");
            return "redirect:/ke-hoach-day-hoc/phan-cong/edit/" + pcgd.getId();
        }
        try {
            pcgdService.save(pcgd);
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật phân công thành công");
            return "redirect:/ke-hoach-day-hoc/detail/" + pcgd.getHocPhanTrongKeHoach().getKeHoachDayHoc().getId();
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/ke-hoach-day-hoc/phan-cong/edit/" + pcgd.getId();
        }
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_admin')")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            PhanCongGiangDay pcgd = pcgdService.getById(id);
            pcgdService.delete(id);
            redirectAttributes.addFlashAttribute("successMessage", "Xóa phân công thành công");
            return "redirect:/ke-hoach-day-hoc/detail/" + pcgd.getHocPhanTrongKeHoach().getKeHoachDayHoc().getId();
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/ke-hoach-day-hoc/phan-cong";
        }
    }
}