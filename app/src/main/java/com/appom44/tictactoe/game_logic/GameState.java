package com.appom44.tictactoe.game_logic;

public enum GameState {
	TurnX(0),
	TurnO(1),
	XWon(2),
	OWon(3),
	Draw(4),
	Reset(5);

	public final int id;

	GameState(int id){
		this.id = id;
	}

}
