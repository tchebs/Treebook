package ca.mcgill.ecse321.TreeBook.model;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.26.1-f40f105-3613 modeling language!*/


import java.sql.Date;

// line 27 "TreeBook.ump"
// line 98 "TreeBook.ump"
public class Tree
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum KindOfLandUse { Residential, Institutional, Park, Municipal }
  public enum Status { Planted, Diseased, ToBeCutDown, CutDown }

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static int nextId = 1;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Tree Attributes
  private String species;
  private boolean toBeChopped;
  private Status status;
  private float height;
  private float diameterOfCanopy;
  private String municipality;
  private float diameterOfTrunk;
  private KindOfLandUse landUse;
  private Date whenPlanted;
  private int reports;
  private Date whenCutDown;
  private Date lastSurvey;

  //Autounique Attributes
  private int id;

  //Tree Associations
  private Location coordinates;
  private User lastReportingPerson;
  private SustainabilityAttributes treeSustainability;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Tree(String aSpecies, boolean aToBeChopped, Status aStatus, float aHeight, float aDiameterOfCanopy, String aMunicipality, float aDiameterOfTrunk, KindOfLandUse aLandUse, Date aWhenPlanted, int aReports, Date aWhenCutDown, Date aLastSurvey, Location aCoordinates, User aLastReportingPerson, SustainabilityAttributes aTreeSustainability)
  {
    species = aSpecies;
    toBeChopped = aToBeChopped;
    status = aStatus;
    height = aHeight;
    diameterOfCanopy = aDiameterOfCanopy;
    municipality = aMunicipality;
    diameterOfTrunk = aDiameterOfTrunk;
    landUse = aLandUse;
    whenPlanted = aWhenPlanted;
    reports = aReports;
    whenCutDown = aWhenCutDown;
    lastSurvey = aLastSurvey;
    id = nextId++;
    if (!setCoordinates(aCoordinates))
    {
      throw new RuntimeException("Unable to create Tree due to aCoordinates");
    }
    if (!setLastReportingPerson(aLastReportingPerson))
    {
      throw new RuntimeException("Unable to create Tree due to aLastReportingPerson");
    }
    if (!setTreeSustainability(aTreeSustainability))
    {
      throw new RuntimeException("Unable to create Tree due to aTreeSustainability");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setSpecies(String aSpecies)
  {
    boolean wasSet = false;
    species = aSpecies;
    wasSet = true;
    return wasSet;
  }

  public boolean setToBeChopped(boolean aToBeChopped)
  {
    boolean wasSet = false;
    toBeChopped = aToBeChopped;
    wasSet = true;
    return wasSet;
  }

  public boolean setStatus(Status aStatus)
  {
    boolean wasSet = false;
    status = aStatus;
    wasSet = true;
    return wasSet;
  }

  public boolean setHeight(float aHeight)
  {
    boolean wasSet = false;
    height = aHeight;
    wasSet = true;
    return wasSet;
  }

  public boolean setDiameterOfCanopy(float aDiameterOfCanopy)
  {
    boolean wasSet = false;
    diameterOfCanopy = aDiameterOfCanopy;
    wasSet = true;
    return wasSet;
  }

  public boolean setMunicipality(String aMunicipality)
  {
    boolean wasSet = false;
    municipality = aMunicipality;
    wasSet = true;
    return wasSet;
  }

  public boolean setDiameterOfTrunk(float aDiameterOfTrunk)
  {
    boolean wasSet = false;
    diameterOfTrunk = aDiameterOfTrunk;
    wasSet = true;
    return wasSet;
  }

  public boolean setLandUse(KindOfLandUse aLandUse)
  {
    boolean wasSet = false;
    landUse = aLandUse;
    wasSet = true;
    return wasSet;
  }

  public boolean setWhenPlanted(Date aWhenPlanted)
  {
    boolean wasSet = false;
    whenPlanted = aWhenPlanted;
    wasSet = true;
    return wasSet;
  }

  public boolean setReports(int aReports)
  {
    boolean wasSet = false;
    reports = aReports;
    wasSet = true;
    return wasSet;
  }

  public boolean setWhenCutDown(Date aWhenCutDown)
  {
    boolean wasSet = false;
    whenCutDown = aWhenCutDown;
    wasSet = true;
    return wasSet;
  }

  public boolean setLastSurvey(Date aLastSurvey)
  {
    boolean wasSet = false;
    lastSurvey = aLastSurvey;
    wasSet = true;
    return wasSet;
  }

  public String getSpecies()
  {
    return species;
  }

  public boolean getToBeChopped()
  {
    return toBeChopped;
  }

  public Status getStatus()
  {
    return status;
  }

  public float getHeight()
  {
    return height;
  }

  public float getDiameterOfCanopy()
  {
    return diameterOfCanopy;
  }

  public String getMunicipality()
  {
    return municipality;
  }

  public float getDiameterOfTrunk()
  {
    return diameterOfTrunk;
  }

  public KindOfLandUse getLandUse()
  {
    return landUse;
  }

  public Date getWhenPlanted()
  {
    return whenPlanted;
  }

  public int getReports()
  {
    return reports;
  }

  public Date getWhenCutDown()
  {
    return whenCutDown;
  }

  public Date getLastSurvey()
  {
    return lastSurvey;
  }

  public int getId()
  {
    return id;
  }

  public boolean isToBeChopped()
  {
    return toBeChopped;
  }

  public Location getCoordinates()
  {
    return coordinates;
  }

  public User getLastReportingPerson()
  {
    return lastReportingPerson;
  }

  public SustainabilityAttributes getTreeSustainability()
  {
    return treeSustainability;
  }

  public boolean setCoordinates(Location aNewCoordinates)
  {
    boolean wasSet = false;
    if (aNewCoordinates != null)
    {
      coordinates = aNewCoordinates;
      wasSet = true;
    }
    return wasSet;
  }

  public boolean setLastReportingPerson(User aNewLastReportingPerson)
  {
    boolean wasSet = false;
    if (aNewLastReportingPerson != null)
    {
      lastReportingPerson = aNewLastReportingPerson;
      wasSet = true;
    }
    return wasSet;
  }

  public boolean setTreeSustainability(SustainabilityAttributes aNewTreeSustainability)
  {
    boolean wasSet = false;
    if (aNewTreeSustainability != null)
    {
      treeSustainability = aNewTreeSustainability;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    coordinates = null;
    lastReportingPerson = null;
    treeSustainability = null;
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "species" + ":" + getSpecies()+ "," +
            "toBeChopped" + ":" + getToBeChopped()+ "," +
            "height" + ":" + getHeight()+ "," +
            "diameterOfCanopy" + ":" + getDiameterOfCanopy()+ "," +
            "municipality" + ":" + getMunicipality()+ "," +
            "diameterOfTrunk" + ":" + getDiameterOfTrunk()+ "," +
            "reports" + ":" + getReports()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "status" + "=" + (getStatus() != null ? !getStatus().equals(this)  ? getStatus().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "landUse" + "=" + (getLandUse() != null ? !getLandUse().equals(this)  ? getLandUse().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "whenPlanted" + "=" + (getWhenPlanted() != null ? !getWhenPlanted().equals(this)  ? getWhenPlanted().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "whenCutDown" + "=" + (getWhenCutDown() != null ? !getWhenCutDown().equals(this)  ? getWhenCutDown().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "lastSurvey" + "=" + (getLastSurvey() != null ? !getLastSurvey().equals(this)  ? getLastSurvey().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "coordinates = "+(getCoordinates()!=null?Integer.toHexString(System.identityHashCode(getCoordinates())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "lastReportingPerson = "+(getLastReportingPerson()!=null?Integer.toHexString(System.identityHashCode(getLastReportingPerson())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "treeSustainability = "+(getTreeSustainability()!=null?Integer.toHexString(System.identityHashCode(getTreeSustainability())):"null");
  }
}