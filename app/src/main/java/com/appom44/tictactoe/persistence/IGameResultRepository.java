package com.appom44.tictactoe.persistence;

import com.appom44.tictactoe.entities.Player;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by swar8080 on 11/22/2016.
 */

public interface IGameResultRepository {
    boolean SaveGameResultNoWinner(ArrayList<Player> players, boolean isCompleted) throws SQLException;
    boolean SaveGameResultNoWinner(ArrayList<Player> players, boolean isCompleted, Date completed) throws SQLException;
    boolean SaveGameResultWithWinner(ArrayList<Player> players, Player winner) throws SQLException;
    boolean SaveGameResultWithWinner(ArrayList<Player> players, Player winner, Date completed) throws SQLException;


}