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

class Question{
    String question;
    String answer_A;
    String answer_B;
    String answer_C;
    String answer_D;
    char correct;
    int used;
    int difficulty;
}
public class SQLiteExample extends Activity {

    LinearLayout Linear;
    SQLiteDatabase mydb;
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
        //TextView t0 = new TextView(this);
        //t0.setText("This tutorial covers CREATION, INSERTION, UPDATION AND DELETION USING SQLITE DATABASES.                                                Creating table complete........");
        //Linear.addView(t0);
        //Toast.makeText(getApplicationContext(), "Creating table complete.", Toast.LENGTH_SHORT).show();
        //insertIntoTable();
        //TextView t1 = new TextView(this);
        //t1.setText("Insert into table complete........");
        //Linear.addView(t1);
        //Toast.makeText(getApplicationContext(), "Insert into table complete", Toast.LENGTH_SHORT).show();
        //TextView t2 = new TextView(this);
        //t2.setText("Showing table values............");
        //Linear.addView(t2);
        //showTableValues();
        //Toast.makeText(getApplicationContext(), "Showing table values", Toast.LENGTH_SHORT).show();
        //updateTable();
        //TextView t3 = new TextView(this);
        //t3.setText("Updating table values............");
        //Linear.addView(t3);
        //Toast.makeText(getApplicationContext(), "Updating table values", Toast.LENGTH_SHORT).show();
        //TextView t4 = new TextView(this);
        //t4.setText("Showing table values after updation..........");
        //Linear.addView(t4);
        //Toast.makeText(getApplicationContext(), "Showing table values after updation.", Toast.LENGTH_SHORT).show();
        //showTableValues();
        //deleteValues(3);
        //TextView t5 = new TextView(this);
        //t5.setText("Deleting table values..........");
        //Linear.addView(t5);
        //Toast.makeText(getApplicationContext(), "Deleting table values", Toast.LENGTH_SHORT).show();
        //TextView t6 = new TextView(this);
        //t6.setText("Showing table values after deletion.........");
        //Linear.addView(t6);
        //deleteValues(1);
        Question q = new Question();
        q.question = "What is 5 + 5?";
        q.answer_A = "1";
        q.answer_B = "3";
        q.answer_C = "432";
        q.answer_D = "10";
        q.correct = 'D';
        q.used = 0;
        q.difficulty = 1;

        insertOneIntoTable(q);
        //insertOneIntoTable("Cameron", "Purdue");
        //Toast.makeText(getApplicationContext(), "Showing table values after deletion.", Toast.LENGTH_SHORT).show();
        showTableValues();
        //setColor(t0);
        //setColor(t1);
        //setColor(t2);
        //setColor(t3);
        //setColor(t4);
        //setColor(t5);
        //setColor(t6);
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
            mydb.execSQL("CREATE TABLE IF  NOT EXISTS "+ TABLE +" (ID INTEGER PRIMARY KEY, QUESTION TEXT, ANSWER_A TEXT, ANSWER_B TEXT, ANSWER_C TEXT, ANSWER_D TEXT, CORRECT CHARACTER, USED INTEGER, DIFFICULTY INTEGER );");
            mydb.close();
        }catch(Exception e){
            Toast.makeText(getApplicationContext(), "Error in creating table", Toast.LENGTH_LONG);
        }
    }
    // THIS FUNCTION INSERTS DATA TO THE DATABASE
    public void insertOneIntoTable(Question q){
        try{
            mydb = openOrCreateDatabase(DBNAME, Context.MODE_PRIVATE,null);
            mydb.execSQL("INSERT INTO " + TABLE + "(QUESTION, ANSWER_A, ANSWER_B, ANSWER_C, ANSWER_D, CORRECT, USED, DIFFICULTY)" +
                    " VALUES('"+q.question+"','"+q.answer_A+"','"+q.answer_B+"','"+q.answer_C+"','"+q.answer_D+"','"+q.correct+"','"+q.used+"','"+q.difficulty+"')");
            mydb.close();
        }catch(Exception e){
            Toast.makeText(getApplicationContext(), "Error in inserting into table", Toast.LENGTH_LONG);
        }
    }

    public void insertIntoTable(){
        try{
            mydb = openOrCreateDatabase(DBNAME, Context.MODE_PRIVATE,null);
            mydb.execSQL("INSERT INTO " + TABLE + "(NAME, PLACE) VALUES('CODERZHEAVEN','GREAT INDIA')");
            mydb.execSQL("INSERT INTO " + TABLE + "(NAME, PLACE) VALUES('ANTHONY','USA')");
            mydb.execSQL("INSERT INTO " + TABLE + "(NAME, PLACE) VALUES('SHUING','JAPAN')");
            mydb.execSQL("INSERT INTO " + TABLE + "(NAME, PLACE) VALUES('JAMES','INDIA')");
            mydb.execSQL("INSERT INTO " + TABLE + "(NAME, PLACE) VALUES('SOORYA','INDIA')");
            mydb.execSQL("INSERT INTO " + TABLE + "(NAME, PLACE) VALUES('MALIK','INDIA')");
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

            TextView t = new TextView(this);
            t.setText("========================================");
            //Linear.removeAllViews();
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


                    final TextView id_  = new TextView(this);
                    final TextView question_ = new TextView(this);
                    final TextView answer_a_ = new TextView(this);
                    final TextView answer_b_ = new TextView(this);
                    final TextView answer_c_ = new TextView(this);
                    final TextView answer_d_ = new TextView(this);
                    final TextView correct_ = new TextView(this);
                    final TextView used_ = new TextView(this);
                    final TextView difficulty_ = new TextView(this);
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

                    System.out.println("QUESTION " + allrows.getString(cindex) + " ANSWER_A : "+ allrows.getString(cindex1) + " ANSWER_B : "+ allrows.getString(cindex2)
                                  + " ANSWER_C : "+ allrows.getString(cindex3) + " ANSWER_D : "+ allrows.getString(cindex4) + " CORRECT : "+ allrows.getString(cindex5)
                                  + " USED : "+ allrows.getString(cindex6) + " DIFFICULTY : "+ allrows.getString(cindex7));
                    System.out.println("ID : "+ ID  + " || QUESTION " + QUESTION + "|| ANSWER_A : "+ ANSWER_A + "|| ANSWER_B : "+ ANSWER_B +
                                       "|| ANSWER_C : "+ ANSWER_C + "|| ANSWER_D : "+ ANSWER_D + "|| CORRECT : "+ CORRECT + "|| USED : "+ USED +
                                       "|| DIFFICULTY : "+DIFFICULTY);

                    id_.setText("ID : " + ID);
                    id_row.addView(id_);
                    Linear.addView(id_row);
                    question_.setText("QUESTION : "+QUESTION);
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