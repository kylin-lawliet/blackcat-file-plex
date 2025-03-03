package com.blackcat.scaffolding.common.result;

import com.blackcat.scaffolding.utils.StringUtils;
import lombok.NoArgsConstructor;

import java.util.HashMap;

/**
 * 操作消息提醒
 * @author : zhangdahui  2024/8/23 上午9:20
 */
@NoArgsConstructor
public class AjaxResult extends HashMap<String, Object> {

    private static final long serialVersionUID = 1L;

    /** 状态码 */
    public static final String CODE_TAG = "code";

    /** 返回内容 */
    public static final String MSG_TAG = "msg";

    /** 数据对象 */
    public static final String DATA_TAG = "data";

    /**
     * 初始化一个新创建的 AjaxResult 对象
     *
     * @param code 状态码
     * @param msg 返回内容
     */
    public AjaxResult(int code, String msg)
    {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
    }

    /**
     * 初始化一个新创建的 AjaxResult 对象
     * @param code 状态枚举
     */
    public AjaxResult(ResultCode code)
    {
        super.put(CODE_TAG, code.code());
        super.put(MSG_TAG, code.message());
    }

    /**
     * 初始化一个新创建的 AjaxResult 对象
     * @param code 状态枚举
     * @param data 数据对象
     */
    public AjaxResult(ResultCode code, Object data)
    {
        super.put(CODE_TAG, code.code());
        super.put(MSG_TAG, code.message());
        if (StringUtils.isNotNull(data))
        {
            super.put(DATA_TAG, data);
        }
    }

    /**
     * 返回成功消息
     * @return 成功消息
     */
    public static AjaxResult success()
    {
        return new AjaxResult(ResultCode.SUCCESS);
    }

    /**
     * 返回成功消息
     * @return 成功消息
     */
    public static AjaxResult success(String msg)
    {
        return new AjaxResult(HttpStatus.SUCCESS,msg);
    }

    /**
     * 返回成功消息，指定状态码
     * @return 成功消息
     */
    public static AjaxResult success(ResultCode code)
    {
        return new AjaxResult(code);
    }

    /**
     * 返回成功数据
     * @return 成功消息
     */
    public static AjaxResult success(Object data)
    {
        return new AjaxResult(ResultCode.SUCCESS,data);
    }

    /**
     * 返回成功数据
     * @return 成功消息
     */
    public static AjaxResult success(ResultCode code,Object data){
        return new AjaxResult(code,data);
    }

    /**
     * 返回错误消息
     * @return 错误消息
     */
    public static AjaxResult error()
    {
        return new AjaxResult(ResultCode.ERROR);
    }

    /**
     * 返回错误消息
     * @return 错误消息
     */
    public static AjaxResult error(String msg)
    {
        return new AjaxResult(HttpStatus.ERROR,msg);
    }

    /**
     * 返回错误消息，指定状态码
     * @return 错误消息
     */
    public static AjaxResult error(ResultCode code)
    {
        return new AjaxResult(code);
    }

    /**
     * 返回错误消息，数据
     * @return 错误消息
     */
    public static AjaxResult error(Object data)
    {
        return new AjaxResult(ResultCode.ERROR,data);
    }

    /**
     * 方便链式调用
     * @param key 键
     * @param value 值
     * @return 数据对象
     */
    @Override
    public AjaxResult put(String key, Object value)
    {
        super.put(key, value);
        return this;
    }
}
