package com.example.warerides.controllers;

import com.example.warerides.DBUtils;
import com.example.warerides.models.Vehicle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class NewInquiryController implements Initializable {
    @FXML
    private ChoiceBox<String> vehicleTypeChoiceBox;
    @FXML
    private HBox vehicleContainer;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Vehicle> vehicleList = new ArrayList<>();

        vehicleTypeChoiceBox.getItems().addAll("CAR","SUV","CROSSOVER","VAN","MINIVAN");
        vehicleTypeChoiceBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                DBUtils.getNoneServingVehicles(newValue).forEach(vehicle -> {
                    System.out.println(vehicle.getVehicleId());
                    vehicleList.add(vehicle);
                    addVehicles(vehicleList);
                });
            }
        });
    }
    public void addVehicles(List<Vehicle> vehicleList){
        if(!vehicleList.isEmpty()){
            vehicleList.forEach(vehicle -> {
                try {
                    System.out.println(vehicle.getVehicleModel());
                    Node node = setVehicleData(vehicle.getVehicleImagePath(),vehicle.getVehicleModel());
                    vehicleContainer.getChildren().clear();
                    vehicleContainer.getChildren().add(node);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

        }
    }
    public Node setVehicleData(String imagePath, String modelName) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/warerides/VehicleItem.fxml"));
        Node node = loader.load();
        VehicleItemController vehicleItemController = loader.getController();
        vehicleItemController.setData(imagePath, modelName);
        return node;
    }
}
