package com.ishuyin.gecollsn.base;

/**
 * Created by Gecollsn on 2017/2/3.
 */

public class UserInfo {
    private static UserInfo instance;
    private boolean isLogin;
    private String userName;
    private String gender;
    private String userId;
    private String location;
    private String logo;

    private UserInfo() {
        resetUserInfo();
    }

    public static UserInfo getUser() {
        if (instance == null) {
            synchronized (UserInfo.class) {
                instance = instance == null ? new UserInfo() : instance;
            }
        }
        return instance;
    }

    public void resetUserInfo() {
        isLogin = false;
        userName = "";
        gender = "";
        userId = "";
        location = "";
    }


    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
