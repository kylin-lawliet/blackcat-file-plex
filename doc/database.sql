drop table if exists sys_oper_log;
create table sys_oper_log (
      oper_id           bigint(20)      not null auto_increment    comment '日志主键',
      title             varchar(50)     default ''                 comment '模块标题',
      business_type     int(2)          default 0                  comment '业务类型（0其它 1新增 2修改 3删除）',
      method            varchar(100)    default ''                 comment '方法名称',
      request_method    varchar(10)     default ''                 comment '请求方式',
      operator_type     int(1)          default 0                  comment '操作类别（0其它 1后台用户 2手机端用户）',
      oper_name         varchar(50)     default ''                 comment '操作人员',
      dept_name         varchar(50)     default ''                 comment '部门名称',
      oper_url          varchar(255)    default ''                 comment '请求URL',
      oper_ip           varchar(128)    default ''                 comment '主机地址',
      oper_location     varchar(255)    default ''                 comment '操作地点',
      oper_param        varchar(2000)   default ''                 comment '请求参数',
      json_result       varchar(2000)   default ''                 comment '返回参数',
      status            int(1)          default 0                  comment '操作状态（0正常 1异常）',
      error_msg         varchar(2000)   default ''                 comment '错误消息',
      oper_time         datetime                                   comment '操作时间',
      cost_time         bigint(20)      default 0                  comment '消耗时间',
      primary key (oper_id),
      key idx_sys_oper_log_bt (business_type),
      key idx_sys_oper_log_s  (status),
      key idx_sys_oper_log_ot (oper_time)
) engine=innodb auto_increment=100 comment = '操作日志记录';

drop table if exists sys_user;
CREATE TABLE `sys_user` (
                            `user_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                            `login_name` varchar(30) NOT NULL COMMENT '登录名称',
                            `password` varchar(50) NOT NULL COMMENT '登录密码',
                            `user_name` varchar(100) DEFAULT NULL COMMENT '用户名称',
                            `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
                            `telephone` varchar(20) DEFAULT NULL COMMENT '电话',
                            `we_chat` varchar(255) DEFAULT NULL COMMENT '微信',
                            `qq` varchar(255) DEFAULT NULL COMMENT 'QQ',
                            `valid_status` varchar(2) NOT NULL COMMENT '状态',
                            `create_time` datetime NOT NULL COMMENT '创建时间',
                            `update_time` datetime DEFAULT NULL COMMENT '修改时间',
                            `create_by` varchar(36) NOT NULL COMMENT '创建者',
                            `update_by` varchar(36) DEFAULT NULL COMMENT '更新者',
                            `remark` varchar(500) DEFAULT NULL COMMENT '备注',
                            `sex` varchar(10) NOT NULL COMMENT '性别',
                            PRIMARY KEY (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3 COMMENT='用户表';

INSERT INTO sys_user
(user_id, login_name, password, user_name, email, telephone, we_chat, qq, valid_status, create_time, update_time, create_by, update_by, remark, sex)
VALUES(1, 'admin', '123456', '管理员', NULL, '231', NULL, NULL, '1', '2025-03-01 10:22:20', NULL, '1', NULL, NULL, '1');
INSERT INTO sys_user
(user_id, login_name, password, user_name, email, telephone, we_chat, qq, valid_status, create_time, update_time, create_by, update_by, remark, sex)
VALUES(2, 'demo', '!QAZ3edc', '测试用户', NULL, NULL, NULL, NULL, '1', '2025-02-22 00:00:00', NULL, '1', NULL, NULL, '0');
