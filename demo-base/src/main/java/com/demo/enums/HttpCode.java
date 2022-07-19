package com.demo.enums;

import lombok.Data;

/**
 * 自定义http状态码
 */
public enum HttpCode {
    SUCCESS("success", 200), FAIL("fail", 500);

    private String value;
    private Integer code;

    HttpCode(String value, int code) {
        this.code = code;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
