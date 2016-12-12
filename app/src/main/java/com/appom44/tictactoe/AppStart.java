package com.appom44.tictactoe;

import android.app.Application;

import com.appom44.tictactoe.dagger.DaggerMainComponent;
import com.appom44.tictactoe.dagger.DaoModule;
import com.appom44.tictactoe.dagger.MainComponent;
import com.appom44.tictactoe.persistence.DatabaseHelper;

/**
 * Created by swar8080 on 11/22/2016.
 */

public class AppStart extends Application {

    private MainComponent mainComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());

        mainComponent = DaggerMainComponent.builder()
                .daoModule(new DaoModule(databaseHelper))
                .build();
    }

    public MainComponent getMainComponent(){
        return mainComponent;
    }
}
