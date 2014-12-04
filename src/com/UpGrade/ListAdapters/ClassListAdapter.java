package com.UpGrade.ListAdapters;

import java.util.List;

import com.UpGrade.UpGradeClass;
import com.activities.upgrade.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

public class ClassListAdapter extends ArrayAdapter<UpGradeClass>{
	
	private List<UpGradeClass> items;
	private int layoutResourceId;
	private Context context;

	public ClassListAdapter(Context context, int layoutResourceId, List<UpGradeClass> items) {
		super(context, layoutResourceId,items);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.items = items;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		
		View row = convertView;
		LayoutInflater inflater = ((Activity) context).getLayoutInflater();
		row = inflater.inflate(layoutResourceId, parent, false);
		
		TextView name = (TextView)row.findViewById(R.id.class_name);
		name.setText(items.get(position).getName());
		ImageButton deleteButton = (ImageButton)row.findViewById(R.id.class_edit);
		deleteButton.setTag(position);
		return row;
		
	}
}
