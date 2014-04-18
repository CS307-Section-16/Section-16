package com.example.section_16;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	public QuestionsDataSource datasource;
	private boolean created = false;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		View decorView = getWindow().getDecorView();
		// Hide the status bar.
		int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
		decorView.setSystemUiVisibility(uiOptions);
		
		ActionBar actionBar = getActionBar();
		actionBar.hide();
		
		
		datasource = new QuestionsDataSource(this);
		datasource.open();
		
		if(!datasource.exists()){
			datasource.insertAllIntoTable(datasource);
			created = !created;
		}else{
			Log.d("insertion", "Already Created");
		}
	Question q = datasource.retrieveQuestion();
	Log.d("Q_ID",String.valueOf(q.id));
		
	}
	  protected void onResume() {
	    datasource.open();
	    super.onResume();
	}

	  @Override
	  protected void onPause() {
	    datasource.close();
	    super.onPause();
	  }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void startContinue(View view) {
		Intent cont = new Intent(this, MazeActivity.class);
		startActivity(cont);
	}
	
	public void startInitialization(View view) {
		Intent init = new Intent(this, Initialize.class);
		startActivity(init);	    
	}
	
	public void startHighScores(View view) {
		Intent hscore = new Intent(this, HighScoreScreen.class);
		startActivity(hscore);	   
	}
	
	public void startSettings(View view) {
		Intent sett = new Intent(this, SettingsScreen.class);
		startActivity(sett);	 
	}

}
