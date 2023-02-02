package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage mainWindow) throws IOException {
        FXMLLoader programListLoader = new FXMLLoader(Main.class.getResource("program-list.fxml"));
        Scene programListScene = new Scene(programListLoader.load(), 600, 400);

        ChooseProgramController programListController = programListLoader.getController();

        mainWindow.setTitle("Interpreter - Choose a program");
        mainWindow.setScene(programListScene);
        mainWindow.show();

        FXMLLoader programDetailsLoader = new FXMLLoader(Main.class.getResource("program-details.fxml"));
        Scene programDetailsScene = new Scene(programDetailsLoader.load(), 906, 701);
        Stage secondaryWindow = new Stage();
        programListController.setDetailsService(programDetailsLoader.getController());
        secondaryWindow.setTitle("Interpreter");
        secondaryWindow.setScene(programDetailsScene);
        secondaryWindow.show();
    }

    public static void main(String[] args) {
        launch();
    }
}