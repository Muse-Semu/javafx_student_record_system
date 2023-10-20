module What {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires java.sql;
	requires javafx.base;
	requires java.rmi;
	opens dataModels to javafx.fxml;
    exports dataModels;
	opens application to javafx.graphics, javafx.fxml;
   

}
