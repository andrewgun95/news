package com.example.news.web.rest.response;

import lombok.Data;

@Data
public class BaseResponse {

    public static BaseResponse of(Object data, Integer status) {
        BaseResponse response = new BaseResponse();
        response.setResponse(data);
        response.setStatus(status);
        return response;
    }

    public static BaseResponse of(Object data) {
        return of(data, 200);
    }

    private Object response;

    private Integer status;

}
