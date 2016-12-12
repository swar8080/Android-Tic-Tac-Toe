package com.appom44.tictactoe;

import android.util.Log;

import com.appom44.tictactoe.communication.PassiveListener;
import com.appom44.tictactoe.communication.PassiveMessanger;
import com.appom44.tictactoe.entities.Player;
import com.appom44.tictactoe.persistence.IGameResultRepository;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by swar8080 on 11/20/2016.
 */

public class GameResultListener {

    private IGameResultRepository _gameResultRepository;

    private TicTacToePlayer playerX;
    private TicTacToePlayer playerO;

    public GameResultListener (IGameResultRepository gameResultRepository){
        this._gameResultRepository = gameResultRepository;
    }

    public void TrackOneGame(final PassiveMessanger<GameState> gameUpdates, final TicTacToePlayer playerX, final TicTacToePlayer playerO){
        //todo validation
        final ArrayList<Player> players = new ArrayList<Player>();
        players.add(playerX);
        players.add(playerO);


        gameUpdates.registerPassiveGameStateListener(new PassiveListener<GameState>() {
            @Override
            public void onMessageReceived(GameState state) {
                try {
                    switch (state){
                        case XWon:
                            _gameResultRepository.SaveGameResultWithWinner(players,playerX);
                            gameUpdates.unsubscribe(this);
                            break;
                        case OWon:
                            _gameResultRepository.SaveGameResultWithWinner(players,playerO);
                            gameUpdates.unsubscribe(this);
                            break;
                        case Draw:
                            _gameResultRepository.SaveGameResultNoWinner(players, true);
                            gameUpdates.unsubscribe(this);
                            break;
                        case Reset:
                            gameUpdates.unsubscribe(this);
                            return;
                        default:
                            return;
                    }
                }
                catch (SQLException e){
                    String msg = e.getMessage() + e.getStackTrace();
                    Log.v("GameResultListener",msg);
                    //throw new RuntimeException(msg);
                }
            }
        });
    }
}
