package com.farmhulian.beans;

/**
 * 分类右边GridView 一个图片一个文字,封装成一个对象来写
 *  @author 谭杰栖
 */
public class KindRightBean {
    private int imgId;
    private String imgName;

    public KindRightBean(String imgName, int imgId) {
        this.imgName = imgName;
        this.imgId = imgId;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    @Override
    public String toString() {
        return "KindRightBean{" +
                "imgId=" + imgId +
                ", imgName='" + imgName + '\'' +
                '}';
    }
}
