CREATE SCHEMA `quickstart` DEFAULT CHARACTER SET utf8mb4 ;
ALTER TABLE `quickstart`.`region` ADD COLUMN `isLeaf` BOOLEAN NULL AFTER `level`;