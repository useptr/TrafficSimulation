module com.example.trafficsimulation {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.trafficsimulation to javafx.fxml;
    exports com.example.trafficsimulation;
}