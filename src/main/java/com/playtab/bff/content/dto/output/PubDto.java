package com.playtab.bff.content.dto.output;

public class PubDto {

    private Long id;
    private String collegeName;
    private String thumbnailImageUrl;
    private boolean nameConfirmed;
    private int displayOrder;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public String getThumbnailImageUrl() {
        return thumbnailImageUrl;
    }

    public void setThumbnailImageUrl(String thumbnailImageUrl) {
        this.thumbnailImageUrl = thumbnailImageUrl;
    }

    public boolean isNameConfirmed() {
        return nameConfirmed;
    }

    public void setNameConfirmed(boolean nameConfirmed) {
        this.nameConfirmed = nameConfirmed;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }
}
