package com.sapient.javaintern.DatabaseEngine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sapient.databaseEngine.fileData.ExtractFileData;
import com.sapient.databaseEngine.parametersQuery.ExtractionQuery;


public class App 
{
    public static void main( String[] args )
    {
        Scanner sc = new Scanner(System.in);
		try {
			// making object of QueryParameter
			ExtractionQuery extqobj = new ExtractionQuery();
			System.out.println("Enter query");
			// calling set query method

			//String query = sc.nextLine();
			String query = "select * from ipl.cvs ;";
			extqobj.setQuery(query);
			System.out.println("You have entered");
			// calling get query method
			System.out.println(extqobj.getQuery());
			// get the base part
			System.out.println(extqobj.setBasePart(query));
			// printing after where part
			System.out.println(extqobj.setAfterWherePart(query));
			// setting file name
			System.out.println(extqobj.setFileName(query));
			// setting the conditions of where
			// getting conditions
			String[] s = extqobj.setDesired(extqobj.setBasePart(query));
			// getting no of fields in conditions
			int noOfFields = s.length;
			System.out.println(noOfFields);

			// creating object of file data handler
			ExtractFileData f = new ExtractFileData();
			// calling function to read the file and determining the data type of header
			f.setDataType();
			// getting all the records in an array list
			ArrayList<String> data = new ArrayList<String>();
			data = f.getFileRecords();
			// getting the header of file
			String header = data.get(0);
			System.out.println(header);
			// splitting the header
			String[] headerSplit = header.split(",");
			// getting after where part
			String temp = extqobj.setAfterWherePart(query);
			if (temp == null) {

				if (s.length == 1 && s[0].equals("*")) {
					// iterating the data and getting the desired output for command
					// select * from ipl.csv ;
					Iterator<String> iterator = data.iterator();
					System.out.println("List elements : ");

					while (iterator.hasNext())
						System.out.print(iterator.next() + "\n");

				} else {
					// iterating the data and getting the desired output for command
					// select city id from ipl.csv ;
					int index = 0;
					for (index = 0; index < headerSplit.length; index++) {
						String element = headerSplit[index];
						for (int k = 0; k < noOfFields; k++) {
							if (element.equals(s[k])) {
								for (int j = 0; j < 578; j++) {
									String[] raw = data.get(j).split(",");
									System.out.println(raw[index]);
								}
							}
						}
					}
				}
			}

			else {
				// iterating the data and getting the desired output for command
				// select city id from ipl.csv where conditions;

				ArrayList<String> conditions = new ArrayList<String>();
				conditions = extqobj.setConditions(extqobj.setAfterWherePart(query));

				ArrayList<String> conditionName = new ArrayList<String>();
				ArrayList<String> conditionoperator = new ArrayList<String>();
				ArrayList<String> conditionActual = new ArrayList<String>();
				String s1 = extqobj.setAfterWherePart(query);
				if (s1 != null) {
					Pattern p2 = Pattern.compile("([A-Za-z0-9]+[ ]?)(<=|>=|<>|=|>|<)([ ]?[']?)([A-Za-z0-9]+)([']?)");
					Matcher m2 = p2.matcher(s1);
					// getting all the desired conditions in arraylist
					while (m2.find()) {
						conditionName.add(m2.group(1));
						conditionoperator.add(m2.group(2));
						conditionActual.add(m2.group(4));
					}
				}
				System.out.println(conditions);
				System.out.println(conditionName);
				System.out.println(conditionoperator);
				System.out.println(conditionActual);
				// reading the different component of a where query
				int index = 0;
				int[] indexarray = new int[10];
				for (index = 0; index < headerSplit.length; index++) {
					String element = headerSplit[index];
					for (int j = 0; j < conditionName.size(); j++) {
						if (element.equals(conditionName.get(j))) {
							indexarray[j] = index;
							System.out.println(index);
						}
					}
				}
			}

		} catch (Exception e) {
			// catching the exceptions
			System.out.println("incorrect query format");
		} finally {
			sc.close();
		}
    }
}
