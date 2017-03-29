package com.appom44.tictactoe.portable;

public interface PassiveListener<T> {

	void onMessageReceived(T message);
}
