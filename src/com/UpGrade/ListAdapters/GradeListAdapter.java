package com.UpGrade.ListAdapters;

import java.util.List;

import com.activities.upgrade.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class GradeListAdapter extends ArrayAdapter<String>{
	
	private List<String> names;
	private List<String> grades;
	private int layoutResourceId;
	private Context context;

	public GradeListAdapter(Context context, int layoutResourceId, 
			List<String> names, List<String> grades) {
		super(context, layoutResourceId,grades);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.names = names;
		this.grades = grades;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		
		View row = convertView;
		LayoutInflater inflater = ((Activity) context).getLayoutInflater();
		row = inflater.inflate(layoutResourceId, parent, false);
		
		TextView name = (TextView)row.findViewById(R.id.section_name);
		TextView grade = (TextView)row.findViewById(R.id.section_grade);
		name.setText(names.get(position+1));
		grade.setText(grades.get(position));
		return row;
		
	}
}
