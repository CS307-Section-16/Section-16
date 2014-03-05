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
		
		
		createTable();
		insertAllIntoTable();
		
		
		
		
		//Intent init = new Intent(this, SQLiteExample.class);
		//startActivity(init);	 
		
		
		
		//SQLiteExample sl = new SQLiteExample();
		//sl.createTable();
		
		
		//sl.insertAllIntoTable();
		
		q = retrieveQuestion();
		
		TextView tv = (TextView)findViewById(R.id.questionBox);
		tv.setText(q.question + "\n\nA: " + q.answer_A + "\n\nB: " + q.answer_B+ "\n\nC: " + q.answer_C+ "\n\nD: " + q.answer_D);
	}
	
	public void createTable(){
        try{
            mydb = openOrCreateDatabase(DBNAME, Context.MODE_PRIVATE,null);
            mydb.execSQL("CREATE TABLE IF  NOT EXISTS "+ TABLE +" (ID INTEGER PRIMARY KEY, QUESTION TEXT, ANSWER_A TEXT, ANSWER_B TEXT, ANSWER_C TEXT, ANSWER_D TEXT, CORRECT TEXT, USED INTEGER, DIFFICULTY INTEGER, HINT TEXT );");
            mydb.close();
        }catch(Exception e){
            //Toast.makeText(getApplicationContext(), "Error in creating table", Toast.LENGTH_LONG);
        }
    }public void insertAllIntoTable(){

        q_array = new Question[100];

        q_array[0] = new Question();
        q_array[1] = new Question();
        q_array[2] = new Question();
        q_array[3] = new Question();
        q_array[4] = new Question();
        q_array[5] = new Question();
        q_array[6] = new Question();
        q_array[7] = new Question();

        q_array[0].question = "Why would you use a private constructor?";
        q_array[0].answer_A = "If you want to disallow all instantiation of that class from outside that class";
        q_array[0].answer_B = "If you want only child objects to inherit values of the class";
        q_array[0].answer_C = "If you want to allow access from all outside classes";
        q_array[0].answer_D = "Because public constructors are too mainstream";
        q_array[0].correct = "A";
        q_array[0].used = 0;
        q_array[0].difficulty = 1;
        q_array[0].hint = "Not D";

        q_array[1].question = "..........\n" +
                "if(a == b){\n" +
                "\tif(c != d){\n" +
                "\t\te = f;\n" +
                "\tif(e == f){\n" +
                "\t\tSystem.out.println(\"E = F!!!!!\");\n" +
                "\t}\n" +
                "}\n" +
                ".......... \n" +
                "What is wrong with the above code?";
        q_array[1].answer_A = "There is no closing bracket for the first if-statement (line 1)";
        q_array[1].answer_B = "There is no closing bracket for the second nested if-statement (line 4)";
        q_array[1].answer_C = "There is no closing bracket for the first nested if-statement (line 2)";
        q_array[1].answer_D = "Nothing. The code is correct.";
        q_array[1].correct = "C";
        q_array[1].used = 0;
        q_array[1].difficulty = 1;
        q_array[1].hint = "Not A";


        q_array[2].question = "Which of these is not a primitive data type in Java?";
        q_array[2].answer_A = "int";
        q_array[2].answer_B = "String";
        q_array[2].answer_C = "byte";
        q_array[2].answer_D = "char";
        q_array[2].correct = "B";
        q_array[2].used = 0;
        q_array[2].difficulty = 1;
        q_array[2].hint = "Not A";

        q_array[3].question = " ..........\n" +
                "int a[10] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9];\n" +
                "for(int i = 10; i > 0; i--){\n" +
                "\tSystem.out.print(a[i] + \" \");\n" +
                "}\n" +
                "..........\n" +
                "What is the output of the above code?";
        q_array[3].answer_A = "0 1 2 3 4 5 6 7 8 9";
        q_array[3].answer_B = "9 8 7 6 5 4 3 2 1 0";
        q_array[3].answer_C = "10 9 8 7 6 5 4 3 2 1";
        q_array[3].answer_D = "The program crashes.";
        q_array[3].correct = "D";
        q_array[3].used = 0;
        q_array[3].difficulty = 5;
        q_array[3].hint = "Not C";

        q_array[4].question = "..........\n" +
                "int x, y, z;\n" +
                "y = 1;\n" +
                "z = 5;\n" +
                "x = 0 - (++y) + z++;\n" +
                "..........\n" +
                "What are the values of x, y, and z after this code executes?";
        q_array[4].answer_A = "x = 4, y = 2, z = 6";
        q_array[4].answer_B = "x = 4, y = 1, z = 5";
        q_array[4].answer_C = "x = 3, y = 2, z = 6";
        q_array[4].answer_D = "x = -7, y = 1, z = 5";
        q_array[4].correct = "C";
        q_array[4].used = 0;
        q_array[4].difficulty = 7;
        q_array[4].hint = "Not D";

        q_array[5].question = "..........\n" +
                "int a = 1;\n" +
                "long b = 2;\n" +
                "float c = 3.0f;\n" +
                "double d = 4.0;\n" +
                "..........\n" +
                "Which of the following commands is illegal?";
        q_array[5].answer_A = "b = a";
        q_array[5].answer_B = "c = d";
        q_array[5].answer_C = "d = a";
        q_array[5].answer_D = "c = b";
        q_array[5].correct = "B";
        q_array[5].used = 0;
        q_array[5].difficulty = 6;
        q_array[5].hint = "Not A";

        q_array[6].question = "Which of the following is true about an abstract method inherited into a class Apple?";
        q_array[6].answer_A = "It must be defined in Apple before Apple can be instantiated";
        q_array[6].answer_B = "It always forces Apple to become abstract.";
        q_array[6].answer_C = "Both a and b";
        q_array[6].answer_D = "Neither a nor b";
        q_array[6].correct = "A";
        q_array[6].used = 0;
        q_array[6].difficulty = 9;
        q_array[6].hint = "Not C";

        q_array[7].question = "Suppose the class Undergraduate extends the class Student which extends the class Person.\n" +
                "Given the following variable declaration:\n" +
                "..........\n" +
                "Person p = new Person();\n" +
                "Student s = new Student();\n" +
                "Undergraduate ug = new Undergraduate();\n" +
                "..........\n" +
                "Which of the following assignments are legal?\n" +
                "I. p = ug;\n" +
                "II. p = new Undergraduate();\n" +
                "III. ug = p;\n" +
                "IV. ug = p;\n" +
                "V. s = new Person();";
        q_array[7].answer_A = "III and IV";
        q_array[7].answer_B = "I and IV";
        q_array[7].answer_C = "I and II";
        q_array[7].answer_D = "II, III, and V";
        q_array[7].correct = "C";
        q_array[7].used = 0;
        q_array[7].difficulty = 8;
        q_array[7].hint = "Not B";

        try{
            mydb = openOrCreateDatabase(DBNAME, Context.MODE_PRIVATE,null);
            int i = 0;

            while(q_array[i] != null){
                 mydb.execSQL("INSERT INTO " + TABLE + "(QUESTION, ANSWER_A, ANSWER_B, ANSWER_C, ANSWER_D, CORRECT, USED, DIFFICULTY, HINT)" +
                            " VALUES('"+q_array[i].question+"','"+q_array[i].answer_A+"','"+q_array[i].answer_B+"','"+q_array[i].answer_C+"','"+q_array[i].answer_D+"','"+q_array[i].correct+"','"+q_array[i].used+"','"+q_array[i].difficulty+"','"+q_array[i].hint+"')");
                 i++;
            }
            mydb.close();
        }catch(Exception e){
            Toast.makeText(getApplicationContext(), "Error in inserting into table", Toast.LENGTH_LONG);
        }
    }
    public Question retrieveQuestion(){

        Random rand = new Random();
        Question q = new Question();

        try{
            mydb = openOrCreateDatabase(DBNAME, Context.MODE_PRIVATE,null);
            Cursor question_to_retrieve  = mydb.rawQuery("SELECT * FROM "+  TABLE+ " WHERE USED = 0 ORDER BY RANDOM()", null);

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
