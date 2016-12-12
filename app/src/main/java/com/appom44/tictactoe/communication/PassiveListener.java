package com.appom44.tictactoe.communication;

import java.sql.SQLException;

public interface PassiveListener<T> {

	void onMessageReceived(T message);
}
