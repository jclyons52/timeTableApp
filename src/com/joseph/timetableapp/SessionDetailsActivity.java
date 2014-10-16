package com.joseph.timetableapp;

import java.util.ArrayList;
import java.util.List;

import com.joseph.timetableapp.R;
import com.joseph.timetableapp.model.Course;
import com.joseph.timetableapp.model.Subject;
import com.joseph.timetableapp.parsers.SubjectJSONParser;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class SessionDetailsActivity extends Activity{
	
	private Course course;
	List<MyTask> tasks;
	Subject subject;
	TextView output;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_session_details);
		tasks = new ArrayList<>();
		Intent intent = this.getIntent();
		course = new Course();
		course.setName(intent.getStringExtra("Name"));
		course.setCourseCode(intent.getStringExtra("courseCode"));
		course.setLocation(intent.getStringExtra("Location"));	
		course.setDay(intent.getStringExtra("Day"));
		course.setStartTime(intent.getStringExtra("StartTime"));
		course.setEndTime(intent.getStringExtra("EndTime"));
		
		
		
		if(isOnline()){
    		StringBuilder sb = new StringBuilder();
    		sb.append("http://defiant-dayle.gopagoda.com/courses/");
    		sb.append(course.getCourseCode());
    		String strI = sb.toString();
    		System.out.println(strI);
    		requestData(strI);
		}
	
		
	}
	
	 @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        // Inflate the menu; this adds items to the action bar if it is present.
	        getMenuInflater().inflate(R.menu.main, menu);
	        return true;
	    }
	 
	 private void requestData(String uri) {
			// TODO Auto-generated method stub
			MyTask task = new MyTask();
			task.execute(uri);
		}
		
		protected void updateDisplay() {
			

			
			TextView myTextView = (TextView) findViewById(R.id.myTextView);
			myTextView.setText(subject.getName()+"\n start time: "+course.getStartTime()+"\n end time: "+course.getEndTime()+"\n location: "+course.getLocation());
			
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

	    
	    
	    private class MyTask extends AsyncTask<String, String, String> {

			@Override
			protected void onPreExecute() {
//				updateDisplay("Starting task");
				tasks.add(this);
			}
			
			@Override
			protected String doInBackground(String... params) {
				
				String content = HttpManager.getData(params[0]);
				return content;
			}
			
			@Override
			protected void onPostExecute(String result) {
				
				subject = SubjectJSONParser.parseFeed(result);
				updateDisplay();

				tasks.remove(this);

			}
			
			@Override
			protected void onProgressUpdate(String... values) {
//				updateDisplay(values[0]);
			}
		}

}
