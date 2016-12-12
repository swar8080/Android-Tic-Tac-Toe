package com.appom44.tictactoe.state;

import android.os.Bundle;

/**
 * Created by steven on 2016-11-05.
 */

public abstract class AbstractStatePersistenceManager<T extends IBundleable> {

    private final String SAVE_KEY = this.getClass().getName();

    protected abstract T restore(Bundle state);
    protected abstract Bundle save(T source);

    public final T restoreLastInstanaceOrDefault(Bundle activityState, T defaultIfNotExists){
        return restoreSpecificInstanaceOrDefault(activityState,null,defaultIfNotExists);
    }

    public final T restoreSpecificInstanaceOrDefault(Bundle activityState, String objIdentifier, T defaultIfNotExists){
        String key;

        if (activityState != null){
            key = SaveKey(objIdentifier);
            if (activityState.containsKey(key)){
                return restore(activityState.getBundle(key));
            }
            else {
                return defaultIfNotExists;
            }
        }
        else {
            return defaultIfNotExists;
        }
    }

    public final void saveSingleState(T source, Bundle activityState){
        saveSpecificState(source,activityState,null);
    }

    public final void saveSpecificState(T source, Bundle activityState, String objIdentifier){
        if (activityState != null){
            activityState.putBundle(SaveKey(objIdentifier),this.save(source));
        }
    }



    private String SaveKey(String identifier){
        if (identifier != null){
            return SAVE_KEY + identifier;
        }
        else {
            return SAVE_KEY;
        }
    }

}
