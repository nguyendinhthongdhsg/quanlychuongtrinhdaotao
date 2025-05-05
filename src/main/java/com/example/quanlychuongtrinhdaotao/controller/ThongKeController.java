package com.example.quanlychuongtrinhdaotao.controller;

import com.example.quanlychuongtrinhdaotao.entity.GiangVien;
import com.example.quanlychuongtrinhdaotao.entity.HocPhan;
import com.example.quanlychuongtrinhdaotao.entity.KeHoachDayHoc;
import com.example.quanlychuongtrinhdaotao.entity.User;
import com.example.quanlychuongtrinhdaotao.repository.GiangVienRepository;
import com.example.quanlychuongtrinhdaotao.repository.HocPhanRepository;
import com.example.quanlychuongtrinhdaotao.repository.UserRepository;
import com.example.quanlychuongtrinhdaotao.service.GiangVienService;
import com.example.quanlychuongtrinhdaotao.service.KeHoachDayHocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ThongKeController {

    @Autowired
    private GiangVienService giangVienService;

    @Autowired
    private KeHoachDayHocService keHoachDayHocService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GiangVienRepository giangVienRepository;

    @Autowired
    private HocPhanRepository hocPhanRepository;

    @GetMapping("/thong-ke")
    @PreAuthorize("hasRole('ROLE_admin') or hasRole('ROLE_truongkhoa') or hasRole('ROLE_giangvien')")
    public String showThongKe(Model model) {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findByUsername(username).orElse(null);
        List<GiangVien> giangVienList = giangVienService.getAllGiangVien();
        List<KeHoachDayHoc> keHoachList = keHoachDayHocService.getAllKeHoach();
        List<HocPhan> hocPhanList = hocPhanRepository.findAll();
        model.addAttribute("tongGiangVien", giangVienList.size());
        model.addAttribute("tongGiangVienDangLamViec",
                giangVienList.stream().filter(gv -> "Đang làm việc".equals(gv.getTrangThai())).count());
        model.addAttribute("tongGiangVienNghiViec",
                giangVienList.stream().filter(gv -> "Không làm việc".equals(gv.getTrangThai())).count());
        model.addAttribute("tongHocPhanAll", hocPhanList.size());
        model.addAttribute("tongTinChi", hocPhanList.stream()
                .mapToInt(HocPhan::getSoTinChi)
                .sum());
        model.addAttribute("tongTietLyThuyet", hocPhanList.stream()
                .mapToInt(HocPhan::getSoTietLyThuyet)
                .sum());
        model.addAttribute("tongTietThucHanh", hocPhanList.stream()
                .mapToInt(HocPhan::getSoTietThucHanh)
                .sum());
        model.addAttribute("tongTietThucTap", hocPhanList.stream()
                .mapToInt(HocPhan::getSoTietThucTap)
                .sum());
        long totalGiangVienInKeHoach = keHoachList.stream()
                .mapToLong(keHoach -> keHoachDayHocService.countTotalGiangVienInKeHoach(keHoach))
                .sum();
        int totalTeachingHours = keHoachList.stream()
                .mapToInt(keHoach -> keHoachDayHocService.calculateTotalTeachingHours(keHoach))
                .sum();
        model.addAttribute("tongHocPhan", keHoachList.stream().mapToInt(kh -> kh.getHocPhanTrongKeHoachList().size()).sum());
        model.addAttribute("tongGiangVienInKeHoach", totalGiangVienInKeHoach);
        model.addAttribute("tongTietGiangDay", totalTeachingHours);
        return "thong_ke";
    }
}