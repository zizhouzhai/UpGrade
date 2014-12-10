package com.UpGrade;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 
 * Represents one class. Each class should hold its own section information,
 * name, gradesource link and number
 * 
 * @author Wingo's
 *
 */
public class UpGradeClass implements Serializable{

	private static final long serialVersionUID = 944563812578072380L;
	private String name;
	private String gradeSource_link;
	private int gradeSource_number;
	private ArrayList<String> sectionNames = new ArrayList<String>();
	private ArrayList<String> sectionPercents= new ArrayList<String>();

	
	
	/**
	 * One param constructor
	 * @param name name of the class
	 */
	public UpGradeClass(String name){
		this.name = name;
		this.setGradeSourceLink(null);
		this.setGradeSourceNumber(0);
	}
	
	/**
	 * Default constructor of UpGradeClass. Name will be blank.
	 */
	public UpGradeClass(){
		name = "blank";
		this.setGradeSourceLink(null);
		this.setGradeSourceNumber(0);
	}
	
	public UpGradeClass(String name, String link, int number){
		this.name = name;
		this.gradeSource_link = link;
		this.gradeSource_number = number;
	}

	/**
	 * Creates a WebScrapManager which will update this class. Should be called
	 * in a new thread.
	 */
	public UpGradeClass updateClass(){
		WebScrapManager scrapManager = new WebScrapManager(this);
		return scrapManager.updateClass();
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


	@Override
    public boolean equals(Object other) {
        if (!(other instanceof UpGradeClass)) {
            return false;
        }
        UpGradeClass otherClass = (UpGradeClass) other;
        return this.name.equals(otherClass.name) &&
               this.gradeSource_link.equals(otherClass.gradeSource_link) &&
               this.gradeSource_number == otherClass.gradeSource_number;
    }
	
	
	
	
}
