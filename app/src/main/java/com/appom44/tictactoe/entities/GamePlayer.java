package com.appom44.tictactoe.entities;

import com.appom44.tictactoe.entities.FieldNames.GamePlayerFields;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by swar8080 on 12/1/2016.
 */

@DatabaseTable(tableName = "GamePlayer")
public class GamePlayer {

    @DatabaseField(generatedId = true, columnName = GamePlayerFields.GAME_PLAYER_ID)
    private long GamePlayerId;

    @DatabaseField(foreign = true, canBeNull = false, uniqueCombo = true, columnName = GamePlayerFields.GAME)
    private GamePlayed game;

    @DatabaseField(foreign = true, canBeNull = false, uniqueCombo = true, columnName = GamePlayerFields.PLAYER)
    private Player player;

    @DatabaseField(canBeNull = true, columnName = GamePlayerFields.IS_WINNER)
    private Boolean isWinner;

    public GamePlayer(Player player, GamePlayed gamePlayed, Boolean isWinner) {
        setGame(gamePlayed);
        setPlayer(player);
        setIsWinner(isWinner);
    }

    //needed for ORM
    public GamePlayer(){

    }

    public GamePlayed getGame() {
        return game;
    }

    public void setGame(GamePlayed game) {
        this.game = game;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Boolean getIsWinner() {
        return isWinner;
    }

    public void setIsWinner(Boolean winner) {
        isWinner = winner;
    }
}
