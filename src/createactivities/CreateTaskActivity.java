package com.myexample.createactivities;


import java.util.ArrayList;
import java.util.List;

import com.myexample.model.Category;
import com.myexample.model.Task;
import com.myexample.sqlite.DatabaseHelper;
import com.myexample.todolisttab.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class CreateTaskActivity extends Activity implements OnItemSelectedListener{
	EditText taskName;
	EditText description;
	Spinner category;
	DatabaseHelper db;
	List<Category> spinnerList;
	int selectedCategory;
	String selectedName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_task);
		
		db = new DatabaseHelper(this);
		Button submit = (Button)findViewById(R.id.submitbutton);
		Button cancel = (Button)findViewById(R.id.cancelbutton);
		Button button1 = (Button)findViewById(R.id.button1);
		taskName = (EditText)findViewById(R.id.editText1);
		description = (EditText)findViewById(R.id.editText2);
		category = (Spinner) findViewById(R.id.spinner1);
		category.setOnItemSelectedListener(this);
        // Loading spinner data from database
        loadSpinnerData();
		
		submit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if(!taskNameEmpty()){
					addNewTask();
					Toast.makeText(getApplicationContext(), "Task Created", Toast.LENGTH_LONG).show();
					finish();
				}
				else {
					Toast.makeText(getApplicationContext(), "Submit Failed. Fill in Task Name.", Toast.LENGTH_LONG).show();
				}
			}
		});
		button1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if(!taskNameEmpty()){
					addNewTask();
					Toast.makeText(getApplicationContext(), "Task Created", Toast.LENGTH_LONG).show();
					emptyFields();
				}
				else {
					Toast.makeText(getApplicationContext(), "Submit Failed. Fill in Task Name.", Toast.LENGTH_LONG).show();
				}
			}
		});
		cancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_task, menu);
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
	public void emptyFields(){
		taskName.setText("");
		description.setText("");
	}
	public void addNewTask(){
		db.createTask(new Task(taskName.getText().toString(), description.getText().toString(), selectedCategory, selectedName, false));
		Log.d("Category id: ", String.valueOf(selectedCategory));
	}
	public boolean taskNameEmpty(){
		if(taskName.getText().toString().length()==0){
			return true;
		}return false;
	}
	 private void loadSpinnerData() {
	        // database handler
	        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
	        List<Category> catList = db.getAllCategories();
	        ArrayList<String> labels = new ArrayList<String>();
	        spinnerList = catList;
	        for(Category cat: catList){
				labels.add(cat.getCategoryName());
			}
			if(labels.isEmpty()){
				//do something when the array is empty (change this to something else)
				db.close();
			}
			else{
				// Creating adapter for spinner
				ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
		                android.R.layout.simple_spinner_item, labels);
	 
	        // Drop down layout style - list view with radio button
	        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	 
	        // attaching data adapter to spinner
	        category.setAdapter(dataAdapter);
	    }
	 }

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Log.d("Category Name: ", spinnerList.get(position).getCategoryName());
		selectedCategory = spinnerList.get(position).getId();
		if(selectedCategory > 0){
			selectedName = spinnerList.get(position).getCategoryName();
		}
		else{
			selectedName = "";
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}
}
