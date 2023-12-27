package com.kidpix.demo.Model.DTO;

public class SceneDTO {
    private Long sceneId;
    private String scenePath;
    private String keywords;

    private  Byte pageNumber ;
    private Long categoryId;

    // Getters and Setters


    public Byte getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Byte pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Long getSceneId() {
        return sceneId;
    }

    public void setSceneId(Long sceneId) {
        this.sceneId = sceneId;
    }

    public String getScenePath() {
        return scenePath;
    }

    public void setScenePath(String scenePath) {
        this.scenePath = scenePath;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }


// Constructor, toString(), etc. (optional)
}
