package com.android.example.primordialspirit.entity;

import java.util.Date;

/**
 * 后端用户表实体类
 */
public class User {
    private String regId;
    private String nickName;
    private String registerPhone;
    private String registerEmail;
    private String encryptedPassword;
    private Integer account;
    private Date createTime;

    public User(String regId, String nickName, String registerPhone, String registerEmail, String encryptedPassword, Integer account, Date createTime) {
        this.regId = regId;
        this.nickName = nickName;
        this.registerPhone = registerPhone;
        this.registerEmail = registerEmail;
        this.encryptedPassword = encryptedPassword;
        this.account = account;
        this.createTime = createTime;
    }

    public User() {
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getRegisterPhone() {
        return registerPhone;
    }

    public void setRegisterPhone(String registerPhone) {
        this.registerPhone = registerPhone;
    }

    public String getRegisterEmail() {
        return registerEmail;
    }

    public void setRegisterEmail(String registerEmail) {
        this.registerEmail = registerEmail;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public Integer getAccount() {
        return account;
    }

    public void setAccount(Integer account) {
        this.account = account;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("regId='").append(regId).append('\'');
        sb.append(", nickName='").append(nickName).append('\'');
        sb.append(", registerPhone='").append(registerPhone).append('\'');
        sb.append(", registerEmail='").append(registerEmail).append('\'');
        sb.append(", encryptedPassword='").append(encryptedPassword).append('\'');
        sb.append(", account=").append(account);
        sb.append(", createTime=").append(createTime);
        sb.append('}');
        return sb.toString();
    }
}
