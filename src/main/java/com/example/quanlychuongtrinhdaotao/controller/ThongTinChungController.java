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

        // Lấy danh sách học phần trong chương trình
        List<HocPhan_ChuongTrinh> hocPhanList = hocPhanChuongTrinhRepository.findHocPhan_ChuongTrinhsByThongTinChung_Id(id);
        List<HocPhan_ChuongTrinh> hocPhanDaiCuongBatBuoc = new ArrayList<>();
        List<HocPhan_ChuongTrinh> hocPhanDaiCuongTuChon = new ArrayList<>();
        List<HocPhan_ChuongTrinh> hocPhanCoSoNganhBatBuoc = new ArrayList<>();
        List<HocPhan_ChuongTrinh> hocPhanCoSoNganhTuChon = new ArrayList<>();
        List<HocPhan_ChuongTrinh> hocPhanKienThucNganhBatBuoc = new ArrayList<>();
        List<HocPhan_ChuongTrinh> hocPhanKienThucNganhTuChon = new ArrayList<>();
        List<HocPhan_ChuongTrinh> hocPhanChuyenNganhBatBuoc = new ArrayList<>();
        List<HocPhan_ChuongTrinh> hocPhanChuyenNganhTuChon = new ArrayList<>();

        Integer tcDaiCuongBatBuoc = 0;
        Integer tcDaiCuongTuChon = 0;
        Integer tcCoSoNganhBatBuoc = 0;
        Integer tcCoSoNganhTuChon = 0;
        Integer tcKienThucNganhBatBuoc = 0;
        Integer tcKienThucNganhTuChon = 0;
        Integer tcChuyenNganhBatBuoc = 0;
        Integer tcChuyenNganhTuChon = 0;

        Integer tcToiThieuDaiCuongBatBuoc = 0;
        Integer tcToiThieuDaiCuongTuChon = 0;
        Integer tcToiThieuCoSoNganhBatBuoc = 0;
        Integer tcToiThieuCoSoNganhTuChon = 0;
        Integer tcToiThieuKienThucNganhBatBuoc = 0;
        Integer tcToiThieuKienThucNganhTuChon = 0;
        Integer tcToiThieuChuyenNganhBatBuoc = 0;
        Integer tcToiThieuChuyenNganhTuChon = 0;

        int count = 1;
        for(HocPhan_ChuongTrinh hp : hocPhanList) {
            hp.setStt(count);
            count++;

            if(hp.getKhungChuongTrinh().getNhomKienThuc().getTenNhom().equals("Kiến thức Giáo dục thể chất và Giáo dục quốc phòng và an ninh") ||
                    hp.getKhungChuongTrinh().getNhomKienThuc().getTenNhom().equals("Kiến thức Ngoại ngữ") ||
                    hp.getKhungChuongTrinh().getNhomKienThuc().getTenNhom().equals("Kiến thức Lý luận chính trị") ||
                    hp.getKhungChuongTrinh().getNhomKienThuc().getTenNhom().equals("Kiến thức giáo dục đại cương khác")) {

                if(hp.getBatBuoc() == 1) {
                    hocPhanDaiCuongBatBuoc.add(hp);
                    if(!hp.getKhungChuongTrinh().getNhomKienThuc().getTenNhom().equals("Kiến thức Giáo dục thể chất và Giáo dục quốc phòng và an ninh")) {
                        tcDaiCuongBatBuoc += hp.getHocPhan().getSoTinChi();
                    }
                } else {
                    hocPhanDaiCuongTuChon.add(hp);
                    tcDaiCuongTuChon += hp.getHocPhan().getSoTinChi();
                }
            } else if(hp.getKhungChuongTrinh().getNhomKienThuc().getTenNhom().equals("Kiến thức cơ sở của ngành")) {
                if(hp.getBatBuoc() == 1) {
                    hocPhanCoSoNganhBatBuoc.add(hp);
                    tcCoSoNganhBatBuoc += hp.getHocPhan().getSoTinChi();
                } else {
                    hocPhanCoSoNganhTuChon.add(hp);
                    tcCoSoNganhTuChon += hp.getHocPhan().getSoTinChi();
                }
            } else if(hp.getKhungChuongTrinh().getNhomKienThuc().getTenNhom().equals("Kiến thức ngành") ||
                    hp.getKhungChuongTrinh().getNhomKienThuc().getTenNhom().equals("Khóa luận tốt nghiệp") ||
                    hp.getKhungChuongTrinh().getNhomKienThuc().getTenNhom().equals("Các học phần thay thế khóa luận")) {
                if(hp.getBatBuoc() == 1) {
                    hocPhanKienThucNganhBatBuoc.add(hp);
                    tcKienThucNganhBatBuoc += hp.getHocPhan().getSoTinChi();
                } else {
                    hocPhanKienThucNganhTuChon.add(hp);
                    tcKienThucNganhTuChon += hp.getHocPhan().getSoTinChi();
                }
            } else {
                if(hp.getBatBuoc() == 1) {
                    hocPhanChuyenNganhBatBuoc.add(hp);
                    tcChuyenNganhBatBuoc += hp.getHocPhan().getSoTinChi();
                } else {
                    hocPhanChuyenNganhTuChon.add(hp);
                    tcChuyenNganhTuChon += hp.getHocPhan().getSoTinChi();
                }
            }
        }

        for(KhungChuongTrinh k : khungList) {
            for(HocPhan_ChuongTrinh hp : hocPhanDaiCuongBatBuoc) {
                if(hp.getKhungChuongTrinh().equals(k)) {
                    if(hp.getBatBuoc() == 1) {
                        k.setSoTinChiBatBuocToiThieu(k.getSoTinChiBatBuocToiThieu() + hp.getHocPhan().getSoTinChi());
                    } else {
                        k.setSoTinChiTuChonToiThieu(k.getSoTinChiTuChonToiThieu() + hp.getHocPhan().getSoTinChi());
                    }
                }
            }
        }

        for (KhungChuongTrinh k : khungList) {
            if(!k.getNhomKienThuc().getTenNhom().equals("Kiến thức Giáo dục thể chất và Giáo dục quốc phòng và an ninh") &&
                    (k.getNhomKienThuc().getTenNhom().equals("Kiến thức Ngoại ngữ") ||
                    k.getNhomKienThuc().getTenNhom().equals("Kiến thức Lý luận chính trị") ||
                    k.getNhomKienThuc().getTenNhom().equals("Kiến thức giáo dục đại cương khác"))) {
                soTinChiKhoiGiaoDucDaiCuongBatBuoc += k.getSoTinChiBatBuocToiThieu();
                soTinChiKhoiGiaoDucDaiCuongTuChon += k.getSoTinChiTuChonToiThieu();
            } else if(!k.getNhomKienThuc().getTenNhom().equals("Kiến thức Giáo dục thể chất và Giáo dục quốc phòng và an ninh")) {
                soTinChiKhoiGiaoDucChuyenNghiepBatBuoc += k.getSoTinChiBatBuocToiThieu();
                soTinChiKhoiGiaoDucChuyenNghiepTuChon += k.getSoTinChiTuChonToiThieu();

            }
        }
        tongSoTinChiBatBuoc = soTinChiKhoiGiaoDucDaiCuongBatBuoc + soTinChiKhoiGiaoDucChuyenNghiepBatBuoc;
        tongSoTinChiTuChon = soTinChiKhoiGiaoDucDaiCuongTuChon + soTinChiKhoiGiaoDucChuyenNghiepTuChon;

        tongSoTinChiTichLuyToiThieu = tongSoTinChiBatBuoc + tongSoTinChiTuChon;


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
            model.addAttribute("hocPhanDaiCuongBatBuoc", hocPhanDaiCuongBatBuoc);
            model.addAttribute("hocPhanDaiCuongTuChon", hocPhanDaiCuongTuChon);
            model.addAttribute("hocPhanCoSoNganhBatBuoc", hocPhanCoSoNganhBatBuoc);
            model.addAttribute("hocPhanCoSoNganhTuChon", hocPhanCoSoNganhTuChon);
            model.addAttribute("hocPhanKienThucNganhBatBuoc", hocPhanKienThucNganhBatBuoc);
            model.addAttribute("hocPhanKienThucNganhTuChon", hocPhanKienThucNganhTuChon);
            model.addAttribute("hocPhanChuyenNganhBatBuoc", hocPhanChuyenNganhBatBuoc);
            model.addAttribute("hocPhanChuyenNganhTuChon", hocPhanChuyenNganhTuChon);
            model.addAttribute("tcDaiCuongBatBuoc", tcDaiCuongBatBuoc);
            model.addAttribute("tcDaiCuongTuChon", tcDaiCuongTuChon);
            model.addAttribute("tcCoSoNganhBatBuoc", tcCoSoNganhBatBuoc);
            model.addAttribute("tcCoSoNganhTuChon", tcCoSoNganhTuChon);
            model.addAttribute("tcKienThucNganhBatBuoc", tcKienThucNganhBatBuoc);
            model.addAttribute("tcKienThucNganhTuChon", tcKienThucNganhTuChon);
            model.addAttribute("tcChuyenNganhBatBuoc", tcChuyenNganhBatBuoc);
            model.addAttribute("tcChuyenNganhTuChon", tcChuyenNganhTuChon);
            model.addAttribute("tcToiThieuDaiCuongBatBuoc", tcToiThieuDaiCuongBatBuoc);
            model.addAttribute("tcToiThieuDaiCuongTuChon", tcToiThieuDaiCuongTuChon);
            model.addAttribute("tcToiThieuCoSoNganhBatBuoc", tcToiThieuCoSoNganhBatBuoc);
            model.addAttribute("tcToiThieuCoSoNganhTuChon", tcToiThieuCoSoNganhTuChon);
            model.addAttribute("tcToiThieuKienThucNganhBatBuoc", tcToiThieuKienThucNganhBatBuoc);
            model.addAttribute("tcToiThieuKienThucNganhTuChon", tcToiThieuKienThucNganhTuChon);
            model.addAttribute("tcToiThieuChuyenNganhBatBuoc", tcToiThieuChuyenNganhBatBuoc);
            model.addAttribute("tcToiThieuChuyenNganhTuChon", tcToiThieuChuyenNganhTuChon);
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
