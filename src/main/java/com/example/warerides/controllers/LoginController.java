package com.example.warerides.controllers;

import com.example.warerides.DBUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private Button signInButton;
    @FXML
    private Button closeButton;
    @FXML
    private TextField userNameText;
    @FXML
    private PasswordField passwordText;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        signInButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!userNameText.getText().trim().isEmpty() && !passwordText.getText().trim().isEmpty()){
                    try {
                        DBUtils.logInVerify(event, "Dashboard.fxml", userNameText.getText().toLowerCase(), passwordText.getText());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }else{
                    System.out.println("Fill in all information");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please fill in all information to sign up!");
                    alert.show();
                }
            }
        });

        closeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.exit(0);
            }
        });
    }
}