/*
 Navicat Premium Data Transfer

 Source Server         : conect
 Source Server Type    : MySQL
 Source Server Version : 80019
 Source Host           : localhost:3306
 Source Schema         : stonedt_portal

 Target Server Type    : MySQL
 Target Server Version : 80019
 File Encoding         : 65001

 Date: 17/10/2023 10:42:47
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for data_favorite
-- ----------------------------
DROP TABLE IF EXISTS `data_favorite`;
CREATE TABLE `data_favorite`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '自增长id',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标题',
  `article_public_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文章唯一id',
  `publish_time` datetime NULL DEFAULT NULL COMMENT '发布时间',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `favoritetime` datetime NULL DEFAULT NULL COMMENT '收藏时间',
  `status` int NULL DEFAULT 1 COMMENT '状态1.正常 2.删除',
  `emotionalIndex` int NULL DEFAULT NULL COMMENT '情感 1正面 2中性 3负面',
  `projectid` bigint NULL DEFAULT NULL COMMENT '方案id',
  `groupid` bigint NULL DEFAULT NULL COMMENT '方案组id',
  `source_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '来源网站',
  PRIMARY KEY (`id`, `user_id`) USING BTREE,
  UNIQUE INDEX `article_public_id`(`article_public_id` ASC, `user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 108 CHARACTER SET = utf8mb4 COLLATE = utf8_general_ci COMMENT = '文章收藏表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of data_favorite
-- ----------------------------

-- ----------------------------
-- Table structure for data_import
-- ----------------------------
DROP TABLE IF EXISTS `data_import`;
CREATE TABLE `data_import`  (
  `id` varchar(512) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL COMMENT '主键',
  `title` varchar(1024) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '标题',
  `content` longtext CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL COMMENT '文本内容',
  `contenthtml` longtext CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL COMMENT 'html内容',
  `source_url` varchar(512) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '地址',
  `source_name` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '来源地址名称',
  `websitelogo` varchar(512) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT 'logo图',
  `publish_time` datetime NULL DEFAULT NULL COMMENT '发布时间',
  `seed_id` int NULL DEFAULT NULL COMMENT '种子id',
  `website_id` int NULL DEFAULT NULL COMMENT '站点id',
  `type_id` int NULL DEFAULT NULL COMMENT '数据类型id',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of data_import
-- ----------------------------

-- ----------------------------
-- Table structure for flyway_schema_history
-- ----------------------------
DROP TABLE IF EXISTS `flyway_schema_history`;
CREATE TABLE `flyway_schema_history`  (
  `installed_rank` int NOT NULL,
  `version` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `description` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `script` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `checksum` int NULL DEFAULT NULL,
  `installed_by` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `installed_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `execution_time` int NOT NULL,
  `success` tinyint(1) NOT NULL,
  PRIMARY KEY (`installed_rank`) USING BTREE,
  INDEX `flyway_schema_history_s_idx`(`success` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of flyway_schema_history
-- ----------------------------
INSERT INTO `flyway_schema_history` VALUES (1, '1', '<< Flyway Baseline >>', 'BASELINE', '<< Flyway Baseline >>', NULL, 'root', '2023-10-16 19:43:24', 0, 1);
INSERT INTO `flyway_schema_history` VALUES (2, '1.1', 'InitTableAndData', 'SQL', 'V1.1__InitTableAndData.sql', 1477440974, 'root', '2023-10-16 19:43:24', 140, 1);
INSERT INTO `flyway_schema_history` VALUES (3, '1.2', 'InitTableAndData', 'SQL', 'V1.2__InitTableAndData.sql', 1177090637, 'root', '2023-10-16 19:43:24', 14, 1);
INSERT INTO `flyway_schema_history` VALUES (4, '1.3', 'InitTableAndData', 'SQL', 'V1.3__InitTableAndData.sql', 1117735008, 'root', '2023-10-16 19:43:24', 2, 1);
INSERT INTO `flyway_schema_history` VALUES (5, '1.4', 'InitTableAndData', 'SQL', 'V1.4__InitTableAndData.sql', -383198443, 'root', '2023-10-16 19:47:20', 2, 1);

-- ----------------------------
-- Table structure for full_menu
-- ----------------------------
DROP TABLE IF EXISTS `full_menu`;
CREATE TABLE `full_menu`  (
  `only_id` int NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `id` int NOT NULL COMMENT '唯一id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `type` int NULL DEFAULT NULL COMMENT '1一级分类2二级分类',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分类名称',
  `value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '传值（一级分类为空）',
  `type_one_id` int NULL DEFAULT NULL COMMENT '所属一级分类id（一级分类为空）',
  `type_two_id` int NULL DEFAULT NULL COMMENT '所属二级分类id',
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '一级分类图标',
  `is_show` tinyint NULL DEFAULT 0 COMMENT '是否展示，0展示，1不展示',
  `is_default` tinyint NULL DEFAULT 0 COMMENT '默认菜单列表，0是、1不是',
  PRIMARY KEY (`only_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 108 CHARACTER SET = utf8mb4 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of full_menu
-- ----------------------------

-- ----------------------------
-- Table structure for full_polymerization
-- ----------------------------
DROP TABLE IF EXISTS `full_polymerization`;
CREATE TABLE `full_polymerization`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `type` tinyint NULL DEFAULT 0 COMMENT '聚合菜单分类 0为系统默认分类、1为用户分类',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '聚合菜单名称',
  `value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '一级菜单id，多个用,间隔',
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '聚合菜单图标',
  `is_show` tinyint NULL DEFAULT 0 COMMENT '是否展示，0展示，1不展示',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of full_polymerization
-- ----------------------------

-- ----------------------------
-- Table structure for full_type
-- ----------------------------
DROP TABLE IF EXISTS `full_type`;
CREATE TABLE `full_type`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `type` int NULL DEFAULT NULL COMMENT '1一级分类2二级分类',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分类名称',
  `value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '传值（一级分类为空）',
  `type_one_id` int NULL DEFAULT NULL COMMENT '所属一级分类id（一级分类为空）',
  `type_two_id` int NULL DEFAULT NULL COMMENT '所属二级分类id',
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '一级分类图标',
  `is_show` tinyint NULL DEFAULT 0 COMMENT '是否展示，0展示，1不展示',
  `is_default` tinyint NULL DEFAULT 0 COMMENT '默认菜单列表，0是、1不是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 59 CHARACTER SET = utf8mb4 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of full_type
-- ----------------------------

-- ----------------------------
-- Table structure for full_word
-- ----------------------------
DROP TABLE IF EXISTS `full_word`;
CREATE TABLE `full_word`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `create_time` datetime NULL DEFAULT NULL COMMENT '记录时间',
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户id',
  `search_word` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '搜索词',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2093 CHARACTER SET = utf8mb4 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of full_word
-- ----------------------------

-- ----------------------------
-- Table structure for module
-- ----------------------------
DROP TABLE IF EXISTS `module`;
CREATE TABLE `module`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `module_id` int NULL DEFAULT NULL COMMENT '模块id',
  `module_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模块名称',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of module
-- ----------------------------

-- ----------------------------
-- Table structure for module_method
-- ----------------------------
DROP TABLE IF EXISTS `module_method`;
CREATE TABLE `module_method`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `module_id` int NULL DEFAULT NULL COMMENT '模块id',
  `method_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '方法名称',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of module_method
-- ----------------------------

-- ----------------------------
-- Table structure for monitor_analysis
-- ----------------------------
DROP TABLE IF EXISTS `monitor_analysis`;
CREATE TABLE `monitor_analysis`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `analysis_id` bigint NULL DEFAULT NULL COMMENT '监测分析公共id',
  `project_id` bigint NULL DEFAULT NULL COMMENT '方案id',
  `time_period` int NULL DEFAULT NULL COMMENT '时间周期',
  `data_overview` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '数据概览',
  `emotional_proportion` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '情感占比',
  `plan_word_hit` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '方案命中主体词',
  `keyword_emotion_trend` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '关键词情感分析走势',
  `hot_event_ranking` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '热点事件排名',
  `highword_cloud` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '关键词高频分布统计',
  `keyword_index` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '高频词指数',
  `media_activity_analysis` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '媒体活跃度分析',
  `hot_spot_ranking` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '热点地区排名',
  `data_source_distribution` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '数据来源分布',
  `data_source_analysis` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '数据来源分析',
  `keyword_exposure_rank` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '关键词曝光度环比排行',
  `selfmedia_volume_rank` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '自媒体渠道声量排名',
  `popular_event` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '热点事件json',
  `popular_information` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '热门资讯数据json',
  `relative_news` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '相关资讯json',
  `hot_company` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '热点公司json',
  `hot_people` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '热点人物json',
  `hot_spot` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '热点地区json',
  `keyword_emotion_statistical` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '关键词情感分析数据统计分布json',
  `ner` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '实体',
  `category_rank` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '分类趋势',
  `industrial_distribution` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '行业分布',
  `event_statistics` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '事件统计',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `project_id`(`project_id` ASC, `time_period` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3602 CHARACTER SET = utf8mb4 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of monitor_analysis
-- ----------------------------

-- ----------------------------
-- Table structure for monitor_analysis_copy1
-- ----------------------------
DROP TABLE IF EXISTS `monitor_analysis_copy1`;
CREATE TABLE `monitor_analysis_copy1`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `analysis_id` bigint NULL DEFAULT NULL COMMENT '监测分析公共id',
  `project_id` bigint NULL DEFAULT NULL COMMENT '方案id',
  `time_period` int NULL DEFAULT NULL COMMENT '时间周期',
  `data_overview` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '数据概览',
  `emotional_proportion` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '情感占比',
  `plan_word_hit` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '方案命中主体词',
  `keyword_emotion_trend` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '关键词情感分析走势',
  `hot_event_ranking` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '热点事件排名',
  `highword_cloud` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '关键词高频分布统计',
  `keyword_index` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '高频词指数',
  `media_activity_analysis` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '媒体活跃度分析',
  `hot_spot_ranking` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '热点地区排名',
  `data_source_distribution` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '数据来源分布',
  `data_source_analysis` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '数据来源分析',
  `keyword_exposure_rank` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '关键词曝光度环比排行',
  `selfmedia_volume_rank` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '自媒体渠道声量排名',
  `popular_event` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '热点事件json',
  `popular_information` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '热门资讯数据json',
  `relative_news` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '相关资讯json',
  `hot_company` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '热点公司json',
  `hot_people` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '热点人物json',
  `hot_spot` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '热点地区json',
  `keyword_emotion_statistical` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '关键词情感分析数据统计分布json',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `project_id`(`project_id` ASC, `time_period` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of monitor_analysis_copy1
-- ----------------------------

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '公告id',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公告内容',
  `user_id` int NULL DEFAULT NULL COMMENT '发布者id',
  `publish_time` datetime NULL DEFAULT NULL COMMENT '发布时间',
  `status` int NULL DEFAULT 1 COMMENT '(1代表正常,0代表已经删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of notice
-- ----------------------------

-- ----------------------------
-- Table structure for opinion_condition
-- ----------------------------
DROP TABLE IF EXISTS `opinion_condition`;
CREATE TABLE `opinion_condition`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `opinion_condition_id` bigint NULL DEFAULT NULL COMMENT '偏好设置公共id',
  `project_id` bigint NULL DEFAULT NULL COMMENT '方案id',
  `time` int NULL DEFAULT NULL COMMENT '时间范围(1:24小时，2:昨天，3:今天，4:3天，5：7天，6：15天，7：30天，8自定义)',
  `precise` int NULL DEFAULT NULL COMMENT '精准筛选（0：关闭：1打开）',
  `emotion` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '情感属性（1：正面 2：中性 3：负面）\r\n可多选，英文逗号分隔',
  `similar` int NULL DEFAULT NULL COMMENT '相似文章(0:取消合并 1：合并文章)',
  `sort` int NULL DEFAULT NULL COMMENT '信息排序（1：时间降序 2：时间升序 3：相似数倒序）',
  `matchs` int NULL DEFAULT NULL COMMENT '匹配方式（1：全文 2：标题 3：正文）',
  `times` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '自定义时间',
  `timee` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `classify` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT '[0]' COMMENT '数据来源',
  `websitename` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '网站名称',
  `author` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '作者名称',
  `organization` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '涉及机构',
  `categorylable` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文章分类',
  `enterprisetype` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '涉及企业',
  `hightechtype` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '高科技型企业',
  `policylableflag` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '涉及政策',
  `datasource_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据品类',
  `eventIndex` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '涉及事件',
  `industryIndex` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '涉及行业',
  `province` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '涉及省份',
  `city` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '涉及城市',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `opinion_condition_id`(`opinion_condition_id` ASC) USING BTREE,
  UNIQUE INDEX `project_id`(`project_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 930 CHARACTER SET = utf8mb4 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of opinion_condition
-- ----------------------------
INSERT INTO `opinion_condition` VALUES (926, '2022-02-14 17:26:23', 1493154645307166720, 1493154645181337600, 4, 1, '[1,2,3]', 0, 1, 1, '', '', '[0]', '', '', '0', '0', '0', '0', '0', '0', '', '', '', '');
INSERT INTO `opinion_condition` VALUES (927, '2022-02-14 17:28:23', 1493155148741087232, 1493155148443291648, 4, 0, '[1,2,3]', 0, 1, 1, '', '', '[0]', '', '', '0', '0', '0', '0', '0', '0', '', '', '', '');
INSERT INTO `opinion_condition` VALUES (928, '2022-02-14 17:31:24', 1493155905670352896, 1493155905573883904, 4, 1, '[1,2,3]', 0, 1, 1, '', '', '[0]', '', '', '0', '0', '0', '0', '0', '0', '', '', '', '');
INSERT INTO `opinion_condition` VALUES (929, '2022-02-14 17:31:59', 1493156056224894976, 1493156056132620288, 4, 1, '[1,2,3]', 0, 1, 1, '', '', '[0]', '', '', '0', '0', '0', '0', '0', '0', '', '', '', '');

-- ----------------------------
-- Table structure for organization
-- ----------------------------
DROP TABLE IF EXISTS `organization`;
CREATE TABLE `organization`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `organization_id` bigint NULL DEFAULT NULL COMMENT '机构公共id',
  `organization_short` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '机构简称',
  `organization_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '机构名全称',
  `organization_type` int NULL DEFAULT 1 COMMENT '机构类型（1机构、2个人）',
  `term_of_validity` datetime NULL DEFAULT NULL COMMENT '有效期',
  `status` int NULL DEFAULT 1 COMMENT '状态（1代表正常 2代表注销）',
  `organization_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组织代码',
  `logo_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'logo地址',
  `system_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '系统名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of organization
-- ----------------------------

-- ----------------------------
-- Table structure for project
-- ----------------------------
DROP TABLE IF EXISTS `project`;
CREATE TABLE `project`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `project_id` bigint NULL DEFAULT NULL COMMENT '方案公共id',
  `project_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '方案名',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `project_type` int NULL DEFAULT 1 COMMENT '方案类型（普通1，高级2）',
  `project_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '方案描述',
  `subject_word` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '主体词',
  `character_word` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '人物词',
  `event_word` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '事件词',
  `regional_word` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '地域词',
  `stop_word` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '屏蔽词',
  `del_status` int NULL DEFAULT 0 COMMENT '软删除（0：否 1：是）',
  `group_id` bigint NULL DEFAULT NULL COMMENT '方案组id',
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 966 CHARACTER SET = utf8mb4 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of project
-- ----------------------------
INSERT INTO `project` VALUES (962, '2022-02-14 17:26:23', 1493154645181337600, '政府部门', '2022-02-14 17:26:23', 2, NULL, '汽车政策,汽车产业发展政策,汽车行业,新能源,汽车产业,汽车领域', '邵卫东,工信部,发改委,国家能源局,能源局,政府,政策,补贴,改革,地方政策,行业协会,领域专家,有关部门,促进发展', '', '', '涨停,跌停,证券,股票,股价,A股,券商,涨幅,买入,涨跌,融资券,跌幅,涨跌幅,归母净利润,分类信息,黄页88,商务网,建材网,机械网,机电之家,云商讯,招聘启事,人才招聘,沪深股市,物流专线,校园招聘,培训,就业信息网,基金托管,交易日,多少钱,哪家好,厂家价格,优质供应商,信托产品,资产转让,债权转让,中概股,港股通,沪深两市,诊股,价格快讯,港交所,深交所,抄底,证券代码,股票代码,股票开户,代写融资计划书,代写方案,证券之星,代写,代考,淘股吧,电机维修,家电维修,股东大会决议公告,招标,投标,铸铁加工,铸铁盖板,铸铁管件,硅藻泥设计,微孔铝单板,空包代发,专线发车,股指,棋牌手机,融资融券,暴涨,暴跌', 0, 1493154486150107136, 13900000000);
INSERT INTO `project` VALUES (963, '2022-02-14 17:28:23', 1493155148443291648, '南京政策', '2022-02-14 17:28:23', 2, NULL, '南京市政府,政策,扶持创新,扶持创业,创新周,南京政府,市政府,科技局,发改委,经信委,产业园,人才引进,政府补贴,扶持力度,四新,城市创新,创新名城,智慧城市,新基建', '邵卫东,工信部,发改委,国家能源局,能源局,政府,政策,补贴,改革,地方政策,行业协会,领域专家,有关部门,促进发展', '大数据,人工智能,物联网,5G,区块链', '南京', '', 0, 1493154486150107136, 13900000000);
INSERT INTO `project` VALUES (964, '2022-02-14 17:31:24', 1493155905573883904, '舆情监测', '2022-02-14 17:31:24', 1, NULL, '舆情监测|网络舆情监测|舆情监控|舆情分析|舆情系统|免费舆情|大数据舆情|舆情大数据|舆情信息简报|大数据舆情', '', '', '', '涨停|跌停|证券|股票|股价|A股|券商|涨幅|买入|涨跌|融资券|跌幅|涨跌幅|归母净利润|分类信息|黄页88|商务网|建材网|机械网|机电之家|云商讯|培训|招聘启事|人才招聘|沪深股市|物流专线|校园招聘|培训|就业信息网|基金托管|交易日|多少钱|哪家好|厂家价格|优质供应商|信托产品|资产转让|债权转让|中概股|港股通|沪深两市|诊股|价格快讯|港交所|深交所|抄底|证券代码|股票代码', 0, 1493154486150107136, 13900000000);
INSERT INTO `project` VALUES (965, '2022-02-14 17:31:59', 1493156056132620288, '地方政策', '2022-02-14 17:31:59', 2, NULL, '汽车政策,新能源补贴,新能源政策,新能源汽车推广应用财政补贴政策', '', '', '上海,北京,广州,南京,江苏,浙江,杭州,深圳,广东', '涨停,跌停,证券,股票,股价,A股,券商,涨幅,买入,涨跌,融资券,跌幅,涨跌幅,归母净利润,分类信息,黄页88,商务网,建材网,机械网,机电之家,云商讯,培训,招聘启事,人才招聘,沪深股市,物流专线,校园招聘,培训,就业信息网,基金托管,交易日,多少钱,哪家好,厂家价格,优质供应商,信托产品,资产转让,债权转让,中概股,港股通,沪深两市,诊股,价格快讯', 0, 1493154486150107136, 13900000000);

-- ----------------------------
-- Table structure for project_task
-- ----------------------------
DROP TABLE IF EXISTS `project_task`;
CREATE TABLE `project_task`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `create_time` datetime NULL DEFAULT NULL,
  `project_id` bigint NULL DEFAULT NULL,
  `project_type` int NULL DEFAULT NULL,
  `subject_word` text CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL,
  `regional_word` text CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL,
  `character_word` text CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL,
  `event_word` text CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL,
  `stop_word` text CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL,
  `analysis_flag` int NULL DEFAULT 0,
  `volume_flag` int NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `project_id`(`project_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 781 CHARACTER SET = utf8mb4 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of project_task
-- ----------------------------
INSERT INTO `project_task` VALUES (777, '2022-02-14 17:26:23', 1493154645181337600, 2, '汽车政策,汽车产业发展政策,汽车行业,新能源,汽车产业,汽车领域', '', '邵卫东,工信部,发改委,国家能源局,能源局,政府,政策,补贴,改革,地方政策,行业协会,领域专家,有关部门,促进发展', '', '涨停,跌停,证券,股票,股价,A股,券商,涨幅,买入,涨跌,融资券,跌幅,涨跌幅,归母净利润,分类信息,黄页88,商务网,建材网,机械网,机电之家,云商讯,招聘启事,人才招聘,沪深股市,物流专线,校园招聘,培训,就业信息网,基金托管,交易日,多少钱,哪家好,厂家价格,优质供应商,信托产品,资产转让,债权转让,中概股,港股通,沪深两市,诊股,价格快讯,港交所,深交所,抄底,证券代码,股票代码,股票开户,代写融资计划书,代写方案,证券之星,代写,代考,淘股吧,电机维修,家电维修,股东大会决议公告,招标,投标,铸铁加工,铸铁盖板,铸铁管件,硅藻泥设计,微孔铝单板,空包代发,专线发车,股指,棋牌手机,融资融券,暴涨,暴跌', 0, 0);
INSERT INTO `project_task` VALUES (778, '2022-02-14 17:28:23', 1493155148443291648, 2, '南京市政府,政策,扶持创新,扶持创业,创新周,南京政府,市政府,科技局,发改委,经信委,产业园,人才引进,政府补贴,扶持力度,四新,城市创新,创新名城,智慧城市,新基建', '南京', '邵卫东,工信部,发改委,国家能源局,能源局,政府,政策,补贴,改革,地方政策,行业协会,领域专家,有关部门,促进发展', '大数据,人工智能,物联网,5G,区块链', '', 0, 0);
INSERT INTO `project_task` VALUES (779, '2022-02-14 17:31:24', 1493155905573883904, 1, '舆情监测|网络舆情监测|舆情监控|舆情分析|舆情系统|免费舆情|大数据舆情|舆情大数据|舆情信息简报|大数据舆情', '', '', '', '涨停|跌停|证券|股票|股价|A股|券商|涨幅|买入|涨跌|融资券|跌幅|涨跌幅|归母净利润|分类信息|黄页88|商务网|建材网|机械网|机电之家|云商讯|培训|招聘启事|人才招聘|沪深股市|物流专线|校园招聘|培训|就业信息网|基金托管|交易日|多少钱|哪家好|厂家价格|优质供应商|信托产品|资产转让|债权转让|中概股|港股通|沪深两市|诊股|价格快讯|港交所|深交所|抄底|证券代码|股票代码', 0, 0);
INSERT INTO `project_task` VALUES (780, '2022-02-14 17:32:00', 1493156056132620288, 2, '汽车政策,新能源补贴,新能源政策,新能源汽车推广应用财政补贴政策', '上海,北京,广州,南京,江苏,浙江,杭州,深圳,广东', '', '', '涨停,跌停,证券,股票,股价,A股,券商,涨幅,买入,涨跌,融资券,跌幅,涨跌幅,归母净利润,分类信息,黄页88,商务网,建材网,机械网,机电之家,云商讯,培训,招聘启事,人才招聘,沪深股市,物流专线,校园招聘,培训,就业信息网,基金托管,交易日,多少钱,哪家好,厂家价格,优质供应商,信托产品,资产转让,债权转让,中概股,港股通,沪深两市,诊股,价格快讯', 0, 0);

-- ----------------------------
-- Table structure for publicoption_detail
-- ----------------------------
DROP TABLE IF EXISTS `publicoption_detail`;
CREATE TABLE `publicoption_detail`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '自增长id',
  `publicoption_id` int NULL DEFAULT NULL COMMENT '研判报告id',
  `back_analysis` text CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '溯源分析',
  `event_context` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '事件脉络',
  `event_trace` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '事件跟踪',
  `hot_analysis` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '热点分析',
  `netizens_analysis` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '重点网民分析',
  `statistics` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '统计',
  `propagation_analysis` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '传播分析',
  `thematic_analysis` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '专题分析',
  `unscramble_content` text CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '内容解读',
  `create_time` datetime NULL DEFAULT NULL COMMENT '生成时间',
  `detail_status` int NULL DEFAULT 0 COMMENT '状态值（暂时没用）',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `publicoption_id`(`publicoption_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 107 CHARACTER SET = utf8mb4 COLLATE = utf8_general_ci COMMENT = '舆情研判任务详情表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of publicoption_detail
-- ----------------------------

-- ----------------------------
-- Table structure for publicoptionevent
-- ----------------------------
DROP TABLE IF EXISTS `publicoptionevent`;
CREATE TABLE `publicoptionevent`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '自增长id',
  `eventname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '任务名称',
  `eventkeywords` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '任务关键词',
  `eventstarttime` datetime NULL DEFAULT NULL COMMENT '任务开始时间',
  `eventendtime` datetime NULL DEFAULT NULL COMMENT '任务结束时间',
  `createtime` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `status` int NULL DEFAULT 2 COMMENT '1.创建失败2.正在创建3.创建成功',
  `updatetime` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户id',
  `eventstopwords` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '屏蔽词',
  `isdelete` int NULL DEFAULT 1 COMMENT '是否删除标记',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 66 CHARACTER SET = utf8mb4 COLLATE = utf8_general_ci COMMENT = '舆情研判任务表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of publicoptionevent
-- ----------------------------

-- ----------------------------
-- Table structure for read_sign
-- ----------------------------
DROP TABLE IF EXISTS `read_sign`;
CREATE TABLE `read_sign`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '自增长id',
  `article_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文章ID',
  `user_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `type` int NULL DEFAULT NULL COMMENT '新增字段',
  `str` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '新增字段',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_id`(`article_id` ASC, `user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 59 CHARACTER SET = utf8mb4 COLLATE = utf8_general_ci COMMENT = '已读标记表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of read_sign
-- ----------------------------

-- ----------------------------
-- Table structure for report_custom
-- ----------------------------
DROP TABLE IF EXISTS `report_custom`;
CREATE TABLE `report_custom`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `report_id` bigint NULL DEFAULT NULL COMMENT '报告公共id',
  `report_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '报告名称',
  `report_type` int NULL DEFAULT NULL COMMENT '报告类型（1:日报，2:周报，3:月报）',
  `report_starttime` datetime NULL DEFAULT NULL COMMENT '报告周期开始时间',
  `report_endtime` datetime NULL DEFAULT NULL COMMENT '报告周期结束时间',
  `report_status` int NULL DEFAULT 1 COMMENT '报告状态（0：已生成任务，1：正在编制，2:编制成功3：编制失败）',
  `report_topping` int NULL DEFAULT 0 COMMENT '报告是否置顶(0:未置顶，1:置顶)，默认0',
  `report_time` datetime NULL DEFAULT NULL COMMENT '报告编制时间',
  `del_status` int NULL DEFAULT 0 COMMENT '软删除（0:否，1：是）',
  `number_period` int NULL DEFAULT NULL COMMENT '期数',
  `processes` int NULL DEFAULT NULL COMMENT '生成进度',
  `module_sum` int NULL DEFAULT NULL COMMENT '模板组件数量',
  `template_id` bigint NULL DEFAULT NULL COMMENT '报告模板id',
  `template_info` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模板信息',
  `project_id` bigint NULL DEFAULT NULL COMMENT '方案id',
  `keyword` text CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '报告关键词',
  `stopword` text CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '报告屏蔽词',
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `report_id`(`report_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 279535 CHARACTER SET = utf8mb4 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of report_custom
-- ----------------------------

-- ----------------------------
-- Table structure for report_detail
-- ----------------------------
DROP TABLE IF EXISTS `report_detail`;
CREATE TABLE `report_detail`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `report_id` bigint NULL DEFAULT NULL COMMENT '报告id',
  `data_overview` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '1数据概览逻辑处理 2、3资讯和社交数据逻辑处理',
  `emotion_analysis` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '4、情感分析',
  `hot_event_ranking` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '5、热点事件排名 ',
  `media_activity_analysis` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '10、媒体活跃度分析',
  `self_media_ranking` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '13、自媒体热度排名',
  `high_word_index` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '14、高频词指数',
  `hot_spot_ranking` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '15、热点地区排名',
  `netizen_word_cloud` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '11、网民高频词云',
  `media_cord_cloud` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '12、媒体高频词云',
  `hot_people` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '6、热点人物',
  `hot_spots` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '7、热点地区 ',
  `topic_clustering` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '8、主题观点聚类分析',
  `social_v_ranking` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '9、社交网络大V热度排名',
  `highword_cloud` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '关键词高频分布统计',
  `keyword_index` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '高频词指数',
  `highword_cloud_index` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL,
  `ner` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '实体',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `report_id`(`report_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6351 CHARACTER SET = utf8mb4 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of report_detail
-- ----------------------------

-- ----------------------------
-- Table structure for report_detail_copy1
-- ----------------------------
DROP TABLE IF EXISTS `report_detail_copy1`;
CREATE TABLE `report_detail_copy1`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `report_id` bigint NULL DEFAULT NULL COMMENT '报告id',
  `data_overview` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '1数据概览逻辑处理 2、3资讯和社交数据逻辑处理',
  `emotion_analysis` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '4、情感分析',
  `hot_event_ranking` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '5、热点事件排名 ',
  `media_activity_analysis` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '10、媒体活跃度分析',
  `self_media_ranking` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '13、自媒体热度排名',
  `high_word_index` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '14、高频词指数',
  `hot_spot_ranking` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '15、热点地区排名',
  `netizen_word_cloud` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '11、网民高频词云',
  `media_cord_cloud` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '12、媒体高频词云',
  `hot_people` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '6、热点人物',
  `hot_spots` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '7、热点地区 ',
  `topic_clustering` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '8、主题观点聚类分析',
  `social_v_ranking` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '9、社交网络大V热度排名',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `report_id`(`report_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of report_detail_copy1
-- ----------------------------

-- ----------------------------
-- Table structure for search_type
-- ----------------------------
DROP TABLE IF EXISTS `search_type`;
CREATE TABLE `search_type`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `type_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '类型',
  `status` int NULL DEFAULT 1 COMMENT '状态',
  `create_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图标',
  `type` int NULL DEFAULT NULL COMMENT '类型',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of search_type
-- ----------------------------
INSERT INTO `search_type` VALUES (1, '资讯', 1, '2022-10-31 10:32:54', 'mdi mdi-widgets', 1);

-- ----------------------------
-- Table structure for solution_group
-- ----------------------------
DROP TABLE IF EXISTS `solution_group`;
CREATE TABLE `solution_group`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `group_id` bigint NULL DEFAULT NULL COMMENT '方案组公共id',
  `group_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '方案组名称',
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户id',
  `del_status` int NULL DEFAULT 0 COMMENT '软删除（0：否 1：是）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `group_name`(`group_name` ASC, `user_id` ASC, `del_status` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 557 CHARACTER SET = utf8mb4 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of solution_group
-- ----------------------------
INSERT INTO `solution_group` VALUES (556, '2022-02-14 17:25:45', 1493154486150107136, '国家政策', 13900000000, 0);

-- ----------------------------
-- Table structure for submodule
-- ----------------------------
DROP TABLE IF EXISTS `submodule`;
CREATE TABLE `submodule`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `submodule_id` int NULL DEFAULT NULL COMMENT '子模块id',
  `submodule_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '子模块名称',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `module_id` int NULL DEFAULT NULL COMMENT '模块id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8mb4 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of submodule
-- ----------------------------

-- ----------------------------
-- Table structure for synthesize
-- ----------------------------
DROP TABLE IF EXISTS `synthesize`;
CREATE TABLE `synthesize`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '自增长id',
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户id',
  `cteate_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `report_day` text CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '日报',
  `report_week` text CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '周报',
  `hot_weibo` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '微博热点',
  `hot_all` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '热点事件',
  `hot_search_terms` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '热点搜索词',
  `leaders_PO` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '领导人舆情',
  `today_PO_status` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '今日舆情情况',
  `warning_PO` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '预警舆情展示',
  `upload_PO` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '个人信息报送',
  `project_PO_status` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '专题展示',
  `online` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '系统当前在线统计',
  `push_PO` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT ' 推送舆情',
  `reprint_PO` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '转载查询',
  `collection_po` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '收藏贴文',
  `hot_wechat` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '微信热点',
  `hot_douyin` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '抖音',
  `hot_bilibili` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT 'bilibili',
  `hot_tecentvedio` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '腾讯视频',
  `hot_36kr` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL,
  `hot_finaceData` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL,
  `hot_policydata` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 64 CHARACTER SET = utf8mb4 COLLATE = utf8_general_ci COMMENT = '综合看板表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of synthesize
-- ----------------------------
INSERT INTO `synthesize` VALUES (64, 1, NULL, '2023-10-16 18:00:47', NULL, NULL, '[{\"topic\":\"从大写意到工笔画\",\"source_url\":\"https://s.weibo.com/weibo?q=%23%E4%BB%8E%E5%A4%A7%E5%86%99%E6%84%8F%E5%88%B0%E5%B7%A5%E7%AC%94%E7%94%BB%23&Refer=top\",\"source_name\":\"微博\",\"original_weight\":\"\"},{\"topic\":\"麻酱拿铁\",\"source_url\":\"https://s.weibo.com/weibo?q=%23%E9%BA%BB%E9%85%B1%E6%8B%BF%E9%93%81%23&Refer=top\",\"source_name\":\"微博\",\"original_weight\":\"2877000\"},{\"topic\":\"朱一龙 北大\",\"source_url\":\"https://s.weibo.com/weibo?q=%23%E6%9C%B1%E4%B8%80%E9%BE%99+%E5%8C%97%E5%A4%A7%23&Refer=top\",\"source_name\":\"微博\",\"original_weight\":\" 2583618\"},{\"topic\":\"牡丹与玫瑰的丝路流芳\",\"source_url\":\"https://s.weibo.com/weibo?q=%23%E7%89%A1%E4%B8%B9%E4%B8%8E%E7%8E%AB%E7%91%B0%E7%9A%84%E4%B8%9D%E8%B7%AF%E6%B5%81%E8%8A%B3%23&Refer=top\",\"source_name\":\"微博\",\"original_weight\":\"1353000\"},{\"topic\":\"尊重式分手\",\"source_url\":\"https://s.weibo.com/weibo?q=%23%E5%B0%8A%E9%87%8D%E5%BC%8F%E5%88%86%E6%89%8B%23&Refer=top\",\"source_name\":\"微博\",\"original_weight\":\"1056000\"}]', '[{\"topic\":\"开拓造福各国、惠及世界的幸福路\",\"id\":\"03B5905F49D9650B46E23DA0AE528C2E\",\"original_weight\":\"4965251\",\"source_url\":\"https://www.baidu.com/s?wd=%E5%BC%80%E6%8B%93%E9%80%A0%E7%A6%8F%E5%90%84%E5%9B%BD%E3%80%81%E6%83%A0%E5%8F%8A%E4%B8%96%E7%95%8C%E7%9A%84%E5%B9%B8%E7%A6%8F%E8%B7%AF&sa=fyb_news&rsv_dl=fyb_news\",\"source_name\":\"百度风云榜\"},{\"topic\":\"中方向巴勒斯坦提供人道主义援助\",\"id\":\"446703608E8BEA30267251B0DF52D7BA\",\"original_weight\":\"4994903\",\"source_url\":\"https://www.baidu.com/s?wd=%E4%B8%AD%E6%96%B9%E5%90%91%E5%B7%B4%E5%8B%92%E6%96%AF%E5%9D%A6%E6%8F%90%E4%BE%9B%E4%BA%BA%E9%81%93%E4%B8%BB%E4%B9%89%E6%8F%B4%E5%8A%A9&sa=fyb_news&rsv_dl=fyb_news\",\"source_name\":\"百度风云榜\"},{\"topic\":\"2349名缅北电诈嫌犯移交中方\",\"id\":\"E9322B239F344D0139A31B31ECE911AD\",\"original_weight\":\"4847466\",\"source_url\":\"https://www.baidu.com/s?wd=2349%E5%90%8D%E7%BC%85%E5%8C%97%E7%94%B5%E8%AF%88%E5%AB%8C%E7%8A%AF%E7%A7%BB%E4%BA%A4%E4%B8%AD%E6%96%B9&sa=fyb_news&rsv_dl=fyb_news\",\"source_name\":\"百度风云榜\"},{\"topic\":\"文明厚植沃土 新风助力振兴\",\"id\":\"809B9F74324E3612AF1617A7AF79703F\",\"original_weight\":\"4717245\",\"source_url\":\"https://www.baidu.com/s?wd=%E6%96%87%E6%98%8E%E5%8E%9A%E6%A4%8D%E6%B2%83%E5%9C%9F+%E6%96%B0%E9%A3%8E%E5%8A%A9%E5%8A%9B%E6%8C%AF%E5%85%B4&sa=fyb_news&rsv_dl=fyb_news\",\"source_name\":\"百度风云榜\"},{\"topic\":\"妈妈想陪女儿上大学当上食堂阿姨\",\"id\":\"0FD3946354C88906009C727201FAA596\",\"original_weight\":\"4639313\",\"source_url\":\"https://www.baidu.com/s?wd=%E5%A6%88%E5%A6%88%E6%83%B3%E9%99%AA%E5%A5%B3%E5%84%BF%E4%B8%8A%E5%A4%A7%E5%AD%A6%E5%BD%93%E4%B8%8A%E9%A3%9F%E5%A0%82%E9%98%BF%E5%A7%A8&sa=fyb_news&rsv_dl=fyb_news\",\"source_name\":\"百度风云榜\"},{\"topic\":\"长期处于消极思维更易患老年痴呆\",\"id\":\"D8B00780F7EC81C74AE06D95C53FFD07\",\"original_weight\":\"4552467\",\"source_url\":\"https://www.baidu.com/s?wd=%E9%95%BF%E6%9C%9F%E5%A4%84%E4%BA%8E%E6%B6%88%E6%9E%81%E6%80%9D%E7%BB%B4%E6%9B%B4%E6%98%93%E6%82%A3%E8%80%81%E5%B9%B4%E7%97%B4%E5%91%86&sa=fyb_news&rsv_dl=fyb_news\",\"source_name\":\"百度风云榜\"},{\"topic\":\"女童在小区遭恶犬围咬 警方通报\",\"id\":\"6CD68F9EE18FD7F6DC4DF2A98D21A544\",\"original_weight\":\"4491313\",\"source_url\":\"https://www.baidu.com/s?wd=%E5%A5%B3%E7%AB%A5%E5%9C%A8%E5%B0%8F%E5%8C%BA%E9%81%AD%E6%81%B6%E7%8A%AC%E5%9B%B4%E5%92%AC+%E8%AD%A6%E6%96%B9%E9%80%9A%E6%8A%A5&sa=fyb_news&rsv_dl=fyb_news\",\"source_name\":\"百度风云榜\"},{\"topic\":\"美国国务卿布林肯再次访问以色列\",\"id\":\"9E3153E1280C94D191A22BE85C198CCD\",\"original_weight\":\"4330613\",\"source_url\":\"https://www.baidu.com/s?wd=%E7%BE%8E%E5%9B%BD%E5%9B%BD%E5%8A%A1%E5%8D%BF%E5%B8%83%E6%9E%97%E8%82%AF%E5%86%8D%E6%AC%A1%E8%AE%BF%E9%97%AE%E4%BB%A5%E8%89%B2%E5%88%97&sa=fyb_news&rsv_dl=fyb_news\",\"source_name\":\"百度风云榜\"},{\"topic\":\"济南女生宿舍内死亡？当地辟谣\",\"id\":\"4550656161670B65B98A6B7C272D8938\",\"original_weight\":\"4242389\",\"source_url\":\"https://www.baidu.com/s?wd=%E6%B5%8E%E5%8D%97%E5%A5%B3%E7%94%9F%E5%AE%BF%E8%88%8D%E5%86%85%E6%AD%BB%E4%BA%A1%EF%BC%9F%E5%BD%93%E5%9C%B0%E8%BE%9F%E8%B0%A3&sa=fyb_news&rsv_dl=fyb_news\",\"source_name\":\"百度风云榜\"},{\"topic\":\"巴勒斯坦男孩：我们在这里长不大\",\"id\":\"139166385037BC3FF8B27476452E2C97\",\"original_weight\":\"4156470\",\"source_url\":\"https://www.baidu.com/s?wd=%E5%B7%B4%E5%8B%92%E6%96%AF%E5%9D%A6%E7%94%B7%E5%AD%A9%EF%BC%9A%E6%88%91%E4%BB%AC%E5%9C%A8%E8%BF%99%E9%87%8C%E9%95%BF%E4%B8%8D%E5%A4%A7&sa=fyb_news&rsv_dl=fyb_news\",\"source_name\":\"百度风云榜\"},{\"topic\":\"谷村新司作品曾被邓丽君翻唱\",\"id\":\"3372D980A690D1F189F1385E5E13C3BD\",\"original_weight\":\"4093475\",\"source_url\":\"https://www.baidu.com/s?wd=%E8%B0%B7%E6%9D%91%E6%96%B0%E5%8F%B8%E4%BD%9C%E5%93%81%E6%9B%BE%E8%A2%AB%E9%82%93%E4%B8%BD%E5%90%9B%E7%BF%BB%E5%94%B1&sa=fyb_news&rsv_dl=fyb_news\",\"source_name\":\"百度风云榜\"},{\"topic\":\"成都2岁女童遭烈犬撕咬入院治疗\",\"id\":\"6B7F96FD4DA98264635FF8F3C5AE340D\",\"original_weight\":\"3900377\",\"source_url\":\"https://www.baidu.com/s?wd=%E6%88%90%E9%83%BD2%E5%B2%81%E5%A5%B3%E7%AB%A5%E9%81%AD%E7%83%88%E7%8A%AC%E6%92%95%E5%92%AC%E5%85%A5%E9%99%A2%E6%B2%BB%E7%96%97&sa=fyb_news&rsv_dl=fyb_news\",\"source_name\":\"百度风云榜\"},{\"topic\":\"第一批援助物资进入加沙\",\"id\":\"C440F6043B7A51F596EB16D4BE0B1FD2\",\"original_weight\":\"3847522\",\"source_url\":\"https://www.baidu.com/s?wd=%E7%AC%AC%E4%B8%80%E6%89%B9%E6%8F%B4%E5%8A%A9%E7%89%A9%E8%B5%84%E8%BF%9B%E5%85%A5%E5%8A%A0%E6%B2%99&sa=fyb_news&rsv_dl=fyb_news\",\"source_name\":\"百度风云榜\"},{\"topic\":\"洛杉矶奥运会确认新增五个项目\",\"id\":\"0A63C5993CA735A199A0CA12D4DAEE7A\",\"original_weight\":\"3788988\",\"source_url\":\"https://www.baidu.com/s?wd=%E6%B4%9B%E6%9D%89%E7%9F%B6%E5%A5%A5%E8%BF%90%E4%BC%9A%E7%A1%AE%E8%AE%A4%E6%96%B0%E5%A2%9E%E4%BA%94%E4%B8%AA%E9%A1%B9%E7%9B%AE&sa=fyb_news&rsv_dl=fyb_news\",\"source_name\":\"百度风云榜\"},{\"topic\":\"饭圈女孩倒卖明星航班信息被判刑\",\"id\":\"B52FE1C4A6725D2BFC2DC66D211C21CE\",\"original_weight\":\"3696030\",\"source_url\":\"https://www.baidu.com/s?wd=%E9%A5%AD%E5%9C%88%E5%A5%B3%E5%AD%A9%E5%80%92%E5%8D%96%E6%98%8E%E6%98%9F%E8%88%AA%E7%8F%AD%E4%BF%A1%E6%81%AF%E8%A2%AB%E5%88%A4%E5%88%91&sa=fyb_news&rsv_dl=fyb_news\",\"source_name\":\"百度风云榜\"},{\"topic\":\"体育局介入调查马拉松选手冲刺被挡\",\"id\":\"620769391E09A7E302F1BB77D7C33D44\",\"original_weight\":\"3514384\",\"source_url\":\"https://www.baidu.com/s?wd=%E4%BD%93%E8%82%B2%E5%B1%80%E4%BB%8B%E5%85%A5%E8%B0%83%E6%9F%A5%E9%A9%AC%E6%8B%89%E6%9D%BE%E9%80%89%E6%89%8B%E5%86%B2%E5%88%BA%E8%A2%AB%E6%8C%A1&sa=fyb_news&rsv_dl=fyb_news\",\"source_name\":\"百度风云榜\"},{\"topic\":\"加沙地带近百万巴勒斯坦人流离失所\",\"id\":\"B2FFBB71B1CED46230D7B3A006C1863B\",\"original_weight\":\"3485384\",\"source_url\":\"https://www.baidu.com/s?wd=%E5%8A%A0%E6%B2%99%E5%9C%B0%E5%B8%A6%E8%BF%91%E7%99%BE%E4%B8%87%E5%B7%B4%E5%8B%92%E6%96%AF%E5%9D%A6%E4%BA%BA%E6%B5%81%E7%A6%BB%E5%A4%B1%E6%89%80&sa=fyb_news&rsv_dl=fyb_news\",\"source_name\":\"百度风云榜\"},{\"topic\":\"巴以冲突已致超4200人死亡\",\"id\":\"663F10B80DFA5B9B3119136A42A2A45A\",\"original_weight\":\"3357619\",\"source_url\":\"https://www.baidu.com/s?wd=%E5%B7%B4%E4%BB%A5%E5%86%B2%E7%AA%81%E5%B7%B2%E8%87%B4%E8%B6%854200%E4%BA%BA%E6%AD%BB%E4%BA%A1&sa=fyb_news&rsv_dl=fyb_news\",\"source_name\":\"百度风云榜\"},{\"topic\":\"外交部：建议在以中国公民尽快回国\",\"id\":\"ADD798F89CB3576814275C134CAB704C\",\"original_weight\":\"3295752\",\"source_url\":\"https://www.baidu.com/s?wd=%E5%A4%96%E4%BA%A4%E9%83%A8%EF%BC%9A%E5%BB%BA%E8%AE%AE%E5%9C%A8%E4%BB%A5%E4%B8%AD%E5%9B%BD%E5%85%AC%E6%B0%91%E5%B0%BD%E5%BF%AB%E5%9B%9E%E5%9B%BD&sa=fyb_news&rsv_dl=fyb_news\",\"source_name\":\"百度风云榜\"},{\"topic\":\"以色列否认“停火允许援助进加沙”\",\"id\":\"70A2DB9697ACE93F5E0AB04E6FA4B0AE\",\"original_weight\":\"3184899\",\"source_url\":\"https://www.baidu.com/s?wd=%E4%BB%A5%E8%89%B2%E5%88%97%E5%90%A6%E8%AE%A4%E2%80%9C%E5%81%9C%E7%81%AB%E5%85%81%E8%AE%B8%E6%8F%B4%E5%8A%A9%E8%BF%9B%E5%8A%A0%E6%B2%99%E2%80%9D&sa=fyb_news&rsv_dl=fyb_news\",\"source_name\":\"百度风云榜\"},{\"topic\":\"郑州有一条“唐人街”？管理方回应\",\"id\":\"F1EEB3AEC198D64B79294BD508CBE1E6\",\"original_weight\":\"3002299\",\"source_url\":\"https://www.baidu.com/s?wd=%E9%83%91%E5%B7%9E%E6%9C%89%E4%B8%80%E6%9D%A1%E2%80%9C%E5%94%90%E4%BA%BA%E8%A1%97%E2%80%9D%EF%BC%9F%E7%AE%A1%E7%90%86%E6%96%B9%E5%9B%9E%E5%BA%94&sa=fyb_news&rsv_dl=fyb_news\",\"source_name\":\"百度风云榜\"},{\"topic\":\"东三省省会气温集体跌破0度\",\"id\":\"892C3450DEA0EDE288A06DC374CE1E22\",\"original_weight\":\"2902529\",\"source_url\":\"https://www.baidu.com/s?wd=%E4%B8%9C%E4%B8%89%E7%9C%81%E7%9C%81%E4%BC%9A%E6%B0%94%E6%B8%A9%E9%9B%86%E4%BD%93%E8%B7%8C%E7%A0%B40%E5%BA%A6&sa=fyb_news&rsv_dl=fyb_news\",\"source_name\":\"百度风云榜\"},{\"topic\":\"顾客肯德基炸鸡中吃出蛆虫\",\"id\":\"3CCD9C0AA4E732CA27272BF6EF0458AE\",\"original_weight\":\"2899742\",\"source_url\":\"https://www.baidu.com/s?wd=%E9%A1%BE%E5%AE%A2%E8%82%AF%E5%BE%B7%E5%9F%BA%E7%82%B8%E9%B8%A1%E4%B8%AD%E5%90%83%E5%87%BA%E8%9B%86%E8%99%AB&sa=fyb_news&rsv_dl=fyb_news\",\"source_name\":\"百度风云榜\"},{\"topic\":\"法拉第未来市值仅0.2亿美元\",\"id\":\"123A9A069A53E5CC379086C84105E2C4\",\"original_weight\":\"2718892\",\"source_url\":\"https://www.baidu.com/s?wd=%E6%B3%95%E6%8B%89%E7%AC%AC%E6%9C%AA%E6%9D%A5%E5%B8%82%E5%80%BC%E4%BB%850.2%E4%BA%BF%E7%BE%8E%E5%85%83&sa=fyb_news&rsv_dl=fyb_news\",\"source_name\":\"百度风云榜\"},{\"topic\":\"盗割贵州2600年古楠木 三人获刑\",\"id\":\"5C83CA22799E225133C6B2A0400FF29D\",\"original_weight\":\"2685720\",\"source_url\":\"https://www.baidu.com/s?wd=%E7%9B%97%E5%89%B2%E8%B4%B5%E5%B7%9E2600%E5%B9%B4%E5%8F%A4%E6%A5%A0%E6%9C%A8+%E4%B8%89%E4%BA%BA%E8%8E%B7%E5%88%91&sa=fyb_news&rsv_dl=fyb_news\",\"source_name\":\"百度风云榜\"},{\"topic\":\"足协新任执委会成员背景介绍\",\"id\":\"61C15946489FF1C1B3DA3268644588DE\",\"original_weight\":\"2546106\",\"source_url\":\"https://www.baidu.com/s?wd=%E8%B6%B3%E5%8D%8F%E6%96%B0%E4%BB%BB%E6%89%A7%E5%A7%94%E4%BC%9A%E6%88%90%E5%91%98%E8%83%8C%E6%99%AF%E4%BB%8B%E7%BB%8D&sa=fyb_news&rsv_dl=fyb_news\",\"source_name\":\"百度风云榜\"},{\"topic\":\"专家建议住宅70年产权改为永久\",\"id\":\"E46B41ACEED093561F7F2022A227BDFA\",\"original_weight\":\"2463382\",\"source_url\":\"https://www.baidu.com/s?wd=%E4%B8%93%E5%AE%B6%E5%BB%BA%E8%AE%AE%E4%BD%8F%E5%AE%8570%E5%B9%B4%E4%BA%A7%E6%9D%83%E6%94%B9%E4%B8%BA%E6%B0%B8%E4%B9%85&sa=fyb_news&rsv_dl=fyb_news\",\"source_name\":\"百度风云榜\"},{\"topic\":\"俄罗斯对自日本进口水产品实施限制\",\"id\":\"E2EF284386CCC734BF404B0863AA54FA\",\"original_weight\":\"2323181\",\"source_url\":\"https://www.baidu.com/s?wd=%E4%BF%84%E7%BD%97%E6%96%AF%E5%AF%B9%E8%87%AA%E6%97%A5%E6%9C%AC%E8%BF%9B%E5%8F%A3%E6%B0%B4%E4%BA%A7%E5%93%81%E5%AE%9E%E6%96%BD%E9%99%90%E5%88%B6&sa=fyb_news&rsv_dl=fyb_news\",\"source_name\":\"百度风云榜\"},{\"topic\":\"贾玲为缺席王牌8写请假条\",\"id\":\"54BBFC7C89D80F67863B964A8E912ADB\",\"original_weight\":\"2216331\",\"source_url\":\"https://www.baidu.com/s?wd=%E8%B4%BE%E7%8E%B2%E4%B8%BA%E7%BC%BA%E5%B8%AD%E7%8E%8B%E7%89%8C8%E5%86%99%E8%AF%B7%E5%81%87%E6%9D%A1&sa=fyb_news&rsv_dl=fyb_news\",\"source_name\":\"百度风云榜\"},{\"topic\":\"拜登：美国可同时支持以色列乌克兰\",\"id\":\"F51C1D2F5D64617CAA5BE5EEEF71A348\",\"original_weight\":\"2188548\",\"source_url\":\"https://www.baidu.com/s?wd=%E6%8B%9C%E7%99%BB%EF%BC%9A%E7%BE%8E%E5%9B%BD%E5%8F%AF%E5%90%8C%E6%97%B6%E6%94%AF%E6%8C%81%E4%BB%A5%E8%89%B2%E5%88%97%E4%B9%8C%E5%85%8B%E5%85%B0&sa=fyb_news&rsv_dl=fyb_news\",\"source_name\":\"百度风云榜\"},{\"topic\":\"上海宝格丽酒店拟被挂牌出售\",\"id\":\"B580387BF5C74B44AE521A8B625534F3\",\"original_weight\":\"2049199\",\"source_url\":\"https://www.baidu.com/s?wd=%E4%B8%8A%E6%B5%B7%E5%AE%9D%E6%A0%BC%E4%B8%BD%E9%85%92%E5%BA%97%E6%8B%9F%E8%A2%AB%E6%8C%82%E7%89%8C%E5%87%BA%E5%94%AE&sa=fyb_news&rsv_dl=fyb_news\",\"source_name\":\"百度风云榜\"}]', '[{\"x\":\"开拓造福各国、惠及世界的幸福路\",\"value\":\"4942221\"},{\"x\":\"2349名缅北电诈嫌犯移交中方\",\"value\":\"4845552\"},{\"x\":\"妈妈想陪女儿上大学当上食堂阿姨\",\"value\":\"4643547\"},{\"x\":\"长期处于消极思维更易患老年痴呆\",\"value\":\"4513555\"},{\"x\":\"女童在小区遭恶犬围咬 警方通报\",\"value\":\"4477369\"},{\"x\":\"美国国务卿布林肯再次访问以色列\",\"value\":\"4376086\"},{\"x\":\"济南女生宿舍内死亡？当地辟谣\",\"value\":\"4208174\"},{\"x\":\"巴勒斯坦男孩：我们在这里长不大\",\"value\":\"4155108\"},{\"x\":\"谷村新司作品曾被邓丽君翻唱\",\"value\":\"4077322\"},{\"x\":\"成都2岁女童遭烈犬撕咬入院治疗\",\"value\":\"3937228\"},{\"x\":\"第一批援助物资进入加沙\",\"value\":\"3846125\"},{\"x\":\"洛杉矶奥运会确认新增五个项目\",\"value\":\"3770723\"},{\"x\":\"饭圈女孩倒卖明星航班信息被判刑\",\"value\":\"3614431\"},{\"x\":\"体育局介入调查马拉松选手冲刺被挡\",\"value\":\"3588775\"},{\"x\":\"加沙地带近百万巴勒斯坦人流离失所\",\"value\":\"3484934\"},{\"x\":\"巴以冲突已致超4200人死亡\",\"value\":\"3338523\"},{\"x\":\"外交部：建议在以中国公民尽快回国\",\"value\":\"3246666\"},{\"x\":\"以色列否认“停火允许援助进加沙”\",\"value\":\"3199280\"},{\"x\":\"郑州有一条“唐人街”？管理方回应\",\"value\":\"3005049\"},{\"x\":\"东三省省会气温集体跌破0度\",\"value\":\"2974901\"},{\"x\":\"顾客肯德基炸鸡中吃出蛆虫\",\"value\":\"2887288\"},{\"x\":\"法拉第未来市值仅0.2亿美元\",\"value\":\"2748250\"},{\"x\":\"盗割贵州2600年古楠木 三人获刑\",\"value\":\"2604595\"},{\"x\":\"足协新任执委会成员背景介绍\",\"value\":\"2577359\"},{\"x\":\"专家建议住宅70年产权改为永久\",\"value\":\"2405178\"},{\"x\":\"俄罗斯对自日本进口水产品实施限制\",\"value\":\"2392108\"},{\"x\":\"贾玲为缺席王牌8写请假条\",\"value\":\"2259038\"},{\"x\":\"拜登：美国可同时支持以色列乌克兰\",\"value\":\"2132758\"}]', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '[{\"topic\":\"传递中国声音\",\"source_url\":\"https://weixin.sogou.com/weixin?type=2&ie=utf8&s_from=hotnews&query=%E4%BC%A0%E9%80%92%E4%B8%AD%E5%9B%BD%E5%A3%B0%E9%9F%B3\",\"source_name\":\"微信热词\",\"original_weight\":\"1067394\"},{\"topic\":\"时刻保持解决大党独有难题的清醒和坚定\",\"source_url\":\"https://weixin.sogou.com/weixin?type=2&ie=utf8&s_from=hotnews&query=%E6%97%B6%E5%88%BB%E4%BF%9D%E6%8C%81%E8%A7%A3%E5%86%B3%E5%A4%A7%E5%85%9A%E7%8B%AC%E6%9C%89%E9%9A%BE%E9%A2%98%E7%9A%84%E6%B8%85%E9%86%92%E5%92%8C%E5%9D%9A%E5%AE%9A\",\"source_name\":\"微信热词\",\"original_weight\":\"952969\"},{\"topic\":\"坚持改革创新发扬斗争精神\",\"source_url\":\"https://weixin.sogou.com/weixin?type=2&ie=utf8&s_from=hotnews&query=%E5%9D%9A%E6%8C%81%E6%94%B9%E9%9D%A9%E5%88%9B%E6%96%B0%E5%8F%91%E6%89%AC%E6%96%97%E4%BA%89%E7%B2%BE%E7%A5%9E\",\"source_name\":\"微信热词\",\"original_weight\":\"861550\"},{\"topic\":\"全力以赴端牢端稳中国饭碗\",\"source_url\":\"https://weixin.sogou.com/weixin?type=2&ie=utf8&s_from=hotnews&query=%E5%85%A8%E5%8A%9B%E4%BB%A5%E8%B5%B4%E7%AB%AF%E7%89%A2%E7%AB%AF%E7%A8%B3%E4%B8%AD%E5%9B%BD%E9%A5%AD%E7%A2%97\",\"source_name\":\"微信热词\",\"original_weight\":\"721324\"},{\"topic\":\"更好统筹当前和长远 ——形成共促高质量发展的合力\",\"source_url\":\"https://weixin.sogou.com/weixin?type=2&ie=utf8&s_from=hotnews&query=%E6%9B%B4%E5%A5%BD%E7%BB%9F%E7%AD%B9%E5%BD%93%E5%89%8D%E5%92%8C%E9%95%BF%E8%BF%9C+%E2%80%94%E2%80%94%E5%BD%A2%E6%88%90%E5%85%B1%E4%BF%83%E9%AB%98%E8%B4%A8%E9%87%8F%E5%8F%91%E5%B1%95%E7%9A%84%E5%90%88%E5%8A%9B\",\"source_name\":\"微信热词\",\"original_weight\":\"675728\"}]', '[{\"topic\":\"华为 谢霆锋\",\"source_url\":\"https://www.douyin.com/search/%E5%8D%8E%E4%B8%BA+%E8%B0%A2%E9%9C%86%E9%94%8B\",\"source_name\":\"抖音\",\"original_weight\":\"11673000\"},{\"topic\":\"降温穿搭主打一个时尚保暖\",\"source_url\":\"https://www.douyin.com/search/%E9%99%8D%E6%B8%A9%E7%A9%BF%E6%90%AD%E4%B8%BB%E6%89%93%E4%B8%80%E4%B8%AA%E6%97%B6%E5%B0%9A%E4%BF%9D%E6%9A%96\",\"source_name\":\"抖音\",\"original_weight\":\"11280000\"},{\"topic\":\"中国钢产量占全球总产量53.9％\",\"source_url\":\"https://www.douyin.com/search/%E4%B8%AD%E5%9B%BD%E9%92%A2%E4%BA%A7%E9%87%8F%E5%8D%A0%E5%85%A8%E7%90%83%E6%80%BB%E4%BA%A7%E9%87%8F53.9%EF%BC%85\",\"source_name\":\"抖音\",\"original_weight\":\"11157000\"},{\"topic\":\"大连马拉松组委会致歉\",\"source_url\":\"https://www.douyin.com/search/%E5%A4%A7%E8%BF%9E%E9%A9%AC%E6%8B%89%E6%9D%BE%E7%BB%84%E5%A7%94%E4%BC%9A%E8%87%B4%E6%AD%89\",\"source_name\":\"抖音\",\"original_weight\":\"11003000\"},{\"topic\":\"各国政要抵京出席一带一路论坛\",\"source_url\":\"https://www.douyin.com/search/%E5%90%84%E5%9B%BD%E6%94%BF%E8%A6%81%E6%8A%B5%E4%BA%AC%E5%87%BA%E5%B8%AD%E4%B8%80%E5%B8%A6%E4%B8%80%E8%B7%AF%E8%AE%BA%E5%9D%9B\",\"source_name\":\"抖音\",\"original_weight\":\"10688000\"}]', '[{\"topic\":\"跟着福岛渔民出海，捞上来的鱼检测结果如何？\",\"source_url\":\"https://search.bilibili.com/all?keyword=跟着福岛渔民出海，捞上来的鱼检测结果如何？\",\"source_name\":\"哔哩哔哩\",\"original_weight\":\"8151000\"},{\"topic\":\"一套房能买断你的理想吗？\",\"source_url\":\"https://search.bilibili.com/all?keyword=一套房能买断你的理想吗？\",\"source_name\":\"哔哩哔哩\",\"original_weight\":\"5156000\"},{\"topic\":\"领导说干不了就滚\",\"source_url\":\"https://search.bilibili.com/all?keyword=领导说干不了就滚\",\"source_name\":\"哔哩哔哩\",\"original_weight\":\"4632000\"},{\"topic\":\"男人陷入无限次复活，就为了救自己美丽善良的爱妻，超燃科幻动作\",\"source_url\":\"https://search.bilibili.com/all?keyword=男人陷入无限次复活，就为了救自己美丽善良的爱妻，超燃科幻动作\",\"source_name\":\"哔哩哔哩\",\"original_weight\":\"8989000\"},{\"topic\":\"【何同学】鸿蒙的最后一块拼图｜Mate60 系列体验\",\"source_url\":\"https://search.bilibili.com/all?keyword=【何同学】鸿蒙的最后一块拼图｜Mate60 系列体验\",\"source_name\":\"哔哩哔哩\",\"original_weight\":\"2564000\"}]', '[{\"topic\":\"习近平“一带一路”万里行\",\"source_url\":\"https://v.qq.com/x/search/?q=习近平“一带一路”万里行\",\"source_name\":\"腾讯新闻\",\"original_weight\":\"589000\"},{\"topic\":\"中国学者：对巴以冲突 我没那么悲观\",\"source_url\":\"https://v.qq.com/x/search/?q=中国学者：对巴以冲突 我没那么悲观\",\"source_name\":\"腾讯新闻\",\"original_weight\":\"1556000\"},{\"topic\":\"华春莹：过去的悲剧不应再重现\",\"source_url\":\"https://v.qq.com/x/search/?q=华春莹：过去的悲剧不应再重现\",\"source_name\":\"腾讯新闻\",\"original_weight\":\"1502000\"},{\"topic\":\"粮食里的绝美中国色\",\"source_url\":\"https://v.qq.com/x/search/?q=粮食里的绝美中国色\",\"source_name\":\"腾讯新闻\",\"original_weight\":\"1487000\"},{\"topic\":\"海底捞拒绝给棉花娃娃过生日引热议\",\"source_url\":\"https://v.qq.com/x/search/?q=海底捞拒绝给棉花娃娃过生日引热议\",\"source_name\":\"腾讯新闻\",\"original_weight\":\"1472000\"}]', '[{\"topic\":\"月薪两万的新中产，都在沉迷这种快乐\",\"source_url\":\"https://www.36kr.com/search/articles/%E6%9C%88%E8%96%AA%E4%B8%A4%E4%B8%87%E7%9A%84%E6%96%B0%E4%B8%AD%E4%BA%A7%EF%BC%8C%E9%83%BD%E5%9C%A8%E6%B2%89%E8%BF%B7%E8%BF%99%E7%A7%8D%E5%BF%AB%E4%B9%90\",\"source_name\":\"36氪\",\"original_weight\":\"1046384\"},{\"topic\":\"90后回流东北，花10万块自建房：没有房贷，身体都变好了\",\"source_url\":\"https://www.36kr.com/search/articles/90%E5%90%8E%E5%9B%9E%E6%B5%81%E4%B8%9C%E5%8C%97%EF%BC%8C%E8%8A%B110%E4%B8%87%E5%9D%97%E8%87%AA%E5%BB%BA%E6%88%BF%EF%BC%9A%E6%B2%A1%E6%9C%89%E6%88%BF%E8%B4%B7%EF%BC%8C%E8%BA%AB%E4%BD%93%E9%83%BD%E5%8F%98%E5%A5%BD%E4%BA%86\",\"source_name\":\"36氪\",\"original_weight\":\"922961\"},{\"topic\":\"昔日的“行业灯塔”5000亿卖了\",\"source_url\":\"https://www.36kr.com/search/articles/%E6%98%94%E6%97%A5%E7%9A%84%E2%80%9C%E8%A1%8C%E4%B8%9A%E7%81%AF%E5%A1%94%E2%80%9D5000%E4%BA%BF%E5%8D%96%E4%BA%86\",\"source_name\":\"36氪\",\"original_weight\":\"862925\"},{\"topic\":\"火爆外网，23岁华人博士修复22年历史漏洞，网友：我喜欢这个故事\",\"source_url\":\"https://www.36kr.com/search/articles/%E7%81%AB%E7%88%86%E5%A4%96%E7%BD%91%EF%BC%8C23%E5%B2%81%E5%8D%8E%E4%BA%BA%E5%8D%9A%E5%A3%AB%E4%BF%AE%E5%A4%8D22%E5%B9%B4%E5%8E%86%E5%8F%B2%E6%BC%8F%E6%B4%9E%EF%BC%8C%E7%BD%91%E5%8F%8B%EF%BC%9A%E6%88%91%E5%96%9C%E6%AC%A2%E8%BF%99%E4%B8%AA%E6%95%85%E4%BA%8B\",\"source_name\":\"36氪\",\"original_weight\":\"773927\"},{\"topic\":\"诺基亚，这下真成 “山寨机” 了\",\"source_url\":\"https://www.36kr.com/search/articles/%E8%AF%BA%E5%9F%BA%E4%BA%9A%EF%BC%8C%E8%BF%99%E4%B8%8B%E7%9C%9F%E6%88%90+%E2%80%9C%E5%B1%B1%E5%AF%A8%E6%9C%BA%E2%80%9D+%E4%BA%86\",\"source_name\":\"36氪\",\"original_weight\":\"603152\"}]', '[{\"publish_time\":\"2023-10-16 17:02:36\",\"rank\":1,\"topic\":\"国务院：推动内蒙古高质量发展 奋力书写中国式现代化新篇章\",\"source_url\":\"http://finance.eastmoney.com/a/202310162870934775.html\",\"original_weight\":100000,\"source_name\":\"东方财富网\"},{\"publish_time\":\"2023-10-16 15:52:11\",\"rank\":2,\"topic\":\"美政府将采取更多措施限制对华芯片出口？外交部回应\",\"source_url\":\"http://finance.eastmoney.com/a/202310162870883460.html\",\"original_weight\":100000,\"source_name\":\"东方财富网\"},{\"publish_time\":\"2023-10-16 17:59:44\",\"rank\":3,\"topic\":\"英雄互娱全资收购启虹游戏\",\"source_url\":\"http://finance.eastmoney.com/a/202310162870986690.html\",\"original_weight\":100000,\"source_name\":\"东方财富网\"},{\"publish_time\":\"2023-10-16 17:59:28\",\"rank\":4,\"topic\":\"【高端访谈】共建“一带一路”使非洲受益颇多——访埃及前总理谢拉夫\",\"source_url\":\"http://finance.eastmoney.com/a/202310162870986859.html\",\"original_weight\":100000,\"source_name\":\"东方财富网\"},{\"publish_time\":\"2023-10-16 17:59:28\",\"rank\":5,\"topic\":\"京津冀三地深化工伤保险工作合作\",\"source_url\":\"http://finance.eastmoney.com/a/202310162870986784.html\",\"original_weight\":100000,\"source_name\":\"东方财富网\"},{\"publish_time\":\"2023-10-16 17:58:50\",\"rank\":6,\"topic\":\"陈吉宁分别会见日本野村控股董事长永井浩二 比利时贝卡尔特董事会主席丁有仁\",\"source_url\":\"http://finance.eastmoney.com/a/202310162870985793.html\",\"original_weight\":100000,\"source_name\":\"东方财富网\"},{\"publish_time\":\"2023-10-16 17:58:14\",\"rank\":7,\"topic\":\"作案30余起！男子骑着偷来的摩托车盗窃电线卖钱\",\"source_url\":\"http://finance.eastmoney.com/a/202310162870985351.html\",\"original_weight\":100000,\"source_name\":\"东方财富网\"},{\"publish_time\":\"2023-10-16 17:58:13\",\"rank\":8,\"topic\":\"南向资金今日净买入超40亿港元 盈富基金获净买入居前\",\"source_url\":\"http://finance.eastmoney.com/a/202310162870985441.html\",\"original_weight\":100000,\"source_name\":\"东方财富网\"},{\"publish_time\":\"2023-10-16 17:55:52\",\"rank\":9,\"topic\":\"山西绛县西吴壁遗址发现一座兽骨坑 系最早“牛羊猪狗”祭牲组合\",\"source_url\":\"http://finance.eastmoney.com/a/202310162870983560.html\",\"original_weight\":100000,\"source_name\":\"东方财富网\"},{\"publish_time\":\"2023-10-16 17:55:52\",\"rank\":10,\"topic\":\"台媒看大陆：福建南平——湛卢宝剑诞生地\",\"source_url\":\"http://finance.eastmoney.com/a/202310162870983295.html\",\"original_weight\":100000,\"source_name\":\"东方财富网\"},{\"publish_time\":\"2023-10-16 17:55:51\",\"rank\":11,\"topic\":\"11国570余名高校师生齐聚云南 以体育会友\",\"source_url\":\"http://finance.eastmoney.com/a/202310162870982948.html\",\"original_weight\":100000,\"source_name\":\"东方财富网\"},{\"publish_time\":\"2023-10-16 17:49:45\",\"rank\":12,\"topic\":\"前三季利率债发行逼近20万亿 创历史新高！机构预测四季度或迎3.45万亿净融资 投资承压？\",\"source_url\":\"http://finance.eastmoney.com/a/202310162870980522.html\",\"original_weight\":100000,\"source_name\":\"东方财富网\"},{\"publish_time\":\"2023-10-16 17:44:51\",\"rank\":13,\"topic\":\"家庭资产将迎信托化浪潮！再谈行业十年来最大利好——家庭服务信托\",\"source_url\":\"http://finance.eastmoney.com/a/202310162870977598.html\",\"original_weight\":100000,\"source_name\":\"东方财富网\"},{\"publish_time\":\"2023-10-16 17:44:39\",\"rank\":14,\"topic\":\"巴布亚新几内亚（中国香港）总商会成立五周年志庆在港举行\",\"source_url\":\"http://finance.eastmoney.com/a/202310162870976762.html\",\"original_weight\":100000,\"source_name\":\"东方财富网\"},{\"publish_time\":\"2023-10-16 17:42:30\",\"rank\":15,\"topic\":\"国务院：支持按程序申请设立中国（内蒙古）自由贸易试验区\",\"source_url\":\"http://finance.eastmoney.com/a/202310162870975342.html\",\"original_weight\":100000,\"source_name\":\"东方财富网\"},{\"publish_time\":\"2023-10-16 17:42:30\",\"rank\":16,\"topic\":\"国务院：支持包头稀土产品交易所依法合规建设面向全国的稀土产品交易中心\",\"source_url\":\"http://finance.eastmoney.com/a/202310162870975262.html\",\"original_weight\":100000,\"source_name\":\"东方财富网\"},{\"publish_time\":\"2023-10-16 17:42:30\",\"rank\":17,\"topic\":\"国务院：推进内蒙古大型风电光伏基地建设 研究推动浑善达克沙地至京津冀输电通道建设\",\"source_url\":\"http://finance.eastmoney.com/a/202310162870975172.html\",\"original_weight\":100000,\"source_name\":\"东方财富网\"},{\"publish_time\":\"2023-10-16 17:42:29\",\"rank\":18,\"topic\":\"国务院：加快推进中蒙俄中线铁路升级改造可行性研究\",\"source_url\":\"http://finance.eastmoney.com/a/202310162870975436.html\",\"original_weight\":100000,\"source_name\":\"东方财富网\"},{\"publish_time\":\"2023-10-16 17:40:36\",\"rank\":19,\"topic\":\"北京市经信局印发人工智能算力券实施方案\",\"source_url\":\"http://finance.eastmoney.com/a/202310162870973194.html\",\"original_weight\":100000,\"source_name\":\"东方财富网\"},{\"publish_time\":\"2023-10-16 17:40:35\",\"rank\":20,\"topic\":\"北京市发布人工智能算力券实施方案 切实降低企业算力使用成本\",\"source_url\":\"http://finance.eastmoney.com/a/202310162870972860.html\",\"original_weight\":100000,\"source_name\":\"东方财富网\"}]', '[{\"publish_time\":\"2023-10-16 00:00:00\",\"rank\":1,\"topic\":\"国务院关于推动内蒙古高质量发展奋力书写中国式现代化新篇章的意见\",\"source_url\":\"https://www.gov.cn/zhengce/content/202310/content_6909411.htm\",\"original_weight\":100000,\"source_name\":\"国务院\"},{\"publish_time\":\"2023-10-15 00:00:00\",\"rank\":2,\"topic\":\"中共中央印发《干部教育培训工作条例》\",\"source_url\":\"../202310/content_6909282.htm\",\"original_weight\":100000,\"source_name\":\"国务院\"},{\"publish_time\":\"2023-10-13 00:00:00\",\"rank\":3,\"topic\":\"中办国办关于调整工业和信息化部职责机构编制的通知\",\"source_url\":\"../202310/content_6908734.htm\",\"original_weight\":100000,\"source_name\":\"国务院\"},{\"publish_time\":\"2023-10-13 00:00:00\",\"rank\":4,\"topic\":\"中办国办关于调整生态环境部职责机构编制的通知\",\"source_url\":\"../202310/content_6908737.htm\",\"original_weight\":100000,\"source_name\":\"国务院\"},{\"publish_time\":\"2023-10-13 00:00:00\",\"rank\":5,\"topic\":\"中办国办关于调整国家卫生健康委员会职责机构编制的通知\",\"source_url\":\"../202310/content_6908740.htm\",\"original_weight\":100000,\"source_name\":\"国务院\"}]');

-- ----------------------------
-- Table structure for systemlog
-- ----------------------------
DROP TABLE IF EXISTS `systemlog`;
CREATE TABLE `systemlog`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '自增长id',
  `user_browser` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户浏览器',
  `user_id` int NULL DEFAULT NULL COMMENT '用户id',
  `user_browser_version` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户浏览器版本',
  `operatingSystem` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作系统',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名称',
  `loginip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登陆ip',
  `module` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模块',
  `submodule` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '子模块',
  `type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类型',
  `createtime` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13659 CHARACTER SET = utf8mb4 COLLATE = utf8_general_ci COMMENT = '系统操作日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of systemlog
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户id',
  `telephone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `end_login_time` datetime NULL DEFAULT NULL COMMENT '最后登陆时间',
  `status` int NULL DEFAULT 1 COMMENT '状态（1代表正常 2代表注销）',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户姓名',
  `wechat_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '微信号',
  `openid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'openid',
  `login_count` int NULL DEFAULT 0 COMMENT '登录次数',
  `identity` int NULL DEFAULT NULL COMMENT '身份标识',
  `organization_id` bigint NULL DEFAULT NULL COMMENT '所属机构id',
  `user_type` int NULL DEFAULT NULL COMMENT '用户类型(0普通用户,1渠道商,2渠道专员,3管理员)',
  `user_level` int NULL DEFAULT NULL COMMENT '用户等级',
  `wechatflag` int NULL DEFAULT NULL COMMENT '微信绑定状态（1代表绑定 0代表捆绑）',
  `is_online` int NULL DEFAULT NULL COMMENT '是否在线',
  `nlp_secret_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'nlp平台id',
  `nlp_secret_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'nlp平台key',
  `nlp_flag` int NULL DEFAULT 0 COMMENT 'nlp平台绑定状态(1代表已绑定，0代表未绑定)',
  `xie_secret_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '写作宝平台id',
  `xie_secret_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '写作宝平台key',
  `xie_flag` int NULL DEFAULT 0 COMMENT '写作宝平台绑定状态(1代表已绑定,0代表未绑定)',
  `term_of_validity` datetime NULL DEFAULT NULL COMMENT '有效期',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `union_user_telephone`(`telephone` ASC) USING BTREE COMMENT '电话号码唯一索引'
) ENGINE = InnoDB AUTO_INCREMENT = 98 CHARACTER SET = utf8mb4 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (97, '2022-02-14 17:22:13', 13900000000, '13900000000', '1ed91049c7697d6aaf7d1959e588e735', '13900000000@qq.com', '2023-10-16 19:55:03', 1, NULL, NULL, NULL, 3, NULL, NULL, 3, NULL, NULL, 2, NULL, NULL, 0, NULL, NULL, 0, '2025-01-19 00:00:00');

-- ----------------------------
-- Table structure for user_apply
-- ----------------------------
DROP TABLE IF EXISTS `user_apply`;
CREATE TABLE `user_apply`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `openid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'openid',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `telephone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号码',
  `industry` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '行业',
  `company` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '公司',
  `applytime` datetime NULL DEFAULT NULL,
  `dealstatus` int NULL DEFAULT 0 COMMENT '处理状态',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `openid`(`openid` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_apply
-- ----------------------------

-- ----------------------------
-- Table structure for user_log
-- ----------------------------
DROP TABLE IF EXISTS `user_log`;
CREATE TABLE `user_log`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户id',
  `article_public_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '日志id',
  `method_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '方法名称',
  `module_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模块名称',
  `submodule_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '子模块名称',
  `times` datetime NULL DEFAULT NULL COMMENT '开始时间',
  `timee` datetime NULL DEFAULT NULL COMMENT '结束时间',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名称',
  `organization_id` bigint NULL DEFAULT NULL COMMENT '组织id',
  `organization_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `status` int NULL DEFAULT NULL COMMENT '用户状态',
  `parameters` text CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '请求参数',
  `class_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类名',
  `module_id` int NULL DEFAULT NULL COMMENT '模块id',
  `submodule_id` int NULL DEFAULT NULL COMMENT '子模块id',
  `operation` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_log
-- ----------------------------

-- ----------------------------
-- Table structure for volume_monitor
-- ----------------------------
DROP TABLE IF EXISTS `volume_monitor`;
CREATE TABLE `volume_monitor`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `volume_monitor_id` bigint NULL DEFAULT NULL COMMENT '声量监测公共id',
  `project_id` bigint NULL DEFAULT NULL COMMENT '方案id',
  `time_period` int NULL DEFAULT NULL COMMENT '时间周期(1:24h 2:3d 3:7d 4:15d)',
  `keyword_emotion_statistical` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '关键词情感分析数据统计分布',
  `keyword_source_distribution` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '关键词数据来源分布',
  `keyword_news_rank` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '关键词资讯数量排名',
  `topic_cluster_analysis` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '主题观点聚类分析',
  `keyword_emotion_trend` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '关键词情感分析数据走势',
  `highword_cloud` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '关键词高频分布统计',
  `keyword_exposure_rank` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '关键词曝光度环比排行',
  `keyword_correlation_news` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '热点内容聚类分析排名',
  `user_portrait_label` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '用户画像标签',
  `social_user_volume_rank` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '社交网络用户声量排名',
  `media_user_volume_rank` longtext CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '自媒体用户声量排名',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `project_id`(`project_id` ASC, `time_period` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 209516 CHARACTER SET = utf8mb4 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of volume_monitor
-- ----------------------------

-- ----------------------------
-- Table structure for warning_article
-- ----------------------------
DROP TABLE IF EXISTS `warning_article`;
CREATE TABLE `warning_article`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '记录创建时间',
  `warning_article_id` bigint NULL DEFAULT NULL COMMENT '预警内容公共id',
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户id',
  `popup_id` bigint NULL DEFAULT NULL COMMENT '预警弹窗公共id',
  `popup_content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预警弹窗内容',
  `popup_time` datetime NULL DEFAULT NULL COMMENT '预警弹窗时间',
  `article_id` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '内容id',
  `article_time` datetime NULL DEFAULT NULL COMMENT '内容时间',
  `article_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '内容标题',
  `article_emotion` int NULL DEFAULT NULL COMMENT '内容情感(1:正面，2:中性，3:负面)',
  `status` int NULL DEFAULT 0 COMMENT '状态 ( 0:未弹窗，1:已弹窗 )',
  `project_id` bigint NULL DEFAULT NULL COMMENT '方案id',
  `read_status` int NULL DEFAULT NULL COMMENT '阅读状态 ( 0:未读，1:已读 )',
  `article_detail` text CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL COMMENT '资讯补充字段json',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_id`(`user_id` ASC, `project_id` ASC, `article_id` ASC) USING BTREE,
  UNIQUE INDEX `popup_id`(`popup_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4023702 CHARACTER SET = utf8mb4 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of warning_article
-- ----------------------------

-- ----------------------------
-- Table structure for warning_setting
-- ----------------------------
DROP TABLE IF EXISTS `warning_setting`;
CREATE TABLE `warning_setting`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `warning_setting_id` bigint NULL DEFAULT NULL COMMENT '预警设置公共id',
  `project_id` bigint NULL DEFAULT NULL COMMENT '方案id',
  `warning_status` int NULL DEFAULT NULL COMMENT '预警开关 ( 0:关，1:开 )',
  `warning_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预警名称',
  `warning_word` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预警词(英文逗号分隔)',
  `warning_classify` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '来源类型(1-11)(英文逗号分隔)',
  `warning_content` int NULL DEFAULT NULL COMMENT '预警内容(0:全部 1:敏感)',
  `warning_similar` int NULL DEFAULT NULL COMMENT '相似文章合并（0：取消合并 1：合并）',
  `warning_match` int NULL DEFAULT NULL COMMENT '匹配方式（1：全文 2：标题 3：正文）',
  `warning_deduplication` int NULL DEFAULT NULL COMMENT '预警去重（0：关闭 1：开启）',
  `warning_source` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预警来源json（[type]1:系统推送 2：邮箱推送 [email]:邮箱地址，可为空）',
  `warning_receive_time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '接收时间json [start]:开始时间 [end]:结束时间',
  `weekend_warning` int NULL DEFAULT NULL COMMENT '周末预警（0：关闭 1：开启）',
  `warning_interval` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预警间隔json（[type]1:实时预警 2：定时预警 [time]:时间，可为空）',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `project_id`(`project_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 921 CHARACTER SET = utf8mb4 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of warning_setting
-- ----------------------------
INSERT INTO `warning_setting` VALUES (917, '2022-02-14 17:26:23', 1493154645428801536, 1493154645181337600, 0, '预警', '', '1,2,3,4,5,6,7,8,9,10,11', 0, 0, 2, 0, '{\"type\":\"1\",\"email\":\"\"}', '{\"start\":\"00:00\",\"end\":\"23:00\"}', 1, '{\"type\":\"1\",\"time\":\"1\"}');
INSERT INTO `warning_setting` VALUES (918, '2022-02-14 17:28:23', 1493155148850139136, 1493155148443291648, 0, '预警', '', '1,2,3,4,5,6,7,8,9,10,11', 0, 0, 2, 0, '{\"type\":\"1\",\"email\":\"\"}', '{\"start\":\"00:00\",\"end\":\"23:00\"}', 1, '{\"type\":\"1\",\"time\":\"1\"}');
INSERT INTO `warning_setting` VALUES (919, '2022-02-14 17:31:24', 1493155905775210496, 1493155905573883904, 0, '预警', '', '1,2,3,4,5,6,7,8,9,10,11', 0, 0, 2, 0, '{\"type\":\"1\",\"email\":\"\"}', '{\"start\":\"00:00\",\"end\":\"23:00\"}', 1, '{\"type\":\"1\",\"time\":\"1\"}');
INSERT INTO `warning_setting` VALUES (920, '2022-02-14 17:31:59', 1493156056325558272, 1493156056132620288, 0, '预警', '', '1,2,3,4,5,6,7,8,9,10,11', 0, 0, 2, 0, '{\"type\":\"1\",\"email\":\"\"}', '{\"start\":\"00:00\",\"end\":\"23:00\"}', 1, '{\"type\":\"1\",\"time\":\"1\"}');

-- ----------------------------
-- Table structure for wechatqrcode
-- ----------------------------
DROP TABLE IF EXISTS `wechatqrcode`;
CREATE TABLE `wechatqrcode`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `telephone` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号码',
  `ticket` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '二维码地址',
  `updatetime` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uniquestr`(`telephone` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 32124 CHARACTER SET = utf8mb4 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wechatqrcode
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
