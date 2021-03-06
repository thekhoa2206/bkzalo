-- MySQL dump 10.13  Distrib 8.0.23, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: bkzalo
-- ------------------------------------------------------
-- Server version	8.0.23

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
-- Table structure for table `tbl_blocks`
--

DROP TABLE IF EXISTS `tbl_blocks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_blocks` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_user_block` int DEFAULT NULL,
  `id_block_user` int DEFAULT NULL,
  `type_message` tinyint(1) DEFAULT '0',
  `type_diary` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `id_user_block_idx` (`id_user_block`),
  KEY `id_block_user_idx` (`id_block_user`),
  CONSTRAINT `id_block_user` FOREIGN KEY (`id_block_user`) REFERENCES `tbl_users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `id_user_block` FOREIGN KEY (`id_user_block`) REFERENCES `tbl_users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_blocks`
--

LOCK TABLES `tbl_blocks` WRITE;
/*!40000 ALTER TABLE `tbl_blocks` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_blocks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_chat`
--

DROP TABLE IF EXISTS `tbl_chat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_chat` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id_sender` int DEFAULT NULL,
  `user_id_receiver` int DEFAULT NULL,
  `content` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `unread` tinyint(1) DEFAULT '1',
  `create_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_user_a_idx` (`user_id_receiver`),
  KEY `id_user_b_idx` (`user_id_sender`),
  CONSTRAINT `id_user_a` FOREIGN KEY (`user_id_receiver`) REFERENCES `tbl_users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `id_user_b` FOREIGN KEY (`user_id_sender`) REFERENCES `tbl_users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_chat`
--

LOCK TABLES `tbl_chat` WRITE;
/*!40000 ALTER TABLE `tbl_chat` DISABLE KEYS */;
INSERT INTO `tbl_chat` VALUES (1,1,2,'tin nh???n 1',1,NULL),(2,1,2,'tin nh???n 2',1,NULL);
/*!40000 ALTER TABLE `tbl_chat` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_comment`
--

DROP TABLE IF EXISTS `tbl_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_comment` (
  `id` int NOT NULL,
  `user_id` int DEFAULT NULL,
  `content` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_comment_users_idx` (`user_id`),
  CONSTRAINT `id_comment_users` FOREIGN KEY (`user_id`) REFERENCES `tbl_users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_comment`
--

LOCK TABLES `tbl_comment` WRITE;
/*!40000 ALTER TABLE `tbl_comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_comment_post`
--

DROP TABLE IF EXISTS `tbl_comment_post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_comment_post` (
  `id_post` int NOT NULL,
  `id_comment` int NOT NULL,
  PRIMARY KEY (`id_post`,`id_comment`),
  KEY `tbl_comment_post_user_idx` (`id_comment`),
  KEY `tbl_comment_post_idx` (`id_post`),
  CONSTRAINT `id_comment` FOREIGN KEY (`id_comment`) REFERENCES `tbl_comment` (`id`),
  CONSTRAINT `id_post` FOREIGN KEY (`id_post`) REFERENCES `tbl_posts` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_comment_post`
--

LOCK TABLES `tbl_comment_post` WRITE;
/*!40000 ALTER TABLE `tbl_comment_post` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_comment_post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_friends`
--

DROP TABLE IF EXISTS `tbl_friends`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_friends` (
  `id_user_a` int NOT NULL,
  `id_user_b` int NOT NULL,
  `id` int NOT NULL AUTO_INCREMENT,
  `is_accept` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `id_friends_user_idx` (`id_user_a`),
  KEY `id_friends_user_2_idx` (`id_user_b`),
  CONSTRAINT `id_friends_user` FOREIGN KEY (`id_user_a`) REFERENCES `tbl_users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `id_friends_user_2` FOREIGN KEY (`id_user_b`) REFERENCES `tbl_users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_friends`
--

LOCK TABLES `tbl_friends` WRITE;
/*!40000 ALTER TABLE `tbl_friends` DISABLE KEYS */;
INSERT INTO `tbl_friends` VALUES (1,4,1,0),(2,4,2,0),(1,3,3,0),(2,3,4,0),(1,2,5,0),(4,3,8,0),(4,3,9,0);
/*!40000 ALTER TABLE `tbl_friends` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_likes_posts`
--

DROP TABLE IF EXISTS `tbl_likes_posts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_likes_posts` (
  `id_post` int NOT NULL,
  `id_users` int NOT NULL,
  KEY `id_like_post_user_idx` (`id_users`),
  KEY `id_like_post_user_2_idx` (`id_post`),
  CONSTRAINT `id_like_post` FOREIGN KEY (`id_post`) REFERENCES `tbl_posts` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `id_like_post_user` FOREIGN KEY (`id_users`) REFERENCES `tbl_users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_likes_posts`
--

LOCK TABLES `tbl_likes_posts` WRITE;
/*!40000 ALTER TABLE `tbl_likes_posts` DISABLE KEYS */;
INSERT INTO `tbl_likes_posts` VALUES (1,2),(1,1),(2,1);
/*!40000 ALTER TABLE `tbl_likes_posts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_posts`
--

DROP TABLE IF EXISTS `tbl_posts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_posts` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `content` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `media` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `can_comment` tinyint(1) DEFAULT '1',
  `state` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `id_user_post_idx` (`user_id`),
  CONSTRAINT `id_user_post` FOREIGN KEY (`user_id`) REFERENCES `tbl_users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_posts`
--

LOCK TABLES `tbl_posts` WRITE;
/*!40000 ALTER TABLE `tbl_posts` DISABLE KEYS */;
INSERT INTO `tbl_posts` VALUES (1,1,'Khoa','Hieu',NULL,1,1),(2,2,'Noi dung','media',NULL,1,1),(3,3,'abc','media',NULL,1,1),(4,4,'xyz','media',NULL,1,1),(8,4,'media',NULL,'2021-04-16 14:32:29',0,0),(9,4,'b??ng ???? Vi???t Nam',NULL,'2021-04-16 14:36:02',0,0);
/*!40000 ALTER TABLE `tbl_posts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_posts_images`
--

DROP TABLE IF EXISTS `tbl_posts_images`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_posts_images` (
  `id` int NOT NULL AUTO_INCREMENT,
  `post_id` int DEFAULT NULL,
  `content` varchar(45) DEFAULT NULL,
  `path` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_posts_images`
--

LOCK TABLES `tbl_posts_images` WRITE;
/*!40000 ALTER TABLE `tbl_posts_images` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_posts_images` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_response`
--

DROP TABLE IF EXISTS `tbl_response`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_response` (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` int DEFAULT NULL,
  `message` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `note` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_response`
--

LOCK TABLES `tbl_response` WRITE;
/*!40000 ALTER TABLE `tbl_response` DISABLE KEYS */;
INSERT INTO `tbl_response` VALUES (1,1000,'OK',NULL),(2,9992,'Post is not existed','B??i vi???t kh??ng t???n t???i'),(3,9993,'Code verify is incorrect','M?? x??c th???c kh??ng ????ng'),(4,9994,'No Data or end of list data','Kh??ng c?? d??? li???u ho???c kh??ng c??n d??? li???u'),(5,9995,'User is not validate','Kh??ng c?? ng?????i d??ng n??y'),(6,9996,'User existed','Ng?????i d??ng n??y ???? t???n t???i'),(7,9997,'Method is invalid','Ph????ng th???c kh??ng ????ng'),(8,9998,'Token is invalid','Sai Token'),(9,9999,'Exception error','L???i exception'),(10,1001,'Can not connect to DB','L???i m???t k???t n???i DB/ ho???c th???c thi c??u SQL'),(11,1002,'Parameter is not enought','S??? l?????ng Parameter kh??ng ?????y ?????'),(12,1003,'Parameter type is invalid','Ki???u tham s??? kh??ng ????ng ?????n'),(13,1004,'Parameter value is invalid','Gi?? tr??? tham s??? kh??ng h???p l???'),(14,1005,'Unknown error','Unknown error'),(15,1006,'File size is too big','C??? file v?????t m???c cho ph??p'),(16,1007,'Upload File Failed!','upload th???t b???i'),(17,1008,'Maximum number of images','S??? l?????ng images v?????t qu?? quy ?????nh'),(18,1009,'Not access','Kh??ng c?? quy???n truy c???p t??i nguy??n'),(19,1010,'Action has been done previously by this user','h??nh ?????ng ???? ???????c ng?????i d??ng th???c hi???n tr?????c ????y');
/*!40000 ALTER TABLE `tbl_response` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_role_user`
--

DROP TABLE IF EXISTS `tbl_role_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_role_user` (
  `id_role` int NOT NULL,
  `id_user` int NOT NULL,
  PRIMARY KEY (`id_role`,`id_user`),
  KEY `id_role_user_1_idx` (`id_user`),
  CONSTRAINT `id_role_user_1` FOREIGN KEY (`id_user`) REFERENCES `tbl_users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `id_role_user_2` FOREIGN KEY (`id_role`) REFERENCES `tbl_roles` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_role_user`
--

LOCK TABLES `tbl_role_user` WRITE;
/*!40000 ALTER TABLE `tbl_role_user` DISABLE KEYS */;
INSERT INTO `tbl_role_user` VALUES (1,1),(2,2),(2,3),(1,4),(1,5);
/*!40000 ALTER TABLE `tbl_role_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_roles`
--

DROP TABLE IF EXISTS `tbl_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_roles` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_roles`
--

LOCK TABLES `tbl_roles` WRITE;
/*!40000 ALTER TABLE `tbl_roles` DISABLE KEYS */;
INSERT INTO `tbl_roles` VALUES (1,'ADMIN'),(2,'USER');
/*!40000 ALTER TABLE `tbl_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_users`
--

DROP TABLE IF EXISTS `tbl_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `phone` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `avatar` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_users`
--

LOCK TABLES `tbl_users` WRITE;
/*!40000 ALTER TABLE `tbl_users` DISABLE KEYS */;
INSERT INTO `tbl_users` VALUES (1,'092872121','12334','bui','avatar.img'),(2,'092231321','1233223','nguyen','avatar.img'),(3,'0923872132','1233432322','vu','avatar.img'),(4,'0904515270','123456','vo',NULL),(5,'0912345678','123456','tran',NULL);
/*!40000 ALTER TABLE `tbl_users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-04-16 19:34:17
