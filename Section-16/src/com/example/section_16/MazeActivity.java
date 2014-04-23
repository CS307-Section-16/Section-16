package com.example.section_16;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;



public class MazeActivity extends Activity {

	DrawView drawview = null;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		drawview = new DrawView(this);
		setContentView(R.layout.activity_maze);


		View decorView = getWindow().getDecorView();
		// Hide the status bar.
		int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
		decorView.setSystemUiVisibility(uiOptions);

		ActionBar actionBar = getActionBar();
		actionBar.hide();

		
		Button b = new Button(this);
		View anchor = new View(this);
		
		RelativeLayout dpadButt = (RelativeLayout)findViewById(R.id.dpad_layout);	
		dpadButt.addView(drawview);
		dpadButt.addView(b);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
