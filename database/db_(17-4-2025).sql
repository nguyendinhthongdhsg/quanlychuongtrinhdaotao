-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: localhost    Database: quanlydaotao
-- ------------------------------------------------------
-- Server version	8.0.41

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `ctdt_cotdiem`
--

DROP TABLE IF EXISTS `ctdt_cotdiem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ctdt_cotdiem` (
  `id` int NOT NULL AUTO_INCREMENT,
  `decuong_id` int NOT NULL,
  `ten_cot_diem` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `ty_le_phan_tram` float DEFAULT NULL,
  `hinh_thuc` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `decuong_id` (`decuong_id`),
  CONSTRAINT `ctdt_cotdiem_ibfk_1` FOREIGN KEY (`decuong_id`) REFERENCES `ctdt_decuongchitiet` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ctdt_cotdiem`
--

LOCK TABLES `ctdt_cotdiem` WRITE;
/*!40000 ALTER TABLE `ctdt_cotdiem` DISABLE KEYS */;
INSERT INTO `ctdt_cotdiem` VALUES (19,6,'Đánh giá quá trình',0.1,''),(20,6,'Đánh giá kiểm tra giữa kỳ',0.3,'Tự luận, trắc nghiệm');
/*!40000 ALTER TABLE `ctdt_cotdiem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ctdt_decuongchitiet`
--

DROP TABLE IF EXISTS `ctdt_decuongchitiet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ctdt_decuongchitiet` (
  `id` int NOT NULL AUTO_INCREMENT,
  `hoc_phan_id` int NOT NULL,
  `muc_tieu` text COLLATE utf8mb4_unicode_ci,
  `noi_dung` text COLLATE utf8mb4_unicode_ci,
  `phuong_phap_giang_day` text COLLATE utf8mb4_unicode_ci,
  `phuong_phap_danh_gia` text COLLATE utf8mb4_unicode_ci,
  `tai_lieu_tham_khao` text COLLATE utf8mb4_unicode_ci,
  `trang_thai` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `hoc_phan_id` (`hoc_phan_id`),
  CONSTRAINT `ctdt_decuongchitiet_ibfk_1` FOREIGN KEY (`hoc_phan_id`) REFERENCES `ctdt_hocphan` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ctdt_decuongchitiet`
--

LOCK TABLES `ctdt_decuongchitiet` WRITE;
/*!40000 ALTER TABLE `ctdt_decuongchitiet` DISABLE KEYS */;
INSERT INTO `ctdt_decuongchitiet` VALUES (6,30,'Định hướng học tập và hiểu biết về Triết học Mác-Lênin.','Tổng quan về các vấn đề cơ bản trong Triết học Mác-Lênin.','Kết hợp giữa lý thuyết và thực tiễn, sử dụng phương pháp giảng dạy tích cực.','Bài kiểm tra giữa kỳ và cuối kỳ.','Các sách giáo khoa, tài liệu học tập về Triết học Mác-Lênin.',1);
/*!40000 ALTER TABLE `ctdt_decuongchitiet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ctdt_giangvien`
--

DROP TABLE IF EXISTS `ctdt_giangvien`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ctdt_giangvien` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `ma_gv` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `ho_ten` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `bo_mon` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `khoa` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `trinh_do` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `chuyen_mon` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `trang_thai` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ma_gv` (`ma_gv`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `ctdt_giangvien_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `ctdt_user` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ctdt_giangvien`
--

LOCK TABLES `ctdt_giangvien` WRITE;
/*!40000 ALTER TABLE `ctdt_giangvien` DISABLE KEYS */;
INSERT INTO `ctdt_giangvien` VALUES (1,3,'GV001','Trần Thị Phương','Công nghệ phần mềm','Công nghệ thông tin','Tiến sĩ','Công nghệ phần mềm, Kiểm thử phần mềm','Đang làm việc'),(2,4,'GV002','Lê Thanh Hùng','Khoa học máy tính','Công nghệ thông tin','Tiến sĩ','Trí tuệ nhân tạo, Machine Learning','Đang làm việc'),(3,5,'GV003','Phạm Tuấn Minh','Hệ thống thông tin','Công nghệ thông tin','Thạc sĩ','Cơ sở dữ liệu, Data Mining','Đang làm việc'),(4,6,'GV004','Nguyễn Thị Lan','Mạng máy tính','Công nghệ thông tin','Tiến sĩ','An toàn mạng, Điện toán đám mây','Đang làm việc'),(5,7,'GV005','Trần Văn Bình','Công nghệ phần mềm','Công nghệ thông tin','Thạc sĩ','Phát triển Web, Mobile Computing','Đang làm việc'),(6,8,'GV006','Võ Thị Linh','Khoa học máy tính','Công nghệ thông tin','Thạc sĩ','Xử lý ngôn ngữ tự nhiên, Thị giác máy tính','Đang làm việc');
/*!40000 ALTER TABLE `ctdt_giangvien` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ctdt_hocphan`
--

DROP TABLE IF EXISTS `ctdt_hocphan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ctdt_hocphan` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ma_hp` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `ten_hp` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `so_tin_chi` int NOT NULL,
  `so_tiet_ly_thuyet` int DEFAULT NULL,
  `so_tiet_thuc_hanh` int DEFAULT NULL,
  `so_tiet_thuc_tap` int DEFAULT NULL,
  `hoc_phan_tien_quyet` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `he_so` float DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ma_hp` (`ma_hp`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ctdt_hocphan`
--

LOCK TABLES `ctdt_hocphan` WRITE;
/*!40000 ALTER TABLE `ctdt_hocphan` DISABLE KEYS */;
INSERT INTO `ctdt_hocphan` VALUES (30,'861301','Triết học Mác – Lênin',3,45,0,0,'',1),(31,'861302','Kinh tế chính trị Mác – Lênin',2,30,0,0,'861301',1);
/*!40000 ALTER TABLE `ctdt_hocphan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ctdt_hocphan_chuongtrinhdaotao`
--

DROP TABLE IF EXISTS `ctdt_hocphan_chuongtrinhdaotao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ctdt_hocphan_chuongtrinhdaotao` (
  `id` int NOT NULL AUTO_INCREMENT,
  `hocphan_id` int NOT NULL,
  `ctdt_id` int NOT NULL,
  `khung_id` int NOT NULL,
  `batbuoc` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ctdt_hocphan_chuontrinhdaotao_fk1_idx` (`ctdt_id`),
  KEY `hocphan_chuongtrinhdaotao_fk2_idx` (`hocphan_id`),
  KEY `hocphan_chuontrinhdaotao_fk3_idx` (`khung_id`),
  CONSTRAINT `hocphan_chuongtrinhdaotao_fk1` FOREIGN KEY (`ctdt_id`) REFERENCES `ctdt_thongtinchung` (`id`) ON DELETE CASCADE,
  CONSTRAINT `hocphan_chuongtrinhdaotao_fk2` FOREIGN KEY (`hocphan_id`) REFERENCES `ctdt_hocphan` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `hocphan_chuontrinhdaotao_fk3` FOREIGN KEY (`khung_id`) REFERENCES `ctdt_khungchuongtrinh` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ctdt_hocphan_chuongtrinhdaotao`
--

LOCK TABLES `ctdt_hocphan_chuongtrinhdaotao` WRITE;
/*!40000 ALTER TABLE `ctdt_hocphan_chuongtrinhdaotao` DISABLE KEYS */;
INSERT INTO `ctdt_hocphan_chuongtrinhdaotao` VALUES (1,30,16,19,1);
/*!40000 ALTER TABLE `ctdt_hocphan_chuongtrinhdaotao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ctdt_kehoachdayhoc`
--

DROP TABLE IF EXISTS `ctdt_kehoachdayhoc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ctdt_kehoachdayhoc` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ctdt_id` int NOT NULL,
  `hoc_phan_id` int NOT NULL,
  `hoc_ky` int NOT NULL,
  `nam_hoc` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ctdt_id` (`ctdt_id`),
  KEY `hoc_phan_id` (`hoc_phan_id`),
  CONSTRAINT `ctdt_kehoachdayhoc_ibfk_1` FOREIGN KEY (`ctdt_id`) REFERENCES `ctdt_thongtinchung` (`id`) ON DELETE CASCADE,
  CONSTRAINT `ctdt_kehoachdayhoc_ibfk_2` FOREIGN KEY (`hoc_phan_id`) REFERENCES `ctdt_hocphan` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ctdt_kehoachdayhoc`
--

LOCK TABLES `ctdt_kehoachdayhoc` WRITE;
/*!40000 ALTER TABLE `ctdt_kehoachdayhoc` DISABLE KEYS */;
/*!40000 ALTER TABLE `ctdt_kehoachdayhoc` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ctdt_kehoachmonhom`
--

DROP TABLE IF EXISTS `ctdt_kehoachmonhom`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ctdt_kehoachmonhom` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ma_nhom` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `hoc_phan_id` int NOT NULL,
  `nam_hoc` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `hoc_ky` int NOT NULL,
  `so_luong_sv` int DEFAULT NULL,
  `thoi_gian_bat_dau` date DEFAULT NULL,
  `thoi_gian_ket_thuc` date DEFAULT NULL,
  `trang_thai` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `hoc_phan_id` (`hoc_phan_id`),
  CONSTRAINT `ctdt_kehoachmonhom_ibfk_1` FOREIGN KEY (`hoc_phan_id`) REFERENCES `ctdt_hocphan` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ctdt_kehoachmonhom`
--

LOCK TABLES `ctdt_kehoachmonhom` WRITE;
/*!40000 ALTER TABLE `ctdt_kehoachmonhom` DISABLE KEYS */;
/*!40000 ALTER TABLE `ctdt_kehoachmonhom` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ctdt_khungchuongtrinh`
--

DROP TABLE IF EXISTS `ctdt_khungchuongtrinh`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ctdt_khungchuongtrinh` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ctdt_id` int NOT NULL,
  `nhom_kien_thuc_id` int NOT NULL,
  `so_tin_chi_bat_buoc_toi_thieu` int NOT NULL,
  `so_tin_chi_tu_chon_toi_thieu` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ctdt_id` (`ctdt_id`),
  KEY `khungchuongtrinh_fk2_idx` (`nhom_kien_thuc_id`),
  CONSTRAINT `khungchuongtrinh_fk1` FOREIGN KEY (`ctdt_id`) REFERENCES `ctdt_thongtinchung` (`id`) ON DELETE CASCADE,
  CONSTRAINT `khungchuongtrinh_fk2` FOREIGN KEY (`nhom_kien_thuc_id`) REFERENCES `ctdt_nhomkienthuc` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ctdt_khungchuongtrinh`
--

LOCK TABLES `ctdt_khungchuongtrinh` WRITE;
/*!40000 ALTER TABLE `ctdt_khungchuongtrinh` DISABLE KEYS */;
INSERT INTO `ctdt_khungchuongtrinh` VALUES (17,16,1,12,2),(18,16,2,9,0),(19,16,3,11,0),(20,16,4,14,0),(21,16,5,37,0),(22,16,6,37,16),(23,16,7,16,15);
/*!40000 ALTER TABLE `ctdt_khungchuongtrinh` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ctdt_nhomkienthuc`
--

DROP TABLE IF EXISTS `ctdt_nhomkienthuc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ctdt_nhomkienthuc` (
  `id` int NOT NULL AUTO_INCREMENT,
  `manhom` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `ten_nhom` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `trangthai` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ctdt_nhomkienthuc`
--

LOCK TABLES `ctdt_nhomkienthuc` WRITE;
/*!40000 ALTER TABLE `ctdt_nhomkienthuc` DISABLE KEYS */;
INSERT INTO `ctdt_nhomkienthuc` VALUES (1,'QPAN','Kiến thức Giáo dục thể chất và Giáo dục quốc phòng và an ninh',1),(2,'NN','Kiến thức Ngoại ngữ',1),(3,'LLCT','Kiến thức Lý luận chính trị',1),(4,'GDDCK','Kiến thức giáo dục đại cương khác',1),(5,'CSN','Kiến thức cơ sở của ngành',1),(6,'NG','Kiến thức ngành',1),(7,'CN','Kiến thức chuyên ngành ',1),(8,'KLTN','Khóa luận tốt nghiệp',1),(9,'TTKL','Các học phần thay thế khóa luận',1);
/*!40000 ALTER TABLE `ctdt_nhomkienthuc` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ctdt_phanconggiangday`
--

DROP TABLE IF EXISTS `ctdt_phanconggiangday`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ctdt_phanconggiangday` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nhom_id` int NOT NULL,
  `giang_vien_id` int NOT NULL,
  `vai_tro` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `so_tiet` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `nhom_id` (`nhom_id`),
  KEY `giang_vien_id` (`giang_vien_id`),
  CONSTRAINT `ctdt_phanconggiangday_ibfk_1` FOREIGN KEY (`nhom_id`) REFERENCES `ctdt_kehoachmonhom` (`id`) ON DELETE CASCADE,
  CONSTRAINT `ctdt_phanconggiangday_ibfk_2` FOREIGN KEY (`giang_vien_id`) REFERENCES `ctdt_giangvien` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ctdt_phanconggiangday`
--

LOCK TABLES `ctdt_phanconggiangday` WRITE;
/*!40000 ALTER TABLE `ctdt_phanconggiangday` DISABLE KEYS */;
/*!40000 ALTER TABLE `ctdt_phanconggiangday` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ctdt_thongtinchung`
--

DROP TABLE IF EXISTS `ctdt_thongtinchung`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ctdt_thongtinchung` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ma_ctdt` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `ten_ctdt` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `nganh` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ma_nganh` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `khoa_quan_ly` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `he_dao_tao` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `trinh_do` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `tong_tin_chi` int DEFAULT NULL,
  `thoi_gian_dao_tao` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `nam_ban_hanh` int DEFAULT NULL,
  `trang_thai` int DEFAULT NULL,
  `gioi_thieu` text COLLATE utf8mb4_unicode_ci,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ma_ctdt` (`ma_ctdt`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ctdt_thongtinchung`
--

LOCK TABLES `ctdt_thongtinchung` WRITE;
/*!40000 ALTER TABLE `ctdt_thongtinchung` DISABLE KEYS */;
INSERT INTO `ctdt_thongtinchung` VALUES (16,'CNTT2024','Chương trình đào tạo ngành Công nghệ thông tin','Công nghệ thông tin','7480201','Khoa Công nghệ Thông tin','Chính quy','Đại học',155,'4.5 năm',2024,1,'Chương trình đào tạo kỹ sư Công nghệ thông tin (CNTT) đào tạo hệ chính quy ở trình độ đại học được thiết kế lần đầu vào năm 2007. Chương trình được cập nhật hàng năm ở cấp môn học và được chỉnh sửa theo định kỳ 4 năm một lần sau mỗi Khóa tốt nghiệp vào các năm 2012, 2016, 2020, 2024. Chương trình hiện đang được áp dụng là phiên bản theo chu kỳ chu kỳ 2024-2028, được giảng dạy trong 4.5 năm bao gồm 155 tín chỉ. ');
/*!40000 ALTER TABLE `ctdt_thongtinchung` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ctdt_user`
--

DROP TABLE IF EXISTS `ctdt_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ctdt_user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `ho_ten` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `so_dien_thoai` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `vai_tro` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `nam_sinh` int DEFAULT NULL,
  `trang_thai` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ctdt_user`
--

LOCK TABLES `ctdt_user` WRITE;
/*!40000 ALTER TABLE `ctdt_user` DISABLE KEYS */;
INSERT INTO `ctdt_user` VALUES (1,'admin','$2y$10$FKlZJ6Ky0GPM/tAEFO.DwehOQHblUCfEbOM35ZED3kN32yVxUcmOe','Quản trị viên','admin@truong.edu.vn','0901234567','admin',1985,_binary ''),(2,'truongkhoa','$2y$10$IiU5NAzEpBnZ1K7V3YlbVew5a3RKV1hEfYCJqXcLYU4DQB3TLW3hW','Nguyễn Văn Trưởng','truongkhoa@truong.edu.vn','0912345678','truongkhoa',1975,_binary ''),(3,'phuong','$2y$10$0gLaiD6mxrx.yBBgF5yKZOAfl/IK6D1Kj0.oRiJK7n0ZwGpysVZxi','Trần Thị Phương','phuong@truong.edu.vn','0923456789','giangvien',1980,_binary ''),(4,'hung','$2y$10$YeG6nF2FtV4y09KF96yTveyrNhU0Mb6aSvDsycHMZCHoG4s45HUFq','Lê Thanh Hùng','hung@truong.edu.vn','0934567890','giangvien',1982,_binary ''),(5,'minh','$2y$10$1E/R23EwIfWf8QQyF3lqkeNYHj.YlbVJbCVP5N523XsIxkzZBBmHC','Phạm Tuấn Minh','minh@truong.edu.vn','0945678901','giangvien',1985,_binary ''),(6,'lan','$2y$10$9UAfLvv/OO5K19RlVGJO0e2Rl43W1JSQio4BXdGJZyDfvYKbnlPom','Nguyễn Thị Lan','lan@truong.edu.vn','0956789012','giangvien',1987,_binary ''),(7,'binh','$2y$10$w6BFRxeRefP9zZrmdQA3qedAI0FN9XgpRcvJLSOLmV0kHXUbDEROa','Trần Văn Bình','binh@truong.edu.vn','0967890123','giangvien',1979,_binary ''),(8,'linh','$2y$10$uM/mfghPzDzT0ZoJ5DJVQuhl3rjXwQeE04zzar9Kru9mKdgIEQ8kW','Võ Thị Linh','linh@truong.edu.vn','0978901234','giangvien',1990,_binary ''),(9,'taikhoan1','$2a$10$Iuq3apUOplCLSRD1EV4ASeHcw46mLWC451RvPyLnbm5RS0yTMhwha','Tài khoản 1','taikhoan1@gmail.com','0123456789','giangvien',2000,_binary '');
/*!40000 ALTER TABLE `ctdt_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-04-17  8:05:36
