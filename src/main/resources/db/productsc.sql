
SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `id` int(20) NOT NULL ,
  `type` int(20) NOT NULL,
  `name` varchar(200) NOT NULL,
  `status` int(20) NOT NULL,
  PRIMARY KEY (`id`)
)
