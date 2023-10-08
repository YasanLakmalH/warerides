package com.example.warerides.controllers;

import com.example.warerides.DBUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    private Label homeButton;
    @FXML
    private Label newInquiryButton;
    @FXML
    private Label vehicleRegisterButton;
    @FXML
    private Label branchIdLabel;
    @FXML
    private Label userNameLabel;
    @FXML
    private Label userIdLabel;
    @FXML
    private AnchorPane menuViewer;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setDefaultData();
        homeButton.setOnMouseClicked(event -> {
            changeView("/com/example/warerides/Home.fxml");
        });
        newInquiryButton.setOnMouseClicked(event -> {
            changeView("/com/example/warerides/NewInquiry.fxml");
        });
        vehicleRegisterButton.setOnMouseClicked(event -> {
            changeView("/com/example/warerides/VehicleRegister.fxml");
        });

    }

    public void changeView(String fxmlFilePath){
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFilePath));
        try {
            Node node = loader.load();
            menuViewer.getChildren().clear();
            menuViewer.getChildren().add(node);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void setUserData(int userId, String userName,String branchId){
        userIdLabel.setText("#"+ userId);
        userNameLabel.setText(userName);
        branchIdLabel.setText(branchId);
    }
    public void setDefaultData(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/warerides/Home.fxml"));
        try {
            Node node = loader.load();
            menuViewer.getChildren().add(node);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }














//    public void setWaitingQueueCustomerNames(ArrayList<Customer> waitingCustomersList){
//        try {
//            for (int i = 0; i < waitingCustomersList.size(); i++) {
//                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gui/customerDetailsItem.fxml"));
//                Node node = loader.load();
//                customerDetailsItemController controller = loader.getController();
//                controller.setFirstNameLabel(waitingCustomersList.get(i).getFirstName());
//                controller.setSecondNameLabel(waitingCustomersList.get(i).getSecondName());
//                nameBox.getChildren().add(node);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
