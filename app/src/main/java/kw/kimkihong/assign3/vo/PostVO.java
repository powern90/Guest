package kw.kimkihong.assign3.vo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class PostVO {
    //declare variable
    private Integer id;
    private String writer;
    private String name;
    private String price;
    private String content;
    private Integer count;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime enterTime;
    private LocalTime exitTime;
    private String address;
    private String altitude;
    private String latitude;
    private String img;

    //declare getter and setter
    public LocalTime getEnterTime() {
        return enterTime;
    }

    public void setEnterTime(LocalTime enterTime) {
        this.enterTime = enterTime;
    }

    public void setEnterTime(String enterTime) {
        this.enterTime = LocalTime.parse(enterTime, DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    public LocalTime getExitTime() {
        return exitTime;
    }

    public void setExitTime(LocalTime exitTime) {
        this.exitTime = exitTime;
    }

    public void setExitTime(String exitTime) {
        this.exitTime = LocalTime.parse(exitTime, DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = LocalDate.parse(startDate, DateTimeFormatter.ISO_DATE);;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = LocalDate.parse(endDate, DateTimeFormatter.ISO_DATE);;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAltitude() {
        return altitude;
    }

    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String longitude) {
        this.latitude = longitude;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
