package main.utils;

import main.models.PlayerModel;

import java.util.Comparator;

public class ScoreSorter implements Comparator<PlayerModel> {

    @Override
    public int compare(PlayerModel p1, PlayerModel p2) {
        return Integer.compare(p1.getScore(), p2.getScore());
    }
}




