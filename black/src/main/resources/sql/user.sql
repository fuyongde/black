USE `quickstart`;

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` varchar(255) NOT NULL,
  `created` bigint(20) NOT NULL,
  `updated` bigint(20) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `idcard` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `region_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
