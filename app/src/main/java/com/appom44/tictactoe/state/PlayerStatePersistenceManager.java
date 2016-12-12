package com.appom44.tictactoe.state;

import android.os.Bundle;

import com.appom44.tictactoe.BoardSpaceValue;
import com.appom44.tictactoe.TicTacToePlayer;

/**
 * Created by swar8080 on 11/20/2016.
 */

public class PlayerStatePersistenceManager extends AbstractStatePersistenceManager<TicTacToePlayer> {

    public static final String PLAYER_NAME = "PLAYER_NAME";
    public static final String BOARD_SPACE_VALUE = "BOARD_SPACE_VALUEs";


    public PlayerStatePersistenceManager(){
    }

    @Override
    protected TicTacToePlayer restore(Bundle state) {
        String name = state.getString(PLAYER_NAME);
        BoardSpaceValue boardVal = (BoardSpaceValue)state.getSerializable(BOARD_SPACE_VALUE);
        return new TicTacToePlayer(name,boardVal);
    }

    @Override
    protected Bundle save(TicTacToePlayer source) {
        return source.createBundle();
    }
}
