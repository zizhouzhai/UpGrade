package com.activities.upgrade;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.UpGrade.ClassListManager;
import com.UpGrade.UpGradeClass;
import com.UpGrade.ListAdapters.GradeListAdapter;

public class ClassDisplay extends ActionBarActivity {

	private ListView listView1;
	private ProgressBar pBar;
	private String url;
	private int number;
	private ArrayList<String> sectionNames;
	private ArrayList<String> sectionPercents;
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// get rid of the action bar.
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_class_display);
		
		context = this;

		// set up private variables
		sectionNames = new ArrayList<String>();
		sectionPercents = new ArrayList<String>();
		ClassListManager classManager = new ClassListManager(this);

		Intent i = this.getIntent();
		int position = i.getIntExtra("position", 0);

		// find the views
		pBar = (ProgressBar) findViewById(R.id.progressBar1);
		listView1 = (ListView) findViewById(R.id.listView1);

		// get classlist
		ArrayList<UpGradeClass> list = classManager.readFromInternalStorage();

		// get class
		UpGradeClass currentClass = list.get(position);

		// get class info
		url = currentClass.getGradeSourceLink();
		number = currentClass.getGradeSourceNumber();

		new parseGradeSource().execute();

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
			Document doc;
			try {
				doc = Jsoup.connect(url).get();
				Elements tables = doc.select("table");
				System.out.println(number);

				for (Element table : tables) {

					int current;

					for (Element row : table.select("tr")) {

						// get the first columns value
						Elements columns = row.select("td");

						// get rid of other tables
						if (columns.size() < 1) {
							continue;
						}

						String secret_number = columns.get(0).text();

						// get the section names
						if (secret_number.equals("Secret Number")) {
							for (Element column : columns) {
								if(!(column.text().equals("Secret Number")))
								sectionNames.add(column.text());
							}
						}

						// stringBuilder.append(secret_number);
						try {
							current = Integer.parseInt(secret_number);
						} catch (NumberFormatException e) {
							continue;
						}

						// if equals, then this is correct row.
						if (current == number) {
							for (Element column : columns) {
								String columnText = column.text();
								//check if it is percent column
								if (columnText.contains("%")) {
									sectionPercents.add(column.text());

								}
							}
							return null;
						}
					}
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;

		}

		protected void onPostExecute(Void result) {
			pBar.setVisibility(View.INVISIBLE);
			listView1.setVisibility(View.VISIBLE);
			
			//set up listVeiw1
			GradeListAdapter gAdapter = new GradeListAdapter(context,
					R.layout.grade_list_view,sectionNames,sectionPercents);
			listView1.setAdapter(gAdapter);
			
			
			super.onPostExecute(result);
		}

	}

}
