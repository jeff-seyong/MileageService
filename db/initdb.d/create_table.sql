-- eventservice.review_event_history definition
use eventservice;
CREATE TABLE `review_event_history` (
                                        `event_history_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '리뷰 이벤트 히스토리 아이디',
                                        `user_id` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '리뷰 작성자 아이디',
                                        `place_id` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '리뷰가 작성된 장소 아이디',
                                        `review_id` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '리뷰 아이디',
                                        `action` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '리뷰 이벤트 종류',
                                        `contents_size` int(11) NOT NULL DEFAULT '0' COMMENT '리뷰 내용 길이',
                                        `photo_count` int(11) NOT NULL DEFAULT '0' COMMENT '첨부 이미지 개수',
                                        `point_policy` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '적용된 포인트 적립 정책',
                                        `register_date` datetime NOT NULL COMMENT '이벤트 등록일',
                                        `update_date` datetime DEFAULT NULL COMMENT '이벤트 수정일',
                                        PRIMARY KEY (`event_history_id`),
                                        KEY `review_event_history_user_id_IDX` (`user_id`,`place_id`,`action`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- eventservice.user_mileage definition

CREATE TABLE `user_mileage` (
                                `user_id` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '유저 아이디',
                                `mileage` int(11) NOT NULL DEFAULT '0' COMMENT '유저 마일리지',
                                `register_date` datetime NOT NULL COMMENT '유저 마일리지 정보 등록일',
                                `update_date` datetime DEFAULT NULL COMMENT '유저 마일리지 정보 수정일',
                                PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- eventservice.user_point_history definition

CREATE TABLE `user_point_history` (
                                      `point_history_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '유저 포인트 히스토리 아이디',
                                      `user_id` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '유저 아이디',
                                      `review_id` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '리뷰 아이디',
                                      `point` int(11) NOT NULL COMMENT '적립/차감 포인트',
                                      `reason` varchar(15) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '포인트 적립/차람 사유',
                                      `register_date` datetime NOT NULL COMMENT '포인트 적립/차감 등록일',
                                      `update_date` datetime DEFAULT NULL COMMENT '포인트 적립/차감 수정일',
                                      PRIMARY KEY (`point_history_id`),
                                      KEY `user_point_history_user_id_IDX` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;