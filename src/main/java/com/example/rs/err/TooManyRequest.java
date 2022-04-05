package com.example.rs.err;

public class TooManyRequest extends Exception{
    public TooManyRequest(String msg, Integer code) {
        super(msg);
        this.code = code;
    }
    private Integer code;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
