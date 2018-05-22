package lancerpoint.gui;

import java.util.ArrayList;
import java.util.List;

import com.lancerpoint.R;

import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends Activity {

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	CustomDrawerAdapter adapter;

	List<DrawerItem> dataList;

	private Bundle bundle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity_layout);
		//setTheme(android.R.style.Theme_Black);
		
		// get values from the Login Activity
		bundle = getIntent().getExtras();
		
		// Initializing
		dataList = new ArrayList<DrawerItem>();
		mTitle = mDrawerTitle = getTitle();
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

		// Add Drawer Item to dataList
		dataList.add(new DrawerItem("Registration Date"));
		dataList.add(new DrawerItem("Transcript"));
		dataList.add(new DrawerItem("Add/Drop Classes"));
		dataList.add(new DrawerItem("Schedule"));
		
		adapter = new CustomDrawerAdapter(this, R.layout.custom_drawer_item, dataList);

		mDrawerList.setAdapter(adapter);
		//mDrawerList.setDivider(divider);
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			
			Bundle args = new Bundle();
			Fragment fragment = new WelcomeFragment();
			
			args.putString(WelcomeFragment.STUDENT_NAME, bundle.getString(WelcomeFragment.STUDENT_NAME));
			
			fragment.setArguments(args);
			
			FragmentManager frgManager = getFragmentManager();
			frgManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void SelectItem(int possition) {

		Fragment fragment = null;

		Bundle args = new Bundle();
		
		switch (possition) {
		case 0:
			fragment = new RegistrationDateFragment();
			break;
		case 1:
			fragment = new TranscriptFragment();
			break;
		case 2:
			//setTheme(android.R.style.Theme_Black);
			fragment = new AddDropClassesFragment();
			break;
		case 3:
			fragment = new MyScheduleFragment();
			break;
		default:
			break;
		}
		
		args.putString("username", bundle.getString("username"));
		args.putString("password", bundle.getString("password"));
		
		fragment.setArguments(args);
		
		FragmentManager frgManager = getFragmentManager();
		frgManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

		mDrawerList.setItemChecked(possition, true);
		setTitle(dataList.get(possition).getItemName());
		mDrawerLayout.closeDrawer(mDrawerList);

	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggles
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// The action bar home/up action should open or close the drawer.
		// ActionBarDrawerToggle will take care of this.
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		return false;
	}

	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			SelectItem(position);
		}
	}

	@Override
	public void onBackPressed() {
		final Dialog closingDialog = new Dialog(this);
		
		closingDialog.setContentView(R.layout.closing_dialog);
		closingDialog.setCancelable(false);
		closingDialog.setTitle("Log Out");
		
		final Button btnYes = (Button) closingDialog.findViewById(R.id.btnYes);
		final Button btnNo = (Button) closingDialog.findViewById(R.id.btnNo);
		
		btnNo.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				closingDialog.cancel();
			}
		});
		
		btnYes.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(MainActivity.this, LoginActivity.class));
				finish();
			}
		});
		
		closingDialog.show();
	}
}