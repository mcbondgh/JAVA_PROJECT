package cass.myapp;

import cass.myapp.multistage.AllStage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
public class Start extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        AllStage OBJECT = new AllStage();
//        Image logo = new Image("logo.png");
//        stage.getIcons().add(logo);
        stage = OBJECT.UserLogin();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}