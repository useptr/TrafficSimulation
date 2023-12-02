module com.example.trafficsimulation {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;

    opens com.example.trafficsimulation to javafx.fxml;
    exports com.example.trafficsimulation;
    exports com.example.trafficsimulation.views;
    opens com.example.trafficsimulation.views to javafx.fxml;
    exports com.example.trafficsimulation.events;
    opens com.example.trafficsimulation.events to javafx.fxml;
    exports com.example.trafficsimulation.controllers;
    opens com.example.trafficsimulation.controllers to javafx.fxml;
    exports com.example.trafficsimulation.factories;
    opens com.example.trafficsimulation.factories to javafx.fxml;

}