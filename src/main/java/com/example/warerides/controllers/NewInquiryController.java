package com.example.warerides.controllers;

import com.example.warerides.DBUtils;
import com.example.warerides.models.Service;
import com.example.warerides.models.Vehicle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class NewInquiryController implements Initializable {
    List<Vehicle> vehicleList = new ArrayList<>();

    @FXML
    private ChoiceBox<String> vehicleTypeChoiceBox;
    @FXML
    private DatePicker pickupDate;
    @FXML
    private DatePicker returnDate;
    @FXML
    private Button searchButton,submitButton;
    @FXML
    private HBox vehicleContainer;
    @FXML
    private ChoiceBox<String> vehicleBrandChoiceBox;
    @FXML
    private ChoiceBox<String> vehicleModelChoiceBox;
    @FXML
    private ChoiceBox<String> serviceTypesChoiceBox;
    @FXML
    private ChoiceBox<String> pickupLocationChoiceBox;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        defaultChoices(vehicleTypeChoiceBox,serviceTypesChoiceBox,pickupLocationChoiceBox);
        vehicleTypeChoiceBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                vehicleList.clear();
                DBUtils.getNoneServingVehicles(newValue).forEach(vehicle -> {
                    vehicleList.add(vehicle);
                });
                addVehicleNodes(vehicleList);
            }
        });
        vehicleBrandChoiceBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                vehiclesFilterByBrand(vehicleList,newValue);
                getModelChoices(vehicleModelChoiceBox,newValue);
            }
        });

        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DBUtils.getAvailableServingVehicles(
                        pickupDate.getValue().toString(),
                        returnDate.getValue().toString(),
                        vehicleTypeChoiceBox.getValue()
                ).forEach(
                        vehicle->{
                            vehicleList.add(vehicle);
                        }
                );
                addVehicleNodes(vehicleList);
                getBrandChoices(vehicleBrandChoiceBox,vehicleTypeChoiceBox.getValue());

            }
        });
        s
    }
    public void getBrandChoices(ChoiceBox<String> choiceBox,String type){
        Set<String> uniqueChoices = new HashSet<>();
        vehicleList.forEach(vehicle -> {
            if(type.equals(vehicle.getvehicleType())){
                uniqueChoices.add(vehicle.getVehicleBrand());
            }
        });
        ObservableList<String> uniqueChoicesList = FXCollections.observableArrayList(uniqueChoices);
        choiceBox.setItems(uniqueChoicesList);
    }
    public void getModelChoices(ChoiceBox<String> choiceBox,String brand){
        Set<String> uniqueChoices = new HashSet<>();
        vehicleList.forEach(vehicle -> {
            if(brand.equals(vehicle.getVehicleBrand())){
                uniqueChoices.add(vehicle.getVehicleModel());
            }
        });
        ObservableList<String> uniqueChoicesList = FXCollections.observableArrayList(uniqueChoices);
        choiceBox.setItems(uniqueChoicesList);
    }
    public void vehiclesFilterByBrand(List<Vehicle> vehicleList, String brandName){
//  reset vehicle list.
        List<Vehicle> filteredVehicles = new ArrayList<>();
        vehicleList.forEach(
                vehicle -> {
                    if(vehicle.getVehicleBrand().equals(brandName)){
                        filteredVehicles.add(vehicle);
                    }
                }
        );
        vehicleList.addAll(filteredVehicles);

//  add vehicle nodes.
        addVehicleNodes(filteredVehicles);
    }
    public void defaultChoices(ChoiceBox<String> vehicleTypes,
                           ChoiceBox<String> serviceTypes,
                           ChoiceBox<String> pickupLocaions){
        try {
            Set<String> uniqueTypes = new HashSet<>();
            DBUtils.getVehicleData().forEach(
                    vehicle -> {
                        uniqueTypes.add(vehicle.getvehicleType());
                    }
            );
            DBUtils.getServiceTypes().forEach(
                    service -> {
                        serviceTypes.getItems().add(service.getServiceType());
                    }
            );
            DBUtils.getPickupLocations().forEach(
                    location ->{
                        pickupLocaions.getItems().add(location);
                    }
            );
            ObservableList<String> uniqueTypesList = FXCollections.observableArrayList(uniqueTypes);
            vehicleTypes.setItems(uniqueTypesList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void addVehicleNodes(List<Vehicle> vehicleList){

        if(!vehicleList.isEmpty()){
            vehicleContainer.getChildren().clear();
        }
        vehicleList.forEach(vehicle -> {
            try {
                Node node = setVehicleData(vehicle.getVehicleImagePath(),vehicle.getVehicleModel());
                vehicleContainer.getChildren().add(node);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });


    }
    public Node setVehicleData(String imagePath, String modelName) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/warerides/VehicleItem.fxml"));
        Node node = loader.load();
        VehicleItemController vehicleItemController = loader.getController();
        vehicleItemController.setData(imagePath, modelName);
        return node;
    }
    public void submit(String serviceType, String pickupLocation, String pickupDate, String returnDate, String vehicleType, String vehicleNo){
        DBUtils.submitQuery(getServiceType(serviceType),pickupLocation,pickupDate,returnDate,vehicleType,vehicleNo);
    }
    public int getServiceType(String serviceType){
        List<Service> services = DBUtils.getServiceTypes();
        for(Service service:services){
            if(service.getServiceType().equals(serviceType)){
                return service.getServiceId();
            }
        }
        return 0;
    }
}
