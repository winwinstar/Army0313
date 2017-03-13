CREATE TABLE seckill(
`seckill_id` bigint NOT NULL AUTO_INCREMENT COMMENT '商品库存id',
`name` VARCHAR(120) NOT NULL COMMENT '商品名称',
`number` int NOT NULL COMMENT '库存数量',
`start_time` TIMESTAMP not null comment '秒杀开始时间',
`end_time` TIMESTAMP not null comment '秒杀结束时间',
`create_time` TIMESTAMP not null DEFAULT CURRENT_TIMESTAMP comment '创建时间',
PRIMARY KEY (seckill_id),
key idx_start_time(start_time),
key idx_end_time(end_time),
key idx_create_time(create_time)
)ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 comment '秒杀库存表';


insert into
seckill (name,number,start_time,end_time)
VALUES
('10元秒杀iPhone 7 Plus',100,'2017-01-27 00:00:00','2017-01-28 00:00:00'),
('9元秒杀iPhone 7 ',200,'2017-01-27 00:00:00','2017-01-28 00:00:00'),
('8元秒杀iPhone 6 Plus',300,'2017-01-27 00:00:00','2017-01-28 00:00:00');

create table success_kill(
`seckill_id` bigint not null comment '秒杀商品id',
`user_phone` bigint not null comment '用户手机',
`state` tinyint not null DEFAULT -1 comment '状态表示',
`create_time` TIMESTAMP not null comment '秒杀成功时间',
PRIMARY KEY (seckill_id,user_phone), /*联合主键*/
KEY idx_create_time (create_time)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '秒杀库存表';
