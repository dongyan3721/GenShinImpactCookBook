package com.example.myapplication.entity.extend_for_listview;

import com.example.myapplication.entity.Comments;

public class CommentsListViewItem extends Comments {
    private int imgId;

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CommentsListViewItem{");
        sb.append("imgId=").append(imgId);
        sb.append(", comId='").append(comId).append('\'');
        sb.append(", roleName='").append(roleName).append('\'');
        sb.append(", content='").append(content).append('\'');
        sb.append(", adder='").append(adder).append('\'');
        sb.append(", address='").append(address).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
