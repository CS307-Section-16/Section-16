package com.example.section_16;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.graphics.PorterDuff;



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

		
		Button up = (Button)findViewById(R.id.d_button1);	
		Button right = (Button)findViewById(R.id.d_button2);	
		Button down = (Button)findViewById(R.id.d_button3);	
		Button left = (Button)findViewById(R.id.d_button4);	
		View anchor = findViewById(R.id.d_anchor);
		
		RelativeLayout dpadButt = (RelativeLayout)findViewById(R.id.dpad_layout);	
		dpadButt.addView(drawview);
		anchor.bringToFront();
		up.bringToFront();
		right.bringToFront();
		down.bringToFront();
		left.bringToFront();
		
		up.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	
            }
        });
		
		right.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	
            }
        });
		
		down.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	
            }
        });
		
		left.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	
            }
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
