package com.android.example.primordialspirit.entity;

/**
 * 评论查询实体类
 */
public class CommentsQueryEntity {
    private String roleName;
    private int pageNum;
    private int startNum;
    private int endNum;

    public CommentsQueryEntity(String roleName, int pageNum, int startNum, int endNum) {
        this.roleName = roleName;
        this.pageNum = pageNum;
        this.startNum = startNum;
        this.endNum = endNum;
    }

    public CommentsQueryEntity() {
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getStartNum() {
        return startNum;
    }

    public void setStartNum(int startNum) {
        this.startNum = startNum;
    }

    public int getEndNum() {
        return endNum;
    }

    public void setEndNum(int endNum) {
        this.endNum = endNum;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CommentsQueryEntity{");
        sb.append("roleName='").append(roleName).append('\'');
        sb.append(", pageNum=").append(pageNum);
        sb.append(", startNum=").append(startNum);
        sb.append(", endNum=").append(endNum);
        sb.append('}');
        return sb.toString();
    }
}
