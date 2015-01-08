package com.myexample.sqlite;

import java.util.ArrayList;

import com.myexample.model.Category;
import com.myexample.model.Task;
import com.myexample.todolisttab.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomListAdapter extends ArrayAdapter{
	public CustomListAdapter(Context context, ArrayList<Category> list) {
	       super(context, 0, list);
	    }

	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	       // Get the data item for this position
	       final Category cat = (Category) getItem(position);    
	       // Check if an existing view is being reused, otherwise inflate the view
	       if (convertView == null) {
	          convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_user2, parent, false);
	       }
	       // Lookup view for data population
	       TextView taskName = (TextView) convertView.findViewById(R.id.taskName);
	       TextView description = (TextView) convertView.findViewById(R.id.description);
	       // Populate the data into the template view using the data object
	       taskName.setText(cat.getCategoryName());
	       description.setText(cat.getDescription());
	       
	       // Return the completed view to render on screen
	       return convertView;
	   }
	}
