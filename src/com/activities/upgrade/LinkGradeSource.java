package com.activities.upgrade;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.UpGrade.ClassListManager;
import com.UpGrade.UpGradeClass;

public class LinkGradeSource extends ActionBarActivity {
	
	ArrayList<UpGradeClass> list;
	ClassListManager classManager;
	int position;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_link_grade_source);
		
		Intent i = this.getIntent();
		position = i.getIntExtra("position", 0);
		
		classManager = new ClassListManager(this.getApplicationContext());
		
		list = classManager.getList();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.link_grade_source, menu);
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
	
	/*
	 * Click response for go button
	 */
	public void onClick(View v){
		
		EditText nameText = (EditText)findViewById(R.id.link_editText);
		String link = nameText.getText().toString();
		
		EditText numberText = (EditText)findViewById(R.id.number_editText);
		int number = Integer.parseInt(numberText.getText().toString());
		
		UpGradeClass toChange = list.get(position);
		toChange.setGradeSourceLink(link);
		toChange.setGradeSourceNumber(number);
		
		classManager.saveToInternalStorage(list);
		Intent i = new Intent(getApplicationContext(),MainActivity.class);
		startActivity(i);
		
	}

	

}
