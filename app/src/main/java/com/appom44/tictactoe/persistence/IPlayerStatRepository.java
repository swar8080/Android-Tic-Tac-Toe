package com.appom44.tictactoe.persistence;

import com.appom44.tictactoe.entities.Player;
import com.appom44.tictactoe.entities.derived.PlayerStats;

/**
 * Created by swar8080 on 12/4/2016.
 */

public interface IPlayerStatRepository {

    RepositoryResult<PlayerStats> getPlayerStatsByPlayer(String playerName);
    RepositoryResult<PlayerStats> getPlayerStatsByPlayer(Player player);
}
