package com.appom44.tictactoe.entities.derived;

/**
 * Created by swar8080 on 12/4/2016.
 */

public class PlayerStats {

    private int games;
    private int wins;
    private int losses;

    public int getGamesPlayed() {return games;}
    public int getWins() {
        return wins;
    }
    public int getLosses() {
        return losses;
    }

    public PlayerStats(int games, int wins, int losses){
        this.games = games;
        this.wins = wins;
        this.losses = losses;
    }

    public Double getWinPct(){
        if (games == 0){
            return null;
        }
        return new Double((double)wins/games);
    }

    public Double getLossPct() {
        if (games == 0){
            return null;
        }
        return new Double((double)losses/games);
    }

    public Double getDrawPct(){
        if (games == 0){
            return null;
        }
        return new Double((double)(games-wins-losses)/games);
    }

}
