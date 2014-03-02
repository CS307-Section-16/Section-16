package com.example.section_16;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class SettingsScreen extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings_screen);
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
