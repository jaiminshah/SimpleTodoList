/*Not in Use. Kept for reference. */
package com.codepath.apps.simpletodolist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends Activity {

	private int position; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_item);
		String editText = getIntent().getStringExtra("text");
		position = getIntent().getIntExtra("position",0);
		EditText etEditItem = (EditText) findViewById(R.id.etEditItem);
		etEditItem.setText(editText);
		etEditItem.setSelection(editText.length());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_item, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void onSubmit(View v){
		EditText etEditItem = (EditText) findViewById(R.id.etEditItem);
		Intent data = new Intent();
		data.putExtra("text", etEditItem.getText().toString());
		data.putExtra("position", position);
		setResult(RESULT_OK, data);
		finish();
	}
}
