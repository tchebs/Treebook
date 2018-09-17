package ca.mcgill.ecse321.TreeBook.model;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.26.1-f40f105-3613 modeling language!*/


import java.util.*;
import java.sql.Date;

// line 10 "TreeBook.ump"
// line 88 "TreeBook.ump"
public class User
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //User Attributes
  private String phoneNumber;
  private String name;
  private String password;
  private boolean isSuper;
  private int treesPlanted;
  private int treesCutDown;
  private int treesRequested;

  //User Associations
  private List<Trophy> trophies;
  private List<ForecastTree> forecastTrees;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public User(String aPhoneNumber, String aName, String aPassword, boolean aIsSuper)
  {
    phoneNumber = aPhoneNumber;
    name = aName;
    password = aPassword;
    isSuper = aIsSuper;
    treesPlanted = 0;
    treesCutDown = 0;
    treesRequested = 0;
    trophies = new ArrayList<Trophy>();
    forecastTrees = new ArrayList<ForecastTree>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setPhoneNumber(String aPhoneNumber)
  {
    boolean wasSet = false;
    phoneNumber = aPhoneNumber;
    wasSet = true;
    return wasSet;
  }

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setPassword(String aPassword)
  {
    boolean wasSet = false;
    password = aPassword;
    wasSet = true;
    return wasSet;
  }

  public boolean setIsSuper(boolean aIsSuper)
  {
    boolean wasSet = false;
    isSuper = aIsSuper;
    wasSet = true;
    return wasSet;
  }

  public boolean setTreesPlanted(int aTreesPlanted)
  {
    boolean wasSet = false;
    treesPlanted = aTreesPlanted;
    wasSet = true;
    return wasSet;
  }

  public boolean setTreesCutDown(int aTreesCutDown)
  {
    boolean wasSet = false;
    treesCutDown = aTreesCutDown;
    wasSet = true;
    return wasSet;
  }

  public boolean setTreesRequested(int aTreesRequested)
  {
    boolean wasSet = false;
    treesRequested = aTreesRequested;
    wasSet = true;
    return wasSet;
  }

  public String getPhoneNumber()
  {
    return phoneNumber;
  }

  public String getName()
  {
    return name;
  }

  public String getPassword()
  {
    return password;
  }

  public boolean getIsSuper()
  {
    return isSuper;
  }

  public int getTreesPlanted()
  {
    return treesPlanted;
  }

  public int getTreesCutDown()
  {
    return treesCutDown;
  }

  public int getTreesRequested()
  {
    return treesRequested;
  }

  public boolean isIsSuper()
  {
    return isSuper;
  }

  public Trophy getTrophy(int index)
  {
    Trophy aTrophy = trophies.get(index);
    return aTrophy;
  }

  public List<Trophy> getTrophies()
  {
    List<Trophy> newTrophies = Collections.unmodifiableList(trophies);
    return newTrophies;
  }

  public int numberOfTrophies()
  {
    int number = trophies.size();
    return number;
  }

  public boolean hasTrophies()
  {
    boolean has = trophies.size() > 0;
    return has;
  }

  public int indexOfTrophy(Trophy aTrophy)
  {
    int index = trophies.indexOf(aTrophy);
    return index;
  }

  public ForecastTree getForecastTree(int index)
  {
    ForecastTree aForecastTree = forecastTrees.get(index);
    return aForecastTree;
  }

  public List<ForecastTree> getForecastTrees()
  {
    List<ForecastTree> newForecastTrees = Collections.unmodifiableList(forecastTrees);
    return newForecastTrees;
  }

  public int numberOfForecastTrees()
  {
    int number = forecastTrees.size();
    return number;
  }

  public boolean hasForecastTrees()
  {
    boolean has = forecastTrees.size() > 0;
    return has;
  }

  public int indexOfForecastTree(ForecastTree aForecastTree)
  {
    int index = forecastTrees.indexOf(aForecastTree);
    return index;
  }

  public static int minimumNumberOfTrophies()
  {
    return 0;
  }

  public boolean addTrophy(Trophy aTrophy)
  {
    boolean wasAdded = false;
    if (trophies.contains(aTrophy)) { return false; }
    trophies.add(aTrophy);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeTrophy(Trophy aTrophy)
  {
    boolean wasRemoved = false;
    if (trophies.contains(aTrophy))
    {
      trophies.remove(aTrophy);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addTrophyAt(Trophy aTrophy, int index)
  {  
    boolean wasAdded = false;
    if(addTrophy(aTrophy))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTrophies()) { index = numberOfTrophies() - 1; }
      trophies.remove(aTrophy);
      trophies.add(index, aTrophy);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveTrophyAt(Trophy aTrophy, int index)
  {
    boolean wasAdded = false;
    if(trophies.contains(aTrophy))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTrophies()) { index = numberOfTrophies() - 1; }
      trophies.remove(aTrophy);
      trophies.add(index, aTrophy);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addTrophyAt(aTrophy, index);
    }
    return wasAdded;
  }

  public static int minimumNumberOfForecastTrees()
  {
    return 0;
  }

  public boolean addForecastTree(ForecastTree aForecastTree)
  {
    boolean wasAdded = false;
    if (forecastTrees.contains(aForecastTree)) { return false; }
    forecastTrees.add(aForecastTree);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeForecastTree(ForecastTree aForecastTree)
  {
    boolean wasRemoved = false;
    if (forecastTrees.contains(aForecastTree))
    {
      forecastTrees.remove(aForecastTree);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addForecastTreeAt(ForecastTree aForecastTree, int index)
  {  
    boolean wasAdded = false;
    if(addForecastTree(aForecastTree))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfForecastTrees()) { index = numberOfForecastTrees() - 1; }
      forecastTrees.remove(aForecastTree);
      forecastTrees.add(index, aForecastTree);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveForecastTreeAt(ForecastTree aForecastTree, int index)
  {
    boolean wasAdded = false;
    if(forecastTrees.contains(aForecastTree))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfForecastTrees()) { index = numberOfForecastTrees() - 1; }
      forecastTrees.remove(aForecastTree);
      forecastTrees.add(index, aForecastTree);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addForecastTreeAt(aForecastTree, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    trophies.clear();
    forecastTrees.clear();
  }


  public String toString()
  {
    return super.toString() + "["+
            "phoneNumber" + ":" + getPhoneNumber()+ "," +
            "name" + ":" + getName()+ "," +
            "password" + ":" + getPassword()+ "," +
            "isSuper" + ":" + getIsSuper()+ "," +
            "treesPlanted" + ":" + getTreesPlanted()+ "," +
            "treesCutDown" + ":" + getTreesCutDown()+ "," +
            "treesRequested" + ":" + getTreesRequested()+ "]";
  }
}