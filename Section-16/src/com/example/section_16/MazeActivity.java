package com.example.section_16;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.view.Menu;
import android.view.View;



public class MazeActivity extends Activity {

	public static MazeCell a[][] = null;
	DrawView drawview = null;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		drawview = new DrawView(this);
		setContentView(drawview);
		
		
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
}
