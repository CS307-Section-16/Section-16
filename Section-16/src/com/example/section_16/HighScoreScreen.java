package com.example.section_16;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class HighScoreScreen extends Activity {

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_high_score_screen);
		this.setScores();
		this.setProgress();
		
		View decorView = getWindow().getDecorView();
		// Hide the status bar.
		int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
		decorView.setSystemUiVisibility(uiOptions);

		ActionBar actionBar = getActionBar();
		actionBar.hide();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.high_score_screen, menu);
		return true;
	}
	
	public void startBack(View view) {
		Intent back = new Intent(this, MainActivity.class);
		startActivity(back);
	}

	public void setProgress(){
		MainActivity.datasource.open();
		Score scores[] = MainActivity.datasource.getScores();
		MainActivity.datasource.close();
		
		/*if(scores[0].score > 0){
			int maxScore = scores[0].score;
			ProgressBar pb1 = (ProgressBar) findViewById(R.id.progressBar1);
			ProgressBar pb2 = (ProgressBar) findViewById(R.id.ProgressBar04);
			ProgressBar pb3 = (ProgressBar) findViewById(R.id.ProgressBar03);
			ProgressBar pb4 = (ProgressBar) findViewById(R.id.ProgressBar02);
			ProgressBar pb5 = (ProgressBar) findViewById(R.id.ProgressBar01);
			pb1.setMax(maxScore);
			pb2.setMax(maxScore);
			pb3.setMax(maxScore);
			pb4.setMax(maxScore);
			pb5.setMax(maxScore);
			pb1.setProgress(scores[0].score);
			pb2.setProgress(scores[1].score);
			pb3.setProgress(scores[2].score);
			pb4.setProgress(scores[3].score);
			pb5.setProgress(scores[4].score);
			
		}*/
	}
	
	public void setScores(){
		MainActivity.datasource.open();
		Score scores[] = MainActivity.datasource.getScores();
		MainActivity.datasource.close();
		
		/*TextView player1 = (TextView) findViewById(R.id.textView1);
		TextView player2 = (TextView) findViewById(R.id.textView2);
		TextView player3 = (TextView) findViewById(R.id.textView3);
		TextView player4 = (TextView) findViewById(R.id.textView4);
		TextView player5 = (TextView) findViewById(R.id.textView5);
		TextView score1 = (TextView) findViewById(R.id.TextView11);
		TextView score2 = (TextView) findViewById(R.id.TextView10);
		TextView score3 = (TextView) findViewById(R.id.TextView09);
		TextView score4 = (TextView) findViewById(R.id.TextView08);
		TextView score5 = (TextView) findViewById(R.id.TextView07); 
		
		player1.setText(scores[0].name);
		player2.setText(scores[1].name);
		player3.setText(scores[2].name);
		player4.setText(scores[3].name);
		player5.setText(scores[4].name);
		score1.setText(String.valueOf(scores[0].score));
		score2.setText(String.valueOf(scores[1].score));
		score3.setText(String.valueOf(scores[2].score));
		score4.setText(String.valueOf(scores[3].score));
		score5.setText(String.valueOf(scores[4].score));*/
		
	}
}
	
