package com.example.quanlychuongtrinhdaotao.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.quanlychuongtrinhdaotao.entity.GiangVien;
import com.example.quanlychuongtrinhdaotao.entity.HocPhan;
import com.example.quanlychuongtrinhdaotao.entity.KeHoachDayHoc;
import com.example.quanlychuongtrinhdaotao.entity.PhanCongGiangDay;
import com.example.quanlychuongtrinhdaotao.entity.ThongKePhanCongDTO;
import com.example.quanlychuongtrinhdaotao.entity.User;
import com.example.quanlychuongtrinhdaotao.repository.GiangVienRepository;
import com.example.quanlychuongtrinhdaotao.repository.HocPhanRepository;
import com.example.quanlychuongtrinhdaotao.repository.UserRepository;
import com.example.quanlychuongtrinhdaotao.service.GiangVienService;
import com.example.quanlychuongtrinhdaotao.service.KeHoachDayHocService;
import com.example.quanlychuongtrinhdaotao.service.PhanCongGiangDayService;

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

        @Autowired
        private PhanCongGiangDayService phanCongGiangDayService;

        @GetMapping("/thong-ke")
        @PreAuthorize("hasRole('ROLE_admin') or hasRole('ROLE_truongkhoa') or hasRole('ROLE_giangvien')")
        public String showThongKe(Model model) {
                String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                                .getUsername();
                User user = userRepository.findByUsername(username).orElse(null);
                List<GiangVien> giangVienList = giangVienService.getAllGiangVien();
                List<KeHoachDayHoc> keHoachList = keHoachDayHocService.getAllKeHoach();
                List<HocPhan> hocPhanList = hocPhanRepository.findAll();
                model.addAttribute("tongGiangVien", giangVienList.size());
                model.addAttribute("tongGiangVienDangLamViec",
                                giangVienList.stream().filter(gv -> "Đang làm việc".equals(gv.getTrangThai())).count());
                model.addAttribute("tongGiangVienNghiViec",
                                giangVienList.stream().filter(gv -> "Không làm việc".equals(gv.getTrangThai()))
                                                .count());
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
                model.addAttribute("tongHocPhan",
                                keHoachList.stream().mapToInt(kh -> kh.getHocPhanTrongKeHoachList().size()).sum());
                model.addAttribute("tongGiangVienInKeHoach", totalGiangVienInKeHoach);
                model.addAttribute("tongTietGiangDay", totalTeachingHours);

                return "thong_ke";
        }

        @GetMapping("/thong-ke/phan-cong-giang-vien")
        @PreAuthorize("hasRole('ROLE_admin') or hasRole('ROLE_truongkhoa')")
        public String thongKePhanCongGiangVien(Model model) {
                List<GiangVien> giangVienList = giangVienService.getAllGiangVien();
                List<PhanCongGiangDay> phanCongList = phanCongGiangDayService.getAll();

                Map<Long, List<PhanCongGiangDay>> phanCongByGiangVien = phanCongList.stream()
                                .collect(Collectors.groupingBy(pc -> pc.getGiangVien().getId()));

                List<ThongKePhanCongDTO> thongKeList = new java.util.ArrayList<>();
                for (GiangVien gv : giangVienList) {
                        List<PhanCongGiangDay> pcList = phanCongByGiangVien.getOrDefault(gv.getId(), List.of());
                    int tongTietGiangDay = pcList.stream()
                            .mapToInt(PhanCongGiangDay::getSoTietThucHien)
                            .sum();

                        int tongTietCongTacKhac = 0;
                        thongKeList.add(new ThongKePhanCongDTO(gv, pcList, tongTietGiangDay, tongTietCongTacKhac));
                }
                model.addAttribute("thongKeList", thongKeList);
                return "thongke_phancong_giangvien";
        }

}