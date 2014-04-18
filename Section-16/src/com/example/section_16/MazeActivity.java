package com.example.section_16;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.Menu;
import android.view.View;



public class MazeActivity extends Activity {

	MazeCell a[][] = null;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(new MyView(this));
		View decorView = getWindow().getDecorView();
		// Hide the status bar.
		int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
		decorView.setSystemUiVisibility(uiOptions);
		
		ActionBar actionBar = getActionBar();
		actionBar.hide();
		
		while( true ) 
		{
			a =	 MazeGen.generateMaze();
			if ( checkBorder(a) )
			{
				break;
			}  
		}

		/*int[][] a1 = {{1,2,1,1,1,1,1,4,1},
				 	  {1,0,1,0,1,0,1,0,1},
				 	  {1,0,0,0,1,3,0,0,1},
				 	  {1,0,1,1,1,0,1,1,1},
				 	  {1,0,3,0,0,3,0,1,1},
				 	  {1,1,0,1,1,1,0,3,1},
				 	  {1,0,3,0,0,1,1,0,1},
				 	  {1,0,1,1,0,1,0,0,1},
				 	  {1,1,1,1,1,1,1,1,1},
				 	  };*/

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	private static boolean checkBorder(MazeCell[][] maze)
	{
		for(int i=0; i < maze.length; i++)
		{
			if(!maze[i][0].isWall() || !maze[i][maze.length - 1].isWall() || !maze[0][i].isWall() || !maze[maze.length -1][i].isWall() )
			{
				return false;
			}
		}
		return true;
	}


	public class MyView extends View {
		public MyView(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
		}

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

			/*for(int i = 0; i < a.length; i++){
           	for(int j = 0; j < a[i].length; j++){
           		if(a[i][j].wall == true)
           			paint.setColor(Color.parseColor("#000000"));
           		else if(a[i][j].pathWay == true)
           			paint.setColor(Color.parseColor("#FFFFFF"));
           		else if(a[i][j].isPerson == true)
           			paint.setColor(Color.parseColor("#00FF00"));
           		else if(a[i][j].obstacle == true)
           			paint.setColor(Color.parseColor("#FF0000"));
               	canvas.drawRect(j*(x/a[i].length),i*(y/a.length) ,j*(x/a[i].length)+x/a[i].length ,i*(y/a.length)+y/a.length, paint);

               }
           }*/

			int startX = x/2 - y/2;
			for(int j = 0; j < a[1].length; j++){
				for(int i = 0; i < a.length; i++){
					if(a[i][j].wall == true)
						paint.setColor(Color.parseColor("#000000"));
					else if(a[i][j].player == true)
						paint.setColor(Color.parseColor("#00FF00"));
					else if(a[i][j].obstacle == true)
						paint.setColor(Color.parseColor("#FF0000"));
					else if(a[i][j].end == true)
						paint.setColor(Color.parseColor("#F34AB2"));
					else
						paint.setColor(Color.parseColor("#FFFFFF"));


					//canvas.drawRect(j*(x/a[i].length),i*(y/a.length) ,j*(x/a[i].length)+x/a[i].length ,i*(y/a.length)+y/a.length, paint);
					canvas.drawRect(startX + j*(y/a[i].length),i*(y/a.length) ,startX + j*(y/a[i].length)+y/a[i].length ,i*(y/a.length)+y/a.length, paint);

				}
			}		
		}
	}
}
