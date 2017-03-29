package com.appom44.tictactoe.state;

import android.os.Bundle;

import com.appom44.tictactoe.game_logic.BoardModel;
import com.appom44.tictactoe.game_logic.BoardSpaceValue;
import com.appom44.tictactoe.game_logic.GameState;
import com.appom44.tictactoe.portable.AbstractStatePersistenceManager;

/**
 * Created by steven on 2016-11-05.
 */

public class BoardModelStatePersistenceManager extends AbstractStatePersistenceManager<BoardModel> {

    public static final String ROWS = "rows";
    public static final String COLUMNS = "columns";
    public static final String BOARD_VALUES = "BoardValues";
    public static final String GAME_STATE = "GameState";


    @Override
    protected BoardModel restore(Bundle state) {
        return new BoardModel(
            state.getInt(ROWS),
            state.getInt(COLUMNS),
            (BoardSpaceValue[])state.getSerializable(BOARD_VALUES),
            (GameState)state.getSerializable(GAME_STATE)
        );
    }

    @Override
    protected Bundle save(BoardModel source) {
        return source.createBundle();
    }

}
