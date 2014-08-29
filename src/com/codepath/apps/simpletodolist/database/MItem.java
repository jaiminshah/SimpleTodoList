package com.codepath.apps.simpletodolist.database;

import java.util.List;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

@Table(name = "Items")
public class MItem extends Model {
	@Column(name = "Position", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
	public int position;
	
	@Column(name = "Text")
	public String text;
	
	public MItem(){
		super();
	}
	
	public MItem(int position, String text){
		super();
		this.position = position;
		this.text = text;
	}
	
	//Get single item based on the position
	public static MItem getItem(int position) {
        return new Select()
	      .from(MItem.class)
	      .where("Position = ?", position)
	      .executeSingle();
	}
	
	//Get all items. 
	public static List<MItem> getAll() {
		return new Select()
		  .from(MItem.class)
          .orderBy("Position ASC")
          .execute();
	 }
	
}
