package com.myexample.sqlite;

import java.util.ArrayList;

import com.myexample.model.Category;
import com.myexample.model.Task;
import com.myexample.todolisttab.R;
import com.myexample.todolisttab.TaskActivity;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class CustomTaskAdapter extends ArrayAdapter{
	DatabaseHelper db;
	View aView;
	Context value;
	boolean checked;
	Task task;
	Editor editor;
	SharedPreferences sharedPrefs;
	public CustomTaskAdapter(Context context, ArrayList<Task> task) {
	       super(context, 0, task);
	       db = new DatabaseHelper(context);
	       value = context;
	       sharedPrefs = context.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE);
	    }

	    @Override
	    public View getView(final int position, View convertView, final ViewGroup parent) {
	       // Get the data item for this position
	       task = (Task) getItem(position);
	       // Check if an existing view is being reused, otherwise inflate the view
	       if (convertView == null) {
  	          convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_user, parent, false);
  	          aView = convertView;
  	       }
	       // Lookup view for data population
	       editor = sharedPrefs.edit();
	       TextView taskName = (TextView) convertView.findViewById(R.id.taskName);
	       TextView catName = (TextView)convertView.findViewById(R.id.textView1);
	       TextView description = (TextView) convertView.findViewById(R.id.description);
	       CheckBox check = (CheckBox)convertView.findViewById(R.id.checkBox1);
	       check.setChecked(sharedPrefs.getBoolean("CheckValue", false));
	       check.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				
				// TODO Auto-generated method stub
				editor.putBoolean("CheckValue", isChecked);
				editor.commit();
				
		        }
	       });
	       check.setChecked(sharedPrefs.getBoolean("CheckValue", false));
	       catName.setText(task.getCategoryName());
	       taskName.setText(task.getTaskName());
		   description.setText(task.getDescription());
	       
	       //get the category id from the task
	       
	       // Populate the data into the template view using the data object
	       	       
	       // Return the completed view to render on screen
	       return convertView;
	   }
	}
