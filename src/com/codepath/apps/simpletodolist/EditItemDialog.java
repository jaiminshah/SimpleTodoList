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

public class EditItemDialog extends DialogFragment {

	private EditText mEditText;
	private DatePicker mDueDate;

	public EditItemDialog() {
		// Empty constructor required for DialogFragment
	}

	public static EditItemDialog newInstance(Item item) {
		EditItemDialog frag = new EditItemDialog();
		Bundle args = new Bundle();
		args.putInt("position", item.getPosition());
		args.putString("text", item.getText());
		args.putString("dueDate", item.getDueDate());
		frag.setArguments(args);
		return frag;
	}

	public interface EditItemDialogListener {
		public void onEditItemClick(Item item);
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();

		builder.setTitle(R.string.edit_dialog);
		View view = inflater.inflate(R.layout.item_dialog, null, false);
		mEditText = (EditText) view.findViewById(R.id.etAddItemText);
		mEditText.setText(getArguments().getString("text"));
		mEditText.requestFocus();
		// Parse dueDate(mm/dd/yyyy) to get month day and year
		String date[] = getArguments().getString("dueDate").split("/");
		int month = Integer.parseInt(date[0]);
		int day = Integer.parseInt(date[1]);
		int year = Integer.parseInt(date[2]);
		mDueDate = (DatePicker) view.findViewById(R.id.dpDueDate);
		mDueDate.updateDate(year, month, day);

		builder.setView(view)
				.setPositiveButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(
									DialogInterface dialogInterface, int i) {
								EditItemDialogListener listener = (EditItemDialogListener) getActivity();
								String date = mDueDate.getMonth() + "/"
										+ mDueDate.getDayOfMonth() + "/"
										+ mDueDate.getYear();
								listener.onEditItemClick(new Item(
										getArguments().getInt("position"),
										mEditText.getText().toString(), date));
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
