package com.sapient.databaseEngine.parametersQuery;
import java.util.*;
import java.util.regex.*;

public class ExtractionQuery {
	private String inputQuery = null;
	private String first = null;
	private String last = null;
	private String[] split = null;
	private ArrayList<String> fileName = new ArrayList<String>();
	private ArrayList<String> conditions = new ArrayList<String>();
	private ArrayList<String> operators = new ArrayList<String>();
	private String[] selectedFieldsSplit = null;
	private String orderByField = null;
	private String groupByField = null;
	private ArrayList<String> aggregate = new ArrayList<String>();

	// function to get the query from user
	public void setQuery(String query) {
		inputQuery = query;
	}

	// function to get the query from user
	public String getQuery() {
		return inputQuery;
	}

	// function to tokenize the query
	public String[] tokenizeQuery(String query) {

		if (!query.equals("")) {
			split = query.split("[ ,]");
		} else {
			split = null;
		}
		return split;
	}



	// function to get the base part of query
	public String setBasePart(String query) {

		if (!query.equals("")) {
			int indexOfWhere = query.indexOf("where");
			int indexOfSemiColon = query.indexOf(";");
			if (indexOfWhere != -1) {
				first = query.substring(0, indexOfWhere - 1);
			} else if (indexOfSemiColon != -1) {

				first = query.substring(0, indexOfSemiColon - 1);
			} else {
				first = null;
			}

		} else {
			first = null;
		}
		return first;

	}
	// function to get the after part of query
	public String setAfterWherePart(String query) {
		if (!query.equals("")) {
			int indexOfWhere = query.indexOf("where");
			if (indexOfWhere != -1) {
				last = query.substring(indexOfWhere + 6, query.length());

			} else {
				last = null;
			}
		} else {
			last = null;
		}
		return last;
	}

	
	//function to set the file name
	public ArrayList<String> setFileName(String query) {
		if (!query.equals("")) {
			Pattern p = Pattern.compile("[a-zA-Z0-9]+\\.(csv)");
			Matcher m = p.matcher(query);
			while (m.find()) {
				fileName.add(m.group());
			}
		}
		return fileName;
	}
	
	//function to get the conditions in the query
	public ArrayList<String> setConditions(String query) {
		if (query != null) {
			Pattern p2 = Pattern.compile("([A-Za-z0-9]+[ ]?)((<=)|(>=)|(<>)|=|>|<)([ ]?[']?[A-Za-z0-9]+[']?)");
			Matcher m2 = p2.matcher(query);

			while (m2.find()) {
				conditions.add(m2.group());
			}
		}
		return conditions;
	}
	
	
	//function to get the operators in the query
	public ArrayList<String> setOperator(String query) {
		if (query != null) {
			Pattern p3 = Pattern.compile("(and)|(or){2}|(not)");
			Matcher m3 = p3.matcher(query);

			while (m3.find()) {
				operators.add(m3.group());
			}
		}
		return operators;
	}
	
	//function to get all the condition of where clause
	public String[] setDesired(String query) {
		if (query != null) {
			int index_of_from = query.indexOf("from");
			int index_of_select = query.indexOf("select");

			String selected_fields = query.substring(index_of_select + 7, index_of_from);

			if (!(selected_fields.equals("*"))) {
				selectedFieldsSplit = selected_fields.split("[ ,]");
			}
		}
		return selectedFieldsSplit;
	}
	
	//finding the order by field of the query
	public String setOrderBy(String query) {
		if (query != null) {
			Pattern p4 = Pattern.compile("(order by)[ ]?[A-Za-z_]+");
			Matcher m4 = p4.matcher(query);
			if (m4.find()) {

				m4.group();
				int index_of_by = m4.group().indexOf("by");
				orderByField = m4.group().substring(index_of_by + 3, m4.group().length());
			}

		}
		return orderByField;
	}
	
	//finding the group by field of the query
	public String setGroupBy(String query) {
		if (query != null) {
			Pattern p5 = Pattern.compile("(group by)[ ]?[A-Za-z_]+");
			Matcher m5 = p5.matcher(query);

			if (m5.find()) {
				m5.group();
				int index_of_by1 = m5.group().indexOf("by");
				groupByField = m5.group().substring(index_of_by1 + 3, m5.group().length());
			}

		}
		return groupByField;
	}
	
	//finding the aggregate field the query
	public ArrayList<String> setAggregate(String query) {
		if (query != null) {
			Pattern p6 = Pattern.compile("(sum|avg|min|max)(\\([A-Za-z_]*\\))");
			Matcher m6 = p6.matcher(query);
			while (m6.find()) {
				aggregate.add(m6.group());
			}
		}
		return aggregate;
	}
}
