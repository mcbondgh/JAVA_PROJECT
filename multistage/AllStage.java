package cass.myapp.multistage;

import cass.myapp.Start;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class AllStage {
    Stage stage = new Stage();
//    public void staffInterface() throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("staffinterface.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 1280, 675);
//        StaffDashboard OBJECT = fxmlLoader.getController();
//        UserLogin LOGIN_OBJECT = new UserLogin();
//        String username = LOGIN_OBJECT.getUsername();
//        OBJECT.activeUsername.setText(username);
//        stage.setTitle("STAFF DASHBOARD");
//        stage.setScene(scene);
//        stage.setResizable(false);
//        stage.isFullScreen();
//        stage.show();
////        log.loginButton.getScene().getWindow();
////        stage.show();
//    }
    public Stage UserLogin() throws IOException {
        FXMLLoader loadFile = new FXMLLoader(Start.class.getResource("loginForm.fxml"));
        Scene scene  = new Scene(loadFile.load(), 500, 350);
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
        return stage;
    }
//
//    public Stage StaffDashboard() throws IOException {
//        FXMLLoader loadFile = new FXMLLoader(Start.class.getResource("staffDashboard.fxml"));
//        Scene scene = new Scene(loadFile.load(), 1280, 675);
//        stage.setScene(scene);
//        stage.setTitle("STAFF DASHBOARD");
//        stage.setResizable(false);
//        stage.initStyle(StageStyle.UNIFIED);
//        return stage;
//    }
//    public Stage addUserForm() throws IOException{
//        FXMLLoader loadFile = new FXMLLoader(Start.class.getResource("newuserform.fxml"));
//        Scene scene = new Scene(loadFile.load(), 450, 570);
//        Stage stage = new Stage();
//        stage.setScene(scene);
//        stage.setTitle("ADD NEW USER");
//        stage.setResizable(false);
//        stage.centerOnScreen();
//        return stage;
//    }
//    public Stage addStaffForm() throws IOException {
//        FXMLLoader loadFile = new FXMLLoader(Start.class.getResource("staffform.fxml"));
//        Scene scene = new Scene(loadFile.load(), 600, 574);
//        Stage stage = new Stage();
//        stage.setScene(scene);
//        stage.setTitle("ADD NEW USER");
//        stage.setResizable(false);
//        return stage;
//    }
//    public Stage studentForm() throws IOException {
//        FXMLLoader loadFile = new FXMLLoader(Start.class.getResource("studentform.fxml"));
//        Scene scene = new Scene(loadFile.load(), 600, 574);
//        Stage stage = new Stage();
//        stage.setScene(scene);
//        stage.setTitle("ADD NEW STUDENT");
//        stage.setResizable(false);
//        stage.show();
//        return stage;
//    }
    public void reportSheet() throws IOException {
        FXMLLoader loadFile = new FXMLLoader(Start.class.getResource("attendance-report.fxml"));
        Scene scene = new Scene(loadFile.load(), 1200, 600);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }







}
