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

        //todo move SharedPreference get/set to seperate class
        final SharedPreferences sharedPreferences = this.getPreferences(MODE_PRIVATE);

        player1.setText(
            sharedPreferences.getString(
                getString(R.string.last_player1_name_key),
                getString(R.string.default_player1_name)
            )
        );
        player2.setText(
            sharedPreferences.getString(
                    getString(R.string.last_player2_name_key),
                    getString(R.string.default_player2_name)
            )
        );

        Button startButton = (Button)findViewById(R.id.start_game_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String enteredP1Name = player1.getText().toString();
                String enteredP2Name = player2.getText().toString();

                SharedPreferences.Editor sharedPreferenceEditor = sharedPreferences.edit();
                sharedPreferenceEditor.putString(getString(R.string.last_player1_name_key), enteredP1Name);
                sharedPreferenceEditor.putString(getString(R.string.last_player2_name_key), enteredP2Name);
                sharedPreferenceEditor.apply();

                Intent gameStartIntent = new Intent();
                //pass this PlayerSelectActivity as the current app context, to be sent to the next activity
                gameStartIntent.setClass(PlayerSelectActivity.this,TicTacToeActivity.class);
                gameStartIntent.putExtra(TicTacToeActivity.PLAYER1_NAME,enteredP1Name);
                gameStartIntent.putExtra(TicTacToeActivity.PLAYER2_NAME,enteredP2Name);
                startActivity(gameStartIntent);
            }
        });

    }
}
