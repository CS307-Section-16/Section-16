package com.example.section_16;

import java.util.Random;

import com.example.section_16.R;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class ContinueGame extends Activity {
	
	SQLiteDatabase mydb;
	Question q_array[];
	Question q;
    private static String DBNAME = "QUESTIONS.db";    // THIS IS THE SQLITE DATABASE FILE NAME.
    private static String TABLE = "MY_TABLE";       // THIS IS THE TABLE NAME
    private static String SCORETABLE = "SCORE_TABLE";
    
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_continue_game);
		
		View decorView = getWindow().getDecorView();
		// Hide the status bar.
		int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
		decorView.setSystemUiVisibility(uiOptions);
		
		ActionBar actionBar = getActionBar();
		actionBar.hide();
		
		TextView tv = (TextView)findViewById(R.id.questionBox);
		//tv.setText("NAME " + s.name + "\n\n SCORE: " + s.score );
		tv.setText(q.question + "\n\nA: " + q.answer_A + "\n\nB: " + q.answer_B+ "\n\nC: " + q.answer_C+ "\n\nD: " + q.answer_D);
	}
	 public void insertHighScore(Score s){
	    	try{
	            mydb = openOrCreateDatabase(DBNAME, Context.MODE_PRIVATE,null);
	            mydb.execSQL("INSERT INTO " + SCORETABLE + "(NAME, SCORE)" +
	                    " VALUES('"+s.name+"','"+s.score+"')");
	            mydb.close();
	        }catch(Exception e){
	           // Toast.makeText(getApplicationContext(), "Error in inserting into table", Toast.LENGTH_LONG);
	        }
	    }
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.continue_game, menu);
		return true;
	}
	
	@SuppressWarnings("deprecation")
	public void confirmAnswer(View view){
		RadioGroup rg= (RadioGroup) findViewById(R.id.radiogroup);
		int id = rg.getCheckedRadioButtonId();
		View radioButton = rg.findViewById(id);
		int index = rg.indexOfChild(radioButton);
		
		int ans=4;
		
		if(q.correct.equals("A")){
			ans = 0;
		}else if(q.correct.equals("B")){
			ans = 1;
		}else if(q.correct.equals("C")){
			ans = 2;
		}else if(q.correct.equals("D")){
			ans = 3;
		}
		String resp=null;
		if(ans == index){
			resp = "Correct!!!";
		}else{
			resp = "You suck...";
		}
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setTitle("Response");
		alertDialog.setMessage(resp);
		alertDialog.setButton("Ok", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
		// here you can add functions
		}
		});
		alertDialog.show();
    }

	public Score retrieveScore(){

	        Score s = new Score();

	        try{
	            mydb = openOrCreateDatabase(DBNAME, Context.MODE_PRIVATE,null);
	            Cursor score_to_retrieve  = mydb.rawQuery("SELECT * FROM "+  SCORETABLE, null);

	            if(score_to_retrieve.moveToFirst()){
	                    String ID = score_to_retrieve .getString(0);
	                    String NAME= score_to_retrieve .getString(1);
	                    String SCORE= score_to_retrieve .getString(2);
	                    
	                    s.name = NAME;
	                    s.score = Integer.parseInt(SCORE);
	            }
	            mydb.close();
	        }catch(Exception e){
	            Toast.makeText(getApplicationContext(), "Error encountered.", Toast.LENGTH_LONG);
	        }

	        return s;
	    }
}
