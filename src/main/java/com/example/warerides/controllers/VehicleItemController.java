package com.example.warerides.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class VehicleItemController implements Initializable {
    @FXML
    private ImageView vehicleImage;
    @FXML
    private Label vehicleModel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

    }
    public void setData(String imagePath, String modelName) {
        Image image = new Image(getClass().getResourceAsStream(imagePath));
        vehicleImage.setImage(image);
        vehicleModel.setText(modelName);
    }

}
