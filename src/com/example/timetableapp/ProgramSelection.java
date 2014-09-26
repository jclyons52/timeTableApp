package com.example.timetableapp;

import java.util.ArrayList;
import java.util.List;

import com.example.timetableapp.model.Program;
import com.example.timetableapp.parsers.ProgramJSONParser;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class ProgramSelection extends ListActivity {
	
	TextView output;
	ProgressBar pb;
	List<MyTask> tasks;
	
	List<Program> programList;
	
	@Override
	protected void onCreate (Bundle savedInstanceState){
		super. onCreate(savedInstanceState);
		 setContentView(R.layout.activity_select_program);
	        
//			Initialize the TextView for vertical scrolling
			output = (TextView) findViewById(R.id.textView);
			output.setMovementMethod(new ScrollingMovementMethod());
			
			pb = (ProgressBar) findViewById(R.id.progressBar2);
			pb.setVisibility(View.INVISIBLE);
			
			tasks = new ArrayList<>();
			
			if(isOnline()){
        		requestData("http://defiant-dayle.gopagoda.com/programs");
        	} else {
        		Toast.makeText(this, "Network isn't available", Toast.LENGTH_LONG).show();
        	}
	}

	 @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        // Inflate the menu; this adds items to the action bar if it is present.
	        getMenuInflater().inflate(R.menu.main, menu);
	        return true;
	    }

	    @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        // Handle action bar item clicks here. The action bar will
	        // automatically handle clicks on the Home/Up button, so long
	        // as you specify a parent activity in AndroidManifest.xml.
	        return false;
	    }


	    private void requestData(String uri) {
			// TODO Auto-generated method stub
			MyTask task = new MyTask();
			task.execute(uri);
		}
		
		protected void updateDisplay() {
			if (programList != null) {
				ArrayAdapter<Program> adapter =
						new ArrayAdapter<Program>(this, R.layout.list_item_layout, programList);
				setListAdapter(adapter);
			}
		}

		protected boolean isOnline() {
			ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo netInfo = cm.getActiveNetworkInfo();
			if (netInfo != null && netInfo.isConnectedOrConnecting()) {
				return true;
			} else {
				return false;
			
		}
		}
		
		protected void onListItemClick(ListView l, View v, int position, long id){
			Program program = programList.get(position);
			Intent intent = new Intent(this, GroupSelection.class);
			intent.putExtra("program_id", program.getProgramID());
			startActivityForResult(intent, 1001);
		}

private class MyTask extends AsyncTask<String, String, String> {

	@Override
	protected void onPreExecute() {
//		updateDisplay("Starting task");
		
		if (tasks.size() == 0) {
			pb.setVisibility(View.VISIBLE);
		}
		tasks.add(this);
	}
	
	@Override
	protected String doInBackground(String... params) {
		
		String content = HttpManager.getData(params[0]);
		return content;
	}
	
	@Override
	protected void onPostExecute(String result) {
		
		programList = ProgramJSONParser.parseFeed(result);
		updateDisplay();

		tasks.remove(this);
		if (tasks.size() == 0) {
			pb.setVisibility(View.INVISIBLE);
		}

	}
	
	@Override
	protected void onProgressUpdate(String... values) {
//		updateDisplay(values[0]);
	}
}
}
