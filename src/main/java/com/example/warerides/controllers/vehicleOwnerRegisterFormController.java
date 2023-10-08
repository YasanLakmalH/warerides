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

public class vehicleOwnerRegisterFormController implements Initializable {
    @FXML
    private TextField vehicleOwnerNameText;
    @FXML
    private TextField vehicleOwnerAddressText;
    @FXML
    private TextField vehicleOwnerDOBText;
    @FXML
    private TextField vehicleOwnerNICText;
    @FXML
    private Button submitButton;
    @FXML
    private AnchorPane formContainer;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    if(!vehicleOwnerNameText.getText().isEmpty() &&
                            vehicleOwnerAddressText.getText().isEmpty() &&
                            vehicleOwnerDOBText.getText().isEmpty() &&
                            vehicleOwnerNICText.getText().isEmpty()
                    ) {
                        int vehicleOwnerId = DBUtils.registerVehicleOwner(
                                vehicleOwnerNameText.getText(),
                                vehicleOwnerAddressText.getText(),
                                vehicleOwnerDOBText.getText(),
                                vehicleOwnerNICText.getText()
                        );
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/warerides/vehicleRegisterForm.fxml"));
                        try {
                            Node node = loader.load();
                            vehicleRegisterFormController vehicleRegisterFormController = loader.getController();
                            vehicleRegisterFormController.setVehicleOwnerId(vehicleOwnerId);
                            formContainer.getChildren().clear();
                            formContainer.getChildren().add(node);

                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Please fill the blanks");
                        alert.show();
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            }
        });
    }
}
