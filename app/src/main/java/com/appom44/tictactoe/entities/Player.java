package com.appom44.tictactoe.entities;

import com.appom44.tictactoe.entities.FieldNames.PlayerFields;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by swar8080 on 11/29/2016.
 */
@DatabaseTable(tableName = "Player")
public class Player {

    //players name in lowercase which ensures case insensitive uniqueness
    //equivalent to UNIQUE COLLATE NOCASE
    @DatabaseField(id = true, useGetSet = true, columnName = PlayerFields.PLAYER_ID)
    private String id;

    //this is the players name in its original form
    @DatabaseField(canBeNull = false, columnName = PlayerFields.NAME)
    private String name;

    @ForeignCollectionField(columnName = PlayerFields.GAMES_PLAYED)
    ForeignCollection<GamePlayer> gamesPlayed;

    //needed for ORM
    public Player(){

    }

    public Player(String name){
        setName(name);
        setId(name);
    }


    public String getId() {
        return getName().toLowerCase();
    }
    public void setId(String n){
        if (n == null){
            throw new NullPointerException();
        }
        id = n.toLowerCase();
    }


    public String getName(){
        return new String(name);
    }

    public void setName(String name) {
        if (name == null){
            throw new NullPointerException("Player name cannot be null");
        }
        this.name = new String(name);
    }



}
