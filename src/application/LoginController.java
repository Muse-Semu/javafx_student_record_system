package application;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import DBase.Database;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.*;
import javafx.stage.Stage;


public class LoginController implements Initializable{
	
    @FXML
    private Button loginBtn;

    @FXML
    private Label loginErrorLabel;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button registerBtn;

    @FXML
    private TextField usernameField;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    
    Alert alert;
    private Stage stage;
    private Parent root;
    private Scene scene;
    
    
    
    @FXML
    void gotoRegister(ActionEvent event) {

    }

    @FXML
    void login(ActionEvent event) throws IOException {
       String username = usernameField.getText();
       String password = passwordField.getText();
      
       if (!username.equals("") && !password.equals("")) {
    	  loginErrorLabel.setText(username +" "+ password);
    	  connect = Database.connectDB();
    	  String sql = "Select * from user where username =  ? and password = ?";
    	  try {
			prepare = connect.prepareStatement(sql);
			prepare.setString(1, username);
			prepare.setString(2, password);
			result = prepare.executeQuery();
//			System.out.println(result.toString());
			if(result.next()) {
				loginBtn.getScene().getWindow().hide();
				String sql2 = "UPDATE user SET is_loggedin = ? WHERE username = "+"'" +username+ "'"  ;
				prepare = connect.prepareStatement(sql2);
				prepare.setInt(1, 1);
				prepare.executeUpdate();
				
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dashboard.fxml"));
			 
				root = loader.load();
				stage = (Stage)((Node )event.getSource()).getScene().getWindow();
				scene = new Scene(root);
				stage.setScene(scene);
				stage.show();
			}
			else {
				
				loginErrorLabel.setText("Invalid password/username");
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	   
	}else {
	       loginErrorLabel.setText("all fields are required");
	       if(username.equals("")) {
	    	   usernameField.setStyle("-fx-border-color:red");
	    	   usernameField.setPromptText("required");
	    	   
	       }
	       if(password.equals("")) {
	    	   passwordField.setStyle("-fx-border-color:red");
	    	   passwordField.setPromptText("required");
	    	   
	       }
	       
	       

	}
    }
    
   

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
	}

}
