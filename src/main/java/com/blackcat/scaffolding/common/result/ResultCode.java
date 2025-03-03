package com.blackcat.scaffolding.common.result;

/**
 * 返回信息枚举
 * @author : zhangdahui  2024/8/23 上午9:34
 */
public enum ResultCode {

    FORBIDDEN(HttpStatus.FORBIDDEN, "访问受限，授权过期"),

    SUCCESS(HttpStatus.SUCCESS, "操作成功"),
    NO_CONTENT(HttpStatus.NO_CONTENT, "查询无结果"),
    ERROR(HttpStatus.ERROR, "系统异常");

    private final Integer code;
    private final String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }
}
