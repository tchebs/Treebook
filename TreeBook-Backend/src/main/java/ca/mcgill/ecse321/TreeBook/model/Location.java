package ca.mcgill.ecse321.TreeBook.model;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.26.1-f40f105-3613 modeling language!*/



// line 68 "TreeBook.ump"
// line 108 "TreeBook.ump"
public class Location
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Location Attributes
  private float latitude;
  private float longitude;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Location(float aLatitude, float aLongitude)
  {
    latitude = aLatitude;
    longitude = aLongitude;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setLatitude(float aLatitude)
  {
    boolean wasSet = false;
    latitude = aLatitude;
    wasSet = true;
    return wasSet;
  }

  public boolean setLongitude(float aLongitude)
  {
    boolean wasSet = false;
    longitude = aLongitude;
    wasSet = true;
    return wasSet;
  }

  public float getLatitude()
  {
    return latitude;
  }

  public float getLongitude()
  {
    return longitude;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "latitude" + ":" + getLatitude()+ "," +
            "longitude" + ":" + getLongitude()+ "]";
  }
}