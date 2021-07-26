package main.models;

import javafx.animation.FadeTransition;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;


public class CardModel extends StackPane {

    Text text;
    public CardState state;
    public int viewsNumber = 0;
    public boolean showFinished = true;
    public boolean hideFinished = true;

    public CardModel(String letter) {
        Rectangle rectangle = new Rectangle(100, 100);
        rectangle.setStroke(Color.rgb(127, 100, 245));
        rectangle.setFill(Color.rgb(247, 203, 82));
        this.text = new Text(letter);
        this.text.setFont(Font.font(34));
        getChildren().addAll(rectangle, text);
        text.setOpacity(0);
        this.state = CardState.HIDDEN;
    }

    public String getCardValue() {
        return this.text.getText();
    }

    public void hideCardValue() {
        if (this.state == CardState.SHOWN && this.hideFinished) {
            this.state = CardState.HIDDEN;
            FadeTransition ft = new FadeTransition(Duration.seconds(2), text);
            ft.setOnFinished(event -> { this.hideFinished = true; });
            ft.setFromValue(1);
            ft.setToValue(0);
            ft.play();
            this.hideFinished = false;
        }
    }

    public void showCardValue() {
        if (this.state == CardState.HIDDEN && this.showFinished) {
            this.state = CardState.SHOWN;
            FadeTransition ft = new FadeTransition(Duration.seconds(0.5), text);
            ft.setOnFinished(event -> { this.showFinished = true; });
            ft.setFromValue(0);
            ft.setToValue(1);
            ft.play();
            this.showFinished = false;
            this.viewsNumber++;
        }
    }

}
