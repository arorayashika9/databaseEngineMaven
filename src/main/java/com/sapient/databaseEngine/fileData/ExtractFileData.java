package com.sapient.databaseEngine.fileData;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ExtractFileData {
	private ArrayList<String> fileStore = new ArrayList<String>();
	private HashMap<String, String> mapDataType = new HashMap<String, String>();

	public void setDataType() {

		try {
			//reading the csv file
			FileReader reader = new FileReader("ipl.csv");
			BufferedReader bfreader = new BufferedReader(reader);
			//reading line by line
			String line = bfreader.readLine();
			while (line != null) {
				//adding each line to array list
				fileStore.add(line);
				line = bfreader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		//splitting the header field
		String[] splitLine1 = fileStore.get(1).split(",");
		String[] key = fileStore.get(0).split(",");

		//determining the data type of each field
		ArrayList<String> dataType = new ArrayList<String>();
		for (String data : splitLine1) {
			try {
				Integer.parseInt(data);
				dataType.add("int");
			} catch (Exception e) {
				dataType.add("string");
			}
		}

		//putting key value pair in the map
		for (int i = 0; i < 17; i++) {
			mapDataType.put(key[i], dataType.get(i));
		}
	}

	//returning the data types
	public HashMap<String, String> getDataType() {
		return mapDataType;
	}
	//returning all the data
	public ArrayList<String> getFileRecords() {
		return fileStore;
	}
}
