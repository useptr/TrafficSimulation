package com.example.trafficsimulation.controllers;

import com.example.trafficsimulation.factories.CarFactory;
import com.example.trafficsimulation.models.Vehicle;
import com.example.trafficsimulation.trafficlight.TrafficLightsControl;
import com.example.trafficsimulation.trafficlight.models.TextFieldContainInt;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class mainScreenController {
    @FXML
    private ListView<String> windowsListView; // ListView всех открытых симуляций
    private HashMap<String, Stage> simulations = new HashMap<>(); // HashMap со всеми открытыми окнами где ключ заголовок окна, а значение Stage окна
    private int number = 0; // счетчик открытых окон
    @FXML
    private void createNewSimulationAction() {
        Stage newStage = new Stage();
        newStage.setTitle("Simulation #" + number);

        SimulationController simulation = new SimulationController();
        Scene newScene = new Scene(simulation.root(), 800, 300);

        newStage.setScene(newScene);
        newStage.setOnCloseRequest(event -> removeFromAllControls(newStage.getTitle()));
        newStage.show();

        ++number;
        simulations.put(newStage.getTitle(), newStage);
        windowsListView.getItems().add(newStage.getTitle());
    } // создание симуляции в новом окне
    @FXML
    private void closeSimulation() {
        MultipleSelectionModel<String> selectionModel = windowsListView.getSelectionModel();
        int id = selectionModel.getSelectedIndex();
        String name = selectionModel.getSelectedItem();

        Stage selectedStage = simulations.get(name);
        selectedStage.close();

        windowsListView.getItems().remove(id);
    } // обработка нажатия на кнопку "закрыть выбранную симуляцию"
    private void removeFromAllControls(String name) {
        simulations.remove(name);
        windowsListView.getItems().clear();
        for(Map.Entry<String, Stage> entry : simulations.entrySet()) {
            String key = entry.getKey();
            windowsListView.getItems().add(key);
        }
    } // закрытие выбранного окна и удаление его из windowsListView и simulations
}

