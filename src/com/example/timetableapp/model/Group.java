package com.example.timetableapp.model;

public class Group {
	private int groupID;
	private String groupCode;
	private int programID;
	
	public int getGroupID() {
		return groupID;
	}
	public void setGroupID(int groupID) {
		this.groupID = groupID;
	}
	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	public int getProgramID() {
		return programID;
	}
	public void setProgramID(int programID) {
		this.programID = programID;
	}
	@Override
	public String toString() {
		return "Group [groupCode=" + groupCode + "]";
	}
	
}
