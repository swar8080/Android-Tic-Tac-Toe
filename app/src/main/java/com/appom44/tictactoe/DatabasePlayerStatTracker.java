package com.appom44.tictactoe;

/**
 * Created by swar8080 on 11/20/2016.
 */

public class DatabasePlayerStatTracker extends AbstractPlayerStatTracker {

    public DatabasePlayerStatTracker(Player player){
        super(player);
    }

    @Override
    protected int games() {
        return 0;
    }

    @Override
    protected int wins() {
        return 0;
    }

    @Override
    protected int losses() {
        return 0;
    }

    @Override
    protected int draws() {
        return 0;
    }

    @Override
    protected void saveWin() {

    }

    @Override
    protected void saveLoss() {

    }

    @Override
    protected void saveDraw() {

    }
}
