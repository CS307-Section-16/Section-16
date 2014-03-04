package com.example.section_16;

import com.example.section_16.R;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.View;
import android.widget.RadioGroup;

public class ContinueGame extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_continue_game);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.continue_game, menu);
		return true;
	}
	
	@SuppressWarnings("deprecation")
	public void confirmAnswer(View view){
		RadioGroup rg= (RadioGroup) findViewById(R.id.radiogroup);
		int id = rg.getCheckedRadioButtonId();
		View radioButton = rg.findViewById(id);
		int index = rg.indexOfChild(radioButton);

		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setTitle("Response");
		alertDialog.setMessage(Integer.toString(index));
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
		// here you can add functions
		}
		});
		alertDialog.show();
    }

}
