package lancerpoint.gui;

import java.util.ArrayList;

import com.lancerpoint.R;

import lancerpoint.site.component.Login;
import lancerpoint.site.component.ViewTranscript;
import lancerpoint.utility.GradedCourse;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TranscriptFragment extends Fragment {

	TextView tvItemName;

	public TranscriptFragment(){
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view=inflater.inflate(R.layout.transcript_layout,container, false);
		
		tvItemName=(TextView)view.findViewById(R.id.txt_transcript);
		
		tvItemName.setText("Loading...");
		
		strUserName = getArguments().getString("username");
		strPassword = getArguments().getString("password");
		
		return view;
	}

	public void getTranscript(){
		new Log().execute(null, null);
	}
	
	String strPassword;
	String strUserName;
	
	String transcript = "";
	private class Log extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... arg0) {
			
			String loginTicket = Login.getLoginTicket(strUserName, strPassword);
			
			if(loginTicket != null){
				
	        	ArrayList<GradedCourse> gc = ViewTranscript.getTranscriptGrades(strUserName, strPassword);

		        	for (GradedCourse g : gc){
		        		transcript += g + "\r\n\n";
		        	}
		        	
		        	// checks if there is no transcript
		        	if(transcript.equals("null\r\n\n")){
		        		transcript = "No transcript found";
		        	}
				
				try {/* ************* */
					getActivity().runOnUiThread(new Runnable() {
						public void run() {
							
					    	tvItemName.setText((CharSequence) transcript);
					
						}
					});
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}/* ************* */
						
			}else{
				
			}
		
			return null;
		}
	}
	
	@Override
	public void onStart() {
		super.onStart();
		
		getTranscript();
	}
}
