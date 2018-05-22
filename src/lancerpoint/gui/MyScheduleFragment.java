package lancerpoint.gui;

import java.util.ArrayList;

import java.util.HashMap;

import com.lancerpoint.R;

import lancerpoint.site.component.Login;
import lancerpoint.site.component.ViewSchedule;
import lancerpoint.site.component.ViewRegistration;
import lancerpoint.utility.ScheduledCourse;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MyScheduleFragment extends Fragment {

	TextView tvItemName;

	public MyScheduleFragment() {

	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view=inflater.inflate(R.layout.my_schedule_layout ,container, false);
		
		tvItemName=(TextView)view.findViewById(R.id.txt_schedule);
		
		tvItemName.setText("Loading...");
		
		strUserName = getArguments().getString("username");
		strPassword = getArguments().getString("password");
		
		return view;
	}

	String strUserName, strPassword;
	String schedule_term = "";

	public void getSchedule() {
		new Log().execute(null, null);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		
		getSchedule();
	}
	private class Log extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... arg0) {
			
			String loginTicket = Login.getLoginTicket(strUserName, strPassword);
			
			if(loginTicket != null){
				
	        	HashMap<String, String> terms = ViewRegistration.getRegistrationTerms(strUserName, strPassword);
	            terms = ViewSchedule.getScheduleTerms(strUserName, strPassword); //I think this is the same as registration terms...    
				ArrayList<ScheduledCourse> schedule = ViewSchedule.getSchedule(strUserName, strPassword, terms.get("Fall 2014"));
	        	
	        	for (ScheduledCourse g : schedule){
	        		schedule_term += g + "\r\n\n";
	        	}
				
				try {
					getActivity().runOnUiThread(new Runnable() {
						public void run() {
							
					    	tvItemName.setText((CharSequence) schedule_term);
					
						}
					});
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
						
			}else{
				
			}
		
			return null;
		}
	}

}
