package app.entities;

import java.time.LocalDateTime;


public class Inquiry {
    private int inquiryId;
    private int customerId;
    private int carportLength;
    private int carportWidth;
    private String roofType;
    private int slopeRoof;
    private String siding;
    private int shedLength;
    private int shedWidth;
    private LocalDateTime date;

    public Inquiry(int inquiryId, int customerId, int carportLength, int carportWidth, String roofType, int slopeRoof, String siding, int shedLength, int shedWidth, LocalDateTime date) {
        this.inquiryId = inquiryId;
        this.customerId = customerId;
        this.carportLength = carportLength;
        this.carportWidth = carportWidth;
        this.roofType = roofType;
        this.slopeRoof = slopeRoof;
        this.siding = siding;
        this.shedLength = shedLength;
        this.shedWidth = shedWidth;
        this.date = date;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getInquiryId() {
        return inquiryId;
    }

    public void setInquiryId(int inquiryId) {
        this.inquiryId = inquiryId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getCarportLength() {
        return carportLength;
    }

    public void setCarportLength(int carportLength) {
        this.carportLength = carportLength;
    }

    public int getCarportWidth() {
        return carportWidth;
    }

    public void setCarportWidth(int carportWidth) {
        this.carportWidth = carportWidth;
    }

    public String getRoofType() {
        return roofType;
    }

    public void setRoofType(String roofType) {
        this.roofType = roofType;
    }

    public int getSlopeRoof() {
        return slopeRoof;
    }

    public void setSlopeRoof(int slopeRoof) {
        this.slopeRoof = slopeRoof;
    }

    public String getSiding() {
        return siding;
    }

    public void setSiding(String siding) {
        this.siding = siding;
    }

    public int getShedLength() {
        return shedLength;
    }

    public void setShedLength(int shedLength) {
        this.shedLength = shedLength;
    }

    public int getShedWidth() {
        return shedWidth;
    }

    public void setShedWidth(int shedWidth) {
        this.shedWidth = shedWidth;
    }


}
