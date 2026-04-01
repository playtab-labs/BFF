package com.playtab.bff.content.dto.output;

import java.util.List;

public class MdItemDetailDto {

    private Long id;
    private String name;
    private String thumbnailImageUrl;
    private String detailImageUrl;
    private int price;
    private boolean soldOut;
    private String productDescription;
    private String detailDescription;
    private List<MdOptionGroupDto> optionGroups;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbnailImageUrl() {
        return thumbnailImageUrl;
    }

    public void setThumbnailImageUrl(String thumbnailImageUrl) {
        this.thumbnailImageUrl = thumbnailImageUrl;
    }

    public String getDetailImageUrl() {
        return detailImageUrl;
    }

    public void setDetailImageUrl(String detailImageUrl) {
        this.detailImageUrl = detailImageUrl;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isSoldOut() {
        return soldOut;
    }

    public void setSoldOut(boolean soldOut) {
        this.soldOut = soldOut;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getDetailDescription() {
        return detailDescription;
    }

    public void setDetailDescription(String detailDescription) {
        this.detailDescription = detailDescription;
    }

    public List<MdOptionGroupDto> getOptionGroups() {
        return optionGroups;
    }

    public void setOptionGroups(List<MdOptionGroupDto> optionGroups) {
        this.optionGroups = optionGroups;
    }
}
