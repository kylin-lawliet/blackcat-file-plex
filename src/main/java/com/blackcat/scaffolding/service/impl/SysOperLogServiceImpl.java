package com.blackcat.scaffolding.service.impl;

import com.blackcat.scaffolding.entity.SysOperLog;
import com.blackcat.scaffolding.dao.SysOperLogMapper;
import com.blackcat.scaffolding.service.SysOperLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * <p> 操作日志记录 服务实现类
 * @author blackcat 2024-08-23
 */
@Service
public class SysOperLogServiceImpl extends ServiceImpl<SysOperLogMapper, SysOperLog> implements SysOperLogService {

}
