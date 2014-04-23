package com.example.section_16;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class QuestionsDataSource {

  // Database fields
  private Question q_array[];
  private SQLiteDatabase database;
  private MySQLiteHelper dbHelper;
  private  String[] allColumns = { MySQLiteHelper.Q_ID,
      MySQLiteHelper.QUESTION , MySQLiteHelper.ANSWER_A,MySQLiteHelper.ANSWER_B,
      MySQLiteHelper.ANSWER_C,MySQLiteHelper.ANSWER_D,MySQLiteHelper.CORRECT,
      MySQLiteHelper.USED,MySQLiteHelper.TYPE,MySQLiteHelper.HINT}; 
  
  private String[] scoreColumns = {MySQLiteHelper.HS_ID, MySQLiteHelper.HS_NAME, MySQLiteHelper.HS_SCORE};

  public QuestionsDataSource(Context context) {
    dbHelper = new MySQLiteHelper(context);
  }

  public void open() throws SQLException {
    database = dbHelper.getWritableDatabase();
  }

  public void close() {
    dbHelper.close();
  }

  
  public Question createQuestion(Question question){
    ContentValues values = new ContentValues();
    values.put(MySQLiteHelper.QUESTION, question.question);
    values.put(MySQLiteHelper.ANSWER_A, question.answer_A);
    values.put(MySQLiteHelper.ANSWER_B, question.answer_B);
    values.put(MySQLiteHelper.ANSWER_C, question.answer_C);
    values.put(MySQLiteHelper.ANSWER_D, question.answer_D);
    values.put(MySQLiteHelper.CORRECT, question.correct);
    values.put(MySQLiteHelper.USED, question.used);
    values.put(MySQLiteHelper.TYPE, question.type);
    values.put(MySQLiteHelper.HINT, question.hint);
    long insertId = database.insert(MySQLiteHelper.TABLE_QUESTIONS, null,
        values);
    Log.d("insertId", String.valueOf(insertId));
    Cursor cursor = database.query(MySQLiteHelper.TABLE_QUESTIONS,
        allColumns, MySQLiteHelper.Q_ID + " = " + insertId, null,
        null, null, null);
    //Cursor cursor = database.rawQuery("SELECT * FROM " + MySQLiteHelper.TABLE_QUESTIONS + " ", null);
    cursor.moveToFirst();
    Question newQuestion = cursorToQuestion(cursor);
    cursor.close();
    Log.d("createQuestion", newQuestion.question);
    Log.d("createQuestion", newQuestion.type);
    return newQuestion;
  }

  public void deleteQuestion(Question q) {
    long id = q.id;
    System.out.println("Comment deleted with id: " + id);
    database.delete(MySQLiteHelper.TABLE_QUESTIONS, MySQLiteHelper.Q_ID
        + " = " + id, null);
  }

  public Question retrieveQuestion(){
	  String type = "";
	  int t = 0;
	  Cursor sc = database.rawQuery("SELECT * FROM " + MySQLiteHelper.TABLE_SETTINGS, null);
	  	sc.moveToFirst();
	  	t = sc.getInt(1);
	  	if(t==0){
	  		type = "AND TYPE = 'JAVA'";
	  	}else if(t == 1){
	  		type = "AND TYPE = 'C'";
	  	}else if(t == 2){
	  		type = "AND TYPE = 'CONCEPT'";
	  	}
      Cursor cursor  = database.rawQuery("SELECT * FROM "+  MySQLiteHelper.TABLE_QUESTIONS + 
    		  " WHERE (USED = 0) " + type + " ORDER BY RANDOM()", null);
      cursor.moveToFirst();
      Question q = cursorToQuestion(cursor);
      //database.execSQL("UPDATE " + MySQLiteHelper.TABLE_QUESTIONS + " SET USED = 1 WHERE ID = " + q.id);            
      return q;       
 }
			
  public List<Question> getAllQuestions() {
    List<Question> questions = new ArrayList<Question>();

    Cursor cursor = database.query(MySQLiteHelper.TABLE_QUESTIONS,
        allColumns, null, null, null, null, null);
    cursor.moveToFirst();
    while (!cursor.isAfterLast()) {
      Question question = cursorToQuestion(cursor);
      questions.add(question);
      cursor.moveToNext();
    }
    // make sure to close the cursor
    cursor.close();
    return questions;
  }
  
  
  
  public void addSettings(int type, int diff){
	  database.execSQL("drop table if exists " + MySQLiteHelper.TABLE_SETTINGS);
	  database.execSQL(MySQLiteHelper.SETTINGS_CREATE);
	  ContentValues v = new ContentValues();
	  v.put(MySQLiteHelper.S_TYPE, type);
	  v.put(MySQLiteHelper.S_DIFF, diff);
	  long insertId = database.insert(MySQLiteHelper.TABLE_SETTINGS, null, v);
	  Log.d("addSettings", "Settings ID = "+String.valueOf(insertId));
  }
  
  public void addHighScore(String name, int score){
	  ContentValues v = new ContentValues();
	  v.put(MySQLiteHelper.HS_NAME, name);
	  v.put(MySQLiteHelper.HS_SCORE, score);
	  long insertId = database.insert(MySQLiteHelper.TABLE_SCORES, null, v);
	  Log.d("addScore", "Score ID = "+String.valueOf(insertId));
  }
  
  public void clearAllScores(){
	  database.execSQL("drop table if exists " + MySQLiteHelper.TABLE_SCORES);
	  database.execSQL(MySQLiteHelper.HIGH_SCORE_CREATE);
  }
  
  
  public Score[] getScores(){
	    Score scores[] = new Score[5];
	    Cursor cursor = database.query(MySQLiteHelper.TABLE_SCORES,
	        scoreColumns, null, null, null, null, "SCORE DESC");
	    int test = 0;
    	int i=0;
	    if(cursor.moveToFirst()){
	    test = cursor.getCount();
	    	while (!cursor.isAfterLast() && test>0 && i<5) {
	    		Score score = cursorToScore(cursor);
	   			scores[i] = score;
	   			cursor.moveToNext();
	   			i++;
	   			test--;
	   		}
	    }
	    	while(test <= 0 && i < 5){
	    		Score score = new Score();
	    		score.name = "-------";
	    		score.score = 0;
	    		scores[i] = score;
	    		i++;
	    	}
	    // make sure to close the cursor
	    cursor.close();
	    return scores;
}
  
  private Question cursorToQuestion(Cursor cursor) {
	    Question q = new Question();
	    q.id = cursor.getLong(0);
	    q.question = cursor.getString(1);
	    q.answer_A = cursor.getString(2);
	    q.answer_B = cursor.getString(3);
	    q.answer_C = cursor.getString(4);
	    q.answer_D = cursor.getString(5);
	    q.correct = cursor.getString(6);
	    q.used = cursor.getInt(7);
	    q.type = cursor.getString(8);
	    q.hint = cursor.getString(9);
	    return q;
}
  
  private Score cursorToScore(Cursor cursor) {
	  //cursor.moveToFirst();
	    Score s = new Score();
	    s.id = cursor.getLong(0);
	    s.name = cursor.getString(1);
	    s.score = cursor.getInt(2);
	    return s;
}
  

public void insertAllIntoTable(QuestionsDataSource ds){
	int numQuestions = 21;
    q_array = new Question[numQuestions];
    
    final String java = "JAVA";
    final String c = "C";
    final String conc = "CONCEPT";

    for(int i=0;i<q_array.length;i++){
    	q_array[i] = new Question();
    }

    q_array[0].question = "Why would you use a private constructor?";
    q_array[0].answer_A = "If you want to disallow all instantiation of that class from outside that class";
    q_array[0].answer_B = "If you want only child objects to inherit values of the class";
    q_array[0].answer_C = "If you want to allow access from all outside classes";
    q_array[0].answer_D = "Because public constructors are too mainstream";
    q_array[0].correct = "A";
    q_array[0].used = 0;
    q_array[0].type = "JAVA";
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
    q_array[1].type = java;
    q_array[1].hint = "Not A";


    q_array[2].question = "Which of these is not a primitive data type in Java?";
    q_array[2].answer_A = "int";
    q_array[2].answer_B = "String";
    q_array[2].answer_C = "byte";
    q_array[2].answer_D = "char";
    q_array[2].correct = "B";
    q_array[2].used = 0;
    q_array[2].type = java;
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
    q_array[3].type = java;
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
    q_array[4].type = c;
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
    q_array[5].type = c;
    q_array[5].hint = "Not A";

    q_array[6].question = "Which of the following is true about an abstract method inherited into a class Apple?";
    q_array[6].answer_A = "It must be defined in Apple before Apple can be instantiated";
    q_array[6].answer_B = "It always forces Apple to become abstract.";
    q_array[6].answer_C = "Both a and b";
    q_array[6].answer_D = "Neither a nor b";
    q_array[6].correct = "A";
    q_array[6].used = 0;
    q_array[6].type = conc;
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
    q_array[7].type = java;
    q_array[7].hint = "Not B";
    

	q_array[8].question = "What does GUI stand for?\n";
	q_array[8].answer_A = "Greatest Universe Inside";
	q_array[8].answer_B = "Graphical User Interface";
	q_array[8].answer_C = "Good Until Interface";
	q_array[8].answer_D = "Growing Urban Integration";
	q_array[8].correct = "B";
	q_array[8].used = 0;
	q_array[8].type = conc;
	q_array[8].hint = "It isn't growing or good";

	q_array[9].question = "What is pseudocode?\n";
	q_array[9].answer_A = "Code you write in a group or in a formal business meeting";
	q_array[9].answer_B = "Trick question -- it's not code at all";
	q_array[9].answer_C = "Code written in the programming language called PSE";
	q_array[9].answer_D = "Used by programmers to help map out what a program is supposed to do";
	q_array[9].correct = "D";
	q_array[9].used = 0;
	q_array[9].type = conc;
	q_array[9].hint = "It's used in planning out your code";

	q_array[10].question = "Which is an example of only declaring a number variable\n";
	q_array[10].answer_A = "String var = \"hello\";";
	q_array[10].answer_B = "int a;";
	q_array[10].answer_C = "int number = 4;";
	q_array[10].answer_D = "Integer int = new Integer()";
	q_array[10].correct = "B";
	q_array[10].used = 0;
	q_array[10].type = conc;
	q_array[10].hint = "Remember, declarations aren't initializations";

	q_array[11].question = "What does it mean to \"override\" a method\n";
	q_array[11].answer_A = "It's when you override an inherited method from a super class";
	q_array[11].answer_B = "When you redefine a method with new ";
	q_array[11].answer_C = "To erase the method from an object";
	q_array[11].answer_D = "It's when you hack into someone else's methods and use them as your own";
	q_array[11].correct = "A";
	q_array[11].used = 0;
	q_array[11].type = java;
	q_array[11].hint = "No hacking required";

	q_array[12].question = "Which data type is the best for numbers\n";
	q_array[12].answer_A = "char";
	q_array[12].answer_B = "String";
	q_array[12].answer_C = "byte";
	q_array[12].answer_D = "int";
	q_array[12].correct = "D";
	q_array[12].used = 0;
	q_array[12].type = conc;
	q_array[12].hint = "char and String are for letters";
	
	q_array[13].question = "An array with that ranges from 0 - 4 has a length of..\n";
	q_array[13].answer_A = "3";
	q_array[13].answer_B = "4";
	q_array[13].answer_C = "5";
	q_array[13].answer_D = "6";
	q_array[13].correct = "C";
	q_array[13].used = 0;
	q_array[13].type = conc;
	q_array[13].hint = "Count from 0";
	
	q_array[14].question = "To terminate a case in a switch statement you must have a..\n";
	q_array[14].answer_A = "Null terminator";
	q_array[14].answer_B = "Semi-colon";
	q_array[14].answer_C = "Break statement";
	q_array[14].answer_D = "Period";
	q_array[14].correct = "C";
	q_array[14].used = 0;
	q_array[14].type = conc;
	q_array[14].hint = "It isn't A or D";
	
	q_array[15].question = "To use predefined java methods you must _____ a library\n";
	q_array[15].answer_A = "Import";
	q_array[15].answer_B = "Buy";
	q_array[15].answer_C = "Include";
	q_array[15].answer_D = "Ask to use";
	q_array[15].correct = "A";
	q_array[15].used = 0;
	q_array[15].type = java;
	q_array[15].hint = "Java doesn't use #include statements";

	q_array[16].question = "Which statement is true:\n";
	q_array[16].answer_A = "Java is an Object Orientated language";
	q_array[16].answer_B = "Steve Jobs is the father of Java";
	q_array[16].answer_C = "Java was named after coffee";
	q_array[16].answer_D = "Java a low level language";
	q_array[16].correct = "A";
	q_array[16].used = 0;
	q_array[16].type = java;
	q_array[16].hint = "Steve Jobs didn't create Java";

	q_array[17].question = "Which language would be considered a low-level language?\n";
	q_array[17].answer_A = "Java";
	q_array[17].answer_B = "C";
	q_array[17].answer_C = "No one uses low-level programming since the 80s";
	q_array[17].answer_D = "Javascript";
	q_array[17].correct = "B";
	q_array[17].used = 0;
	q_array[17].type = java;
	q_array[17].hint = "Low-level programming is still used today";

	q_array[18].question = "When a variable is follwed by ++ it is...\n";
	q_array[18].answer_A = "Twice as +";
	q_array[18].answer_B = "Incremented by one";
	q_array[18].answer_C = "Incremented by two";
	q_array[18].answer_D = "A C++ statement";
	q_array[18].correct = "B";
	q_array[18].used = 0;
	q_array[18].type = conc;
	q_array[18].hint = "It's used in Java too";
	
	q_array[19].question = "Which symbol means \"or\"?\n";
	q_array[19].answer_A = "$";
	q_array[19].answer_B = "||";
	q_array[19].answer_C = "&&";
	q_array[19].answer_D = "&";
	q_array[19].correct = "B";
	q_array[19].used = 0;
	q_array[19].type = conc;
	q_array[19].hint = "& means \"and\"";

	q_array[20].question = "Does Java use garbage collection?\n";
	q_array[20].answer_A = "Yes";
	q_array[20].answer_B = "Only in for loops";
	q_array[20].answer_C = "Yes, but only on garbage days";
	q_array[20].answer_D = "No";
	q_array[20].correct = "A";
	q_array[20].used = 0;
	q_array[20].type = java;
	q_array[20].hint = "Garbage collection is used by most high-level languages";
    
    long added = 0;
    for(int i=0;i<numQuestions;i++){
    	added = ds.createQuestion(q_array[i]).id;
    }
    
    Log.d("Add All Into Table", "Added "+ String.valueOf(added) + " Questions");
    
}

public boolean exists(){
	Cursor cursor = database.rawQuery("SELECT * FROM " + MySQLiteHelper.TABLE_QUESTIONS + " ", null);
	if(cursor.moveToFirst()){
		return true;
	}
	return false;
}

}


