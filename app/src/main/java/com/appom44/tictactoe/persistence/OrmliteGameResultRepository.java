package com.appom44.tictactoe.persistence;

import com.appom44.tictactoe.entities.GamePlayed;
import com.appom44.tictactoe.entities.GamePlayer;
import com.appom44.tictactoe.entities.Player;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by swar8080 on 12/1/2016.
 */

public class OrmliteGameResultRepository implements IGameResultRepository {

    private Dao<Player,?> playerDao;
    private Dao<GamePlayed,?> gameDao;
    private Dao<GamePlayer,?> gamePlayerDao;


    public OrmliteGameResultRepository(
            Dao<Player,?> playerDao,
            Dao<GamePlayed,?> gameDao,
            Dao<GamePlayer,?> gamePlayerDao)
    {
        this.playerDao = playerDao;
        this.gameDao = gameDao;
        this.gamePlayerDao = gamePlayerDao;
    }


    private boolean SaveGameResult(ArrayList<Player> players, Player winner, boolean isCompleted, Date completedDate) throws SQLException {
        Player savedPlayer;
        Boolean playerWon;

        if (winner != null && players.indexOf(winner) == -1){
            throw new IllegalArgumentException("Winner must be included in list of players");
        }

        GamePlayed game = new GamePlayed();
        game.setCompleted(isCompleted);
        game.setDate(completedDate);
        gameDao.create(game);

        for (Player possiblyUnsavedPlayer : players) {
            savedPlayer = playerDao.createIfNotExists(possiblyUnsavedPlayer);

            if (winner == null){
                playerWon = null;
            }
            else if (possiblyUnsavedPlayer == winner){
                playerWon = true;
            }
            else {
                playerWon = false;
            }

            gamePlayerDao.create(new GamePlayer(savedPlayer,game,playerWon));
        }
        return false;
    }


    @Override
    public boolean SaveGameResultWithWinner(ArrayList<Player> players, Player winner) throws SQLException {
        return SaveGameResultWithWinner(players,winner,new Date());
    }

    @Override
    public boolean SaveGameResultWithWinner(ArrayList<Player> players, Player winner, Date completed) throws SQLException {
        return SaveGameResult(players,winner,true,completed);
    }

    @Override
    public boolean SaveGameResultNoWinner(ArrayList<Player> players, boolean isCompleted) throws SQLException {
        return SaveGameResultNoWinner(players,isCompleted,new Date());
    }

    @Override
    public boolean SaveGameResultNoWinner(ArrayList<Player> players, boolean isCompleted, Date completedDate) throws SQLException {
        return SaveGameResult(players,null,isCompleted,completedDate);
    }

}
