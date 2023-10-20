package application;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class ValidationWithAlert {
	Alert alert;
public 	void getErrorAlert(String msg) {
		alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setContentText(msg);
		alert.showAndWait();
	}
	
public	void getSuccessAlert(String msg) {
		alert = new Alert(AlertType.INFORMATION) ;
//		alert.setTitle("Successfull");
		alert.setContentText("Operation Succsessfull ");
		alert.setContentText(msg);
		alert.showAndWait();
	}
	
	public ButtonType getConfirmAlert(String header, String msg) {
		 alert = new Alert(AlertType.CONFIRMATION);
	     alert.setTitle("Delete Student");
	     alert.setHeaderText(header);
	     alert.setContentText(msg);
	     return alert.showAndWait().get();
	}
	
 boolean checkEmail(String emailadd) {
		boolean checker =false;
		 if((emailadd.endsWith("@gmail.com")||(emailadd.endsWith("@yahoo.com")) )&& emailadd.charAt(0)!='@')
		 {
			 checker=true;
		 }
		 else
		 {
			 checker=false;
		 }
		 
		 return checker;
		
	}


	 boolean checkAge(String age) {
		boolean digit = false;
		for(int i=0;i<age.length();i++)
		{
			if(Character.isDigit(age.charAt(i)) )
			{
				digit=true;
				
			}
			else
				digit=false;
				
		}
		
		
		return age.length() <= 3 && Integer.parseInt(age) >= 18  && digit;
		
		
	}

    
  boolean checkPhone(String phoneNum) {
		boolean digit = false;
		for(int i=0;i<phoneNum.length();i++)
		{
			if(Character.isDigit(phoneNum.charAt(i)))
			{
				digit=true;
			}
			else
				digit=false;
				
		}
		
		return phoneNum.length() == 10 && phoneNum.charAt(0)=='0' && phoneNum.charAt(1)=='9' && phoneNum.contains(phoneNum) && digit;
		
	}


	
	
}
