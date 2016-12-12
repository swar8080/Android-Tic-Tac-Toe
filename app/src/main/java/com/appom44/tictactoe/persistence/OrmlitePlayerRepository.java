package com.appom44.tictactoe.persistence;

import com.appom44.tictactoe.entities.FieldNames.PlayerFields;
import com.appom44.tictactoe.entities.Player;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

/**
 * Created by swar8080 on 12/4/2016.
 */

public class OrmlitePlayerRepository implements IPlayerRepository {


    private Dao<Player,String> playerDao;

    public OrmlitePlayerRepository(Dao<Player,String> playerDao){
        this.playerDao = playerDao;
    }

    @Override
    public RepositoryResult<Player> getByNameCaseInsensitive(String name){

        try {
            Player player = playerDao.queryBuilder()
                    .where()
                    .like(PlayerFields.NAME,name)
                    .queryForFirst();
            if (player != null){
                return RepositoryResult.SuccessExists(player);
            }
            else {
                return RepositoryResult.SuccessNotExists();
            }
        } catch (SQLException e) {
            return RepositoryResult.Failure(e);
        }
    }
}
