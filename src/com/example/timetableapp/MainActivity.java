package com.example.timetableapp;

import java.util.ArrayList;
import java.util.List;

import com.example.timetableapp.model.Course;
import com.example.timetableapp.parsers.CourseJSONParser;
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


public class MainActivity extends ListActivity {
	
	int groupID;
	TextView output;
	ProgressBar pb;
	List<MyTask> tasks;
	
	List<Course> courseList;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = this.getIntent();
        groupID = intent.getIntExtra("group_id", 0);
//		Initialize the TextView for vertical scrolling
		output = (TextView) findViewById(R.id.textView);
		output.setMovementMethod(new ScrollingMovementMethod());
		
		pb = (ProgressBar) findViewById(R.id.progressBar1);
		pb.setVisibility(View.INVISIBLE);
		
		tasks = new ArrayList<>();
		
		if(isOnline()){
    		StringBuilder sb = new StringBuilder();
    		sb.append("http://defiant-dayle.gopagoda.com/groups/");
    		sb.append(groupID);
    		sb.append("/courseclasses");
    		String strI = sb.toString();
    		System.out.println(strI);
    		requestData(strI);
	}
		
//		updateDisplay();
		

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
        if(id == R.id.action_set_details){
        	Intent intent = new Intent(this, ProgramSelection.class);
        	startActivity(intent);
        }else {
    		Toast.makeText(this, "Network isn't available", Toast.LENGTH_LONG).show();
    	}
        return false;
    }


    private void requestData(String uri) {
		// TODO Auto-generated method stub
		MyTask task = new MyTask();
		task.execute(uri);
	}
	
	protected void updateDisplay() {
		if (courseList != null) {
			ArrayAdapter<Course> adapter =
					new ArrayAdapter<Course>(this, R.layout.list_item_layout, courseList);
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
		Course course = courseList.get(position);
		Intent intent = new Intent(this, SessionDetailsActivity.class);
		intent.putExtra("courseCode", course.getCourseCode());
		intent.putExtra("Day", course.getDay());
		intent.putExtra("EndTime", course.getEndTime());
		intent.putExtra("Location", course.getLocation());
		intent.putExtra("Name", course.getName());
		startActivityForResult(intent, 1001);
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
			
			courseList = CourseJSONParser.parseFeed(result);
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
