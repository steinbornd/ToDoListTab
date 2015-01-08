package com.myexample.sqlite;

import java.util.LinkedList;
import java.util.List;

import com.myexample.model.Category;
import com.myexample.model.Task;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper{
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "TODO_LIST";
	
	//table names
	private static final String TABLE_TASK = "task";
	private static final String TABLE_CATEGORY = "category";
	
	//task table column names
	public static final String KEY_TASK_NAME = "task_name";
	public static final String KEY_DESCRIPTION = "description";
	
	//id names
	private static final String KEY_CATEGORY_ID = "category_id";
	private static final String KEY_TASK_ID = "task_id";
	
	//category table column names
	private static final String KEY_CATEGORY_NAME = "category_name";
	
	
	private static final String[] CATEGORY_COLUMNS = {KEY_CATEGORY_ID, KEY_CATEGORY_NAME,KEY_DESCRIPTION};
	private static final String[] TASK_COLUMNS = {KEY_TASK_ID, KEY_TASK_NAME,KEY_DESCRIPTION, KEY_CATEGORY_ID, KEY_CATEGORY_NAME};
	
	
	//create tables
	private static final String CREATE_TABLE_TASK = "CREATE TABLE " + TABLE_TASK + 
			"( task_id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
			"task_name TEXT, " + 
			"description TEXT, " + 
			"category_id INTEGER REFERENCES category(category_id), " +
			"category_name TEXT, " + 
			"checked TEXT)";
	
	private static final String CREATE_TABLE_CATEGORY = "CREATE TABLE " + TABLE_CATEGORY + 
			"( category_id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
			"category_name TEXT, " + 
			"description TEXT)";
	
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);	
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_TASK);
		db.execSQL(CREATE_TABLE_CATEGORY);
	}
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older books table if existed
        db.execSQL("DROP TABLE IF EXISTS task");
        db.execSQL("DROP TABLE IF EXISTS category");
        
        // create fresh books table
        this.onCreate(db);
	}
	
	
	/*-------------------------------Category Table Methods ------------------------------------*/
	/*
	 * Creating category
	 */
	public void createCategory(Category category) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_CATEGORY_NAME, category.getCategoryName());
		values.put(KEY_DESCRIPTION, category.getDescription());

		// insert row
		db.insert(TABLE_CATEGORY, null, values);
	}
	/*
	 * get category
	 */
	public Category getCategory(int id){

		// 1. get reference to readable DB
		SQLiteDatabase db = this.getReadableDatabase();
		 
		// 2. build query
        Cursor cursor = 
        		db.query(TABLE_CATEGORY, // a. table
        		CATEGORY_COLUMNS, // b. column names
        		" category_id = ?", // c. selections 
                new String[] { String.valueOf(id) }, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
        
        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();
 
        // 4. build contact object
        Category category = new Category();
        category.setId(Integer.parseInt(cursor.getString(0)));
        category.setCategoryName(cursor.getString(1));
        category.setCategoryDescription(cursor.getString(2));

        // 5. return book
        return category;
	}
	/*
	 * search category
	 */
	public List<Category> searchCategory(String selectQuery) {
		List<Category> categories = new LinkedList<Category>();

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (c.moveToFirst()) {
			do {
				Category cat = new Category();
				cat.setId(Integer.parseInt(c.getString(0)));
				cat.setCategoryName(c.getString(1));
				cat.setCategoryDescription(c.getString(2));

				// adding to tags list
				categories.add(cat);
			} while (c.moveToNext());
		}
		return categories;
	}
	/*
	 * get all categories
	 */
	public List<Category> getAllCategories() {
        // 1. build the query
        String query = "SELECT * FROM " + TABLE_CATEGORY;
        return searchCategory(query);
	}
	
	public List<Category> searchCategory(String category_name, String category_description) {
		String query = "SELECT * FROM " + TABLE_CATEGORY + " WHERE ";
		boolean needAnd = false;
		List<String> fields = new LinkedList<String>();
		fields.add(category_name);
		fields.add(category_description);
		
		for (String field : fields) {
			if(field.length() == 0) {
				continue;
			} else {
				if(needAnd) {
					query = query + " AND " + CATEGORY_COLUMNS[fields.indexOf(field)] + " LIKE '%" + field + "%'";
				} else {
					query = query + CATEGORY_COLUMNS[fields.indexOf(field)] + " LIKE '%" + field + "%'";
					needAnd = true;
				}
			}
		}
		return searchCategory(query);
	}
	/*
	 * update category
	 */
	 public int updateCategory(Category category) {

	    	// 1. get reference to writable DB
	        SQLiteDatabase db = this.getWritableDatabase();
	 
			// 2. create ContentValues to add key "column"/value
	        ContentValues values = new ContentValues();
	        values.put(KEY_CATEGORY_NAME, category.getCategoryName());
	        values.put(KEY_DESCRIPTION, category.getDescription());
	        
	        // 3. updating row
	        int i = db.update(TABLE_CATEGORY, //table
	        		values, // column/value
	        		KEY_CATEGORY_ID+" = ?", // selections
	                new String[] { String.valueOf(category.getId()) }); //selection args
	        
	        // 4. close
	        db.close();
	        
	        return i;
	        
	    }
	 /*
		 * delete category
		 * ADD NEW UNKNOWN CATEGORY IF TASK HAS NO CATEGORY - then can be edited blah blah
		 */
		public void deleteCategory(Category category){
			SQLiteDatabase db = this.getWritableDatabase();
			// 2. delete
			
	        db.delete(TABLE_CATEGORY,
	        		KEY_CATEGORY_ID+" = ?",
	                new String[] { String.valueOf(category.getId()) });
	        
	        // if there are values in category_task with category_id as a value 
			// then delete from category_task
	        // 3. close
	        db.close();
		}
	
	/*-----------------------------------Task Table Methods----------------------------------------*/
	
	/*
	 * search tasks
	 */
	public List<Task> searchTask(String selectQuery) {
		List<Task> tasks = new LinkedList<Task>();

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (c.moveToFirst()) {
			do {
				Task cat = new Task();
				cat.setId(Integer.parseInt(c.getString(0)));
				cat.setTaskName(c.getString(1));
				cat.setDescription(c.getString(2));
				cat.setCategoryId(Integer.parseInt(c.getString(3)));
				cat.setCategoryName(c.getString(4));

				// adding to tags list
				tasks.add(cat);
			} while (c.moveToNext());
		}
		return tasks;
	}
	/*
	 * get all tasks
	 */
	public List<Task> getAllTasks(){
		String query = "SELECT * FROM " + TABLE_TASK;
		return searchTask(query);
	}
	/*
	 * Creating a task
	 */
	public void createTask(Task task) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_TASK_NAME, task.getTaskName());
		values.put(KEY_DESCRIPTION, task.getDescription());
		values.put(KEY_CATEGORY_ID, task.getCategoryId());
		values.put(KEY_CATEGORY_NAME, task.getCategoryName());
		values.put("checked", task.getChecked());

		// insert row
		db.insert(TABLE_TASK, null, values);
	}
	/*
	 * get task
	 */
	public Task getTask(int id){

		// 1. get reference to readable DB
		SQLiteDatabase db = this.getReadableDatabase();
		 
		// 2. build query
        Cursor cursor = 
        		db.query(TABLE_TASK, // a. table
        		TASK_COLUMNS, // b. column names
        		" id = ?", // c. selections 
                new String[] { String.valueOf(id) }, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
        
        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();
 
        // 4. build contact object
        Task task = new Task();
        task.setId(Integer.parseInt(cursor.getString(0)));
        task.setTaskName(cursor.getString(1));
        task.setDescription(cursor.getString(2));
        task.setCategoryId(Integer.parseInt(cursor.getString(3)));

        // 5. return book
        return task;
	}
	
	public List<Task> searchTask(String task_name, String task_description, int category_id, String category_name) {
		String query = "SELECT * FROM " + TABLE_TASK + " WHERE ";
		boolean needAnd = false;
		List<String> fields = new LinkedList<String>();
		fields.add(task_name);
		fields.add(task_description);
		fields.add(String.valueOf(category_id));
		fields.add(category_name);
		
		
		for (String field : fields) {
			if(field.length() == 0) {
				continue;
			} else {
				if(needAnd) {
					query = query + " AND " + TASK_COLUMNS[fields.indexOf(field)] + " LIKE '%" + field + "%'";
				} else {
					query = query + TASK_COLUMNS[fields.indexOf(field)] + " LIKE '%" + field + "%'";
					needAnd = true;
				}
			}
		}
		return searchTask(query);
	}
	/*
	 * update task
	 */
	public int updateTask(Task task) {

    	// 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
 
		// 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_TASK_NAME, task.getTaskName());
        values.put(KEY_DESCRIPTION, task.getDescription());
        values.put(KEY_CATEGORY_ID, task.getCategoryId());
        values.put(KEY_CATEGORY_NAME, task.getCategoryName());
        values.put("checked", task.getChecked());
        
        // 3. updating row
        int i = db.update(TABLE_TASK, //table
        		values, // column/value
        		KEY_TASK_ID+" = ?", // selections
                new String[] { String.valueOf(task.getId()) }); //selection args
        
        // 4. close
        db.close();
        
        return i;
        
    }
	public boolean updateTask(Integer id, String name, String description, int category_id, String category_name, boolean checked){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_TASK_NAME, name);
		values.put(KEY_DESCRIPTION, description);
		values.put(KEY_CATEGORY_ID, category_id);
		values.put(KEY_CATEGORY_NAME, category_name);
		values.put("checked", checked);
		
		db.update("task", values, "id = ?", new String[] {Integer.toString(id) });
		return true;
	}
	/*
	 * delete task
	 */
	public void deleteTask(int id){
		SQLiteDatabase db = this.getWritableDatabase();
		// 2. delete
        db.delete(TABLE_TASK,
        		KEY_TASK_ID+" = ?",
                new String[] { String.valueOf(id) });
        
        // if there are rows in category task where task id is matched
        // then delete from category task
        
        // 3. close
        db.close();
	}
	
}
