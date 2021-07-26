package main.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import main.models.PlayerModel;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HighScoresController implements Initializable {

    @FXML
    ScrollPane scrollPane;

    @FXML
    VBox vBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String line;
        try {
            BufferedReader bf = new BufferedReader(new FileReader("../../high_scores.txt"));
            int positionCounter = 1;
            while ((line = bf.readLine()) != null) {
                String[] fields = line.split((" "));
                PlayerModel player = PlayerModel.deserializePlayerData(fields);
                Label playerLabel = new Label(positionCounter + ". " + player.serializablePlayerDataForRanking());
                playerLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16");
                vBox.getChildren().add(playerLabel);
                positionCounter++;
            }
            scrollPane.setContent(vBox);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
