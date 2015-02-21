package com.activities.upgrade;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import com.UpGrade.WebScrapManager;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.utils.XLabels;
import com.github.mikephil.charting.utils.XLabels.XLabelPosition;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

public class GradeSectionDetailed extends Activity {

	BarChart bChart;
	String gradeSource_link;
	String section_name;
	int toHighlight;
	ProgressBar pBar;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	   // this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_grade_section_detailed);
		
		Intent i = this.getIntent();
		gradeSource_link = i.getStringExtra("gradeSource_link");
		section_name = i.getStringExtra("section_name");
		toHighlight = i.getIntExtra("toHighlight", -1);
		
		pBar = (ProgressBar) findViewById(R.id.progressBar);
		bChart = (BarChart)findViewById(R.id.chart);
		bChart.setTouchEnabled(false);
		bChart.setDrawYLabels(false);
		bChart.setDescription("");
		bChart.setUnit(" %");
		bChart.setDrawGridBackground(false);
		bChart.setDrawHorizontalGrid(true);
		bChart.setDrawVerticalGrid(false);
		bChart.setDrawLegend(false);
		bChart.setDrawBarShadow(false);
		bChart.setDrawYValues(false);
		 XLabels xl = bChart.getXLabels();
	        xl.setPosition(XLabelPosition.BOTTOM);
		
		new parseGradeSource().execute();
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.grade_section_detailed, menu);
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

		ArrayList<Integer> results;
		
		@Override
		protected Void doInBackground(Void... params) {

			WebScrapManager scrapManager = new WebScrapManager(gradeSource_link);
			results = scrapManager.parseSection(section_name);
			
			return null;
		}
	
		protected void onPostExecute(Void result) {
		
			int max = Collections.max(results);
			
			int[] valComp = new int[max+1];
			
			//
			for(int i = 0; i < results.size();i++){
				valComp[results.get(i)]++;
			}
			
			ArrayList<String> xVals = new ArrayList<String>();
			for(int i = 0; i < max+1; i++){
				xVals.add(Integer.toString(i));
			}
			
			ArrayList<BarEntry> valsComp1 = new ArrayList<BarEntry>();
			for(int i = 0; i < valComp.length;i++){
				valsComp1.add(new BarEntry(valComp[i],i));
			}
			
			BarDataSet setComp = new BarDataSet(valsComp1,"");
			
			ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
			dataSets.add(setComp);
			
			BarData data = new BarData(xVals,dataSets);
			bChart.setData(data);
			bChart.highlightValue(toHighlight, 0);
			bChart.invalidate();
			Toast.makeText(getApplicationContext(), "Finished", Toast.LENGTH_SHORT).show();
			pBar.setVisibility(View.INVISIBLE);
			bChart.setVisibility(View.VISIBLE);
		}
		
	}
}
