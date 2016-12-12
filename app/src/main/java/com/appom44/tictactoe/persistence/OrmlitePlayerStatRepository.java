package com.appom44.tictactoe.persistence;

import com.appom44.tictactoe.entities.FieldNames.GamePlayerFields;
import com.appom44.tictactoe.entities.GamePlayer;
import com.appom44.tictactoe.entities.Player;
import com.appom44.tictactoe.entities.derived.PlayerStats;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

/**
 * Created by swar8080 on 12/4/2016.
 */

public class OrmlitePlayerStatRepository implements IPlayerStatRepository {

    private IPlayerRepository playerRepository;
    private Dao<GamePlayer,?> gamePlayerDao;

    public OrmlitePlayerStatRepository(IPlayerRepository playerRepository, Dao<GamePlayer,?> gamePlayerDao){
        this.playerRepository = playerRepository;
        this.gamePlayerDao = gamePlayerDao;
    }

    @Override
    public RepositoryResult<PlayerStats> getPlayerStatsByPlayer(Player player) {
        int games, wins, losses;
        if (player.getId() == null){
            throw new NullPointerException("Player ID cannot be null");
        }

        String sumWins = String.format("SUM(%s)",GamePlayerFields.IS_WINNER);
        String sumLosses = String.format("COUNT(%s)-SUM(%s)",GamePlayerFields.IS_WINNER,GamePlayerFields.IS_WINNER);

        try {
            String[] statResults = gamePlayerDao.queryBuilder()
                    .selectRaw("COUNT(*)",sumWins,sumLosses)
                    .where().eq(GamePlayerFields.PLAYER,player.getId())
                    .queryRawFirst();

            games = Integer.parseInt(statResults[0]);
            wins = (statResults[1] != null)? Integer.parseInt(statResults[1]) : 0;
            losses = (statResults[2] != null)? Integer.parseInt(statResults[2]) : 0;

            return RepositoryResult.SuccessExists(new PlayerStats(games,wins,losses));

        } catch (SQLException e) {
            return RepositoryResult.Failure(e);
        }

    }

    @Override
    public RepositoryResult<PlayerStats> getPlayerStatsByPlayer(String playerName) {
        Player player;

        RepositoryResult<Player> playerResult = playerRepository.getByNameCaseInsensitive(playerName);
        if (playerResult.getCode() == RepositoryResult.ResultCode.SuccessExists){
            player = playerResult.getResult();
            return getPlayerStatsByPlayer(player);
        }
        else if (playerResult.getCode() == RepositoryResult.ResultCode.SuccessNotExists) {
            //for simplicity the stats exist even though the player doesn't
            return RepositoryResult.SuccessExists(new PlayerStats(0,0,0));
        }
        else {
            return RepositoryResult.Failure(playerResult.getException());
        }

    }
}
