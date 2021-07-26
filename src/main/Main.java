package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage menuStage) throws Exception {
        Parent menuView = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("./views/MenuView.fxml")));
        menuStage.setTitle("Memory Game");
        Scene menuScene = new Scene(menuView, 600, 400);
        menuStage.setScene(menuScene);
        menuStage.show();
        menuView.requestFocus();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
