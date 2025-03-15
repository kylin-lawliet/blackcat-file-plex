package com.blackcat.nas.dao;

import com.blackcat.nas.common.result.AjaxResult;
import com.blackcat.nas.entity.VideoSeasons;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * <p> 剧集详细信息 Mapper 接口
 * @author zhangdahui 2025-03-13
 */
public interface VideoSeasonsMapper extends BaseMapper<VideoSeasons> {

   /**
    * 描述 :   获取详情
    * @author : zhangdahui 2025/3/14 下午4:38
    * @param id  主键
    */
   VideoSeasons getOne(String id);

   /**
    * 描述 :   分页查询数量
    * @author : zhangdahui 2025/3/14 下午4:36
    * @param videoName 视频名称
    * @param seasonsNumber 季数
   */
   int selectCount(@Param("videoName") String videoName,@Param("seasonsNumber") Integer seasonsNumber);

   /**
    * 描述 :   分页查询
    * @author : zhangdahui 2025/3/14 下午4:37
    * @param start 开始条数
    * @param end 每页条数
    * @param videoName 视频名称
    * @param seasonsNumber 季数
   */
   List<VideoSeasons> selectPage(@Param("start") Integer start, @Param("end") Integer end,@Param("videoName") String videoName,@Param("seasonsNumber") Integer seasonsNumber);
}
