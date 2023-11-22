package com.example.trafficsimulation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader();
        Pane root = fxmlLoader.load(App.class.getResource("main-screen.fxml"));
        Controller controller = (Controller)fxmlLoader.getController();

        Scene scene = new Scene(root);
        stage.setTitle("Traffic simulation");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}