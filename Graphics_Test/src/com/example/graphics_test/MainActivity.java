package com.example.graphics_test;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.Menu;
import android.view.View;



public class MainActivity extends Activity {
	
	MazeCell a[][] = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new MyView(this));
		
		a = new MazeCell[9][9];
		
		int[][] a1 = {{1,2,1,1,1,1,1,4,1},
				 	  {1,0,1,0,1,0,1,0,1},
				 	  {1,0,0,0,1,3,0,0,1},
				 	  {1,0,1,1,1,0,1,1,1},
				 	  {1,0,3,0,0,3,0,1,1},
				 	  {1,1,0,1,1,1,0,3,1},
				 	  {1,0,3,0,0,1,1,0,1},
				 	  {1,0,1,1,0,1,0,0,1},
				 	  {1,1,1,1,1,1,1,1,1},
				 	  };
		
		for(int i = 0; i< a.length; i++){
			for(int j = 0; j<a[i].length; j++){
				a[i][j] = new MazeCell(0,0,null);
				if(a1[i][j] == 0){
					a[i][j].pathWay = true;
				}else if(a1[i][j] == 1){
					a[i][j].wall = true;
				}else if(a1[i][j] == 2){
					a[i][j].isPerson = true;
				}else if(a1[i][j] == 3){
					a[i][j].obstacle = true;
				}else if(a1[i][j] == 4){
					a[i][j].end = true;
				}
			}
		}	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public class MyView extends View {
        public MyView(Context context) {
             super(context);
             // TODO Auto-generated constructor stub
        }

        @Override
        protected void onDraw(Canvas canvas) {
           // TODO Auto-generated method stub
           super.onDraw(canvas);
           int x = getWidth();
           int y = getHeight();
          
           Paint paint = new Paint();
           paint.setStyle(Paint.Style.FILL);
           paint.setColor(Color.WHITE);
           canvas.drawPaint(paint);

           /*for(int i = 0; i < a.length; i++){
           	for(int j = 0; j < a[i].length; j++){
           		if(a[i][j].wall == true)
           			paint.setColor(Color.parseColor("#000000"));
           		else if(a[i][j].pathWay == true)
           			paint.setColor(Color.parseColor("#FFFFFF"));
           		else if(a[i][j].isPerson == true)
           			paint.setColor(Color.parseColor("#00FF00"));
           		else if(a[i][j].obstacle == true)
           			paint.setColor(Color.parseColor("#FF0000"));
               	canvas.drawRect(j*(x/a[i].length),i*(y/a.length) ,j*(x/a[i].length)+x/a[i].length ,i*(y/a.length)+y/a.length, paint);
               	
               }
           }*/
           
           int startX = x/2 - y/2;
           for(int j = 0; j < a[1].length; j++){
              	for(int i = 0; i < a.length; i++){
              		if(a[i][j].wall == true)
              			paint.setColor(Color.parseColor("#000000"));
              		else if(a[i][j].pathWay == true)
              			paint.setColor(Color.parseColor("#FFFFFF"));
              		else if(a[i][j].isPerson == true)
              			paint.setColor(Color.parseColor("#00FF00"));
              		else if(a[i][j].obstacle == true)
              			paint.setColor(Color.parseColor("#FF0000"));
              		else if(a[i][j].end == true)
              			paint.setColor(Color.parseColor("#F34AB2"));
              		
              		
              		//canvas.drawRect(j*(x/a[i].length),i*(y/a.length) ,j*(x/a[i].length)+x/a[i].length ,i*(y/a.length)+y/a.length, paint);
                  	canvas.drawRect(startX + j*(y/a[i].length),i*(y/a.length) ,startX + j*(y/a[i].length)+y/a[i].length ,i*(y/a.length)+y/a.length, paint);
                    
                  }
              }		
       }
    }
}
