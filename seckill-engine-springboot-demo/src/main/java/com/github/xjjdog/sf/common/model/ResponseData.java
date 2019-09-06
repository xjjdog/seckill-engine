package com.github.xjjdog.sf.common.model;

import lombok.Data;

@Data
public class ResponseData<T> {

    private Integer code;

    private String message;

    private T data;

    private Long ts;


    public ResponseData() {
    }


    public ResponseData(Integer code, String message, Long ts) {
        this.code = code;
        this.message = message;
        this.ts = ts;
    }


    public ResponseData(Integer code, String message, T data, Long ts) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.ts = ts;
    }

    public boolean success() {
        return RepStatus.SUCCESS.equals(this.code);
    }



}
