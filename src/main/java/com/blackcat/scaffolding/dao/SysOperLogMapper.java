package com.blackcat.scaffolding.dao;

import com.blackcat.scaffolding.entity.SysOperLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
/**
 * <p> 操作日志记录 Mapper 接口
 * @author blackcat 2024-08-23
 */
@Mapper
public interface SysOperLogMapper extends BaseMapper<SysOperLog> {

}
