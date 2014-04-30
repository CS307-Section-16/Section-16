package com.example.section_16;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	public static QuestionsDataSource datasource;
	private boolean created = false;
	public static MazeCell a[][] = null;
	
	public static int questionsAnswered = 0;
	public static int questionsAttempted = 0;
	
	@SuppressWarnings("unused")
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
		
		Button continueB = (Button)findViewById(R.id.button2);
		
		datasource = new QuestionsDataSource(this);
		datasource.open();
		
		if(!datasource.exists()){
			datasource.insertAllIntoTable(datasource);
			created = !created;
		}else{
			Log.d("insertion", "Already Created");
		}

		//datasource.addSettings(0, 0);
		
		Set s = datasource.getSettings();
		if(s == null || s.save == 0){//|| s.save == 0){
			continueB.setEnabled(false);
		}else{
			continueB.setEnabled(true);
		}
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
		Intent cont = new Intent(this, ContinueGame.class);
		startActivity(cont);
	}
	
	public void startInitialization(View view) {
		Intent init = new Intent(this, Initialize.class);
		datasource.resetQuestions();
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
	public void onBackPressed(){
		finish();
	}
	public void dummyScores(View view){
		datasource.open();
		Score scores[] = new Score[4];
		for(int i=0;i<scores.length;i++){
			scores[i] = new Score();
		}
		scores[0].name = "Tyler";
		scores[1].name = "Dan";
		scores[2].name = "Max";
		scores[3].name = "Phillip";
		//scores[4].name = "Ashley";
		scores[0].score = 500;
		scores[1].score = 10009;
		scores[2].score = 207;
		scores[3].score = 84600;
		//scores[4].score = 33;
		for(int i=0;i<scores.length;i++){
			datasource.addHighScore(scores[i].name, scores[i].score);
		}
		datasource.close();
	}


}
