package com.example.quanlychuongtrinhdaotao.service;

import com.example.quanlychuongtrinhdaotao.entity.GiangVien;
import com.example.quanlychuongtrinhdaotao.entity.User;
import com.example.quanlychuongtrinhdaotao.repository.GiangVienRepository;
import com.example.quanlychuongtrinhdaotao.repository.UserRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ExcelService {

    @Autowired
    private GiangVienRepository giangVienRepository;

    @Autowired
    private UserRepository userRepository;

    // Export giảng viên ra Excel
    public ByteArrayInputStream exportGiangVienToExcel(List<GiangVien> giangVienList) throws IOException {
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            // Tạo sheet chính danh sách giảng viên
            Sheet sheet = workbook.createSheet("Danh sách giảng viên");

            // Tạo font styles
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 14);
            headerFont.setColor(IndexedColors.DARK_BLUE.getIndex());

            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);
            headerCellStyle.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
            headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerCellStyle.setBorderBottom(BorderStyle.THIN);
            headerCellStyle.setBorderTop(BorderStyle.THIN);
            headerCellStyle.setBorderRight(BorderStyle.THIN);
            headerCellStyle.setBorderLeft(BorderStyle.THIN);
            headerCellStyle.setAlignment(HorizontalAlignment.CENTER);

            // Tạo row header
            Row headerRow = sheet.createRow(0);
            String[] columns = {"STT", "Mã GV", "Họ tên", "Bộ môn", "Khoa", "Chuyên môn", "Trình độ", "Trạng thái", "Username", "Email"};

            // Tạo header cells
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerCellStyle);
            }

            // Cell style cho data
            CellStyle dataCellStyle = workbook.createCellStyle();
            dataCellStyle.setBorderBottom(BorderStyle.THIN);
            dataCellStyle.setBorderTop(BorderStyle.THIN);
            dataCellStyle.setBorderRight(BorderStyle.THIN);
            dataCellStyle.setBorderLeft(BorderStyle.THIN);

            // Tạo style cho active và inactive
            CellStyle activeCellStyle = workbook.createCellStyle();
            activeCellStyle.cloneStyleFrom(dataCellStyle);
            Font activeFont = workbook.createFont();
            activeFont.setColor(IndexedColors.GREEN.getIndex());
            activeFont.setBold(true);
            activeCellStyle.setFont(activeFont);

            CellStyle inactiveCellStyle = workbook.createCellStyle();
            inactiveCellStyle.cloneStyleFrom(dataCellStyle);
            Font inactiveFont = workbook.createFont();
            inactiveFont.setColor(IndexedColors.RED.getIndex());
            inactiveFont.setBold(true);
            inactiveCellStyle.setFont(inactiveFont);

            // Điền data
            int rowIdx = 1;
            for (GiangVien giangVien : giangVienList) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(rowIdx - 1);
                row.createCell(1).setCellValue(giangVien.getMaGV());
                row.createCell(2).setCellValue(giangVien.getHoTen());
                row.createCell(3).setCellValue(giangVien.getBoMon());
                row.createCell(4).setCellValue(giangVien.getKhoa());
                row.createCell(5).setCellValue(giangVien.getChuyenMon());
                row.createCell(6).setCellValue(giangVien.getTrinhDo());

                Cell statusCell = row.createCell(7);
                statusCell.setCellValue(giangVien.getTrangThai());
                if ("Đang làm việc".equals(giangVien.getTrangThai())) {
                    statusCell.setCellStyle(activeCellStyle);
                } else {
                    statusCell.setCellStyle(inactiveCellStyle);
                }

                if (giangVien.getUser() != null) {
                    row.createCell(8).setCellValue(giangVien.getUser().getUsername());
                    row.createCell(9).setCellValue(giangVien.getUser().getEmail());
                } else {
                    row.createCell(8).setCellValue("N/A");
                    row.createCell(9).setCellValue("N/A");
                }

                // Áp dụng style cho các cell khác
                for(int i = 0; i < columns.length; i++) {
                    if(i != 7) { // Bỏ qua cell trạng thái đã được styling ở trên
                        row.getCell(i).setCellStyle(dataCellStyle);
                    }
                }
            }

            // Tạo sheet thống kê
            Sheet statsSheet = workbook.createSheet("Thống kê");

            // Header cho sheet thống kê
            Row statsHeaderRow = statsSheet.createRow(0);
            Cell statsHeaderCell = statsHeaderRow.createCell(0);
            statsHeaderCell.setCellValue("THỐNG KÊ GIẢNG VIÊN");
            statsHeaderCell.setCellStyle(headerCellStyle);
            statsSheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(0, 0, 0, 1));

            // Tổng số giảng viên
            Row totalRow = statsSheet.createRow(2);
            totalRow.createCell(0).setCellValue("Tổng số giảng viên:");
            totalRow.createCell(1).setCellValue(giangVienList.size());

            // Giảng viên đang làm việc
            Row activeRow = statsSheet.createRow(3);
            long activeCount = giangVienList.stream()
                    .filter(gv -> "Đang làm việc".equals(gv.getTrangThai()))
                    .count();
            activeRow.createCell(0).setCellValue("Đang làm việc:");
            activeRow.createCell(1).setCellValue(activeCount);

            // Giảng viên không làm việc
            Row inactiveRow = statsSheet.createRow(4);
            long inactiveCount = giangVienList.stream()
                    .filter(gv -> "Không làm việc".equals(gv.getTrangThai()))
                    .count();
            inactiveRow.createCell(0).setCellValue("Không làm việc:");
            inactiveRow.createCell(1).setCellValue(inactiveCount);

            // Thống kê theo khoa
            Row deptHeaderRow = statsSheet.createRow(6);
            deptHeaderRow.createCell(0).setCellValue("THỐNG KÊ THEO KHOA");
            deptHeaderRow.getCell(0).setCellStyle(headerCellStyle);
            statsSheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(6, 6, 0, 1));

            // Tính toán số lượng theo khoa
            Map<String, Long> countByDept = giangVienList.stream()
                    .collect(Collectors.groupingBy(GiangVien::getKhoa, Collectors.counting()));

            int deptRowIdx = 7;
            for (Map.Entry<String, Long> entry : countByDept.entrySet()) {
                Row deptRow = statsSheet.createRow(deptRowIdx++);
                deptRow.createCell(0).setCellValue(entry.getKey());
                deptRow.createCell(1).setCellValue(entry.getValue());
            }

            // Auto size columns cho cả hai sheet
            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }
            statsSheet.autoSizeColumn(0);
            statsSheet.autoSizeColumn(1);

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }

    // Import giảng viên từ Excel
    public List<GiangVien> importGiangVienFromExcel(MultipartFile file) throws IOException {
        List<GiangVien> importedGiangViens = new ArrayList<>();
        List<String> errors = new ArrayList<>();

        try (InputStream is = file.getInputStream();
             Workbook workbook = WorkbookFactory.create(is)) {

            Sheet sheet = workbook.getSheetAt(0); // Lấy sheet đầu tiên
            Iterator<Row> rows = sheet.iterator();

            // Bỏ qua header
            if (rows.hasNext()) {
                rows.next();
            }

            int rowNumber = 1;
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                rowNumber++;

                try {
                    // Đọc dữ liệu từ các ô
                    String maGV = getCellValueAsString(currentRow.getCell(1));
                    String hoTen = getCellValueAsString(currentRow.getCell(2));
                    String boMon = getCellValueAsString(currentRow.getCell(3));
                    String khoa = getCellValueAsString(currentRow.getCell(4));
                    String chuyenMon = getCellValueAsString(currentRow.getCell(5));
                    String trinhDo = getCellValueAsString(currentRow.getCell(6));
                    String trangThai = getCellValueAsString(currentRow.getCell(7));
                    String username = getCellValueAsString(currentRow.getCell(8));

                    // Validate dữ liệu cơ bản
                    if (maGV.isEmpty() || hoTen.isEmpty() || boMon.isEmpty() ||
                            khoa.isEmpty() || trangThai.isEmpty()) {
                        errors.add("Dòng " + rowNumber + ": Thiếu thông tin bắt buộc");
                        continue;
                    }

                    // Kiểm tra xem mã GV đã tồn tại chưa
                    GiangVien existingGV = giangVienRepository.findByMaGV(maGV);
                    GiangVien giangVien;

                    if (existingGV != null) {
                        // Cập nhật thông tin giảng viên hiện có
                        giangVien = existingGV;
                    } else {
                        // Tạo giảng viên mới
                        giangVien = new GiangVien();
                        giangVien.setMaGV(maGV);
                    }

                    // Cập nhật thông tin
                    giangVien.setHoTen(hoTen);
                    giangVien.setBoMon(boMon);
                    giangVien.setKhoa(khoa);
                    giangVien.setChuyenMon(chuyenMon);
                    giangVien.setTrinhDo(trinhDo);
                    giangVien.setTrangThai(trangThai);

                    // Xử lý user nếu có
                    Optional<User> optionalUser = userRepository.findByUsername(username);
                    if (optionalUser.isPresent()) {
                        giangVien.setUser(optionalUser.get());
                    } else {
                        errors.add("Dòng " + rowNumber + ": Không tìm thấy user với username " + username);
                    }
                    importedGiangViens.add(giangVien);

                } catch (Exception e) {
                    errors.add("Dòng " + rowNumber + ": Lỗi xử lý - " + e.getMessage());
                }
            }

            // Lưu các giảng viên hợp lệ vào database
            if (!importedGiangViens.isEmpty()) {
                giangVienRepository.saveAll(importedGiangViens);
            }

            return importedGiangViens;
        }
    }

    // Helper method để đọc giá trị cell dưới dạng String
    private String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf((int) cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }

    // Tạo file mẫu Excel để import
    public ByteArrayInputStream createExcelTemplate() throws IOException {
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("Template Import");

            // Tạo style cho header
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 12);

            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);
            headerCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerCellStyle.setBorderBottom(BorderStyle.THIN);
            headerCellStyle.setBorderTop(BorderStyle.THIN);
            headerCellStyle.setBorderRight(BorderStyle.THIN);
            headerCellStyle.setBorderLeft(BorderStyle.THIN);

            // Tạo header row
            Row headerRow = sheet.createRow(0);
            String[] columns = {"STT", "Mã GV (*)", "Họ tên (*)", "Bộ môn (*)", "Khoa (*)",
                    "Chuyên môn", "Trình độ", "Trạng thái (*)", "Username"};

            // Tạo cells cho header
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerCellStyle);
            }

            // Tạo một dòng mẫu
            Row sampleRow = sheet.createRow(1);
            sampleRow.createCell(0).setCellValue(1);
            sampleRow.createCell(1).setCellValue("GV001");
            sampleRow.createCell(2).setCellValue("Nguyễn Văn A");
            sampleRow.createCell(3).setCellValue("Công nghệ phần mềm");
            sampleRow.createCell(4).setCellValue("Công nghệ thông tin");
            sampleRow.createCell(5).setCellValue("Kỹ thuật phần mềm");
            sampleRow.createCell(6).setCellValue("Tiến sĩ");
            sampleRow.createCell(7).setCellValue("Đang làm việc");
            sampleRow.createCell(8).setCellValue("username123");

            // Auto size columns
            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }
}