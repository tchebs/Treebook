package ca.mcgill.ecse321.TreeBook.model;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.26.1-f40f105-3613 modeling language!*/



// line 23 "TreeBook.ump"
// line 93 "TreeBook.ump"
public class Trophy
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Trophy Attributes
  private String name;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Trophy(String aName)
  {
    name = aName;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "]";
  }
}