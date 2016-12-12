package com.appom44.tictactoe.entities;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created by swar8080 on 12/1/2016.
 */

@DatabaseTable(tableName = "GamePlayed")
public class GamePlayed {

    @DatabaseField(generatedId = true)
    private int gameId;

    @ForeignCollectionField(eager = false)
    private ForeignCollection<GamePlayer> participants;

    @DatabaseField
    private boolean completed;

    @DatabaseField
    private Date date;

    //needed for ORM
    public GamePlayed(){

    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void addParticipant(GamePlayer gamePlayer) {
        this.participants.add(gamePlayer);
    }

}
