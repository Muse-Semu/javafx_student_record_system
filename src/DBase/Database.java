package DBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import application.ValidationWithAlert;

public class Database {
	static ValidationWithAlert alt = new ValidationWithAlert();
	
public static Connection connectDB() {

	try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/Java","root", "");
//         System.out.print("Database is connected !");
//         conn.close();
		 
		return  conn;
	} catch (Exception e) {
		System.out.println(e.getMessage()+"\n\n Check your database Service is running");
	}
	
	return null;
}


}
