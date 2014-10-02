package com.example.timetableapp.parsers;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.timetableapp.model.Group;

public class GroupJSONParser {
	
public static List<Group> parseFeed (String content){
		
		try {
			JSONArray ar = new JSONArray(content);
			List<Group> groupList = new ArrayList<Group>();
			
			for (int i = 0; i < ar.length(); i++) {
				
				JSONObject obj = ar.getJSONObject(i);
				Group group = new Group();
				
				group.setGroupID(obj.getInt("id"));
				group.setGroupCode(obj.getString("group_code"));
				group.setProgramID(obj.getInt("program_id"));
				
				groupList.add(group);
			}
			return groupList;
		} catch (JSONException e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
		
	}

}
