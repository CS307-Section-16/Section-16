package com.example.section_16;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class EndActivity extends Activity {

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_end);


		View decorView = getWindow().getDecorView();
		// Hide the status bar.
		int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
		decorView.setSystemUiVisibility(uiOptions);

		ActionBar actionBar = getActionBar();
		actionBar.hide();
		
		TextView tv = (TextView)findViewById(R.id.e_textView1);
		//tv.setText("NAME " + s.name + "\n\n SCORE: " + s.score );
		tv.setText("Your score is: " + MazeActivity.reportScore());
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings_screen, menu);
		return true;
	}

	public void startBack(View view) {
		Intent back = new Intent(this, MainActivity.class);
		startActivity(back);
	}
	
	public void startMenu(View view) {
		Intent back = new Intent(this, MainActivity.class);
		startActivity(back);
		finish();
	}
	public void startHighScores(View view) {
		Intent hs = new Intent(this, HighScoreScreen.class);
		startActivity(hs);
		finish();
	}
	public void startNew(View view) {
		Intent init = new Intent(this, Initialize.class);
		startActivity(init);
		finish();
	}
}
