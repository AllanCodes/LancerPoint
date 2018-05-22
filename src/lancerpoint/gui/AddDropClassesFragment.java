package lancerpoint.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.lancerpoint.R;

import lancerpoint.site.component.AddDropClasses;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class AddDropClassesFragment extends Fragment {

	ArrayList<EditText> txtBoxes;
	
	private Button btnDrop;
	private Button btnAdd;
	private Spinner spTerms;
	
	public AddDropClassesFragment(){
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.add_drop_classes_layout , container, false);
		
		btnDrop = (Button) view.findViewById(R.id.btn_drop);
		btnAdd = (Button) view.findViewById(R.id.btn_add);
		
		spTerms = (Spinner) view.findViewById(R.id.spinner1);
		
		txtBoxes = new ArrayList<EditText>();
		
		// populate the txtBoxes array with EditText objects
		txtBoxes.add((EditText) view.findViewById(R.id.editText1));
		txtBoxes.add((EditText) view.findViewById(R.id.editText2));
		txtBoxes.add((EditText) view.findViewById(R.id.editText3));
		txtBoxes.add((EditText) view.findViewById(R.id.editText4));
		txtBoxes.add((EditText) view.findViewById(R.id.editText5));
		txtBoxes.add((EditText) view.findViewById(R.id.editText6));
		txtBoxes.add((EditText) view.findViewById(R.id.editText7));
		txtBoxes.add((EditText) view.findViewById(R.id.editText8));
		txtBoxes.add((EditText) view.findViewById(R.id.editText9));
		txtBoxes.add((EditText) view.findViewById(R.id.editText10));
		
		btnAdd.setOnClickListener(addListener);
		btnDrop.setOnClickListener(dropListener);
		
		terms = new ArrayList<String>();
		terms.add("Select Term");
		
		strUserName = getArguments().getString("username");
		strPassword = getArguments().getString("password");
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_expandable_list_item_1, terms);
		spTerms.setAdapter(adapter);
		
		return view;
	}

	private View.OnClickListener addListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			new AddClassesTask().execute(null, null);
			
		}
	};
	
	private View.OnClickListener dropListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {

			new DropClassesTask().execute(null, null);
			
		}
	};
	
	
	private ArrayList<Integer> getCRNArray() {
        ArrayList<Integer> crns = new ArrayList<Integer>();

		// loop through the text boxes
		for (int i = 0; i < txtBoxes.size(); i++) {
			
			// check if the text box is not empty
			if(!txtBoxes.get(i).getText().toString().equals("")){
			    crns.add(Integer.parseInt(txtBoxes.get(i).getText().toString()));
            }
		}

        return crns;
	}
	
	String strPassword;
	String strUserName;
	
	private class AddClassesTask extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... params) {
			ArrayList<Integer> crns = getCRNArray();

            AddDropClasses.addClass(strUserName, strPassword, crns, addTerms.get(spTerms.getSelectedItem().toString()));
            //It's a static access so you don't need to call new. Also you need to get the value from the addTerms HashMap.

			return null;
		}
		
	}
	
	private class DropClassesTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... arg0) {

            AddDropClasses.dropClass(strUserName, strPassword, getCRNArray(), addTerms.get(spTerms.getSelectedItem().toString()));
		
			return null;
		}
	}
	
	public void loadSpinner(){
		new LoadTermsTask().execute(null, null);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		
		loadSpinner();
	}
	
	private List<String> terms;
    private HashMap<String, String> addTerms;
	
	private class LoadTermsTask extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... params) {
			
			addTerms = AddDropClasses.getRegistrationTerms(strUserName, strPassword);
			terms.addAll(addTerms.keySet());
			
			return null;
		}
		
	}
	
}
