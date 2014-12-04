package com.UpGrade;

import java.io.Serializable;
import java.util.ArrayList;

public class UpGradeClass implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 944563812578072380L;
	private String name;
	private String gradeSource_link;
	private int gradeSource_number;
	private ArrayList<String> sectionNames;
	private ArrayList<String> sectionPercents;

	
	// Constructors
	public UpGradeClass(String name){
		this.name = name;
		this.setGradeSourceLink(null);
		this.setGradeSourceNumber(0);
	}
	
	public UpGradeClass(){
		name = "blank";
		this.setGradeSourceLink(null);
		this.setGradeSourceNumber(0);
	}
	

	public void updateClass(){
		WebScrapManager scrapManager = new WebScrapManager(this);
		scrapManager.updateClass();
	}
	
	
	// GETTER AND SETTERS
	public String getName(){
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getGradeSourceLink() {
		return gradeSource_link;
	}

	public void setGradeSourceLink(String gradeSource_link) {
		this.gradeSource_link = gradeSource_link;
	}

	public int getGradeSourceNumber() {
		return gradeSource_number;
	}

	public void setGradeSourceNumber(int gradeSource_number) {
		this.gradeSource_number = gradeSource_number;
	}

	public ArrayList<String> getSectionNames() {
		return sectionNames;
	}

	public void setSectionNames(ArrayList<String> sectionNames) {
		this.sectionNames = sectionNames;
	}

	public ArrayList<String> getSectionPercents() {
		return sectionPercents;
	}

	public void setSectionPercents(ArrayList<String> sectionPercents) {
		this.sectionPercents = sectionPercents;
	}


	
	
	
	
	
}
