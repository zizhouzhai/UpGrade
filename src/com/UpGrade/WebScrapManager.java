package com.UpGrade;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebScrapManager {

	private UpGradeClass upgradeClass;
	private String url;
	private int number;
	private ArrayList<String> currentSectionNames;
	private ArrayList<String> currentSectionPercents;

	public WebScrapManager(String gs_link) {

		url = gs_link;
		number = 0;
		currentSectionNames = new ArrayList<String>();
		currentSectionPercents = new ArrayList<String>();

	}

	public WebScrapManager(UpGradeClass upgradeClass) {
		this.upgradeClass = upgradeClass;
		currentSectionNames = new ArrayList<String>();
		currentSectionPercents = new ArrayList<String>();
	}

	public UpGradeClass updateClass() {
		url = upgradeClass.getGradeSourceLink();
		number = upgradeClass.getGradeSourceNumber();

		Document doc;
		try {
			doc = Jsoup.connect(url).get();
			Elements tables = doc.select("table[cellpadding=3][cellspacing=0]");
			System.out.println("table size: " +tables.size());

			for (Element table : tables) {

				int current;	

				for (Element row : table.select("tr")) {

					// get the first columns value
					Elements columns = row.select("td");

					String secret_number = columns.get(0).text();

					// get the section names
					if (secret_number.equals("Secret Number")) {
						for (Element column : columns) {
							if (!(column.text().equals("Secret Number")))
								currentSectionNames.add(column.text());
						}
					}

					// get the current
					try {
						current = Integer.parseInt(secret_number);
					} catch (NumberFormatException e) {
						continue;
					}

					// if equals, then this is correct row.
					if (current == number) {
						for (Element column : columns) {
							String columnText = column.text();
							// check if it is percent column
							if (columnText.contains("%")) {
								currentSectionPercents.add(column.text());

							}
						}
						currentSectionNames.remove(0);
						upgradeClass.setSectionNames(currentSectionNames);
						upgradeClass.setSectionPercents(currentSectionPercents);
						return upgradeClass;
					}
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return upgradeClass;

	}

	public ArrayList<Integer> parseSection(String sectionName) {

		ArrayList<Integer> toReturn = new ArrayList<Integer>();
		Document doc;
		try {
			doc = Jsoup.connect(url).get();
			Elements tables = doc.select("table[cellpadding=3][cellspacing=0]");

			// for each table
			for (Element table : tables) {

				Elements rows = table.select("tr");	
				
				//get the first row to find position of section name
				Element firstRow = rows.get(0);
				
				int count = -1;
				//get all the columns in firstrow
				for(Element column : firstRow.select("td")){
					
					//find the position of the one.
					if(column.text().equals(sectionName)){
						break;
					}
					else
						count++;
				}
				
				//get the thirdrow
				Element thirdRow = rows.get(2);
				
				//second counter to see how many columns across
				int finalyPos = 0;
				for(Element column : thirdRow.select("td")){
						
					//if % column, add
					if(column.text().contains("%")){
						count --;
						if(count == 0){
							break;
						}
					}
					finalyPos++;
				}
				
				Element currentRow;
				Element currentColumn;
				//loop through all the rows, starting from row 4.
				for(int i = 3;i<rows.size();i++){
					//get the row
					currentRow = rows.get(i);
					//get the column
					currentColumn = currentRow.select("td").get(finalyPos);
					//get column text
					String currentColumn_text = currentColumn.text();
					//remove %
					currentColumn_text = currentColumn_text.replace("%", "");
					//add number to arraylist
					toReturn.add((int)(Double.parseDouble(currentColumn_text)));
				}

				return toReturn;

			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;

	}

}
