package com.example.section_16;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class DrawView extends View {
    Paint paint = new Paint();
    
    public DrawView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		int x = getWidth();
		int y = getHeight();

		Paint paint = new Paint();
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(Color.WHITE);
		canvas.drawPaint(paint);


		int startX = x/2 - y/2;
		for(int j = 0; j < MazeActivity.a[1].length; j++){
			for(int i = 0; i < MazeActivity.a.length; i++){
				if(MazeActivity.a[i][j].wall == true)
					paint.setColor(Color.parseColor("#000000"));
				else if(MazeActivity.a[i][j].player == true)
					paint.setColor(Color.parseColor("#00FF00"));
				else if(MazeActivity.a[i][j].obstacle == true)
					paint.setColor(Color.parseColor("#FF0000"));
				else if(MazeActivity.a[i][j].end == true)
					paint.setColor(Color.parseColor("#F34AB2"));
				else
					paint.setColor(Color.parseColor("#FFFFFF"));


				//canvas.drawRect(j*(x/a[i].length),i*(y/a.length) ,j*(x/a[i].length)+x/a[i].length ,i*(y/a.length)+y/a.length, paint);
				canvas.drawRect(startX + j*(y/MazeActivity.a[i].length),i*(y/MazeActivity.a.length) ,startX + j*(y/MazeActivity.a[i].length)+y/MazeActivity.a[i].length ,i*(y/MazeActivity.a.length)+y/MazeActivity.a.length, paint);

			}
		}		
	}

}