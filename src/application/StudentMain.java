package application;

import java.rmi.Naming;
import java.sql.*;

import DBase.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;

public class StudentMain extends Application {
	Parent root;
	Connection connect;
	ResultSet result;
	PreparedStatement prepare;

	@Override
	public void start(Stage primaryStage) {

		try {
			
			connect = Database.connectDB();
			String sql = "Select is_loggedin from user where is_loggedin = 1";
			prepare = connect.prepareStatement(sql);
			result = prepare.executeQuery();
			if (result.next()) {
				root = FXMLLoader.load(getClass().getResource("/fxml/dashboard.fxml"));

			} else {
				root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));

			}
//			root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));

			Scene scene = new Scene(root);
//			scene.getStylesheets().add(getClass().getResource("/fxml/main.css").toExternalForm());
			primaryStage.setScene(scene);
//			primaryStage.setResizable(false);
			primaryStage.show();

		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText(e.getMessage());
			alert.showAndWait();

		}
	}

	public static void main(String[] args) {
		launch(args);

	}

}
