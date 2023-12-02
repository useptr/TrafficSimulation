package com.example.trafficsimulation.controllers;

import com.example.trafficsimulation.factories.CarFactory;
import com.example.trafficsimulation.models.Vehicle;
import com.example.trafficsimulation.trafficlight.TrafficLightsControl;
import com.example.trafficsimulation.trafficlight.models.TextFieldContainInt;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import org.apache.poi.*;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

public class Controller {
    private final static CarFactory carFactory = new CarFactory();
    private final double WIDTH=700, HEIGHT=300;
    private final double DURATION = 10; // 10 millis - 100 fps
    public boolean sameTimeAcceleration = false;
    private TrafficLightsControl trafficLightsControl = new TrafficLightsControl();
    private LinkedList<VehicleController> vehicleControllers = VehicleController.vehicleControllers;
    private AnchorPane map = new AnchorPane();
    private int simulationTime = 0;
    // 10 millis - 100 fps
    private Timeline timeline = new Timeline(new KeyFrame(Duration.millis(DURATION),this::vehicleHandler));
    private Rectangle road = new Rectangle(WIDTH, HEIGHT/3);
    private Rectangle car = new Rectangle(20, 15);
    private NumberAxis xAxis = new NumberAxis();
    private NumberAxis yAxis  = new NumberAxis();
    private LineChart<Number,Number> lineChart = new LineChart(xAxis, yAxis);
    private XYChart.Series series = new XYChart.Series();
    @FXML
    private void initialize() {
        map.setMaxWidth(WIDTH);
        map.setMaxHeight(HEIGHT);
        map.setPrefWidth(WIDTH);
        map.setPrefHeight(HEIGHT);
        map.setStyle("-fx-background-color: #c8c8c8;");
        map.setClip(null);
        map.setClip(new Rectangle(map.getPrefWidth(), map.getPrefHeight()));

        road.setFill(Color.web("#949391"));
        road.setLayoutY(HEIGHT-road.getHeight());
        map.getChildren().add(road);

        trafficLightsControl.setPrefHeight(40);
        map.getChildren().add(trafficLightsControl);
        trafficLightsControl.setLayoutX(WIDTH/2-trafficLightsControl.getPrefHeight()/2);
        trafficLightsControl.setLayoutY(HEIGHT/3*2-trafficLightsControl.getPrefHeight()*2);



        timeline.setCycleCount(Animation.INDEFINITE);
//        screenHBox.getChildren().add(mapController.getMap());
        screenHBox.getChildren().add(map);

        series.setName("Количество машин от времени (с)");
        lineChart.getData().add(series);
        PaneChart.getChildren().add(lineChart);

    }
    public void start() {
        reset();
        trafficLightsControl.on();
        timeline.play();
        createVehicle();
        TextFieldContainInt textFieldContainInt = parseStageDuration(TextFieldNewCarAppearance);
        if (textFieldContainInt.containInt) {
            carAppearance = textFieldContainInt.value;
        } else {
            TextFieldNewCarAppearance.setText(""+carAppearance);
        }
    }
    public void reset() {
        simulationTime = 0;
        passedVehicles = 0;
        timeline.stop();
        trafficLightsControl.off();

        series.getData().clear();
//        lineChart.getData().clear();
//        lineChart.getData().add(series);
        for (VehicleController vehicleController :vehicleControllers) {
            map.getChildren().remove(vehicleController.view.view());
        }
        vehicleControllers.clear();
    }
    private int passedVehicles = 0;
    private double countdown = 0;
    private int carAppearance = 2;
//    private void drawChart() {
////        if (lineChart.getData() != null)
////            lineChart.getData().clear();
////        PaneChart.getChildren().clear();
//        lineChart.getData().add(series);
//        PaneChart.getChildren().add(lineChart);
//    }
private void exportLineChartToExcel() {
    SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");
    Date date = new Date();
//    System.out.println(formatter.format(date));
    String fileName = "report_"+formatter.format(date)+".xlx";
//    String fileName = "report.xlx";
//    System.out.println(fileName);
    // Export LineChart data to Excel using Apache POI
    try {
//        FileInputStream fis = new FileInputStream(fileName);
//        Workbook workbook = new HSSFWorkbook();
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("LineChart Data");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("X Values");
        headerRow.createCell(1).setCellValue("Y Values");

//        XYChart.Series<Number, Number> series = lineChart.getData().get(0);
        ObservableList<XYChart.Data<Number, Number>> data = series.getData();
        for (int i = 0; i < data.size(); i++) {
            Row dataRow = sheet.createRow(i + 1);
            dataRow.createCell(0).setCellValue(data.get(i).getXValue().doubleValue());
            dataRow.createCell(1).setCellValue(data.get(i).getYValue().doubleValue());
        }
        FileOutputStream fos = new FileOutputStream(fileName);
        workbook.write(fos);
        fos.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
}
    public void vehicleHandler(ActionEvent event) {
        if (simulationTime%1000 == 0) {
//            System.out.println(simulationTime);
            series.getData().add(new XYChart.Data(simulationTime/1000, passedVehicles));
//            drawChart();
        }
        simulationTime += DURATION;
        LabelSimulationTime.setText("время сиуляции: " + simulationTime/1000);
        LabelPassedVehicles.setText("машины проехавшие светофор: " + passedVehicles);
        countdown += DURATION;
        if (countdown > carAppearance*1000 ) { // && count < 3
            countdown = 0;
            createVehicle();
        }
//        double offset = 2;
//        car.setX(car.getX()+offset);

        moveVehicles();
//        removeVehiclesBeyondMap();
    }
    @FXML
    private void buttonChartExportAction() {
        exportLineChartToExcel();
    }
    @FXML
    private void buttonStartAction() {
        ButtonStart.setDisable(true);
        ButtonReset.setDisable(false);
        start();
    }
    @FXML
    private void buttonResetAction() {
        ButtonReset.setDisable(true);
        ButtonStart.setDisable(false);
        reset();
    }
    public void moveVehicles() {
        for (VehicleController vehicle : vehicleControllers) {
            vehicle.update(DURATION);
            if (vehicle.vehicle.x() + vehicle.vehicle.width() > vehicle.nextTrafficLightPosX && !vehicle.passed) {
                vehicle.passed = true;
                passedVehicles++;
            }
//            view.vehicle.update(2);
//            view.update();
//            System.out.println(view.view().getLayoutX() + " " + view.view().getLayoutY());
        }
    }
    public void createVehicle() {
        VehicleController controller = carFactory.createVehicle(20, 15);

        double x = -controller.vehicle.width();
        double y = road.getLayoutY()+road.getHeight()/2-controller.vehicle.height()/2;

        for (VehicleController vehicleController :vehicleControllers) {
            Vehicle other = vehicleController.vehicle;
            if (other.x() <= x+other.width())
                return;
        }
        if (sameTimeAcceleration)
            controller.setSameTimeAcceleration(true);
        controller.vehicle.setPosition(x, y);

        map.getChildren().add(controller.view.view());
        controller.view.setPosition(x, y);
        vehicleControllers.add(controller);
//        controller.setNextTrafficLight(WIDTH/2-trafficLightsControl.getPrefHeight()/2);
        controller.setNextTrafficLight(trafficLightsControl.getLayoutX()); // WIDTH/2
        trafficLightsControl.events.subscribeAll(controller);
    }
    private TextFieldContainInt parseStageDuration(TextField textField) {
        TextFieldContainInt textFieldContainInt = new TextFieldContainInt();
        try {
            textFieldContainInt.value = Integer.parseInt(textField.getText().trim());
            if (textFieldContainInt.value < 1)
                textFieldContainInt.containInt = false;
            else
                textFieldContainInt.containInt = true;
        } catch (NumberFormatException e) {
            textFieldContainInt.containInt = false;
        }
        return textFieldContainInt;
    }

    @FXML
    private Pane PaneChart;
    @FXML
    private AnchorPane root;
@FXML
private TextField TextFieldNewCarAppearance;

    @FXML
    private HBox screenHBox;
    @FXML
    private Label LabelSimulationTime;
    @FXML
    private Label LabelPassedVehicles;
    @FXML
    private ToggleButton ToggleButtonSameTimeAccelerating;
    @FXML
    private Button ButtonStart;
    @FXML
    private Button ButtonReset;
    @FXML
    private void toggleButtonSameTimeAcceleratingAction() {
        if (ToggleButtonSameTimeAccelerating.isSelected()) {
            ToggleButtonSameTimeAccelerating.setText("Вкл.");
            sameTimeAcceleration = true;
//            ToggleButtonSameTimeAccelerating.on();
        }
        else {
            ToggleButtonSameTimeAccelerating.setText("Выкл.");
            sameTimeAcceleration = false;
//            trafficLightsControl.reset();
        }
    }
//    protected void onHelloButtonClick() {
//        welcomeText.setText("Welcome to JavaFX Application!");
//    }
}