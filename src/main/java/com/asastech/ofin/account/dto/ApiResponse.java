package com.asastech.ofin.account.dto;

import lombok.*;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ApiResponse<T> implements Serializable {

    private boolean success;
    private String message;
    private Date date = new Date();
    private T data;

    public ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        formatter.format(date);
        this.data = data;
    }

}

