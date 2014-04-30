package com.example.section_16;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.*;
import java.lang.Math;



public class MazeActivity extends Activity implements OnClickListener, Serializable {

	private static final int ANSWER_RESPONSE = 0;
	private final String savefile = "maze.ser";

	
	
	public static int reportScore(){
		if(MainActivity.questionsAttempted == 0)
			return 0;
		return (int)(100*Math.pow(MainActivity.questionsAnswered,2)/MainActivity.questionsAttempted);
	}
	
	public void setVisibility(){
		int x = MazeCell.playerPos.x;
		int y = MazeCell.playerPos.y;
		for(int i = -1; i <= 1; i++){
			for(int j = -1; j <=1; j++){
				MainActivity.a[y+i][x+j].visible = true;
			}
		}
	}
	
	DrawView drawview = null;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setVisibility();
		super.onCreate(savedInstanceState);
		drawview = new DrawView(this);
		setContentView(R.layout.activity_maze);

		MainActivity.datasource.open();
		Set s = MainActivity.datasource.getSettings();
		MainActivity.datasource.close();

		if (s.save == 1){
			loadCurrentState();
		}


		View decorView = getWindow().getDecorView();
		// Hide the status bar.
		int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
		decorView.setSystemUiVisibility(uiOptions);

		ActionBar actionBar = getActionBar();
		actionBar.hide();


		Button up = (Button)findViewById(R.id.d_up);	
		Button right = (Button)findViewById(R.id.d_right);	
		Button down = (Button)findViewById(R.id.d_down);	
		Button left = (Button)findViewById(R.id.d_left);	
		View anchor = findViewById(R.id.d_anchor);
		
		TextView sv = (TextView)findViewById(R.id.score_text);
		sv.setText("Score: "+ reportScore());
		
		RelativeLayout dpadButt = (RelativeLayout)findViewById(R.id.dpad_layout);	
		dpadButt.addView(drawview);
		anchor.bringToFront();
		up.bringToFront();
		right.bringToFront();
		down.bringToFront();
		left.bringToFront();
		sv.bringToFront();

		up.setOnClickListener(this);
		right.setOnClickListener(this);
		down.setOnClickListener(this);	
		left.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void handlePlayerMove(int x0, int x1, int y0, int y1){
		if(!MainActivity.a[y1][x1].isWall()){
			MazeCell.playerPos.y = y1;
			MazeCell.playerPos.x = x1;
			MainActivity.a[y0][x0].player = false;
			MainActivity.a[y1][x1].player = true;
			if(MainActivity.a[y1][x1].obstacle){
				Intent question = new Intent(this, QuestionIntent.class);
				startActivityForResult(question, ANSWER_RESPONSE);
			}else if(MainActivity.a[y1][x1].end){
				MainActivity.datasource.open();
				MainActivity.datasource.updateSave(0);
				MainActivity.datasource.close();
				Intent end = new Intent(this, EndActivity.class);
				startActivity(end);
				finish();
			}
			setVisibility();
			drawview.invalidate();
		}
	}

	@Override
	public void onClick(View v) {
		int b = v.getId();

		int x = MazeCell.playerPos.x;
		int y = MazeCell.playerPos.y;

		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(b == R.id.d_up){
			handlePlayerMove(x,x,y,y-1);
		}else if(b == R.id.d_right){
			handlePlayerMove(x,x+1,y,y);
		}else if(b == R.id.d_down){
			handlePlayerMove(x,x,y,y+1);
		}else if(b == R.id.d_left){
			handlePlayerMove(x,x-1,y,y);
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == ANSWER_RESPONSE) {
			if (resultCode == RESULT_OK) {
				String myValue = null;
				if(((myValue=data.getStringExtra("1"))!=null)){
					MainActivity.questionsAttempted++;
					MainActivity.questionsAnswered++;
					int x = MazeCell.playerPos.x;
					int y = MazeCell.playerPos.y;
					MainActivity.a[y][x].obstacle = false;
					getIntent().removeExtra("1");

				}else if(((myValue=data.getStringExtra("0"))!=null)){
					MainActivity.questionsAttempted++;
					Intent question = new Intent(this, QuestionIntent.class);
					startActivityForResult(question, ANSWER_RESPONSE);
					getIntent().removeExtra("0");
				}
			}
		}
		
		TextView sv = (TextView)findViewById(R.id.score_text);
		sv.setText("Score: "+ reportScore());
	}
	@Override
	public void onBackPressed() {
		this.saveCurrentState();
		MainActivity.datasource.open();
		MainActivity.datasource.updateSave(1);
		MainActivity.datasource.close();
		Log.d("startBack", "Save state initialized");
		Intent back = new Intent(this, MainActivity.class);
		startActivity(back);
		finish();
	}

	public void saveCurrentState(){
		try {
			FileOutputStream fos = new FileOutputStream(savefile);
			ObjectOutputStream out = new ObjectOutputStream(fos);
			out.writeObject(MainActivity.a);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	public void loadCurrentState(){
		try{
			FileInputStream fis = new FileInputStream(savefile);
			ObjectInputStream in = new ObjectInputStream(fis);
			MainActivity.a = (MazeCell[][]) in.readObject();
		}catch(IOException e){
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
