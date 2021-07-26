package main.utils;

import javafx.application.Platform;
import javafx.scene.control.Label;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class GameTimer {

    long countingStart;
    long countedTime;
    ScheduledExecutorService executor;

    public GameTimer() {
        this.countingStart = System.currentTimeMillis();
    }

    public int getTimeInSeconds() {
        return (int) this.countedTime / 1000;
    }

    public void startTimer(Label timeCounter) {
        executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> {
            if (!Thread.interrupted()) {
                this.countedTime = System.currentTimeMillis() - this.countingStart;
                long countedMinutes = (countedTime / 1000) / 60;
                long countedSeconds = (countedTime / 1000) % 60;
                Platform.runLater(() -> timeCounter.setText(getGameTime(countedMinutes, countedSeconds)));
            }
        }, 0, 1000, TimeUnit.MILLISECONDS);

    }

    public void stopTimer() {
        this.executor.shutdown();
    }

    public String getGameTime(long countedMinutes, long countedSeconds) {
        String gameTime = "";
        if (countedSeconds < 10 && countedMinutes < 10) {
            gameTime = "0" + countedMinutes + ":0" + countedSeconds;
        } else if (countedMinutes < 10) {
            gameTime = "0" + countedMinutes + ":" + countedSeconds;
        } else {
            gameTime = countedMinutes + ":" + countedSeconds;
        }
        return gameTime;
    }

}
