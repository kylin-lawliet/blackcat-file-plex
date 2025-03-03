package com.blackcat.scaffolding.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
* <p>
* 示例表
* </p>
*
* @author zhangdahui 2025-03-01
*/
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class Dome extends Model<Dome> {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String userName;

    private String sex;

    private String deptId;

    private String positionId;

    private Integer age;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    private Double weight;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime birthday;

    private String remark;

    @Override
    public Serializable pkVal() {
        return this.id;
    }
}