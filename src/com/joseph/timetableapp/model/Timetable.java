package com.joseph.timetableapp.model;

import java.util.ArrayList;

public abstract class Timetable {
	private ArrayList<Course> timetable;
	
	public Timetable(ArrayList<Course> t) {}
	
	public abstract Course[] getCoursebyDay(String day);
	
	public abstract Course getCourse(int index);
	
	public abstract Course[] getCourses ();
	
}
