package com.appom44.tictactoe.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;

import com.appom44.tictactoe.game_logic.BoardSpaceValue;
import com.appom44.tictactoe.R;


public class BoardSpace extends android.support.v7.widget.AppCompatImageView{

	//state key values
	private static final String VALUE = "value";

	private BoardSpaceValue mValue;
	
	private boolean mTopBorder,mRightBorder,mBottomBorder,mLeftBorder;
	private Paint mBorderPaint;


	public BoardSpace(Context context, AttributeSet attrs) {
		super(context,attrs);
		
	    TypedArray attributes = context.getTheme().obtainStyledAttributes(
	        attrs,
	        R.styleable.BoardValue,
	        0, 0);
	    
		try {
			int boardSpaceValueOrdinal = attributes.getInt(R.styleable.BoardValue_value,BoardSpaceValue.BLANK.ordinal());
			this.setSpaceValue(BoardSpaceValue.values()[boardSpaceValueOrdinal]);
			
			mTopBorder = attributes.getBoolean(R.styleable.BoardValue_topBorder, false);
			mRightBorder = attributes.getBoolean(R.styleable.BoardValue_rightBorder, false);
			mBottomBorder = attributes.getBoolean(R.styleable.BoardValue_bottomBorder, false);
			mLeftBorder = attributes.getBoolean(R.styleable.BoardValue_leftBorder, false);
		}
		finally {
			attributes.recycle();
		}

		init();
	}

	@Override
	protected void onRestoreInstanceState(Parcelable state){
		if (state instanceof Bundle) {
			Bundle bundle = (Bundle) state;
			this.setSpaceValue((BoardSpaceValue)bundle.getSerializable(VALUE));
			state = bundle.getParcelable("super");
		}
		super.onRestoreInstanceState(state);

		//redraw the board
		this.invalidate();
	}

	@Override
	//http://stackoverflow.com/questions/3542333/how-to-prevent-custom-views-from-losing-state-across-screen-orientation-changes/3542895#3542895
	protected Bundle onSaveInstanceState(){
		Bundle bundle = new Bundle();

		bundle.putParcelable("super",super.onSaveInstanceState());
		bundle.putSerializable(VALUE, this.mValue);

		return bundle;
	}

	private void init(){
		
		//initialize objects used by draw once instead of each time draw is called
		mBorderPaint = new Paint();
		mBorderPaint.setColor(Color.BLACK);
		mBorderPaint.setStrokeWidth(10);
	}

	public void setSpaceValue(BoardSpaceValue value){
		this.mValue = value;
		
		switch (mValue){
		case BLANK:
			super.setImageResource(android.R.color.transparent);
			break;
		case X:
			super.setImageResource(R.drawable.x_blue);
			break;
		case O:
			super.setImageResource(R.drawable.o_red);
			break;
		}
		
	}

	public BoardSpaceValue getSpaceValue(){
		return this.mValue;
	}
	
	@Override
	protected void onDraw(Canvas canvas){
		super.onDraw(canvas);
		
		int width = getWidth();
		int height = getHeight();
		
		if (mTopBorder){
			canvas.drawLine(0, 0, width, 0, mBorderPaint);
		}
		if (mBottomBorder){
			canvas.drawLine(0, height, width, height, mBorderPaint);
		}
		if (mRightBorder){
			canvas.drawLine(width, 0, width, height, mBorderPaint);
		}
		if (mLeftBorder){
			canvas.drawLine(0, 0, 0, height, mBorderPaint);
		}
	
	}

}
