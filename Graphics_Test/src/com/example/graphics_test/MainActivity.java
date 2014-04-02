package com.example.graphics_test;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {
     /** Called when the activity is first created. */
     @Override
     public void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(new MyView(this));
     }

     public class MyView extends View {
         public MyView(Context context) {
              super(context);
              // TODO Auto-generated constructor stub
         }

         @Override
         protected void onDraw(Canvas canvas) {
            // TODO Auto-generated method stub
        	 
        	 
        	int[][] a = {{1,2,1,0,1,1},
        				 {1,0,1,0,0,1},
        				 {1,0,1,1,0,1},
        				 {1,0,3,0,0,1},
        				 {1,1,1,1,1,1}};
            super.onDraw(canvas);
            int x = getWidth();
            int y = getHeight();
            int radius;
            radius = 100;
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.WHITE);
            canvas.drawPaint(paint);
            // Use Color.parseColor to define HTML colors
            //paint.setColor(Color.parseColor("#CD5C5C"));
            //canvas.drawRect(0, 0, x, y, paint);
            //paint.setColor(Color.parseColor("#F0EAD6"));
            for(int i = 0; i < a.length; i++){
            	for(int j = 0; j < a[i].length; j++){
            		if(a[i][j] == 1)
            			paint.setColor(Color.parseColor("#000000"));
            		else if(a[i][j] == 0)
            			paint.setColor(Color.parseColor("#FFFFFF"));
            		else if(a[i][j] == 2)
            			paint.setColor(Color.parseColor("#00FF00"));
            		else if(a[i][j] == 3)
            			paint.setColor(Color.parseColor("#FF0000"));
                	canvas.drawRect(j*100,i*100,(j*100)+100,(i*100)+100, paint);
                	
                }
            }
            
        }
         
         protected void drawGrid(Canvas canvas){
        	 
         }
     }
}