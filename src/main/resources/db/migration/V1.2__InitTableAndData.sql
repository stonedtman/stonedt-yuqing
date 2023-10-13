ALTER TABLE `stonedt_portal`.`user`
ADD COLUMN `term_of_validity` datetime(0) NULL DEFAULT NULL COMMENT '有效期' AFTER `xie_flag`;