package com.joseph.timetableapp.parsers;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.joseph.timetableapp.model.Subject;


public class SubjectJSONParser {

	public static Subject parseFeed (String content){
		
		try {
			JSONObject obj = new JSONObject(content);
			Subject subject = new Subject();

				
				subject.setSubjectCode(obj.getString("course_code"));
				subject.setName(obj.getString("course_name"));
				
			return subject;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
