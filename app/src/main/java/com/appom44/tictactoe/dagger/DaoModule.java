package com.appom44.tictactoe.dagger;

import com.appom44.tictactoe.GameResultListener;
import com.appom44.tictactoe.entities.GamePlayed;
import com.appom44.tictactoe.entities.GamePlayer;
import com.appom44.tictactoe.entities.Player;
import com.appom44.tictactoe.persistence.DatabaseHelper;
import com.appom44.tictactoe.persistence.IGameResultRepository;
import com.appom44.tictactoe.persistence.OrmliteGameResultRepository;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by swar8080 on 11/22/2016.
 */

@Module
public class DaoModule {

    private DatabaseHelper databaseHelper;

    public DaoModule(DatabaseHelper databaseHelper){
        this.databaseHelper = databaseHelper;
    }

    @Provides
    @Singleton
    public Dao<Player,String> providePlayerDao(){
        try {
            return databaseHelper.getDao(Player.class);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Provides
    @Singleton
    public Dao<GamePlayed,Integer> provideGamePlayedDao() {
        try {
            return databaseHelper.getDao(GamePlayed.class);
        } catch (SQLException e) {
            return null;
        }
    }

    @Provides
    @Singleton
    public Dao<GamePlayer,Integer> provideGamePlayerDao() {
        try {
            return databaseHelper.getDao(GamePlayer.class);
        } catch (SQLException e) {
            return null;
        }
    }
}
