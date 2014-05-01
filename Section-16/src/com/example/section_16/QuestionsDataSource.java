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
  private String[] saveColumns = {MySQLiteHelper.S_ID, MySQLiteHelper.S_TYPE, MySQLiteHelper.S_DIFF, MySQLiteHelper.S_SAVE};
  
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
      database.execSQL("UPDATE " + MySQLiteHelper.TABLE_QUESTIONS + " SET USED = 1 WHERE ID = " + q.id);            
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
  public Set getSettings(){
	  Set s = null;
	  Cursor cursor = database.query(MySQLiteHelper.TABLE_SETTINGS,
	        saveColumns, null, null, null, null, null);
	  if(cursor.moveToFirst()){
		  s = new Set();
		  s.id = cursor.getLong(0);
		  s.type  = cursor.getInt(1);
		  s.diff = cursor.getInt(2);
		  s.save = cursor.getInt(3);
	  }
	  return s;
  }
  
  public void updateSave(int state){
	  database.execSQL("update " + MySQLiteHelper.TABLE_SETTINGS +
			  " set " + MySQLiteHelper.S_SAVE + "=" + state +
			  " where ID=1");
  }
  public void resetQuestions(){
	  database.execSQL("UPDATE " + MySQLiteHelper.TABLE_QUESTIONS + " SET USED = 0");
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
	int numQuestions = 102;
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
	
	q_array[21].question = "In Java, an object...";
	q_array[21].answer_A = "is a list of classes.";
	q_array[21].answer_B = "is the same as a variable.";
	q_array[21].answer_C = "is an instance of a class.";
	q_array[21].answer_D = "cannot be used.";
	q_array[21].correct = "C";
	q_array[21].used = 0;
	q_array[21].type = java;
	q_array[21].hint = "An object is not a list of classes.";
	
	q_array[22].question = "In Java, a class...";
	q_array[22].answer_A = "cannot have its own methods.";
	q_array[22].answer_B = "is an overarching design pattern that creates objects.";
	q_array[22].answer_C = "is a subtype of objects";
	q_array[22].answer_D = "must have created instances in order to be a class";
	q_array[22].correct = "B";
	q_array[22].used = 0;
	q_array[22].type = java;
	q_array[22].hint = "A class is not a subtype of objects";
	
	q_array[23].question = "What is the purpose of a semi-colon?";
	q_array[23].answer_A = "To aid the programmer in visualizing the code";
	q_array[23].answer_B = "Semi-colons are needed to end class and method creation.";
	q_array[23].answer_C = "Semi-colons are needed to allow the use of a new line while coding.";
	q_array[23].answer_D = "The compiler uses semi-colons to determine the termination of a statement.";
	q_array[23].correct = "D";
	q_array[23].used = 0;
	q_array[23].type = conc;
	q_array[23].hint = "Semi-colons are recognized by the compiler.";
	
	q_array[24].question = "In Java, a constructor...";
	q_array[24].answer_A = "is used to create an instance of an object within a class.";
	q_array[24].answer_B = "is a waster of code";
	q_array[24].answer_C = "needs a return value";
	q_array[24].answer_D = "is a design pattern that creates an object";
	q_array[24].correct = "A";
	q_array[24].used = 0;
	q_array[24].type = java;
	q_array[24].hint = "It is not a waste of code.";
	
	q_array[25].question = "A Java variable name CANNOT begin with a...";
	q_array[25].answer_A = "4";
	q_array[25].answer_B = "_";
	q_array[25].answer_C = "$";
	q_array[25].answer_D = "C";
	q_array[25].correct = "A";
	q_array[25].used = 0;
	q_array[25].type = java;
	q_array[25].hint = "Can't be numeric.";
	
	q_array[26].question = "What is the purpose of static variables?";
	q_array[26].answer_A = "When performance can be sacrificed for the sake of protection";
	q_array[26].answer_B = "When only a copy of a variable is needed for every nstance of a class";
	q_array[26].answer_C = "All variables are static variables.";
	q_array[26].answer_D = "Static variables are not used in Java";
	q_array[26].correct = "B";
	q_array[26].used = 0;
	q_array[26].type = java;
	q_array[26].hint = "They are used in Java";
	
	q_array[27].question = "A boolean variable is...";
	q_array[27].answer_A = "a set of numbers";
	q_array[27].answer_B = "a set of letters";
	q_array[27].answer_C = "either true or false";
	q_array[27].answer_D = "a set of colors";
	q_array[27].correct = "C";
	q_array[27].used = 0;
	q_array[27].type = conc;
	q_array[27].hint = "Has nothing to do with colors";
	
	q_array[28].question = "..........\n"
			+ "int x = 0, y = 1;\n"
			+ "while(x>10){\n"
			+ "\tx+=2;\n"
			+ "\ty*=2;\n"
			+ "}\n"
			+ "..........\n"
			+ "After the loop, the value of y is...";
	q_array[28].answer_A = "Compiler Error";
	q_array[28].answer_B = "16";
	q_array[28].answer_C = "64";
	q_array[28].answer_D = "42";
	q_array[28].correct = "D";
	q_array[28].used = 0;
	q_array[28].type = conc;
	q_array[28].hint = "It is a power of 2";
	
	q_array[29].question = "..........\n"
			+ "for(int j = 0; j < 100; j++){\n"
			+ "\tt = 0;\n"
			+ "\tt--;\n"
			+ "}\n"
			+ "..........\n"
			+ "After the loop the value of t is...";
	q_array[29].answer_A = "Compiler Error";
	q_array[29].answer_B = "100";
	q_array[29].answer_C = "-100";
	q_array[29].answer_D = "-10";
	q_array[29].correct = "A";
	q_array[29].used = 0;
	q_array[29].type = conc;
	q_array[29].hint = "Its not negative";
	
	q_array[30].question = "What is an array index out of bounds exception?";
	q_array[30].answer_A = "When an array has been referenced that does not exist.";
	q_array[30].answer_B = "When an element of an array is referenced that is not a part of the array.";
	q_array[30].answer_C = "When an array stores the wrong data type.";
	q_array[30].answer_D = "When one array shares the emory of another array.";
	q_array[30].correct = "B";
	q_array[30].used = 0;
	q_array[30].type = java;
	q_array[30].hint = "Has nothing to do with shared memory";
	
	q_array[31].question = "In which direction does a stack grow?";
	q_array[31].answer_A = "Up";
	q_array[31].answer_B = "Left";
	q_array[31].answer_C = "Down";
	q_array[31].answer_D = "Right";
	q_array[31].correct = "C";
	q_array[31].used = 0;
	q_array[31].type = conc;
	q_array[31].hint = "A stack is visualized vertically";
	
	q_array[32].question = "To reference the array initialzed as 'array[10]' you must use elements...";
	q_array[32].answer_A = "0 - 10";
	q_array[32].answer_B = "1 - 9";
	q_array[32].answer_C = "1 - 10";
	q_array[32].answer_D = "0 - 9";
	q_array[32].correct = "D";
	q_array[32].used = 0;
	q_array[32].type = conc;
	q_array[32].hint = "Array referencing begins at 0";
	
	q_array[33].question = "ASCII characters...";
	q_array[33].answer_A = "are the number representations of English charactesr.";
	q_array[33].answer_B = "are the charasters in a popular Java education textbook.";
	q_array[33].answer_C = "are used when converting code from Java to C#.";
	q_array[33].answer_D = "are all negative values";
	q_array[33].correct = "A";
	q_array[33].used = 0;
	q_array[33].type = conc;
	q_array[33].hint = "They are numeric values";
	
	q_array[34].question = "int a = 0, b = 0, c = 0;\n"
			+ "while(a < 10 || b < 5){\n"
			+ "\ta++;\n"
			+ "\tb++;\n"
			+ "\tc++;\b"
			+ "}\n"
			+ "What is the value of c after the loop?";
	q_array[34].answer_A = "Compiler Error";
	q_array[34].answer_B = "5";
	q_array[34].answer_C = "10";
	q_array[34].answer_D = "0";
	q_array[34].correct = "B";
	q_array[34].used = 0;
	q_array[34].type = conc;
	q_array[34].hint = "Check the details of the while loop.";

	q_array[35].question = "char val = 'B';\n"
			+ "val++;\n"
			+ "What is the new value of val?";
	q_array[35].answer_A = "A";
	q_array[35].answer_B = "B";
	q_array[35].answer_C = "C";
	q_array[35].answer_D = "Compilation Error";
	q_array[35].correct = "C";
	q_array[35].used = 0;
	q_array[35].type = conc;
	q_array[35].hint = "Add one to 'B' and you get...?";
	
	q_array[36].question = "char c = 0; is the same as char = '0'.";
	q_array[36].answer_A = "True";
	q_array[36].answer_B = "False";
	q_array[36].answer_C = "N/A";
	q_array[36].answer_D = "N/A";
	q_array[36].correct = "B";
	q_array[36].used = 0;
	q_array[36].type = java;
	q_array[36].hint = "N/A";
	
	q_array[37].question = "Polymorphism...";
	q_array[37].answer_A = "allows variables to change their type.";
	q_array[37].answer_B = "is determined at runtime alone.";
	q_array[37].answer_C = "is determined at the compile time alone.";
	q_array[37].answer_D = "allows you to define multiple different functions with the same name.";
	q_array[37].correct = "D";
	q_array[37].used = 0;
	q_array[37].type = java;
	q_array[37].hint = "Not determined at compilation or runtime alone";
	
	q_array[38].question = "What is the difference between overloading and overriding?";
	q_array[38].answer_A = "Overloading happens during compile time while overriding happens during runtime.";
	q_array[38].answer_B = "They are the same thing.";
	q_array[38].answer_C = "Overloading is a systems error when you try to override.";
	q_array[38].answer_D = "Overriding is changing code after compilation while overloading is to change the code too much,causing a crash.";
	q_array[38].correct = "A";
	q_array[38].used = 0;
	q_array[38].type = java;
	q_array[38].hint = "They both occur at seperate times.";
	
	q_array[39].question = "Which of the following is true about switch statements?";
	q_array[39].answer_A = "A switch statement can only access one case per evaluation.";
	q_array[39].answer_B = "Breaks are used to ensure the correct amount of cases are used.";
	q_array[39].answer_C = "Switch statements can do operations that cannot be done with if-then-else statements.";
	q_array[39].answer_D = "Switch statements need to end with a break statement.";
	q_array[39].correct = "C";
	q_array[39].used = 0;
	q_array[39].type = conc;
	q_array[39].hint = "Breaks are helpful, but not required.";

	q_array[40].question = "Which choice is INCORRECT about threads in Java?";
	q_array[40].answer_A = "A single thread in a program can be used without the need for other threads.";
	q_array[40].answer_B = "Threading allows multiple points of execution in the program.";
	q_array[40].answer_C = "Threading is less CPU intensive then OS forking";
	q_array[40].answer_D = "All threads share a single execution stack";
	q_array[40].correct = "D";
	q_array[40].used = 0;
	q_array[40].type = java;
	q_array[40].hint = "Why would different threads have the same execution stack?";
	
	q_array[41].question = "What operation is used to make sure all threads are done before continuing?";
	q_array[41].answer_A = "join()";
	q_array[41].answer_B = "wait()";
	q_array[41].answer_C = "connect()";
	q_array[41].answer_D = "fuse()";
	q_array[41].correct = "A";
	q_array[41].used = 0;
	q_array[41].type = java;
	q_array[41].hint = "Thing of it as a bunch of threads all coming together.";
	
	q_array[42].question = "What does making an object public in Java do?";
	q_array[42].answer_A = "It causes an error at compilation.";
	q_array[42].answer_B = "It allows the object to be accessed from anywhere in the program.";
	q_array[42].answer_C = "It allows an object to be accessed only from classes in the same directory.";
	q_array[42].answer_D = "It prevents the object from being accessed by any other class.";
	q_array[42].correct = "B";
	q_array[42].used = 0;
	q_array[42].type = java;
	q_array[42].hint = "You can also make objects private and protected.";

	q_array[43].question = "What does making an object private in java do?";
	q_array[43].answer_A = "It allows the object to be accessed from any class.";
	q_array[43].answer_B = "It prevents the object from being used in public classes.";
	q_array[43].answer_C = "It prevents any other class from accessing the object.";
	q_array[43].answer_D = "The object must be placed in a protected class";
	q_array[43].correct = "C";
	q_array[43].used = 0;
	q_array[43].type = java;
	q_array[43].hint = "You can also make objects public and protected.";
	
	q_array[44].question = "What does making an object protected in Java do?";
	q_array[44].answer_A = "It does the same thing as makeing an object public";
	q_array[44].answer_B = "It allows the object to only be used in encrypted programs.";
	q_array[44].answer_C = "It prevents the object's variables from being modified.";
	q_array[44].answer_D = "It allows the object to be accessed only from classes in the same directory.";
	q_array[44].correct = "D";
	q_array[44].used = 0;
	q_array[44].type = java;
	q_array[44].hint = "You can also make objects public and private.";
	
	q_array[45].question = "G.U.I. stands for...";
	q_array[45].answer_A = "Graphical User Interface";
	q_array[45].answer_B = "Graphs Utilizing Intelligence";
	q_array[45].answer_C = "Generic Unit Instance";
	q_array[45].answer_D = "Generation Underflow Inline";
	q_array[45].correct = "A";
	q_array[45].used = 0;
	q_array[45].type = conc;
	q_array[45].hint = "It is an interface.";
	
	q_array[46].question = "What is a pointer in C?";
	q_array[46].answer_A = "Pointers are the same as variables.";
	q_array[46].answer_B = "Pointers hold the memory address of variables.";
	q_array[46].answer_C = "Pointers store the values saved in variables.";
	q_array[46].answer_D = "Pointers are variables that do not need to be initialized.";
	q_array[46].correct = "B";
	q_array[46].used = 0;
	q_array[46].type = c;
	q_array[46].hint = "They hold something pertaining to a variables.";
	
	q_array[47].question = "A pointer can point to a function.";
	q_array[47].answer_A = "True";
	q_array[47].answer_B = "False";
	q_array[47].answer_C = "N/A";
	q_array[47].answer_D = "N/A";
	q_array[47].correct = "A";
	q_array[47].used = 0;
	q_array[47].type = c;
	q_array[47].hint = "N/A";
	
	q_array[48].question = "When pointing to a structure, which operator is used to access the members of the structure?";
	q_array[48].answer_A = "&";
	q_array[48].answer_B = "*";
	q_array[48].answer_C = "->";
	q_array[48].answer_D = ".";
	q_array[48].correct = "C";
	q_array[48].used = 0;
	q_array[48].type = c;
	q_array[48].hint = "Think of it as a literal pointer.";
	
	q_array[49].question = "In a printf statement '%d' is used to print what type of variable?";
	q_array[49].answer_A = "Float";
	q_array[49].answer_B = "String";
	q_array[49].answer_C = "Hexadecimal Value";
	q_array[49].answer_D = "Integer";
	q_array[49].correct = "D";
	q_array[49].used = 0;
	q_array[49].type = conc;
	q_array[49].hint = "It is a decimal, numerical value";
	
	q_array[50].question = "In a printf statement '%s' is used to print what type of variable?";
	q_array[50].answer_A = "Float";
	q_array[50].answer_B = "String";
	q_array[50].answer_C = "Integer";
	q_array[50].answer_D = "Hexadecimal Value";
	q_array[50].correct = "B";
	q_array[50].used = 0;
	q_array[50].type = conc;
	q_array[50].hint = "The letter matches the type of variable represented.";
	
	q_array[51].question = "In a printf statement '%f' is used to print what type of variable?";
	q_array[51].answer_A = "String";
	q_array[51].answer_B = "Integer";
	q_array[51].answer_C = "Float";
	q_array[51].answer_D = "Hexadecimal Value";
	q_array[51].correct = "C";
	q_array[51].used = 0;
	q_array[51].type = conc;
	q_array[51].hint = "The letter matches the type of variable represented.";
	
	q_array[52].question = "In a printf statement '%s' is used to print what type of variable?";
	q_array[52].answer_A = "Float";
	q_array[52].answer_B = "Integer";
	q_array[52].answer_C = "String";
	q_array[52].answer_D = "Hexadecimal Value";
	q_array[52].correct = "D";
	q_array[52].used = 0;
	q_array[52].type = conc;
	q_array[52].hint = "It is a non-decimal, numeric value.";
	
	q_array[53].question = "LIFO stands for...";
	q_array[53].answer_A = "Last In First Out";
	q_array[53].answer_B = "Late Initialization For Objects";
	q_array[53].answer_C = "Listing Instance Fields Openly";
	q_array[53].answer_D = "Looking in Final Operations";
	q_array[53].correct = "A";
	q_array[53].used = 0;
	q_array[53].type = conc;
	q_array[53].hint = "This is utilized in a stack.";

	q_array[54].question = "FIFO stands for...";
	q_array[54].answer_A = "Finalizing Instances For Objects";
	q_array[54].answer_B = "First In First Out";
	q_array[54].answer_C = "Fields Initializing Final Operations";
	q_array[54].answer_D = "Forgetting Inheritance For Orientations";
	q_array[54].correct = "B";
	q_array[54].used = 0;
	q_array[54].type = conc;
	q_array[54].hint = "This is utilized in a queue.";
	
	q_array[55].question = "What is the average time complexity of a bubble sort?";
	q_array[55].answer_A = "O(n)";
	q_array[55].answer_B = "O(n*log(n))";
	q_array[55].answer_C = "O(n^2)";
	q_array[55].answer_D = "O(n*n!)";
	q_array[55].correct = "C";
	q_array[55].used = 0;
	q_array[55].type = conc;
	q_array[55].hint = "Think of the number of operations required to run this kind of sort.";
	
	q_array[56].question = "What is the average time complexity of a quick sort?";
	q_array[56].answer_A = "O(n)";
	q_array[56].answer_B = "O(n^2)";
	q_array[56].answer_C = "O(n*n!)";
	q_array[56].answer_D = "O(n*log(n))";
	q_array[56].correct = "D";
	q_array[56].used = 0;
	q_array[56].type = conc;
	q_array[56].hint = "Think of the number of operations required to run this kind of sort.";
	
	q_array[57].question = "What is the average time complexity of a bogo sort?";
	q_array[57].answer_A = "O(n*n!)";
	q_array[57].answer_B = "O(n)";
	q_array[57].answer_C = "O(n^2)";
	q_array[57].answer_D = "O(n*log(n))";
	q_array[57].correct = "A";
	q_array[57].used = 0;
	q_array[57].type = conc;
	q_array[57].hint = "Think of the number of operations required to run this kind of sort.";

	q_array[58].question = "What operation is used to transfer data and control from the current function to the calling function?";
	q_array[58].answer_A = "transfer()";
	q_array[58].answer_B = "return()";
	q_array[58].answer_C = "back()";
	q_array[58].answer_D = "switch()";
	q_array[58].correct = "B";
	q_array[58].used = 0;
	q_array[58].type = conc;
	q_array[58].hint = "";
	
	q_array[59].question = "In binary (base 2), what is the value of 5?";
	q_array[59].answer_A = "0b111";
	q_array[59].answer_B = "0b11111";
	q_array[59].answer_C = "0b101";
	q_array[59].answer_D = "0b10101";
	q_array[59].correct = "C";
	q_array[59].used = 0;
	q_array[59].type = conc;
	q_array[59].hint = "4 in binary is 100";
	
	q_array[60].question = "What is the decimal value of the binary number 0b10000";
	q_array[60].answer_A = "10,000";
	q_array[60].answer_B = "64";
	q_array[60].answer_C = "8";
	q_array[60].answer_D = "16";
	q_array[60].correct = "D";
	q_array[60].used = 0;
	q_array[60].type = conc;
	q_array[60].hint = "0b1000 is 8";
	
	q_array[61].question = "Which statement returns TRUE?";
	q_array[61].answer_A = "1 && 1 || 0";
	q_array[61].answer_B = "0 && 0 || 0";
	q_array[61].answer_C = "1 && 0 || 0";
	q_array[61].answer_D = "0 && 1 || 0";
	q_array[61].correct = "A";
	q_array[61].used = 0;
	q_array[61].type = conc;
	q_array[61].hint = "Anything and 0 will return false.";
	
	q_array[62].question = "Which header is needed to use printf()?";
	q_array[62].answer_A = "stdlib.h";
	q_array[62].answer_B = "stdio.h";
	q_array[62].answer_C = "math.h";
	q_array[62].answer_D = "string.h";
	q_array[62].correct = "B";
	q_array[62].used = 0;
	q_array[62].type = c;
	q_array[62].hint = "You need the header for Standard Input/Output.";
	
	q_array[63].question = "Which header file is needed to allocate memory with malloc()?";
	q_array[63].answer_A = "math.h";
	q_array[63].answer_B = "stdio.h";
	q_array[63].answer_C = "stdlib.h";
	q_array[63].answer_D = "string.h";
	q_array[63].correct = "C";
	q_array[63].used = 0;
	q_array[63].type = c;
	q_array[63].hint = "You need the header for Standard Library.";
	
	q_array[64].question = "What function should be used to clear memory allocated from malloc()?";
	q_array[64].answer_A = "erase()";
	q_array[64].answer_B = "clear()";
	q_array[64].answer_C = "dealloc()";
	q_array[64].answer_D = "free()";
	q_array[64].correct = "D";
	q_array[64].used = 0;
	q_array[64].type = c;
	q_array[64].hint = "The memory is not erased.";
	
	q_array[65].question = "In C, when passing an array as an argument, what is being passed?";
	q_array[65].answer_A = "Value of the elements in the array";
	q_array[65].answer_B = "The first element in the array";
	q_array[65].answer_C = "The base address of the array";
	q_array[65].answer_D = "The address of the last element in the array";
	q_array[65].correct = "A";
	q_array[65].used = 0;
	q_array[65].type = c;
	q_array[65].hint = "Passing an array is different than passing a pointer";
	
	q_array[66].question = "What type cannot be checked in a switch statement?";
	q_array[66].answer_A = "Enum";
	q_array[66].answer_B = "Float";
	q_array[66].answer_C = "Integer";
	q_array[66].answer_D = "Character";
	q_array[66].correct = "B";
	q_array[66].used = 0;
	q_array[66].type = conc;
	q_array[66].hint = "It is a numerical value.";
	
	q_array[67].question = "In C, results from logical operations are either...";
	q_array[67].answer_A = "True or False";
	q_array[67].answer_B = "Depends on the computer";
	q_array[67].answer_C = "0 or 1";
	q_array[67].answer_D = "None of these are correct";
	q_array[67].correct = "C";
	q_array[67].used = 0;
	q_array[67].type = c;
	q_array[67].hint = "It doesn't depend on the computer.";
	
	q_array[68].question = "Which is NOT a logical operator?";
	q_array[68].answer_A = "!=";
	q_array[68].answer_B = "==";
	q_array[68].answer_C = "||";
	q_array[68].answer_D = "=";
	q_array[68].correct = "D";
	q_array[68].used = 0;
	q_array[68].type = c;
	q_array[68].hint = "Notice how only one consists of a single character.";
	
	q_array[69].question = "The statement:\n"
			+ "..........\n"
			+ "for(;;)\n"
			+ "..........\n"
			+ "is valid C code";
	q_array[69].answer_A = "True";
	q_array[69].answer_B = "False";
	q_array[69].answer_C = "N/A";
	q_array[69].answer_D = "N/A";
	q_array[69].correct = "A";
	q_array[69].used = 0;
	q_array[69].type = c;
	q_array[69].hint = "N/A";
	
	q_array[70].question = "It is impossible for a for loop to enter an infinite loop.";
	q_array[70].answer_A = "True";
	q_array[70].answer_B = "False";
	q_array[70].answer_C = "N/A";
	q_array[70].answer_D = "N/A";
	q_array[70].correct = "B";
	q_array[70].used = 0;
	q_array[70].type = conc;
	q_array[70].hint = "N/A";
	
	q_array[71].question = "Which keyword is used to leave an iteration of a loop?";
	q_array[71].answer_A = "break()";
	q_array[71].answer_B = "return()";
	q_array[71].answer_C = "continue()";
	q_array[71].answer_D = "next()";
	q_array[71].correct = "C";
	q_array[71].used = 0;
	q_array[71].type = c;
	q_array[71].hint = "You want to stay in the loop, and just skip the current  iteration.";
	
	q_array[72].question = "Which keyword is used to exit an entire loop bu stay in the same function?";
	q_array[72].answer_A = "continue()";
	q_array[72].answer_B = "return()";
	q_array[72].answer_C = "end()";
	q_array[72].answer_D = "break()";
	q_array[72].correct = "D";
	q_array[72].used = 0;
	q_array[72].type = c;
	q_array[72].hint = "You want to leave the loop, but stay in the function";
	
	q_array[73].question = "Compilers allow a return to be in the middle of a while or for loop.";
	q_array[73].answer_A = "True";
	q_array[73].answer_B = "False";
	q_array[73].answer_C = "N/A";
	q_array[73].answer_D = "N/A";
	q_array[73].correct = "A";
	q_array[73].used = 0;
	q_array[73].type = c;
	q_array[73].hint = "N/A";
	
	q_array[74].question = "An array can have at most 3 dimensions.";
	q_array[74].answer_A = "True";
	q_array[74].answer_B = "False";
	q_array[74].answer_C = "N/A";
	q_array[74].answer_D = "N/A";
	q_array[74].correct = "B";
	q_array[74].used = 0;
	q_array[74].type = conc;
	q_array[74].hint = "N/A";
	
	q_array[75].question = "The for loop:\n"
			+ "..........\n"
			+ "for(x = 0; x < 10; x++)\n"
			+ "..........\n"
			+ "will loop how many times?";
	q_array[75].answer_A = "9";
	q_array[75].answer_B = "11";
	q_array[75].answer_C = "10";
	q_array[75].answer_D = "Until the program crashes";
	q_array[75].correct = "C";
	q_array[75].used = 0;
	q_array[75].type = conc;
	q_array[75].hint = "";
	
	q_array[76].question = "A linked list can implement random access.";
	q_array[76].answer_A = "True";
	q_array[76].answer_B = "False";
	q_array[76].answer_C = "N/A";
	q_array[76].answer_D = "N/A";
	q_array[76].correct = "B";
	q_array[76].used = 0;
	q_array[76].type = conc;
	q_array[76].hint = "N/A";
	
	q_array[77].question = "What is the result of 'int var = 2.6' and then printing out var?";
	q_array[77].answer_A = "2.6";
	q_array[77].answer_B = "3";
	q_array[77].answer_C = "2";
	q_array[77].answer_D = "Error";
	q_array[77].correct = "C";
	q_array[77].used = 0;
	q_array[77].type = conc;
	q_array[77].hint = "A decimal put into an int is truncated; not rounded.";
	
	q_array[78].question = "A function is not allowed to call itself.";
	q_array[78].answer_A = "True";
	q_array[78].answer_B = "False";
	q_array[78].answer_C = "N/A";
	q_array[78].answer_D = "N/A";
	q_array[78].correct = "B";
	q_array[78].used = 0;
	q_array[78].type = conc;
	q_array[78].hint = "N/A";
	
	q_array[79].question = "What is an external variable used for in C?";
	q_array[79].answer_A = "An external variable prevents the use of multiple functions in a file";
	q_array[79].answer_B = "An external variable is allowed to be used in other source files than the one it was initialized in";
	q_array[79].answer_C = "An external variable is not used in C code, but in Java";
	q_array[79].answer_D = "An external variable does not need to be initialized at compiler time, but instead can ber done at runtime";
	q_array[79].correct = "B";
	q_array[79].used = 0;
	q_array[79].type = c;
	q_array[79].hint = "Why would something want to be used externally?";
	
	q_array[80].question = "Both a pointer's source and target can be changed.";
	q_array[80].answer_A = "True";
	q_array[80].answer_B = "False";
	q_array[80].answer_C = "N/A";
	q_array[80].answer_D = "N/A";
	q_array[80].correct = "A";
	q_array[80].used = 0;
	q_array[80].type = c;
	q_array[80].hint = "N/A";
	
	q_array[81].question = "What happens when using '%d' in a printf statement when printing a character?";
	q_array[81].answer_A = "This causes a compilation error";
	q_array[81].answer_B = "This causes a runtime error";
	q_array[81].answer_C = "The character is still printed";
	q_array[81].answer_D = "The ANSII value of the character is printed.";
	q_array[81].correct = "D";
	q_array[81].used = 0;
	q_array[81].type = conc;
	q_array[81].hint = "What would an integer value of a character be?";
	
	q_array[82].question = "How do you refer to the first element of an array?";
	q_array[82].answer_A = "array[1]";
	q_array[82].answer_B = "array[]";
	q_array[82].answer_C = "array[0]";
	q_array[82].answer_D = "arra[start]";
	q_array[82].correct = "C";
	q_array[82].used = 0;
	q_array[82].type = conc;
	q_array[82].hint = "Counting starts at 0 in programming.";
	
	q_array[83].question = "What is the maximum amount of characters that can be used in command-line arguments?";
	q_array[83].answer_A = "64";
	q_array[83].answer_B = "126";
	q_array[83].answer_C = "256";
	q_array[83].answer_D = "Depends on your operating system";
	q_array[83].correct = "D";
	q_array[83].used = 0;
	q_array[83].type = conc;
	q_array[83].hint = "Are all operating systems created equal?";
	
	q_array[84].question = "Macros have a local scope.";
	q_array[84].answer_A = "True";
	q_array[84].answer_B = "False";
	q_array[84].answer_C = "N/A";
	q_array[84].answer_D = "N/A";
	q_array[84].correct = "B";
	q_array[84].used = 0;
	q_array[84].type = c;
	q_array[84].hint = "N/A";
	
	q_array[85].question = "Which is the correct function to reverse a string?";
	q_array[85].answer_A = "strback()";
	q_array[85].answer_B = "revstr()";
	q_array[85].answer_C = "strrev()";
	q_array[85].answer_D = "strswitch()";
	q_array[85].correct = "C";
	q_array[85].used = 0;
	q_array[85].type = c;
	q_array[85].hint = "Almost all string functions begin with str";
	
	q_array[86].question = "Which is the correct function to copy a string?";
	q_array[86].answer_A = "copystr()";
	q_array[86].answer_B = "stringcopy()";
	q_array[86].answer_C = "memcopy()";
	q_array[86].answer_D = "strcpy()";
	q_array[86].correct = "D";
	q_array[86].used = 0;
	q_array[86].type = c;
	q_array[86].hint = "Almost all string functions begin with str";
	
	q_array[87].question = "Which is the correct function to find a string?";
	q_array[87].answer_A = "strfind()";
	q_array[87].answer_B = "strlocate()";
	q_array[87].answer_C = "strwhr()";
	q_array[87].answer_D = "strstr()";
	q_array[87].correct = "D";
	q_array[87].used = 0;
	q_array[87].type = c;
	q_array[87].hint = "It finds a string within a string.";
	
	q_array[88].question = "What is the correct order that C code does arithmetic operations?";
	q_array[88].answer_A = "* / + -";
	q_array[88].answer_B = "/ * - +";
	q_array[88].answer_C = "/ * + -";
	q_array[88].answer_D = "* / - +";
	q_array[88].correct = "C";
	q_array[88].used = 0;
	q_array[88].type = c;
	q_array[88].hint = "Its not excatly the normal order of operations.";
	
	q_array[89].question = "In hexadecimal, the number 0xFFF represents:";
	q_array[89].answer_A = "45";
	q_array[89].answer_B = "151515";
	q_array[89].answer_C = "4095";
	q_array[89].answer_D = "255";
	q_array[89].correct = "C";
	q_array[89].used = 0;
	q_array[89].type = c;
	q_array[89].hint = "Each F represents 15 multiplied by 16 to the power of where the digit is.";
	
	q_array[90].question = "The decimal number 3011 can be displayed in hexadecimal as:";
	q_array[90].answer_A = "0xC3D0";
	q_array[90].answer_B = "0xB0F";
	q_array[90].answer_C = "0xDD0";
	q_array[90].answer_D = "0xBC3";
	q_array[90].correct = "D";
	q_array[90].used = 0;
	q_array[90].type = conc;
	q_array[90].hint = "Divide by 16. This is the furthest left digit. Then divide the remainder by 16, etc. until the number in base 16 is reached";
	
	q_array[91].question = "How do you reverence the last element in an array of 20 elements?";
	q_array[91].answer_A = "array[19]";
	q_array[91].answer_B = "array[20]";
	q_array[91].answer_C = "array[21]";
	q_array[91].answer_D = "array[end]";
	q_array[91].correct = "A";
	q_array[91].used = 0;
	q_array[91].type = conc;
	q_array[91].hint = "Counting starts at 0 in programming";
	
	q_array[92].question = "Nested for loops are not allowed.";
	q_array[92].answer_A = "True";
	q_array[92].answer_B = "False";
	q_array[92].answer_C = "N/A";
	q_array[92].answer_D = "N/A";
	q_array[92].correct = "B";
	q_array[92].used = 0;
	q_array[92].type = conc;
	q_array[92].hint = "N/A";
	
	q_array[93].question = "Nested while loops are not allowed.";
	q_array[93].answer_A = "True";
	q_array[93].answer_B = "False";
	q_array[93].answer_C = "N/A";
	q_array[93].answer_D = "N/A";
	q_array[93].correct = "B";
	q_array[93].used = 0;
	q_array[93].type = conc;
	q_array[93].hint = "N/A";
	
	q_array[94].question = "For loops and while loops are allowed to be nested in each other.";
	q_array[94].answer_A = "True";
	q_array[94].answer_B = "False";
	q_array[94].answer_C = "N/A";
	q_array[94].answer_D = "N/A";
	q_array[94].correct = "A";
	q_array[94].used = 0;
	q_array[94].type = conc;
	q_array[94].hint = "N/A";
	
	q_array[95].question = "A C program using a large amount of recursive calls may cause a stack overflow.";
	q_array[95].answer_A = "True";
	q_array[95].answer_B = "False";
	q_array[95].answer_C = "N/A";
	q_array[95].answer_D = "N/A";
	q_array[95].correct = "A";
	q_array[95].used = 0;
	q_array[95].type = c;
	q_array[95].hint = "N/A";
	
	q_array[96].question = "A macro cannot be initialized with lower case letters.";
	q_array[96].answer_A = "True";
	q_array[96].answer_B = "False";
	q_array[96].answer_C = "N/A";
	q_array[96].answer_D = "N/A";
	q_array[96].correct = "B";
	q_array[96].used = 0;
	q_array[96].type = c;
	q_array[96].hint = "N/A";
	
	q_array[97].question = "Header files contain:";
	q_array[97].answer_A = "Macros";
	q_array[97].answer_B = "Structure Declarations";
	q_array[97].answer_C = "Function Prototypes";
	q_array[97].answer_D = "All of these";
	q_array[97].correct = "D";
	q_array[97].used = 0;
	q_array[97].type = c;
	q_array[97].hint = "It contains more than just macros";
	
	q_array[98].question = "Input and output functin prototypes are stored in which header file.";
	q_array[98].answer_A = "inout.h";
	q_array[98].answer_B = "stdlib.h";
	q_array[98].answer_C = "stdio.h";
	q_array[98].answer_D = "math.h";
	q_array[98].correct = "C";
	q_array[98].used = 0;
	q_array[98].type = c;
	q_array[98].hint = "The header is for Standard Input and Output";
	
	q_array[99].question = "Every if-else statement can be mirrored in functionality with '?:'";
	q_array[99].answer_A = "True";
	q_array[99].answer_B = "False";
	q_array[99].answer_C = "N/A";
	q_array[99].answer_D = "N/A";
	q_array[99].correct = "B";
	q_array[99].used = 0;
	q_array[99].type = conc;
	q_array[99].hint = "N/A";
	
	q_array[100].question = "A function cannot contain two seperate return() statements.";
	q_array[100].answer_A = "True";
	q_array[100].answer_B = "False";
	q_array[100].answer_C = "N/A";
	q_array[100].answer_D = "N/A";
	q_array[100].correct = "B";
	q_array[100].used = 0;
	q_array[100].type = conc;
	q_array[100].hint = "N/A";
	
	q_array[101].question = "You cannot change the size of a statically defined array during runtime.";
	q_array[101].answer_A = "True";
	q_array[101].answer_B = "False";
	q_array[101].answer_C = "N/A";
	q_array[101].answer_D = "N/A";
	q_array[101].correct = "A";
	q_array[101].used = 0;
	q_array[101].type = conc;
	q_array[101].hint = "N/A";
	
	
	
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


