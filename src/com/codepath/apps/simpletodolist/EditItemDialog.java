package com.codepath.apps.simpletodolist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;


public class EditItemDialog extends DialogFragment {
	
	private EditText mEditText;
	
	public EditItemDialog(){
		  // Empty constructor required for DialogFragment
	}
	
	public static EditItemDialog newInstance(int position, String text) {
		EditItemDialog frag = new EditItemDialog();
		Bundle args = new Bundle();
		args.putInt("position", position);
		args.putString("text", text);
		frag.setArguments(args);
		return frag;
	}
	
	public interface EditItemDialogListener{
		public void onDialogPositiveClick(int position, String editedText); 
	}
	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    LayoutInflater inflater = getActivity().getLayoutInflater();
        
        builder.setTitle(R.string.edit_dialog);
        View view = inflater.inflate(R.layout.fragment_edit_item, null, false);
    	mEditText = (EditText) view.findViewById(R.id.etEditItemText);
    	mEditText.setText(getArguments().getString("text"));
    	mEditText.requestFocus();
	    builder.setView(view)
            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                   EditItemDialogListener listener = (EditItemDialogListener) getActivity();
                   listener.onDialogPositiveClick(
                		   getArguments().getInt("position"), 
                		   mEditText.getText().toString());
                }
            })
            .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                   dialogInterface.dismiss();
                }
            });
 
        return builder.create();
    }
}
