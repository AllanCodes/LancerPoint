package lancerpoint.gui;

import com.lancerpoint.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class WelcomeFragment extends Fragment {
	
	public static final String STUDENT_NAME = "student_name";
	
	TextView tvItemName;
	
	  @Override
	  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    View view = inflater.inflate(R.layout.welcome_layout, container, false);
	    
	    tvItemName=(TextView)view.findViewById(R.id.nameTextView);
	    tvItemName.setText(getArguments().getString(STUDENT_NAME)); //user's name goes here
	    
	    return view;
	  }

}
