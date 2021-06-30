package com.my.common.instrument.http;

import lombok.Data;

/**
 * @author liu peng bo
 * date: 2020/3/16 17:44
 */
@Data
public class HttpRespInfo {
    /**
     * 响应状态码
     */
    private int httpStatus;

    /**
     * 响应数据
     */
    private String content;

    public HttpRespInfo() {
    }

    public HttpRespInfo(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    public HttpRespInfo(int httpStatus, String content) {
        this.httpStatus = httpStatus;
        this.content = content;
    }

    public boolean isSuccess(){
        return httpStatus==200;
    }
}
