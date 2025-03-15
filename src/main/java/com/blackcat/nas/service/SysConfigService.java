package com.blackcat.nas.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blackcat.nas.common.result.AjaxResult;
import com.blackcat.nas.entity.SysConfig;
import java.util.List;

/**
 * <p> 配置表 服务类
 * @author zhangdahui 2025-03-06
 */
public interface SysConfigService extends IService<SysConfig> {

    /**
     * 描述 :   获取配置
     * @author : zhangdahui 2025/3/6 下午5:23
     * @param key  配置键值
    */
    String getConfigValue(String key);
}
