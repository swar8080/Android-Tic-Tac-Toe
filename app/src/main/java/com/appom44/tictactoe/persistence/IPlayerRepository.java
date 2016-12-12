package com.appom44.tictactoe.persistence;

import com.appom44.tictactoe.entities.Player;

import java.sql.SQLException;

/**
 * Created by swar8080 on 12/4/2016.
 */

public interface IPlayerRepository {
    RepositoryResult<Player> getByNameCaseInsensitive(String name);
}
