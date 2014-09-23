package com.example.timetableapp;

import java.util.ArrayList;
import java.util.List;

import com.example.timetableapp.model.Group;
import com.example.timetableapp.parsers.GroupJSONParser;


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
		setContentView(R.layout.activity_main);
        
//		Initialize the TextView for vertical scrolling
		output = (TextView) findViewById(R.id.textView);
		output.setMovementMethod(new ScrollingMovementMethod());
		
		pb = (ProgressBar) findViewById(R.id.progressBar1);
		pb.setVisibility(View.INVISIBLE);
		
		tasks = new ArrayList<>();
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
        int id = item.getItemId();
        if (id == R.id.action_get_data) {
        	if(isOnline()){
        		requestData("http://http://defiant-dayle.gopagoda.com/programs"+programID+"/groups");
        	} else {
        		Toast.makeText(this, "Network isn't available", Toast.LENGTH_LONG).show();
        	}
        }
        return false;
    }


	private void requestData(String uri) {
		
		RequestPackage p = new RequestPackage();
		p.setMethod("GET");
		p.setUri(uri);
		MyTask task = new MyTask();
		task.execute(p);
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
		Intent intent = new Intent(this, GroupSelection.class);
		intent.putExtra("program_id", group.getGroupID());
		startActivityForResult(intent, 1001);
	}
	
	private class MyTask extends AsyncTask<RequestPackage, String, String> {

		@Override
		protected void onPreExecute() {
//			updateDisplay("Starting task");
			
			if (tasks.size() == 0) {
				pb.setVisibility(View.VISIBLE);
			}
			tasks.add(this);
		}
		
		@Override
		protected String doInBackground(RequestPackage... params) {
			
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
