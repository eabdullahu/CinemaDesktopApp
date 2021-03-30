CREATE DATABASE  IF NOT EXISTS `defaultdb` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `defaultdb`;
-- MySQL dump 10.13  Distrib 8.0.20, for Win64 (x86_64)
--
-- Host: movies-do-user-3780416-0.a.db.ondigitalocean.com    Database: defaultdb
-- ------------------------------------------------------
-- Server version	8.0.19

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
SET @MYSQLDUMP_TEMP_LOG_BIN = @@SESSION.SQL_LOG_BIN;
SET @@SESSION.SQL_LOG_BIN= 0;

--
-- GTID state at the beginning of the backup 
--

SET @@GLOBAL.GTID_PURGED=/*!80000 '+'*/ 'b7e8855e-8c85-11ea-a748-8a4e096dce2b:1-1031';

--
-- Table structure for table `BoughtTickets`
--

DROP TABLE IF EXISTS `BoughtTickets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `BoughtTickets` (
  `BTicket_id` int NOT NULL AUTO_INCREMENT,
  `BTicket_client_id` int NOT NULL,
  `BTicket_cinema_id` int NOT NULL,
  `BTicket_hall_id` int NOT NULL,
  `BTicket_movie_id` int NOT NULL,
  `BTicket_movie_ticket_id` int NOT NULL,
  `BTickte_status` enum('onhold','paid','canceled','error','refunded') DEFAULT NULL,
  `BTicket_generated_token` varchar(1000) DEFAULT NULL,
  `BTicket_Price` decimal(4,2) NOT NULL,
  `BTicket_seat_number` decimal(4,0) NOT NULL,
  `BTicket_row` decimal(4,0) NOT NULL,
  `BTicket_date` date DEFAULT NULL,
  PRIMARY KEY (`BTicket_id`),
  KEY `BTicket_client_id` (`BTicket_client_id`),
  KEY `BTicket_cinema_id` (`BTicket_cinema_id`),
  KEY `BTicket_hall_id` (`BTicket_hall_id`),
  KEY `BTicket_movie_id` (`BTicket_movie_id`),
  KEY `BTicket_movie_ticket_id` (`BTicket_movie_ticket_id`),
  CONSTRAINT `BoughtTickets_ibfk_1` FOREIGN KEY (`BTicket_client_id`) REFERENCES `Consumator` (`Client_id`),
  CONSTRAINT `BoughtTickets_ibfk_2` FOREIGN KEY (`BTicket_cinema_id`) REFERENCES `Cinema` (`Cinema_id`),
  CONSTRAINT `BoughtTickets_ibfk_3` FOREIGN KEY (`BTicket_hall_id`) REFERENCES `Hall` (`Hall_id`),
  CONSTRAINT `BoughtTickets_ibfk_4` FOREIGN KEY (`BTicket_movie_id`) REFERENCES `Movie` (`Movie_id`),
  CONSTRAINT `BoughtTickets_ibfk_5` FOREIGN KEY (`BTicket_movie_ticket_id`) REFERENCES `Movie_Tickets` (`Ticket_id`)
) ENGINE=InnoDB AUTO_INCREMENT=295 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `BoughtTickets`
--

LOCK TABLES `BoughtTickets` WRITE;
/*!40000 ALTER TABLE `BoughtTickets` DISABLE KEYS */;
INSERT INTO `BoughtTickets` VALUES (286,1,27,15,30,32,'paid','asdasdasdasdasd',12.00,7,4,'2020-06-25'),(287,1,27,15,30,32,'paid','asdasdasdasdasd',12.00,8,4,'2020-06-25'),(288,1,27,15,30,32,'paid','asdasdasdasdasd',12.00,9,4,'2020-06-25'),(289,1,27,15,30,32,'paid','asdasdasdasdasd',12.00,11,4,'2020-06-26'),(291,1,27,15,30,32,'paid','asdasdasdasdasd',12.00,12,4,'2020-06-26'),(293,1,27,15,30,32,'paid','asdasdasdasdasd',12.00,8,2,'2020-06-26'),(294,1,27,15,30,32,'paid','asdasdasdasdasd',12.00,9,2,'2020-06-26');
/*!40000 ALTER TABLE `BoughtTickets` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Cinema`
--

DROP TABLE IF EXISTS `Cinema`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Cinema` (
  `Cinema_id` int NOT NULL AUTO_INCREMENT,
  `Cinema_name` varchar(50) DEFAULT NULL,
  `Cinema_location` varchar(50) DEFAULT NULL,
  `Cinema_phone` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`Cinema_id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Cinema`
--

LOCK TABLES `Cinema` WRITE;
/*!40000 ALTER TABLE `Cinema` DISABLE KEYS */;
INSERT INTO `Cinema` VALUES (27,'Cineplexsdss','Prishtine','044349512'),(32,'bsdvdbsjh','fsdefd','sdfds');
/*!40000 ALTER TABLE `Cinema` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Consumator`
--

DROP TABLE IF EXISTS `Consumator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Consumator` (
  `Client_id` int NOT NULL AUTO_INCREMENT,
  `Client_role` enum('0','1') DEFAULT '0',
  `Client_name` varchar(50) NOT NULL,
  `Client_surname` varchar(50) NOT NULL,
  `Client_addres` varchar(150) DEFAULT NULL,
  `Client_email` varchar(50) NOT NULL,
  `Client_password` varchar(150) NOT NULL,
  `Client_phone` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`Client_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Consumator`
--

LOCK TABLES `Consumator` WRITE;
/*!40000 ALTER TABLE `Consumator` DISABLE KEYS */;
INSERT INTO `Consumator` VALUES (1,'1','Enis','Abdullahu','Caraleva 90','ennisabbb@gmail.com','ennisabb12','44349512'),(7,'1','arminas','pakashtica','obn','armin.pakashtica99@gmail.com','2ovo220i',NULL);
/*!40000 ALTER TABLE `Consumator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Hall`
--

DROP TABLE IF EXISTS `Hall`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Hall` (
  `Hall_id` int NOT NULL AUTO_INCREMENT,
  `Hall_Cinema_id` int NOT NULL,
  `Hall_name` varchar(50) NOT NULL,
  `Hall_capacity` decimal(4,0) NOT NULL,
  `Hall_row` decimal(4,0) NOT NULL,
  `Hall_columns` decimal(4,0) NOT NULL,
  PRIMARY KEY (`Hall_id`),
  KEY `Hall_Cinema_id` (`Hall_Cinema_id`),
  CONSTRAINT `Hall_ibfk_1` FOREIGN KEY (`Hall_Cinema_id`) REFERENCES `Cinema` (`Cinema_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Hall`
--

LOCK TABLES `Hall` WRITE;
/*!40000 ALTER TABLE `Hall` DISABLE KEYS */;
INSERT INTO `Hall` VALUES (15,27,'Salla 1',0,10,20);
/*!40000 ALTER TABLE `Hall` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Invoices`
--

DROP TABLE IF EXISTS `Invoices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Invoices` (
  `Invoice_id` int NOT NULL AUTO_INCREMENT,
  `Invoice_bTicket_id` int NOT NULL,
  `Invoice_number` varchar(255) NOT NULL,
  `Invoice_generate` text,
  `Invoice_created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`Invoice_id`),
  KEY `Invoice_bTicket_id` (`Invoice_bTicket_id`),
  CONSTRAINT `Invoices_ibfk_2` FOREIGN KEY (`Invoice_bTicket_id`) REFERENCES `BoughtTickets` (`BTicket_id`)
) ENGINE=InnoDB AUTO_INCREMENT=243 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Invoices`
--

LOCK TABLES `Invoices` WRITE;
/*!40000 ALTER TABLE `Invoices` DISABLE KEYS */;
INSERT INTO `Invoices` VALUES (234,286,'E1_20200626_47','<html lang=\"en\">\n<head>\n<meta charset=\"utf-8\">\n<title>Moving booking system</title>\n<style>\n.clearfix:after {\ncontent: \"\";\ndisplay: table;\nclear: both;\n}\n\na {\ncolor: #0087C3;\ntext-decoration: none;\n}\n\nbody {\nposition: relative;\nwidth: 21cm;  \nheight: 24.7cm; \nmargin: 0 auto; \ncolor: #555555;\nbackground: #FFFFFF; \nfont-family: sans-serif; \nfont-size: 14px;\n}\n\nheader {\npadding: 10px 0;\nmargin-bottom: 20px;\nborder-bottom: 1px solid #AAAAAA;\n}\n\n#logo {\nfloat: left;\nmargin-top: 8px;\n}\n\n#logo img {\nheight: 70px;\n}\n\n#company {\nfloat: right;\ntext-align: right;\n}\n\n#details {\nmargin-bottom: 50px;\n}\n\n#client {\npadding-left: 6px;\nborder-left: 6px solid #0087C3;\nfloat: left;\n}\n#client .to {\ncolor: #777777;\n}\nh2.name {\nfont-size: 1.4em;\nfont-weight: normal;\nmargin: 0;\n}\n#invoice {\nfloat: right;\ntext-align: right;\n}\n#invoice h1 {\ncolor: #0087C3;\nfont-size: 2.4em;\nline-height: 1em;\nfont-weight: normal;\nmargin: 0  0 10px 0;\n}\n#invoice .date {\nfont-size: 1.1em;\ncolor: #777777;\n}\ntable {\nwidth: 100%;\nborder-collapse: collapse;\nborder-spacing: 0;\nmargin-bottom: 20px;\n}\ntable th, table td {\npadding: 20px;\nbackground: #EEEEEE;\ntext-align: center;\nborder-bottom: 1px solid #FFFFFF;\n}\ntable th {\nwhite-space: nowrap;        \nfont-weight: normal;\n}\ntable td {\ntext-align: right;\n}\ntable td h3{\ncolor: #57B223;\nfont-size: 1.2em;\nfont-weight: normal;\nmargin: 0 0 0.2em 0;\n}\ntable .no {\ncolor: #FFFFFF;\nfont-size: 1.6em;\nbackground: #57B223;\n}\ntable .desc {\ntext-align: left;\n}\ntable .unit {\nbackground: #DDDDDD;\n}\ntable .total {\nbackground: #57B223;\ncolor: #FFFFFF;\n}\ntable td.unit, table td.qty, table td.total {\nfont-size: 1.2em;\n}\ntable tbody tr:last-child td {\nborder: none;\n}\ntable tfoot td {\npadding: 10px 20px;\nbackground: #FFFFFF;\nborder-bottom: none;\nfont-size: 1.2em;\nwhite-space: nowrap; \nborder-top: 1px solid #AAAAAA; \n}\ntable tfoot tr:first-child td {\nborder-top: none; \n}\ntable tfoot tr:last-child td {\ncolor: #57B223;\nfont-size: 1.4em;\nborder-top: 1px solid #57B223; \n}\ntable tfoot tr td:first-child {\nborder: none;\n}\n#thanks{\nfont-size: 2em;\nmargin-bottom: 50px;\n}\n#notices{\npadding-left: 6px;\nborder-left: 6px solid #0087C3;  \n}\n#notices .notice {\nfont-size: 1.2em;\n}\nfooter {\ncolor: #777777;\nwidth: 100%;\nheight: 30px;\nposition: absolute;\nbottom: 0;\nborder-top: 1px solid #AAAAAA;\npadding: 8px 0;\ntext-align: center;\n}\n</style>\n</head>\n<body>\n<header class=\"clearfix\">\n<div id=\"logo\">\n<img src=\"logo.png\">\n</div>\n<div id=\"company\">\n<h2 class=\"name\">Movie Booking System</h2>\n<div>Cinema Address</div>\n<div>383 44349512</div>\n<div><a href=\"mailto:info@cinema.com\">info@cinema.com</a></div>\n</div>\n</header>\n<main>\n<div id=\"details\" class=\"clearfix\">\n<div id=\"client\">\n<div class=\"to\">INVOICE TO:</div>\n<h2 class=\"name\">Enis Abdullahu</h2>\n<div class=\"address\">Caraleva 90</div>\n<div class=\"email\"><a>ennisabbb@gmail.com</a></div>\n</div>\n<div id=\"invoice\">\n<h1>INVOICE E1_20200626_47</h1>\n<div class=\"date\">Date of Invoice: 26-06-2020</div>\n</div>\n</div>\n<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n<thead>\n<tr>\n<th class=\"no\">#</th>\n<th class=\"desc\">DESCRIPTION</th>\n<th class=\"unit\">UNIT PRICE</th>\n<th class=\"qty\">QUANTITY</th>\n<th class=\"total\">TOTAL</th>\n</tr>\n</thead>\n<tbody>\n<tr>\n<td class=\"no\">01</td>\n<td class=\"desc\"><h3>Blackpanther</h3></td>\n<td class=\"unit\">12.00</td>\n<td class=\"qty\">3</td>\n<td class=\"total\">12.0&euro;</td>\n</tr>\n</tbody>\n<tfoot>\n<tr>\n<td colspan=\"2\"></td>\n<td colspan=\"2\">SUBTOTAL</td>\n<td>12.0&euro;</td>\n</tr>\n<tr>\n<td colspan=\"2\"></td>\n<td colspan=\"2\">TAX 0%</td>\n<td>0&euro;</td>\n</tr>\n<tr>\n<td colspan=\"2\"></td>\n<td colspan=\"2\">GRAND TOTAL</td>\n<td>12.0&euro;</td>\n</tr>\n</tfoot>\n</table>\n<div id=\"thanks\">Thank you!</div>\n<div id=\"notices\">\n<div>NOTICE:</div>\n<div class=\"notice\">You can\'t get a refund for bought tickets.</div>\n</div>\n</main>\n<footer>\nInvoice was created on a computer and is valid without the signature and seal.\n</footer>\n</body>\n</html>',NULL),(235,287,'E1_20200626_48','<html lang=\"en\">\n<head>\n<meta charset=\"utf-8\">\n<title>Moving booking system</title>\n<style>\n.clearfix:after {\ncontent: \"\";\ndisplay: table;\nclear: both;\n}\n\na {\ncolor: #0087C3;\ntext-decoration: none;\n}\n\nbody {\nposition: relative;\nwidth: 21cm;  \nheight: 24.7cm; \nmargin: 0 auto; \ncolor: #555555;\nbackground: #FFFFFF; \nfont-family: sans-serif; \nfont-size: 14px;\n}\n\nheader {\npadding: 10px 0;\nmargin-bottom: 20px;\nborder-bottom: 1px solid #AAAAAA;\n}\n\n#logo {\nfloat: left;\nmargin-top: 8px;\n}\n\n#logo img {\nheight: 70px;\n}\n\n#company {\nfloat: right;\ntext-align: right;\n}\n\n#details {\nmargin-bottom: 50px;\n}\n\n#client {\npadding-left: 6px;\nborder-left: 6px solid #0087C3;\nfloat: left;\n}\n#client .to {\ncolor: #777777;\n}\nh2.name {\nfont-size: 1.4em;\nfont-weight: normal;\nmargin: 0;\n}\n#invoice {\nfloat: right;\ntext-align: right;\n}\n#invoice h1 {\ncolor: #0087C3;\nfont-size: 2.4em;\nline-height: 1em;\nfont-weight: normal;\nmargin: 0  0 10px 0;\n}\n#invoice .date {\nfont-size: 1.1em;\ncolor: #777777;\n}\ntable {\nwidth: 100%;\nborder-collapse: collapse;\nborder-spacing: 0;\nmargin-bottom: 20px;\n}\ntable th, table td {\npadding: 20px;\nbackground: #EEEEEE;\ntext-align: center;\nborder-bottom: 1px solid #FFFFFF;\n}\ntable th {\nwhite-space: nowrap;        \nfont-weight: normal;\n}\ntable td {\ntext-align: right;\n}\ntable td h3{\ncolor: #57B223;\nfont-size: 1.2em;\nfont-weight: normal;\nmargin: 0 0 0.2em 0;\n}\ntable .no {\ncolor: #FFFFFF;\nfont-size: 1.6em;\nbackground: #57B223;\n}\ntable .desc {\ntext-align: left;\n}\ntable .unit {\nbackground: #DDDDDD;\n}\ntable .total {\nbackground: #57B223;\ncolor: #FFFFFF;\n}\ntable td.unit, table td.qty, table td.total {\nfont-size: 1.2em;\n}\ntable tbody tr:last-child td {\nborder: none;\n}\ntable tfoot td {\npadding: 10px 20px;\nbackground: #FFFFFF;\nborder-bottom: none;\nfont-size: 1.2em;\nwhite-space: nowrap; \nborder-top: 1px solid #AAAAAA; \n}\ntable tfoot tr:first-child td {\nborder-top: none; \n}\ntable tfoot tr:last-child td {\ncolor: #57B223;\nfont-size: 1.4em;\nborder-top: 1px solid #57B223; \n}\ntable tfoot tr td:first-child {\nborder: none;\n}\n#thanks{\nfont-size: 2em;\nmargin-bottom: 50px;\n}\n#notices{\npadding-left: 6px;\nborder-left: 6px solid #0087C3;  \n}\n#notices .notice {\nfont-size: 1.2em;\n}\nfooter {\ncolor: #777777;\nwidth: 100%;\nheight: 30px;\nposition: absolute;\nbottom: 0;\nborder-top: 1px solid #AAAAAA;\npadding: 8px 0;\ntext-align: center;\n}\n</style>\n</head>\n<body>\n<header class=\"clearfix\">\n<div id=\"logo\">\n<img src=\"logo.png\">\n</div>\n<div id=\"company\">\n<h2 class=\"name\">Movie Booking System</h2>\n<div>Cinema Address</div>\n<div>383 44349512</div>\n<div><a href=\"mailto:info@cinema.com\">info@cinema.com</a></div>\n</div>\n</header>\n<main>\n<div id=\"details\" class=\"clearfix\">\n<div id=\"client\">\n<div class=\"to\">INVOICE TO:</div>\n<h2 class=\"name\">Enis Abdullahu</h2>\n<div class=\"address\">Caraleva 90</div>\n<div class=\"email\"><a>ennisabbb@gmail.com</a></div>\n</div>\n<div id=\"invoice\">\n<h1>INVOICE E1_20200626_48</h1>\n<div class=\"date\">Date of Invoice: 26-06-2020</div>\n</div>\n</div>\n<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n<thead>\n<tr>\n<th class=\"no\">#</th>\n<th class=\"desc\">DESCRIPTION</th>\n<th class=\"unit\">UNIT PRICE</th>\n<th class=\"qty\">QUANTITY</th>\n<th class=\"total\">TOTAL</th>\n</tr>\n</thead>\n<tbody>\n<tr>\n<td class=\"no\">01</td>\n<td class=\"desc\"><h3>Blackpanther</h3></td>\n<td class=\"unit\">12.00</td>\n<td class=\"qty\">0</td>\n<td class=\"total\">12.0&euro;</td>\n</tr>\n</tbody>\n<tfoot>\n<tr>\n<td colspan=\"2\"></td>\n<td colspan=\"2\">SUBTOTAL</td>\n<td>12.0&euro;</td>\n</tr>\n<tr>\n<td colspan=\"2\"></td>\n<td colspan=\"2\">TAX 0%</td>\n<td>0&euro;</td>\n</tr>\n<tr>\n<td colspan=\"2\"></td>\n<td colspan=\"2\">GRAND TOTAL</td>\n<td>12.0&euro;</td>\n</tr>\n</tfoot>\n</table>\n<div id=\"thanks\">Thank you!</div>\n<div id=\"notices\">\n<div>NOTICE:</div>\n<div class=\"notice\">You can\'t get a refund for bought tickets.</div>\n</div>\n</main>\n<footer>\nInvoice was created on a computer and is valid without the signature and seal.\n</footer>\n</body>\n</html>',NULL),(236,288,'E1_20200626_49','<html lang=\"en\">\n<head>\n<meta charset=\"utf-8\">\n<title>Moving booking system</title>\n<style>\n.clearfix:after {\ncontent: \"\";\ndisplay: table;\nclear: both;\n}\n\na {\ncolor: #0087C3;\ntext-decoration: none;\n}\n\nbody {\nposition: relative;\nwidth: 21cm;  \nheight: 24.7cm; \nmargin: 0 auto; \ncolor: #555555;\nbackground: #FFFFFF; \nfont-family: sans-serif; \nfont-size: 14px;\n}\n\nheader {\npadding: 10px 0;\nmargin-bottom: 20px;\nborder-bottom: 1px solid #AAAAAA;\n}\n\n#logo {\nfloat: left;\nmargin-top: 8px;\n}\n\n#logo img {\nheight: 70px;\n}\n\n#company {\nfloat: right;\ntext-align: right;\n}\n\n#details {\nmargin-bottom: 50px;\n}\n\n#client {\npadding-left: 6px;\nborder-left: 6px solid #0087C3;\nfloat: left;\n}\n#client .to {\ncolor: #777777;\n}\nh2.name {\nfont-size: 1.4em;\nfont-weight: normal;\nmargin: 0;\n}\n#invoice {\nfloat: right;\ntext-align: right;\n}\n#invoice h1 {\ncolor: #0087C3;\nfont-size: 2.4em;\nline-height: 1em;\nfont-weight: normal;\nmargin: 0  0 10px 0;\n}\n#invoice .date {\nfont-size: 1.1em;\ncolor: #777777;\n}\ntable {\nwidth: 100%;\nborder-collapse: collapse;\nborder-spacing: 0;\nmargin-bottom: 20px;\n}\ntable th, table td {\npadding: 20px;\nbackground: #EEEEEE;\ntext-align: center;\nborder-bottom: 1px solid #FFFFFF;\n}\ntable th {\nwhite-space: nowrap;        \nfont-weight: normal;\n}\ntable td {\ntext-align: right;\n}\ntable td h3{\ncolor: #57B223;\nfont-size: 1.2em;\nfont-weight: normal;\nmargin: 0 0 0.2em 0;\n}\ntable .no {\ncolor: #FFFFFF;\nfont-size: 1.6em;\nbackground: #57B223;\n}\ntable .desc {\ntext-align: left;\n}\ntable .unit {\nbackground: #DDDDDD;\n}\ntable .total {\nbackground: #57B223;\ncolor: #FFFFFF;\n}\ntable td.unit, table td.qty, table td.total {\nfont-size: 1.2em;\n}\ntable tbody tr:last-child td {\nborder: none;\n}\ntable tfoot td {\npadding: 10px 20px;\nbackground: #FFFFFF;\nborder-bottom: none;\nfont-size: 1.2em;\nwhite-space: nowrap; \nborder-top: 1px solid #AAAAAA; \n}\ntable tfoot tr:first-child td {\nborder-top: none; \n}\ntable tfoot tr:last-child td {\ncolor: #57B223;\nfont-size: 1.4em;\nborder-top: 1px solid #57B223; \n}\ntable tfoot tr td:first-child {\nborder: none;\n}\n#thanks{\nfont-size: 2em;\nmargin-bottom: 50px;\n}\n#notices{\npadding-left: 6px;\nborder-left: 6px solid #0087C3;  \n}\n#notices .notice {\nfont-size: 1.2em;\n}\nfooter {\ncolor: #777777;\nwidth: 100%;\nheight: 30px;\nposition: absolute;\nbottom: 0;\nborder-top: 1px solid #AAAAAA;\npadding: 8px 0;\ntext-align: center;\n}\n</style>\n</head>\n<body>\n<header class=\"clearfix\">\n<div id=\"logo\">\n<img src=\"logo.png\">\n</div>\n<div id=\"company\">\n<h2 class=\"name\">Movie Booking System</h2>\n<div>Cinema Address</div>\n<div>383 44349512</div>\n<div><a href=\"mailto:info@cinema.com\">info@cinema.com</a></div>\n</div>\n</header>\n<main>\n<div id=\"details\" class=\"clearfix\">\n<div id=\"client\">\n<div class=\"to\">INVOICE TO:</div>\n<h2 class=\"name\">Enis Abdullahu</h2>\n<div class=\"address\">Caraleva 90</div>\n<div class=\"email\"><a>ennisabbb@gmail.com</a></div>\n</div>\n<div id=\"invoice\">\n<h1>INVOICE E1_20200626_49</h1>\n<div class=\"date\">Date of Invoice: 26-06-2020</div>\n</div>\n</div>\n<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n<thead>\n<tr>\n<th class=\"no\">#</th>\n<th class=\"desc\">DESCRIPTION</th>\n<th class=\"unit\">UNIT PRICE</th>\n<th class=\"qty\">QUANTITY</th>\n<th class=\"total\">TOTAL</th>\n</tr>\n</thead>\n<tbody>\n<tr>\n<td class=\"no\">01</td>\n<td class=\"desc\"><h3>Blackpanther</h3></td>\n<td class=\"unit\">12.00</td>\n<td class=\"qty\">0</td>\n<td class=\"total\">12.0&euro;</td>\n</tr>\n</tbody>\n<tfoot>\n<tr>\n<td colspan=\"2\"></td>\n<td colspan=\"2\">SUBTOTAL</td>\n<td>12.0&euro;</td>\n</tr>\n<tr>\n<td colspan=\"2\"></td>\n<td colspan=\"2\">TAX 0%</td>\n<td>0&euro;</td>\n</tr>\n<tr>\n<td colspan=\"2\"></td>\n<td colspan=\"2\">GRAND TOTAL</td>\n<td>12.0&euro;</td>\n</tr>\n</tfoot>\n</table>\n<div id=\"thanks\">Thank you!</div>\n<div id=\"notices\">\n<div>NOTICE:</div>\n<div class=\"notice\">You can\'t get a refund for bought tickets.</div>\n</div>\n</main>\n<footer>\nInvoice was created on a computer and is valid without the signature and seal.\n</footer>\n</body>\n</html>',NULL),(237,289,'E1_20200626_411','<html lang=\"en\">\n<head>\n<meta charset=\"utf-8\">\n<title>Moving booking system</title>\n<style>\n.clearfix:after {\ncontent: \"\";\ndisplay: table;\nclear: both;\n}\n\na {\ncolor: #0087C3;\ntext-decoration: none;\n}\n\nbody {\nposition: relative;\nwidth: 21cm;  \nheight: 24.7cm; \nmargin: 0 auto; \ncolor: #555555;\nbackground: #FFFFFF; \nfont-family: sans-serif; \nfont-size: 14px;\n}\n\nheader {\npadding: 10px 0;\nmargin-bottom: 20px;\nborder-bottom: 1px solid #AAAAAA;\n}\n\n#logo {\nfloat: left;\nmargin-top: 8px;\n}\n\n#logo img {\nheight: 70px;\n}\n\n#company {\nfloat: right;\ntext-align: right;\n}\n\n#details {\nmargin-bottom: 50px;\n}\n\n#client {\npadding-left: 6px;\nborder-left: 6px solid #0087C3;\nfloat: left;\n}\n#client .to {\ncolor: #777777;\n}\nh2.name {\nfont-size: 1.4em;\nfont-weight: normal;\nmargin: 0;\n}\n#invoice {\nfloat: right;\ntext-align: right;\n}\n#invoice h1 {\ncolor: #0087C3;\nfont-size: 2.4em;\nline-height: 1em;\nfont-weight: normal;\nmargin: 0  0 10px 0;\n}\n#invoice .date {\nfont-size: 1.1em;\ncolor: #777777;\n}\ntable {\nwidth: 100%;\nborder-collapse: collapse;\nborder-spacing: 0;\nmargin-bottom: 20px;\n}\ntable th, table td {\npadding: 20px;\nbackground: #EEEEEE;\ntext-align: center;\nborder-bottom: 1px solid #FFFFFF;\n}\ntable th {\nwhite-space: nowrap;        \nfont-weight: normal;\n}\ntable td {\ntext-align: right;\n}\ntable td h3{\ncolor: #57B223;\nfont-size: 1.2em;\nfont-weight: normal;\nmargin: 0 0 0.2em 0;\n}\ntable .no {\ncolor: #FFFFFF;\nfont-size: 1.6em;\nbackground: #57B223;\n}\ntable .desc {\ntext-align: left;\n}\ntable .unit {\nbackground: #DDDDDD;\n}\ntable .total {\nbackground: #57B223;\ncolor: #FFFFFF;\n}\ntable td.unit, table td.qty, table td.total {\nfont-size: 1.2em;\n}\ntable tbody tr:last-child td {\nborder: none;\n}\ntable tfoot td {\npadding: 10px 20px;\nbackground: #FFFFFF;\nborder-bottom: none;\nfont-size: 1.2em;\nwhite-space: nowrap; \nborder-top: 1px solid #AAAAAA; \n}\ntable tfoot tr:first-child td {\nborder-top: none; \n}\ntable tfoot tr:last-child td {\ncolor: #57B223;\nfont-size: 1.4em;\nborder-top: 1px solid #57B223; \n}\ntable tfoot tr td:first-child {\nborder: none;\n}\n#thanks{\nfont-size: 2em;\nmargin-bottom: 50px;\n}\n#notices{\npadding-left: 6px;\nborder-left: 6px solid #0087C3;  \n}\n#notices .notice {\nfont-size: 1.2em;\n}\nfooter {\ncolor: #777777;\nwidth: 100%;\nheight: 30px;\nposition: absolute;\nbottom: 0;\nborder-top: 1px solid #AAAAAA;\npadding: 8px 0;\ntext-align: center;\n}\n</style>\n</head>\n<body>\n<header class=\"clearfix\">\n<div id=\"logo\">\n<img src=\"logo.png\">\n</div>\n<div id=\"company\">\n<h2 class=\"name\">Movie Booking System</h2>\n<div>Cinema Address</div>\n<div>383 44349512</div>\n<div><a href=\"mailto:info@cinema.com\">info@cinema.com</a></div>\n</div>\n</header>\n<main>\n<div id=\"details\" class=\"clearfix\">\n<div id=\"client\">\n<div class=\"to\">INVOICE TO:</div>\n<h2 class=\"name\">Enis Abdullahu</h2>\n<div class=\"address\">Caraleva 90</div>\n<div class=\"email\"><a>ennisabbb@gmail.com</a></div>\n</div>\n<div id=\"invoice\">\n<h1>INVOICE E1_20200626_411</h1>\n<div class=\"date\">Date of Invoice: 26-06-2020</div>\n</div>\n</div>\n<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n<thead>\n<tr>\n<th class=\"no\">#</th>\n<th class=\"desc\">DESCRIPTION</th>\n<th class=\"unit\">UNIT PRICE</th>\n<th class=\"qty\">QUANTITY</th>\n<th class=\"total\">TOTAL</th>\n</tr>\n</thead>\n<tbody>\n<tr>\n<td class=\"no\">01</td>\n<td class=\"desc\"><h3>Blackpanther</h3></td>\n<td class=\"unit\">12.00</td>\n<td class=\"qty\">2</td>\n<td class=\"total\">12.0&euro;</td>\n</tr>\n</tbody>\n<tfoot>\n<tr>\n<td colspan=\"2\"></td>\n<td colspan=\"2\">SUBTOTAL</td>\n<td>12.0&euro;</td>\n</tr>\n<tr>\n<td colspan=\"2\"></td>\n<td colspan=\"2\">TAX 0%</td>\n<td>0&euro;</td>\n</tr>\n<tr>\n<td colspan=\"2\"></td>\n<td colspan=\"2\">GRAND TOTAL</td>\n<td>12.0&euro;</td>\n</tr>\n</tfoot>\n</table>\n<div id=\"thanks\">Thank you!</div>\n<div id=\"notices\">\n<div>NOTICE:</div>\n<div class=\"notice\">You can\'t get a refund for bought tickets.</div>\n</div>\n</main>\n<footer>\nInvoice was created on a computer and is valid without the signature and seal.\n</footer>\n</body>\n</html>',NULL),(239,291,'E1_20200626_412','<html lang=\"en\">\n<head>\n<meta charset=\"utf-8\">\n<title>Moving booking system</title>\n<style>\n.clearfix:after {\ncontent: \"\";\ndisplay: table;\nclear: both;\n}\n\na {\ncolor: #0087C3;\ntext-decoration: none;\n}\n\nbody {\nposition: relative;\nwidth: 21cm;  \nheight: 24.7cm; \nmargin: 0 auto; \ncolor: #555555;\nbackground: #FFFFFF; \nfont-family: sans-serif; \nfont-size: 14px;\n}\n\nheader {\npadding: 10px 0;\nmargin-bottom: 20px;\nborder-bottom: 1px solid #AAAAAA;\n}\n\n#logo {\nfloat: left;\nmargin-top: 8px;\n}\n\n#logo img {\nheight: 70px;\n}\n\n#company {\nfloat: right;\ntext-align: right;\n}\n\n#details {\nmargin-bottom: 50px;\n}\n\n#client {\npadding-left: 6px;\nborder-left: 6px solid #0087C3;\nfloat: left;\n}\n#client .to {\ncolor: #777777;\n}\nh2.name {\nfont-size: 1.4em;\nfont-weight: normal;\nmargin: 0;\n}\n#invoice {\nfloat: right;\ntext-align: right;\n}\n#invoice h1 {\ncolor: #0087C3;\nfont-size: 2.4em;\nline-height: 1em;\nfont-weight: normal;\nmargin: 0  0 10px 0;\n}\n#invoice .date {\nfont-size: 1.1em;\ncolor: #777777;\n}\ntable {\nwidth: 100%;\nborder-collapse: collapse;\nborder-spacing: 0;\nmargin-bottom: 20px;\n}\ntable th, table td {\npadding: 20px;\nbackground: #EEEEEE;\ntext-align: center;\nborder-bottom: 1px solid #FFFFFF;\n}\ntable th {\nwhite-space: nowrap;        \nfont-weight: normal;\n}\ntable td {\ntext-align: right;\n}\ntable td h3{\ncolor: #57B223;\nfont-size: 1.2em;\nfont-weight: normal;\nmargin: 0 0 0.2em 0;\n}\ntable .no {\ncolor: #FFFFFF;\nfont-size: 1.6em;\nbackground: #57B223;\n}\ntable .desc {\ntext-align: left;\n}\ntable .unit {\nbackground: #DDDDDD;\n}\ntable .total {\nbackground: #57B223;\ncolor: #FFFFFF;\n}\ntable td.unit, table td.qty, table td.total {\nfont-size: 1.2em;\n}\ntable tbody tr:last-child td {\nborder: none;\n}\ntable tfoot td {\npadding: 10px 20px;\nbackground: #FFFFFF;\nborder-bottom: none;\nfont-size: 1.2em;\nwhite-space: nowrap; \nborder-top: 1px solid #AAAAAA; \n}\ntable tfoot tr:first-child td {\nborder-top: none; \n}\ntable tfoot tr:last-child td {\ncolor: #57B223;\nfont-size: 1.4em;\nborder-top: 1px solid #57B223; \n}\ntable tfoot tr td:first-child {\nborder: none;\n}\n#thanks{\nfont-size: 2em;\nmargin-bottom: 50px;\n}\n#notices{\npadding-left: 6px;\nborder-left: 6px solid #0087C3;  \n}\n#notices .notice {\nfont-size: 1.2em;\n}\nfooter {\ncolor: #777777;\nwidth: 100%;\nheight: 30px;\nposition: absolute;\nbottom: 0;\nborder-top: 1px solid #AAAAAA;\npadding: 8px 0;\ntext-align: center;\n}\n</style>\n</head>\n<body>\n<header class=\"clearfix\">\n<div id=\"logo\">\n<img src=\"logo.png\">\n</div>\n<div id=\"company\">\n<h2 class=\"name\">Movie Booking System</h2>\n<div>Cinema Address</div>\n<div>383 44349512</div>\n<div><a href=\"mailto:info@cinema.com\">info@cinema.com</a></div>\n</div>\n</header>\n<main>\n<div id=\"details\" class=\"clearfix\">\n<div id=\"client\">\n<div class=\"to\">INVOICE TO:</div>\n<h2 class=\"name\">Enis Abdullahu</h2>\n<div class=\"address\">Caraleva 90</div>\n<div class=\"email\"><a>ennisabbb@gmail.com</a></div>\n</div>\n<div id=\"invoice\">\n<h1>INVOICE E1_20200626_412</h1>\n<div class=\"date\">Date of Invoice: 26-06-2020</div>\n</div>\n</div>\n<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n<thead>\n<tr>\n<th class=\"no\">#</th>\n<th class=\"desc\">DESCRIPTION</th>\n<th class=\"unit\">UNIT PRICE</th>\n<th class=\"qty\">QUANTITY</th>\n<th class=\"total\">TOTAL</th>\n</tr>\n</thead>\n<tbody>\n<tr>\n<td class=\"no\">01</td>\n<td class=\"desc\"><h3>Blackpanther</h3></td>\n<td class=\"unit\">12.00</td>\n<td class=\"qty\">2</td>\n<td class=\"total\">12.0&euro;</td>\n</tr>\n</tbody>\n<tfoot>\n<tr>\n<td colspan=\"2\"></td>\n<td colspan=\"2\">SUBTOTAL</td>\n<td>12.0&euro;</td>\n</tr>\n<tr>\n<td colspan=\"2\"></td>\n<td colspan=\"2\">TAX 0%</td>\n<td>0&euro;</td>\n</tr>\n<tr>\n<td colspan=\"2\"></td>\n<td colspan=\"2\">GRAND TOTAL</td>\n<td>12.0&euro;</td>\n</tr>\n</tfoot>\n</table>\n<div id=\"thanks\">Thank you!</div>\n<div id=\"notices\">\n<div>NOTICE:</div>\n<div class=\"notice\">You can\'t get a refund for bought tickets.</div>\n</div>\n</main>\n<footer>\nInvoice was created on a computer and is valid without the signature and seal.\n</footer>\n</body>\n</html>',NULL),(241,293,'E1_20200626_28','<html lang=\"en\">\n<head>\n<meta charset=\"utf-8\">\n<title>Moving booking system</title>\n<style>\n.clearfix:after {\ncontent: \"\";\ndisplay: table;\nclear: both;\n}\n\na {\ncolor: #0087C3;\ntext-decoration: none;\n}\n\nbody {\nposition: relative;\nwidth: 21cm;  \nheight: 24.7cm; \nmargin: 0 auto; \ncolor: #555555;\nbackground: #FFFFFF; \nfont-family: sans-serif; \nfont-size: 14px;\n}\n\nheader {\npadding: 10px 0;\nmargin-bottom: 20px;\nborder-bottom: 1px solid #AAAAAA;\n}\n\n#logo {\nfloat: left;\nmargin-top: 8px;\n}\n\n#logo img {\nheight: 70px;\n}\n\n#company {\nfloat: right;\ntext-align: right;\n}\n\n#details {\nmargin-bottom: 50px;\n}\n\n#client {\npadding-left: 6px;\nborder-left: 6px solid #0087C3;\nfloat: left;\n}\n#client .to {\ncolor: #777777;\n}\nh2.name {\nfont-size: 1.4em;\nfont-weight: normal;\nmargin: 0;\n}\n#invoice {\nfloat: right;\ntext-align: right;\n}\n#invoice h1 {\ncolor: #0087C3;\nfont-size: 2.4em;\nline-height: 1em;\nfont-weight: normal;\nmargin: 0  0 10px 0;\n}\n#invoice .date {\nfont-size: 1.1em;\ncolor: #777777;\n}\ntable {\nwidth: 100%;\nborder-collapse: collapse;\nborder-spacing: 0;\nmargin-bottom: 20px;\n}\ntable th, table td {\npadding: 20px;\nbackground: #EEEEEE;\ntext-align: center;\nborder-bottom: 1px solid #FFFFFF;\n}\ntable th {\nwhite-space: nowrap;        \nfont-weight: normal;\n}\ntable td {\ntext-align: right;\n}\ntable td h3{\ncolor: #57B223;\nfont-size: 1.2em;\nfont-weight: normal;\nmargin: 0 0 0.2em 0;\n}\ntable .no {\ncolor: #FFFFFF;\nfont-size: 1.6em;\nbackground: #57B223;\n}\ntable .desc {\ntext-align: left;\n}\ntable .unit {\nbackground: #DDDDDD;\n}\ntable .total {\nbackground: #57B223;\ncolor: #FFFFFF;\n}\ntable td.unit, table td.qty, table td.total {\nfont-size: 1.2em;\n}\ntable tbody tr:last-child td {\nborder: none;\n}\ntable tfoot td {\npadding: 10px 20px;\nbackground: #FFFFFF;\nborder-bottom: none;\nfont-size: 1.2em;\nwhite-space: nowrap; \nborder-top: 1px solid #AAAAAA; \n}\ntable tfoot tr:first-child td {\nborder-top: none; \n}\ntable tfoot tr:last-child td {\ncolor: #57B223;\nfont-size: 1.4em;\nborder-top: 1px solid #57B223; \n}\ntable tfoot tr td:first-child {\nborder: none;\n}\n#thanks{\nfont-size: 2em;\nmargin-bottom: 50px;\n}\n#notices{\npadding-left: 6px;\nborder-left: 6px solid #0087C3;  \n}\n#notices .notice {\nfont-size: 1.2em;\n}\nfooter {\ncolor: #777777;\nwidth: 100%;\nheight: 30px;\nposition: absolute;\nbottom: 0;\nborder-top: 1px solid #AAAAAA;\npadding: 8px 0;\ntext-align: center;\n}\n</style>\n</head>\n<body>\n<header class=\"clearfix\">\n<div id=\"logo\">\n<img src=\"logo.png\">\n</div>\n<div id=\"company\">\n<h2 class=\"name\">Movie Booking System</h2>\n<div>Cinema Address</div>\n<div>383 44349512</div>\n<div><a href=\"mailto:info@cinema.com\">info@cinema.com</a></div>\n</div>\n</header>\n<main>\n<div id=\"details\" class=\"clearfix\">\n<div id=\"client\">\n<div class=\"to\">INVOICE TO:</div>\n<h2 class=\"name\">Enis Abdullahu</h2>\n<div class=\"address\">Caraleva 90</div>\n<div class=\"email\"><a>ennisabbb@gmail.com</a></div>\n</div>\n<div id=\"invoice\">\n<h1>INVOICE E1_20200626_28</h1>\n<div class=\"date\">Date of Invoice: 26-06-2020</div>\n</div>\n</div>\n<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n<thead>\n<tr>\n<th class=\"no\">#</th>\n<th class=\"desc\">DESCRIPTION</th>\n<th class=\"unit\">UNIT PRICE</th>\n<th class=\"qty\">QUANTITY</th>\n<th class=\"total\">TOTAL</th>\n</tr>\n</thead>\n<tbody>\n<tr>\n<td class=\"no\">01</td>\n<td class=\"desc\"><h3>Blackpanther</h3></td>\n<td class=\"unit\">12.00</td>\n<td class=\"qty\">2</td>\n<td class=\"total\">12.0&euro;</td>\n</tr>\n</tbody>\n<tfoot>\n<tr>\n<td colspan=\"2\"></td>\n<td colspan=\"2\">SUBTOTAL</td>\n<td>12.0&euro;</td>\n</tr>\n<tr>\n<td colspan=\"2\"></td>\n<td colspan=\"2\">TAX 0%</td>\n<td>0&euro;</td>\n</tr>\n<tr>\n<td colspan=\"2\"></td>\n<td colspan=\"2\">GRAND TOTAL</td>\n<td>12.0&euro;</td>\n</tr>\n</tfoot>\n</table>\n<div id=\"thanks\">Thank you!</div>\n<div id=\"notices\">\n<div>NOTICE:</div>\n<div class=\"notice\">You can\'t get a refund for bought tickets.</div>\n</div>\n</main>\n<footer>\nInvoice was created on a computer and is valid without the signature and seal.\n</footer>\n</body>\n</html>',NULL),(242,294,'E1_20200626_29','<html lang=\"en\">\n<head>\n<meta charset=\"utf-8\">\n<title>Moving booking system</title>\n<style>\n.clearfix:after {\ncontent: \"\";\ndisplay: table;\nclear: both;\n}\n\na {\ncolor: #0087C3;\ntext-decoration: none;\n}\n\nbody {\nposition: relative;\nwidth: 21cm;  \nheight: 24.7cm; \nmargin: 0 auto; \ncolor: #555555;\nbackground: #FFFFFF; \nfont-family: sans-serif; \nfont-size: 14px;\n}\n\nheader {\npadding: 10px 0;\nmargin-bottom: 20px;\nborder-bottom: 1px solid #AAAAAA;\n}\n\n#logo {\nfloat: left;\nmargin-top: 8px;\n}\n\n#logo img {\nheight: 70px;\n}\n\n#company {\nfloat: right;\ntext-align: right;\n}\n\n#details {\nmargin-bottom: 50px;\n}\n\n#client {\npadding-left: 6px;\nborder-left: 6px solid #0087C3;\nfloat: left;\n}\n#client .to {\ncolor: #777777;\n}\nh2.name {\nfont-size: 1.4em;\nfont-weight: normal;\nmargin: 0;\n}\n#invoice {\nfloat: right;\ntext-align: right;\n}\n#invoice h1 {\ncolor: #0087C3;\nfont-size: 2.4em;\nline-height: 1em;\nfont-weight: normal;\nmargin: 0  0 10px 0;\n}\n#invoice .date {\nfont-size: 1.1em;\ncolor: #777777;\n}\ntable {\nwidth: 100%;\nborder-collapse: collapse;\nborder-spacing: 0;\nmargin-bottom: 20px;\n}\ntable th, table td {\npadding: 20px;\nbackground: #EEEEEE;\ntext-align: center;\nborder-bottom: 1px solid #FFFFFF;\n}\ntable th {\nwhite-space: nowrap;        \nfont-weight: normal;\n}\ntable td {\ntext-align: right;\n}\ntable td h3{\ncolor: #57B223;\nfont-size: 1.2em;\nfont-weight: normal;\nmargin: 0 0 0.2em 0;\n}\ntable .no {\ncolor: #FFFFFF;\nfont-size: 1.6em;\nbackground: #57B223;\n}\ntable .desc {\ntext-align: left;\n}\ntable .unit {\nbackground: #DDDDDD;\n}\ntable .total {\nbackground: #57B223;\ncolor: #FFFFFF;\n}\ntable td.unit, table td.qty, table td.total {\nfont-size: 1.2em;\n}\ntable tbody tr:last-child td {\nborder: none;\n}\ntable tfoot td {\npadding: 10px 20px;\nbackground: #FFFFFF;\nborder-bottom: none;\nfont-size: 1.2em;\nwhite-space: nowrap; \nborder-top: 1px solid #AAAAAA; \n}\ntable tfoot tr:first-child td {\nborder-top: none; \n}\ntable tfoot tr:last-child td {\ncolor: #57B223;\nfont-size: 1.4em;\nborder-top: 1px solid #57B223; \n}\ntable tfoot tr td:first-child {\nborder: none;\n}\n#thanks{\nfont-size: 2em;\nmargin-bottom: 50px;\n}\n#notices{\npadding-left: 6px;\nborder-left: 6px solid #0087C3;  \n}\n#notices .notice {\nfont-size: 1.2em;\n}\nfooter {\ncolor: #777777;\nwidth: 100%;\nheight: 30px;\nposition: absolute;\nbottom: 0;\nborder-top: 1px solid #AAAAAA;\npadding: 8px 0;\ntext-align: center;\n}\n</style>\n</head>\n<body>\n<header class=\"clearfix\">\n<div id=\"logo\">\n<img src=\"logo.png\">\n</div>\n<div id=\"company\">\n<h2 class=\"name\">Movie Booking System</h2>\n<div>Cinema Address</div>\n<div>383 44349512</div>\n<div><a href=\"mailto:info@cinema.com\">info@cinema.com</a></div>\n</div>\n</header>\n<main>\n<div id=\"details\" class=\"clearfix\">\n<div id=\"client\">\n<div class=\"to\">INVOICE TO:</div>\n<h2 class=\"name\">Enis Abdullahu</h2>\n<div class=\"address\">Caraleva 90</div>\n<div class=\"email\"><a>ennisabbb@gmail.com</a></div>\n</div>\n<div id=\"invoice\">\n<h1>INVOICE E1_20200626_29</h1>\n<div class=\"date\">Date of Invoice: 26-06-2020</div>\n</div>\n</div>\n<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n<thead>\n<tr>\n<th class=\"no\">#</th>\n<th class=\"desc\">DESCRIPTION</th>\n<th class=\"unit\">UNIT PRICE</th>\n<th class=\"qty\">QUANTITY</th>\n<th class=\"total\">TOTAL</th>\n</tr>\n</thead>\n<tbody>\n<tr>\n<td class=\"no\">01</td>\n<td class=\"desc\"><h3>Blackpanther</h3></td>\n<td class=\"unit\">12.00</td>\n<td class=\"qty\">2</td>\n<td class=\"total\">12.0&euro;</td>\n</tr>\n</tbody>\n<tfoot>\n<tr>\n<td colspan=\"2\"></td>\n<td colspan=\"2\">SUBTOTAL</td>\n<td>12.0&euro;</td>\n</tr>\n<tr>\n<td colspan=\"2\"></td>\n<td colspan=\"2\">TAX 0%</td>\n<td>0&euro;</td>\n</tr>\n<tr>\n<td colspan=\"2\"></td>\n<td colspan=\"2\">GRAND TOTAL</td>\n<td>12.0&euro;</td>\n</tr>\n</tfoot>\n</table>\n<div id=\"thanks\">Thank you!</div>\n<div id=\"notices\">\n<div>NOTICE:</div>\n<div class=\"notice\">You can\'t get a refund for bought tickets.</div>\n</div>\n</main>\n<footer>\nInvoice was created on a computer and is valid without the signature and seal.\n</footer>\n</body>\n</html>',NULL);
/*!40000 ALTER TABLE `Invoices` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Movie`
--

DROP TABLE IF EXISTS `Movie`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Movie` (
  `Movie_id` int NOT NULL AUTO_INCREMENT,
  `Movie_Cinema_id` int NOT NULL,
  `Movie_showToSlide` enum('0','1') DEFAULT '0',
  `Movie_name` varchar(50) NOT NULL,
  `Movie_Status` enum('playing','comingsoon') DEFAULT 'playing',
  `Movie_duration` varchar(255) NOT NULL,
  `Movie_category` varchar(255) DEFAULT NULL,
  `Movie_rating` decimal(4,2) DEFAULT NULL,
  `Movie_awards` varchar(190) DEFAULT NULL,
  `Movie_showingFromDate` date NOT NULL,
  `Movie_showingToDate` date DEFAULT NULL,
  `Movie_icon` varchar(500) DEFAULT NULL,
  `Movie_image` varchar(500) DEFAULT NULL,
  `Movie_trailer` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`Movie_id`),
  KEY `Movie_Cinema_id` (`Movie_Cinema_id`),
  CONSTRAINT `Movie_ibfk_1` FOREIGN KEY (`Movie_Cinema_id`) REFERENCES `Cinema` (`Cinema_id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Movie`
--

LOCK TABLES `Movie` WRITE;
/*!40000 ALTER TABLE `Movie` DISABLE KEYS */;
INSERT INTO `Movie` VALUES (30,27,'1','Blackpanther','comingsoon','120:24h','Action, Fantasy, SC-FI',5.00,NULL,'2020-06-27','2020-07-07','blackpanther.jpg','blackpantherbanner.jpg',NULL);
/*!40000 ALTER TABLE `Movie` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Movie_Tickets`
--

DROP TABLE IF EXISTS `Movie_Tickets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Movie_Tickets` (
  `Ticket_id` int NOT NULL AUTO_INCREMENT,
  `Movie_id` int NOT NULL,
  `Ticket_hall_id` int NOT NULL,
  `Ticket_play_date` date DEFAULT NULL,
  `Ticket_play_hour` time DEFAULT NULL,
  `Tickets_available` decimal(4,0) DEFAULT NULL,
  `Tickets_sold` decimal(4,0) DEFAULT NULL,
  `Ticket_price` decimal(4,2) DEFAULT NULL,
  PRIMARY KEY (`Ticket_id`),
  KEY `Movie_id` (`Movie_id`),
  KEY `Ticket_hall_id` (`Ticket_hall_id`),
  CONSTRAINT `Movie_Tickets_ibfk_1` FOREIGN KEY (`Movie_id`) REFERENCES `Movie` (`Movie_id`),
  CONSTRAINT `Movie_Tickets_ibfk_2` FOREIGN KEY (`Ticket_hall_id`) REFERENCES `Hall` (`Hall_id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Movie_Tickets`
--

LOCK TABLES `Movie_Tickets` WRITE;
/*!40000 ALTER TABLE `Movie_Tickets` DISABLE KEYS */;
INSERT INTO `Movie_Tickets` VALUES (30,30,15,'2020-06-28','16:00:00',200,0,5.30),(31,30,15,'2020-06-29','16:00:00',200,0,2.90),(32,30,15,'2020-06-02','18:00:00',193,7,12.00),(33,30,15,'2020-06-01','22:30:00',200,0,12.00),(34,30,15,'2020-05-31','19:45:00',200,0,11.00),(35,30,15,'2020-06-01','18:00:00',200,0,11.00);
/*!40000 ALTER TABLE `Movie_Tickets` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'defaultdb'
--

--
-- Dumping routines for database 'defaultdb'
--
SET @@SESSION.SQL_LOG_BIN = @MYSQLDUMP_TEMP_LOG_BIN;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-06-27  8:18:49
