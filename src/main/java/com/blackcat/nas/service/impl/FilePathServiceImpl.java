package com.blackcat.nas.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blackcat.nas.common.result.AjaxResult;
import com.blackcat.nas.common.result.CustomPage;
import com.blackcat.nas.entity.FilePath;
import com.blackcat.nas.dao.FilePathMapper;
import com.blackcat.nas.entity.SysUser;
import com.blackcat.nas.service.FilePathService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.blackcat.nas.constant.Constant.VALID;


/**
 * <p> 文件路径配置 服务实现类
 * @author zhangdahui 2025-03-06
 */
@Service
public class FilePathServiceImpl extends ServiceImpl<FilePathMapper, FilePath> implements FilePathService {

    @Autowired
    private FilePathMapper filePathMapper;

    @Override
    public AjaxResult selectPage(Integer pageNow, Integer pageSize) {
        Page<FilePath> page = new Page<>(pageNow, pageSize);
        QueryWrapper<FilePath> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("path_name","file_type");
        Page<FilePath> result = filePathMapper.selectPage(page, queryWrapper);
        return AjaxResult.success(CustomPage.mybatisPage(result));
    }

    @Override
    public AjaxResult edit(FilePath obj) {
        if (obj.getId() == null) {
            filePathMapper.insert(obj);
        }else {
            filePathMapper.updateById(obj);
        }
        return AjaxResult.success();
    }
}
