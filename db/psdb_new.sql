/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 100428 (10.4.28-MariaDB)
 Source Host           : localhost:3306
 Source Schema         : psdb_new

 Target Server Type    : MySQL
 Target Server Version : 100428 (10.4.28-MariaDB)
 File Encoding         : 65001

 Date: 09/12/2023 20:26:08
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_nota
-- ----------------------------
DROP TABLE IF EXISTS `tb_nota`;
CREATE TABLE `tb_nota`  (
  `id_nota` int NOT NULL AUTO_INCREMENT,
  `id_ps` int NULL DEFAULT NULL,
  `id_staff` int NULL DEFAULT NULL,
  `jenis` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `tgl_sewa` date NULL DEFAULT NULL,
  `tgl_kembali` date NULL DEFAULT NULL,
  `durasi` int NULL DEFAULT NULL,
  `total_biaya` float NULL DEFAULT NULL,
  `last_updated` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id_nota`) USING BTREE,
  INDEX `id_ps`(`id_ps` ASC) USING BTREE,
  INDEX `id_staff`(`id_staff` ASC) USING BTREE,
  CONSTRAINT `id_ps` FOREIGN KEY (`id_ps`) REFERENCES `tb_ps` (`id_ps`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `id_staff` FOREIGN KEY (`id_staff`) REFERENCES `tb_staff` (`id_staff`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tb_ps
-- ----------------------------
DROP TABLE IF EXISTS `tb_ps`;
CREATE TABLE `tb_ps`  (
  `id_ps` int NOT NULL AUTO_INCREMENT,
  `tipe` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `harga` float NULL DEFAULT NULL,
  `last_updated` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id_ps`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12345 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tb_staff
-- ----------------------------
DROP TABLE IF EXISTS `tb_staff`;
CREATE TABLE `tb_staff`  (
  `id_staff` int NOT NULL AUTO_INCREMENT,
  `nama` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `pass` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `last_updated` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id_staff`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 55662 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Triggers structure for table tb_nota
-- ----------------------------
DROP TRIGGER IF EXISTS `total_before_insert`;
delimiter ;;
CREATE TRIGGER `total_before_insert` BEFORE INSERT ON `tb_nota` FOR EACH ROW BEGIN
    DECLARE harga_ps FLOAT;
    
    -- Ambil harga dari tb_ps
    SELECT harga INTO harga_ps FROM tb_ps WHERE id_ps = NEW.id_ps;
    
    -- Hitung total_harga
    SET NEW.total_biaya = NEW.durasi * harga_ps;
    
    
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table tb_nota
-- ----------------------------
DROP TRIGGER IF EXISTS `jenis_before_insert`;
delimiter ;;
CREATE TRIGGER `jenis_before_insert` BEFORE INSERT ON `tb_nota` FOR EACH ROW BEGIN
    -- Jika jenis nota adalah 'nota pinjam'
    IF NEW.jenis = 'nota pinjam' THEN
        -- Update status ps menjadi 'Sedang Disewa'
        UPDATE tb_ps SET status = 'Sedang Disewa' WHERE id_ps = NEW.id_ps;
		-- Jika jenis nota adalah 'nota kembali'
    ELSEIF NEW.jenis = 'nota kembali' THEN
        -- Update status ps menjadi 'Tersedia'
        UPDATE tb_ps SET status = 'Tersedia' WHERE id_ps = NEW.id_ps;
    END IF;
    
    -- Set last_updated
    SET NEW.last_updated = NOW();
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table tb_nota
-- ----------------------------
DROP TRIGGER IF EXISTS `insert_updated_tb_nota`;
delimiter ;;
CREATE TRIGGER `insert_updated_tb_nota` BEFORE INSERT ON `tb_nota` FOR EACH ROW SET NEW.last_updated = NOW()
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table tb_nota
-- ----------------------------
DROP TRIGGER IF EXISTS `total_before_update`;
delimiter ;;
CREATE TRIGGER `total_before_update` BEFORE UPDATE ON `tb_nota` FOR EACH ROW BEGIN
    DECLARE harga_ps FLOAT;
    
    -- Ambil harga dari tb_ps
    SELECT harga INTO harga_ps FROM tb_ps WHERE id_ps = NEW.id_ps;
    
    -- Hitung total_harga
    SET NEW.total_biaya = NEW.durasi * harga_ps;
    
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table tb_nota
-- ----------------------------
DROP TRIGGER IF EXISTS `jenis_before_update`;
delimiter ;;
CREATE TRIGGER `jenis_before_update` BEFORE UPDATE ON `tb_nota` FOR EACH ROW BEGIN
    -- Jika jenis nota adalah 'nota pinjam'
    IF NEW.jenis = 'nota pinjam' THEN
        -- Update status ps menjadi 'Sedang Disewa'
        UPDATE tb_ps SET status = 'Sedang Disewa' WHERE id_ps = NEW.id_ps;
    -- Jika jenis nota adalah 'nota kembali'
    ELSEIF NEW.jenis = 'nota kembali' THEN
        -- Update status ps menjadi 'Tersedia'
        UPDATE tb_ps SET status = 'Tersedia' WHERE id_ps = NEW.id_ps;
    END IF;

    -- Set last_updated
    SET NEW.last_updated = NOW();
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table tb_nota
-- ----------------------------
DROP TRIGGER IF EXISTS `update_updated_tb_nota`;
delimiter ;;
CREATE TRIGGER `update_updated_tb_nota` BEFORE UPDATE ON `tb_nota` FOR EACH ROW SET NEW.last_updated = NOW()
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table tb_ps
-- ----------------------------
DROP TRIGGER IF EXISTS `insert_updated_tb_ps`;
delimiter ;;
CREATE TRIGGER `insert_updated_tb_ps` BEFORE INSERT ON `tb_ps` FOR EACH ROW SET NEW.last_updated = NOW()
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table tb_ps
-- ----------------------------
DROP TRIGGER IF EXISTS `update_updated_tb_ps`;
delimiter ;;
CREATE TRIGGER `update_updated_tb_ps` BEFORE UPDATE ON `tb_ps` FOR EACH ROW SET NEW.last_updated = NOW()
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table tb_staff
-- ----------------------------
DROP TRIGGER IF EXISTS `insert_updated_tb_staff`;
delimiter ;;
CREATE TRIGGER `insert_updated_tb_staff` BEFORE INSERT ON `tb_staff` FOR EACH ROW SET NEW.last_updated = NOW()
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table tb_staff
-- ----------------------------
DROP TRIGGER IF EXISTS `update_updated_tb_staff`;
delimiter ;;
CREATE TRIGGER `update_updated_tb_staff` BEFORE UPDATE ON `tb_staff` FOR EACH ROW SET NEW.last_updated = NOW()
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
