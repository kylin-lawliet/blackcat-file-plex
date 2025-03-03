package com.blackcat.scaffolding.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 用户对象 @author : zhangdahui  2025/2/22 下午2:40
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class SysUser extends BaseEntity{

    /**
     * 主键
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;

    /**
     * 登录名称
     */
    private String loginName;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 性别
     */
    private String sex;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 电话
     */
    private String telephone;

    /**
     * 微信
     */
    private String weChat;

    /**
     * QQ
     */
    private String qq;

    /**
     * 状态
     */
    private String validStatus;
}
