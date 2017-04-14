USE `testdb`;

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` varchar(255) NOT NULL,
  `created` bigint(20) NOT NULL,
  `updated` bigint(20) NOT NULL,
  `age` int(11) DEFAULT NULL,
  `birthday` bigint(20) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `idcard` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `region_id` int(11) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

insert  into `user`(`id`,`created`,`updated`,`age`,`birthday`,`email`,`idcard`,`name`,`phone`,`status`,`region_id`,`date`) values ('FBJEE40EB',1491553647350,1491630262055,NULL,NULL,'fuyongde@foxmail.com',NULL,'fuyongde',NULL,1,NULL,NULL),('SNQEE40TD',1491561212444,1491561212444,NULL,NULL,NULL,NULL,'fuyongqi',NULL,1,NULL,NULL);
