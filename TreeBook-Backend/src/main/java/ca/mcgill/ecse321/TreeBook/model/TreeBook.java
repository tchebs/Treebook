package ca.mcgill.ecse321.TreeBook.model;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.26.1-f40f105-3613 modeling language!*/


import java.util.*;
import java.sql.Date;

// line 4 "TreeBook.ump"
// line 83 "TreeBook.ump"
public class TreeBook
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TreeBook Associations
  private List<User> users;
  private List<Tree> trees;
  private List<Location> requestedTrees;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TreeBook()
  {
    users = new ArrayList<User>();
    trees = new ArrayList<Tree>();
    requestedTrees = new ArrayList<Location>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public User getUser(int index)
  {
    User aUser = users.get(index);
    return aUser;
  }

  public List<User> getUsers()
  {
    List<User> newUsers = Collections.unmodifiableList(users);
    return newUsers;
  }

  public int numberOfUsers()
  {
    int number = users.size();
    return number;
  }

  public boolean hasUsers()
  {
    boolean has = users.size() > 0;
    return has;
  }

  public int indexOfUser(User aUser)
  {
    int index = users.indexOf(aUser);
    return index;
  }

  public Tree getTree(int index)
  {
    Tree aTree = trees.get(index);
    return aTree;
  }

  public List<Tree> getTrees()
  {
    List<Tree> newTrees = Collections.unmodifiableList(trees);
    return newTrees;
  }

  public int numberOfTrees()
  {
    int number = trees.size();
    return number;
  }

  public boolean hasTrees()
  {
    boolean has = trees.size() > 0;
    return has;
  }

  public int indexOfTree(Tree aTree)
  {
    int index = trees.indexOf(aTree);
    return index;
  }

  public Location getRequestedTree(int index)
  {
    Location aRequestedTree = requestedTrees.get(index);
    return aRequestedTree;
  }

  public List<Location> getRequestedTrees()
  {
    List<Location> newRequestedTrees = Collections.unmodifiableList(requestedTrees);
    return newRequestedTrees;
  }

  public int numberOfRequestedTrees()
  {
    int number = requestedTrees.size();
    return number;
  }

  public boolean hasRequestedTrees()
  {
    boolean has = requestedTrees.size() > 0;
    return has;
  }

  public int indexOfRequestedTree(Location aRequestedTree)
  {
    int index = requestedTrees.indexOf(aRequestedTree);
    return index;
  }

  public static int minimumNumberOfUsers()
  {
    return 0;
  }

  public boolean addUser(User aUser)
  {
    boolean wasAdded = false;
    if (users.contains(aUser)) { return false; }
    users.add(aUser);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeUser(User aUser)
  {
    boolean wasRemoved = false;
    if (users.contains(aUser))
    {
      users.remove(aUser);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addUserAt(User aUser, int index)
  {  
    boolean wasAdded = false;
    if(addUser(aUser))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfUsers()) { index = numberOfUsers() - 1; }
      users.remove(aUser);
      users.add(index, aUser);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveUserAt(User aUser, int index)
  {
    boolean wasAdded = false;
    if(users.contains(aUser))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfUsers()) { index = numberOfUsers() - 1; }
      users.remove(aUser);
      users.add(index, aUser);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addUserAt(aUser, index);
    }
    return wasAdded;
  }

  public static int minimumNumberOfTrees()
  {
    return 0;
  }

  public boolean addTree(Tree aTree)
  {
    boolean wasAdded = false;
    if (trees.contains(aTree)) { return false; }
    trees.add(aTree);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeTree(Tree aTree)
  {
    boolean wasRemoved = false;
    if (trees.contains(aTree))
    {
      trees.remove(aTree);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addTreeAt(Tree aTree, int index)
  {  
    boolean wasAdded = false;
    if(addTree(aTree))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTrees()) { index = numberOfTrees() - 1; }
      trees.remove(aTree);
      trees.add(index, aTree);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveTreeAt(Tree aTree, int index)
  {
    boolean wasAdded = false;
    if(trees.contains(aTree))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTrees()) { index = numberOfTrees() - 1; }
      trees.remove(aTree);
      trees.add(index, aTree);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addTreeAt(aTree, index);
    }
    return wasAdded;
  }

  public static int minimumNumberOfRequestedTrees()
  {
    return 0;
  }

  public boolean addRequestedTree(Location aRequestedTree)
  {
    boolean wasAdded = false;
    if (requestedTrees.contains(aRequestedTree)) { return false; }
    requestedTrees.add(aRequestedTree);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeRequestedTree(Location aRequestedTree)
  {
    boolean wasRemoved = false;
    if (requestedTrees.contains(aRequestedTree))
    {
      requestedTrees.remove(aRequestedTree);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addRequestedTreeAt(Location aRequestedTree, int index)
  {  
    boolean wasAdded = false;
    if(addRequestedTree(aRequestedTree))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfRequestedTrees()) { index = numberOfRequestedTrees() - 1; }
      requestedTrees.remove(aRequestedTree);
      requestedTrees.add(index, aRequestedTree);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveRequestedTreeAt(Location aRequestedTree, int index)
  {
    boolean wasAdded = false;
    if(requestedTrees.contains(aRequestedTree))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfRequestedTrees()) { index = numberOfRequestedTrees() - 1; }
      requestedTrees.remove(aRequestedTree);
      requestedTrees.add(index, aRequestedTree);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addRequestedTreeAt(aRequestedTree, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    users.clear();
    trees.clear();
    requestedTrees.clear();
  }

}