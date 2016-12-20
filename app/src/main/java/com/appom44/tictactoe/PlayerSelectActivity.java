package com.appom44.tictactoe;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.appom44.tictactoe.persistence.AndroidDatabaseManager;
import com.appom44.tictactoe.persistence.DatabaseHelper;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

public class PlayerSelectActivity extends Activity {

    private EditText player1,player2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_select);

        player1 = (EditText)findViewById(R.id.player1_edit_text);
        player2 = (EditText)findViewById(R.id.player2_edit_text);

        SharedPreferences sharedPreferences = this.getPreferences(MODE_PRIVATE);
        player1.setText(
                sharedPreferences.getString(
                    getString(R.string.last_playerX_name),
                    getString(R.string.default_player1_name)
                )
        );
        player2.setText(
                sharedPreferences.getString(
                        getString(R.string.last_playerO_name),
                        getString(R.string.default_player2_name)
                )
        );

        Button startButton = (Button)findViewById(R.id.start_game_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gameStartIntent = new Intent();
                //pass this PlayerSelectActivity as the current app context, to be sent to the next activity
                gameStartIntent.setClass(PlayerSelectActivity.this,TicTacToeActivity.class);
                gameStartIntent.putExtra(TicTacToeActivity.PLAYER1_NAME,player1.getText().toString());
                gameStartIntent.putExtra(TicTacToeActivity.PLAYER2_NAME,player2.getText().toString());
                startActivity(gameStartIntent);
            }
        });

        ((Button)findViewById(R.id.database_manager_button)).setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent dbIntent = new Intent();
                  dbIntent.setClass(PlayerSelectActivity.this,AndroidDatabaseManager.class);
                  startActivity(dbIntent);
              }
          }

        );

    }
}
