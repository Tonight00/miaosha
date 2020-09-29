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


insert  into `item`(`id`,`title`,`price`,`description`,`sales`,`img_url`) values (1,'苹果','99.9800','可口的红苹果,恶毒的女人心',2,'static/apple.jpg');
insert  into `item_stock`(`id`,`stock`,`item_id`) values (1,999998,1);
insert  into `order`(`id`,`user_id`,`item_id`,`price`,`order_price`,`amount`) values ('2020092141',1,1,'99.9800','99.9800',1),('2020092151',1,1,'99.9800','99.9800',1);
insert  into `sequence`(`id`,`cur`,`step`,`name`) values (1,5,1,'order_info');
insert  into `user_info`(`id`,`name`,`gender`,`age`,`telephone`,`register_mode`,`third_part_id`) values (1,'person1',0,18,'18501010001',NULL,NULL);
insert  into `user_password`(`id`,`user_id`,`encrpt_password`) values (1,1,'4QrcOUm6Wau+VuBX8g+IPg==');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
