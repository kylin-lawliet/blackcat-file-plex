package com.blackcat.scaffolding.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blackcat.scaffolding.common.result.AjaxResult;
import com.blackcat.scaffolding.entity.Dome;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p> 示例表 Mapper 接口
 * @author zhangdahui 2025-03-01
 */
public interface DomeMapper extends BaseMapper<Dome> {

    @Select("select * from dome_dept")
    List<Map<String,String>> selectDeptList();

    @Select("select * from dome_position")
    List<Map<String,String>> selectPositionAllList();

    @Select("select * from dome_position where dept_id=#{deptId}")
    List<Map<String,String>> selectPositionListByDeptId(String deptId);
}
