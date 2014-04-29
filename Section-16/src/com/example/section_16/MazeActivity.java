package com.example.section_16;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import java.io.*;



public class MazeActivity extends Activity implements OnClickListener, Serializable {

	private static final int ANSWER_RESPONSE = 0;
	private final String savefile = "maze.ser";
	
	DrawView drawview = null;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		drawview = new DrawView(this);
		setContentView(R.layout.activity_maze);
		

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

		RelativeLayout dpadButt = (RelativeLayout)findViewById(R.id.dpad_layout);	
		dpadButt.addView(drawview);
		anchor.bringToFront();
		up.bringToFront();
		right.bringToFront();
		down.bringToFront();
		left.bringToFront();

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
		if(!MazeCell.a[y1][x1].isWall()){
			MazeCell.playerPos.y = y1;
			MazeCell.playerPos.x = x1;
			MazeCell.a[y0][x0].player = false;
			MazeCell.a[y1][x1].player = true;
			if(MazeCell.a[y1][x1].obstacle){
				Intent question = new Intent(this, QuestionIntent.class);
				startActivityForResult(question, ANSWER_RESPONSE);
			}
			drawview.invalidate();
		}
	}
	
	@Override
	public void onClick(View v) {
		int b = v.getId();
		
		int x = MazeCell.playerPos.x;
		int y = MazeCell.playerPos.y;
		
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
	            	
	            	int x = MazeCell.playerPos.x;
	        		int y = MazeCell.playerPos.y;
	        		MazeCell.a[y][x].obstacle = false;
	            	
	            }else if(((myValue=data.getStringExtra("0"))!=null)){
	            	
					Intent question = new Intent(this, QuestionIntent.class);
					startActivityForResult(question, ANSWER_RESPONSE);
	            	
	            }
	          }
	      }
	}
	public void saveCurrentState(){
		try {
			FileOutputStream fos = new FileOutputStream(savefile);
			ObjectOutputStream out = new ObjectOutputStream(fos);
			out.writeObject(MazeCell.a);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	public void loadCurrentState(){
		try{
			FileInputStream fis = new FileInputStream(savefile);
			ObjectInputStream in = new ObjectInputStream(fis);
			MazeCell.a = (MazeCell[][]) in.readObject();
		}catch(IOException e){
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
}
