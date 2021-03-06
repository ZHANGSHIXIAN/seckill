--数据库初始化脚本
--创建数据库
create  database seckill;
use seckill;

create table seckill(
`seckill_id` bigint NOT NULL AUTO_INCREMENT COMMENT '商品库存id',
`name` varchar(120) NOT NULL COMMENT '商品名称',
`number` int NOT NULL COMMENT '库存数量',
`start_time` timestamp NOT NULL COMMENT '秒杀开始时间',
`end_time` timestamp NOT NULL COMMENT '秒杀结束时间',
`create_time` timestamp NOT NULL default current_timestamp COMMENT '创建时间',
PRIMARY KEY (seckill_id),
key idx_start_time(start_time),
key idx_end_time(end_time),
key idx_create_time(create_time)
)ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='秒杀库存表';

--初始化数据
insert into
  seckill(name,number,start_time,end_time)
values
  ('1000元秒杀iphone8',100,'2018-07-18 00:00:00','2018-07-19 00:00:00'),
  ('500元秒杀ipad4',200,'2018-07-18 00:00:00','2018-07-19 00:00:00'),
  ('200元秒杀小米6',300,'2018-07-18 00:00:00','2018-07-18 00:00:00'),
  ('300元秒杀平衡车',500,'2018-07-18 00:00:00','2018-07-19 00:00:00');

--秒杀成功明细表
create table success_killed(
  `seckill_id` bitint NOT NULL AUTO_INCREMENT COMMENT '秒杀商品id',
  `user_phone` bitint NOT NULL COMMENT '用户手机号',
  `state` tinyint NOT NULL default -1 COMMENT '状态表示:-1无效 0成功 1已付款 2已发货 3已收货 4已退款 5申请退款',
  `create_time` timestamp NOT NULL COMMENT '创建时间',
  PRIMARY KEY(seckill_id,user_phone),/* 联合主键 */
  key idx_create_time(create_time)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='秒杀成功明细表';


