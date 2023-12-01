package com.example.genshinimpact;

public class detailData {
    String characterName;
    String talentName;
    String talentDetail;
    String picUrl;
    public detailData(){}
    public detailData(String characterName, String talentName, String talentDetail, String picUrl){
        this.characterName = characterName;
        this.talentDetail = talentDetail;
        this.picUrl = picUrl;
        this.talentName = talentName;
    }
    public String getCharacterName(){
        return characterName;
    }

    public String getTalentDetail() {
        return talentDetail;
    }

    public String getTalentName() {
        return talentName;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public void setTalentDetail(String talentDetail) {
        this.talentDetail = talentDetail;
    }

    public void setTalentName(String talentName) {
        this.talentName = talentName;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
}
