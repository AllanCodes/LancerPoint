package lancerpoint.gui;

import com.lancerpoint.R;

import lancerpoint.site.component.Login;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {

	Button btnLogIn;
	EditText txtUserName;
	EditText txtPassword;
	TextView lblForgot;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in_layout);
        
        createWidgets();
        
    }

    private void createWidgets(){
    	
    	btnLogIn = (Button) findViewById(R.id.btnLogIn);
    	txtUserName = (EditText) findViewById(R.id.txtUserName);
    	txtUserName.setSelected(true);
    	txtUserName.requestFocus();
    	txtPassword = (EditText) findViewById(R.id.txtPassword);
    	/* use for debugging and testing out*/
    	txtUserName.setText("");// type your user name
    	txtPassword.setText("");// type your password
 
    	btnLogIn.setOnClickListener(logInListener);
    	
    	lblForgot = (TextView) findViewById(R.id.lblForgot);
    	lblForgot.setOnClickListener(changePasswordListener);
    }

    private View.OnClickListener changePasswordListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View view) {
			try {
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://reset.pasadena.edu/Identify.asp?Action=reset"));
				startActivity(browserIntent);
			} catch (Exception e) {
				
			}
		}
	};
    
    private View.OnClickListener logInListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View view) {
			/* ************ disable button  *************** */
			btnLogIn.setEnabled(false);
			new LogInTask().execute(null, null);
		}

	};
	
	private class LogInTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... arg0) {
			
			final String strPassword = txtPassword.getText().toString();
			final String strUserName = txtUserName.getText().toString();
			
			String loginTicket = Login.getLoginTicket(strUserName, strPassword);
			
			
			// check if the user name and password exist to log in
			if(loginTicket != null){
				final String studentName = Login.getRealName(strUserName, strPassword);
				
				runOnUiThread(new Runnable() {
					public void run() {
						Toast.makeText(getBaseContext(), "You logged in", Toast.LENGTH_SHORT ).show();
						Intent main = new Intent(LoginActivity.this, MainActivity.class);
						
						main.putExtra("username", strUserName);
						main.putExtra("password", strPassword);
						main.putExtra(WelcomeFragment.STUDENT_NAME, studentName);
						
						startActivity(main); // open main activity (Navigation Drawer)
						finish() ;
					}
				});
			}
			else{
				runOnUiThread(new Runnable() {
					public void run() {
						Toast.makeText(getBaseContext(), "Incorrect Password or Username", Toast.LENGTH_LONG).show();
						/* ************ enable button  *************** */
						btnLogIn.setEnabled(true);
					}
				});
			}
			return null;
		}
		
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}