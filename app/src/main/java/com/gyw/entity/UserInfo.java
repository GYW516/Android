package com.gyw.entity;

public class UserInfo {
    private int user_id;
    private String account;
    private String password;
    private String nickname;

    public UserInfo() {
    }

    public UserInfo(int user_id, String account, String password, String nickname) {
        this.user_id = user_id;
        this.account = account;
        this.password = password;
        this.nickname = nickname;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
