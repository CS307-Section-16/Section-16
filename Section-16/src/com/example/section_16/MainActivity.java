package com.example.section_16;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent db = new Intent(this, SQLiteDB.class);
		startActivity(db);
		setContentView(R.layout.activity_main);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void startContinue(View view) {
		Intent cont = new Intent(this, ContinueGame.class);
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
