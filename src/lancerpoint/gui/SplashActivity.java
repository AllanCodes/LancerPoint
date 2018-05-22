package lancerpoint.gui;

import java.util.Timer;
import java.util.TimerTask;

import com.lancerpoint.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SplashActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_layout);
		
		TimerTask task = new TimerTask(){
			public void run() {
				startActivity(new Intent(SplashActivity.this, LoginActivity.class));
				finish();
			};
		};
		
		Timer opening = new Timer();
		opening.schedule(task, 3700);
	}
}