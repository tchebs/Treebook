package ca.mcgill.ecse321.TreeBook.dto;

public class SuperUserDto {
	private String phoneNumber;
	 private String name;
	 private String password;
	 private String occupation;
	 
	 public SuperUserDto() {
		 
	 }
	 public SuperUserDto(String phonenumber, String name, String password, String occupation) {
		 this.phoneNumber = phonenumber;
		 this.name = name;
		 this.password = password;
		 this.occupation = occupation;
	 }
	 public String getPhoneNumber() {
		 return phoneNumber;
	 }
	 
	 public String getName() {
		 return name;
	 }
	 
	 public String getPassword() {
		 return password;
	 }
	 public String getOccupation() {
		 return occupation;
	 }
}
