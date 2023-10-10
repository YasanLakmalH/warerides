package com.example.warerides.controllers;

import com.example.warerides.DBUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class vehicleRegisterFormController implements Initializable{
    @FXML
    private Button submitButton;
    @FXML
    private Button cancelButton;
    @FXML
    private AnchorPane formContainer;
    @FXML
    private TextField vehicleOwnerIdText;
    @FXML
    private TextField vehicleNoText;
    @FXML
    private TextField vehicleTypeText;
    @FXML
    private TextField vehicleBrandText;
    @FXML
    private TextField vehicleModelText;
    @FXML
    private TextField branchIdText;
    @FXML
    private TextField vehicleImagePathText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    if (!vehicleNoText.getText().isEmpty() &&
                            vehicleTypeText.getText().isEmpty() &&
                            vehicleBrandText.getText().isEmpty() &&
                            vehicleModelText.getText().isEmpty() &&
                            branchIdText.getText().isEmpty() &&
                            vehicleImagePathText.getText().isEmpty() &&
                            vehicleOwnerIdText.getText().isEmpty()
                    ) {
                        DBUtils.registerVehicle(vehicleNoText.getText(),
                                vehicleTypeText.getText(),
                                vehicleBrandText.getText(),
                                vehicleModelText.getText(),
                                branchIdText.getText(),
                                vehicleImagePathText.getText(),
                                Integer.parseInt(vehicleOwnerIdText.getText())
                        );
                    }else{
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Please fill the blanks");
                        alert.show();
                    }
                    } catch(SQLException e){
                        throw new RuntimeException(e);
                    }

            }
        });
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
               changeView("/com/example/warerides/vehicleOwnerRegisterForm.fxml");
            }
        });
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
    public void setVehicleOwnerId(int id){
        vehicleOwnerIdText.setText(String.valueOf(id));
    }

}
