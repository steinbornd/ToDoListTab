package com.myexample.model;

public class Task {
	int task_id;
	String task_name;
	String description;
	int category_id;
	String category_name;
	boolean checked;
	
	
	public Task(){
	}
	public Task (int task_id, String task_name, String description, int category_id, String category_name, boolean checked){
		this.task_id = task_id;
		this.task_name = task_name;
		this.description = description;
		this.category_id = category_id;
		this.category_name = category_name;
	}
	public Task(int task_id, String task_name, String description, boolean checked){
		this.task_id = task_id;
		this.task_name = task_name;
		this.description = description;
	}
	public Task(String task_name, String description, int category_id, String category_name, boolean checked){
		this.task_name = task_name;
		this.description = description;
		this.category_id = category_id;
		this.category_name = category_name;
	}
	public Task(String task_name, String description, boolean checked){
		this.task_name = task_name;
		this.description = description;
		
	}
	public void setId(int task_id){
		this.task_id = task_id;
	}
	public void setTaskName(String task_name){
		this.task_name = task_name;
	}
	public void setDescription(String description){
		this.description = description;
	}
	public int getId (){
		return this.task_id;
	}
	public String getTaskName(){
		return this.task_name;
	}
	public String getDescription(){
		return this.description;
	}
	public int getCategoryId(){
		return this.category_id;
	}
	public void setCategoryId(int category_id){
		this.category_id = category_id;
	}
	public String getCategoryName(){
		return this.category_name;
	}
	public void setCategoryName(String category_name){
		this.category_name = category_name;
	}
	public boolean getChecked(){
		return this.checked;
	}
	public void setChecked(boolean checked){
		this.checked = checked;
	}
}
