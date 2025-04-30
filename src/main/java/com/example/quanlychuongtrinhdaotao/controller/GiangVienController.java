package com.example.quanlychuongtrinhdaotao.controller;

import com.example.quanlychuongtrinhdaotao.entity.GiangVien;
import com.example.quanlychuongtrinhdaotao.entity.User;
import com.example.quanlychuongtrinhdaotao.repository.GiangVienRepository;
import com.example.quanlychuongtrinhdaotao.repository.UserRepository;
import com.example.quanlychuongtrinhdaotao.service.GiangVienService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class GiangVienController {

    private final GiangVienService giangVienService;
    private final UserRepository userRepository;
    private final GiangVienRepository giangVienRepository;

    public GiangVienController(GiangVienService giangVienService, UserRepository userRepository, GiangVienRepository giangVienRepository) {
        this.giangVienService = giangVienService;
        this.userRepository = userRepository;
        this.giangVienRepository = giangVienRepository;
    }

    @GetMapping("/api/giangvien")
    @PreAuthorize("hasRole('ROLE_admin') or hasRole('ROLE_giangvien')")
    public ResponseEntity<List<GiangVien>> getAllGiangVien() {
        return ResponseEntity.ok(giangVienService.getAllGiangVien());
    }

    @PostMapping("/giangvien/add")
    @PreAuthorize("hasRole('ROLE_admin')")
    public String addGiangVien(@RequestParam Integer userId,
                               @RequestParam String maGV,
                               @RequestParam String hoTen,
                               @RequestParam String boMon,
                               @RequestParam String khoa,
                               @RequestParam String chuyenMon,
                               @RequestParam String trinhDo,
                               RedirectAttributes redirectAttributes) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "User không tồn tại");
            return "redirect:/giangvien/add";
        }
        GiangVien gv = new GiangVien();
        gv.setUser(user);
        gv.setMaGV(maGV);
        gv.setHoTen(hoTen);
        gv.setBoMon(boMon);
        gv.setKhoa(khoa);
        gv.setTrinhDo(trinhDo);
        gv.setChuyenMon(chuyenMon);
        gv.setTrangThai("Đang làm việc");

        giangVienRepository.save(gv);

        return "redirect:/giangvien";
    }

    @GetMapping("/giangvien/add")
    @PreAuthorize("hasRole('ROLE_admin')")
    public String showAddGiangVienForm(Model model) {
        List<User> availableUsers = userRepository.findUsersNotAssignedToGiangVien();
        model.addAttribute("availableUsers", availableUsers);
        return "giangvienAdd";
    }

    @GetMapping("/giangvien/edit/{id}")
    @PreAuthorize("hasRole('ROLE_admin')")
    public String showEditForm(@PathVariable Long id, Model model) {
        GiangVien giangVien = giangVienService.getGiangVienById(id);
        if (giangVien == null) {
            return "redirect:/giangvien";
        }
        List<User> availableUsers = userRepository.findUsersNotAssignedToGiangVien();
        if (giangVien.getUser() != null) {
            availableUsers.add(giangVien.getUser());
        }
        model.addAttribute("giangVien", giangVien);
        model.addAttribute("availableUsers", availableUsers);
        return "giangvienEdit";
    }

    @PostMapping("/giangvien/update")
    @PreAuthorize("hasRole('ROLE_admin')")
    public String updateGiangVien(@RequestParam Long id,
                                  @RequestParam Integer userId,
                                  @RequestParam String maGV,
                                  @RequestParam String hoTen,
                                  @RequestParam String boMon,
                                  @RequestParam String khoa,
                                  @RequestParam String chuyenMon,
                                  @RequestParam(required = false) String trinhDo,
                                  RedirectAttributes redirectAttributes) {
        GiangVien giangVien = giangVienService.getGiangVienById(id);
        if (giangVien == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Giảng viên không tồn tại");
            return "redirect:/giangvien";
        }
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "User không tồn tại");
            return "redirect:/giangvien/edit/" + id;
        }
        giangVien.setUser(user);
        giangVien.setMaGV(maGV);
        giangVien.setHoTen(hoTen);
        giangVien.setBoMon(boMon);
        giangVien.setKhoa(khoa);
        giangVien.setChuyenMon(chuyenMon);
        giangVien.setTrinhDo(trinhDo);
        giangVien.setTrangThai("Đang làm việc");

        giangVienService.updateGiangVien(giangVien);
        redirectAttributes.addFlashAttribute("successMessage", "Cập nhật giảng viên thành công");
        return "redirect:/giangvien";
    }

    @GetMapping("/giangvien/delete/{id}")
    @PreAuthorize("hasRole('ROLE_admin')")
    public String deleteGiangVien(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        GiangVien giangVien = giangVienService.getGiangVienById(id);
        if (giangVien == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Giảng viên không tồn tại");
            return "redirect:/giangvien";
        }
        giangVien.setTrangThai("Không làm việc");
        giangVienRepository.save(giangVien);
        redirectAttributes.addFlashAttribute("successMessage", "Đã cập nhật trạng thái giảng viên thành 'Không làm việc'");
        return "redirect:/giangvien";
    }

    @GetMapping("/giangvien")
    @PreAuthorize("hasRole('ROLE_admin') or hasRole('ROLE_giangvien')")
    public String showGiangVienList(
            @RequestParam(required = false) String search,
            Model model
    ) {
        try {
            List<GiangVien> giangVienList;
            if (search != null && !search.isEmpty()) {
                giangVienList = giangVienService.searchGiangVien(search);
            } else {
                giangVienList = giangVienService.getAllGiangVien();
            }
            model.addAttribute("giangVienList", giangVienList);
            model.addAttribute("searchKeyword", search);
        } catch (Exception e) {
            model.addAttribute("giangVienList", List.of());
            model.addAttribute("errorMessage", "Không thể lấy danh sách giảng viên: " + e.getMessage());
        }
        return "giangvien";
    }

    @GetMapping("/giangvien/xuat-phieu")
    @PreAuthorize("hasRole('ROLE_admin') or hasRole('ROLE_giangvien')")
    public String xuatPhieuDanhSachGiangVien(Model model) {
        try {
            List<GiangVien> giangVienList = giangVienService.getAllGiangVien();
            model.addAttribute("giangVienList", giangVienList);
            // Thống kê tổng thể
            model.addAttribute("tongGiangVien", giangVienList.size());
            model.addAttribute("tongGiangVienDangLamViec",
                    giangVienList.stream().filter(gv -> "Đang làm việc".equals(gv.getTrangThai())).count());
            model.addAttribute("tongGiangVienNghiViec",
                    giangVienList.stream().filter(gv -> "Không làm việc".equals(gv.getTrangThai())).count());
            // Thống kê theo khoa
            model.addAttribute("getGiangVienByKhoa", (java.util.function.Function<String, List<GiangVien>>)
                    khoa -> giangVienList.stream()
                            .filter(gv -> khoa.equals(gv.getKhoa()))
                            .collect(Collectors.toList()));
            Map<String, Long> giangVienByKhoa = giangVienList.stream()
                    .collect(Collectors.groupingBy(GiangVien::getKhoa, Collectors.counting()));
            model.addAttribute("giangVienByKhoa", giangVienByKhoa);
            return "giangvien_export";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Không thể xuất phiếu danh sách giảng viên: " + e.getMessage());
            return "redirect:/giangvien";
        }
    }


}