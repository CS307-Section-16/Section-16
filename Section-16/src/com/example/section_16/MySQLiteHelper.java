package com.example.section_16;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {
	
	  private static final String DATABASE_NAME = "game.db";
	  private static final int DATABASE_VERSION = 1;

	  public static final String TABLE_QUESTIONS = "questions";
	  public static final String TABLE_SETTINGS = "settings";
	  public static final String TABLE_SCORES = "scores";
	  
	  //Question Data Fields
	  public static final String Q_ID = "ID";
	  public static final String QUESTION = "QUESTION";
	  public static final String ANSWER_A = "ANSWER_A";
	  public static final String ANSWER_B = "ANSWER_B";
	  public static final String ANSWER_C = "ANSWER_C";
	  public static final String ANSWER_D = "ANSWER_D";
	  public static final String CORRECT = "CORRECT";
	  public static final String USED = "USED";
	  public static final String DIFFICULTY = "DIFFICULTY";
	  public static final String HINT = "HINT";
	  
	  public static final String QUESTION_CREATE = "create table "
			  + TABLE_QUESTIONS + "(" + Q_ID
			  + " integer primary key autoincrement, " + QUESTION
			  + " text not null, " + ANSWER_A
			  + " text not null, " + ANSWER_B
			  + " text not null, " + ANSWER_C
			  + " text not null, " + ANSWER_D
			  + " text not null, " + CORRECT
			  + " text not null, " + USED
			  + " integer, " + DIFFICULTY
			  + " integer, " + HINT
			  + " text not null);";
	  
	  //Settings Data Fields
	  public static final String S_ID = "ID";
	  public static final String S_TYPE = "TYPE";
	  public static final String S_DIFF = "DIFFICULTY";
	  public static final String S_FONT = "FONT";
	  
	  public static final String SETTINGS_CREATE = "create table "
			  + TABLE_SETTINGS + "(" + S_ID 
			  + " integer primary key autoincrement, " + S_TYPE
			  + " integer, " + S_DIFF
			  + " integer, " + S_FONT
			  + " integer);";
	  
	  //Score Data Fields
	  public static final String HS_ID = "ID";
	  public static final String HS_NAME = "NAME";
	  public static final String HS_SCORE = "SCORE";
	  
	  public static final String HIGH_SCORE_CREATE = "create table "
			  + TABLE_SCORES + "(" + HS_ID 
			  + " integer primary key autoincrement, " + HS_NAME
			  + " text not null, " + HS_SCORE
			  + " integer);";

	  public MySQLiteHelper(Context context) {
	    super(context, DATABASE_NAME, null, DATABASE_VERSION);
	  }

	  @Override
	  public void onCreate(SQLiteDatabase database) {
		  database.execSQL(QUESTION_CREATE);
		  database.execSQL(HIGH_SCORE_CREATE);
		  database.execSQL(SETTINGS_CREATE);
		  Log.d("onCreate", "Database created");
	  }

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	    Log.w(MySQLiteHelper.class.getName(),
	        "Upgrading database from version " + oldVersion + " to "
	            + newVersion + ", which will destroy all old data");
	    db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTIONS);
	    onCreate(db);
	  }
}

class Question{

	long id;
    String question;
    String answer_A;
    String answer_B;
    String answer_C;
    String answer_D;
    String correct;
    int used;
    int difficulty;
    String hint;

    public Question(){}
}

class Score{
	String name;
	int score;
	public Score(){}
}