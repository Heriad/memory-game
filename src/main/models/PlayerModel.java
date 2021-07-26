package main.models;

public class PlayerModel {
    String name, time;
    int gridSize, score;

    public PlayerModel(String name, String time, int gridSize, int score) {
        this.name = name;
        this.time = time;
        this.gridSize = gridSize;
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public String serializePlayerData() {
        return this.name + " " + this.time + " " + this.gridSize + " " + this.score;
    }

    public String serializablePlayerDataForRanking() {
        return this.name + " (Time: " + this.time + ", " + convertGridSize(this.gridSize) + ") " + "Score: " + this.score;
    }

    public static PlayerModel deserializePlayerData(String[] fields) {
        String name = fields[0];
        String time = fields[1];
        int gridSize = Integer.parseInt(fields[2]);
        int score = Integer.parseInt(fields[3]);
        return new PlayerModel(name, time, gridSize, score);
    }

    public String convertGridSize(int gridSize) {
        return gridSize + "x" + gridSize;
    }

}
