package com.activities.upgrade;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.UpGrade.ClassListManager;
import com.UpGrade.UpGradeClass;
import com.UpGrade.ListAdapters.GradeListAdapter;

public class ClassDisplay extends ActionBarActivity {

	private ListView listView1;
	private ProgressBar pBar;
	private ArrayList<String> sectionNames;
	private ArrayList<String> sectionPercents;
	private Context context;
	private UpGradeClass currentClass;
	private SwipeRefreshLayout swipeRefresh;
	private ClassListManager classListManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// get rid of the action bar.
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		/*this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);*/

		setContentView(R.layout.activity_class_display);

		context = this;
		pBar = (ProgressBar) findViewById(R.id.progressBar1);
		listView1 = (ListView) findViewById(R.id.listView1);
		swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_container);


		// set up private variables

		classListManager = new ClassListManager(this);
		// get classlist
		ArrayList<UpGradeClass> list = classListManager.readFromInternalStorage();

		Intent i = this.getIntent();
		int position = i.getIntExtra("position", 0);
		// get class
		currentClass = list.get(position);
		sectionNames = currentClass.getSectionNames();
		sectionPercents = currentClass.getSectionPercents();
		GradeListAdapter gAdapter = new GradeListAdapter(context,
				R.layout.grade_list_view, sectionNames, sectionPercents);
		listView1.setAdapter(gAdapter);
		listView1.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Intent i = new Intent(getApplicationContext(),GradeSectionDetailed.class);
				i.putExtra("gradeSource_link",currentClass.getGradeSourceLink());
				TextView section_name = (TextView)view.findViewById(R.id.section_name);
				TextView section_grade = (TextView)view.findViewById(R.id.section_grade);
				i.putExtra("section_name",section_name.getText().toString());
				
				String toHighlightString = section_grade.getText().toString();
				toHighlightString = toHighlightString.replace("%", "");
				i.putExtra("toHighlight", (int)(Double.parseDouble(toHighlightString)));
				startActivity(i);
			}
			
			
		});


		// find the views
		swipeRefresh
				.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
					@Override
					public void onRefresh() {
						swipeRefresh.setRefreshing(true);
						pBar.setVisibility(View.VISIBLE);
						listView1.setVisibility(View.INVISIBLE);

						new parseGradeSource().execute();
					}
				});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.class_display, menu);
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

	public class parseGradeSource extends AsyncTask<Void, Void, Void> {

		String response;

		@Override
		protected Void doInBackground(Void... params) {

			classListManager.updateClassinList(currentClass.updateClass());
			//currentClass.updateClass();

			return null;

		}

		protected void onPostExecute(Void result) {
			pBar.setVisibility(View.INVISIBLE);
			listView1.setVisibility(View.VISIBLE);
			

			sectionNames = currentClass.getSectionNames();
			sectionPercents = currentClass.getSectionPercents();

			// set up listVeiw1
			GradeListAdapter gAdapter = new GradeListAdapter(context,
					R.layout.grade_list_view, sectionNames, sectionPercents);
			listView1.setAdapter(gAdapter);

			swipeRefresh.setRefreshing(false);
			
			super.onPostExecute(result);
		}

	}

}
