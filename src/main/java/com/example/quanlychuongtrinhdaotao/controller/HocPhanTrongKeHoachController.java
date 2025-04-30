package com.example.quanlychuongtrinhdaotao.controller;

import com.example.quanlychuongtrinhdaotao.entity.HocPhan;
import com.example.quanlychuongtrinhdaotao.entity.HocPhanTrongKeHoach;
import com.example.quanlychuongtrinhdaotao.entity.KeHoachDayHoc;
import com.example.quanlychuongtrinhdaotao.entity.PhanCongGiangDay;
import com.example.quanlychuongtrinhdaotao.repository.HocPhanChuongTrinhRepository;
import com.example.quanlychuongtrinhdaotao.repository.HocPhanRepository;
import com.example.quanlychuongtrinhdaotao.repository.PhanCongGiangDayRepository;
import com.example.quanlychuongtrinhdaotao.service.HocPhanTrongKeHoachService;
import com.example.quanlychuongtrinhdaotao.service.KeHoachDayHocService;
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
@RequestMapping("/ke-hoach-day-hoc/hoc-phan")
public class HocPhanTrongKeHoachController {

    private final HocPhanTrongKeHoachService hocPhanService;
    private final KeHoachDayHocService keHoachService;
    private final HocPhanRepository hocPhanRepository;
    private final HocPhanChuongTrinhRepository hocPhanChuongTrinhRepository;
    //
    private final PhanCongGiangDayService pcgdService;

    public HocPhanTrongKeHoachController(HocPhanTrongKeHoachService hocPhanService,
                                         KeHoachDayHocService keHoachService,
                                         HocPhanRepository hocPhanRepository,
                                         HocPhanChuongTrinhRepository hocPhanChuongTrinhRepository,
                                         PhanCongGiangDayService pcgdService
                                         ) {
        this.hocPhanService = hocPhanService;
        this.keHoachService = keHoachService;
        this.hocPhanRepository = hocPhanRepository;
        this.hocPhanChuongTrinhRepository = hocPhanChuongTrinhRepository;
        this.pcgdService = pcgdService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_admin') or hasRole('ROLE_giangvien')")
    public String showHocPhanList(Model model) {
        try {
            List<HocPhanTrongKeHoach> hocPhanList = hocPhanService.getAll();
            model.addAttribute("hocPhanList", hocPhanList);
        } catch (Exception e) {
            model.addAttribute("hocPhanList", List.of());
            model.addAttribute("errorMessage", "Không thể lấy danh sách học phần: " + e.getMessage());
        }
        return "hocphan_list";
    }

    @GetMapping("/add/{keHoachId}")
    @PreAuthorize("hasRole('ROLE_admin')")
    public String addForm(@PathVariable Long keHoachId, Model model) {
        try {
            HocPhanTrongKeHoach hocPhan = new HocPhanTrongKeHoach();
            hocPhan.setKeHoachDayHoc(keHoachService.getKeHoachById(keHoachId));
            model.addAttribute("hocPhan", hocPhan);
            model.addAttribute("keHoachId", keHoachId);
            // Lấy danh sách học phần từ HocPhanRepository
            List<HocPhan> hocPhanList = hocPhanRepository.findAll();
            model.addAttribute("hocPhanList", hocPhanList);
            return "hocphan_form";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error_page";
        }
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("hocPhan") HocPhanTrongKeHoach hocPhan,
                       BindingResult result,
                       Model model,
                       RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("hocPhanList", hocPhanRepository.findAll());
            return "hocphan_form";
        }
        try {
            // Debug log
            System.out.println("KeHoachDayHoc: " + hocPhan.getKeHoachDayHoc());
            System.out.println("KeHoachDayHoc ID: " + (hocPhan.getKeHoachDayHoc() != null ? hocPhan.getKeHoachDayHoc().getId() : "null"));

            if (hocPhan.getKeHoachDayHoc() == null || hocPhan.getKeHoachDayHoc().getId() == null) {
                throw new IllegalArgumentException("Kế hoạch dạy học chưa được xác định.");
            }

            KeHoachDayHoc keHoachDayHoc = keHoachService.getKeHoachById(hocPhan.getKeHoachDayHoc().getId());
            hocPhan.setKeHoachDayHoc(keHoachDayHoc);

            HocPhan hocPhanExist = hocPhanRepository.findById(hocPhan.getHocPhan().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Học phần không tồn tại"));

            // Cập nhật thông tin học phần
            hocPhan.setMaHocPhan(hocPhanExist.getMaHp());
            hocPhan.setTenHocPhan(hocPhanExist.getTenHp());
            hocPhan.setSoTC(hocPhanExist.getSoTinChi());
            hocPhan.setSoTietLT(hocPhanExist.getSoTietLyThuyet());
            hocPhan.setSoTietTH(hocPhanExist.getSoTietThucHanh());
            hocPhan.setTongSoTiet(hocPhan.getSoTietLT() + hocPhan.getSoTietTH());

            // Lưu đối tượng
            hocPhanService.save(hocPhan);

            redirectAttributes.addFlashAttribute("successMessage", "Thêm học phần thành công");
            return "redirect:/ke-hoach-day-hoc/detail/" + hocPhan.getKeHoachDayHoc().getId();
        } catch (Exception e) {
            model.addAttribute("hocPhanList", hocPhanRepository.findAll());
            model.addAttribute("errorMessage", e.getMessage());
            return "hocphan_form";
        }
    }


    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_admin')")
    public String showEditForm(@PathVariable Long id, Model model) {
        HocPhanTrongKeHoach hocPhan = hocPhanService.getById(id);
        model.addAttribute("hocPhan", hocPhan);
        model.addAttribute("keHoachId", hocPhan.getKeHoachDayHoc().getId());
        model.addAttribute("hocPhanList", hocPhanRepository.findAll());
        return "hocphan_form";
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('ROLE_admin')")
    public String update(@Valid @ModelAttribute("hocPhan") HocPhanTrongKeHoach hocPhan,
                         BindingResult result,
                         Model model,
                         RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("hocPhanList", hocPhanRepository.findAll());
            model.addAttribute("keHoachId", hocPhan.getKeHoachDayHoc() != null ? hocPhan.getKeHoachDayHoc().getId() : 0);
            model.addAttribute("errorMessage", "Dữ liệu học phần không hợp lệ");
            return "hocphan_form";
        }

        try {
            HocPhan selectedHocPhan = hocPhanRepository.findById(hocPhan.getHocPhan().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Học phần không tồn tại"));
            hocPhan.setMaHocPhan(selectedHocPhan.getMaHp());
            hocPhan.setTenHocPhan(selectedHocPhan.getTenHp());
            hocPhan.setSoTC(selectedHocPhan.getSoTinChi());
            hocPhan.setSoTietLT(selectedHocPhan.getSoTietLyThuyet());
            hocPhan.setSoTietBT(0);
            hocPhan.setSoTietTH(selectedHocPhan.getSoTietThucHanh());
            hocPhan.setTongSoTiet(hocPhan.getSoTietLT() + hocPhan.getSoTietTH());

            hocPhanService.save(hocPhan);


            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật học phần thành công");
            return "redirect:/ke-hoach-day-hoc/detail/" + hocPhan.getKeHoachDayHoc().getId();

        } catch (Exception e) {
            model.addAttribute("hocPhanList", hocPhanRepository.findAll());
            model.addAttribute("errorMessage", e.getMessage());
            return "hocphan_form";
        }
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_admin')")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            HocPhanTrongKeHoach hocPhan = hocPhanService.getById(id);
            hocPhanService.delete(id);
            redirectAttributes.addFlashAttribute("successMessage", "Xóa học phần thành công");
            return "redirect:/ke-hoach-day-hoc/detail/" + hocPhan.getKeHoachDayHoc().getId();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/ke-hoach-day-hoc/hoc-phan";
        }
    }

//    @GetMapping("/detail/{keHoachId}")
//    @PreAuthorize("hasRole('ROLE_admin') or hasRole('ROLE_giangvien')")
//    public String showDetail(@PathVariable Long keHoachId, Model model) {
//        try {
//            var keHoach = keHoachService.getKeHoachById(keHoachId);
//            var hocPhanList = hocPhanService.getAllByKeHoachId(keHoachId);
//
//            if (hocPhanList.isEmpty()) {
//                model.addAttribute("errorMessage", "Không có học phần trong kế hoạch dạy học này.");
//            }
//
//            model.addAttribute("keHoach", keHoach);
//            model.addAttribute("hocPhanList", hocPhanList);
//            return "hocphan_detail";
//        } catch (Exception e) {
//            model.addAttribute("errorMessage", "Không thể lấy thông tin chi tiết kế hoạch dạy học: " + e.getMessage());
//            return "error_page";
//        }
//    }

    @GetMapping("/detail/{keHoachId}")
    @PreAuthorize("hasRole('ROLE_admin') or hasRole('ROLE_giangvien')")
    public String showDetail(@PathVariable Long keHoachId, Model model) {
        try {
            var keHoach = keHoachService.getKeHoachById(keHoachId);
            var hocPhanList = hocPhanService.getAllByKeHoachId(keHoachId);

            // Lấy thông tin phân công cho từng học phần
            for (HocPhanTrongKeHoach hp : hocPhanList) {
                List<PhanCongGiangDay> phanCongList = pcgdService.getAllByHocPhanId(hp.getId());
                hp.setPhanCongList(phanCongList);
            }

            if (hocPhanList.isEmpty()) {
                model.addAttribute("errorMessage", "Không có học phần trong kế hoạch dạy học này.");
            }

            model.addAttribute("keHoach", keHoach);
            model.addAttribute("hocPhanList", hocPhanList);
            return "hocphan_detail";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Không thể lấy thông tin chi tiết kế hoạch dạy học: " + e.getMessage());
            return "error_page";
        }
    }
}
