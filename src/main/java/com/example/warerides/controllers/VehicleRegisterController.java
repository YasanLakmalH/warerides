package com.example.warerides.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class VehicleRegisterController implements Initializable {
    @FXML
    private VBox formContainer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        changeView("/com/example/warerides/vehicleOwnerRegisterForm.fxml");

    }
    public void changeView(String fxmlFilePath){
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFilePath));
        try {
            Node node = loader.load();
            formContainer.getChildren().clear();
            formContainer.getChildren().add(node);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
