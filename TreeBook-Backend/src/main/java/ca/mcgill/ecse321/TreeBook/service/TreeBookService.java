package ca.mcgill.ecse321.TreeBook.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.*;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.TreeBook.model.ForecastSustainabilityAttributes;
import ca.mcgill.ecse321.TreeBook.model.ForecastTree;
import ca.mcgill.ecse321.TreeBook.model.Location;
import ca.mcgill.ecse321.TreeBook.model.SustainabilityAttributes;
import ca.mcgill.ecse321.TreeBook.model.Tree;
import ca.mcgill.ecse321.TreeBook.model.Tree.KindOfLandUse;
import ca.mcgill.ecse321.TreeBook.model.Tree.Status;
import ca.mcgill.ecse321.TreeBook.model.TreeBook;
import ca.mcgill.ecse321.TreeBook.model.Trophy;
import ca.mcgill.ecse321.TreeBook.model.User;
import ca.mcgill.ecse321.TreeBook.persistence.PersistenceXStream;

@Service
public class TreeBookService 
{
	private TreeBook tb;

	public TreeBookService(TreeBook tb)
	{
		this.tb = tb;
	}


	public User createUser(String phoneNumber, String username, String password) throws InvalidInputException
	{
		String error = "";

		// Ensure the username isn't empty.
		if (username == null || username.trim().length() == 0 || username.equals("undefined")) 
			error+="Username cannot be empty! ";

		// Ensure username isn't already in use.
		for(int i = 0;i<tb.getUsers().size();i++) 
		{
			if(tb.getUser(i).getName().equals(username)) 
			{
				error+="Username already in use! ";
				break;
			}
		}

		//Ensure the Phone Number isn't empty.
		if(phoneNumber == null || phoneNumber.trim().length() == 0)
			error+="Phone Number cannot be empty! ";

		// Ensure the Phone number contains 10 digits.
		if (phoneNumber.length() != 10 && phoneNumber.length()!=0 && phoneNumber!=null) 
			error+="Phone Number must be ten digits! ";

		//Ensure the Phone number contains digits and nothing else.
		for(int i = 0;i<phoneNumber.length();i++)
		{
			if(!Character.isDigit(phoneNumber.charAt(i))) 
			{
				error+="Phone Number must only contain digits! ";
				break;
			}
		}

		//Ensure phone number isn't already in use.
		for(int i = 0;i<tb.getUsers().size();i++)
		{
			if(tb.getUser(i).getPhoneNumber().equals(phoneNumber)) 
			{
				error+="Phone Number already in use! ";
				break;
			}
		}

		//Ensure the password is greater than or equal to 4 characters.
		if(password.length()<4)
			error+="Password must be at least four characters! ";

		//Ensure the password contains a number.
		boolean check = false;
		for(int i = 0;i<password.length();i++)
		{
			if(Character.isDigit(password.charAt(i)))
			{
				check = true;
				break;
			}
		}

		if (!check)
			error+="Password must contain at least one digit! ";

		//Ensure the password contains at least one uppercase letter.
		check = false;
		for(int i=0;i<password.length();i++)
		{
			if(Character.isUpperCase(password.charAt(i)))
			{
				check=true;
				break;
			}
		}
		if (!check)
			error+="Password must contain at least one uppercase letter! ";

		//Ensure the password contains at least one lowercase letter.
		check = false;
		for(int i=0;i<password.length();i++)
		{
			if(Character.isLowerCase(password.charAt(i)))
			{
				check=true;
				break;
			}
		}
		if (!check)
			error+="Password must contain at least one lowercase letter! ";

		//Ensure the password does not contain a symbol.
		check = false;
		for(int i=0;i<password.length();i++)
		{
			if(!Character.isLetterOrDigit(password.charAt(i)))
			{
				check=true;
				break;
			}
		}
		if (check)
			error+="Password cannot contain a symbol! ";

		error = error.trim();

		if(error.length() !=0) throw new InvalidInputException(error);

		//Apparently no errors are present. Register the user:

		User u1 = new User (phoneNumber, username, password,false);
		for(int i=0;i<9;i++)
			u1.addTrophy(new Trophy(""));
		tb.addUser(u1);
		PersistenceXStream.saveToXMLwithXStream(tb);
		return u1;

	}

	public User createUser(String phoneNumber, String username, String password,boolean isSuper) throws InvalidInputException
	{
		String error = "";

		// Ensure the username isn't empty.
		if (username == null || username.trim().length() == 0 || username.equals("undefined")) 
			error+="Username cannot be empty! ";

		// Ensure username isn't already in use.
		for(int i = 0;i<tb.getUsers().size();i++) 
		{
			if(tb.getUser(i).getName().equals(username)) 
			{
				error+="Username already in use! ";
				break;
			}
		}

		//Ensure the Phone Number isn't empty.
		if(phoneNumber == null || phoneNumber.trim().length() == 0)
			error+="Phone Number cannot be empty! ";

		// Ensure the Phone number contains 10 digits.
		if (phoneNumber.length() != 10 && phoneNumber.length()!=0 && phoneNumber!=null) 
			error+="Phone Number must be ten digits! ";

		//Ensure the Phone number contains digits and nothing else.
		for(int i = 0;i<phoneNumber.length();i++)
		{
			if(!Character.isDigit(phoneNumber.charAt(i))) 
			{
				error+="Phone Number must only contain digits! ";
				break;
			}
		}

		//Ensure phone number isn't already in use.
		for(int i = 0;i<tb.getUsers().size();i++)
		{
			if(tb.getUser(i).getPhoneNumber().equals(phoneNumber)) 
			{
				error+="Phone Number already in use! ";
				break;
			}
		}

		//Ensure the password is greater than or equal to 4 characters.
		if(password.length()<4)
			error+="Password must be at least four characters! ";

		//Ensure the password contains a number.
		boolean check = false;
		for(int i = 0;i<password.length();i++)
		{
			if(Character.isDigit(password.charAt(i)))
			{
				check = true;
				break;
			}
		}

		if (!check)
			error+="Password must contain at least one digit! ";

		//Ensure the password contains at least one uppercase letter.
		check = false;
		for(int i=0;i<password.length();i++)
		{
			if(Character.isUpperCase(password.charAt(i)))
			{
				check=true;
				break;
			}
		}
		if (!check)
			error+="Password must contain at least one uppercase letter! ";

		//Ensure the password contains at least one lowercase letter.
		check = false;
		for(int i=0;i<password.length();i++)
		{
			if(Character.isLowerCase(password.charAt(i)))
			{
				check=true;
				break;
			}
		}
		if (!check)
			error+="Password must contain at least one lowercase letter! ";

		//Ensure the password does not contain a symbol.
		check = false;
		for(int i=0;i<password.length();i++)
		{
			if(!Character.isLetterOrDigit(password.charAt(i)))
			{
				check=true;
				break;
			}
		}
		if (check)
			error+="Password cannot contain a symbol! ";

		error = error.trim();

		if(error.length() !=0) throw new InvalidInputException(error);

		//Apparently no errors are present. Register the user:

		User u1 = new User (phoneNumber, username, password,isSuper);

		for(int i=0;i<9;i++)
			u1.addTrophy(new Trophy(""));
		tb.addUser(u1);
		PersistenceXStream.saveToXMLwithXStream(tb);
		return u1;

	}


	public Location requestTree (float latitude,float longitude,User U) throws InvalidInputException 
	{
		//Apparently no errors are present. Request the tree:
		for(int i = 0;i<tb.getRequestedTrees().size();i++)
			if(tb.getRequestedTree(i).getLatitude() == latitude && tb.getRequestedTree(i).getLongitude() == longitude)
			{
				throw new InvalidInputException("Tree already exists within requested trees! ");
			}
		for(int i = 0;i<tb.getTrees().size();i++)
			if(tb.getTree(i).getCoordinates().getLatitude() == latitude && tb.getTree(i).getCoordinates().getLongitude() == longitude)
			{
				throw new InvalidInputException("Tree already exists in planted trees");
			}

		Location L = new Location(latitude,longitude);
		tb.addRequestedTree(L);
		PersistenceXStream.saveToXMLwithXStream(tb);
		U.setTreesRequested(U.getTreesRequested()+1);

		if(U.getTreesRequested() == 10)
			U.getTrophy(3).setName("TreesRequestedLv1");
		else if(U.getTreesRequested() == 25)
			U.getTrophy(4).setName("TreesRequestedLv2");
		else if(U.getTreesRequested() == 50)
			U.getTrophy(5).setName("TreesRequestedLv3");
		PersistenceXStream.saveToXMLwithXStream(tb);

		return L;
	}

	public Tree confirmTree (String aSpecies, boolean aToBeChopped, Status aStatus, float aHeight, 
			float aDiameterOfCanopy, String aMunicipality, float aDiameterOfTrunk,
			KindOfLandUse aLandUse, Date aWhenPlanted, int aReports,
			Date aWhenCutDown, Date aLastSurvey, Location aCoordinates,
			User aLastReportingPerson, SustainabilityAttributes aTreeSustainability) throws InvalidInputException
	{
		String error = "";

		//Check if Tree species is defined. 
		if (aSpecies == null || aSpecies.trim().length() == 0 || aSpecies.equals("undefined"))
			error+= "The tree species must be defined.";

		//Check if the municipality is defined.
		if (aMunicipality == null || aMunicipality.trim().length() == 0 || aMunicipality.equals("undefined"))
			error+= "The municipality must be defined.";

		//Check if status is defined.
		if (aStatus == null || !(aStatus.toString().equals("Planted") || aStatus.toString().equals("CutDown")||
				aStatus.toString().equals("Diseased")||aStatus.toString().equals("ToBeCutDown")))
			error+="Please select the tree status.";
		error = error.trim();

		//Check if land use is defined

		if (aLandUse==null || (!(aLandUse.equals(KindOfLandUse.Residential) || aLandUse.equals(KindOfLandUse.Institutional) ||
				aLandUse.equals(KindOfLandUse.Park) || aLandUse.equals(KindOfLandUse.Municipal))))
			error+="Please select the land being used.";
		boolean check = false;
		for(int i = 0;i<tb.getRequestedTrees().size();i++)
			if(tb.getRequestedTree(i).getLatitude() == aCoordinates.getLatitude() && tb.getRequestedTree(i).getLongitude() == aCoordinates.getLongitude())
			{
				check = true;
			}
		if(!check)
			error+="Tree must exist in requested trees! ";
		if(!(aLastReportingPerson.getIsSuper()))
			error+="User must be expert to confirm a tree!";

		if(error.length()!=0)
			throw new InvalidInputException(error);

		//Apparently no errors are present. Plant the tree:

		Tree T = new Tree(aSpecies, aToBeChopped, aStatus,aHeight, aDiameterOfCanopy,aMunicipality,aDiameterOfTrunk, aLandUse, aWhenPlanted, aReports, aWhenCutDown, aLastSurvey,aCoordinates,aLastReportingPerson, aTreeSustainability);
		tb.addTree(T);
		updateBiodiversityOfMunicipality(T.getMunicipality());
		T.getTreeSustainability().setCo2Absorption(calculateCO2AbsorptionPerYear(T.getDiameterOfCanopy(),T.getHeight(),T.getWhenPlanted()));
		for(int i = 0;i<tb.getRequestedTrees().size();i++)
			if(tb.getRequestedTree(i).getLatitude() == aCoordinates.getLatitude() && tb.getRequestedTree(i).getLongitude() == aCoordinates.getLongitude())
			{
				tb.removeRequestedTree(tb.getRequestedTree(i));
				break;
			}

		aLastReportingPerson.setTreesPlanted(aLastReportingPerson.getTreesPlanted()+1);

		if(aLastReportingPerson.getTreesPlanted() == 10)
			aLastReportingPerson.getTrophy(0).setName("PlantedLv1");
		else if(aLastReportingPerson.getTreesPlanted() == 25)
			aLastReportingPerson.getTrophy(1).setName("PlantedLv2");
		else if(aLastReportingPerson.getTreesPlanted() == 50)
			aLastReportingPerson.getTrophy(2).setName("PlantedLv3");
		PersistenceXStream.saveToXMLwithXStream(tb);

		return T;

	}	

	public Tree plantTree (String aSpecies, boolean aToBeChopped, Status aStatus, float aHeight, 
			float aDiameterOfCanopy, String aMunicipality, float aDiameterOfTrunk,
			KindOfLandUse aLandUse, Date aWhenPlanted, int aReports,
			Date aWhenCutDown, Date aLastSurvey, Location aCoordinates,
			User aLastReportingPerson, SustainabilityAttributes aTreeSustainability) throws InvalidInputException
	{
		String error = "";

		//Check if Tree species is defined. 
		if (aSpecies == null || aSpecies.trim().length() == 0 || aSpecies.equals("undefined"))
			error+= "The tree species must be defined.";

		//Check if the municipality is defined.
		if (aMunicipality == null || aMunicipality.trim().length() == 0 || aMunicipality.equals("undefined"))
			error+= "The municipality must be defined.";

		//Check if status is defined.
		if (aStatus==null || !(aStatus.toString().equals("Planted") || aStatus.toString().equals("CutDown")||
				aStatus.toString().equals("Diseased")||aStatus.toString().equals("ToBeCutDown")))
			error+="Please select the tree status.";
		
		for(int i = 0;i<tb.getTrees().size();i++)
			if(tb.getTree(i).getCoordinates().getLatitude() == aCoordinates.getLatitude() && tb.getTree(i).getCoordinates().getLongitude() == aCoordinates.getLongitude())
			{
				error+= "Tree already exists in planted trees";
			}

		error = error.trim();

		//Check if land use is defined

		if (aLandUse == null || (!(aLandUse.equals(KindOfLandUse.Residential) || aLandUse.equals(KindOfLandUse.Institutional) ||
				aLandUse.equals(KindOfLandUse.Park) || aLandUse.equals(KindOfLandUse.Municipal))))
			error+="Please select the land being used.";
		if(error.length()!=0)
			throw new InvalidInputException(error);


		//Apparently no errors are present. Plant the tree:

		Tree T = new Tree(aSpecies, aToBeChopped, aStatus,aHeight, aDiameterOfCanopy,aMunicipality,aDiameterOfTrunk, aLandUse, aWhenPlanted, aReports, aWhenCutDown, aLastSurvey,aCoordinates,aLastReportingPerson, aTreeSustainability);
		tb.addTree(T);
		updateBiodiversityOfMunicipality(T.getMunicipality());
		T.getTreeSustainability().setCo2Absorption(calculateCO2AbsorptionPerYear(T.getDiameterOfCanopy(),T.getHeight(),T.getWhenPlanted()));
		aLastReportingPerson.setTreesPlanted(aLastReportingPerson.getTreesPlanted()+1);

		if(aLastReportingPerson.getTreesPlanted() == 10)
			aLastReportingPerson.getTrophy(0).setName("PlantedLv1");
		else if(aLastReportingPerson.getTreesPlanted() == 25)
			aLastReportingPerson.getTrophy(1).setName("PlantedLv2");
		else if(aLastReportingPerson.getTreesPlanted() == 50)
			aLastReportingPerson.getTrophy(2).setName("PlantedLv3");
		PersistenceXStream.saveToXMLwithXStream(tb);

		return T;
	}

	//Reject a requested tree!
	public void rejectTree(Location L,User U) throws InvalidInputException 
	{
		boolean check = false;
		for(int i = 0;i<tb.getRequestedTrees().size();i++)
			if(tb.getRequestedTree(i).getLatitude() == L.getLatitude() && tb.getRequestedTree(i).getLongitude() == L.getLongitude())
			{
				check = true;
			}
		if(!check)
			throw new InvalidInputException("Tree must exist in requested trees! ");
		if(!(U.getIsSuper()))
			throw new InvalidInputException("User must be expert to reject a tree!");
		for(int i = 0;i<tb.getRequestedTrees().size();i++)
		{
			if(tb.getRequestedTree(i).getLatitude() == L.getLatitude() && tb.getRequestedTree(i).getLongitude() == L.getLongitude())

			{
				tb.removeRequestedTree(tb.getRequestedTree(i));
				break;
			}
		}
		PersistenceXStream.saveToXMLwithXStream(tb);
	}

	//Get all the trees in the Requested Trees list:
	public List<Location> getAllRequestedTrees()
	{
		return tb.getRequestedTrees();
	}

	//Login to your account
	public boolean login (String username, String password ) throws InvalidInputException 
	{
		String error= "";
		// Ensure the username isn't empty.
		if (username == null || username.trim().length() == 0 || username.equals("undefined")) 
			error+="Username cannot be empty! ";
		//Ensure the Password isn't empty.
		if (password == null || password.trim().length() == 0 || password.equals("undefined")) 
			error+="Password cannot be empty! ";
		error = error.trim();

		if(error.length()!=0) throw new InvalidInputException(error);

		//Apparently no errors are present. Attempt to login the user.

		if (findUserByUsername(username)!=null && password.equals(findUserByUsername(username).getPassword()))
			return true;
		else
			return false;
	}

	//Get all the users
	public List<User> getAllUsers()
	{
		return tb.getUsers();	
	}

	//Get all the trees
	public List<Tree> getAllTrees()
	{
		return tb.getTrees();
	}

	//Find a tree by its ID number
	public Tree findTreeByID(int ID) 
	{

		for(int i = 0;i<tb.getTrees().size();i++) 
			if(tb.getTrees().get(i).getId() == ID) return tb.getTree(i);

		return null;
	}

	//Find a forecast tree by its ID number
	public ForecastTree findForecastTreeByID(int ID, User U) 
	{

		for(int i = 0;i<U.getForecastTrees().size();i++) 
			if(U.getForecastTrees().get(i).getId() == ID) return U.getForecastTree(i);

		return null;
	}

	//Find a user by his username.
	public User findUserByUsername(String username) 
	{
		for(int i = 0;i<tb.getUsers().size();i++)
		{
			if(tb.getUser(i).getName().equals(username)) return tb.getUser(i);
		}	
		return null;
	}

	//Cut down a tree
	public void cutDownTree(Tree T,User U) throws InvalidInputException
	{
		String error = "";

		//Check if Tree exists. 
		if(T == null) 
			error+="Tree has to exist! ";

		//Check is User exists.
		if(U == null) 
			error+="User has to exist! ";

		//Check if the Tree isn't already cut down.
		if(T!=null && T.getStatus().equals(Status.CutDown))
			error+="Tree is already cut down! ";

		if(U!=null && !(U.getIsSuper()))
			error+="User must be expert to cut down a tree!";

		error = error.trim();

		if(error.length()!=0)
			throw new InvalidInputException(error);

		//Apparently no errors are present. Cut Down the tree:

		T.setStatus(Status.CutDown);
		T.setLastReportingPerson(U);
		T.getTreeSustainability().setCo2Absorption(0);

		U.setTreesCutDown(U.getTreesCutDown()+1);

		if(U.getTreesCutDown() == 10)
			U.getTrophy(6).setName("CutDownLv1");

		else if(U.getTreesCutDown() == 25)
			U.getTrophy(7).setName("CutDownLv2");

		else if(U.getTreesCutDown() == 50)
			U.getTrophy(8).setName("CutDownLv3");

		T.setWhenCutDown(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
		updateBiodiversityOfMunicipality(T.getMunicipality());
		PersistenceXStream.saveToXMLwithXStream(tb);
	}


	//Remove a tree from the XML
	public void removeTree(Tree T,User U) throws InvalidInputException 
	{
		if(!tb.getTrees().contains(T))
			throw new InvalidInputException("Tree has to exist to be removed!");
		if(!(U.getIsSuper()))
			throw new InvalidInputException("User must be expert to remove a tree!");
		tb.removeTree(T);
		PersistenceXStream.saveToXMLwithXStream(tb);
	}

	//Remove ALL the trees from the XML
	public void removeAllTrees() 
	{
		int nTrees = tb.numberOfTrees();
		while(!tb.getTrees().isEmpty())
			tb.removeTree(tb.getTree(0));

		PersistenceXStream.saveToXMLwithXStream(tb);
	}

	//Get the number of unique trees from a list of trees.
	private ArrayList<String> getUniqueSpecies(ArrayList<Tree> listOfTrees)
	{
		ArrayList<String> listOfSpecies = new ArrayList<String>();
		for(int i=0;i<listOfTrees.size();i++) 
		{
			if(!listOfSpecies.contains(listOfTrees.get(i).getSpecies())) //if list doesn't have this species
			{
				listOfSpecies.add(listOfTrees.get(i).getSpecies());//add species to list
			}
		}
		return listOfSpecies;
	}

	//Get all the trees within a specific municipality.
	private List<Tree> getTreesByMunicipality(String municipality)
	{
		List<Tree> filteredTrees = new ArrayList<Tree>();
		List<Tree> allTrees =  tb.getTrees();
		for(int i=0;i<allTrees.size();i++) 
		{
			if(allTrees.get(i).getMunicipality().contentEquals(municipality)
					&& !(allTrees.get(i).getStatus().toString().contentEquals("CutDown"))) 
			{
				filteredTrees.add(allTrees.get(i));
			}
		}
		return filteredTrees;
	}

	//Calculate the biodiversity within a municipality.
	private float calculateBiodiversityByMunicipality(String municipality) 
	{
		List<Tree> localTrees = getTreesByMunicipality(municipality);
		if(localTrees.size()==0) 
		{
			return 0;
		}
		List<String> uniqueSpecies = getUniqueSpecies((ArrayList<Tree>) localTrees);
		float numSpecies = (float) uniqueSpecies.size();
		float numTrees = (float) localTrees.size();
		float biodiversityIndex= numSpecies/numTrees;

		return biodiversityIndex;
	}

	//Update the biodiversity of a muncipiality every time a tree is cut down or planted.
	public boolean updateBiodiversityOfMunicipality(String municipality) 

	{
		boolean updated = false;
		List<Tree> Trees = getTreesByMunicipality(municipality);
		float bioIndex = calculateBiodiversityByMunicipality(municipality);
		int index=0;
		for(int i=0;i<Trees.size();i++) 
		{
			index = tb.indexOfTree(Trees.get(i));
			//get the tree from TB list and update biodiversity index
			tb.getTree(index).getTreeSustainability().setBiodiversityIndex(bioIndex);
		}
		PersistenceXStream.saveToXMLwithXStream(tb);
		//if the last tree was updated properly, flip the boolean to true
		if(tb.getTree(index).getTreeSustainability().getBiodiversityIndex()==bioIndex) 
		{
			updated = true;
		}

		return updated;
	}


	//Filter all the trees by an identifier.
	public List<Tree> filterTrees(String species, boolean searchingForChopped,boolean toBeChopped, Tree.Status status,String municipality, float minHeight, float maxHeight, float minDiameterOfCanopy, float maxDiameterOfCanopy, Tree.KindOfLandUse KoLU)
	{ 

		List<Tree> filtered = new ArrayList();

		int size = tb.getTrees().size();
		for(int i = 0;i<size;i++) 
			//All of the parameters being used in this tree are defaulted to null
			//The one parameter that you're FILTERING for is NOT null!
			//Therefore, if the parameter ISN't null, AND the parameter equals the specific trait of a tree
			//ADD it to the filtered list!
		{
			if((species == null || tb.getTree(i).getSpecies().toLowerCase().contains(species.toLowerCase())) &&
					(searchingForChopped == false || tb.getTree(i).getToBeChopped() == toBeChopped) &&
					(status == null || tb.getTree(i).getStatus().equals(status)) &&
					(minHeight == -1 || tb.getTree(i).getHeight()>=minHeight) &&
					(maxHeight == -1 || tb.getTree(i).getHeight()<=maxHeight) &&
					(municipality == null || tb.getTree(i).getMunicipality().toLowerCase().contains(municipality.toLowerCase())) &&
					(minDiameterOfCanopy == -1 || tb.getTree(i).getDiameterOfCanopy()>=minDiameterOfCanopy) &&
					(maxDiameterOfCanopy == -1 || tb.getTree(i).getDiameterOfCanopy()<=maxDiameterOfCanopy) &&
					(KoLU == null || tb.getTree(i).getLandUse().equals(KoLU))
					)
				filtered.add(tb.getTree(i));
		}

		return filtered;
	}

	//Sort the trees by species
	public List<Tree> sortBySpecies(boolean reverse)
	{
		//Create a new list identical to the original tree list
		//Sort it using Quicksort.
		List<Tree> toBeSorted = new ArrayList();
		int size = tb.getTrees().size();
		for(int i = 0;i<size;i++)
		{
			toBeSorted.add(tb.getTree(i));
		}
		toBeSorted = quickSortBySpecies(0,size-1,toBeSorted);
		//If this function is called twice, it will reverse the order.
		if(reverse) 
		{
			Collections.reverse(toBeSorted);
		}
		return toBeSorted;
	}

	//This method is used to quicksort the trees by species
	private List<Tree> quickSortBySpecies(int low, int high, List<Tree> toBeSorted) 
	{
		int i = low, j = high;
		String pivot = toBeSorted.get(low + (high-low)/2).getSpecies().toLowerCase();

		while (i <= j) 
		{
			while (toBeSorted.get(i).getSpecies().compareToIgnoreCase(pivot)<0) 
			{
				i++;
			}

			while (toBeSorted.get(j).getSpecies().compareToIgnoreCase(pivot)>0) 
			{
				j--;
			}

			if (i <= j) 
			{
				Collections.swap(toBeSorted,i, j);
				i++;
				j--;
			}
		}

		if (low < j)
		{
			quickSortBySpecies(low, j,toBeSorted);
		}

		if (i < high)
		{
			quickSortBySpecies(i, high,toBeSorted);
		}

		return toBeSorted;
	}

	//Sort the trees by their "ToBeChopped" attribute
	public List<Tree> sortByToBeChopped(boolean reverse)
	{
		//Create a new list of trees identical to the original
		//Then SORT it using QUICKSORT
		List<Tree> toBeSorted = new ArrayList();
		int size = tb.getTrees().size();
		for(int i = 0;i<size;i++)
		{
			toBeSorted.add(tb.getTree(i));
		}
		toBeSorted = quickSortByToBeChopped(0,size-1,toBeSorted);
		if(reverse)
		{
			Collections.reverse(toBeSorted);
		}
		return toBeSorted;
	}

	//Quicksort the list of trees by its "ToBeChopped" attribute
	private List<Tree> quickSortByToBeChopped(int low, int high, List<Tree> toBeSorted)
	{
		int i = low, j = high;
		String pivot = String.valueOf(toBeSorted.get(low + (high-low)/2).getToBeChopped());
		while (i <= j) 
		{
			while (String.valueOf(toBeSorted.get(i).getToBeChopped()).compareTo(pivot)<0) 
			{
				i++;
			}

			while (String.valueOf(toBeSorted.get(j).getToBeChopped()).compareTo(pivot)>0) 
			{
				j--;
			}

			if (i <= j) 
			{
				Collections.swap(toBeSorted,i, j);
				i++;
				j--;
			}
		}

		if (low < j)
		{
			quickSortByToBeChopped(low, j,toBeSorted);
		}

		if (i < high)
		{
			quickSortByToBeChopped(i, high,toBeSorted);
		}

		return toBeSorted;
	}

	//Sort the trees by their "status" attribute
	public List<Tree> sortByStatus(boolean reverse)
	{
		//Create a new list of trees identical to the original
		//Then SORT it using QUICKSORT
		List<Tree> toBeSorted = new ArrayList();
		int size = tb.getTrees().size();
		for(int i = 0;i<size;i++)
		{
			toBeSorted.add(tb.getTree(i));
		}
		toBeSorted = quickSortByStatus(0,size-1,toBeSorted);
		if(reverse)
		{
			Collections.reverse(toBeSorted);
		}
		return toBeSorted;
	}

	//Sort the listof trees by their "status" attribute
	private List<Tree> quickSortByStatus(int low, int high, List<Tree> toBeSorted) 
	{
		int i = low, j = high;
		String pivot = String.valueOf(toBeSorted.get(low + (high-low)/2).getStatus()).toLowerCase();
		while (i <= j) {
			while (String.valueOf(toBeSorted.get(i).getStatus()).compareToIgnoreCase(pivot)<0) 
			{
				i++;
			}

			while (String.valueOf(toBeSorted.get(j).getStatus()).compareToIgnoreCase(pivot)>0) 
			{
				j--;
			}

			if (i <= j) 
			{
				Collections.swap(toBeSorted,i, j);
				i++;
				j--;
			}
		}

		if (low < j)
		{
			quickSortByStatus(low, j,toBeSorted);
		}

		if (i < high)
		{
			quickSortByStatus(i, high,toBeSorted);
		}

		return toBeSorted;
	}

	//Sort the list of trees by its Height

	public List<Tree> sortByHeight(boolean reverse)
	{
		//Create a list of trees identical to the original
		//Then SORT it using QUICKSORT

		List<Tree> toBeSorted = new ArrayList();
		int size = tb.getTrees().size();

		for(int i = 0;i<size;i++)
		{
			toBeSorted.add(tb.getTree(i));
		}
		toBeSorted = quickSortByHeight(0,size-1,toBeSorted);

		if(reverse)
		{
			Collections.reverse(toBeSorted);
		}

		return toBeSorted;


	}

	//Quicksort the trees by their height
	private List<Tree> quickSortByHeight(int low, int high, List<Tree> toBeSorted) 
	{
		int i = low, j = high;
		float pivot = toBeSorted.get(low + (high-low)/2).getHeight();
		while (i <= j) 
		{
			while (toBeSorted.get(i).getHeight()<pivot) 
			{
				i++;
			}
			while (toBeSorted.get(j).getHeight()>pivot) 
			{
				j--;
			}

			if (i <= j) 
			{
				Collections.swap(toBeSorted,i, j);
				i++;
				j--;
			}
		}

		if (low < j)
		{
			quickSortByHeight(low, j,toBeSorted);
		}
		if (i < high)
		{
			quickSortByHeight(i, high,toBeSorted);
		}
		return toBeSorted;
	}

	//Sort the list of tries by its diameter
	public List<Tree> sortByDiameterOfCanopy(boolean reverse)
	{
		//Create a new list of trees IDENTICAL to the original
		//And then QUICKSORT it
		List<Tree> toBeSorted = new ArrayList();
		int size = tb.getTrees().size();

		for(int i = 0;i<size;i++)
		{
			toBeSorted.add(tb.getTree(i));
		}

		toBeSorted = quickSortByDiameterOfCanopy(0,size-1,toBeSorted);

		if(reverse)
		{
			Collections.reverse(toBeSorted);
		}
		return toBeSorted;
	}

	//Quicksort the tree list by its diameter of canopy
	private List<Tree> quickSortByDiameterOfCanopy(int low, int high, List<Tree> toBeSorted) 
	{
		int i = low, j = high;
		float pivot = toBeSorted.get(low + (high-low)/2).getDiameterOfCanopy();

		while (i <= j) 
		{
			while (toBeSorted.get(i).getDiameterOfCanopy()<pivot) 
			{
				i++;
			}
			while (toBeSorted.get(j).getDiameterOfCanopy()>pivot) 
			{
				j--;
			}
			if (i <= j) 
			{
				Collections.swap(toBeSorted,i, j);
				i++;
				j--;
			}
		}
		if (low < j)
		{
			quickSortByDiameterOfCanopy(low, j,toBeSorted);
		}
		if (i < high)
		{
			quickSortByDiameterOfCanopy(i, high,toBeSorted);
		}
		return toBeSorted;
	}

	//Sort the list of trees by its municipality
	public List<Tree> sortByMunicipality(boolean reverse)
	{

		//Create a list of trees identical to the original
		//Then SORT it by its municipality!
		List<Tree> toBeSorted = new ArrayList();
		int size = tb.getTrees().size();
		for(int i = 0;i<size;i++)
			toBeSorted.add(tb.getTree(i));
		toBeSorted = quickSortByMunicipality(0,size-1,toBeSorted);
		if(reverse)
			Collections.reverse(toBeSorted);
		return toBeSorted;
	}

	//Quicksort the list of trees by its municipality
	private List<Tree> quickSortByMunicipality(int low, int high, List<Tree> toBeSorted) 
	{
		int i = low, j = high;
		String pivot = toBeSorted.get(low + (high-low)/2).getMunicipality();
		while (i <= j) 
		{
			while (toBeSorted.get(i).getMunicipality().compareToIgnoreCase(pivot)<0) 
			{
				i++;
			}

			while (toBeSorted.get(j).getMunicipality().compareToIgnoreCase(pivot)>0) 
			{
				j--;
			}

			if (i <= j) 
			{
				Collections.swap(toBeSorted,i, j);
				i++;
				j--;
			}
		}

		if (low < j)
		{
			quickSortByMunicipality(low, j,toBeSorted);
		}

		if (i < high)
		{
			quickSortByMunicipality(i, high,toBeSorted);
		}

		return toBeSorted;
	}

	//Sort the trees by land use
	public List<Tree> sortByLandUse(boolean reverse)
	{
		List<Tree> toBeSorted = new ArrayList();
		int size = tb.getTrees().size();

		//Create a list of trees identical to the original
		//And then SORT it by its landuse

		for(int i = 0;i<size;i++)
		{
			toBeSorted.add(tb.getTree(i));
		}

		toBeSorted = quickSortByLandUse(0,size-1,toBeSorted);

		if(reverse)
		{
			Collections.reverse(toBeSorted);
		}

		return toBeSorted;
	}

	//Quicksort the list by its land use
	private List<Tree> quickSortByLandUse(int low, int high, List<Tree> toBeSorted) 
	{
		int i = low, j = high;
		String pivot = String.valueOf(toBeSorted.get(low + (high-low)/2).getLandUse()).toLowerCase();
		while (i <= j) 
		{
			while (String.valueOf(toBeSorted.get(i).getLandUse()).toLowerCase().compareTo(pivot)<0) 
			{
				i++;
			}

			while (String.valueOf(toBeSorted.get(j).getLandUse()).toLowerCase().compareTo(pivot)>0) 
			{
				j--;
			}

			if (i <= j) 
			{
				Collections.swap(toBeSorted,i, j);
				i++;
				j--;
			}
		}

		if (low < j)
		{
			quickSortByLandUse(low, j,toBeSorted);
		}

		if (i < high)
		{
			quickSortByLandUse(i, high,toBeSorted);
		}

		return toBeSorted;
	}

	//CO2 absorption code

	//Convert from meters to inches
	private float convertToInches(float meters)
	{
		float inches = meters*(((float)100)/((float)2.54));
		return inches;
	}

	//Convert from inches to feet
	private float convertToFeet(float inches) 
	{
		return inches/12;
	}

	//Convert from pounds to kilograms
	private float poundsToKg(float pounds)
	{
		return (float) (pounds/2.20462);
	}

	//Calculate the carbon dioxide the weight of a tree.
	private float carbonDioxideWeightOfTree(float diameter, float height) 
	{
		float diameterInches=convertToInches(diameter);
		float heightFeet=convertToFeet(convertToInches(height));
		float weight;

		if(diameterInches>=11) 
		{
			weight=(float) (0.15*((float) Math.pow(diameterInches,2))*heightFeet);
		}
		else 
		{
			weight=(float) (0.25*((float) Math.pow(diameterInches,2))*heightFeet);			
		}

		float carbonDioxideWeight = weight*((float)0.725)*((float)0.5)*((float)3.6663);
		return carbonDioxideWeight;
		//returns CO2 weight of tree in POUNDS
	}

	//Calculate the CO2 Absorption of a tree per year.
	public float calculateCO2AbsorptionPerYear(float diameter, float height, Date planted)
	{
		//calculate CO2 weight of tree
		float CO2WeightInKg = poundsToKg(carbonDioxideWeightOfTree(diameter, height));
		//retrieve the year the tree was planted and store as int
		String plantedYearString = planted.toString().substring(0,4);
		int yearPlanted = Integer.parseInt(plantedYearString);
		//retrieve current year
		LocalDateTime now = LocalDateTime.now();
		int presentYear = now.getYear();
		int difference;
		if(presentYear-yearPlanted==0) 
		{
			difference = 1;
		}
		else 
		{
			difference = presentYear-yearPlanted;
		}
		float CO2PerYear = CO2WeightInKg/difference;
		return CO2PerYear;
	}

	//Initialize the forecast list!
	//Essentially, we will duplicate the original list of trees into a new type of tree called "forecastTree"
	public List<ForecastTree> initializeForecastTrees(User U) throws InvalidInputException
	{
		if(!(U.getIsSuper()))
		{
			throw new InvalidInputException("Error: User must be expert for forecasts");
		}

		while(!U.getForecastTrees().isEmpty())
		{
			U.removeForecastTree(U.getForecastTree(0));
		}

		ForecastTree t = null;
		ForecastSustainabilityAttributes S = null;

		//Take all the trees
		//Take their attributes one by one
		//Plant a forecast tree with identical attributes.
		for(int i = 0;i<tb.getTrees().size();i++) 
		{
			float co2 = tb.getTree(i).getTreeSustainability().getCo2Absorption();
			float bio = tb.getTree(i).getTreeSustainability().getBiodiversityIndex();
			S = new ForecastSustainabilityAttributes(co2,bio);
			ForecastTree.Status status = ForecastTree.Status.valueOf(tb.getTree(i).getStatus().toString());
			ForecastTree.KindOfLandUse kind = ForecastTree.KindOfLandUse.valueOf(tb.getTree(i).getLandUse().toString());
			t = new ForecastTree(tb.getTree(i).getSpecies(),tb.getTree(i).getToBeChopped(),status,
					tb.getTree(i).getHeight(),tb.getTree(i).getDiameterOfCanopy(),tb.getTree(i).getMunicipality(),
					tb.getTree(i).getDiameterOfTrunk(),kind,tb.getTree(i).getWhenPlanted(),
					tb.getTree(i).getReports(),tb.getTree(i).getWhenCutDown(),tb.getTree(i).getLastSurvey(),
					tb.getTree(i).getCoordinates(),S);
			plantForecastTree(t, U);
		}
		PersistenceXStream.saveToXMLwithXStream(tb);
		return U.getForecastTrees();
	}

	//To get the list of forecast trees of a specific user:
	public List<ForecastTree> getForecastTreesForUser(User U)
	{
		return U.getForecastTrees();
	}

	//To plant a forecast trees:
	private ForecastTree plantForecastTree(ForecastTree T, User aLastReportingPerson) 
	{
		aLastReportingPerson.addForecastTree(T);
		T.getTreeSustainability().setCo2Absorption(calculateCO2AbsorptionPerYear(T.getDiameterOfCanopy(),T.getHeight(),T.getWhenPlanted()));
		updateForecastBiodiversityOfMunicipality(T.getMunicipality(),aLastReportingPerson);
		return T;
	}

	//Plant a forecast tree given all its traits.
	public ForecastTree plantForecastTree (String aSpecies, boolean aToBeChopped, Status aStatus, float aHeight, 
			float aDiameterOfCanopy, String aMunicipality, float aDiameterOfTrunk,
			KindOfLandUse aLandUse, Date aWhenPlanted, int aReports,
			Date aWhenCutDown, Date aLastSurvey, Location aCoordinates,
			User aLastReportingPerson, SustainabilityAttributes aTreeSustainability) throws InvalidInputException
	{
		String error = "";

		//Check if Tree species is defined. 
		if (aSpecies == null || aSpecies.trim().length() == 0 || aSpecies.equals("undefined"))
			error+= "The tree species must be defined.";

		//Check if the municipality is defined.
		if (aMunicipality == null || aMunicipality.trim().length() == 0 || aMunicipality.equals("undefined"))
			error+= "The municipality must be defined.";

		//Check if status is defined.
		if (!(aStatus.toString().equals("Planted") || aStatus.toString().equals("CutDown")||
				aStatus.toString().equals("Diseased")||aStatus.toString().equals("ToBeCutDown")))
			error+="Please select the tree status.";
		error = error.trim();

		//Check if land use is defined

		if ((!(aLandUse.equals(KindOfLandUse.Residential) || aLandUse.equals(KindOfLandUse.Institutional) ||
				aLandUse.equals(KindOfLandUse.Park) || aLandUse.equals(KindOfLandUse.Municipal))))
			error+="Please select the land being used.";
		if(error.length()!=0)
			throw new InvalidInputException(error);


		//Apparently no errors are present. Plant the tree:
		ForecastTree.Status status = ForecastTree.Status.valueOf(aStatus.toString());
		ForecastTree.KindOfLandUse kind = ForecastTree.KindOfLandUse.valueOf(aLandUse.toString());

		float co2 = aTreeSustainability.getCo2Absorption();
		float bio = aTreeSustainability.getBiodiversityIndex();
		ForecastSustainabilityAttributes S = new ForecastSustainabilityAttributes(co2,bio);
		ForecastTree T = new ForecastTree(aSpecies, aToBeChopped, status, aHeight, aDiameterOfCanopy, aMunicipality, aDiameterOfTrunk, kind, aWhenPlanted, aReports, aWhenCutDown, aLastSurvey, aCoordinates, S);
		aLastReportingPerson.addForecastTree(T);
		T.getTreeSustainability().setCo2Absorption(calculateCO2AbsorptionPerYear(T.getDiameterOfTrunk(),T.getHeight(),T.getWhenPlanted()));
		updateForecastBiodiversityOfMunicipality(T.getMunicipality(),aLastReportingPerson);

		PersistenceXStream.saveToXMLwithXStream(tb);

		return T;
	}

	//Update the biodiversity of the forecasted municipality upon planting or cutting down a forecast tree.
	public boolean updateForecastBiodiversityOfMunicipality(String municipality,User U) 
	{
		boolean updated = false;

		float bioIndex = calculateForecastBiodiversityByMunicipality(municipality,U);

		for(int i=0;i<U.getForecastTrees().size();i++)
		{	
			if(U.getForecastTree(i).getMunicipality().contentEquals(municipality)) 
			{
				U.getForecastTree(i).getTreeSustainability().setBiodiversityIndex(bioIndex);
			}
		}

		PersistenceXStream.saveToXMLwithXStream(tb);		
		if(U.getForecastTree(0).getTreeSustainability().getBiodiversityIndex()==bioIndex)
		{
			updated = true;
		}

		return updated;
	}

	//Calculate the forecast biodiversity of each municipality
	private float calculateForecastBiodiversityByMunicipality(String municipality,User U) 
	{
		List<ForecastTree> localTrees = new ArrayList<ForecastTree>();
		for(int i = 0;i<U.getForecastTrees().size();i++)
			if(U.getForecastTree(i).getMunicipality().contentEquals(municipality) 
					&& !U.getForecastTree(i).getStatus().toString().contentEquals("CutDown"))
			{
				localTrees.add(U.getForecastTree(i));
			}

		List<String> uniqueSpecies = getUniqueForecastSpecies((ArrayList<ForecastTree>) localTrees);
		float numSpecies = (float) uniqueSpecies.size();
		float numTrees = (float) localTrees.size();
		float biodiversityIndex= numSpecies/numTrees;
		return biodiversityIndex;
	}

	//Get the unique species within a forecasted municipality.
	private ArrayList<String> getUniqueForecastSpecies(ArrayList<ForecastTree> listOfTrees)
	{
		ArrayList<String> listOfSpecies = new ArrayList<String>();
		for(int i=0;i<listOfTrees.size();i++) 
		{
			//if list doesn't have this species
			if(!listOfSpecies.contains(listOfTrees.get(i).getSpecies())) 
			{
				listOfSpecies.add(listOfTrees.get(i).getSpecies());//add species to list
			}
		}
		return listOfSpecies;
	}

	//Cut down a forecast tree!
	public void cutDownForecastTree(ForecastTree T,User U) throws InvalidInputException
	{
		String error = "";

		//Check if Tree exists. 
		if(T == null) 
			error+="Tree has to exist! ";

		//Check is User exists.
		if(U == null) 
			error+="User has to exist! ";

		//Check if the Tree isn't already cut down.
		if(T!=null && T.getStatus().equals(Status.CutDown))
			error+="Tree is already cut down! ";
		//		if(!(U instanceof SuperUser))
		//			error+="User must be expert to cut down a tree!";

		error = error.trim();

		if(error.length()!=0)
			throw new InvalidInputException(error);

		//Apparently no errors are present. Cut Down the tree:

		T.setStatus(ForecastTree.Status.CutDown);
		T.getTreeSustainability().setCo2Absorption(0);

		//When tree is cut down, update biodiversity
		updateForecastBiodiversityOfMunicipality(T.getMunicipality(),U);
		PersistenceXStream.saveToXMLwithXStream(tb);
	}

	public Tree editTree (Tree T,String aSpecies, boolean aToBeChopped, Status aStatus, float aHeight, 
			float aDiameterOfCanopy, String aMunicipality, float aDiameterOfTrunk,
			KindOfLandUse aLandUse, Date aWhenPlanted, int aReports,
			Date aWhenCutDown, Date aLastSurvey, Location aCoordinates,
			User aLastReportingPerson, SustainabilityAttributes aTreeSustainability) throws InvalidInputException
	{
		String error = "";

		//Check if Tree species is defined. 
		if (aSpecies == null || aSpecies.trim().length() == 0 || aSpecies.equals("undefined"))
			error+= "The tree species must be defined.";

		//Check if the municipality is defined.
		if (aMunicipality == null || aMunicipality.trim().length() == 0 || aMunicipality.equals("undefined"))
			error+= "The municipality must be defined.";

		//Check if status is defined.
		if (aStatus==null || !(aStatus.toString().equals("Planted") || aStatus.toString().equals("CutDown")||
				aStatus.toString().equals("Diseased")||aStatus.toString().equals("ToBeCutDown")))
			error+="Please select the tree status.";
		error = error.trim();

		//Check if land use is defined

		if (aLandUse == null || (!(aLandUse.equals(KindOfLandUse.Residential) || aLandUse.equals(KindOfLandUse.Institutional) ||
				aLandUse.equals(KindOfLandUse.Park) || aLandUse.equals(KindOfLandUse.Municipal))))
			error+="Please select the land being used.";
		if(error.length()!=0)
			throw new InvalidInputException(error);

		//Apparently no errors are present. Plant the tree:

		T.setSpecies(aSpecies);
		T.setCoordinates(aCoordinates);
		T.setDiameterOfCanopy(aDiameterOfCanopy);
		T.setLandUse(aLandUse);
		T.setHeight(aHeight);
		T.setDiameterOfTrunk(aDiameterOfTrunk);
		T.setMunicipality(aMunicipality);
		T.setStatus(aStatus);
		T.setLastReportingPerson(aLastReportingPerson);
		T.setLastSurvey(aLastSurvey);
		T.setReports(aReports);
		T.setToBeChopped(aToBeChopped);
		T.setTreeSustainability(aTreeSustainability);
		T.setWhenCutDown(aWhenCutDown);
		T.setWhenPlanted(aWhenPlanted);

		updateBiodiversityOfMunicipality(T.getMunicipality());
		T.getTreeSustainability().setCo2Absorption(calculateCO2AbsorptionPerYear(T.getDiameterOfCanopy(),T.getHeight(),T.getWhenPlanted()));

		PersistenceXStream.saveToXMLwithXStream(tb);

		return T;
	}


	//ADD FROM FILE METHODS BEGIN HERE:
	public void saveFromFile(File file, User reporter) throws InvalidInputException
	{
		//save the excel file we were given as a Text (tab delimited) file and write out the pathway here

		FileReader reader = null;
		try {
			reader = new FileReader(file);
		} catch (FileNotFoundException e) {
			throw new InvalidInputException("Problem reading file. Please try again.");
		}
		BufferedReader br = new BufferedReader(reader);
		ArrayList<String> stringTokens = new ArrayList<String>();
		ArrayList<ArrayList<String>> lines = new ArrayList<ArrayList<String>>();

		String st = "";
		try {
			st = br.readLine();
			while(br.ready()) {
				st = br.readLine();
				stringTokens = tokenizeLine(st);
				lines.add(stringTokens);
			}
		} catch (IOException e) {
			throw new InvalidInputException("Had trouble reading file. Please try again.");
		}

		//Do stuff with stringTokens, ie plant trees
		try {
			for(int i=0;i<lines.size()-1;i++) {
				String aSpecies; //0
				boolean aToBeChopped; //1
				Status aStatus; //2
				float aHeight; //3
				float aDiameterOfCanopy; //4
				float aDiameterOfTrunk; //5
				String aMunicipality; //6
				KindOfLandUse aLandUse; //7
				Date aWhenPlanted; //8
				Date aWhenCutDown; //9
				int aReports; //10
				Date aLastSurvey; //11
				float aLatitude; //12
				float aLongitude; //13
				//ABOVE IS THE REQUIRED FORMAT OF THE EXCEL FILE IN ORDER
				//Below is stuff to be calculated from above values
				float aCo2Absorption;
				SustainabilityAttributes aTreeSustainability;
				Location aCoordinates;


				aSpecies = lines.get(i).get(0);
				if(lines.get(i).get(1).contentEquals("TRUE") || lines.get(i).get(1).contentEquals("FALSE")) 
				{
					if(lines.get(i).get(1).contentEquals("TRUE")) aToBeChopped=true;
					else aToBeChopped=false;
				}

				else
				{
					aToBeChopped=false;
				}

				//Default all the values that are not filled within the file:

				if(lines.get(i).get(2).contentEquals("NULL")) aStatus = Status.valueOf("Planted");					
				else aStatus = Status.valueOf(lines.get(i).get(2));

				if(lines.get(i).get(3).contentEquals("NULL")) aHeight = 1;					
				else aHeight = Float.valueOf(lines.get(i).get(3));

				if(lines.get(i).get(4).contentEquals("NULL")) aDiameterOfCanopy = 1;
				else aDiameterOfCanopy = Float.valueOf(lines.get(i).get(4));					

				if(lines.get(i).get(5).contentEquals("NULL")) aDiameterOfTrunk = 1;
				else aDiameterOfTrunk = Float.valueOf(lines.get(i).get(5));

				aMunicipality = lines.get(i).get(6);

				if(lines.get(i).get(7).contentEquals("NULL")) aLandUse = KindOfLandUse.valueOf("Municipal");
				else aLandUse = KindOfLandUse.valueOf(lines.get(i).get(7));					


				if(lines.get(i).get(8).contentEquals("NULL")) aWhenPlanted = Date.valueOf(java.time.LocalDate.now());
				else aWhenPlanted = Date.valueOf(lines.get(i).get(8));					

				try {
					aWhenCutDown = Date.valueOf(lines.get(i).get(9));
				}catch (IllegalArgumentException e){
					aWhenCutDown = null;
				}

				if(lines.get(i).get(10).contentEquals("NULL")) aReports = 0;
				else aReports = Integer.valueOf(lines.get(i).get(10));					


				if(lines.get(i).get(11).contentEquals("NULL")) aLastSurvey = Date.valueOf(java.time.LocalDate.now());
				else aLastSurvey = Date.valueOf(lines.get(i).get(11));

				aLatitude = Float.valueOf(lines.get(i).get(12));
				aLongitude = Float.valueOf(lines.get(i).get(13));

				aCo2Absorption = calculateCO2AbsorptionPerYear(aDiameterOfTrunk, aHeight, aWhenPlanted);
				//Force biodiversity to 1 since it'll be properly calculated when tree is planted
				aTreeSustainability = new SustainabilityAttributes(aCo2Absorption, 1);
				aCoordinates = new Location(aLatitude, aLongitude);

				try {
					plantTree(aSpecies, aToBeChopped, aStatus, aHeight, 
							aDiameterOfCanopy, aMunicipality, aDiameterOfTrunk,
							aLandUse, aWhenPlanted, aReports,
							aWhenCutDown, aLastSurvey, aCoordinates,
							reporter, aTreeSustainability);
				}catch (InvalidInputException e) {
					//	System.out.println("Encountered input error.");
				}
			}
		}catch (IllegalArgumentException e){
			throw new InvalidInputException("Found invalid inputs. Please make sure your file conforms to the accepted format.");
		}
	}

	//Tokenize a line
	private static ArrayList<String> tokenizeLine(String string)
	{
		ArrayList<String> tokens = new ArrayList<String>();
		StringBuilder token = new StringBuilder();
		for(int i=0;i<string.length();i++) 
		{
			if(token.toString().contentEquals("       ")) 
			{
				break;
			}
			
			//The following are strictly for the end of the string
			if(i == string.length()-1 && string.charAt(i) == '\t')//if string ends with null entry 
			{
				if(token.length()>0) //the previous token under construction was not empty, and must be added
				{
					tokens.add(token.toString());
				}

				else ///the previous token was empty, so add it.
				{
					tokens.add("NULL");
				}

				tokens.add("NULL");
				break;
			}
			//If the string ends without null
			else if(i == string.length()-1 && string.charAt(i) != '\t') 
			{
				token.append(string.charAt(i));
				tokens.add(token.toString());
				break;
			}

			//The following are for the middle of the string
			//case for a null entry in middle of string
			if(string.charAt(i)=='\t' && token.length()==0) 
			{
				tokens.add("NULL");
			}
			//normal entry among normal entries
			else if(string.charAt(i)=='\t')
			{
				tokens.add(token.toString());
				token.delete(0,token.length());
			}

			else{//build token of non-null entry
				token.append(string.charAt(i));
			}
		}
		return tokens;
	}
}
