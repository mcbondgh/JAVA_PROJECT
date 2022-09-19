package cass.myapp.multistage;

import cass.myapp.Start;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class UniversalController {
    public UniversalController() {}

    @FXML
    private static BorderPane view;


    public static BorderPane switchView(String provideFxmlFileName) {
        try {
            FXMLLoader loadFile = new FXMLLoader(Start.class.getResource(provideFxmlFileName));
            view.setCenter(loadFile.load());
        } catch (Exception x) {
            x.printStackTrace();
        }
        return view;
    }












}
