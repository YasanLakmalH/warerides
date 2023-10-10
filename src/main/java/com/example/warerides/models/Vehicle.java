package com.example.warerides.models;

public class Vehicle {
    private int vehicleId;
    private String vehicleNo;
    private String vehicleType;
    private String vehicleBrand;
    private String vehicleModel;
    private String vehicleImagePath;
    private int vehicleOwnerId;
    private String branchId;
    public Vehicle(){

    }
    public Vehicle(int vehicleId,String vehicleNo, String vehicleType, String vehicleBrand,
                   String vehicleModel,
                   String vehicleImagePath, int vehicleOwnerId,String branchId){
        this.vehicleId = vehicleId;
        this.vehicleNo = vehicleNo;
        this.vehicleType = vehicleType;
        this.vehicleBrand = vehicleBrand;
        this.vehicleModel = vehicleModel;
        this.vehicleImagePath = vehicleImagePath;
        this.vehicleOwnerId = vehicleOwnerId;
        this.branchId = branchId;
    }

    public int getVehicleId() {
        return vehicleId;
    }
    public String getVehicleNo(){
        return vehicleNo;
    }
    public String getvehicleType(){
        return vehicleType;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public String getVehicleBrand() {
        return vehicleBrand;
    }


    public String getVehicleModel() {
        return vehicleModel;
    }
    public String getVehicleImagePath(){
        return vehicleImagePath;
    }
    public int getVehicleOwnerId() {
        return vehicleOwnerId;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }
    public void setVehicleNo(String vehicleNo){
        this.vehicleNo = vehicleNo;
    }
    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }
    public void setVehicleBrand(String vehicleBrand) {
        this.vehicleBrand = vehicleBrand;
    }


    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public void setVehicleImagePath(String vehicleImagePath) {
        this.vehicleImagePath = vehicleImagePath;
    }


    public void setVehicleOwnerId(int vehicleOwnerId) {
        this.vehicleOwnerId = vehicleOwnerId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }
}
