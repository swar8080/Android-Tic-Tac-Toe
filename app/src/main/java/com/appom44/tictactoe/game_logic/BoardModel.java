package com.appom44.tictactoe.game_logic;

import android.os.Bundle;

import com.appom44.tictactoe.portable.PassiveMessanger;
import com.appom44.tictactoe.state.BoardModelStatePersistenceManager;
import com.appom44.tictactoe.portable.IBundleable;

public class BoardModel extends PassiveMessanger<GameState> implements IBundleable  {

	private GameState state;
	private BoardSpaceValue[] board;
	private int rows,columns;

	
	private static final GameState STARTING_STATE = GameState.TurnX;
	public static final int MIN_WIN_LEN_REQ = 3;
	
	//initializes the default board
	public BoardModel(int rows, int columns)
	{	
		this.rows = rows;
		this.columns = columns;
		this.state = STARTING_STATE;
		board = new BoardSpaceValue[rows*columns];
		for (int i=0; i<rows*columns; i++){
			board[i] = BoardSpaceValue.BLANK;
		}

	}

	//initializes a given board
	public BoardModel(int rows, int columns, BoardSpaceValue[] boardValues, GameState state)
	{
		this.rows = rows;
		this.columns = columns;
		this.state = state;

		board = new BoardSpaceValue[rows*columns];
		for (int i=0; i<rows*columns; i++){
			board[i] = boardValues[i];
		}
	}
	
	public GameState getState(){
		return this.state;	
	}

	public boolean isGameOver(){
		return this.state == GameState.XWon || 
			   this.state == GameState.OWon ||
			   this.state == GameState.Draw;
	}
	
	public MoveSelectionInstruction tryMove(int space) {
		MoveSelectionInstruction msi;
		boolean stateChanged = false;
		
		switch (state){
		
		case TurnX:
			if (board[space] == BoardSpaceValue.BLANK){
				msi = MoveSelectionInstruction.X;
				board[space] = BoardSpaceValue.X;
				stateChanged = true;
			}
			else {
				msi = MoveSelectionInstruction.INVALID;
			}
			break;
		
		case TurnO:
			if (board[space] == BoardSpaceValue.BLANK){
				msi = MoveSelectionInstruction.O;
				board[space] = BoardSpaceValue.O;
				stateChanged = true;
			}
			else {
				msi = MoveSelectionInstruction.INVALID;
			}
			break;
			
		case Reset:
			msi = MoveSelectionInstruction.CLEAR;
			board[space] = BoardSpaceValue.BLANK;
			stateChanged = true;
			break;
			
		case OWon:
		case XWon:
		case Draw:
			msi = MoveSelectionInstruction.INVALID;
			break;
			
		default:
			msi = MoveSelectionInstruction.FATAL;
			break;
		}
		
		if (stateChanged){
			updateState();
		}
		
		return msi;
	}
	
	private void updateState() {
		int filled;

		//check if the game is already over
		if (this.state != GameState.XWon && this.state != GameState.OWon && this.state != GameState.Draw){

			filled = 0;
			for (int i=0; i<board.length;i++){
				if (board[i] != BoardSpaceValue.BLANK)
					filled++;
			}
			
			//check if enough spaces are filled for there to potentially be a winner
			if (filled >= MIN_WIN_LEN_REQ*2-1 ){
				
				if (isWinner(BoardSpaceValue.X)){
					this.state = GameState.XWon;
				}
				else if (isWinner(BoardSpaceValue.O)){
					this.state = GameState.OWon;
				}
				else if (filled == rows*columns){
					this.state = GameState.Draw;
				}
			}
			//Game is not over, find out whose turn it is
			if (!isGameOver()){
				if (filled%2==0) {
					this.state = GameState.TurnX;
				}
				else {
					this.state = GameState.TurnO;
				}
			}
			
			this.notifyGameStateListeners(this.state);
		}
	}

	private boolean isWinner(BoardSpaceValue playerValue){
		BoardSpaceValue[][] spaces = new BoardSpaceValue[rows][columns];
		int run,i;

		//check for horizontal wins
		for (i=0; i<rows; i++){
			run = 0;
			for (int j=0;j<columns;j++){
				if (board[i*columns+j] == playerValue){
					if (++run == MIN_WIN_LEN_REQ){
						return true;
					}
				}
				else {
					break;
				}
			}
			run = 0;
		}


		//check for vertical wins
		for (i=0; i<rows; i++){
			run = 0;
			for (int j=0;j<rows;j++){
				if (board[i+j*columns] == playerValue){
					if (++run == MIN_WIN_LEN_REQ){
						return true;
					}
				}
				else {
					break;
				}
			}
		}

		run = 0;
		//check for left-diagonal win
		i = 0;
		while (i<rows*columns){
			if (board[i] == playerValue){
				if (++run == MIN_WIN_LEN_REQ){
					return true;
				}
			}
			else {
				break;
			}
			i += columns+1;
		}

		run = 0;
		//check for right-diagonal win
		i = columns-1;
		while (i<rows*columns){
			if (board[i] == playerValue){
				if (++run == MIN_WIN_LEN_REQ){
					return true;
				}
			}
			else {
				break;
			}
			i += columns-1;
		}


		return false;
	}


	public void reset() {
		for (int i=0; i<rows*columns; i++){
			board[i] = BoardSpaceValue.BLANK;
		}

		//let listeners know the board has reset before resetting to initial state
		this.state = GameState.Reset;
		notifyGameStateListeners(this.state);

		//after listeners have reacted to reset, restart the game
		state = STARTING_STATE;
		notifyGameStateListeners(this.state);
	}

	@Override
	public Bundle createBundle() {
		Bundle bundle = new Bundle();

		bundle.putInt(BoardModelStatePersistenceManager.ROWS,rows);
		bundle.putInt(BoardModelStatePersistenceManager.COLUMNS,columns);
		bundle.putSerializable(BoardModelStatePersistenceManager.BOARD_VALUES,board);
		bundle.putSerializable(BoardModelStatePersistenceManager.GAME_STATE,state);

		return bundle;
	}
}
