package com.reserve.restaurantservice.dto;


public class RatingCountDto {

    private Integer zeroCount;
    private Integer oneCount;
    private Integer twoCount;
    private Integer threeCount;
    private Integer fourCount;
    private Integer fiveCount;

    private Integer reviewCount;

    public RatingCountDto() {
    }

    public Integer getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(Integer reviewCount) {
        this.reviewCount = reviewCount;
    }

    public Integer getZeroCount() {
        return zeroCount;
    }

    public void setZeroCount(Integer zeroCount) {
        this.zeroCount = zeroCount;
    }

    public Integer getOneCount() {
        return oneCount;
    }

    public void setOneCount(Integer oneCount) {
        this.oneCount = oneCount;
    }

    public Integer getTwoCount() {
        return twoCount;
    }

    public void setTwoCount(Integer twoCount) {
        this.twoCount = twoCount;
    }

    public Integer getThreeCount() {
        return threeCount;
    }

    public void setThreeCount(Integer threeCount) {
        this.threeCount = threeCount;
    }

    public Integer getFourCount() {
        return fourCount;
    }

    public void setFourCount(Integer fourCount) {
        this.fourCount = fourCount;
    }

    public Integer getFiveCount() {
        return fiveCount;
    }

    public void setFiveCount(Integer fiveCount) {
        this.fiveCount = fiveCount;
    }
}
