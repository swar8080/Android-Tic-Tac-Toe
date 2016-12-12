package com.appom44.tictactoe;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.appom44.tictactoe.persistence.AndroidDatabaseManager;
import com.appom44.tictactoe.state.BoardModelStatePersistenceManager;
import com.appom44.tictactoe.state.PlayerStatePersistenceManager;

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

        try {
            ((AppStart) this.getApplication()).getMainComponent().inject(this);
        }
        catch (RuntimeException ru){

        }

        String p1Name,p2Name;
        final TextView p1Link,p2Link;

        //inflates the views
        setContentView(R.layout.activity_game);

        Bundle playerExtra = getIntent().getExtras();

        if (playerExtra != null){
            p1Name = playerExtra.getString(PLAYER1_NAME);
            p2Name = playerExtra.getString(PLAYER2_NAME);
        }
        else {
            p1Name = getResources().getString(R.string.player1_default_name);
            p2Name = getResources().getString(R.string.player2_default_name);
        }

        player1 = playerStatePersistenceManager.restoreSpecificInstanaceOrDefault(
                state,
                PLAYER1_NAME,
                new TicTacToePlayer(p1Name,BoardSpaceValue.X)
        );
        player2 = playerStatePersistenceManager.restoreSpecificInstanaceOrDefault(state,
                PLAYER2_NAME,
                new TicTacToePlayer(p2Name,BoardSpaceValue.O)
        );

        p1Link = ((TextView)super.findViewById(R.id.Player1Link));
        p1Link.setText(player1.getName());
        registerPlayerStatDialogLink(p1Link,p1Name);

        p2Link = ((TextView)super.findViewById(R.id.Player2Link));
        p2Link.setText(player2.getName());
        registerPlayerStatDialogLink(p2Link,p2Name);

        boardView = (BoardLayout)super.findViewById(R.id.BoardLayout);

        boardModel = bmspm.restoreLastInstanaceOrDefault(state,
                new BoardModel(boardView.rowCount(),boardView.columnCount())
        );

        moveController = new MoveController(boardView,boardModel);
        boardModel.registerPassiveGameStateListener(moveController);

        gameCommentaryListener = new GameCommentaryListener((TextView)super.findViewById(R.id.game_status), player1, player2);
        boardModel.registerPassiveGameStateListener(gameCommentaryListener);

        gameResultListener.TrackOneGame(boardModel,player1,player2);

        //have listeners react to their first event
        boardModel.notifyGameStateListeners(boardModel.getState());

        ((Button)findViewById(R.id.reset_game)).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                boardModel.reset();
                gameResultListener.TrackOneGame(boardModel,player1,player2);
            }
        });

        ((Button)findViewById(R.id.save_game)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dbIntent = new Intent();
                dbIntent.setClass(TicTacToeActivity.this,AndroidDatabaseManager.class);
                startActivity(dbIntent);
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
                ft.addToBackStack(null);

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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
