package game.section_16;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	}
	
	public void initScreen(View view){
		Intent init = new Intent(this, game.section_16.InitializeScreen.class);
		startActivity(init);
	}
	
	public void contScreen(View view){
		Intent cont = new Intent(this, game.section_16.ContinueScreen.class);
		startActivity(cont);
	}
	
	public void settScreen(View view){
		Intent sett = new Intent(this, game.section_16.SettingScreen.class);
		startActivity(sett);
	}

	public void hsScreen(View view){
		Intent hscore = new Intent(this, game.section_16.HighScoresScreen.class);
		startActivity(hscore);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	

}
