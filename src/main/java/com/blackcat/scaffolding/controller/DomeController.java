package com.blackcat.scaffolding.controller;

import com.blackcat.scaffolding.common.annotation.Log;
import com.blackcat.scaffolding.common.exception.ServiceException;
import com.blackcat.scaffolding.common.result.AjaxResult;
import com.blackcat.scaffolding.entity.Dome;
import com.blackcat.scaffolding.enums.BusinessType;
import com.blackcat.scaffolding.service.DomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 使用示例
 * @author : zhangdahui  2024/8/23 下午4:45
 */
@RestController
@RequestMapping("dome")
public class DomeController {

    @Autowired
    private DomeService domeService;

    @GetMapping("/selectDeptList")
    public AjaxResult selectDeptList() {
        return domeService.selectDeptList();
    }

    @GetMapping("/selectPositionList")
    public AjaxResult selectPositionList(String deptId) {
        return domeService.selectPositionList(deptId);
    }

    @GetMapping("/selectPage")
    public AjaxResult selectPage(Integer page, Integer size, String userName) {
        return domeService.selectPage(page,size,userName);
    }

    @Log(title = "编辑", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    public AjaxResult edit(@RequestBody Dome obj) {
        return domeService.edit(obj);
    }

    @GetMapping("/getOne")
    public AjaxResult getOne(String id) {
        return AjaxResult.success(domeService.getById(id));
    }

    @Log(title = "删除", businessType = BusinessType.DELETE)
    @DeleteMapping("/delete")
    public AjaxResult delete(String id) {
        domeService.removeById(id);
        return AjaxResult.success();
    }



    @GetMapping("/testError2")
    public AjaxResult testError2() {
        return AjaxResult.error("自定义异常信息");
    }

    // 全局异常捕获测试
    @GetMapping("/testError3")
    public AjaxResult testError3() {
        int s = 1/0;
        return AjaxResult.error();
    }

    @GetMapping("/testError4")
    public AjaxResult testError4(String param) {
        if (param.equals("1")) {
            throw  new ServiceException("自定义业务异常抛出");
        }
        return AjaxResult.success();
    }

    // pagination分页插件自带分页类
    @Log(title = "分页插件自带分页类", businessType = BusinessType.OTHER)
    @GetMapping("/getList")
    public AjaxResult getList(Integer pageNow, Integer pageSize) {
        return domeService.getList(pageNow,pageSize);
    }

}
