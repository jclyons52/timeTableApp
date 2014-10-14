package com.example.timetableapp;

import com.example.timetableapp.model.Course;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class SessionDetailsActivity extends Activity{
	
	private Course course;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_session_details);
		
		Intent intent = this.getIntent();
		course = new Course();
		course.setName(intent.getStringExtra("Name"));
		course.setCourseCode(intent.getStringExtra("coursecode"));
		course.setLocation(intent.getStringExtra("Location"));	
		course.setDay(intent.getStringExtra("Day"));
		course.setStartTime(intent.getStringExtra("StartTime"));
		course.setEndTime(intent.getStringExtra("EndTime"));
	
		TextView myTextView = (TextView) findViewById(R.id.myTextView);
		myTextView.setText(course.toString());
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

}
