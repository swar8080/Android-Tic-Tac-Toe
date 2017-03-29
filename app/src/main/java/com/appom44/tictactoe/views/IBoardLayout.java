package com.appom44.tictactoe.views;

import com.appom44.tictactoe.game_logic.BoardSpaceValue;

public interface IBoardLayout {
	
	void registerOnBoardSpaceClickListener(onBoardSpaceClickListener listener);
	
	void setSpaceValue(int index, BoardSpaceValue value);
	BoardSpaceValue getSpaceValue(int index);
	
	//rowCount() and columnCount() should be used to ensure
	//A BoardSpaceValue array of the correct dimensions are sent to setBoard
	void setBoard(BoardSpaceValue[] boardValues) throws Exception;
	int rowCount();
	int columnCount();
	
	void resetBoard();
}
