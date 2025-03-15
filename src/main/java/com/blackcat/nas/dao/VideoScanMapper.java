package com.blackcat.nas.dao;

import com.blackcat.nas.entity.VideoScan;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * <p> 视频数据 Mapper 接口
 * @author zhangdahui 2025-03-06
 */
public interface VideoScanMapper extends BaseMapper<VideoScan> {

    /**
     * 描述 :   分页-系列合并查询
     * @author : zhangdahui 2025/3/7 上午9:37
     * @param start 起始条数
     * @param end 结束条数
     * @param name  影视名称
    */
    List<VideoScan> selectPageBySeriesName(@Param("start") Integer start, @Param("end") Integer end, @Param("name") String name, @Param("category") String category);

    /**
     * 描述 :  查询总是
     * @author : zhangdahui 2025/3/7 上午9:37
     * @param name  影视名称
    */
    int selectPageBySeriesNameCount(@Param("name") String name,@Param("category") String category);
}
