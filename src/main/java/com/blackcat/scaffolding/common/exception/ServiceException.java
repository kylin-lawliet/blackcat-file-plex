package com.blackcat.scaffolding.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 业务异常
 * @author : zhangdahui  2024/8/23 上午10:43
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
public class ServiceException  extends RuntimeException{

    private static final long serialVersionUID = 1L;

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误提示
     */
    private String message;

    /**
     * 错误明细，内部调试错误
     */
    private String detailMessage;

    public ServiceException(String message)
    {
        this.message = message;
    }

    public ServiceException(String message, Integer code)
    {
        this.message = message;
        this.code = code;
    }
}
