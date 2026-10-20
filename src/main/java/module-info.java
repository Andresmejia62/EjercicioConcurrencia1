module com.appregistros.ejemploconcurrencia1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.appregistros.ejemploconcurrencia1 to javafx.fxml;
    exports com.appregistros.ejemploconcurrencia1;
}