package com.codepath.apps.simpletodolist;

public class Item {
	private String text;
	private int position;
	private String dueDate;

	public Item() {

	}

	public Item(int position, String text, String dueDate) {
		this.text = text;
		this.dueDate = dueDate;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

}
