/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 8.0.18 : Database - miaosha
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;




CREATE DATABASE IF NOT EXISTS `miaosha` ;
USE `miaosha`;

/*Table structure for table `item` */

DROP TABLE IF EXISTS `item`;

CREATE TABLE `item` (
                      `id` int(11) NOT NULL AUTO_INCREMENT,
                      `title` varchar(64) NOT NULL,
                      `price` decimal(19,4) NOT NULL,
                      `description` varchar(500) NOT NULL,
                      `sales` int(11) NOT NULL DEFAULT '0',
                      `img_url` varchar(500) NOT NULL,
                      PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `item` */

/*Table structure for table `item_stock` */

DROP TABLE IF EXISTS `item_stock`;

CREATE TABLE `item_stock` (
                            `id` int(11) NOT NULL AUTO_INCREMENT,
                            `stock` int(11) NOT NULL,
                            `item_id` int(11) NOT NULL,
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf16;

/*Data for the table `item_stock` */

/*Table structure for table `order` */

DROP TABLE IF EXISTS `order`;

CREATE TABLE `order` (
                       `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                       `user_id` int(64) NOT NULL DEFAULT '0',
                       `item_id` int(64) NOT NULL DEFAULT '0',
                       `price` decimal(19,4) NOT NULL DEFAULT '0.0000',
                       `order_price` decimal(19,4) NOT NULL DEFAULT '0.0000',
                       `amount` int(32) NOT NULL,
                       PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `order` */

/*Table structure for table `promo` */

DROP TABLE IF EXISTS `promo`;

CREATE TABLE `promo` (
                       `id` int(32) NOT NULL AUTO_INCREMENT,
                       `promo_name` varchar(500) NOT NULL,
                       `start_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       `item_id` int(32) NOT NULL,
                       `promo_item_price` decimal(19,4) NOT NULL,
                       PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `promo` */

/*Table structure for table `sequence` */

DROP TABLE IF EXISTS `sequence`;

CREATE TABLE `sequence` (
                          `id` int(32) NOT NULL DEFAULT '0',
                          `cur` int(32) NOT NULL DEFAULT '0',
                          `step` int(32) NOT NULL DEFAULT '1',
                          `name` varchar(64) NOT NULL,
                          PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `sequence` */

/*Table structure for table `user_info` */

DROP TABLE IF EXISTS `user_info`;

CREATE TABLE `user_info` (
                           `id` int(11) NOT NULL AUTO_INCREMENT,
                           `name` varchar(64) DEFAULT NULL,
                           `gender` tinyint(64) DEFAULT NULL,
                           `age` int(11) DEFAULT NULL,
                           `telephone` varchar(64) DEFAULT NULL,
                           `register_mode` varchar(64) DEFAULT NULL,
                           `third_part_id` varchar(64) DEFAULT NULL,
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `user_info` */

/*Table structure for table `user_password` */

DROP TABLE IF EXISTS `user_password`;

CREATE TABLE `user_password` (
                               `id` int(11) NOT NULL AUTO_INCREMENT,
                               `user_id` int(11) NOT NULL,
                               `encrpt_password` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                               PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `user_password` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
