package com.activities.upgrade;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import com.UpGrade.ListAdapters.ClassListAdapter;
import com.UpGrade.ClassListManager;
import com.UpGrade.UpGradeClass;

public class MainActivity extends ActionBarActivity {

	ArrayList<UpGradeClass> list;
	ClassListAdapter adapter;
	ClassListManager classManager;
	ListView classList;
	Context c;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// get rid of the action bar.
		//this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		/*this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);*/

		setContentView(R.layout.fragment_main);

		classManager = new ClassListManager(this.getApplicationContext());

		list = classManager.readFromInternalStorage();

		c = this;

		// get the list
		adapter = new ClassListAdapter(this, R.layout.class_list_view, list);
		classList = (ListView) findViewById(R.id.class_list);
		classList.setAdapter(adapter);
		classList.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {

				// if gradeSourceLink already exists, go parse it.
				if (list.get(position).getGradeSourceLink() != null) {
					Intent n = new Intent(getApplicationContext(),
							ClassDisplay.class);
					n.putExtra("position", position);
					startActivity(n);
				}

				//go to page to setup link.
				else {
					Intent n = new Intent(getApplicationContext(),LinkGradeSource.class);
					n.putExtra("position" , position);
					startActivity(n);
							
				}
			}

		});

	}

	// creates AlertDialog when delete button is pressed
	private AlertDialog AskOption(final View v) {
		AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
				// set message, title, and icon
				.setTitle("Delete")
				.setMessage("Do you want to Delete")

				.setPositiveButton("Delete",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int whichButton) {

								// delete the butotn
								ImageButton deleteButton = (ImageButton) v
										.findViewById(R.id.class_edit);
								Integer pos = (Integer) deleteButton.getTag();
								int posInt = pos.intValue();
								classManager.deleteClass(posInt);

								// refresh the listview
								list = classManager.readFromInternalStorage();
								adapter = new ClassListAdapter(c,
										R.layout.class_list_view, list);
								classList.setAdapter(adapter);

								dialog.dismiss();
							}

						})

				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {

								dialog.dismiss();

							}
						}).create();
		return myQuittingDialogBox;

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

	// onClick response to delete button
	public void deleteClass(View v) {

		AlertDialog diaBox = AskOption(v);
		diaBox.show();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	public void addClass(View v) {

		Intent i = new Intent(this, AddClassActivity.class);
		startActivity(i);

	}

}
