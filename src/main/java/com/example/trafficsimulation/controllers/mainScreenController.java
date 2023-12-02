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

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

public class mainScreenController {
    private final static CarFactory carFactory = new CarFactory(); // абрика для создания новых автомобилей
    private final double WIDTH=700, HEIGHT=300; // размер окна симуляции
    private final double DURATION = 10; // частота обновления экрана 10 милисекунд - 100 fps
    public boolean sameTimeAcceleration = false; // включина ли стратегия одновременного разгона
    private TrafficLightsControl trafficLightsControl = new TrafficLightsControl(); // графический элемент светофора
    private LinkedList<VehicleController> vehicleControllers = VehicleController.vehicleControllers; // список всех автомобилей
    private AnchorPane map = new AnchorPane(); // корневой элемент для всех объектов окна симуляции
    private int simulationTime = 0; // время симуляции
    private Timeline timeline = new Timeline(new KeyFrame(Duration.millis(DURATION),this::handle)); // таймер для перерисовки объектов каждый новый кадр
    private Rectangle road = new Rectangle(WIDTH, HEIGHT/3); // дорога
    private NumberAxis xAxis = new NumberAxis(); // данные по оси x для графика
    private NumberAxis yAxis  = new NumberAxis(); // данные по оси y для графика
    private LineChart<Number,Number> lineChart = new LineChart(xAxis, yAxis); // данные для графика
    private XYChart.Series series = new XYChart.Series(); // элемент для построения графика
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
    } // сбросить симуляцию
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
    } // запустить симуляцию
    private int passedVehicles = 0; // количество проехавших машин
    private double countdown = 0; // подсчет времени для генерации машин
    private int carAppearance = 2; // время появления новой машины
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
} // сохранение графика в excel документ
    public void handle(ActionEvent event) {
        if (simulationTime%1000 == 0) {
            series.getData().add(new XYChart.Data(simulationTime/1000, passedVehicles));
        }
        simulationTime += DURATION;
        LabelSimulationTime.setText("время сиуляции: " + simulationTime/1000);
        LabelPassedVehicles.setText("машины проехавшие светофор: " + passedVehicles);
        countdown += DURATION;
        if (countdown > carAppearance*1000 ) {
            countdown = 0;
            createVehicle();
        }
        moveVehicles();
        removeVehiclesBeyondMap();
    } // метод вызывающийся каждый новый кадр
    public void moveVehicles() {
        for (VehicleController vehicle : vehicleControllers) {
            vehicle.update(DURATION);
            if (vehicle.vehicle.x() + vehicle.vehicle.width() > vehicle.nextTrafficLightPosX && !vehicle.passed) {
                vehicle.passed = true;
                passedVehicles++;
            }
        }
    } // передвинуть все автомобили
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
        controller.setNextTrafficLight(trafficLightsControl.getLayoutX()); // WIDTH/2
        trafficLightsControl.events.subscribeAll(controller);
    } // создать новый автомобиль
    public void removeVehiclesBeyondMap() {
        for (int i =0; i < vehicleControllers.size();) {
            Vehicle vehicle = vehicleControllers.get(i).vehicle;
            if (vehicle.x() > WIDTH) {
                vehicleControllers.remove(i);
            } else
                ++i;
        }
    } // удалить автомобиль покинувший сцену
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
        }
        else {
            ToggleButtonSameTimeAccelerating.setText("Выкл.");
            sameTimeAcceleration = false;
        }
    }
}