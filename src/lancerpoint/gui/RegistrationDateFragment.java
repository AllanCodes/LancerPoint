package lancerpoint.gui;

import java.util.HashMap;

import com.lancerpoint.R;

import lancerpoint.site.component.Login;
import lancerpoint.site.component.ViewRegistration;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class RegistrationDateFragment extends Fragment {

	TextView tvItemName;

	public RegistrationDateFragment(){
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view=inflater.inflate(R.layout.registration_date_layout ,container, false);
		
		tvItemName=(TextView)view.findViewById(R.id.txt_registration_date);
		
		tvItemName.setText("Loading...");
		
		strUserName = getArguments().getString("username");
		strPassword = getArguments().getString("password");
		
		return view;
	}

	public void getRegistrationDate(){
		new Log().execute(null, null);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		
		getRegistrationDate();
	};
	
	String strPassword;
	String strUserName;
	
	private class Log extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... arg0) {
			
			String loginTicket = Login.getLoginTicket(strUserName, strPassword);
			
			if(loginTicket != null){
				
	        	HashMap<String, String> terms = ViewRegistration.getRegistrationTerms(strUserName, strPassword);
				final String registrationDate = ViewRegistration.getRegistrationDate(strUserName, strPassword, terms.get("Spring 2015"));
				
				/* ************* */
				try {
					getActivity().runOnUiThread(new Runnable() {
						public void run() {
							
					    	tvItemName.setText((CharSequence) registrationDate);
					
						}
					});
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				/* ************* */
			}else{
				
			}
		
			return null;
		}
	}
}
