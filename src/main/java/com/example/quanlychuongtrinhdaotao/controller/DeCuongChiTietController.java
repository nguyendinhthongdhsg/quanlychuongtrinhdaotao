package com.example.quanlychuongtrinhdaotao.controller;

import com.example.quanlychuongtrinhdaotao.entity.DeCuongChiTiet;
import com.example.quanlychuongtrinhdaotao.entity.CotDiem;
import com.example.quanlychuongtrinhdaotao.repository.DeCuongChiTietRepository;
import com.example.quanlychuongtrinhdaotao.repository.CotDiemRepository;
import com.example.quanlychuongtrinhdaotao.repository.HocPhanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/de-cuong-chi-tiet")
public class DeCuongChiTietController {

    @Autowired
    private DeCuongChiTietRepository deCuongChiTietRepository;

    @Autowired
    private CotDiemRepository cotDiemRepository;

    @Autowired
    private HocPhanRepository hocPhanRepository;

    // Hiển thị danh sách Đề cương chi tiết
    @GetMapping
    public String getAll(Model model) {
        List<DeCuongChiTiet> deCuongChiTietList = deCuongChiTietRepository.findAll();
        model.addAttribute("deCuongChiTietList", deCuongChiTietList);
        return "de_cuong_chi_tiet_list";
    }

    // Hiển thị chi tiết Đề cương theo ID
    @GetMapping("/{id}")
    public String getById(@PathVariable Integer id, Model model) {
        // Tìm Đề cương chi tiết theo ID
        DeCuongChiTiet deCuongChiTiet = deCuongChiTietRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Đề cương với ID: " + id));

        // Lấy danh sách các cột điểm liên quan đến Đề cương này
        List<CotDiem> cotDiemList = cotDiemRepository.findByDeCuongChiTietId(id);

        deCuongChiTiet.setCotDiems(cotDiemList);

        // Thêm đối tượng DeCuongChiTiet và danh sách Cột điểm vào Model
        model.addAttribute("deCuongChiTiet", deCuongChiTiet);

        return "de_cuong_chi_tiet_detail";  // Trang chi tiết Đề cương
    }

    // Hiển thị form thêm Đề cương chi tiết
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("deCuongChiTiet", new DeCuongChiTiet());
        model.addAttribute("hocPhanList", hocPhanRepository.findAll()); // Lấy danh sách học phần
        return "de_cuong_chi_tiet_add";
    }

    // Xử lý thêm Đề cương chi tiết
    @PostMapping("/add")
    public String addDeCuongChiTiet(@ModelAttribute("deCuongChiTiet") DeCuongChiTiet deCuongChiTiet) {
        deCuongChiTietRepository.save(deCuongChiTiet);
        return "redirect:/de-cuong-chi-tiet";
    }

    // Hiển thị form sửa Đề cương chi tiết
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        Optional<DeCuongChiTiet> deCuongChiTiet = deCuongChiTietRepository.findById(id);
        if (deCuongChiTiet.isPresent()) {
            model.addAttribute("deCuongChiTiet", deCuongChiTiet.get());
            model.addAttribute("hocPhanList", hocPhanRepository.findAll()); // Lấy danh sách học phần
        }
        return "de_cuong_chi_tiet_edit";
    }

    // Xử lý cập nhật Đề cương chi tiết
    @PostMapping("/edit/{id}")
    public String editDeCuongChiTiet(@PathVariable Integer id, @ModelAttribute("deCuongChiTiet") DeCuongChiTiet deCuongChiTiet) {
        Optional<DeCuongChiTiet> existingData = deCuongChiTietRepository.findById(id);
        if (existingData.isPresent()) {
            DeCuongChiTiet existing = existingData.get();
            existing.setHocPhan(deCuongChiTiet.getHocPhan());
            existing.setMucTieu(deCuongChiTiet.getMucTieu());
            existing.setNoiDung(deCuongChiTiet.getNoiDung());
            existing.setPhuongPhapGiangDay(deCuongChiTiet.getPhuongPhapGiangDay());
            existing.setPhuongPhapDanhGia(deCuongChiTiet.getPhuongPhapDanhGia());
            existing.setTaiLieuThamKhao(deCuongChiTiet.getTaiLieuThamKhao());
            existing.setTrangThai(deCuongChiTiet.getTrangThai());
            deCuongChiTietRepository.save(existing);
        }
        return "redirect:/de-cuong-chi-tiet";
    }

    // Xóa Đề cương chi tiết
    @GetMapping("/delete/{id}")
    public String deleteDeCuongChiTiet(@PathVariable Integer id) {
        deCuongChiTietRepository.deleteById(id);
        return "redirect:/de-cuong-chi-tiet";
    }

    // Thêm Cột điểm cho Đề cương chi tiết
    @GetMapping("/add-cot-diem/{deCuongId}")
    public String showAddCotDiemForm(@PathVariable Integer deCuongId, Model model) {
        Optional<DeCuongChiTiet> deCuongChiTiet = deCuongChiTietRepository.findById(deCuongId);
        if (deCuongChiTiet.isPresent()) {
            model.addAttribute("deCuongChiTiet", deCuongChiTiet.get());
            model.addAttribute("cotDiem", new CotDiem());
        }
        return "cot_diem_add";
    }

    // Xử lý thêm Cột điểm
    @PostMapping("/add-cot-diem/{deCuongId}")
    public String addCotDiem(@PathVariable Integer deCuongId, @ModelAttribute CotDiem cotDiem) {
        Optional<DeCuongChiTiet> deCuongChiTiet = deCuongChiTietRepository.findById(deCuongId);
        if (deCuongChiTiet.isPresent()) {
            cotDiem.setDeCuongChiTiet(deCuongChiTiet.get());
            cotDiemRepository.save(cotDiem);
        }
        return "redirect:/de-cuong-chi-tiet/" + deCuongId;
    }

    // Hiển thị form sửa Cột điểm
    @GetMapping("/edit-cot-diem/{cotDiemId}")
    public String showEditCotDiemForm(@PathVariable Integer cotDiemId, Model model) {
        Optional<CotDiem> cotDiemOpt = cotDiemRepository.findById(cotDiemId);
        if (cotDiemOpt.isPresent()) {
            CotDiem cotDiem = cotDiemOpt.get();
            model.addAttribute("cotDiem", cotDiem);
            model.addAttribute("deCuongChiTiet", cotDiem.getDeCuongChiTiet());
        }
        return "cot_diem_edit";
    }

    // Xử lý cập nhật Cột điểm
    @PostMapping("/edit-cot-diem/{cotDiemId}")
    public String updateCotDiem(@PathVariable Integer cotDiemId, @ModelAttribute CotDiem updatedCotDiem) {
        Optional<CotDiem> existingCotDiemOpt = cotDiemRepository.findById(cotDiemId);
        if (existingCotDiemOpt.isPresent()) {
            CotDiem existingCotDiem = existingCotDiemOpt.get();
            existingCotDiem.setTenCotDiem(updatedCotDiem.getTenCotDiem());
            existingCotDiem.setTyLePhanTram(updatedCotDiem.getTyLePhanTram());
            existingCotDiem.setHinhThuc(updatedCotDiem.getHinhThuc());
            cotDiemRepository.save(existingCotDiem);
            return "redirect:/de-cuong-chi-tiet/" + existingCotDiem.getDeCuongChiTiet().getId();
        }
        return "redirect:/de-cuong-chi-tiet";
    }

    // Xóa Cột điểm
    @GetMapping("/delete-cot-diem/{id}")
    public String deleteCotDiem(@PathVariable Integer id) {
        cotDiemRepository.deleteById(id);
        return "redirect:/de-cuong-chi-tiet/";
    }
}
