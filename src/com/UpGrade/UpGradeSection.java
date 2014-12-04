package com.UpGrade;

public class UpGradeSection {

	private String sectionName;
	private String sectionPercent;
	
	public UpGradeSection(String sectionN, String sectionP){
		sectionName = sectionN;
		sectionPercent = sectionP;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public String getSectionPercent() {
		return sectionPercent;
	}

	public void setSectionPercent(String sectionPercent) {
		this.sectionPercent = sectionPercent;
	}

}
