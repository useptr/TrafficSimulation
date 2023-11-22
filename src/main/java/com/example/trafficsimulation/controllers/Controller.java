package com.example.trafficsimulation.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class Controller {
    MapController mapController = new MapController();
    @FXML
    private void initialize() {
//        root.getChildren().add(mapController.getMap());
        screenHBox.getChildren().add(mapController.getMap());
    }
    @FXML
    private AnchorPane root;
//    @FXML
//    private Label welcomeText;
//
    @FXML
    private HBox screenHBox;

//    @FXML
//    protected void onHelloButtonClick() {
//        welcomeText.setText("Welcome to JavaFX Application!");
//    }
}