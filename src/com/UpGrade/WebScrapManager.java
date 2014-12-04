package com.UpGrade;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.os.AsyncTask;

public class WebScrapManager {

	private UpGradeClass upgradeClass;
	private String url;
	private int number;
	private ArrayList<String> currentSectionNames;
	private ArrayList<String> currentSectionPercents;

	
	public WebScrapManager(UpGradeClass upgradeClass){
		this.upgradeClass = upgradeClass;
	}
	
	public void updateClass(){
		url = upgradeClass.getGradeSourceLink();
		number = upgradeClass.getGradeSourceNumber();

		new parseGradeSource().execute();

		upgradeClass.setSectionNames(currentSectionNames);
		upgradeClass.setSectionPercents(currentSectionPercents);
		
	}
	

	public class parseGradeSource extends AsyncTask<Void, Void, Void> {

		String response;

		@Override
		protected Void doInBackground(Void... params) {
			Document doc;
			try {
				doc = Jsoup.connect(url).get();
				Elements tables = doc.select("table");
				System.out.println(number);

				for (Element table : tables) {

					int current;

					for (Element row : table.select("tr")) {

						// get the first columns value
						Elements columns = row.select("td");

						// get rid of other tables
						if (columns.size() < 1) {
							continue;
						}

						String secret_number = columns.get(0).text();

						// get the section names
						if (secret_number.equals("Secret Number")) {
							for (Element column : columns) {
								if(!(column.text().equals("Secret Number")))
								currentSectionNames.add(column.text());
							}
						}

						//get the current
						try {
							current = Integer.parseInt(secret_number);
						} catch (NumberFormatException e) {
							continue;
						}

						// if equals, then this is correct row.
						if (current == number) {
							for (Element column : columns) {
								String columnText = column.text();
								//check if it is percent column
								if (columnText.contains("%")) {
									currentSectionPercents.add(column.text());

								}
							}
							return null;
						}
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;

		}

		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
		}

	}

	
}
