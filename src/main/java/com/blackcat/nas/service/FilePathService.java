package com.blackcat.nas.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blackcat.nas.common.result.AjaxResult;
import com.blackcat.nas.entity.FilePath;
import com.blackcat.nas.entity.SysUser;

import java.util.List;

/**
 * <p> 文件路径配置 服务类
 * @author zhangdahui 2025-03-06
 */
public interface FilePathService extends IService<FilePath> {

    /**
     * 描述 :  分页查询
     * @author : zhangdahui 2025/2/24 上午10:28
     * @param pageNow 当前页
     * @param pageSize 每页数量
     */
    AjaxResult selectPage(Integer pageNow, Integer pageSize);

    /**
     * 描述 :   新增或编辑
     * @author : zhangdahui 2025/2/24 上午10:27
     * @param obj  数据对象
     */
    AjaxResult edit(FilePath obj);

}
