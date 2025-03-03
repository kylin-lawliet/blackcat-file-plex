package com.blackcat.scaffolding.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blackcat.scaffolding.common.result.AjaxResult;
import com.blackcat.scaffolding.common.result.CustomPage;
import com.blackcat.scaffolding.dao.DomeMapper;
import com.blackcat.scaffolding.entity.Dome;
import com.blackcat.scaffolding.entity.SysOperLog;
import com.blackcat.scaffolding.entity.SysUser;
import com.blackcat.scaffolding.service.DomeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;


/**
 * <p> 示例表 服务实现类
 * @author zhangdahui 2025-03-01
 */
@Service
public class DomeServiceImpl extends ServiceImpl<DomeMapper, Dome> implements DomeService {

    @Autowired
    private DomeMapper domeMapper;

    @Override
    public AjaxResult selectPositionList(String deptId) {
        if (StringUtils.isNotBlank(deptId)) {
            return AjaxResult.success(domeMapper.selectPositionListByDeptId(deptId));
        }
        return AjaxResult.success(domeMapper.selectPositionAllList());
    }

    @Override
    public AjaxResult selectDeptList() {
        return AjaxResult.success(domeMapper.selectDeptList());
    }

    @Override
    public AjaxResult selectPage(Integer pageNow, Integer pageSize, String userName) {
        Page<Dome> page = new Page<>(pageNow, pageSize);
        QueryWrapper<Dome> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(userName)) {
            queryWrapper.like("user_name", userName);
        }
        Page<Dome> result = domeMapper.selectPage(page, queryWrapper);
        return AjaxResult.success(CustomPage.mybatisPage(result));
    }

    @Override
    public AjaxResult edit(Dome obj) {
        // TODO 未做本地用户信息存储
        obj.setCreateTime(LocalDateTime.now());
        if (obj.getId() == null) {
            domeMapper.insert(obj);
        }else {
            domeMapper.updateById(obj);
        }
        return AjaxResult.success();
    }

    @Override
    public AjaxResult getList(Integer pageNow, Integer pageSize) {
        Page<Dome> page = new Page<>(pageNow, pageSize);
        Page<Dome> result = domeMapper.selectPage(page, null);
        System.out.println(result);
        return AjaxResult.success(result);
        // 使用PageHelper的startPage方法开始分页
//        PageHelper.startPage(pageNow, pageSize);
//        List<Dome> sysOperLogList = sysOperLogMapper.selectList(null);
//        // 使用PageInfo包装查询结果，PageInfo中包含了分页信息，如总记录数
//        PageInfo<Dome> pageInfo = new PageInfo<>(sysOperLogList);
//        return AjaxResult.success(pageInfo);
    }

}
