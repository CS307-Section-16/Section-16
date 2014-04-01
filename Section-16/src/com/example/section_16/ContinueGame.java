package com.example.section_16;

import java.util.Random;

import com.example.section_16.R;

import android.os.Bundle;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_continue_game);
		
		
		q = retrieveQuestion();
		
		TextView tv = (TextView)findViewById(R.id.questionBox);
		tv.setText(q.question + "\n\nA: " + q.answer_A + "\n\nB: " + q.answer_B+ "\n\nC: " + q.answer_C+ "\n\nD: " + q.answer_D);
	}
	
    public Question retrieveQuestion(){
    	
    	mydb = SQLiteDB.getDatabase();
		
        Random rand = new Random();
        Question q = new Question();

        try{
            mydb = openOrCreateDatabase(DBNAME, Context.MODE_PRIVATE,null);
            Cursor question_to_retrieve  = mydb.rawQuery("SELECT * FROM "+  TABLE+  /*WHERE USED = 0*/  " ORDER BY RANDOM()", null);

            if(question_to_retrieve.moveToFirst()){
                    String ID = question_to_retrieve .getString(0);
                    String QUESTION= question_to_retrieve .getString(1);
                    String ANSWER_A= question_to_retrieve .getString(2);
                    String ANSWER_B= question_to_retrieve .getString(3);
                    String ANSWER_C= question_to_retrieve .getString(4);
                    String ANSWER_D= question_to_retrieve .getString(5);
                    String CORRECT= question_to_retrieve .getString(6);
                    String DIFFICULTY = question_to_retrieve .getString(8);
                    String HINT = question_to_retrieve .getString(9);

                    q.question = QUESTION;
                    q.answer_A = ANSWER_A;
                    q.answer_B = ANSWER_B;
                    q.answer_C = ANSWER_C;
                    q.answer_D = ANSWER_D;
                    q.correct = CORRECT;
                    q.used = 1;
                    q.difficulty = Integer.parseInt(DIFFICULTY);
                    q.hint = HINT;

                    mydb.execSQL("UPDATE " + TABLE + " SET USED = 1 WHERE ID = " + ID);

            }
            mydb.close();
        }catch(Exception e){
            Toast.makeText(getApplicationContext(), "Error encountered.", Toast.LENGTH_LONG);
        }

        return q;
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

}
