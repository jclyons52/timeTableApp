package com.example.timetableapp.parsers;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.timetableapp.model.Course;

public class CourseJSONParser {
	
	public static List<Course> parseFeed (String content){
		
		try {
			JSONArray ar = new JSONArray(content);
			List<Course> courseList = new ArrayList<Course>();
			
			for (int i = 0; i < ar.length(); i++) {
				
				JSONObject obj = ar.getJSONObject(i);
				Course course = new Course();
				
				course.setCourseCode(obj.getString("course_id"));
				course.setName(obj.getString("id"));
				course.setDay(obj.getString("day"));
				course.setStartTime(obj.getString("start_time"));
				course.setEndTime(obj.getString("end_time"));
				course.setLocation(obj.getString("class_location"));
				
				courseList.add(course);
			}
			return courseList;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
			
	}
}
