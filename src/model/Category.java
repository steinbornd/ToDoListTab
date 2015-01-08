package com.myexample.model;

public class Category {
	int category_id;
	String category_name;
	String description;
	
	public Category(){}
	
	public Category(int category_id, String category_name, String description){
		this.category_id = category_id;
		this.category_name = category_name;
		this.description = description;
	}
	public Category(String category_name, String description){
		this.category_name = category_name;
		this.description = description;
	}
	public Category(int category_id, String category_name){
		this.category_id = category_id;
		this.category_name = category_name;
	}
	public void setId(int category_id){
		this.category_id = category_id;
	}
	public void setCategoryName(String category_name){
		this.category_name = category_name;
	}
	public void setCategoryDescription(String description){
		this.description = description;
	}
	public int getId(){
		return this.category_id;
	}
	public String getCategoryName(){
		return this.category_name;
	}
	public String getDescription(){
		return this.description;
	}
}
