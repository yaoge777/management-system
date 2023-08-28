package com.example.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
public class Result<T> {
    private int code;
    private String message;
    private T data;

    public static<T> Result<T> success(T data){
        return new Result(20000, "success", data);
    }
    public static Result<Object> success(){
        return new Result(20000, "success", null);
    }

    public static Result<Object> fail(){
        return new Result(20001, "failed", null);
    }
}
