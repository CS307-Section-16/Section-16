package com.example.section_16;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class SettingsScreen extends Activity {

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings_screen);

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
		getMenuInflater().inflate(R.menu.settings_screen, menu);
		return true;
	}

	public void startBack(View view) {
		Intent back = new Intent(this, MainActivity.class);
		startActivity(back);
	}

	public void changeDetail(View view){

		//change details here
		//	if(x...efwefw){
		//	case(low);
		//	case(medium);
		//	case(high);

	}

	public void changeFont(View view){
		//change font here
		//if(x...efwefw){
		//	case(fine);
		//	case(bold);
		//	case(large);
	}


}
