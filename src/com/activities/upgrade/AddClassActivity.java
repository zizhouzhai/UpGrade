package com.activities.upgrade;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.UpGrade.ClassListManager;
import com.UpGrade.UpGradeClass;

public class AddClassActivity extends ActionBarActivity {

	ArrayList<UpGradeClass> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// get rid of the action bar.
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		/*this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);*/

		setContentView(R.layout.activity_add_class);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_class, menu);
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

	// method for button click.
	public void addClass(View v) {
		ClassListManager classManager = new ClassListManager(this);

		list = classManager.readFromInternalStorage();

		EditText nameText = (EditText) findViewById(R.id.editText1);
		String name = nameText.getText().toString();

		if (name.matches("")) {
		    Toast.makeText(this, "You did not enter a name", Toast.LENGTH_SHORT).show();
		    return;
		}
		
		Intent i = new Intent(this, LinkGradeSource.class);
		i.putExtra("class_name", name);
		startActivity(i);

	}

}
