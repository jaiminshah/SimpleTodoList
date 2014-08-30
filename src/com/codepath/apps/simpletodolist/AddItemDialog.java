package com.codepath.apps.simpletodolist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

public class AddItemDialog extends DialogFragment {
	private EditText mEditText;
	private DatePicker mDueDate;

	public AddItemDialog() {
		// Empty constructor required for DialogFragment
	}

	public interface AddItemDialogListener {
		public void onAddItemClick(Item item);
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();

		builder.setTitle(R.string.add_dialog);
		View view = inflater.inflate(R.layout.item_dialog, null, false);
		mEditText = (EditText) view.findViewById(R.id.etAddItemText);
		mEditText.requestFocus();
		mDueDate = (DatePicker) view.findViewById(R.id.dpDueDate);
		builder.setView(view)
				.setPositiveButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(
									DialogInterface dialogInterface, int i) {
								AddItemDialogListener listener = (AddItemDialogListener) getActivity();
								String date = mDueDate.getMonth() + "/"
										+ mDueDate.getDayOfMonth() + "/"
										+ mDueDate.getYear();
								listener.onAddItemClick(new Item(0, mEditText
										.getText().toString(), date));
							}
						})
				.setNegativeButton(android.R.string.cancel,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(
									DialogInterface dialogInterface, int i) {
								dialogInterface.dismiss();
							}
						});

		return builder.create();
	}
}
