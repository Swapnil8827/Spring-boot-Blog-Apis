package com.blog.webapp.payloads;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ApiResponse {

    private String message;
    private boolean success;
    private String time;

    public ApiResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
        this.time = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
