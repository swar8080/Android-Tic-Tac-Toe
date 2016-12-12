package com.appom44.tictactoe;

import com.appom44.tictactoe.communication.onBoardSpaceClickListener;

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
