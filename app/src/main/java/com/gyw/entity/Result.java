package com.gyw.entity;

/**
 * author:Created by ZhangPengFei.
 * data: 2017/12/28
 * 映射服务器返回的结果
 */
public class Result {

    private int code; // code码
    private String text; // 信息

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}