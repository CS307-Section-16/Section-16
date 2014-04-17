package com.example.section_16;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;
/*
class Question{

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
*/
public class SQLiteDB extends Activity {

	LinearLayout Linear;
	static SQLiteDatabase mydb;
	Question q_array[];
	private static String DBNAME = "QUESTIONS.db";    // THIS IS THE SQLITE DATABASE FILE NAME.
	private static String TABLE = "MY_TABLE";// THIS IS THE TABLE NAME
	private static String SCORETABLE = "SCORE_TABLE";

	public static SQLiteDatabase getDatabase(){
		return mydb;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);

		//Linear  = (LinearLayout)findViewById(R.id.linear);

		//dropTable();        // DROPPING THE TABLE.

		//if(!checkDataBase()){
		createTable();
		insertAllIntoTable();
		// }


		//showTableValues();

	}

	// CREATE TABLE IF NOT EXISTS 
	public void createTable(){
		try{
			mydb = openOrCreateDatabase(DBNAME, Context.MODE_PRIVATE,null);
			mydb.execSQL("CREATE TABLE IF  NOT EXISTS "+ TABLE +" (ID INTEGER PRIMARY KEY, QUESTION TEXT, " +
					"ANSWER_A TEXT, ANSWER_B TEXT, ANSWER_C TEXT, ANSWER_D TEXT, CORRECT TEXT, " +
					"USED INTEGER, DIFFICULTY INTEGER, HINT TEXT );");
			mydb.execSQL("CREATE TABLE IF  NOT EXISTS "+ SCORETABLE +" (ID INTEGER PRIMARY KEY," +
					" NAME TEXT, SCORE INTEGER );");
			mydb.close();
		}catch(Exception e){
			//Toast.makeText(getApplicationContext(), "Error in creating table", Toast.LENGTH_LONG);
		}
	}
	// THIS FUNCTION INSERTS DATA TO THE DATABASE
	public void insertQuestionIntoTable(Question q){
		try{
			mydb = openOrCreateDatabase(DBNAME, Context.MODE_PRIVATE,null);
			mydb.execSQL("INSERT INTO " + TABLE + "(QUESTION, ANSWER_A, ANSWER_B, ANSWER_C, ANSWER_D, CORRECT, USED, DIFFICULTY, HINT)" +
					" VALUES('"+q.question+"','"+q.answer_A+"','"+q.answer_B+"','"+q.answer_C+"','"+q.answer_D+"','"+q.correct+"','"+q.used+"','"+q.difficulty+"','"+q.hint+"')");
			mydb.close();
		}catch(Exception e){
			// Toast.makeText(getApplicationContext(), "Error in inserting into table", Toast.LENGTH_LONG);
		}
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
	public Question retrieveQuestion(){

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
	public Score retrieveScore(){

		Score s = new Score();

		try{
			mydb = openOrCreateDatabase(DBNAME, Context.MODE_PRIVATE,null);
			Cursor score_to_retrieve  = mydb.rawQuery("SELECT * FROM "+  SCORETABLE+ " ORDER BY SCORE", null);

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

	public void insertAllIntoTable(){

		q_array = new Question[100];

		/* 
		 * Should we be doing this in a for loop? 
		 */
		q_array[0] = new Question();
		q_array[1] = new Question();
		q_array[2] = new Question();
		q_array[3] = new Question();
		q_array[4] = new Question();
		q_array[5] = new Question();
		q_array[6] = new Question();
		q_array[7] = new Question();
		q_array[8] = new Question();
		q_array[9] = new Question();
		q_array[10] = new Question();
		q_array[11] = new Question();
		q_array[12] = new Question();
		q_array[13] = new Question();
		q_array[14] = new Question();
		q_array[15] = new Question();
		q_array[16] = new Question();
		q_array[17] = new Question();
		q_array[18] = new Question();
		q_array[19] = new Question();
		q_array[20] = new Question();

		
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

		q_array[8].question = "What does GUI stand for?\n";
		q_array[8].answer_A = "Greatest Universe Inside";
		q_array[8].answer_B = "Graphical User Interface";
		q_array[8].answer_C = "Good Until Interface";
		q_array[8].answer_D = "Growing Urban Integration";
		q_array[8].correct = "B";
		q_array[8].used = 0;
		q_array[8].difficulty = 1;
		q_array[8].hint = "It isn't growing or good";

		q_array[9].question = "What is pseudocode?\n";
		q_array[9].answer_A = "Code you write in a group or in a formal business meeting";
		q_array[9].answer_B = "Trick question -- it's not code at all";
		q_array[9].answer_C = "Code written in the programming language called PSE";
		q_array[9].answer_D = "Used by programmers to help map out what a program is supposed to do";
		q_array[9].correct = "D";
		q_array[9].used = 0;
		q_array[9].difficulty = 2;
		q_array[9].hint = "It's used in planning out your code";

		q_array[10].question = "Which is an example of only declaring a number variable\n";
		q_array[10].answer_A = "String var = \"hello\";";
		q_array[10].answer_B = "int a;";
		q_array[10].answer_C = "int number = 4;";
		q_array[10].answer_D = "Integer int = new Integer()";
		q_array[10].correct = "B";
		q_array[10].used = 0;
		q_array[10].difficulty = 3;
		q_array[10].hint = "Remember, declarations aren't initializations";

		q_array[11].question = "What does it mean to \"override\" a method\n";
		q_array[11].answer_A = "It's when you override an inherited method from a super class";
		q_array[11].answer_B = "When you redefine a method with new ";
		q_array[11].answer_C = "To erase the method from an object";
		q_array[11].answer_D = "It's when you hack into someone else's methods and use them as your own";
		q_array[11].correct = "A";
		q_array[11].used = 0;
		q_array[11].difficulty = 8;
		q_array[11].hint = "No hacking required";

		q_array[12].question = "Which data type is the best for numbers\n";
		q_array[12].answer_A = "char";
		q_array[12].answer_B = "String";
		q_array[12].answer_C = "byte";
		q_array[12].answer_D = "int";
		q_array[12].correct = "D";
		q_array[12].used = 0;
		q_array[12].difficulty = 2;
		q_array[12].hint = "char and String are for letters";
		
		q_array[13].question = "An array with that ranges from 0 - 4 has a length of..\n";
		q_array[13].answer_A = "3";
		q_array[13].answer_B = "4";
		q_array[13].answer_C = "5";
		q_array[13].answer_D = "6";
		q_array[13].correct = "C";
		q_array[13].used = 0;
		q_array[13].difficulty = 5;
		q_array[13].hint = "Count from 0";
		
		q_array[14].question = "To terminate a case in a switch statement you must have a..\n";
		q_array[14].answer_A = "Null terminator";
		q_array[14].answer_B = "Semi-colon";
		q_array[14].answer_C = "Break statement";
		q_array[14].answer_D = "Period";
		q_array[14].correct = "C";
		q_array[14].used = 0;
		q_array[14].difficulty = 6;
		q_array[14].hint = "It isn't A or D";
		
		q_array[15].question = "To use predefined java methods you must _____ a library\n";
		q_array[15].answer_A = "Import";
		q_array[15].answer_B = "Buy";
		q_array[15].answer_C = "Include";
		q_array[15].answer_D = "Ask to use";
		q_array[15].correct = "A";
		q_array[15].used = 0;
		q_array[15].difficulty = 4;
		q_array[15].hint = "Java doesn't use #include statements";

		q_array[16].question = "Which statement is true:\n";
		q_array[16].answer_A = "Java is an Object Orientated language";
		q_array[16].answer_B = "Steve Jobs is the father of Java";
		q_array[16].answer_C = "Java was named after coffee";
		q_array[16].answer_D = "Java a low level language";
		q_array[16].correct = "A";
		q_array[16].used = 0;
		q_array[16].difficulty = 3;
		q_array[16].hint = "Steve Jobs didn't create Java";

		q_array[17].question = "Which language would be considered a low-level language?\n";
		q_array[17].answer_A = "Java";
		q_array[17].answer_B = "C";
		q_array[17].answer_C = "No one uses low-level programming since the 80s";
		q_array[17].answer_D = "Javascript";
		q_array[17].correct = "B";
		q_array[17].used = 0;
		q_array[17].difficulty = 2;
		q_array[17].hint = "Low-level programming is still used today";

		q_array[18].question = "When a variable is follwed by ++ it is...\n";
		q_array[18].answer_A = "Twice as +";
		q_array[18].answer_B = "Incremented by one";
		q_array[18].answer_C = "Incremented by two";
		q_array[18].answer_D = "A C++ statement";
		q_array[18].correct = "B";
		q_array[18].used = 0;
		q_array[18].difficulty = 3;
		q_array[18].hint = "It's used in Java too";
		
		q_array[19].question = "Which symbol means \"or\"?\n";
		q_array[19].answer_A = "$";
		q_array[19].answer_B = "||";
		q_array[19].answer_C = "&&";
		q_array[19].answer_D = "&";
		q_array[19].correct = "B";
		q_array[19].used = 0;
		q_array[19].difficulty = 2;
		q_array[19].hint = "& means \"and\"";

		q_array[20].question = "Does Java use garbage collection?\n";
		q_array[20].answer_A = "Yes";
		q_array[20].answer_B = "Only in for loops";
		q_array[20].answer_C = "Yes, but only on garbage days";
		q_array[20].answer_D = "No";
		q_array[20].correct = "A";
		q_array[20].used = 0;
		q_array[20].difficulty = 10;
		q_array[20].hint = "Garbage collection is used by most high-level languages";

		Score s = new Score();
		s.name = "Will";
		s.score = 1337;
		//insertHighScore(s);

		try{
			mydb = openOrCreateDatabase(DBNAME, Context.MODE_PRIVATE,null);
			int i = 0;

			while(q_array[i] != null){
				mydb.execSQL("INSERT INTO " + TABLE + "(QUESTION, ANSWER_A, ANSWER_B, ANSWER_C, ANSWER_D, CORRECT, USED, DIFFICULTY, HINT)" +
						" VALUES('"+q_array[i].question+"','"+q_array[i].answer_A+"','"+q_array[i].answer_B+"','"+q_array[i].answer_C+"','"+q_array[i].answer_D+"','"+q_array[i].correct+"','"+q_array[i].used+"','"+q_array[i].difficulty+"','"+q_array[i].hint+"')");
				i++;
			}
			mydb.execSQL("INSERT INTO " + SCORETABLE + "(NAME, SCORE)" +
					" VALUES('"+s.name+"','"+s.score+"')");
			mydb.close();
		}catch(Exception e){
			Toast.makeText(getApplicationContext(), "Error in inserting into table", Toast.LENGTH_LONG);
		}
	}
	private boolean checkDataBase() {
		boolean ret;
		SQLiteDatabase checkDB = null;
		mydb = openOrCreateDatabase(DBNAME, Context.MODE_PRIVATE,null);
		try {
			Cursor cur = mydb.rawQuery("SELECT COUNT(*) FROM " + TABLE,null);
			cur.moveToFirst();
			if (cur.getInt (0) == 0) { //table is empty
				mydb.close();
				return false;
			}
		}catch (Exception e){
			mydb.close();
			return false;
		}
		mydb.close();
		return true;
	}
	// THIS FUNCTION SHOWS DATA FROM THE DATABASE 

	// THIS FUNCTION UPDATES THE DATABASE ACCORDING TO THE CONDITION 

	// THIS FUNCTION DELETES VALUES FROM THE DATABASE ACCORDING TO THE CONDITION
	public void deleteValues(int id){
		try{
			mydb = openOrCreateDatabase(DBNAME, Context.MODE_PRIVATE,null);
			mydb.execSQL("DELETE FROM " + TABLE + " WHERE ID = "+id +" AND WHERE USED = 0");
			mydb.close();
		}catch(Exception e){
			Toast.makeText(getApplicationContext(), "Error encountered while deleting.", Toast.LENGTH_LONG);
		}
	}
	// THIS FUNTION DROPS A TABLE 
	public void dropTable(){
		try{
			mydb = openOrCreateDatabase(DBNAME, Context.MODE_PRIVATE,null);
			mydb.execSQL("DROP TABLE " + TABLE);
			mydb.close();
		}catch(Exception e){
			Toast.makeText(getApplicationContext(), "Error encountered while dropping.", Toast.LENGTH_LONG);
		}
	}
}
