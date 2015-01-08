package com.myexample.todolisttab;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.myexample.createactivities.CreateListActivity;
import com.myexample.model.Category;
import com.myexample.model.Task;
import com.myexample.sqlite.CustomListAdapter;
import com.myexample.sqlite.DatabaseHelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class ListActivity extends Activity {
	Button button2;
	DatabaseHelper db;
	ListView listview2;
	ArrayList<Category> selectedList;
	int selectedValue;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		db = new DatabaseHelper(this);
		listview2 = (ListView)findViewById(R.id.listView2);
		ArrayList<Category> arrayList = new ArrayList<Category>();
		List<Category> catList = db.getAllCategories();
		
		for(Category cat: catList){
			arrayList.add(cat);
		}
		if(arrayList.isEmpty()){
			//do something when the array is empty (change this to something else)
			db.close();
		}
		else{
			CustomListAdapter adapter = new CustomListAdapter(this, arrayList);
			selectedList = arrayList;
			listview2.setAdapter(adapter);
		
			listview2.setOnItemClickListener(new AdapterView.OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					selectedValue = selectedList.get(position).getId();
					Bundle bundle = new Bundle();
					bundle.putInt("id", selectedValue);
					Intent showTasks = new Intent(ListActivity.this, ShowActivity.class);
					showTasks.putExtra("bundle", bundle);
					startActivity(showTasks);
				}
			
			});
		}
	}
	protected void onResume(){
		super.onResume();
		List<Category> catList = db.getAllCategories();
		ArrayList<Category> arrayList1 = new ArrayList<Category>();
		for(Category cat: catList){
			arrayList1.add(cat);
		}
		if(arrayList1.isEmpty()){
			//do something when the array is empty (change this to something else)
			db.close();
		}
		else{
			CustomListAdapter adapter = new CustomListAdapter(this, arrayList1);
			listview2.setAdapter(adapter);
		
			listview2.setOnItemClickListener(new AdapterView.OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					final String item = (String) parent.getItemAtPosition(position);
				
				}
			
			});
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list, menu);
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
	public void addNewList(View view){
		Intent addNew = new Intent(this, CreateListActivity.class);
		startActivity(addNew);
	}
}
