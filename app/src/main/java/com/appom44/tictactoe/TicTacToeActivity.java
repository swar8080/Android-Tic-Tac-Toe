package com.appom44.tictactoe;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.appom44.tictactoe.entities.TicTacToePlayer;
import com.appom44.tictactoe.portable.ConfirmationDialog;
import com.appom44.tictactoe.portable.IConfirmationDialogListener;
import com.appom44.tictactoe.game_logic.BoardModel;
import com.appom44.tictactoe.game_logic.BoardSpaceValue;
import com.appom44.tictactoe.state.BoardModelStatePersistenceManager;
import com.appom44.tictactoe.state.PlayerStatePersistenceManager;
import com.appom44.tictactoe.views.BoardLayout;

import javax.inject.Inject;


public class TicTacToeActivity extends Activity {

    public static final String PLAYER1_NAME = "PLAYER1_NAME";
    public static final String PLAYER2_NAME = "PLAYER2_NAME";

	private BoardLayout boardView;

    @Inject BoardModelStatePersistenceManager bmspm;
    private BoardModel boardModel;

    private TicTacToePlayer player1,player2;
    @Inject PlayerStatePersistenceManager playerStatePersistenceManager;

	private MoveController moveController;
	private GameCommentaryListener gameCommentaryListener;

    @Inject GameResultListener gameResultListener;

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);

        String p1Name,p2Name;
        final TextView p1Link,p2Link;

        //inflates the views
        setContentView(R.layout.activity_game);

        //dependency injection
        ((AppStart)getApplication()).getMainComponent().inject(this);

        Bundle playerExtra = getIntent().getExtras();

        if (playerExtra != null){
            p1Name = playerExtra.getString(PLAYER1_NAME);
            p2Name = playerExtra.getString(PLAYER2_NAME);
        }
        else {
            p1Name = getResources().getString(R.string.player1_default_name);
            p2Name = getResources().getString(R.string.player2_default_name);
        }

        player1 = playerStatePersistenceManager.restoreSpecificInstanceOrDefault(
                state,
                PLAYER1_NAME,
                new TicTacToePlayer(p1Name, BoardSpaceValue.X)
        );
        player2 = playerStatePersistenceManager.restoreSpecificInstanceOrDefault(state,
                PLAYER2_NAME,
                new TicTacToePlayer(p2Name,BoardSpaceValue.O)
        );

        p1Link = ((TextView)super.findViewById(R.id.Player1Link));
        p1Link.setPaintFlags(p1Link.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        p1Link.setText(player1.getName());
        registerPlayerStatDialogLink(p1Link,p1Name);

        p2Link = ((TextView)super.findViewById(R.id.Player2Link));
        p2Link.setPaintFlags(p2Link.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        p2Link.setText(player2.getName());
        registerPlayerStatDialogLink(p2Link,p2Name);

        boardView = (BoardLayout)super.findViewById(R.id.BoardLayout);

        boardModel = bmspm.restoreLastInstanceOrDefault(state,
                new BoardModel(boardView.rowCount(),boardView.columnCount())
        );

        moveController = new MoveController(boardView,boardModel);
        boardModel.registerPassiveGameStateListener(moveController);

        gameCommentaryListener = new GameCommentaryListener((TextView)super.findViewById(R.id.game_status), player1, player2);
        boardModel.registerPassiveGameStateListener(gameCommentaryListener);

        gameResultListener.trackOneGame(boardModel,player1,player2);

        //have listeners react to their first event
        boardModel.notifyGameStateListeners(boardModel.getState());

        ((Button)findViewById(R.id.reset_game)).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                boardModel.reset();
                gameResultListener.trackOneGame(boardModel,player1,player2);
            }
        });

    }

    private void registerPlayerStatDialogLink(View link, final String playerName){
        link.setOnClickListener(new View.OnClickListener() {

            final String fragmentTag = "statDialog";

            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment previousDialog = getFragmentManager().findFragmentByTag(fragmentTag);
                if(previousDialog != null){
                    ft.remove(previousDialog);
                }

                PlayerStatDialogFragment newStatDialog = PlayerStatDialogFragment.newInstance(playerName);
                newStatDialog.show(ft,fragmentTag);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle state){
        super.onSaveInstanceState(state);
        bmspm.saveSingleState(boardModel,state);
        playerStatePersistenceManager.saveSpecificState(player1,state,PLAYER1_NAME);
        playerStatePersistenceManager.saveSpecificState(player2,state,PLAYER2_NAME);
    }


    @Override
    public void onBackPressed() {
        ConfirmationDialog dialog = new ConfirmationDialog(
                this,
                "Are you sure you want to go back? All game progess will be lost",
                null);

        dialog.registerConfirmationDialogListener(new IConfirmationDialogListener() {
            @Override
            public void onYesClicked() {
                //the dialog is closed automatically, go back
                TicTacToeActivity.super.onBackPressed();
            }

            @Override
            public void onNoClicked() {
                //the dialog is closed automatically, do nothing (continue playing)
            }
        });

        dialog.getUserSelection();
    }
}
