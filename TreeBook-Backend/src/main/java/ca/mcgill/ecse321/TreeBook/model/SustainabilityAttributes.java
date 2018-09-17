package ca.mcgill.ecse321.TreeBook.model;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.26.1-f40f105-3613 modeling language!*/



// line 78 "TreeBook.ump"
// line 118 "TreeBook.ump"
public class SustainabilityAttributes
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //SustainabilityAttributes Attributes
  private float co2Absorption;
  private float biodiversityIndex;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public SustainabilityAttributes(float aCo2Absorption, float aBiodiversityIndex)
  {
    co2Absorption = aCo2Absorption;
    biodiversityIndex = aBiodiversityIndex;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setCo2Absorption(float aCo2Absorption)
  {
    boolean wasSet = false;
    co2Absorption = aCo2Absorption;
    wasSet = true;
    return wasSet;
  }

  public boolean setBiodiversityIndex(float aBiodiversityIndex)
  {
    boolean wasSet = false;
    biodiversityIndex = aBiodiversityIndex;
    wasSet = true;
    return wasSet;
  }

  public float getCo2Absorption()
  {
    return co2Absorption;
  }

  public float getBiodiversityIndex()
  {
    return biodiversityIndex;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "co2Absorption" + ":" + getCo2Absorption()+ "," +
            "biodiversityIndex" + ":" + getBiodiversityIndex()+ "]";
  }
}