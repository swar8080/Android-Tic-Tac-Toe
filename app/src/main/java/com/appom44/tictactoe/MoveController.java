package com.appom44.tictactoe;

import com.appom44.tictactoe.game_logic.BoardModel;
import com.appom44.tictactoe.game_logic.BoardSpaceValue;
import com.appom44.tictactoe.game_logic.GameState;
import com.appom44.tictactoe.game_logic.MoveSelectionInstruction;
import com.appom44.tictactoe.portable.PassiveListener;
import com.appom44.tictactoe.views.IBoardLayout;
import com.appom44.tictactoe.views.onBoardSpaceClickListener;

public class MoveController implements onBoardSpaceClickListener, PassiveListener<GameState> {

	private IBoardLayout mBoardLayout;
	private BoardModel mBoardModel;

	public MoveController(IBoardLayout boardLayout, BoardModel boardModel)
	{
		mBoardLayout = boardLayout;
		this.mBoardModel = boardModel;
		
		//listen for clicks on the board
		boardLayout.registerOnBoardSpaceClickListener(this);
	}


	@Override
	public void onBoardSpaceClick(IBoardLayout boardLayout, int indexOfSpaceClicked) {
		MoveSelectionInstruction msi = null;
		msi = this.mBoardModel.tryMove(indexOfSpaceClicked);

		switch (msi){
			case X:
				boardLayout.setSpaceValue(indexOfSpaceClicked, BoardSpaceValue.X);
				break;
			case O:
				boardLayout.setSpaceValue(indexOfSpaceClicked, BoardSpaceValue.O);
				break;
			case CLEAR:
				boardLayout.setSpaceValue(indexOfSpaceClicked, BoardSpaceValue.BLANK);
				break;
			case INVALID: //square already occupied, do nothing
				break;
			case FATAL:
				//todo handle fatal move selections
				break;
		}
	}

	@Override
	public void onMessageReceived(GameState state) {
		if (state == GameState.Reset){
			this.mBoardLayout.resetBoard();
		}	
	}

}
