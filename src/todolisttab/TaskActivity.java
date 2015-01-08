package com.myexample.todolisttab;

import java.util.ArrayList;
import java.util.List;

import com.myexample.createactivities.CreateTaskActivity;
import com.myexample.model.Category;
import com.myexample.model.Task;
import com.myexample.sqlite.CustomListAdapter;
import com.myexample.sqlite.CustomTaskAdapter;
import com.myexample.sqlite.DatabaseHelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;

public class TaskActivity extends Activity{
	Button button1;
	DatabaseHelper db;
	ListView listview1;
	ArrayList<Task> selectedList;
	int selectedValue;
	Bundle dataBundle;
	ArrayList<Task>checkList = new ArrayList<Task>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task);
		
		db = new DatabaseHelper(this);
		ArrayList<Task>arrayList = new ArrayList<Task>();
		checkList = arrayList;
		listview1 = (ListView)findViewById(R.id.listView1);
		List<Task> taskList = db.getAllTasks();
		for(Task tsk: taskList){
			arrayList.add(tsk);
		}
		if(arrayList.isEmpty()){
			//do something when the array is empty (change this to something else)
			db.close();
		}
		else{
			final CheckBox check = (CheckBox)findViewById(R.id.checkBox1);
			CustomTaskAdapter adapter = new CustomTaskAdapter(this, arrayList);
			selectedList = arrayList;
			listview1.setAdapter(adapter);
		
			listview1.setOnItemClickListener(new AdapterView.OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					
				}
			
			});
		}
	}
	protected void onResume(){
		super.onResume();
		List<Task> taskList = db.getAllTasks();
		ArrayList<Task> arrayList1 = new ArrayList<Task>();
		checkList = arrayList1;
		for(Task tsk: taskList){
			arrayList1.add(tsk);
		}
		if(arrayList1.isEmpty()){
			//do something when the array is empty (change this to something else)
			db.close();
		}
		else{
			CustomTaskAdapter adapter = new CustomTaskAdapter(this, arrayList1);
			listview1.setAdapter(adapter);
		
			listview1.setOnItemClickListener(new AdapterView.OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
				}
			
			});
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.task, menu);
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
	public void addNewTask(View view){
		Intent addNew = new Intent(this, CreateTaskActivity.class);
		startActivity(addNew);
        
   }
}
