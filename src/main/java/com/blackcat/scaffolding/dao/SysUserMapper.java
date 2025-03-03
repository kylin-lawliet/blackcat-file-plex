package com.blackcat.scaffolding.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blackcat.scaffolding.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> 用户表 Mapper 接口
 * @author zhangdahui 2025-02-24
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

}
