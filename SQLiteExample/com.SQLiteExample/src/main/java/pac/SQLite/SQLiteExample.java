package pac.SQLite;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.sql.Statement;
import java.util.Random;

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
public class SQLiteExample extends Activity {

    LinearLayout Linear;
    SQLiteDatabase mydb;
    Question q_array[];
    private static String DBNAME = "PERSONS.db";    // THIS IS THE SQLITE DATABASE FILE NAME.
    private static String TABLE = "MY_TABLE";       // THIS IS THE TABLE NAME

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_sqlite_example);

        Linear  = (LinearLayout)findViewById(R.id.linear);
        Toast.makeText(getApplicationContext(), "Creating table.", Toast.LENGTH_SHORT).show();

        dropTable();        // DROPPING THE TABLE.
        createTable();

        q_array = new Question[100];

        q_array[0] = new Question();
        q_array[1] = new Question();
        q_array[2] = new Question();
        
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

        insertAllIntoTable(q_array);

        q_array[3] = retrieveQuestion();

        insertOneIntoTable(q_array[3]);

        showTableValues();

    }
    // THIS FUNCTION SETS COLOR AND PADDING FOR THE TEXTVIEWS 
    public void setColor(TextView t){
        t.setTextColor(Color.BLACK);
        t.setPadding(20, 5, 0, 5);
        t.setTextSize(1, 15);
    }

    // CREATE TABLE IF NOT EXISTS 
    public void createTable(){
        try{
            mydb = openOrCreateDatabase(DBNAME, Context.MODE_PRIVATE,null);
            mydb.execSQL("CREATE TABLE IF  NOT EXISTS "+ TABLE +" (ID INTEGER PRIMARY KEY, QUESTION TEXT, ANSWER_A TEXT, ANSWER_B TEXT, ANSWER_C TEXT, ANSWER_D TEXT, CORRECT TEXT, USED INTEGER, DIFFICULTY INTEGER, HINT TEXT );");
            mydb.close();
        }catch(Exception e){
            Toast.makeText(getApplicationContext(), "Error in creating table", Toast.LENGTH_LONG);
        }
    }
    // THIS FUNCTION INSERTS DATA TO THE DATABASE
    public void insertOneIntoTable(Question q){
        try{
            mydb = openOrCreateDatabase(DBNAME, Context.MODE_PRIVATE,null);
            mydb.execSQL("INSERT INTO " + TABLE + "(QUESTION, ANSWER_A, ANSWER_B, ANSWER_C, ANSWER_D, CORRECT, USED, DIFFICULTY, HINT)" +
                    " VALUES('"+q.question+"','"+q.answer_A+"','"+q.answer_B+"','"+q.answer_C+"','"+q.answer_D+"','"+q.correct+"','"+q.used+"','"+q.difficulty+"','"+q.hint+"')");
            mydb.close();
        }catch(Exception e){
            Toast.makeText(getApplicationContext(), "Error in inserting into table", Toast.LENGTH_LONG);
        }
    }

    public Question retrieveQuestion(){

        Random rand = new Random();
        int id = rand.nextInt(3) + 1;
        Question q = new Question();

        try{
            mydb = openOrCreateDatabase(DBNAME, Context.MODE_PRIVATE,null);
            Cursor question_to_retrieve  = mydb.rawQuery("SELECT * FROM "+  TABLE+ " WHERE ID = " + id, null);

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

    public void insertAllIntoTable(Question q[]){
        try{
            mydb = openOrCreateDatabase(DBNAME, Context.MODE_PRIVATE,null);
            int i = 0;

            while(q_array[i] != null){
                 mydb.execSQL("INSERT INTO " + TABLE + "(QUESTION, ANSWER_A, ANSWER_B, ANSWER_C, ANSWER_D, CORRECT, USED, DIFFICULTY, HINT)" +
                            " VALUES('"+q[i].question+"','"+q[i].answer_A+"','"+q[i].answer_B+"','"+q[i].answer_C+"','"+q[i].answer_D+"','"+q[i].correct+"','"+q[i].used+"','"+q[i].difficulty+"','"+q[i].hint+"')");
                 i++;
            }
            mydb.close();
        }catch(Exception e){
            Toast.makeText(getApplicationContext(), "Error in inserting into table", Toast.LENGTH_LONG);
        }
    }
    // THIS FUNCTION SHOWS DATA FROM THE DATABASE 
    public void showTableValues(){
        try{
            mydb = openOrCreateDatabase(DBNAME, Context.MODE_PRIVATE,null);
            Cursor allrows  = mydb.rawQuery("SELECT * FROM "+  TABLE, null);
            System.out.println("COUNT : " + allrows.getCount());
            Integer cindex = allrows.getColumnIndex("QUESTION");
            Integer cindex1 = allrows.getColumnIndex("ANSWER_A");
            Integer cindex2 = allrows.getColumnIndex("ANSWER_B");
            Integer cindex3 = allrows.getColumnIndex("ANSWER_C");
            Integer cindex4 = allrows.getColumnIndex("ANSWER_D");
            Integer cindex5 = allrows.getColumnIndex("CORRECT");
            Integer cindex6 = allrows.getColumnIndex("USED");
            Integer cindex7 = allrows.getColumnIndex("DIFFICULTY");
            Integer cindex8 = allrows.getColumnIndex("HINT");

            TextView t = new TextView(this);
            t.setText("========================================");
            Linear.removeAllViews();
            Linear.addView(t);

            if(allrows.moveToFirst()){
                do{
                    LinearLayout id_row   = new LinearLayout(this);
                    LinearLayout question_row = new LinearLayout(this);
                    LinearLayout answer_a_row= new LinearLayout(this);
                    LinearLayout answer_b_row = new LinearLayout(this);
                    LinearLayout answer_c_row= new LinearLayout(this);
                    LinearLayout answer_d_row = new LinearLayout(this);
                    LinearLayout correct_row= new LinearLayout(this);
                    LinearLayout used_row = new LinearLayout(this);
                    LinearLayout difficulty_row= new LinearLayout(this);
                    LinearLayout hint_row= new LinearLayout(this);


                    final TextView id_  = new TextView(this);
                    final TextView question_ = new TextView(this);
                    final TextView answer_a_ = new TextView(this);
                    final TextView answer_b_ = new TextView(this);
                    final TextView answer_c_ = new TextView(this);
                    final TextView answer_d_ = new TextView(this);
                    final TextView correct_ = new TextView(this);
                    final TextView used_ = new TextView(this);
                    final TextView difficulty_ = new TextView(this);
                    final TextView hint_ = new TextView(this);
                    final TextView   sep  = new TextView(this);

                    String ID = allrows.getString(0);
                    String QUESTION= allrows.getString(1);
                    String ANSWER_A= allrows.getString(2);
                    String ANSWER_B= allrows.getString(3);
                    String ANSWER_C= allrows.getString(4);
                    String ANSWER_D= allrows.getString(5);
                    String CORRECT= allrows.getString(6);
                    String USED = allrows.getString(7);
                    String DIFFICULTY = allrows.getString(8);
                    String HINT = allrows.getString(9);

                    id_.setTextColor(Color.RED);
                    id_.setPadding(20, 5, 0, 5);
                    question_.setTextColor(Color.RED);
                    question_.setPadding(20, 5, 0, 5);
                    answer_a_.setTextColor(Color.RED);
                    answer_a_.setPadding(20, 5, 0, 5);
                    answer_b_.setTextColor(Color.RED);
                    answer_b_.setPadding(20, 5, 0, 5);
                    answer_c_.setTextColor(Color.RED);
                    answer_c_.setPadding(20, 5, 0, 5);
                    answer_d_.setTextColor(Color.RED);
                    answer_d_.setPadding(20, 5, 0, 5);
                    correct_.setTextColor(Color.RED);
                    correct_.setPadding(20, 5, 0, 5);
                    used_.setTextColor(Color.RED);
                    used_.setPadding(20, 5, 0, 5);
                    difficulty_.setTextColor(Color.RED);
                    difficulty_.setPadding(20, 5, 0, 5);
                    hint_.setTextColor(Color.RED);
                    hint_.setPadding(20, 5, 0, 5);

                    System.out.println("QUESTION " + allrows.getString(cindex) + " ANSWER_A : "+ allrows.getString(cindex1) + " ANSWER_B : "+ allrows.getString(cindex2)
                                  + " ANSWER_C : "+ allrows.getString(cindex3) + " ANSWER_D : "+ allrows.getString(cindex4) + " CORRECT : "+ allrows.getString(cindex5)
                                  + " USED : "+ allrows.getString(cindex6) + " DIFFICULTY : "+ allrows.getString(cindex7) + " HINT : " + allrows.getString(cindex8));
                    System.out.println("ID : "+ ID  + " || QUESTION " + QUESTION + "|| ANSWER_A : "+ ANSWER_A + "|| ANSWER_B : "+ ANSWER_B +
                                       "|| ANSWER_C : "+ ANSWER_C + "|| ANSWER_D : "+ ANSWER_D + "|| CORRECT : "+ CORRECT + "|| USED : "+ USED +
                                       "|| DIFFICULTY : "+DIFFICULTY + "|| HINT : "+ HINT);

                    id_.setText("ID : " + ID);
                    id_row.addView(id_);
                    Linear.addView(id_row);
                    question_.setText("QUESTION : " + QUESTION);
                    question_row.addView(question_);
                    Linear.addView(question_row);
                    answer_a_.setText("ANSWER_A : " + ANSWER_A);
                    answer_a_row.addView(answer_a_);
                    Linear.addView(answer_a_row);
                    answer_b_.setText("ANSWER_B : " + ANSWER_B);
                    answer_b_row.addView(answer_b_);
                    Linear.addView(answer_b_row);
                    answer_c_.setText("ANSWER_C : " + ANSWER_C);
                    answer_c_row.addView(answer_c_);
                    Linear.addView(answer_c_row);
                    answer_d_.setText("ANSWER_D : " + ANSWER_D);
                    answer_d_row.addView(answer_d_);
                    Linear.addView(answer_d_row);
                    correct_.setText("CORRECT : " + CORRECT);
                    correct_row.addView(correct_);
                    Linear.addView(correct_row);
                    used_.setText("USED : " + USED);
                    used_row.addView(used_);
                    Linear.addView(used_row);
                    difficulty_.setText("DIFFICULTY : " + DIFFICULTY);
                    difficulty_row.addView(difficulty_);
                    Linear.addView(difficulty_row);
                    hint_.setText("HINT : " + HINT);
                    hint_row.addView(hint_);
                    Linear.addView(hint_row);
                    sep.setText("---------------------------------------------------------------");
                    Linear.addView(sep);
                }
                while(allrows.moveToNext());
            }
            mydb.close();
        }catch(Exception e){
            Toast.makeText(getApplicationContext(), "Error encountered.", Toast.LENGTH_LONG);
        }
    }
    // THIS FUNCTION UPDATES THE DATABASE ACCORDING TO THE CONDITION 
    public void updateTable(){
        try{
            mydb = openOrCreateDatabase(DBNAME, Context.MODE_PRIVATE,null);
            mydb.execSQL("UPDATE " + TABLE + " SET NAME = 'MAX' WHERE PLACE = 'USA'");
            mydb.close();
        }catch(Exception e){
            Toast.makeText(getApplicationContext(), "Error encountered", Toast.LENGTH_LONG);
        }
    }
    // THIS FUNCTION DELETES VALUES FROM THE DATABASE ACCORDING TO THE CONDITION
    public void deleteValues(int id){
        try{
            mydb = openOrCreateDatabase(DBNAME, Context.MODE_PRIVATE,null);
            mydb.execSQL("DELETE FROM " + TABLE + " WHERE ID = "+id);
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