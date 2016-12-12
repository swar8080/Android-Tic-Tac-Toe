package com.appom44.tictactoe.dagger;

import android.app.Activity;

import com.appom44.tictactoe.AppStart;
import com.appom44.tictactoe.PlayerStatDialogFragment;
import com.appom44.tictactoe.TicTacToeActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by swar8080 on 11/22/2016.
 */

@Component(modules={DaoModule.class,RepositoryModule.class,PortableModule.class})
@Singleton
public interface MainComponent {
    void inject(TicTacToeActivity activity);
    void inject(PlayerStatDialogFragment playerStatDialogFragment);
}
