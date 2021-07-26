package main.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.stage.Window;


public class MenuController  {

    @FXML
    Label newGameLabel, highScoresLabel, exitLabel, welcomeLabel;

    @FXML
    GridPane gridPane;

    public void startNewGame() throws IOException {
        Stage selectSizeStage = new Stage();
        selectSizeStage.setTitle("New Game");
        FXMLLoader selectSizeLoader = new FXMLLoader(getClass().getResource("../views/SelectSizeView.fxml"));
        Parent selectSizeView = selectSizeLoader.load();
        Scene selectSizeScene = new Scene(selectSizeView, 350, 150);
        selectSizeStage.setScene(selectSizeScene);
        selectSizeStage.show();
        selectSizeView.requestFocus();
        selectSizeStage.setAlwaysOnTop(true);
    }

    public void checkHighScores() throws IOException {
        Stage menuStage = (Stage)highScoresLabel.getScene().getWindow();
        menuStage.setTitle("High Scores");
        Parent highScoresView = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../views/HighScoresView.fxml")));
        Scene highScoresScene = new Scene(highScoresView, 600, 400);
        menuStage.setScene(highScoresScene);
        menuStage.show();
        setShortcutToMenu(menuStage);
    }

    public void exitGame() {
        Platform.exit();
        System.exit(0);
    }

    public static void changeSceneFromMenuToGame(int spinnerValue) {
        List<Window> openedWindows = new ArrayList<>(Window.getWindows());
        Stage menuStage = (Stage)openedWindows.get(0);
        try {
            FXMLLoader gameLoader = new FXMLLoader(MenuController.class.getResource("../views/GameView.fxml"));
            Parent gameView = gameLoader.load();
            GameController gameController = gameLoader.getController();
            gameController.setGridSize(spinnerValue);
            menuStage.setTitle("Game");
            Scene gameScene = new Scene(gameView, 600, 400);
            menuStage.setScene(gameScene);
            menuStage.show();
            setShortcutToMenu(menuStage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setShortcutToMenu(Stage menuStage) {
        menuStage.getScene().getAccelerators().put(
            KeyCombination.keyCombination("CTRL+SHIFT+Q"),
            () -> {
                setMenuView(menuStage);
            }
        );
    }

    public static void setMenuView(Stage menuStage) {
        try {
            Parent menuView = FXMLLoader.load(Objects.requireNonNull(MenuController.class.getResource("../views/MenuView.fxml")));
            menuStage.setTitle("Memory Game");
            Scene menuScene = new Scene(menuView, 600, 400);
            menuStage.setScene(menuScene);
            menuStage.setWidth(600);
            menuStage.setHeight(440);
            menuStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
