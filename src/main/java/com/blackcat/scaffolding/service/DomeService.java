package com.blackcat.scaffolding.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blackcat.scaffolding.common.result.AjaxResult;
import com.blackcat.scaffolding.entity.Dome;
import com.blackcat.scaffolding.entity.SysUser;
import com.github.pagehelper.PageInfo;
import java.util.List;

/**
 * <p> 示例表 服务类
 * @author zhangdahui 2025-03-01
 */
public interface DomeService extends IService<Dome> {

    AjaxResult selectPositionList(String deptId);
    AjaxResult selectDeptList();

    /**
     * 描述 :  分页查询
     * @author : zhangdahui 2025/2/24 上午10:28
     * @param pageNow 当前页
     * @param pageSize 每页数量
     * @param userName  用户名称
     */
    AjaxResult selectPage(Integer pageNow, Integer pageSize, String userName);

    /**
     * 描述 :   新增或编辑
     * @author : zhangdahui 2025/2/24 上午10:27
     * @param obj  数据对象
     */
    AjaxResult edit(Dome obj);

    AjaxResult getList(Integer pageNow, Integer pageSize);
}
