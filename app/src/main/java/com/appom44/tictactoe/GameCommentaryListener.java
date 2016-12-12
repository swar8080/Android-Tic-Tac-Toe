package com.appom44.tictactoe;

import android.widget.TextView;

import com.appom44.tictactoe.communication.PassiveListener;
import com.appom44.tictactoe.entities.Player;

public class GameCommentaryListener implements PassiveListener<GameState> {

	private TextView mTextView;
	private Player playerX, playerO;
	
	GameCommentaryListener(TextView textView, final Player playerX, final Player playerO){
		this.mTextView = textView;
		this.playerX = playerX;
		this.playerO = playerO;
	}
	
	@Override
	public void onMessageReceived(GameState state) {
		String commentary;

		switch (state){
			case TurnX:
				commentary = String.format("%s's turn",playerX.getName());
				break;
			case TurnO:
				commentary = String.format("%s's turn",playerO.getName());
				break;
			case XWon:
				commentary = String.format("%s won",playerX.getName());
				break;
			case OWon:
				commentary = String.format("%s won",playerO.getName());
				break;
			case Draw:
				commentary = String.format("It's a draw");
				break;
			default:
				commentary = "";
				break;
		}

		mTextView.setText(commentary);
	}
	
}
