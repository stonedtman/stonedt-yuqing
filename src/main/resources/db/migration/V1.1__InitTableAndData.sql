ALTER TABLE `stonedt_portal`.`user`
    ADD COLUMN `nlp_secret_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'nlp平台id' AFTER `is_online`;
ALTER TABLE `stonedt_portal`.`user`
    ADD COLUMN `nlp_secret_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'nlp平台key' AFTER `nlp_secret_id`;
ALTER TABLE `stonedt_portal`.`user`
    ADD COLUMN `nlp_flag` int(1) NULL DEFAULT 0 COMMENT 'nlp平台绑定状态(1代表已绑定，0代表未绑定)' AFTER `nlp_secret_key`;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice`  (
                           `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '公告id',
                           `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公告内容',
                           `user_id` int(11) NULL DEFAULT NULL COMMENT '发布者id',
                           `publish_time` datetime NULL DEFAULT NULL COMMENT '发布时间',
                           `status` int(1) NULL DEFAULT 1 COMMENT '(1代表正常,0代表已经删除)',
                           PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_notice
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;

ALTER TABLE `stonedt_portal`.`user`
    ADD COLUMN `xie_secret_id` varchar(255) NULL COMMENT '写作宝平台id' AFTER `nlp_flag`;

ALTER TABLE `stonedt_portal`.`user`
    ADD COLUMN `xie_secret_key` varchar(255) NULL COMMENT '写作宝平台key' AFTER `xie_secret_id`;

ALTER TABLE `stonedt_portal`.`user`
    ADD COLUMN `xie_flag` int(1) NULL DEFAULT 0 COMMENT '写作宝平台绑定状态(1代表已绑定,0代表未绑定)' AFTER `xie_secret_key`;

delete from user
where id not in (
    select id from (
                       select min(id) id from user
                       group by telephone
                   ) t
);

ALTER TABLE `stonedt_portal`.`user`
    ADD UNIQUE INDEX `union_user_telephone`(`telephone`) USING BTREE COMMENT '电话号码唯一索引';