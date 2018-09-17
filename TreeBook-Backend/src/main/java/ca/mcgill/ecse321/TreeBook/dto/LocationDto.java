package ca.mcgill.ecse321.TreeBook.dto;

public class LocationDto {

	private float longitude;
	private float latitude;

	 public LocationDto() {
	 }
	 
	 public LocationDto(float latitude, float longitude) {
		 
		this.longitude=longitude;
		this.latitude=latitude;
	 }
	 
	 public float getLongitude() {
		 return longitude;
	 }
	 
	 public float getLatitude() {
		 return latitude;
	 }	 

}
