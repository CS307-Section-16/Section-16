package com.example.section_16;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.RadioGroup;

public class Initialize extends Activity {

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_initialize);
		
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
		getMenuInflater().inflate(R.menu.initialize, menu);
		return true;
	}
	
	public void startBack(View view) {
		Intent back = new Intent(this, MainActivity.class);
		startActivity(back);
	}
	
	public void initSettings(View view){
		MainActivity.datasource.open();
		RadioGroup type = (RadioGroup) findViewById(R.id.radioGroup1);
		RadioGroup diff = (RadioGroup) findViewById(R.id.radioGroup2);
		int t = type.getCheckedRadioButtonId();
		int d = diff.getCheckedRadioButtonId();
		View radioButton = type.findViewById(t);
		View radioButton1 = diff.findViewById(d);
		int indexType = type.indexOfChild(radioButton);
		int indexDiff = diff.indexOfChild(radioButton1);
		MainActivity.datasource.addSettings(indexType, indexDiff);
		MainActivity.datasource.close();
		
		while( true ) 
		{
			MazeCell.a =	 MazeGen.generateMaze();
			if ( MazeCell.checkBorder(MazeCell.a) )
			{
				break;
			}  
		}
		
		Intent cont = new Intent(this, MazeActivity.class);
		startActivity(cont);
		finish();
	}

}
