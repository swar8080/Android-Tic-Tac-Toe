package com.appom44.tictactoe;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appom44.tictactoe.entities.derived.PlayerStats;
import com.appom44.tictactoe.persistence.IPlayerRepository;
import com.appom44.tictactoe.persistence.IPlayerStatRepository;
import com.appom44.tictactoe.persistence.RepositoryResult;

import javax.inject.Inject;

/**
 * Created by swar8080 on 12/4/2016.
 */

public class PlayerStatDialogFragment extends DialogFragment {

    private static final String PLAYER_NAME = "PLAYER_NAME";
    private String playerName;
    private PlayerStats playerStats;

    @Inject IPlayerStatRepository playerStatRepository;

    public static PlayerStatDialogFragment newInstance(String playerName){

        PlayerStatDialogFragment instance = new PlayerStatDialogFragment();

        Bundle args = new Bundle();
        args.putString(PLAYER_NAME,playerName);
        instance.setArguments(args);

        return instance;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        ((AppStart)this.getActivity().getApplication()).getMainComponent().inject(this);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = this.getArguments();
        if (args != null && args.containsKey(PLAYER_NAME)){
            playerName = args.getString(PLAYER_NAME);
        }
        else {
            //todo handle this better
            playerName = "";
        }

        RepositoryResult<PlayerStats> playerStatResult = playerStatRepository.getPlayerStatsByPlayer(playerName);
        if (playerStatResult.getCode() == RepositoryResult.ResultCode.SuccessExists){
            playerStats = playerStatResult.getResult();
        }
        else {
            playerStats = null;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View dialog = inflater.inflate(R.layout.player_stat_layout,container,false);

        ((TextView)dialog.findViewById(R.id.player_heading_player_name)).setText(playerName);

        //default values
        String gamesPlayedString = "0";
        String winString = "-";
        String lossString = "-";

        if (playerStats != null){
            gamesPlayedString = String.valueOf(playerStats.getGamesPlayed());
            winString = String.valueOf(playerStats.getWins());
            lossString = String.valueOf(playerStats.getLosses());
        }

        ((TextView)dialog.findViewById(R.id.stat_games_played)).setText(gamesPlayedString);
        ((TextView)dialog.findViewById(R.id.stat_games_won_pct)).setText(winString);
        ((TextView)dialog.findViewById(R.id.stat_games_lost_pct)).setText(lossString);

        return dialog;
    }


}
