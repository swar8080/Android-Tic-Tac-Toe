package com.appom44.tictactoe.communication;

import java.util.ArrayList;

/**
 * Created by swar8080 on 12/1/2016.
 */

public abstract class PassiveMessanger<T> {

    private ArrayList<PassiveListener<T>> passiveListeners = new ArrayList<PassiveListener<T>>();

    public final void registerPassiveGameStateListener(PassiveListener<T> listener){
        passiveListeners.add(listener);
    }

    public final void unsubscribe(PassiveListener<T> listener){
        passiveListeners.remove(listener);
    }

    public final void notifyGameStateListeners(T message){
        for (PassiveListener listeners : this.passiveListeners){
            listeners.onMessageReceived(message);
        }
    }
}
