package ca.mcgill.ecse321.TreeBook.dto;

public class TreeHistoryDto {
	private int monetaryValue;
	private int biodiversityIndex;
	
	 public TreeHistoryDto() {
	 }
	 
	 public TreeHistoryDto(int monetaryValue, int biodiversityIndex) {
		 
		this.monetaryValue=monetaryValue;
		this.biodiversityIndex=biodiversityIndex;
	 }
	 
	 public int getMonetaryValue() {
		 return monetaryValue;
	 }
	 
	 public int getBiodiversityIndex() {
		 return biodiversityIndex;
	 }	 

}
