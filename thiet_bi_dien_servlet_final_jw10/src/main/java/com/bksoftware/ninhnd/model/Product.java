package com.bksoftware.ninhnd.model;

import java.util.Date;

public class Product {
    private int id;

    private String name;

    private double price;

    private Date createTime;

    private boolean deleted;

    private String image;

    private String introduction;

    private String specification;

    private boolean soldOut;

    private int guarantee;

    private int categoryId; //category_id => categoryId

    private int bought;

    private int promotion;

    public Product() {
    }

    public Product(int id, String name, double price, Date createTime, boolean deleted, String image, String introduction, String specification, boolean soldOut, int guarantee, int categoryId, int bought, int promotion) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.createTime = createTime;
        this.deleted = deleted;
        this.image = image;
        this.introduction = introduction;
        this.specification = specification;
        this.soldOut = soldOut;
        this.guarantee = guarantee;
        this.categoryId = categoryId;
        this.bought = bought;
        this.promotion = promotion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public boolean isSoldOut() {
        return soldOut;
    }

    public void setSoldOut(boolean soldOut) {
        this.soldOut = soldOut;
    }

    public int getGuarantee() {
        return guarantee;
    }

    public void setGuarantee(int guarantee) {
        this.guarantee = guarantee;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getBought() {
        return bought;
    }

    public void setBought(int bought) {
        this.bought = bought;
    }

    public int getPromotion() {
        return promotion;
    }

    public void setPromotion(int promotion) {
        this.promotion = promotion;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", createTime=" + createTime +
                ", deleted=" + deleted +
                ", image='" + image + '\'' +
                ", introduction='" + introduction + '\'' +
                ", specification='" + specification + '\'' +
                ", soldOut=" + soldOut +
                ", guarantee=" + guarantee +
                ", categoryId=" + categoryId +
                ", bought=" + bought +
                ", promotion=" + promotion +
                '}';
    }
}
