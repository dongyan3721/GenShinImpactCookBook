package com.android.example.primordialspirit.entity;

/**
 * 评论实体类对应后端评论表
 */
public class Comments {
    protected String comId;
    protected String roleName;
    protected String content;
    protected String adder;
    protected String address;

    public Comments(String comId, String roleName, String content, String adder, String address) {
        this.comId = comId;
        this.roleName = roleName;
        this.content = content;
        this.adder = adder;
        this.address = address;
    }

    public Comments() {
    }

    public String getComId() {
        return comId;
    }

    public void setComId(String comId) {
        this.comId = comId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAdder() {
        return adder;
    }

    public void setAdder(String adder) {
        this.adder = adder;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Comments{");
        sb.append("comId='").append(comId).append('\'');
        sb.append(", roleName='").append(roleName).append('\'');
        sb.append(", content='").append(content).append('\'');
        sb.append(", adder='").append(adder).append('\'');
        sb.append(", address='").append(address).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
