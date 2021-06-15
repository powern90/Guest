package kw.kimkihong.assign3.vo;

import android.util.Log;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReservationVO {
    //declare variable
    private int count;
    private LocalDate startDate;
    private LocalDate endDate;
    private String payment;
    private int reservationId;
    private String userId;
    private int postId;
    private LocalDateTime createdAt;
    private String userName;
    private String businessName;

    //declare getter and setter
    public void setStartDate(String startDate) {
        this.startDate = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public void setEndDate(String endDate) {
        this.endDate = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = LocalDateTime.parse(createdAt.substring(0,createdAt.lastIndexOf(".")));
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String[] toStringArray() {
        String[] ret = new String[10];
        ret[0] = String.valueOf(this.count);
        ret[1] = this.startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        ret[2] = this.endDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        ret[3] = this.payment;
        ret[4] = String.valueOf(this.reservationId);
        ret[5] = this.userId;
        ret[6] = String.valueOf(this.postId);
        ret[7] = this.createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        ret[8] = this.userName;
        ret[9] = this.businessName;
        return ret;
    }
}

