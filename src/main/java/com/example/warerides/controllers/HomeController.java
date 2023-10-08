package com.example.warerides.controllers;

import com.example.warerides.DBUtils;
import com.example.warerides.models.Vehicle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    @FXML
    private HBox carContainer;
    @FXML
    private HBox suvContainer;
    @FXML
    private HBox vanContainer;
    @FXML
    private Label registeredVehicleCountLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        try {
//            Replace lambda expression for upcoming updates
            DBUtils.getVehicleData().forEach(vehicle -> {
                setVehicleContainerData(vehicle);
            });
            setCountData(DBUtils.getRegisteredVehicleCount());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void setCountData(int count){
        registeredVehicleCountLabel.setText(String.valueOf(count)+"+");
    }
    public void setVehicleContainerData(Vehicle vehicle){
        try {
            String vehicleType = vehicle.getvehicleType();
            if(vehicleType.equals("CAR")){
                Node node = setVehicleData(vehicle.getVehicleImagePath(), vehicle.getVehicleModel());
                carContainer.getChildren().add(node);
            }
            else if(vehicleType.equals("SUV") || vehicleType.equals("CROSSOVER")){
                Node node = setVehicleData(vehicle.getVehicleImagePath(), vehicle.getVehicleModel());
                suvContainer.getChildren().add(node);
            } else if (vehicleType.equals("VAN") || vehicleType.equals("MINIVAN")) {
                Node node = setVehicleData(vehicle.getVehicleImagePath(), vehicle.getVehicleModel());
                vanContainer.getChildren().add(node);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
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
