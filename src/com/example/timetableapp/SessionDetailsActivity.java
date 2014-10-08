package com.example.timetableapp;

import com.example.timetableapp.model.Course;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

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
	
	

}
