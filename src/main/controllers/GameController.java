package main.controllers;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import main.utils.GameTimer;
import main.models.CardModel;
import main.models.CardState;
import java.io.IOException;
import java.net.URL;
import java.util.*;


public class GameController implements Initializable {

    boolean isGameFinished = false;
    int gridSize, cardsPairs;
    int matchedPairs = 0;
    int luckyFounds = 0;

    GameTimer gt;
    List<CardModel> selectedCards = new ArrayList<>();

    EventHandler<MouseEvent> mouseClickHandler = event -> {
        if (!isGameFinished && ((CardModel)event.getSource()).state != CardState.FOUND) {
            ((CardModel)event.getSource()).showCardValue();
            if (!selectedCards.isEmpty() && selectedCards.get(0) == event.getSource()) {
                return;
            }
            selectedCards.add(((CardModel)event.getSource()));
            try {
                compareSelectedCards();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    @FXML
    Label timeCounter, pairsFound, timeLabel, completedText;

    @FXML
    GridPane gridPane;

    @FXML
    ScrollPane scrollPane;

    @FXML
    BorderPane borderPane;

    public void setGridSize(int gridSize) {
        this.gridSize = gridSize;
        cardsPairs = (int) Math.pow(gridSize, 2) / 2;
        updatePairsFound();
        createCards();
    }

    public void updatePairsFound() {
        pairsFound.setText(matchedPairs + "/" + cardsPairs);
    }

    public void compareSelectedCards() throws IOException {
        if (selectedCards.size() == 2) {
            if (!selectedCards.get(0).getCardValue().equals(selectedCards.get(1).getCardValue())) {
                selectedCards.get(0).hideCardValue();
                selectedCards.get(1).hideCardValue();
            } else {
                matchedPairs++;
                updatePairsFound();
                selectedCards.get(0).state = CardState.FOUND;
                selectedCards.get(1).state = CardState.FOUND;
                if (selectedCards.get(0).viewsNumber == 1 && selectedCards.get(1).viewsNumber == 1) {
                    this.luckyFounds++;
                }
            }
            selectedCards.clear();
            if (checkIfGameCompleted()) {
                this.isGameFinished = true;
                this.stopTimer();
                this.openRankWindow();
            }
        }
    }

    public boolean checkIfGameCompleted() {
        return matchedPairs == cardsPairs;
    }

    public void openRankWindow() throws IOException {
        Stage rankStage = new Stage();
        rankStage.setTitle("Rank");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/RankView.fxml"));
        Parent rankView = loader.load();
        RankController rankController = loader.getController();
        rankController.setGridSize(this.gridSize);
        rankController.setGameTime(this.timeCounter.getText());
        rankController.setPlayerScore(calculateScore());
        Scene rankScene = new Scene(rankView, 350, 160);
        rankStage.setScene(rankScene);
        rankStage.show();
        rankView.requestFocus();
        rankStage.setOnHidden(event -> {
            List<Window> openedWindows = new ArrayList<>(Window.getWindows());
            Stage menuStage = (Stage)openedWindows.get(0);
            MenuController.setMenuView(menuStage);
        });
    }

    public int calculateScore() {
        return ((gridSize * 1000) / gt.getTimeInSeconds()) + (luckyFounds * 200);
    }

    public void createCards() {
        int cardText = 1;
        List<CardModel> cards = new ArrayList<>();
        for (int i=0; i < cardsPairs; i++) {
            for (int j=0; j < 2; j++) {
                cards.add(new CardModel(Integer.toString(cardText)));
            }
            cardText++;
        }
        Collections.shuffle(cards);

        GridPane gameGridPane = new GridPane();
        gameGridPane.setAlignment(Pos.CENTER);
        borderPane.setCenter((gameGridPane));

        for (int i=0; i<cards.size();i++) {
            cards.get(i).setOnMouseClicked(mouseClickHandler);
            gameGridPane.add(cards.get(i), i%gridSize, (i/gridSize) + 1);
        }
        setGameWindowSize();
    }

    public void setGameWindowSize() {
        List<Window> openedWindows = new ArrayList<>(Window.getWindows());
        Stage gameStage = (Stage)openedWindows.get(0);
        gameStage.setHeight((gridSize * 100) + 200);
        gameStage.setWidth((gridSize * 50) + 550);
        int minHeight = 76+(gridSize * 100);
        int minWidth = 100+(gridSize * 100);
        scrollPane.setStyle("-fx-min-height: " + minHeight + ";" + "-fx-min-width: " + minWidth + ";");
        gridPane.setStyle("-fx-min-height: " + minHeight + ";" + "-fx-min-width: " + minWidth + ";");
    }

    public void stopTimer() {
        gt.stopTimer();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)  {
        gt = new GameTimer();
        gt.startTimer(timeCounter);
    }

}
