package com.lkz.blogs01.vo;

public class BlogQuerry {
    private String title;
    private Long typeId;
    private boolean recommend;

    public BlogQuerry() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getTypeId() {
        return typeId;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }


}
