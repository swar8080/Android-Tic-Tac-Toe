package com.appom44.tictactoe;

import java.util.ArrayList;
import java.util.Iterator;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.appom44.tictactoe.communication.onBoardSpaceClickListener;

public class BoardLayout extends LinearLayout implements IBoardLayout{
	private int rows;
	private int columns;
	private ChildSpacesAccessor childSpaces;
	private ArrayList<onBoardSpaceClickListener> boardSpaceClickSubscribers;
	
	
	public BoardLayout(Context context, AttributeSet attrs) throws Exception {
		super(context, attrs);

		//won't be able to find BoardSpaces in edit mode which will throw an exception
		if (this.isInEditMode()){
			return;
		}
	
		boardSpaceClickSubscribers = new ArrayList<onBoardSpaceClickListener>();
		
	    TypedArray attributes = context.getTheme().obtainStyledAttributes(
		        attrs,
		        R.styleable.BoardLayout,
		        0, 0);
		    
			try {
				rows = attributes.getInt(R.styleable.BoardLayout_rows, 0);
				columns = attributes.getInt(R.styleable.BoardLayout_columns, 0);
			}
			finally {
				attributes.recycle();
			}
	}
	
	@Override
	protected void onFinishInflate(){
		childSpaces = new ChildSpacesAccessor();
		
		Iterator<BoardSpace> iterator = childSpaces.iterator();
		while (iterator.hasNext()){
			registerBoardSpaceListeners(iterator.next());
		}
		
		//todo add some validation for rows/column count
	}
	
	@Override
	public void registerOnBoardSpaceClickListener(onBoardSpaceClickListener listener) {		
		//register the listener
		this.boardSpaceClickSubscribers.add(listener);
	}
	
	private void registerBoardSpaceListeners(BoardSpace boardSpace){
		boardSpace.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
			BoardSpace touched = (BoardSpace)v;
			int spaceIndex = childSpaces.indexOf(touched);

			//notify subscribers that a board space was touched in this IBoardLayout
			for (onBoardSpaceClickListener listener : boardSpaceClickSubscribers ){
				listener.onBoardSpaceClick(BoardLayout.this,spaceIndex);
			}
			}
			
		});
	}

	@Override
	public void setSpaceValue(int index, BoardSpaceValue value) {
		childSpaces.spaceAt(index).setSpaceValue(value);
	}

	@Override
	public BoardSpaceValue getSpaceValue(int index) {
		return childSpaces.spaceAt(index).getSpaceValue();
	}

	@Override
	public void setBoard(BoardSpaceValue[] boardSpaceValues) throws Exception {
		if (boardSpaceValues.length != rows*columns){
			throw new Exception("Invalid number of board values");
		}
		
		Iterator<BoardSpace> iterator = childSpaces.iterator();
		int i = 0;
		while (iterator.hasNext()){
			iterator.next().setSpaceValue(boardSpaceValues[i++]);
		}
	}
	
	@Override
	public int rowCount() {
		return rows;
	}

	@Override
	public int columnCount() {
		return columns;
	}
	
	
	@Override
	public void resetBoard() {
		Iterator<BoardSpace> iterator = childSpaces.iterator();
		while (iterator.hasNext()){
			iterator.next().setSpaceValue(BoardSpaceValue.BLANK);
		}
	}

	private class ChildSpacesAccessor {
		private ArrayList<BoardSpace> boardSpaces = null;
		
		public BoardSpace spaceAt(int index){
			return getBoardSpaces().get(index);
		}
		
		public int spaceCount(){
			return getBoardSpaces().size();
		}
		
		public int indexOf(BoardSpace space){
			
			return getBoardSpaces().indexOf(space);
		}
		
		public Iterator<BoardSpace> iterator(){
			return getBoardSpaces().iterator();
		}
		
		private ArrayList<BoardSpace> getBoardSpaces(){
			if (boardSpaces == null){
				boardSpaces = new ArrayList<BoardSpace>();
				for (int i=0; i<getChildCount(); i++){
					ViewGroup boardSpaceGrouping = (ViewGroup)getChildAt(i);
					int j=0;
					while (j<boardSpaceGrouping.getChildCount()){
						boardSpaces.add((BoardSpace)boardSpaceGrouping.getChildAt(j));
						j++;
					}
				}
			}
			return boardSpaces;
		}
	}
	
}
