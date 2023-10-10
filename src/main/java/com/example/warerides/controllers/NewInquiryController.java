package com.example.warerides.controllers;

import com.example.warerides.DBUtils;
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

public class NewInquiryController implements Initializable {
    @FXML
    private ChoiceBox<String> vehicleTypeChoiceBox;
    @FXML
    private DatePicker pickupDate;
    @FXML
    private DatePicker returnDate;
    @FXML
    private Button searchButton;
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
        addChoices(vehicleTypeChoiceBox,vehicleBrandChoiceBox, vehicleModelChoiceBox,serviceTypesChoiceBox,pickupLocationChoiceBox);
        List<Vehicle> vehicleList = new ArrayList<>();

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
                System.out.println(vehicleList.size());
                addVehicleNodes(vehicleList);

            }
        });
    }
    public void addChoices(ChoiceBox vehicleTypes,
                           ChoiceBox vehicleBrands,
                           ChoiceBox vehicleModels,
                           ChoiceBox serviceTypes,
                           ChoiceBox pickupLocaions
    ){
        try {
            Set<String> uniqueTypes = new HashSet<>();
            Set<String> uniqueBrands = new HashSet<>();
            Set<String> uniqueModels = new HashSet<>();

            DBUtils.getVehicleData().forEach(
                    vehicle -> {
                        uniqueTypes.add(vehicle.getvehicleType());
                        uniqueBrands.add(vehicle.getVehicleBrand());
                        uniqueModels.add(vehicle.getVehicleModel());
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
            ObservableList<String> uniqueBrandsList = FXCollections.observableArrayList(uniqueBrands);
            ObservableList<String> uniqueModelsList = FXCollections.observableArrayList(uniqueModels);

            vehicleTypes.setItems(uniqueTypesList);
            vehicleBrands.setItems(uniqueBrandsList);
            vehicleModels.setItems(uniqueModelsList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void addVehicleNodes(List<Vehicle> vehicleList){

        if(!vehicleList.isEmpty()){
            vehicleContainer.getChildren().clear();
            vehicleList.forEach(vehicle -> {
                try {
                    Node node = setVehicleData(vehicle.getVehicleImagePath(),vehicle.getVehicleModel());
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
