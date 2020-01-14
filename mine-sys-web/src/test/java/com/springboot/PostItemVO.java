package com.springboot;

/**
 * @author : liuk
 * @date : 2016/10/17
 */
public class PostItemVO {

    private String infoTitle;
    private String infoDate;
    private String infoUrl;
    private String imageLink;
    private String infoUnit;
    private String readFile;

    public String getInfoTitle() {
        return infoTitle;
    }

    public void setInfoTitle(String infoTitle) {
        this.infoTitle = infoTitle;
    }

    public String getInfoDate() {
        return infoDate;
    }

    public void setInfoDate(String infoDate) {
        this.infoDate = infoDate;
    }

    public String getInfoUrl() {
        return infoUrl;
    }

    public void setInfoUrl(String infoUrl) {
        this.infoUrl = infoUrl;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getInfoUnit() {
        return infoUnit;
    }

    public void setInfoUnit(String infoUnit) {
        this.infoUnit = infoUnit;
    }

    public void setReadFile(String readFile) {
        this.readFile = readFile;
    }

    public String getReadFile() {
        return readFile;
    }
}
