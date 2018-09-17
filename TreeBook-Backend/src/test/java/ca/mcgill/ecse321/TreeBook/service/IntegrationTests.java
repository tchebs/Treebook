package ca.mcgill.ecse321.TreeBook.service;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import ca.mcgill.ecse321.TreeBook.model.TreeBook;
import ca.mcgill.ecse321.TreeBook.model.Tree;
import ca.mcgill.ecse321.TreeBook.model.User;
import ca.mcgill.ecse321.TreeBook.model.Tree.KindOfLandUse;
import ca.mcgill.ecse321.TreeBook.model.Tree.Status;
import ca.mcgill.ecse321.TreeBook.model.Location;
import ca.mcgill.ecse321.TreeBook.model.SustainabilityAttributes;
import ca.mcgill.ecse321.TreeBook.persistence.PersistenceXStream;
import java.io.File;
import java.sql.Date;
import java.util.Calendar;
public class IntegrationTests {

	private TreeBook tb;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		PersistenceXStream.initializeModelManager("output"+File.separator+"data.xml");
	}

	@Before
	public void setUp() throws Exception {
		tb = new TreeBook();
	}

	@After
	public void tearDown() throws Exception {
		tb.delete();
	}
	//Test Case 28: Test Login after creating user
	@Test
	public void testRegisterThenLogin() throws InvalidInputException {

		assertEquals(0, tb.getUsers().size());
		String error = "";
		String name = "Aly";
		String phoneNumber = "1234567890";
		String password = "Abc123";
		boolean loginSuccess = false;
		TreeBookService tbs = new TreeBookService(tb);
		try {
			tbs.createUser(phoneNumber, name,password);
		} catch (InvalidInputException e) {
			fail();
		}
		assertEquals(1,tbs.getAllUsers().size());
		try {
			loginSuccess = tbs.login("Aly", "Abc123");
		} catch (InvalidInputException e) {
			fail();
		}

		assertEquals(loginSuccess,true);
		assertEquals(1,tbs.getAllUsers().size());
		checkResultUser("Aly","Abc123","1234567890",tb);
	}
	
	@Test //Test Case 31: Test Login Then Add Tree Successfully
	public void testLogInThenAddTree() {
		assertEquals(0, tb.getUsers().size());
		String error = "";
		String name = "Aly";
		String phoneNumber = "1234567890";
		String password = "Abc123";
		boolean loginSuccess = false;
		TreeBookService tbs = new TreeBookService(tb);
		try {
			tbs.createUser(phoneNumber, name,password);
		} catch (InvalidInputException e) {
			fail();
		}
		assertEquals(1,tbs.getAllUsers().size());
		checkResultUser("Aly","Abc123","1234567890",tb);
		try {
			loginSuccess = tbs.login("Aly", "Abc123");
		} catch (InvalidInputException e) {
			fail();
		}
		assertEquals(true,loginSuccess);
		
		Calendar setPlant = Calendar.getInstance();
		setPlant.set(2007,Calendar.JANUARY,12,1,27,0);
		Date treePlanted = new Date(setPlant.getTimeInMillis());

		Calendar setCutDown = Calendar.getInstance();
		setCutDown.set(2018,Calendar.DECEMBER,10,1,15,0);
		Date cutDown = new Date(setCutDown.getTimeInMillis());

		Calendar setLastSurvey = Calendar .getInstance();
		setLastSurvey.set(2018,Calendar.DECEMBER,10,10,15,0);
		Date lastSurvey = new Date(setLastSurvey.getTimeInMillis());
		Location l1 = new Location(12,27);
		SustainabilityAttributes th1 = new SustainabilityAttributes ( 12, 25);
		try {
			tbs.plantTree("Oak", false, Status.Planted, 72,12, "Ahunsic", 1, KindOfLandUse.Residential, treePlanted, 1, cutDown, lastSurvey, l1, tbs.findUserByUsername("Aly"), th1);
		} catch (InvalidInputException e) {
			fail();
		}
		assertEquals(1,tbs.getAllTrees().size());
		checkResultTree("Oak", false, Status.Planted, 72,12, "Ahunsic", 1, KindOfLandUse.Residential, treePlanted, 1, cutDown, lastSurvey, l1, tbs.findUserByUsername("Aly"), th1,tb);


	}
	//Test Case 32: Test Login then CutDown a tree
	@Test
	public void testLoginCreateThenCutDown() {
		assertEquals(0, tb.getUsers().size());
		String error = "";
		String name = "Aly";
		String phoneNumber = "1234567890";
		String password = "Abc123";
		TreeBookService tbs = new TreeBookService(tb);
		boolean loginSuccess = false;
		try {
			tbs.createUser(phoneNumber, name,password,true);
		} catch (InvalidInputException e) {
			fail();
		}
		assertEquals(1,tbs.getAllUsers().size());
		checkResultUser("Aly","Abc123","1234567890",tb);
		try {
			loginSuccess = tbs.login("Aly", "Abc123");
		} catch (InvalidInputException e) {
			fail();
		}

		assertEquals(loginSuccess,true);
		
		Calendar setPlant = Calendar.getInstance();
		setPlant.set(2007,Calendar.JANUARY,12,1,27,0);
		Date treePlanted = new Date(setPlant.getTimeInMillis());

		Calendar setCutDown = Calendar.getInstance();
		setCutDown.set(2018,Calendar.DECEMBER,10,1,15,0);
		Date cutDown = new Date(setCutDown.getTimeInMillis());

		Calendar setLastSurvey = Calendar .getInstance();
		setLastSurvey.set(2018,Calendar.DECEMBER,10,10,15,0);
		Date lastSurvey = new Date(setLastSurvey.getTimeInMillis());
		Location l1 = new Location(12,27);
		SustainabilityAttributes th1 = new SustainabilityAttributes ( 12, 25);
		Tree t = null;
		try {
			t = tbs.plantTree("Oak", false, Status.Planted, 72,12, "Ahunsic", 1, KindOfLandUse.Residential, treePlanted, 1, cutDown, lastSurvey, l1, tbs.findUserByUsername("Aly"), th1);
		} catch (InvalidInputException e) {
			fail();
		}
		assertEquals(1,tbs.getAllTrees().size());
		checkResultTree("Oak", false, Status.Planted, 72,12, "Ahunsic", 1, KindOfLandUse.Residential, treePlanted, 1, cutDown, lastSurvey, l1, tbs.findUserByUsername("Aly"), th1,tb);
		try {
			tbs.cutDownTree(t, tbs.findUserByUsername("Aly"));
		} catch (InvalidInputException e) {
			System.out.println(e.getMessage());
			fail();
		}
		assertEquals(Status.CutDown,tbs.getAllTrees().get(0).getStatus());
		assertEquals(tbs.findUserByUsername("Aly"),tbs.getAllTrees().get(0).getLastReportingPerson());
		
		checkResultTree("Oak", false, Status.CutDown, 72,12, "Ahunsic", 1, KindOfLandUse.Residential, treePlanted, 1, tbs.getAllTrees().get(0).getWhenCutDown(), lastSurvey, l1, tbs.findUserByUsername("Aly"), th1,tb);
}
	//Test Case 36: Test Login, create 2 trees and then view in all trees list
	@Test
	public void testLogInCreateThenViewAllTrees() {
		assertEquals(0, tb.getUsers().size());
		String error = "";
		String name = "Aly";
		String phoneNumber = "1234567890";
		String password = "Abc123";
		boolean loginSuccess = false;
		TreeBookService tbs = new TreeBookService(tb);
		try {
			tbs.createUser(phoneNumber, name,password);
		} catch (InvalidInputException e) {
			fail();
		}
		assertEquals(1,tbs.getAllUsers().size());
		checkResultUser("Aly","Abc123","1234567890",tb);
		try {
			loginSuccess = tbs.login("Aly", "Abc123");
		} catch (InvalidInputException e) {
			fail();
		}


		assertEquals(loginSuccess,true);
		
		Calendar setPlant = Calendar.getInstance();
		setPlant.set(2007,Calendar.JANUARY,12,1,27,0);
		Date treePlanted = new Date(setPlant.getTimeInMillis());

		Calendar setCutDown = Calendar.getInstance();
		setCutDown.set(2018,Calendar.DECEMBER,10,1,15,0);
		Date cutDown = new Date(setCutDown.getTimeInMillis());

		Calendar setLastSurvey = Calendar .getInstance();
		setLastSurvey.set(2018,Calendar.DECEMBER,10,10,15,0);
		Date lastSurvey = new Date(setLastSurvey.getTimeInMillis());
		Location l1 = new Location(12,27);
		Location l2 = new Location(12,28);
		SustainabilityAttributes th1 = new SustainabilityAttributes ( 12, 25);
		Tree t1 = null;
		Tree t2 = null;
		try {
			t1 = tbs.plantTree("Oak", false, Status.Planted, 72,12, "Ahunsic", 1, KindOfLandUse.Residential, treePlanted, 1, cutDown, lastSurvey, l1, tbs.findUserByUsername("Aly"), th1);
		} catch (InvalidInputException e) {
			fail();
		}
		assertEquals(1,tbs.getAllTrees().size());
		try {
			t2 = tbs.plantTree("Oak", false, Status.Planted, 74,12, "Ahunsic", 1, KindOfLandUse.Institutional, treePlanted, 1, cutDown, lastSurvey, l2, tbs.findUserByUsername("Aly"), th1);
		} catch (InvalidInputException e) {
			System.out.println(e.getMessage());
			fail();
		}
		assertEquals(2,tbs.getAllTrees().size());
		assertEquals(t1,tbs.getAllTrees().get(0));
		assertEquals(t2,tbs.getAllTrees().get(1));
		
		
		
	}
	
	private void checkResultTree(String aSpecies, boolean aToBeChopped, Status aStatus, float aHeight, 
			float aDiameterOfCanopy, String aMunicipality, float aDiameterOfTrunk,
			KindOfLandUse aLandUse, Date aWhenPlanted, int aReports,
			Date aWhenCutDown, Date aLastSurvey, Location aCoordinates,
			User aLastReportingPerson, SustainabilityAttributes aTreeSustainability,TreeBook tb2) {

		assertEquals(1, tb2.getTrees().size());
		assertEquals(aSpecies, tb2.getTree(0).getSpecies());
		assertEquals(aToBeChopped, tb2.getTree(0).getToBeChopped());
		assertEquals(aStatus, tb2.getTree(0).getStatus());
		assertEquals(aHeight, tb2.getTree(0).getHeight(), 0.01);
		assertEquals(aDiameterOfCanopy, tb2.getTree(0).getDiameterOfCanopy(), 0.01);
		assertEquals(aDiameterOfTrunk, tb2.getTree(0).getDiameterOfTrunk(),0.1);
		assertEquals(aLandUse, tb2.getTree(0).getLandUse());
		assertEquals(aWhenPlanted.toString(), tb2.getTree(0).getWhenPlanted().toString());
		assertEquals(aReports, tb2.getTree(0).getReports());
		assertEquals(aWhenCutDown.toString(), tb2.getTree(0).getWhenCutDown().toString());
		assertEquals(aLastSurvey.toString(), tb2.getTree(0).getLastSurvey().toString());

		//      assertEquals(aCoordinates.toString(), tb2.getTree(0).getCoordinates().toString());
		//		assertEquals(aLastReportingPerson.toString(), tb2.getTree(0).getLastReportingPerson().toString());
		//		assertEquals(aTreeSustainability.toString(), tb2.getTree(0).getTreeSustainability().toString());  
	}
	private void checkResultUser(String name,String password,String phoneNumber,TreeBook tb2) {
		assertEquals(1, tb2.getUsers().size());
		assertEquals(name, tb2.getUser(0).getName());
		assertEquals(password,tb2.getUser(0).getPassword());
		assertEquals(phoneNumber,tb2.getUser(0).getPhoneNumber());
	}

}
		



