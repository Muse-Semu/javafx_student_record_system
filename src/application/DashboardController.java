package application;

import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.Date;
import DBase.Database;
import dataModels.DepartmentData;
import dataModels.StudentData;
import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.*;
import javafx.scene.chart.*;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class DashboardController  implements Initializable{
	public DashboardController() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@FXML
	private BorderPane theWholePane;
	@FXML
	private AnchorPane totalStuPane;
	@FXML
	private BarChart<?, ?> studentBarChart;

	@FXML
	private BarChart<?, ?> departmentBarChart;
	@FXML
	private LineChart<?, ?> studentDepartmentChart;
	@FXML
	private LineChart<?, ?> studentSexLineChart;
	@FXML
	private Label totalNumOfStudent;
	@FXML
	private AnchorPane adminSideMenu;
	@FXML
	private Button addBtn;
	@FXML
	private Label loggedUserLabel;
	@FXML
	private Button addStudentBtn;
	@FXML
	private AnchorPane addStudentPane;
	@FXML
	private AnchorPane studentStatusPane;
	@FXML
	private Label addStudentErrorLabel;
	@FXML
	private Label studentFormLabel;
	@FXML
	private PieChart studentPieChart;
	@FXML
	private AnchorPane manageStudentPane;
	@FXML
	private Button logoutBtn;
	@FXML
	private Button editConfirmBtn;
	@FXML
	private TextField searchStudentField;
	@FXML
	private Button clearAllBtn;
	@FXML
	private Button closeBtn;
	@FXML
	private ChoiceBox<String> department;
	@FXML
	private TextField lastNameField;
	@FXML
	private TextField nameField;
	@FXML
	private TextField phone;
	@FXML
	private TextField email;
	@FXML
	private TextField age;
	@FXML
	private ChoiceBox<String> sex;
	private String[] chooseSex = { "Male", "Female" };
	@FXML
	private TableView<StudentData> studentListTable;
	@FXML
	private TableColumn<StudentData, Integer> ageCol;
	@FXML
	private TableColumn<StudentData, Date> dateCol;
	@FXML
	private TableColumn<StudentData, String> deptCol;
	@FXML
	private TableColumn<StudentData, String> emailCol;
	@FXML
	private TableColumn<String, ?> fnameCol;
	@FXML
	private TableColumn<StudentData, Integer> sIdCol;
	@FXML
	private TableColumn<StudentData, String> lnameCol;
	@FXML
	private TableColumn<StudentData, String> phoneCol;
	@FXML
	private TableColumn<StudentData, String> sexCol;
	@FXML
	private Label numOfFemale;
	@FXML
	private Label numOfMale;
	private Connection connect;
	private PreparedStatement prepare;
	private ResultSet result;
	private Stage stage;
	private Parent root;
	private Scene scene;
	@FXML
	private Button addDeptBtn;
	@FXML
	private TextField deptCapacityField;
	@FXML
	private Label deptErrorLabel;
	@FXML
	private Label deptFormLabel;
	@FXML
	private AnchorPane deptFormPane;
	@FXML
	private TextField deptNameField;
	@FXML
	private Button editDeptConfirmBtn;
	
	GetStudentData st =  new  GetStudentData();
	GetChartData cd = new GetChartData();
	Alert alert;
	ObservableList<PieChart.Data> pieList = FXCollections.observableArrayList();
	ObservableList<PieChart.Data> yearData = FXCollections.observableArrayList();
	private ObservableList<DepartmentData> deptList = FXCollections.observableArrayList();
	private ObservableList<StudentData> studentList = FXCollections.observableArrayList();


	@FXML
	TextField searchDeptField;
	@FXML
	TableView<DepartmentData> departmentListTable;
	@FXML
	TableColumn<DepartmentData, Date> dDateCol;
	@FXML
	TableColumn<DepartmentData, String> dNameCol;
	@FXML
	TableColumn<DepartmentData, Integer> dIdCol;
	@FXML
	TableColumn<DepartmentData, Integer> dCapacityCol;
	@FXML
	AnchorPane manageDepartmentPane;

	public ValidationWithAlert vl = new ValidationWithAlert();

	@FXML
	private PieChart datePieChart;
	private XYChart.Series chart,line;

	private void getTotalNumberOfStudent() {
		totalNumOfStudent.setText(String.valueOf(st.countAllStudent()));
	}

	void getNumberOfFemale() {
		numOfFemale.setText(String.valueOf(st.countFemale()));	
	}

	void getNumberOfMale() {
		numOfMale.setText(String.valueOf(st.countMales()));
	}
	
	public void message(String msg) {
		System.out.println(msg);
	}

	private void getAllStudentList() {
		studentList = st.getStudentList();
		sIdCol.setCellValueFactory(new PropertyValueFactory<>("studId"));
		fnameCol.setCellValueFactory(new PropertyValueFactory<>("fname"));
		lnameCol.setCellValueFactory(new PropertyValueFactory<>("lname"));
		sexCol.setCellValueFactory(new PropertyValueFactory<>("sex"));
		emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
		phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
		dateCol.setCellValueFactory(new PropertyValueFactory<>("registeredData"));
		ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));
		deptCol.setCellValueFactory(new PropertyValueFactory<>("dept"));
		studentListTable.setItems(st.getStudentList());
		
	}

	private void getAllDept() {
		connect = Database.connectDB();
		deptList.clear();
		try {
			result = connect.createStatement().executeQuery("SELECT * FROM department  ORDER BY dName");
			while (result.next()) {

				deptList.add(new DepartmentData(result.getInt("dId"), result.getInt("dCapacity"),
						result.getString("dName"), result.getDate("registered_on")));
				dIdCol.setCellValueFactory(new PropertyValueFactory<>("depId"));
				dNameCol.setCellValueFactory(new PropertyValueFactory<>("depName"));
				dCapacityCol.setCellValueFactory(new PropertyValueFactory<>("depCapacity"));
				dDateCol.setCellValueFactory(new PropertyValueFactory<>("reDate"));
				departmentListTable.setItems(deptList);

			}
		} catch (Exception e) {

			vl.getErrorAlert(e.getMessage());
		}

	}

	void getAllDeptName() {
		connect = Database.connectDB();
		department.getItems().clear();
		try {
			result = connect.createStatement().executeQuery("SELECT dName FROM department  ORDER BY dName");
			while (result.next()) {
				department.getItems().add(result.getString(1));
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	@FXML
	void getFemaleStudent(MouseEvent event) {
		manageStudent(null);
		studentListTable.setItems(st.getFemaliList());
		searchStedent();
	}

	@FXML
	void getMaleStudent(MouseEvent event) {	
		manageStudent(null);
		studentListTable.setItems(st.getMaleiList());	
		searchStedent();
	}

	@FXML
	public
	void addStudent(ActionEvent event) {

		connect = Database.connectDB();

		Date date = new Date();

		java.sql.Date sqlDate = new java.sql.Date(date.getTime());

		if (isValidInput()) {
			int ag = Integer.parseInt(age.getText());
			try {
				String insert = "insert into student  values(?,?,?,?,?,?,?,?,?);";
				prepare = connect.prepareStatement(insert);

				prepare.setInt(1, 0);
				prepare.setString(2, nameField.getText());
				prepare.setString(3, lastNameField.getText());
				prepare.setString(4, email.getText());
				prepare.setString(5, department.getValue());
				prepare.setString(6, phone.getText());
				prepare.setString(7, sex.getValue());
				prepare.setDate(8, sqlDate);
				prepare.setInt(9, ag);

				if (prepare.executeUpdate() == 1) {

					vl.getSuccessAlert("Student Added Successfuly");
					clearAllInput(event);
					studentList.clear();
					getAllStudentList();
					hiddenAddStudentPane(event);
					sexDataChart();
					getTotalNumberOfStudent();
					getNumberOfFemale();
					getNumberOfMale();
					departmnetCharts();
					yearPieChart();
				}
				connect.close();

			} catch (Exception e) {
				vl.getErrorAlert(e.getMessage());
			}
		} else if (!vl.checkPhone(phone.getText())) {
			addStudentErrorLabel.setText("Invalid Phone ");
		} else if (!vl.checkEmail(email.getText())) {
			addStudentErrorLabel.setText("Invalid Email");
		} else if (!vl.checkAge(age.getText())) {
			addStudentErrorLabel.setText("Invalid Age");
		} else {
			addStudentErrorLabel.setText("All Fields Are required");

		}

	}

	private boolean isValidInput() {
		boolean bool = false;

		String fname = nameField.getText();
		String phoneNum = phone.getText();
		String lname = lastNameField.getText();
		String dep = department.getValue();
		String emailadd = email.getText();
		String sx = sex.getValue();

		if ((!fname.isEmpty() && !lname.isEmpty() && !phoneNum.isEmpty() && !emailadd.isEmpty() && !sx.isEmpty()
				&& !dep.isEmpty() && !age.getText().isEmpty())) {

			if (vl.checkPhone(phoneNum) && vl.checkEmail(emailadd) && vl.checkAge(age.getText())) {
				bool = true;
			}

		}
		return bool;

	}

	@FXML
	void clearAllInput(ActionEvent event) {
		nameField.setText("");
		lastNameField.setText("");
		sex.setValue("");
		phone.setText("");
		email.setText("");
		age.setText("");
		department.setValue("");
		addStudentErrorLabel.setText("");
		deptErrorLabel.setText("");
		deptCapacityField.setText("");
		deptNameField.setText("");
	}

	@FXML
	void showAddStudentPane(ActionEvent event) {
		
		addBtn.setVisible(true);
		editConfirmBtn.setVisible(false);
		studentFormLabel.setText("Add Student Here");
		if (studentStatusPane.isVisible()) {
			studentStatusPane.setDisable(true);
			studentStatusPane.setOpacity(0.1);
			manageStudentPane.setVisible(false);
			manageDepartmentPane.setVisible(false);

		} else if (manageStudentPane.isVisible()) {
			manageStudentPane.setDisable(true);
			manageStudentPane.setOpacity(0.1);
			studentStatusPane.setVisible(false);
			manageDepartmentPane.setVisible(false);
		} else if(manageDepartmentPane.isVisible()){
			manageDepartmentPane.setDisable(true);
			manageDepartmentPane.setOpacity(0.1);
			studentStatusPane.setVisible(false);
			manageStudentPane.setVisible(false);
		}
		{

		}
		addStudentPane.setVisible(true);
		adminSideMenu.setDisable(true);

	}

	@FXML
	void hiddenAddStudentPane(ActionEvent event) {
		addStudentPane.setVisible(false);
		adminSideMenu.setDisable(false);
		clearAllInput(event);
		if (studentStatusPane.isVisible()) {
			studentStatusPane.setDisable(false);
			studentStatusPane.setOpacity(1);
			manageStudentPane.setVisible(false);
            manageDepartmentPane.setVisible(false);
		} else if (manageStudentPane.isVisible()) {
			manageStudentPane.setDisable(false);
			manageStudentPane.setOpacity(1);
			studentStatusPane.setVisible(false);
			manageDepartmentPane.setVisible(false);
		} else if(manageDepartmentPane.isVisible()) {
			manageDepartmentPane.setDisable(false);
			manageDepartmentPane.setOpacity(1);
			studentStatusPane.setVisible(false);
			manageStudentPane.setVisible(false);
		}
		{

		}

	}

	@FXML
	void editStudentData(ActionEvent event) {
		try {
			int index = getRowIndex();

			if (index != -1) {
				nameField.setText(studentListTable.getItems().get(index).getFname());
				lastNameField.setText(studentListTable.getItems().get(index).getLname());
				sex.setValue(studentListTable.getItems().get(index).getSex());
				phone.setText(studentListTable.getItems().get(index).getPhone());
				email.setText(studentListTable.getItems().get(index).getEmail());
				age.setText(String.valueOf(studentListTable.getItems().get(index).getAge()));
				department.setValue(studentListTable.getItems().get(index).getDept());

				showAddStudentPane(event);
				addBtn.setVisible(false);
				editConfirmBtn.setVisible(true);
				studentFormLabel.setText("Edit Student Info");

			} else {
				vl.getErrorAlert("Please Select row ");
			}
		} catch (Exception e) {

			vl.getErrorAlert("Error occered");

		}

	}
	
	@FXML
	void editDepartment(ActionEvent event){
		try {
			int index = departmentListTable.getSelectionModel().getSelectedIndex();

			if (index != -1) {
//				dNameField.setText(studentListTable.getItems().get(index).getFname());

				
				deptNameField.setText(departmentListTable.getItems().get(index).getDepName());
				deptCapacityField.setText(String.valueOf(departmentListTable.getItems().get(index).getDepCapacity()));

				showDeptPane(event);
				addDeptBtn.setVisible(false);
				editDeptConfirmBtn.setVisible(true);
				deptFormLabel.setText("Edit Department Info");

			} else {
				vl.getErrorAlert("Please Select row ");
			}
		} catch (Exception e) {

			vl.getErrorAlert("Error occered");

		}

	}

	private int getRowIndex() {
		int in = studentListTable.getSelectionModel().getSelectedIndex();
		return in;
	}

	@FXML
	void manageStudent(ActionEvent event) {
		addStudentPane.setVisible(false);
		studentStatusPane.setVisible(false);
		manageStudentPane.setVisible(true);
		manageStudentPane.setDisable(false);
		manageStudentPane.setOpacity(1);
		manageDepartmentPane.setVisible(false);
		if (event != null) {
			getAllStudentList();
		}
		searchStedent();

	}
	
	@FXML
	void manageDepartment(ActionEvent event) {
		addStudentPane.setVisible(false);
		studentStatusPane.setVisible(false);
		deptFormPane.setVisible(false);
		manageStudentPane.setVisible(false);
		manageDepartmentPane.setDisable(false);
		manageDepartmentPane.setOpacity(1);
		manageDepartmentPane.setVisible(true);
		if (event != null) {
			getAllStudentList();
		}
		searchStedent();
		searchDept();

	}

	@FXML
	void goToHome(ActionEvent event) {
		addStudentPane.setVisible(false);
		studentStatusPane.setVisible(true);
		studentStatusPane.setDisable(false);
		manageStudentPane.setVisible(false);
		manageDepartmentPane.setVisible(false);
		studentStatusPane.setOpacity(1);
	}

	@FXML
	public
	void deleteStudent(ActionEvent event) {
		int index = getRowIndex();

		if (index != -1) {
			ButtonType btnClicked = vl.getConfirmAlert(
					"Delete Student " + "'" +studentListTable.getItems().get(index).getFname()+"'", "Are  you sure to Delete ");
			if (btnClicked == ButtonType.OK) {
				int sId = studentListTable.getItems().get(index).getStudId();

				connect = Database.connectDB();
				try {
					prepare = connect.prepareStatement("DELETE FROM student WHERE stud_id  ='" + sId + "';");
					if (prepare.executeUpdate() == 1) {
						vl.getSuccessAlert(
								studentListTable.getItems().get(index).getFname() + " deleted  successfully");
						studentList.clear();
						getAllStudentList();
						getNumberOfFemale();
						getNumberOfMale();
						getTotalNumberOfStudent();
						departmnetCharts();
						yearPieChart();
						sexDataChart();
					}

				} catch (Exception e) {
					vl.getErrorAlert(e.getMessage());
				}
			}
		} else {
			vl.getErrorAlert("Not selected");
		}

	}
	
	@FXML
	public
	void deleteDepartment(ActionEvent event) {
		int index = departmentListTable.getSelectionModel().getSelectedIndex();
		if (index != -1) {
			ButtonType btnClicked = vl.getConfirmAlert(
					"Delete Department " + "'"+departmentListTable.getItems().get(index).getDepName()+"'", "Are  you sure to Delete ");
			if (btnClicked == ButtonType.OK) {
				int dId = departmentListTable.getItems().get(index).getDepId();

				connect = Database.connectDB();
				try {
					prepare = connect.prepareStatement("DELETE FROM department WHERE dId  ='" + dId + "';");
					if (prepare.executeUpdate() == 1) {
						vl.getSuccessAlert(
								departmentListTable.getItems().get(index).getDepName()+ " deleted  successfully");
						deptList.clear();
						getAllDept();
						getAllDeptName();
					}

				} catch (Exception e) {
					vl.getErrorAlert(e.getMessage());
				}
			}
		} else {
			vl.getErrorAlert("Not selected");
		}


	}

	@FXML
	public
	void confirmEditStudent(ActionEvent event) {
		String fname = nameField.getText();
		String phoneNum = phone.getText();
		String lname = lastNameField.getText();
		String dep = department.getValue();
		String emailadd = email.getText();
		String sx = sex.getValue();
		int index = getRowIndex();

		if (!fname.isEmpty() && !lname.isEmpty() && !phoneNum.isEmpty() && !emailadd.isEmpty() && !sx.isEmpty()
				&& !dep.isEmpty() && !age.getText().isEmpty()) {
			int ag = Integer.parseInt(age.getText());
			try {

				ButtonType btnClicked = vl.getConfirmAlert(
						"Edit Student " + "'"+ studentListTable.getItems().get(index).getFname()+"'",
						"Are  you sure to Update ");
				if (btnClicked == ButtonType.OK) {
					int sId = studentListTable.getItems().get(index).getStudId();
					connect = Database.connectDB();
					try {
						prepare = connect.prepareStatement(
								"UPDATE  student SET fname=?,lname=?,sex=?,department=?,phone=?,email=?, age=? WHERE  stud_id  ='"
										+ sId + "';");

						prepare.setString(1, fname);
						prepare.setString(2, lname);
						prepare.setString(3, sx);
						prepare.setString(4, dep);
						prepare.setString(5, phoneNum);
						prepare.setString(6, emailadd);
						prepare.setInt(7, ag);

						if (prepare.executeUpdate() == 1) {

							vl.getSuccessAlert("Student Updated Successfuly");
							clearAllInput(event);
							studentList.clear();
							getAllStudentList();
							getNumberOfFemale();
							getNumberOfMale();
							getTotalNumberOfStudent();
							hiddenAddStudentPane(event);
							sexDataChart();
							yearPieChart();
							departmnetCharts();
						}

					} catch (Exception e) {
						vl.getErrorAlert(e.getMessage());
					}
				}

				connect.close();

			} catch (Exception e) {
				vl.getErrorAlert(e.getMessage());
			}
		} else {
			addStudentErrorLabel.setText("All Fields Are required");
		}

	}

	@FXML
	void logout(ActionEvent event) {
		System.out.println();
		ButtonType btn = vl.getConfirmAlert("Logout", "Are you sure ?");
		String sql = "UPDATE user SET is_loggedin = ? WHERE username =  '" + loggedUserLabel.getText() + "'";
		if (btn == ButtonType.OK) {
			logoutBtn.getScene().getWindow().hide();
			connect = Database.connectDB();
			try {
				prepare = connect.prepareStatement(sql);
				prepare.setInt(1, 0);
				prepare.executeUpdate();
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));

				root = loader.load();
				stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				scene = new Scene(root);
				stage.setScene(scene);
				stage.show();

			} catch (Exception e) {

				e.printStackTrace();
			}
		}

	}

	void getLoggedUser() {

		try {
			connect = Database.connectDB();
			String sql = "Select username from user where is_loggedin = 1";
			prepare = connect.prepareStatement(sql);
			result = prepare.executeQuery();
			while (result.next()) {
				loggedUserLabel.setText(result.getString("username").trim());
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void sexDataChart() {

		studentBarChart.getData().clear();
		studentSexLineChart.getData().clear();
        chart = cd.getSexBarChart();
        line = cd.getSexLineChart();
		studentSexLineChart.getData().add(line);
		studentBarChart.getData().add(chart);
		

	}

	public void departmnetCharts() {
		
		pieList.clear();
		studentPieChart.getData().clear();
		studentDepartmentChart.getData().clear();
		departmentBarChart.getData().clear();
		XYChart.Series line= cd.getDeptLineChart() ;
		XYChart.Series bar = cd.getDeptBarChart();
		
		pieList = cd.getDeptPieChart();
		pieList.forEach(
				data -> data.nameProperty().bind(Bindings.concat(data.getName(), " : ", data.pieValueProperty())));
		studentPieChart.getData().addAll(pieList);
		studentDepartmentChart.getData().add(line);
		departmentBarChart.getData().add(bar);

	}

	private void yearPieChart() {
		yearData.clear();
		datePieChart.getData().clear();
		yearData = cd.getYearPieChart();
		yearData.forEach(
				data -> data.nameProperty().bind(Bindings.concat(data.getName(), " : ", data.pieValueProperty())));
		datePieChart.getData().addAll(yearData);
	}

	private void searchStedent() {
		FilteredList<StudentData> filter = new FilteredList<>(studentList, b -> true);

		searchStudentField.textProperty().addListener((observable, oldValue, newValue) -> {
			filter.setPredicate(StudentList -> {
				if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
					return true;
				}
				String searchKey = newValue.toLowerCase();
				if (StudentList.getFname().toLowerCase().indexOf(searchKey) > -1) {
					return true;
				}
				if (StudentList.getLname().toLowerCase().indexOf(searchKey) > -1) {
					return true;
				}

				if (StudentList.getDept().toLowerCase().indexOf(searchKey) > -1) {
					return true;
				}
				if (StudentList.getEmail().toLowerCase().indexOf(searchKey) > -1) {
					return true;
				}
				if (StudentList.getPhone().toLowerCase().indexOf(searchKey) > -1) {
					return true;
				}

				else {
					return false;
				}
			});

		});
		SortedList<StudentData> sort = new SortedList<>(filter);
		sort.comparatorProperty().bind(studentListTable.comparatorProperty());
		studentListTable.setItems(sort);

	}

	private void searchDept() {
		FilteredList<DepartmentData> filter = new FilteredList<>(deptList, b -> true);

		searchDeptField.textProperty().addListener((observable, oldValue, newValue) -> {
			filter.setPredicate(list -> {
				if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
					return true;
				}
				String searchKey = newValue.toLowerCase();
				if (list.getDepName().toLowerCase().indexOf(searchKey) > -1) {
					return true;
				}
				

				else {
					return false;
				}
			});

		});
		SortedList<DepartmentData> sort = new SortedList<>(filter);
		sort.comparatorProperty().bind(departmentListTable.comparatorProperty());
		departmentListTable.setItems(sort);


	}
	
	@FXML
	void showTotalStudent(MouseEvent event) {
		manageStudent(null);

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		sex.getItems().addAll(chooseSex);
		departmnetCharts();
		yearPieChart();
		getAllDept();
		getLoggedUser();
		getAllStudentList();
		getTotalNumberOfStudent();
		getNumberOfFemale();
		getNumberOfMale();
		getAllDeptName();
		sexDataChart();

	}

	@FXML
	void hideDeptPane(ActionEvent event) {
		deptFormPane.setVisible(false);
		adminSideMenu.setDisable(false);
		clearAllInput(event);
		if (studentStatusPane.isVisible()) {
			studentStatusPane.setDisable(false);
			studentStatusPane.setOpacity(1);
			manageStudentPane.setVisible(false);
			manageDepartmentPane.setVisible(false);

		} else if (manageStudentPane.isVisible()) {
			manageStudentPane.setDisable(false);
			manageStudentPane.setOpacity(1);
			studentStatusPane.setVisible(false);
			manageDepartmentPane.setVisible(false);
		} else if (manageDepartmentPane.isVisible()) {
			manageDepartmentPane.setOpacity(1);
            manageDepartmentPane.setDisable(false);
            studentStatusPane.setVisible(false);
            manageStudentPane.setVisible(false);
		}
			

	}

	@FXML
	void showDeptPane(ActionEvent event) {
		deptFormPane.setVisible(true);
		addDeptBtn.setVisible(true);
		editDeptConfirmBtn.setVisible(false);
		deptFormLabel.setText("Add New Department Here");
		adminSideMenu.setDisable(true);

		if (studentStatusPane.isVisible()) {
			studentStatusPane.setDisable(true);
			studentStatusPane.setOpacity(0.1);
			manageStudentPane.setVisible(false);
			manageDepartmentPane.setVisible(false);

		} else if (manageStudentPane.isVisible()) {
			manageStudentPane.setDisable(true);
			manageStudentPane.setOpacity(0.1);
			studentStatusPane.setVisible(false);
			manageDepartmentPane.setVisible(false);
		} else if (manageDepartmentPane.isVisible()) {
			manageDepartmentPane.setDisable(true);
			manageDepartmentPane.setOpacity(0.2);
			studentStatusPane.setVisible(false);
			manageStudentPane.setVisible(false);

		}

	}

	@FXML
	public
	void editDeptConfirm(ActionEvent event) {
		String dName = deptNameField.getText();
	    int index = departmentListTable.getSelectionModel().getSelectedIndex();
		connect = Database.connectDB();
		addDeptBtn.setVisible(false);
		editDeptConfirmBtn.setVisible(true);
		deptFormLabel.setText("Edit Department Here");
		
		if (!dName.isEmpty()&& !deptCapacityField.getText().isEmpty()) {
			
			try {
				int dCap = Integer.parseInt(deptCapacityField.getText());
				ButtonType btnClicked = vl.getConfirmAlert(
						"Edit Student " + departmentListTable.getItems().get(index).getDepName(),
						"Are  you sure to Update ");
				if (btnClicked == ButtonType.OK) {
					int dId = departmentListTable.getItems().get(index).getDepId();
					connect = Database.connectDB();
					try {
						prepare = connect.prepareStatement(
								"UPDATE  department SET dName=?,dCapacity=? WHERE dId  ='"+ dId + "';");

						prepare.setString(1, dName);
						prepare.setInt(2, dCap);
						

						if (prepare.executeUpdate() == 1) {

							vl.getSuccessAlert("Depatrment Updated Successfuly");
							clearAllInput(event);
							deptList.clear();
							getAllDept();
							getAllDeptName();
							hideDeptPane(event);
						}

					} catch (Exception e) {
						vl.getErrorAlert(e.getMessage()+"\n Number is Expected");
					}
				}

				connect.close();

			} catch (Exception e) {
				vl.getErrorAlert(e.getMessage());
			}
		} else {
			addStudentErrorLabel.setText("All Fields Are required");
		}

	}

	@FXML
	public
	void addNewDept(ActionEvent event) {
		connect = Database.connectDB();

		Date date = new Date();

		java.sql.Date sqlDate = new java.sql.Date(date.getTime());

		if (!deptNameField.getText().equals("") && !deptCapacityField.getText().equals("")) {
			
			try {
				int cap = Integer.parseInt(deptCapacityField.getText());
				String insert = "insert into department  values(?,?,?,?);";
				prepare = connect.prepareStatement(insert);

				prepare.setInt(1, 0);
				prepare.setString(2, deptNameField.getText());
				prepare.setInt(3, cap);
				prepare.setDate(4, sqlDate);

				if (prepare.executeUpdate() == 1) {

					vl.getSuccessAlert("Department Added Successfuly");
					clearAllInput(event);
					deptList.clear();
					getAllDept();
					getAllDeptName();
					hideDeptPane(event);

				}
				connect.close();

			} catch (Exception e) {
				vl.getErrorAlert(e.getMessage());
			}
		} else {

			deptErrorLabel.setText("All Fields Are required");

		}

	}
}
