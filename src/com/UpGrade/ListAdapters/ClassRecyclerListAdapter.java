package com.UpGrade.ListAdapters;

import java.util.List;

import com.UpGrade.UpGradeClass;
import com.activities.upgrade.ClassDisplay;
import com.activities.upgrade.LinkGradeSource;
import com.activities.upgrade.R;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

public class ClassRecyclerListAdapter extends RecyclerView.Adapter<ClassRecyclerListAdapter.ViewHolder>{

	private static List<UpGradeClass> items;
	private static Context context;

	public ClassRecyclerListAdapter(Context context, int layoutResourceId, List<UpGradeClass> items) {
		ClassRecyclerListAdapter.context = context;
		ClassRecyclerListAdapter.items = items;
	}

	
	public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

		TextView name;
		ImageButton deleteButton;
		
		public ViewHolder(View itemView) {
			super(itemView);
			name = (TextView)itemView.findViewById(R.id.class_name);
			deleteButton = (ImageButton)itemView.findViewById(R.id.class_edit);
			
			itemView.setOnClickListener(this);
		}

		@Override
		public void onClick(View v) {
			if (items.get(this.getPosition()).getGradeSourceLink() != null) {
				Intent n = new Intent(context.getApplicationContext(),
						ClassDisplay.class);
				n.putExtra("position", getPosition());
				context.startActivity(n);
			}

			//go to page to setup link.
			else {
				Intent n = new Intent(context.getApplicationContext(),LinkGradeSource.class);
				n.putExtra("position" , getPosition());
				context.startActivity(n);
						
			}	
			
		}
		
		
	}

	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		return items.size();
	}

	//binding a ViewHolder
	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {

		holder.name.setText(items.get(position).getName());
		holder.deleteButton.setTag(position);
		
	}

	//creating a new ViewHolder
	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
				
		//inflate view
		View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.class_list_view, parent, false);
		
		
		//create viewholder for that view
		ViewHolder vh = new ViewHolder(v);
		
		//return viewholder
		return vh;
	}
	
}
