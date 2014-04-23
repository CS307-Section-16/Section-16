package com.example.section_16;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class HighScoreScreen extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_high_score_screen);
		this.setScores();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.high_score_screen, menu);
		return true;
	}
	
	public void startBack(View view) {
		Intent back = new Intent(this, MainActivity.class);
		startActivity(back);
	}

	public void setScores(){
		MainActivity.datasource.open();
		Score scores[] = MainActivity.datasource.getScores();
		MainActivity.datasource.close();
		//TextView players[] = new TextView[5];
		//TextView scoreText[] = new TextView[5];
		
		/*players[0] = (TextView) findViewById(R.id.textView1);
		players[1] = (TextView) findViewById(R.id.textView2);
		players[2] = (TextView) findViewById(R.id.textView3);
		players[3] = (TextView) findViewById(R.id.textView4);
		players[4] = (TextView) findViewById(R.id.textView5);
		scoreText[0] = (TextView) findViewById(R.id.TextView11);
		scoreText[1] = (TextView) findViewById(R.id.TextView10);
		scoreText[2] = (TextView) findViewById(R.id.TextView09);
		scoreText[3] = (TextView) findViewById(R.id.TextView08);
		scoreText[4] = (TextView) findViewById(R.id.TextView07); */
		TextView player1 = (TextView) findViewById(R.id.textView1);
		TextView player2 = (TextView) findViewById(R.id.textView2);
		TextView player3 = (TextView) findViewById(R.id.textView3);
		TextView player4 = (TextView) findViewById(R.id.textView4);
		TextView player5 = (TextView) findViewById(R.id.textView5);
		TextView score1 = (TextView) findViewById(R.id.TextView11);
		TextView score2 = (TextView) findViewById(R.id.TextView10);
		TextView score3 = (TextView) findViewById(R.id.TextView09);
		TextView score4 = (TextView) findViewById(R.id.TextView08);
		TextView score5 = (TextView) findViewById(R.id.TextView07); 
		
		player1.setText(scores[0].name);
		player2.setText(scores[1].name);
		player3.setText(scores[2].name);
		player4.setText(scores[3].name);
		player5.setText(scores[4].name);
		score1.setText(String.valueOf(scores[0].score));
		score2.setText(String.valueOf(scores[1].score));
		score3.setText(String.valueOf(scores[2].score));
		score4.setText(String.valueOf(scores[3].score));
		score5.setText(String.valueOf(scores[4].score));
		
		/*for(int i=0; i<5; i++){
			if(scores[i] == null){
				scores[i].name = "----------";
				scores[i].score = 0;
			}
			players[i].setText(scores[i].name);
			scoreText[i].setText(scores[i].score);
		}*/
		
	}
}
	
