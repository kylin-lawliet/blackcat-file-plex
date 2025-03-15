package com.blackcat.nas.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

/**
* <p>
* 配置表
* </p>
*
* @author zhangdahui 2025-03-06
*/
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SysConfig extends Model<SysConfig> {

    /**
    * 主键
    */
            @TableId(value = "config_id", type = IdType.AUTO)
    private Integer configId;

    /**
    * 配置名称
    */
    private String configName;

    /**
    * 配置键值
    */
    private String configKey;

    /**
    * 配置值
    */
    private String configValue;

    private String createBy;

    private LocalDateTime createTime;

    private String updateBy;

    private LocalDateTime updateTime;

    private String remark;

    /**
    * 配置类型
    */
    private String configType;

    @Override
    public Serializable pkVal() {
        return this.configId;
    }
}