package com.appom44.tictactoe;

import com.appom44.tictactoe.communication.MoveSelectionInstruction;
import com.appom44.tictactoe.communication.PassiveListener;
import com.appom44.tictactoe.communication.onBoardSpaceClickListener;

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
		try {
			msi = this.mBoardModel.tryMove(indexOfSpaceClicked);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		switch (msi){
		case X:
			boardLayout.setSpaceValue(indexOfSpaceClicked, BoardSpaceValue.X);
			break;
		case O:
			boardLayout.setSpaceValue(indexOfSpaceClicked, BoardSpaceValue.O);
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
