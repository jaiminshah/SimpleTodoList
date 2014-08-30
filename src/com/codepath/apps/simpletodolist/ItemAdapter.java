package com.codepath.apps.simpletodolist;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ItemAdapter extends ArrayAdapter<Item> {

	public ItemAdapter(Context context, ArrayList<Item> objects) {
		super(context, R.layout.item_listview, objects);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Item item = getItem(position);

		// Check if an existing view is being reused, otherwise inflate the view
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(
					R.layout.item_listview, parent, false);
		}

		TextView tvMaintext = (TextView) convertView
				.findViewById(R.id.tvMainText);
		TextView tvSubtext = (TextView) convertView
				.findViewById(R.id.tvSubText);

		tvMaintext.setText(item.getText());
		tvSubtext.setText(item.getDueDate());

		return convertView;
	}

}
