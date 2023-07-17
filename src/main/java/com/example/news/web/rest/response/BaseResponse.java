package com.example.news.web.rest.response;

import lombok.Data;

@Data
public class BaseResponse {

    public static BaseResponse of(Object data, Integer status) {
        BaseResponse response = new BaseResponse();
        response.setData(data);
        response.setStatus(status);
        return response;
    }

    public static BaseResponse of(Object data) {
        return of(data, 200);
    }

    private Object data;

    private Integer status;

}
