package com.activities.upgrade;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.UpGrade.ClassListManager;
import com.UpGrade.UpGradeClass;

public class LinkGradeSource extends ActionBarActivity {
	
	ArrayList<UpGradeClass> list;
	ClassListManager classManager;
	String name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_link_grade_source);

		Intent i = this.getIntent();
		name = i.getStringExtra("class_name");
		
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
		
		if (link.matches("")) {
		    Toast.makeText(this, "You did not enter a link", Toast.LENGTH_SHORT).show();
		    return;
		}
		if(numberText.getText().toString().length()<1 ){
			Toast.makeText(this, "You did not enter a number", Toast.LENGTH_SHORT).show();
		    return;
		}
		
		
		UpGradeClass newClass = new UpGradeClass(name, link, number);
		classManager.addClass(newClass);
		
		Intent i = new Intent(getApplicationContext(),MainActivity.class);
		startActivity(i);
		
	}

	

}
