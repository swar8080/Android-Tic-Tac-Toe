package com.appom44.tictactoe.entities;

import android.os.Bundle;

import com.appom44.tictactoe.game_logic.BoardSpaceValue;
import com.appom44.tictactoe.entities.Player;
import com.appom44.tictactoe.portable.IBundleable;
import com.appom44.tictactoe.state.PlayerStatePersistenceManager;

public class TicTacToePlayer extends Player implements IBundleable  {
    private BoardSpaceValue boardSpaceValue;

    public TicTacToePlayer(String name, BoardSpaceValue boardSpaceValue){
        super(name);
        this.boardSpaceValue = boardSpaceValue;
    }

    public BoardSpaceValue getBoardSpaceValue() {
        return boardSpaceValue;
    }

    public void setBoardSpaceValue(BoardSpaceValue boardSpaceValue) {
        this.boardSpaceValue = boardSpaceValue;
    }

    @Override
    public Bundle createBundle() {
        Bundle bundle = new Bundle();

        bundle.putString(PlayerStatePersistenceManager.PLAYER_NAME,getName());
        return bundle;
    }
}
