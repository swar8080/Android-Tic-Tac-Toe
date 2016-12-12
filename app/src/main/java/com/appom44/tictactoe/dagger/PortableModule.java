package com.appom44.tictactoe.dagger;

import com.appom44.tictactoe.GameResultListener;
import com.appom44.tictactoe.persistence.IGameResultRepository;
import com.appom44.tictactoe.state.BoardModelStatePersistenceManager;
import com.appom44.tictactoe.state.PlayerStatePersistenceManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by swar8080 on 12/2/2016.
 */

@Module
public class PortableModule {

    @Provides
    public GameResultListener provideGameResultListener(IGameResultRepository gameResultRepository){
        return new GameResultListener(gameResultRepository);
    }

    @Provides
    public PlayerStatePersistenceManager providePlayerStatePersistenceManager(){
        return new PlayerStatePersistenceManager();
    }

    @Provides
    BoardModelStatePersistenceManager provideBoardModelStatePeristenceManager(){
        return new BoardModelStatePersistenceManager();
    }

}
