package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import DBase.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;

public class GetChartData {
	Connection connect = Database.connectDB();
	PreparedStatement prepare;
	ResultSet result;
	
	private ObservableList<Data> yearData = FXCollections.observableArrayList();
	ObservableList<PieChart.Data> pieList = FXCollections.observableArrayList();

	
   XYChart.Series getSexLineChart(){
	   XYChart.Series sexLine =new  XYChart.Series();
	   String sql = "SELECT sex, COUNT(stud_id) FROM student GROUP BY sex ORDER BY registered_on ASC LIMIT 7";
		try { 
			
			prepare = connect.prepareStatement(sql);
			result = prepare.executeQuery();

			while (result.next()) {
				sexLine.getData().add(new XYChart.Data(result.getString(1), result.getInt(2)));

			}
			return sexLine;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;


   }

   XYChart.Series getSexBarChart(){
	   XYChart.Series sexChart = new XYChart.Series();
	   String sql = "SELECT sex, COUNT(stud_id) FROM student GROUP BY sex ORDER BY registered_on ASC LIMIT 7";
		try { 
			
			prepare = connect.prepareStatement(sql);
			result = prepare.executeQuery();

			while (result.next()) {
				sexChart.getData().add(new XYChart.Data(result.getString(1), result.getInt(2)));

			}
			return sexChart;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;


   }

public ObservableList<Data> getYearPieChart() {
	
	String sql = "SELECT registered_on, COUNT(stud_id) FROM student GROUP BY registered_on ";
	try {
		prepare = connect.prepareStatement(sql);
		result = prepare.executeQuery();

		while (result.next()) {
			yearData.add(new PieChart.Data(result.getString(1), result.getInt(2)));
		}
		return yearData;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	return null;
	
	
}

public ObservableList<Data> getDeptPieChart() {
	String sql = "SELECT department, COUNT(stud_id) FROM student GROUP BY department ";
	try {
		prepare = connect.prepareStatement(sql);
		result = prepare.executeQuery();

		while (result.next()) {
			pieList.add(new PieChart.Data(result.getString(1), result.getInt(2)));

		}
		return pieList;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	

	return null;
}

public XYChart.Series getDeptBarChart() {
	 XYChart.Series deptBar = new XYChart.Series();
	String sql = "SELECT department, COUNT(stud_id) FROM student GROUP BY department ";
	try { 
		
		prepare = connect.prepareStatement(sql);
		result = prepare.executeQuery();

		while (result.next()) {
			deptBar.getData().add(new XYChart.Data(result.getString(1), result.getInt(2)));

		}
		return deptBar;

	} catch (Exception e) {
		e.printStackTrace();
	}
	return null;
}

public XYChart.Series getDeptLineChart() {
	String sql = "SELECT department, COUNT(stud_id) FROM student GROUP BY department ";
	 XYChart.Series deptLine = new XYChart.Series();

	try { 
		
		prepare = connect.prepareStatement(sql);
		result = prepare.executeQuery();

		while (result.next()) {
			deptLine.getData().add(new XYChart.Data(result.getString(1), result.getInt(2)));

		}
		return deptLine;

	} catch (Exception e) {
		e.printStackTrace();
	}
	return null;
}
}
