package cn.sc.databasesdisplay.dto;

import lombok.Getter;

@Getter
public class Result<T>{

    private String code;
    private T data;

    private Result() {}

    private Result(String code, T data) {
        this.code = code;
        this.data = data;
    }

    public static Result<Object> ok() {
        return new Result<>("0", null);
    }

    public static <T> Result<T> ok(T data) {
        return new Result<>("0", data);
    }

    public static Result<Object> fail() {
        return new Result<>("1", null);
    }

    public static <T> Result<T> fail(T data) {
        return new Result<>("1", data);
    }
}
