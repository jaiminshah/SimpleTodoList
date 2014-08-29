package com.codepath.apps.simpletodolist;

import java.util.ArrayList;
import java.util.List;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.codepath.apps.simpletodolist.EditItemDialog.EditItemDialogListener;
import com.codepath.apps.simpletodolist.database.MItem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public class TodoActivity extends FragmentActivity implements EditItemDialogListener{
	ArrayList<String> items;
	ArrayAdapter<String> itemsAdapter;
	ListView lvItems;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        lvItems = (ListView) findViewById(R.id.lvItems);
        items = new ArrayList<String>();
        
        // Read from items from DB
        readItems(); 
        itemsAdapter = new ArrayAdapter<String>(this,
        		android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
        setupListViewListener();
    }
    
    private void setupListViewListener(){
    	//Set Long click to remove an Item
    	lvItems.setOnItemLongClickListener(new OnItemLongClickListener() {
		@Override
		public boolean onItemLongClick(AdapterView<?> parent,
				View view, int position, long rowId) {
				items.remove(position);
				itemsAdapter.notifyDataSetChanged();
				saveItems();
				return true;
			}
    	});
    	
    	//Set Click for editing an item by opening a dialog
    	lvItems.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				FragmentManager fm = getSupportFragmentManager();
			  	EditItemDialog alertDialog = 
			  			EditItemDialog.newInstance(position, items.get(position));
			  	alertDialog.show(fm, "fragment_alert");
			}			
    	}); 
    }

    //Called when Add btn is pressed
    public void addTodoItem(View v){
    	EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
    	itemsAdapter.add(etNewItem.getText().toString());
    	etNewItem.setText("");
    	saveItems();
    }
        
    private void readItems() {
    	List<MItem> itemList = MItem.getAll();
    	for(MItem item : itemList){
    		items.add(item.position, item.text);
    	}
    }
    
    private void saveItems() {
    	//Use ActiveAndroid transaction for better performance.
    	ActiveAndroid.beginTransaction();
    	try {
    		//Delete the whole table and add entries with the latest Values
    		//TODO: figure out a better way.
    		new Delete().from(MItem.class).execute();
	        for (int i = 0; i < items.size(); i++) {
	            MItem item = new MItem(i, items.get(i));
	            item.save();
	        }
	        ActiveAndroid.setTransactionSuccessful();
    	}
    	finally {
	        ActiveAndroid.endTransaction();
    	}
    }

    //Callback from the edit dialog. 
	@Override
	public void onDialogPositiveClick(int position, String editedText) {
		items.set(position, editedText);
		itemsAdapter.notifyDataSetChanged();
		saveItems();
	}

}
