package com.example.quanlychuongtrinhdaotao.controller;

import com.example.quanlychuongtrinhdaotao.entity.GiangVien;
import com.example.quanlychuongtrinhdaotao.service.ExcelService;
import com.example.quanlychuongtrinhdaotao.service.GiangVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/giangvien/excel")
public class ExcelController {

    @Autowired
    private ExcelService excelService;

    @Autowired
    private GiangVienService giangVienService;

    @GetMapping("/download-template")
    @PreAuthorize("hasRole('ROLE_admin')")
    public ResponseEntity<InputStreamResource> downloadTemplate() throws IOException {
        ByteArrayInputStream in = excelService.createExcelTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=template_import_giangvien.xlsx");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(new InputStreamResource(in));
    }

    @GetMapping("/export")
    @PreAuthorize("hasRole('ROLE_admin') or hasRole('ROLE_giangvien')")
    public ResponseEntity<InputStreamResource> exportToExcel() throws IOException {
        List<GiangVien> giangViens = giangVienService.getAllGiangVien();
        ByteArrayInputStream in = excelService.exportGiangVienToExcel(giangViens);

        LocalDateTime now = LocalDateTime.now();
        String fileName = "danh_sach_giang_vien_" + now.format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".xlsx";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=" + fileName);

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(new InputStreamResource(in));
    }

    @PostMapping("/import")
    @PreAuthorize("hasRole('ROLE_admin')")
    public String importFromExcel(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Vui lòng chọn file Excel để import");
            return "redirect:/giangvien";
        }

        // Kiểm tra định dạng file
        if (!file.getOriginalFilename().endsWith(".xlsx") && !file.getOriginalFilename().endsWith(".xls")) {
            redirectAttributes.addFlashAttribute("errorMessage", "Vui lòng chọn file Excel (.xlsx hoặc .xls)");
            return "redirect:/giangvien";
        }

        try {
            List<GiangVien> importedGiangViens = excelService.importGiangVienFromExcel(file);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Import thành công " + importedGiangViens.size() + " giảng viên");
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi import file: " + e.getMessage());
        }

        return "redirect:/giangvien";
    }

    @GetMapping("/import-view")
    @PreAuthorize("hasRole('ROLE_admin')")
    public String showImportForm(Model model) {
        return "giangvien_import";
    }
}