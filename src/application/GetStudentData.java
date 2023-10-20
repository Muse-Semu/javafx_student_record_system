package application;

import java.sql.*;
import DBase.Database;
import dataModels.StudentData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GetStudentData {
	Connection connect = Database.connectDB();
	ResultSet result;
	PreparedStatement pst;
	ValidationWithAlert vl = new ValidationWithAlert();
	private ObservableList<StudentData> studentList = FXCollections.observableArrayList();

	public int countAllStudent() {
		try {
			int total = 0;
			result = connect.createStatement().executeQuery("SELECT COUNT(stud_id) FROM student");

			while (result.next()) {
				total = result.getInt("COUNT(stud_id)");
			}
			return total;
		} catch (SQLException e) {

			vl.getErrorAlert(e.getMessage());
		}
		return 0;
	}

	public int countMales() {
		try {
			int total = 0;
			result = connect.createStatement().executeQuery("SELECT COUNT(stud_id) FROM student where sex = 'Male'");

			while (result.next()) {
				total = result.getInt("COUNT(stud_id)");
			}

			return total;

		} catch (SQLException e) {

			vl.getErrorAlert(e.getMessage());
		}
		return 0;
	}

	public int countFemale() {
		try {
			int total = 0;
			result = connect.createStatement().executeQuery("SELECT COUNT(stud_id) FROM student where sex = 'Female'");

			while (result.next()) {
				total = result.getInt("COUNT(stud_id)");
			}
			return total;
		} catch (SQLException e) {

			vl.getErrorAlert(e.getMessage());
		}
		return 0;
	}

	public ObservableList<StudentData> getFemaliList() {
		studentList.clear();
		try {

			result = connect.createStatement().executeQuery("SELECT * FROM student where sex = 'Female'");

			while (result.next()) {
				studentList.add(
						new StudentData(result.getInt("stud_id"), result.getString("fname"), result.getString("lname"),
								result.getString("sex"), result.getString("department"), result.getString("phone"),
								result.getString("email"), result.getDate("registered_on"), result.getInt("age")));

			}
			return studentList;

		} catch (SQLException e) {

			vl.getErrorAlert(e.getMessage());
		}

		return null;
	}

	public ObservableList<StudentData> getMaleiList() {
		studentList.clear();
		try {

			result = connect.createStatement().executeQuery("SELECT * FROM student where sex = 'Male'");

			while (result.next()) {
				studentList.add(
						new StudentData(result.getInt("stud_id"), result.getString("fname"), result.getString("lname"),
								result.getString("sex"), result.getString("department"), result.getString("phone"),
								result.getString("email"), result.getDate("registered_on"), result.getInt("age")));

			}

			return studentList;

		} catch (SQLException e) {

			vl.getErrorAlert(e.getMessage());
		}

		return null;
	}

	public ObservableList<StudentData> getStudentList() {
		studentList.clear();
		try {
			result = connect.createStatement().executeQuery("SELECT * FROM student  ORDER BY fname,lname");
			while (result.next()) {

				studentList.add(
						new StudentData(result.getInt("stud_id"), result.getString("fname"), result.getString("lname"),
								result.getString("sex"), result.getString("department"), result.getString("phone"),
								result.getString("email"), result.getDate("registered_on"), result.getInt("age")));
			}
			return studentList;
		} catch (Exception e) {

			vl.getErrorAlert(e.getMessage());
		}

		return null;
	}

}
