CREATE DATABASE  IF NOT EXISTS `makekit` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `makekit`;
-- MySQL dump 10.13  Distrib 8.0.17, for macos10.14 (x86_64)
--
-- Host: localhost    Database: makekit
-- ------------------------------------------------------
-- Server version	8.0.17

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
-- Table structure for table `cartdetail`
--

DROP TABLE IF EXISTS `cartdetail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cartdetail` (
  `DcartNo` int(11) NOT NULL AUTO_INCREMENT,
  `product_productNo` int(11) NOT NULL,
  `userinfo_userEmail` varchar(15) NOT NULL,
  `cartinfo_cartNo` int(11) NOT NULL,
  `cartQuantity` int(11) DEFAULT NULL,
  PRIMARY KEY (`DcartNo`),
  KEY `goods_prdNo` (`product_productNo`),
  KEY `user_userId` (`userinfo_userEmail`),
  KEY `fk_orderdetail_copy1_cartinfo1_idx` (`cartinfo_cartNo`),
  CONSTRAINT `fk_orderdetail_copy1_cartinfo1` FOREIGN KEY (`cartinfo_cartNo`) REFERENCES `cartinfo` (`cartNo`),
  CONSTRAINT `orderdetail_ibfk_20` FOREIGN KEY (`product_productNo`) REFERENCES `product` (`productNo`),
  CONSTRAINT `orderdetail_ibfk_30` FOREIGN KEY (`userinfo_userEmail`) REFERENCES `userinfo` (`userEmail`)
) ENGINE=InnoDB AUTO_INCREMENT=298 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cartdetail`
--

LOCK TABLES `cartdetail` WRITE;
/*!40000 ALTER TABLE `cartdetail` DISABLE KEYS */;
/*!40000 ALTER TABLE `cartdetail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cartinfo`
--

DROP TABLE IF EXISTS `cartinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cartinfo` (
  `cartNo` int(11) NOT NULL AUTO_INCREMENT,
  `userinfo_userEmail` varchar(30) NOT NULL,
  PRIMARY KEY (`cartNo`,`userinfo_userEmail`),
  KEY `fk_orderinfo_user2_idx` (`userinfo_userEmail`),
  CONSTRAINT `fk_orderinfo_user20` FOREIGN KEY (`userinfo_userEmail`) REFERENCES `userinfo` (`userEmail`)
) ENGINE=InnoDB AUTO_INCREMENT=78 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cartinfo`
--

LOCK TABLES `cartinfo` WRITE;
/*!40000 ALTER TABLE `cartinfo` DISABLE KEYS */;
INSERT INTO `cartinfo` VALUES (72,'bong@naver.com'),(73,'dong@naver.com'),(74,'gong@naver.com'),(69,'qkr@naver.com'),(75,'qkr@naver.com'),(76,'rong@naver.com'),(71,'song@naver.com'),(77,'song@naver.com');
/*!40000 ALTER TABLE `cartinfo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chatting`
--

DROP TABLE IF EXISTS `chatting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chatting` (
  `userinfo_userEmail_sender` varchar(15) NOT NULL,
  `userinfo_userEmail_receiver` varchar(15) NOT NULL,
  `chattingContents` text,
  `chattingInsertDate` datetime DEFAULT NULL,
  `chattingNumber` int(11) DEFAULT NULL,
  KEY `fk_chatting_userinfo1_idx` (`userinfo_userEmail_sender`),
  KEY `fk_chatting_userinfo2_idx` (`userinfo_userEmail_receiver`),
  CONSTRAINT `fk_chatting_userinfo1` FOREIGN KEY (`userinfo_userEmail_sender`) REFERENCES `userinfo` (`userEmail`),
  CONSTRAINT `fk_chatting_userinfo2` FOREIGN KEY (`userinfo_userEmail_receiver`) REFERENCES `userinfo` (`userEmail`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chatting`
--

LOCK TABLES `chatting` WRITE;
/*!40000 ALTER TABLE `chatting` DISABLE KEYS */;
INSERT INTO `chatting` VALUES ('qkr@naver.com','song@naver.com','qwer','2021-01-15 01:29:55',1);
/*!40000 ALTER TABLE `chatting` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `likeuser`
--

DROP TABLE IF EXISTS `likeuser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `likeuser` (
  `userinfo_userEmail` varchar(15) NOT NULL,
  `userinfo_like_userEmail` varchar(15) NOT NULL,
  KEY `fk_likeuser_userinfo1_idx` (`userinfo_userEmail`),
  KEY `fk_likeuser_userinfo2_idx` (`userinfo_like_userEmail`),
  CONSTRAINT `fk_likeuser_userinfo1` FOREIGN KEY (`userinfo_userEmail`) REFERENCES `userinfo` (`userEmail`),
  CONSTRAINT `fk_likeuser_userinfo2` FOREIGN KEY (`userinfo_like_userEmail`) REFERENCES `userinfo` (`userEmail`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `likeuser`
--

LOCK TABLES `likeuser` WRITE;
/*!40000 ALTER TABLE `likeuser` DISABLE KEYS */;
INSERT INTO `likeuser` VALUES ('qkr@naver.com','gong@naver.com');
/*!40000 ALTER TABLE `likeuser` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notice`
--

DROP TABLE IF EXISTS `notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notice` (
  `nSeqno` int(11) NOT NULL AUTO_INCREMENT,
  `nTitle` varchar(45) NOT NULL,
  `nContent` text NOT NULL,
  `nDate` date DEFAULT NULL,
  PRIMARY KEY (`nSeqno`),
  UNIQUE KEY `seqno_UNIQUE` (`nSeqno`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notice`
--

LOCK TABLES `notice` WRITE;
/*!40000 ALTER TABLE `notice` DISABLE KEYS */;
/*!40000 ALTER TABLE `notice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orderdetail`
--

DROP TABLE IF EXISTS `orderdetail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orderdetail` (
  `orderDetailNo` int(11) NOT NULL AUTO_INCREMENT,
  `userinfo_userEmail` varchar(15) NOT NULL,
  `orderinfo_orderNo` int(11) NOT NULL,
  `goods_productNo` int(11) NOT NULL,
  `orderQuantity` int(11) DEFAULT NULL,
  `orderConfirm` int(11) DEFAULT NULL,
  `orderRefund` datetime DEFAULT NULL,
  `orderStar` varchar(3) DEFAULT NULL,
  `orderReview` text,
  `orderReviewImg` varchar(45) DEFAULT NULL,
  `orderReviewInsertDate` date DEFAULT NULL,
  PRIMARY KEY (`orderDetailNo`),
  KEY `goods_prdNo` (`goods_productNo`),
  KEY `user_userId` (`userinfo_userEmail`),
  KEY `orderdetail_ibfk_4` (`orderinfo_orderNo`),
  CONSTRAINT `orderdetail_ibfk_2` FOREIGN KEY (`goods_productNo`) REFERENCES `product` (`productNo`),
  CONSTRAINT `orderdetail_ibfk_3` FOREIGN KEY (`userinfo_userEmail`) REFERENCES `userinfo` (`userEmail`),
  CONSTRAINT `orderdetail_ibfk_4` FOREIGN KEY (`orderinfo_orderNo`) REFERENCES `orderinfo` (`orderNo`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orderdetail`
--

LOCK TABLES `orderdetail` WRITE;
/*!40000 ALTER TABLE `orderdetail` DISABLE KEYS */;
INSERT INTO `orderdetail` VALUES (6,'qkr@naver.com',6,56,2,1,NULL,'5','정말 맛있었어요!','beef3.jpg','2021-01-12'),(8,'qkr@naver.com',9,56,2,1,NULL,'3','처음과 맛이 조금 달라진거 같은데요?',NULL,'2021-01-15'),(9,'qkr@naver.com',10,48,1,NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `orderdetail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orderinfo`
--

DROP TABLE IF EXISTS `orderinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orderinfo` (
  `orderNo` int(11) NOT NULL AUTO_INCREMENT,
  `userinfo_userEmail` varchar(15) NOT NULL,
  `orderDate` datetime DEFAULT NULL,
  `orderReceiver` varchar(15) DEFAULT NULL,
  `orderRcvAddress` varchar(45) DEFAULT NULL,
  `orderRcvAddressDetail` varchar(45) DEFAULT NULL,
  `orderRcvPhone` varchar(15) DEFAULT NULL,
  `orderTotalPrice` int(11) DEFAULT NULL,
  `orderBank` varchar(6) DEFAULT NULL,
  `orderCardNo` varchar(20) DEFAULT NULL,
  `orderCardPw` varchar(10) DEFAULT NULL,
  `orderDelivery` varchar(15) DEFAULT '상품 준비중',
  `orderDeliveryDate` datetime DEFAULT NULL,
  PRIMARY KEY (`orderNo`,`userinfo_userEmail`),
  KEY `fk_orderinfo_user2_idx` (`userinfo_userEmail`),
  CONSTRAINT `fk_orderinfo_user2` FOREIGN KEY (`userinfo_userEmail`) REFERENCES `userinfo` (`userEmail`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orderinfo`
--

LOCK TABLES `orderinfo` WRITE;
/*!40000 ALTER TABLE `orderinfo` DISABLE KEYS */;
INSERT INTO `orderinfo` VALUES (6,'qkr@naver.com','2021-01-15 16:14:09','박길동','서울시 중구','55','010-4567-4567',62500,'NH채움','1233-4565-7878','1245','상품 준비중',NULL),(8,'song@naver.com','2021-01-15 16:16:34','tkdtp','부산','상세','010-2222-2222',30000,'신한','2222','2222','상품 준비중',NULL),(9,'qkr@naver.com','2021-01-15 16:44:11','박길동','서울시 중구','55','010-4567-4567',62500,'롯데','1111-1111-1111','5774','상품 준비중',NULL),(10,'qkr@naver.com','2021-01-15 17:26:35','박길동','서울시 중구','55','010-4567-4567',26500,'우리','1234-4444-4444','2342','상품 준비중',NULL);
/*!40000 ALTER TABLE `orderinfo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `productNo` int(11) NOT NULL AUTO_INCREMENT,
  `productName` varchar(45) DEFAULT NULL,
  `productSubTitle` varchar(45) DEFAULT NULL,
  `productType` varchar(25) DEFAULT NULL,
  `productPrice` varchar(10) DEFAULT NULL,
  `productStock` varchar(5) DEFAULT NULL,
  `productContent` text,
  `productFilename` varchar(45) DEFAULT NULL,
  `productDFilename` varchar(45) DEFAULT NULL,
  `productAFilename` varchar(45) DEFAULT NULL,
  `productInsertDate` datetime DEFAULT NULL,
  `productDeleteDate` datetime DEFAULT NULL,
  PRIMARY KEY (`productNo`),
  UNIQUE KEY `prdNo_UNIQUE` (`productNo`)
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (47,'비빔밥','전주식 비빔밥','korea','12000','10','전주식 비빔밥','img_product_bibimmakguksu1.png','img_product_bibimmakguksu2.png','img_product_bibimmakguksu3.png','2021-01-15 10:39:54',NULL),(48,'불고기','언양 불고기st','korea','24000','10','맛있는 불고기 입니다.','img_product_bulgogi1.png','img_product_bulgogi2.jpg','img_product_bulgogi3.jpg',NULL,NULL),(54,'황태국','숙취에 좋아요','korea','8000','10','숙취에 좋은 황태국입니다.','img_product_hwangtae1.png','img_product_hwangtae2.jpg','img_product_hwangtae3.jpg',NULL,NULL),(55,'잡채','잔치때 먹던 그맛','korea','15000','10','잔치에 먹던 그맛 !','img_product_japchae1.png','img_product_japchae2.png','img_product_japchae3.png',NULL,NULL),(56,'몽골리안비프','이국적인 색다른','etc','30000','10','이국적인 색다른 음식을 맛보세요','beef1.jpg','beef2.jpg','beef3.jpg',NULL,NULL),(57,'평양식 어복쟁반','전통 평양식','etc','27000','10','전통 평양음식','pyeongyang1.jpg','pyeongyang2.jpg','pyeongyang3.jpg',NULL,NULL),(58,'수비드 스테이크','단백질 풍부 닭가슴살','american','26800','10','수비드로 부드럽게','chisteak1.jpg','chisteak2.jpg','chisteak3.jpg',NULL,NULL),(59,'단호박 파스타','달콤한 단호박','american','27000','10','달콤한 단호박과 부드러운 크림','pasta1.jpg','pasta2.jpg','pasta3.jpg',NULL,NULL),(61,'해물 짬뽕 전골','신선한 해물이 가득','korea','17500','10','신선한 해물이 가득','jjambbong1.jpg','jjambbong2.jpg','jjambbong3.jpg',NULL,NULL),(62,'매운 불족발','술안주로 제격','korea','21000','10','매운 불족발 !','bul1.jpg','bul2.jpg','bul3.jpg',NULL,NULL),(63,'유니짜장','조선호텔 유니짜장','chinese','9000','10','조선호텔을 담은 짜장','jjajang1.png','jjajang2.png','jjajang3.png',NULL,NULL),(64,'목살세트','목살 스테이크 세트!','set','24900','10','영양 가득 한상','set1.jpg','set2.jpg','set3.jpg',NULL,NULL);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `register`
--

DROP TABLE IF EXISTS `register`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `register` (
  `userinfo_userEmail` varchar(15) NOT NULL,
  `product_productNo` int(11) NOT NULL,
  `selleraddress` varchar(45) DEFAULT NULL,
  `selleraddressdetail` varchar(45) DEFAULT NULL,
  KEY `fk_register_userinfo1_idx` (`userinfo_userEmail`),
  KEY `fk_register_product1_idx` (`product_productNo`),
  CONSTRAINT `fk_register_product1` FOREIGN KEY (`product_productNo`) REFERENCES `product` (`productNo`),
  CONSTRAINT `fk_register_userinfo1` FOREIGN KEY (`userinfo_userEmail`) REFERENCES `userinfo` (`userEmail`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `register`
--

LOCK TABLES `register` WRITE;
/*!40000 ALTER TABLE `register` DISABLE KEYS */;
INSERT INTO `register` VALUES ('gong@naver.com',47,'서울특별시 강남대로 402',NULL),('gong@naver.com',48,'서울특별시 강남대로 358',NULL),('qkr@naver.com',59,'서울특별시 강남대로 413',NULL),('qkr@naver.com',58,'서울특별시 강남대로 409',NULL),('qkr@naver.com',63,'서울특별시 강남대로 385',NULL),('rong@naver.com',54,'서울특별시 강남대로 418',NULL),('rong@naver.com',55,'서울특별시 강남대로 426',NULL),('rong@naver.com',56,'서울특별시 강남대로 48',NULL),('bong@naver.com',57,'서울특별시 강남대로 324',NULL),('bong@naver.com',61,'서울특별시 강남대로 314',NULL),('bong@naver.com',62,'서울특별시 강남대로 245',NULL),('bong@naver.com',64,'서울특별시 강남대로 359',NULL);
/*!40000 ALTER TABLE `register` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `userinfo`
--

DROP TABLE IF EXISTS `userinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `userinfo` (
  `userEmail` varchar(30) NOT NULL,
  `userPw` varchar(25) NOT NULL,
  `userName` varchar(10) DEFAULT NULL,
  `userAddress` varchar(45) DEFAULT NULL,
  `userAddressDetail` varchar(45) DEFAULT NULL,
  `userTel` varchar(15) DEFAULT NULL,
  `userBirth` varchar(13) DEFAULT NULL,
  `userInsertDate` date DEFAULT NULL,
  `userDeleteDate` date DEFAULT NULL,
  `userImage` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`userEmail`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userinfo`
--

LOCK TABLES `userinfo` WRITE;
/*!40000 ALTER TABLE `userinfo` DISABLE KEYS */;
INSERT INTO `userinfo` VALUES ('bong@naver.com','abcd','임꺽정','서울시 구로구 개봉동','2','010-1234-1234',NULL,'2021-01-15',NULL,NULL),('dong@naver.com','456789','조르디','서울시 용산구 용산동','427','010-1234-1234',NULL,'2021-01-15',NULL,NULL),('gong@naver.com','qwer1234','홍길동','서울시 서초구 서초동','12-1','010-1234-1234',NULL,'2021-01-15',NULL,NULL),('qkr@naver.com','123','박길동','서울시 중구','55','010-4567-4567',NULL,'2021-01-15',NULL,'2020123116618250.jpg'),('rong@naver.com','1234567','라이언','서울시 강남구 대치동','123-1','010-1234-1234',NULL,'2021-01-15',NULL,'2020123116618250.jpg'),('song@naver.com','123','유비','서울시 영등포구','7','010-1222-4577',NULL,'2021-01-15',NULL,NULL);
/*!40000 ALTER TABLE `userinfo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wishlist`
--

DROP TABLE IF EXISTS `wishlist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wishlist` (
  `userinfo_userEmail` varchar(15) NOT NULL,
  `product_productNo` int(11) NOT NULL,
  `wishlistInsertDate` datetime DEFAULT NULL,
  KEY `fk_wishlist_userinfo1_idx` (`userinfo_userEmail`),
  KEY `fk_wishlist_product1_idx` (`product_productNo`),
  CONSTRAINT `fk_wishlist_product1` FOREIGN KEY (`product_productNo`) REFERENCES `product` (`productNo`),
  CONSTRAINT `fk_wishlist_userinfo1` FOREIGN KEY (`userinfo_userEmail`) REFERENCES `userinfo` (`userEmail`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wishlist`
--

LOCK TABLES `wishlist` WRITE;
/*!40000 ALTER TABLE `wishlist` DISABLE KEYS */;
INSERT INTO `wishlist` VALUES ('qkr@naver.com',56,'2021-01-15 18:46:17');
/*!40000 ALTER TABLE `wishlist` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-01-16 16:47:15
