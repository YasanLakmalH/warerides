package com.example.warerides;

import com.example.warerides.controllers.DashboardController;
import com.example.warerides.models.Service;
import com.example.warerides.models.Vehicle;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static javafx.fxml.FXMLLoader.load;

public class DBUtils {
    public static Connection DBConnection() throws SQLException {
        String password = System.getProperty("database.password");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/panther", "root", password);
    }
    public static void LoginChangeScene(ActionEvent event, String fxmlFile, int userId, String userName, String branchId) {
        Parent root = null;
        if (userId != 0) {
            try {
                FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
                root = loader.load();
                DashboardController dashboardController = loader.getController();
                dashboardController.setUserData(userId,userName,branchId);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else {
            try {
                root = load(DBUtils.class.getResource(fxmlFile));

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
        Stage primaryStage = new Stage();
        Scene scene = new Scene(root);
//        scene.setFill(Color.TRANSPARENT);
//        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.centerOnScreen();
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void logInVerify(ActionEvent event, String fxmlFile, String userName, String userPassword) throws SQLException {
        Connection connection = DBConnection();
        String receivedPassword = null;
        int userId = 0;
        String branchId = null;

        PreparedStatement psCheckUserExist = connection.prepareStatement("SELECT * FROM users WHERE userName=?");
        psCheckUserExist.setString(1, userName);
        ResultSet resultSet = psCheckUserExist.executeQuery();

        if (!resultSet.isBeforeFirst()) {
            System.out.println("User not found in the database!");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("User does not exist");
            alert.show();
        } else {
            while (resultSet.next()) {
                receivedPassword = resultSet.getString("userPassword");
                userId = resultSet.getInt("userId");
                branchId = resultSet.getString("branchId");
            }
        }
        if (receivedPassword.equals(userPassword)) {
            System.out.println("Success");
            LoginChangeScene(event, fxmlFile, userId,userName,branchId);

        } else {
            System.out.println("Password doesn't match!");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Password doesn't match");
            alert.show();
        }
        resultSet.close();
        psCheckUserExist.close();
        connection.close();
    }
    public static int getRegisteredVehicleCount() throws SQLException{
        Connection connection = DBConnection();
        int vehicleCount = 0;
        String query = "SELECT COUNT(vehicleId) AS vehicleCount FROM vehicles;";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while(resultSet.next()){
            vehicleCount = resultSet.getInt("vehicleCount");
        }
        resultSet.close();
        statement.close();
        connection.close();
        return vehicleCount;
    }
    public static List<Vehicle> getVehicleData() throws SQLException {
        Connection connection = DBConnection();
        Statement statement;
        ResultSet resultSet;
        String query = "SELECT * FROM vehicles;";
        statement = connection.createStatement();
        resultSet = statement.executeQuery(query);
        List<Vehicle> vehicleList = new ArrayList<>();

        createVehicleList(vehicleList,resultSet);

        resultSet.close();
        statement.close();
        connection.close();
        return vehicleList;
    }
    public static int newVehicleOwnerIdGenerator() throws SQLException{
        Connection connection = DBConnection();
        ResultSet resultSet;
        int newVehicleOwnerId = 0;
        String query = "SELECT COUNT(vehicleOwnerId) AS vehicleOwnersCount FROM vehicleOwners;";
        Statement statement = connection.createStatement();
        resultSet = statement.executeQuery(query);
        while(resultSet.next()){
            newVehicleOwnerId = resultSet.getInt("vehicleOwnersCount")+1;
        }
        resultSet.close();
        statement.close();
        connection.close();
        return newVehicleOwnerId;
    }
    public static int newVehicleIdGenerator(){
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        int newVehicleId = 0;
        String query = "SELECT COUNT(vehicleId) AS vehicleCount FROM vehicles;";
        try{
            connection = DBConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                newVehicleId = resultSet.getInt("vehicleCount")+1;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            if(resultSet != null){
                try{
                    resultSet.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(resultSet != null){
                try{
                    statement.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(connection != null){
                try{
                    connection.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }

            }
        }
        return newVehicleId;
    }
    public static int registerVehicleOwner(String name, String address, String dateOfBirth, String NIC ) throws SQLException{
        Connection connection = null;
        PreparedStatement psInsertVehicleOwner = null;
        int vehicleOwnerId = newVehicleOwnerIdGenerator();
        try{
            connection = DBConnection();
            psInsertVehicleOwner = connection.prepareStatement("INSERT INTO vehicleowners VALUES(?,?,?,?,?)");
            psInsertVehicleOwner.setInt(1,vehicleOwnerId);
            psInsertVehicleOwner.setString(2,name);
            psInsertVehicleOwner.setString(3,address);
            psInsertVehicleOwner.setString(4,dateOfBirth);
            psInsertVehicleOwner.setString(5,NIC);
            psInsertVehicleOwner.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Successful");
            alert.show();

        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            psInsertVehicleOwner.close();
            connection.close();
        }
        return vehicleOwnerId;
    }
    public static int registerVehicle(String vehicleNo,String vehicleBrand, String vehicleType, String vehicleModel, String branchId, String imagePath, int vehicleOwnerId ) throws SQLException{
        Connection connection = null;
        PreparedStatement psInsertVehicleOwner = null;
        try{
            connection = DBConnection();
            int vehicleId = newVehicleIdGenerator();
            psInsertVehicleOwner = connection.prepareStatement("INSERT INTO vehicles VALUES(?,?,?,?,?,?,?,?)");
            psInsertVehicleOwner.setInt(1,vehicleId);
            psInsertVehicleOwner.setString(2,vehicleNo);
            psInsertVehicleOwner.setString(3,vehicleBrand);
            psInsertVehicleOwner.setString(4,vehicleType);
            psInsertVehicleOwner.setString(5,vehicleModel);
            psInsertVehicleOwner.setString(6,branchId);
            psInsertVehicleOwner.setString(7,imagePath);
            psInsertVehicleOwner.setInt(8,vehicleOwnerId);
            psInsertVehicleOwner.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Successful");
            alert.show();
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            if(psInsertVehicleOwner != null){
                try{
                    psInsertVehicleOwner.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(connection != null){
                try{
                    connection.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
        }
        return vehicleOwnerId;
    }
    public static List<Vehicle> getNoneServingVehicles(String vehicleType){
        List<Vehicle> vehicleList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement psGetVehicles = null;
        ResultSet resultSet = null;
        try{
            connection = DBConnection();
            psGetVehicles = connection.prepareStatement("SELECT * FROM vehicles WHERE vehicleType=? AND servingStatus=0");
            psGetVehicles.setString(1,vehicleType);
            resultSet = psGetVehicles.executeQuery();
            createVehicleList(vehicleList,resultSet);
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            if(resultSet != null){
                try{
                    connection.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
            if(psGetVehicles != null){
                try{
                    psGetVehicles.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
            if(connection != null){
                try{
                    connection.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }

        }
        return vehicleList;
    }
    public static List<Vehicle> getAvailableServingVehicles(String pickupDate, String returnDate, String vehicleType){
        Connection connection = null;
        PreparedStatement psGetVehicles = null;
        ResultSet resultSet = null;
        List<Vehicle> vehicleList = new ArrayList<>();
        try{
            connection = DBConnection();
            psGetVehicles = connection.prepareStatement(
                    "SELECT * FROM vehicles INNER JOIN inquiries ON vehicles.vehicleNo = inquiries.vehicleNo WHERE (inquiries.pickupDate > ? or inquiries.returnDate < ?) and vehicles.vehicleType=?;"
            );
            psGetVehicles.setString(1,returnDate);
            psGetVehicles.setString(2,pickupDate);
            psGetVehicles.setString(3,vehicleType);
            resultSet = psGetVehicles.executeQuery();

            createVehicleList(vehicleList,resultSet);

        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            if(resultSet != null){
                try{
                    resultSet.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
            if(psGetVehicles != null){
                try{
                    psGetVehicles.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
            if(connection != null){
                try{
                    connection.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }

        }
        return vehicleList;
    }
    public static List<Service> getServiceTypes(){
        List<Service> services = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try{
            connection = DBConnection();
            String query = "SELECT * FROM services;";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                services.add(
                        new Service(
                                resultSet.getInt("serviceId"),
                                resultSet.getString("serviceType")
                        )
                );
            }

        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            try{
                resultSet.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
            try{
                statement.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
            try{
                connection.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return services;
    }

    public static List<String> getPickupLocations(){
        List<String> locations = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try{
            connection = DBConnection();
            String query = "SELECT * FROM branches;";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                locations.add(resultSet.getString("branchName"));
            }

        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            try{
                resultSet.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
            try{
                statement.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
            try{
                connection.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return locations;
    }

    public static void createVehicleList(List<Vehicle> vehicleList,ResultSet resultSet) throws SQLException {
        while(resultSet.next()){
            Vehicle vehicle = new Vehicle();
            vehicle.setVehicleId(resultSet.getInt("vehicleId"));
            vehicle.setVehicleNo(resultSet.getString("vehicleNo"));
            vehicle.setVehicleType(resultSet.getString("vehicleType"));
            vehicle.setVehicleBrand(resultSet.getString("vehicleBrand"));
            vehicle.setVehicleModel(resultSet.getString("vehicleModel"));
            vehicle.setVehicleImagePath(resultSet.getString("vehicleImagePath"));
            vehicle.setVehicleOwnerId(resultSet.getInt("vehicleOwnerId"));
            vehicle.setBranchId(resultSet.getString("branchId"));
            vehicleList.add(vehicle);
        }

    }
    public static void submitQuery( int serviceId, String pickupLocation, String pickupDate, String returnDate, String vehicleType, String vehicleNo) throws SQLException {
        Connection connection = null;
        PreparedStatement psInsertnewInquiry = null;
        int inquiryId = newInquiryIdGenerator();
        try{
            connection = DBConnection();
            psInsertnewInquiry = connection.prepareStatement("INSERT INTO inquiries VALUES(?,?,?,?,?,?,?)");
            psInsertnewInquiry.setInt(1,inquiryId);
            psInsertnewInquiry.setInt(2,serviceId);
            psInsertnewInquiry.setString(3,pickupLocation);
            psInsertnewInquiry.setString(4,pickupDate);
            psInsertnewInquiry.setString(5,returnDate);
            psInsertnewInquiry.setString(6,vehicleType);
            psInsertnewInquiry.setString(7,vehicleNo);

            psInsertnewInquiry.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Successful");
            alert.show();

        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            psInsertnewInquiry.close();
            connection.close();
        }

    }
    public static int newInquiryIdGenerator(){
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        int newInquiryId = 0;
        String query = "SELECT COUNT(inquiryId) AS inquiryCount FROM inquiries;";
        try{
            connection = DBConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                newInquiryId = resultSet.getInt("inquiryCount")+1;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            if(resultSet != null){
                try{
                    resultSet.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(resultSet != null){
                try{
                    statement.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(connection != null){
                try{
                    connection.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }

            }
        }
        return newInquiryId;
    }
}





