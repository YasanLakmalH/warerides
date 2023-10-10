package com.example.warerides.models;

public class Inquiry {
    private int inquiryId;
    private int serviceId;
    private String pickupLocation;
    private String pickupDate;
    private String pickupTime;
    private String returnDate;
    private String vehicleType;

    public Inquiry(int inquiryId, int serviceId, String pickupLocation,
                   String pickupDate, String pickupTime, String returnDate, String vehicleType) {
        this.inquiryId = inquiryId;
        this.serviceId = serviceId;
        this.pickupLocation = pickupLocation;
        this.pickupDate = pickupDate;
        this.pickupTime = pickupTime;
        this.returnDate = returnDate;
        this.vehicleType = vehicleType;
    }

    public int getInquiryId() {
        return inquiryId;
    }

    public void setInquiryId(int inquiryId) {
        this.inquiryId = inquiryId;
    }

    public int getServiceType() {
        return serviceId;
    }

    public void setServiceType(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public String getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(String pickupDate) {
        this.pickupDate = pickupDate;
    }

    public String getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(String pickupTime) {
        this.pickupTime = pickupTime;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }
}
