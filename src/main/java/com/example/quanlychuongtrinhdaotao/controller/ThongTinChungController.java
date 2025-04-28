package com.example.quanlychuongtrinhdaotao.controller;

import com.example.quanlychuongtrinhdaotao.entity.HocPhan_ChuongTrinh;
import com.example.quanlychuongtrinhdaotao.entity.KhungChuongTrinh;
import com.example.quanlychuongtrinhdaotao.entity.NhomKienThuc;
import com.example.quanlychuongtrinhdaotao.entity.ThongTinChung;
import com.example.quanlychuongtrinhdaotao.repository.HocPhanChuongTrinhRepository;
import com.example.quanlychuongtrinhdaotao.repository.KhungChuongTrinhRepository;
import com.example.quanlychuongtrinhdaotao.repository.NhomKienThucRepository;
import com.example.quanlychuongtrinhdaotao.repository.ThongTinChungRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/thong-tin-chung")
public class ThongTinChungController {

    @Autowired
    private ThongTinChungRepository repository;

    @Autowired
    private NhomKienThucRepository nhomKienThucRepository;

    @Autowired
    private HocPhanChuongTrinhRepository hocPhanChuongTrinhRepository;

    @Autowired
    private KhungChuongTrinhRepository khungChuongTrinhRepository;

    // Hiển thị danh sách
    @GetMapping
    public String getAll(Model model) {
        List<ThongTinChung> dataList = repository.findAll();
        model.addAttribute("dataList", dataList);
        return "thong_tin_chung_list";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable Integer id, Model model) {
        Optional<ThongTinChung> data = repository.findById(id);

        // Lấy danh sách khung chương trình
        List<KhungChuongTrinh> khungList = khungChuongTrinhRepository.findByThongTinChung_Id(id);
        int soTinChiKhoiGiaoDucDaiCuongBatBuoc = 0;
        int soTinChiKhoiGiaoDucDaiCuongTuChon = 0;
        int soTinChiKhoiGiaoDucChuyenNghiepBatBuoc = 0;
        int soTinChiKhoiGiaoDucChuyenNghiepTuChon = 0;
        int tongSoTinChiBatBuoc = 0;
        int tongSoTinChiTuChon = 0;
        int tongSoTinChiTichLuyToiThieu = 0;

        for(KhungChuongTrinh kh : khungList) {
            if(kh.getNhomKienThuc().getMaNhom().equals("NN") ||
                    kh.getNhomKienThuc().getMaNhom().equals("LLCT") || kh.getNhomKienThuc().getMaNhom().equals("GDDCK")) {
                soTinChiKhoiGiaoDucDaiCuongBatBuoc += kh.getSoTinChiBatBuocToiThieu();
                soTinChiKhoiGiaoDucDaiCuongTuChon += kh.getSoTinChiTuChonToiThieu();
                tongSoTinChiBatBuoc += kh.getSoTinChiBatBuocToiThieu();
                tongSoTinChiTuChon += kh.getSoTinChiTuChonToiThieu();
            } else if(!kh.getNhomKienThuc().getMaNhom().equals("QPAN")) {
                soTinChiKhoiGiaoDucChuyenNghiepBatBuoc += kh.getSoTinChiBatBuocToiThieu();
                soTinChiKhoiGiaoDucChuyenNghiepTuChon += kh.getSoTinChiTuChonToiThieu();
                tongSoTinChiBatBuoc += kh.getSoTinChiBatBuocToiThieu();
                tongSoTinChiTuChon += kh.getSoTinChiTuChonToiThieu();
            }
        }

        tongSoTinChiTichLuyToiThieu = tongSoTinChiBatBuoc + tongSoTinChiTuChon;

        // Lấy danh sách học phần trong chương trình
        List<HocPhan_ChuongTrinh> hocPhanList = hocPhanChuongTrinhRepository.findHocPhan_ChuongTrinhsByThongTinChung_Id(id);

        if (data.isPresent()) {
            model.addAttribute("ThongTinChung", data.get());
            model.addAttribute("KhungList", khungList);
            model.addAttribute("soTinChiKhoiGiaoDucDaiCuongBatBuoc", soTinChiKhoiGiaoDucDaiCuongBatBuoc);
            model.addAttribute("soTinChiKhoiGiaoDucDaiCuongTuChon", soTinChiKhoiGiaoDucDaiCuongTuChon);
            model.addAttribute("soTinChiKhoiGiaoDucChuyenNghiepBatBuoc", soTinChiKhoiGiaoDucChuyenNghiepBatBuoc);
            model.addAttribute("soTinChiKhoiGiaoDucChuyenNghiepTuChon", soTinChiKhoiGiaoDucChuyenNghiepTuChon);
            model.addAttribute("tongSoTinChiBatBuoc", tongSoTinChiBatBuoc);
            model.addAttribute("tongSoTinChiTuChon", tongSoTinChiTuChon);
            model.addAttribute("tongSoTinChiTichLuyToiThieu", tongSoTinChiTichLuyToiThieu);
            model.addAttribute("HocPhanList", hocPhanList);
        }

        return "thong_tin_chung_detail";
    }

    // Thêm mới
    @GetMapping("/add")
    public String showAddForm(Model model) {
        ThongTinChung thongTinChung = new ThongTinChung();
        ArrayList<KhungChuongTrinh> khungChuongTrinhList = new ArrayList<>();
        List<NhomKienThuc> nhomKienThucList = nhomKienThucRepository.findAll();
        for (int i = 1; i <= 7; i++) {
            KhungChuongTrinh k = new KhungChuongTrinh();
            k.setNhomKienThuc(nhomKienThucList.get(i-1));
            khungChuongTrinhList.add(k);
        }
        thongTinChung.setKhungChuongTrinhList(khungChuongTrinhList);
        model.addAttribute("ThongTinChung", thongTinChung);
        return "thong_tin_chung_add";
    }

    @PostMapping("/add")
    public String addThongTinChung(@ModelAttribute("ThongTinChung") ThongTinChung thongTinChung) {
        thongTinChung.setTrangThai(1);

        // Gán lại mối liên hệ cho từng khung chương trình
        if (thongTinChung.getKhungChuongTrinhList() != null) {
            for (KhungChuongTrinh k : thongTinChung.getKhungChuongTrinhList()) {
                k.setThongTinChung(thongTinChung);
            }
        }

        repository.save(thongTinChung); // cascade sẽ tự lưu KhungChuongTrinh nếu cấu hình đúng
        return "redirect:/thong-tin-chung";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        Optional<ThongTinChung> data = repository.findById(id);
        if (data.isPresent()) {
            ThongTinChung thongTinChung = data.get();

            // Đảm bảo rằng khungChuongTrinhList không null để tránh NullPointerException
            if (thongTinChung.getKhungChuongTrinhList() == null) {
                thongTinChung.setKhungChuongTrinhList(new ArrayList<>());
            }

            model.addAttribute("ThongTinChung", thongTinChung);
        }
        return "thong_tin_chung_edit";
    }

    // POST: Cập nhật thông tin
    @PostMapping("/edit/{id}")
    public String editThongTinChung(@PathVariable Integer id, @ModelAttribute("ThongTinChung") ThongTinChung thongTinChung) {
        Optional<ThongTinChung> existingData = repository.findById(id);
        if (existingData.isPresent()) {
            ThongTinChung existing = existingData.get();

            // Cập nhật thông tin chung
            existing.setMaCtdt(thongTinChung.getMaCtdt());
            existing.setTenCtdt(thongTinChung.getTenCtdt());
            existing.setNganh(thongTinChung.getNganh());
            existing.setMaNganh(thongTinChung.getMaNganh());
            existing.setKhoaQuanLy(thongTinChung.getKhoaQuanLy());
            existing.setHeDaoTao(thongTinChung.getHeDaoTao());
            existing.setTrinhDo(thongTinChung.getTrinhDo());
            existing.setTongTinChi(thongTinChung.getTongTinChi());
            existing.setThoiGianDaoTao(thongTinChung.getThoiGianDaoTao());
            existing.setNamBanHanh(thongTinChung.getNamBanHanh());
            existing.setTrangThai(thongTinChung.getTrangThai());
            existing.setGioiThieu(thongTinChung.getGioiThieu());

            // Cập nhật tín chỉ cho từng nhóm trong khung chương trình đào tạo
            if (thongTinChung.getKhungChuongTrinhList() != null) {
                for (int i = 0; i < thongTinChung.getKhungChuongTrinhList().size(); i++) {
                    KhungChuongTrinh khung = thongTinChung.getKhungChuongTrinhList().get(i);
                    KhungChuongTrinh existingKhung = existing.getKhungChuongTrinhList().get(i);

                    // Cập nhật tín chỉ bắt buộc và tự chọn
                    existingKhung.setSoTinChiBatBuocToiThieu(khung.getSoTinChiBatBuocToiThieu());
                    existingKhung.setSoTinChiTuChonToiThieu(khung.getSoTinChiTuChonToiThieu());
                }
            }

            // Lưu lại thay đổi
            repository.save(existing);
        }
        return "redirect:/thong-tin-chung";
    }

    // Xóa
    @GetMapping("/delete/{id}")
    public String deleteThongTinChung(@PathVariable Integer id) {
        repository.deleteById(id);
        return "redirect:/thong-tin-chung";
    }
}
