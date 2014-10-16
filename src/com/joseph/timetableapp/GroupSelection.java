package com.joseph.timetableapp;

import java.util.ArrayList;
import java.util.List;

import com.joseph.timetableapp.R;
import com.joseph.timetableapp.model.Group;
import com.joseph.timetableapp.parsers.GroupJSONParser;


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

public class GroupSelection extends ListActivity {
	
	int programID;
	TextView output;
	ProgressBar pb;
	List<MyTask> tasks;
	
	List<Group> groupList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = this.getIntent();
		programID = intent.getIntExtra("program_id", 0);
		System.out.println(programID);
		setContentView(R.layout.activity_main);
        
//		Initialize the TextView for vertical scrolling
		output = (TextView) findViewById(R.id.textView);
		output.setMovementMethod(new ScrollingMovementMethod());
		
		pb = (ProgressBar) findViewById(R.id.progressBar1);
		pb.setVisibility(View.INVISIBLE);
		
		tasks = new ArrayList<>();
		
		if(isOnline()){
    		StringBuilder sb = new StringBuilder();
    		sb.append("http://defiant-dayle.gopagoda.com/programs/");
    		sb.append(programID);
    		sb.append("/groups");
    		String strI = sb.toString();
    		System.out.println(strI);
    		requestData(strI);
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
		
		MyTask task = new MyTask();
		task.execute(uri);
	}
	
	protected void updateDisplay() {
		if (groupList != null) {
			ArrayAdapter<Group> adapter =
					new ArrayAdapter<Group>(this, R.layout.list_item_layout, groupList);
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
		Group group = groupList.get(position);
		Intent intent = new Intent(this, MainActivity.class);
		intent.putExtra("group_id", group.getGroupID());
		startActivityForResult(intent, 1002);
	}
	
	private class MyTask extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
//			updateDisplay("Starting task");
			
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
			
			groupList = GroupJSONParser.parseFeed(result);
			updateDisplay();

			tasks.remove(this);
			if (tasks.size() == 0) {
				pb.setVisibility(View.INVISIBLE);
			}

		}
		
		@Override
		protected void onProgressUpdate(String... values) {
//			updateDisplay(values[0]);
		}
	}
}
