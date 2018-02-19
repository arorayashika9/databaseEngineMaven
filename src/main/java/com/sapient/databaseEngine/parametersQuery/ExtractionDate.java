package com.sapient.databaseEngine.parametersQuery;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import com.sapient.databaseEngine.fileData.ExtractFileData;

public class ExtractionDate {
	private ArrayList<Date> extractedDate = new ArrayList<Date>();

	public void setDateParsing() {
		//reading the file using file data handler object
		ExtractFileData fh = new ExtractFileData();
		ArrayList<String> fileData = new ArrayList<String>();
		ArrayList<String> tempDate = new ArrayList<String>();
		//setting all the records
		fh.setDataType();
		//getting all the records in an array list
		fileData = fh.getFileRecords();
		String res = String.join(",", fileData);
		//regex to get all the date
		Pattern p = Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}");
		Matcher m = p.matcher(res);

		while (m.find()) {
			tempDate.add(m.group());
		}

		String res1 = String.join(",", tempDate);
		String[] split = res1.split(",");
		//parsing the date into date object
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for (String data : split) {
			try {
				Date today = sdf.parse(data);
				extractedDate.add(today);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}


	}
	//getting the parsed date
	public ArrayList<Date> getextractedDate() {
		return extractedDate;
	}
}
