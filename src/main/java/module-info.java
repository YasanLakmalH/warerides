module com.example.warerides {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.warerides to javafx.fxml;
    exports com.example.warerides;
    exports com.example.warerides.controllers;
    opens com.example.warerides.controllers to javafx.fxml;
}