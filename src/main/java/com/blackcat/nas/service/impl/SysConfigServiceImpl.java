package com.blackcat.nas.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blackcat.nas.common.result.AjaxResult;
import com.blackcat.nas.common.result.CustomPage;
import com.blackcat.nas.entity.FilePath;
import com.blackcat.nas.entity.SysConfig;
import com.blackcat.nas.dao.SysConfigMapper;
import com.blackcat.nas.service.SysConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * <p> 配置表 服务实现类
 * @author zhangdahui 2025-03-06
 */
@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements SysConfigService {

    @Autowired
    private SysConfigMapper sysConfigMapper;

    @Override
    public String getConfigValue(String key) {
        QueryWrapper<SysConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("config_key", key);
        List<SysConfig> sysConfigList = sysConfigMapper.selectList(queryWrapper);
        if (CollectionUtils.isNotEmpty(sysConfigList)) {
            return sysConfigList.get(0).getConfigValue();
        }
        return "";
    }
}
