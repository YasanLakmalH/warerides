package com.example.warerides.models;

public class Service {
    private int serviceId;
    private String serviceType;
    public Service(int serviceId, String serviceType) {
        this.serviceId = serviceId;
        this.serviceType = serviceType;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }
}
