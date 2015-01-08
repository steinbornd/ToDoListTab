package com.myexample.createactivities;


import com.myexample.model.Category;

import com.myexample.sqlite.DatabaseHelper;
import com.myexample.todolisttab.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateListActivity extends Activity {
	EditText listName;
	EditText description;
	DatabaseHelper db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_list);
		
		db = new DatabaseHelper(this);
		
		listName = (EditText)findViewById(R.id.editText2);
		description = (EditText)findViewById(R.id.editText1);
		Button submitButton = (Button)findViewById(R.id.button2);
		Button cancelButton = (Button)findViewById(R.id.button1);
		Button button3 = (Button)findViewById(R.id.button3);
		
		submitButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if(!listNameEmpty()){
					addNewList();
					Toast.makeText(getApplicationContext(), "List Created", Toast.LENGTH_LONG).show();
					finish();
				}
				else {
					Toast.makeText(getApplicationContext(), "Submit Failed. Fill in List Name.", Toast.LENGTH_LONG).show();
				}
			}
		});
		cancelButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}
		});
		button3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if(!listNameEmpty()){
					addNewList();
					Toast.makeText(getApplicationContext(), "List Created", Toast.LENGTH_LONG).show();
					emptyFields();
				}
				else {
					Toast.makeText(getApplicationContext(), "Submit Failed. Fill in List Name.", Toast.LENGTH_LONG).show();
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_list, menu);
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
		listName.setText("");
		description.setText("");
	}
	public void addNewList(){
		db.createCategory(new Category(listName.getText().toString(), description.getText().toString()));
	}
	public boolean listNameEmpty(){
		if(listName.getText().toString().length()==0){
			return true;
		}return false;
	}
}
