package com.appom44.tictactoe.dagger;

import com.appom44.tictactoe.entities.GamePlayed;
import com.appom44.tictactoe.entities.GamePlayer;
import com.appom44.tictactoe.entities.Player;
import com.appom44.tictactoe.persistence.IGameResultRepository;
import com.appom44.tictactoe.persistence.IPlayerRepository;
import com.appom44.tictactoe.persistence.IPlayerStatRepository;
import com.appom44.tictactoe.persistence.OrmliteGameResultRepository;
import com.appom44.tictactoe.persistence.OrmlitePlayerRepository;
import com.appom44.tictactoe.persistence.OrmlitePlayerStatRepository;
import com.j256.ormlite.dao.Dao;

import dagger.Module;
import dagger.Provides;

/**
 * Created by swar8080 on 12/1/2016.
 */

@Module
public class RepositoryModule {

    @Provides
    public IGameResultRepository provideGameResultRepository(
            Dao<Player,String> playerDao,
            Dao<GamePlayed,Integer> gameDao,
            Dao<GamePlayer,Integer> gamePlayerDao)
    {
        return new OrmliteGameResultRepository(playerDao,gameDao,gamePlayerDao);
    }


    @Provides
    public IPlayerRepository providePlayerRepository(Dao<Player,String> playerDao){
        return new OrmlitePlayerRepository(playerDao);
    }

    @Provides
    public IPlayerStatRepository providePlayerStatRepository(Dao<GamePlayer,Integer> gamePlayerDao, IPlayerRepository playerRepository){
        return new OrmlitePlayerStatRepository(playerRepository,gamePlayerDao);
    }
}
