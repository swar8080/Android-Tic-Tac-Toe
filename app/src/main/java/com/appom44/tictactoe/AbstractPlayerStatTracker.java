package com.appom44.tictactoe;

/**
 * Created by swar8080 on 11/20/2016.
 */

public abstract class AbstractPlayerStatTracker implements PassiveGameStateListener {

    protected Player player;

    public AbstractPlayerStatTracker(Player player){
        this.player = player;
    }

    public final PlayerStats getStats(){
        return new PlayerStats(games(),wins(),losses(),draws());
    }

    protected abstract int games();
    protected abstract int wins();
    protected abstract int losses();
    protected abstract int draws();

    @Override
    public void onGameStateChange(GameState state){
        switch (state){
            case XWon:
                if (player.getBoardSpaceValue() == BoardSpaceValue.X){
                    saveWin();
                }
                else {
                    saveLoss();
                }
                break;
            case OWon:
                if (player.getBoardSpaceValue() == BoardSpaceValue.O){
                    saveWin();
                }
                else {
                    saveLoss();
                }
                break;
            case Draw:
                saveDraw();

        }
    }

    protected abstract void saveWin();
    protected abstract void saveLoss();
    protected abstract void saveDraw();


    private class PlayerStats {

        private int games, won, lost, draws;

        private PlayerStats(int games, int won, int lost, int draws){
            this.games = games;
            this.won = won;
            this.lost = lost;
            this.draws = draws;
        }

        public int getGames() {
            return games;
        }

        public int getWon() {
            return won;
        }

        public int getLost() {
            return lost;
        }

        public int getDraws() {
            return draws;
        }

    }


}
