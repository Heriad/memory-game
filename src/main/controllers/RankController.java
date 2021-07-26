package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.utils.ScoreSorter;
import main.models.PlayerModel;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


public class RankController {

    int gridSize, playerScore;
    String gameTime;

    @FXML
    Label descLabel, errorLabel;

    @FXML
    Button okBtn;

    @FXML
    AnchorPane rankPane;

    @FXML
    TextField playerNameField;

    public void setGridSize(int gridSize) {
        this.gridSize = gridSize;
    }

    public void setPlayerScore(int playerScore) {
        this.playerScore = playerScore;
    }

    public void setGameTime(String gameTime) {
        this.gameTime = gameTime;
    }

    public void savePlayerScore() throws IOException {
        String errorText = validatePlayerName();
        if (errorText.length() > 0) {
            showError(errorText);
            return;
        }
        Path path = Path.of("../../high_scores.txt");
        if (!Files.exists(path)) {
            FileWriter fileWriter = new FileWriter("../../high_scores.txt");
        }
        List<PlayerModel> highScores = new ArrayList<>();
        readScoresFromFile(highScores);
        PlayerModel currentPlayer = new PlayerModel(this.playerNameField.getText(), this.gameTime, this.gridSize, this.playerScore);
        highScores.add(currentPlayer);
        highScores.sort(new ScoreSorter().reversed());
        savePlayerScoreToFile(highScores);
        Stage currentStage = (Stage) okBtn.getScene().getWindow();
        currentStage.close();
    }

    public void readScoresFromFile(List<PlayerModel> highScores) {
        String line;
        try {
            BufferedReader bf = new BufferedReader(new FileReader("../../high_scores.txt"));
            while ((line = bf.readLine()) != null) {
                String[] fields = line.split((" "));
                PlayerModel player = PlayerModel.deserializePlayerData(fields);
                highScores.add(player);
            }
            bf.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void savePlayerScoreToFile(List<PlayerModel> highScores) {
        try {
            FileWriter fileWriter = new FileWriter("../../high_scores.txt");
            for (PlayerModel highScore : highScores) {
                fileWriter.write((highScore.serializePlayerData()));
                fileWriter.write("\r\n");
            }
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException err) {
            err.printStackTrace();
        }
    }

    public void showError(String errorText) {
        Stage currentStage = (Stage) okBtn.getScene().getWindow();
        currentStage.setHeight(225);
        errorLabel.setText(errorText);
    }

    public String validatePlayerName() {
        String nameError = "";
        if (playerNameField.getText().length() == 0) {
            nameError = "Player name field can't be empty";
        }
        return nameError;
    }

}
