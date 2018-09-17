package ca.mcgill.ecse321.TreeBook.dto;


public class BooleanDto {

	private String loginSuccess;

	 public BooleanDto() {
	 }
	 
	 public BooleanDto (String loginSuccess) {
		 
		this.loginSuccess = loginSuccess;
	 }
	 
	 public String getLoginSuccess() {
		 return loginSuccess;
	 }

}