package com.codepath.apps.simpletodolist;

import java.util.ArrayList;
import java.util.List;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.codepath.apps.simpletodolist.AddItemDialog.AddItemDialogListener;
import com.codepath.apps.simpletodolist.EditItemDialog.EditItemDialogListener;
import com.codepath.apps.simpletodolist.database.MItem;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;

public class TodoActivity extends FragmentActivity implements
		EditItemDialogListener, AddItemDialogListener {
	ArrayList<Item> items;
	ItemAdapter itemsAdapter;
	ListView lvItems;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo);
		lvItems = (ListView) findViewById(R.id.lvItems);
		items = new ArrayList<Item>();

		// Read from items from DB
		readItems();
		itemsAdapter = new ItemAdapter(this, items);
		lvItems.setAdapter(itemsAdapter);
		setupListViewListener();
	}

	private void setupListViewListener() {
		// Set Long click to remove an Item
		lvItems.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long rowId) {
				items.remove(position);
				itemsAdapter.notifyDataSetChanged();
				saveItems();
				return true;
			}
		});

		// Set Click for editing an item by opening a dialog
		lvItems.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				EditItemDialog alertDialog = EditItemDialog.newInstance(items
						.get(position));
				alertDialog.show(getSupportFragmentManager(),
						"edit_item_dialog");
			}
		});
	}

	// Called when Add btn is pressed
	public void addTodoItem(View v) {
		DialogFragment addDialog = new AddItemDialog();
		addDialog.show(getSupportFragmentManager(), "add_item_dialog");
	}

	private void readItems() {
		List<MItem> itemList = MItem.getAll();
		for (MItem item : itemList) {
			Item tItem = new Item(item.position, item.text, item.dueDate);
			items.add(tItem);
		}
	}

	private void saveItems() {
		// Use ActiveAndroid transaction for better performance.
		ActiveAndroid.beginTransaction();
		try {
			// Delete the whole table and add entries with the latest Values
			// TODO: figure out a better way.
			new Delete().from(MItem.class).execute();
			for (int i = 0; i < items.size(); i++) {
				MItem item = new MItem(i, items.get(i).getText(), items.get(i)
						.getDueDate().toString());
				item.save();
			}
			ActiveAndroid.setTransactionSuccessful();
		} finally {
			ActiveAndroid.endTransaction();
		}
	}

	// Callback from the edit dialog.
	@Override
	public void onEditItemClick(Item item) {
		items.set(item.getPosition(), item);
		itemsAdapter.notifyDataSetChanged();
		saveItems();
	}

	@Override
	public void onAddItemClick(Item item) {
		// Add new item to the bottom position.
		item.setPosition(items.size());
		itemsAdapter.add(item);
		saveItems();
	}

}
