package main.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

public class SelectSizeController implements Initializable {

    @FXML
    Spinner<Integer> selectValueSpinner;

    @FXML
    Button playBtn;

    @FXML
    AnchorPane selectSizeWindow;

    @FXML
    Label errorLabel, selectSizeText;

    public void startPlaying() {
        String errorText = validateSelectedGridSize();
        if (errorText.length() > 0) {
            showError(errorText);
            return;
        }
        closeSelectSizeWindow();
        MenuController.changeSceneFromMenuToGame(getSpinnerValue());
    }

    public int getSpinnerValue() {
        return selectValueSpinner.getValue();
    }

    public void closeSelectSizeWindow() {
        Stage currentStage = (Stage) playBtn.getScene().getWindow();
        currentStage.close();
    }

    public void showError(String errorText) {
        Stage currentStage = (Stage) selectSizeWindow.getScene().getWindow();
        currentStage.setHeight(225);
        errorLabel.setText(errorText);
    }

    public String validateSelectedGridSize() {
        String sizeError = "";
        if (selectValueSpinner.getValue() % 2 == 1) {
            sizeError = "The size of the grid must be an even number";
        }
        return sizeError;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SpinnerValueFactory<Integer> fieldValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 10);
        fieldValueFactory.setValue(2);
        selectValueSpinner.setValueFactory(fieldValueFactory);
    }
}
