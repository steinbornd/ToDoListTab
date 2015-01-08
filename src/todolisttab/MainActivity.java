package com.myexample.todolisttab;



import com.myexample.model.Category;
import com.myexample.sqlite.DatabaseHelper;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;


public class MainActivity extends TabActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TabHost tabHost = getTabHost(); 
		
		TabSpec taskspec = tabHost.newTabSpec("My Tasks");
		taskspec.setIndicator("My Tasks");
		Intent taskIntent = new Intent(this, TaskActivity.class);
		taskspec.setContent(taskIntent);
		
		TabSpec listspec = tabHost.newTabSpec("My Lists");
		listspec.setIndicator("My Lists");
		Intent listIntent = new Intent(this, ListActivity.class);
		listspec.setContent(listIntent);
		
		tabHost.addTab(taskspec);
		tabHost.addTab(listspec);
		
    }
}
