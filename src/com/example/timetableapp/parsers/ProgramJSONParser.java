package com.example.timetableapp.parsers;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.timetableapp.model.Program;

public class ProgramJSONParser {
	
	public static List<Program> parseFeed (String content){
		
		try {
			JSONArray ar = new JSONArray(content);
			List<Program> programList = new ArrayList<Program>();
			
			for (int i = 0; i < ar.length(); i++) {
				
				JSONObject obj = ar.getJSONObject(i);
				Program program = new Program();
				
				program.setProgramID(obj.getInt("id"));
				program.setProgramCode(obj.getString("program_code"));
				program.setProgramName(obj.getString("program_name"));
				program.setCollege(obj.getString("college"));
				
				programList.add(program);
			}
			return programList;
		} catch (JSONException e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
		
	}

}
