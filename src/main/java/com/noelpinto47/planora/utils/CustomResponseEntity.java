package com.noelpinto47.planora.utils;

import lombok.Data;

@Data
public class CustomResponseEntity {
    private boolean status;
    private String message;
    private Object data;

    public CustomResponseEntity(boolean status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
