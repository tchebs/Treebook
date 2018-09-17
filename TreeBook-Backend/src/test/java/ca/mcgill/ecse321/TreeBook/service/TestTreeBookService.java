package ca.mcgill.ecse321.TreeBook.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyDouble;

import java.io.File;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.util.concurrent.Service;

import ca.mcgill.ecse321.TreeBook.model.ForecastTree;
import ca.mcgill.ecse321.TreeBook.model.Location;
import ca.mcgill.ecse321.TreeBook.model.SustainabilityAttributes;
import ca.mcgill.ecse321.TreeBook.model.Tree;
import ca.mcgill.ecse321.TreeBook.model.Tree.KindOfLandUse;
import ca.mcgill.ecse321.TreeBook.model.Tree.Status;
import ca.mcgill.ecse321.TreeBook.model.TreeBook;
import ca.mcgill.ecse321.TreeBook.model.User;
import ca.mcgill.ecse321.TreeBook.persistence.PersistenceXStream;

public class TestTreeBookService {

	private TreeBook tb;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		PersistenceXStream.initializeModelManager("output" + File.separator + "data.xml");
	}

	@Before
	public void setUp() throws Exception {
		tb = new TreeBook();
	}

	@After
	public void tearDown() throws Exception {
		tb.delete();
	}

	// Test case 1: Test creating user with a null username.

	@Test
	public void testCreateUserUsernameNull() {

		assertEquals(0, tb.getUsers().size());
		String name = null;
		String phoneNumber = "1234567890";
		String password = "Abc123";
		String error = null;

		TreeBookService tbs = new TreeBookService(tb);
		try {
			tbs.createUser(phoneNumber, name, password);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		// check error
		assertEquals("Username cannot be empty!", error);

		// check no change in memory
		assertEquals(0, tb.getUsers().size());
	}

	// Test case 2: Test creating user with empty username.

	@Test
	public void testAddUserEmptyName() {

		assertEquals(0, tb.getUsers().size());

		String name = "";
		String phoneNumber = "1234567890";
		String password = "Abc123";
		String error = null;

		TreeBookService tbs = new TreeBookService(tb);
		try {
			tbs.createUser(phoneNumber, name, password);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		assertEquals("Username cannot be empty!", error);
		assertEquals(0, tb.getUsers().size());
	}

	// Test case 3: Test creating user with password less than 4 characters.
	@Test
	public void testCreateShortPassword() {

		assertEquals(0, tb.getUsers().size());

		String name = "Tudor";
		String phoneNumber = "1234567890";
		String password = "Ab1";
		String error = null;

		TreeBookService tbs = new TreeBookService(tb);
		try {
			tbs.createUser(phoneNumber, name, password);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		assertEquals("Password must be at least four characters!", error);
		assertEquals(0, tb.getUsers().size());
	}

	// Test case 4: Test creating user with password without any digits.
	@Test
	public void testCreatePasswordNoDigits() {

		assertEquals(0, tb.getUsers().size());

		String name = "Tudor";
		String phoneNumber = "1234567890";
		String password = "Abcd";
		String error = null;

		TreeBookService tbs = new TreeBookService(tb);
		try {
			tbs.createUser(phoneNumber, name, password);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		assertEquals("Password must contain at least one digit!", error);
		assertEquals(0, tb.getUsers().size());
	}
	// Test case 5:Test creating user with a phone number that isn't ten digits

	@Test
	public void testCreateUserPhoneNumberNotTenDigits() {

		assertEquals(0, tb.getUsers().size());
		String name = "Matthew";
		String phoneNumber = "0";
		String password = "Abc123";
		String error = null;

		TreeBookService tbs = new TreeBookService(tb);
		try {
			tbs.createUser(phoneNumber, name, password);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		// check error
		assertEquals("Phone Number must be ten digits!", error);

		// check no change in memory
		assertEquals(0, tb.getUsers().size());
	}

	// Test case 6:Test creating user with a phone number that contains letters.

	@Test
	public void testPhoneNumberContent() {

		assertEquals(0, tb.getUsers().size());
		String name = "Matthew";
		String phoneNumber = "1234567a89";
		String password = "Abc123";
		String error = null;

		TreeBookService tbs = new TreeBookService(tb);
		try {
			tbs.createUser(phoneNumber, name, password);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		// check error
		assertEquals("Phone Number must only contain digits!", error);

		// check no change in memory
		assertEquals(0, tb.getUsers().size());
	}

	// Test case 7: Test creating user with a phone number that's already taken.
	@Test
	public void testCreateUserDuplicatePhoneNumber() {

		assertEquals(0, tb.getUsers().size());
		String name = "Matthew";
		String phoneNumber = "1234567891";
		String password = "Abc123";
		String error = null;
		String name2 = "Aly";
		String phoneNumber2 = "1234567891";

		TreeBookService tbs = new TreeBookService(tb);
		try {
			tbs.createUser(phoneNumber, name, password);
		} catch (InvalidInputException e) {
			fail();
		}

		try {
			tbs.createUser(phoneNumber2, name2, password);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		// check error
		assertEquals("Phone Number already in use!", error);

		// check no change in memory
		assertEquals(1, tb.getUsers().size());
	}

	// Test case 8: Test creating user with a username that's already taken.
	@Test
	public void testCreateUserDuplicateUser() {

		assertEquals(0, tb.getUsers().size());
		String name = "Matthew";
		String phoneNumber = "1234567891";
		String error = null;
		String name2 = "Matthew";
		String phoneNumber2 = "1234567891";
		String password = "Abc123";

		TreeBookService tbs = new TreeBookService(tb);
		try {
			tbs.createUser(phoneNumber, name, password);
		} catch (InvalidInputException e) {
			fail();
		}

		try {
			tbs.createUser(phoneNumber2, name2, password);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		// check error
		assertEquals("Username already in use! Phone Number already in use!", error);

		// check no change in memory
		assertEquals(1, tb.getUsers().size());
	}

	// Test case 9: Test correct functionality of Creating a User.
	@Test
	public void testAddUser() {

		assertEquals(0, tb.getUsers().size());

		String name = "Aly";
		String phoneNumber = "1234567890";
		String password = "Abc123";
		TreeBookService tbs = new TreeBookService(tb);
		try {
			tbs.createUser(phoneNumber, name, password);
		} catch (InvalidInputException e) {
			fail();
		}
		checkResultUser(name, tb);
		tb = (TreeBook) PersistenceXStream.loadFromXMLwithXStream();
		checkResultUser(name, tb);
	}

	// Test case 10: Test planting a tree with null as the input for species.
	@Test
	public void testNullSpecies() {

		String error = null;

		TreeBook tb = new TreeBook();
		assertEquals(0, tb.getTrees().size());

		User u1 = new User("12", "Tudor", "abcdefg", true);
		Location l1 = new Location(12, 27);
		SustainabilityAttributes th1 = new SustainabilityAttributes(12, 25);

		Calendar setPlant = Calendar.getInstance();
		setPlant.set(2007, Calendar.JANUARY, 12, 1, 27, 0);
		Date treePlanted = new Date(setPlant.getTimeInMillis());

		Calendar setCutDown = Calendar.getInstance();
		setCutDown.set(2018, Calendar.DECEMBER, 10, 1, 15, 0);
		Date cutDown = new Date(setCutDown.getTimeInMillis());

		Calendar setLastSurvey = Calendar.getInstance();
		setLastSurvey.set(2018, Calendar.DECEMBER, 10, 10, 15, 0);
		Date lastSurvey = new Date(setLastSurvey.getTimeInMillis());

		// test model in memory
		TreeBookService tbs = new TreeBookService(tb);

		try {
			tbs.plantTree(null, false, Status.Planted, 72, 12, "Ahunsic", 1, KindOfLandUse.Residential, treePlanted, 1,
					cutDown, lastSurvey, l1, u1, th1);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}

		// check error
		assertEquals("The tree species must be defined.", error);

		// check no change in memory
		assertEquals(0, tb.getTrees().size());
	}

	// Test case 11: Test planting a tree with null as the input for species.
	@Test
	public void testEmptySpecies() {

		String error = null;

		TreeBook tb = new TreeBook();
		assertEquals(0, tb.getTrees().size());

		User u1 = new User("12", "Tudor", "abcdefg", true);
		Location l1 = new Location(12, 27);
		SustainabilityAttributes th1 = new SustainabilityAttributes(12, 25);

		Calendar setPlant = Calendar.getInstance();
		setPlant.set(2007, Calendar.JANUARY, 12, 1, 27, 0);
		Date treePlanted = new Date(setPlant.getTimeInMillis());

		Calendar setCutDown = Calendar.getInstance();
		setCutDown.set(2018, Calendar.DECEMBER, 10, 1, 15, 0);
		Date cutDown = new Date(setCutDown.getTimeInMillis());

		Calendar setLastSurvey = Calendar.getInstance();
		setLastSurvey.set(2018, Calendar.DECEMBER, 10, 10, 15, 0);
		Date lastSurvey = new Date(setLastSurvey.getTimeInMillis());

		// test model in memory
		TreeBookService tbs = new TreeBookService(tb);

		try {
			tbs.plantTree("", false, Status.Planted, 72, 12, "Ahunsic", 1, KindOfLandUse.Residential, treePlanted, 1,
					cutDown, lastSurvey, l1, u1, th1);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}

		// check error
		assertEquals("The tree species must be defined.", error);

		// check no change in memory
		assertEquals(0, tb.getTrees().size());
	}

	// Test case 12: Test planting a tree with null as the input for municipality.
	@Test
	public void testNullMunicipality() {

		String error = null;

		TreeBook tb = new TreeBook();
		assertEquals(0, tb.getTrees().size());

		User u1 = new User("1234567891", "Tudor", "Abc123", true);
		Location l1 = new Location(12, 27);
		SustainabilityAttributes th1 = new SustainabilityAttributes(12, 25);

		Calendar setPlant = Calendar.getInstance();
		setPlant.set(2007, Calendar.JANUARY, 12, 1, 27, 0);
		Date treePlanted = new Date(setPlant.getTimeInMillis());

		Calendar setCutDown = Calendar.getInstance();
		setCutDown.set(2018, Calendar.DECEMBER, 10, 1, 15, 0);
		Date cutDown = new Date(setCutDown.getTimeInMillis());

		Calendar setLastSurvey = Calendar.getInstance();
		setLastSurvey.set(2018, Calendar.DECEMBER, 10, 10, 15, 0);
		Date lastSurvey = new Date(setLastSurvey.getTimeInMillis());

		// test model in memory
		TreeBookService tbs = new TreeBookService(tb);

		try {
			tbs.plantTree("oak", false, Status.Planted, 72, 12, null, 1, KindOfLandUse.Residential, treePlanted, 1,
					cutDown, lastSurvey, l1, u1, th1);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}

		// check error
		assertEquals("The municipality must be defined.", error);

		// check no change in memory
		assertEquals(0, tb.getTrees().size());
	}

	// Test case 13: Test planting a tree with an empty string as the input for
	// municipality.
	@Test
	public void testEmptyMunicipality() {

		String error = null;

		TreeBook tb = new TreeBook();
		assertEquals(0, tb.getTrees().size());

		User u1 = new User("1234567891", "Tudor", "Abc123", true);
		Location l1 = new Location(12, 27);
		SustainabilityAttributes th1 = new SustainabilityAttributes(12, 25);

		Calendar setPlant = Calendar.getInstance();
		setPlant.set(2007, Calendar.JANUARY, 12, 1, 27, 0);
		Date treePlanted = new Date(setPlant.getTimeInMillis());

		Calendar setCutDown = Calendar.getInstance();
		setCutDown.set(2018, Calendar.DECEMBER, 10, 1, 15, 0);
		Date cutDown = new Date(setCutDown.getTimeInMillis());

		Calendar setLastSurvey = Calendar.getInstance();
		setLastSurvey.set(2018, Calendar.DECEMBER, 10, 10, 15, 0);
		Date lastSurvey = new Date(setLastSurvey.getTimeInMillis());

		// test model in memory
		TreeBookService tbs = new TreeBookService(tb);

		try {
			tbs.plantTree("oak", false, Status.Planted, 72, 12, "", 1, KindOfLandUse.Residential, treePlanted, 1,
					cutDown, lastSurvey, l1, u1, th1);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}

		// check error
		assertEquals("The municipality must be defined.", error);

		// check no change in memory
		assertEquals(0, tb.getTrees().size());
	}

	// Test case 14: Test planting a tree (ALL IS OKAY test)
	@Test
	public void testPlantTree() {
		TreeBook tb = new TreeBook();
		assertEquals(0, tb.getTrees().size());

		User u1 = new User("1234567891", "Tudor", "Abc123", true);
		Location l1 = new Location(12, 27);
		SustainabilityAttributes th1 = new SustainabilityAttributes(12, 25);

		Calendar setPlant = Calendar.getInstance();
		setPlant.set(2007, Calendar.JANUARY, 12, 1, 27, 0);
		Date treePlanted = new Date(setPlant.getTimeInMillis());

		Calendar setCutDown = Calendar.getInstance();
		setCutDown.set(2018, Calendar.DECEMBER, 10, 1, 15, 0);
		Date cutDown = new Date(setCutDown.getTimeInMillis());

		Calendar setLastSurvey = Calendar.getInstance();
		setLastSurvey.set(2018, Calendar.DECEMBER, 10, 10, 15, 0);
		Date lastSurvey = new Date(setLastSurvey.getTimeInMillis());

		// test model in memory
		TreeBookService tbs = new TreeBookService(tb);

		try {
			tbs.plantTree("Oak", false, Status.Planted, 72, 12, "Ahunsic", 1, KindOfLandUse.Residential, treePlanted, 1,
					cutDown, lastSurvey, l1, u1, th1);
		} catch (InvalidInputException e) {
			fail();
		}

		checkResultTree("Oak", false, Status.Planted, 72, 12, "Ahunsic", 1, KindOfLandUse.Residential, treePlanted, 1,
				cutDown, lastSurvey, l1, u1, th1, tb);
		// test file

		TreeBook tb2 = (TreeBook) PersistenceXStream.loadFromXMLwithXStream();
		checkResultTree("Oak", false, Status.Planted, 72, 12, "Ahunsic", 1, KindOfLandUse.Residential, treePlanted, 1,
				cutDown, lastSurvey, l1, u1, th1, tb2);
		tb2.delete();

	}

	// Test case 15: Test the method that gets all trees.
	@Test
	public void testGetAllTrees() {
		TreeBook tb = new TreeBook();
		assertEquals(0, tb.getTrees().size());

		String[] species = { "Oak", "Yew", "Maple" };
		User u1 = new User("1234567891", "Tudor", "Abc123", true);
		Location[] l1 = { new Location(12, 27), new Location(12, 29), new Location(12, 37) };
		SustainabilityAttributes th1 = new SustainabilityAttributes(12, 25);

		Calendar setPlant = Calendar.getInstance();
		setPlant.set(2007, Calendar.JANUARY, 12, 1, 27, 0);
		Date treePlanted = new Date(setPlant.getTimeInMillis());

		Calendar setCutDown = Calendar.getInstance();
		setCutDown.set(2018, Calendar.DECEMBER, 10, 1, 15, 0);
		Date cutDown = new Date(setCutDown.getTimeInMillis());

		Calendar setLastSurvey = Calendar.getInstance();
		setLastSurvey.set(2018, Calendar.DECEMBER, 10, 10, 15, 0);
		Date lastSurvey = new Date(setLastSurvey.getTimeInMillis());

		TreeBookService tbs = new TreeBookService(tb);

		for (int i = 0; i < 3; i++) {
			try {
				tbs.plantTree(species[i], false, Status.Planted, 72, 12, "Ahunsic", 1, KindOfLandUse.Residential,
						treePlanted, 1, cutDown, lastSurvey, l1[i], u1, th1);
			} catch (InvalidInputException e) {
				// Check that no error occurred
				fail();
			}
		}

		List<Tree> registeredTrees = tbs.getAllTrees();

		// check number of registered trees
		assertEquals(3, registeredTrees.size());

		// check each tree
		for (int i = 0; i < 3; i++) {
			assertEquals(species[i], registeredTrees.get(i).getSpecies());
		}

	}

	// Test case 16: Test creating user with password that doesn't contain an
	// uppercase.
	@Test
	public void testCreatePasswordNoUppercase() {

		assertEquals(0, tb.getUsers().size());

		String name = "Tudor";
		String phoneNumber = "1234567890";
		String password = "abc123";
		String error = null;

		TreeBookService tbs = new TreeBookService(tb);
		try {
			tbs.createUser(phoneNumber, name, password);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		assertEquals("Password must contain at least one uppercase letter!", error);
		assertEquals(0, tb.getUsers().size());
	}

	// Test case 17: Test the method that finds tree by ID.
	@Test
	public void testFindTreeByID() throws InvalidInputException {
		TreeBook tb = new TreeBook();
		assertEquals(0, tb.getTrees().size());

		String[] species = { "Oak", "Yew", "Maple" };
		User u1 = new User("1234567891", "Tudor", "Abc123", true);
		Location[] l1 = { new Location(12, 27), new Location(12, 29), new Location(12, 37) };
		SustainabilityAttributes th1 = new SustainabilityAttributes(12, 25);

		Calendar setPlant = Calendar.getInstance();
		setPlant.set(2007, Calendar.JANUARY, 12, 1, 27, 0);
		Date treePlanted = new Date(setPlant.getTimeInMillis());

		Calendar setCutDown = Calendar.getInstance();
		setCutDown.set(2018, Calendar.DECEMBER, 10, 1, 15, 0);
		Date cutDown = new Date(setCutDown.getTimeInMillis());

		Calendar setLastSurvey = Calendar.getInstance();
		setLastSurvey.set(2018, Calendar.DECEMBER, 10, 10, 15, 0);
		Date lastSurvey = new Date(setLastSurvey.getTimeInMillis());

		TreeBookService tbs = new TreeBookService(tb);

		for (int i = 0; i < 3; i++) {
			try {
				tbs.plantTree(species[i], false, Status.Planted, 72, 12, "Ahunsic", 1, KindOfLandUse.Residential,
						treePlanted, 1, cutDown, lastSurvey, l1[i], u1, th1);
			} catch (InvalidInputException e) {
				// Check that no error occurred
				fail();
			}
		}

		List<Tree> registeredTrees = tbs.getAllTrees();

		// Make sure three trees have been uploaded
		assertEquals(3, registeredTrees.size());

		// check if the tree is found by its identification.

		assertEquals(registeredTrees.get(2), tbs.findTreeByID(registeredTrees.get(2).getId()));
	}

	// Test case 18: Test the method that finds users by their username.
	@Test
	public void testFindUserByUsername() throws InvalidInputException {

		assertEquals(0, tb.getUsers().size());

		String[] names = { "Charles", "Julianna" };
		String[] phoneNumbers = { "1234567890", "1234567891" };
		String[] passwords = { "Abc123", "Def456" };

		TreeBookService tbs = new TreeBookService(tb);

		for (int i = 0; i < names.length; i++) {
			try {
				tbs.createUser(phoneNumbers[i], names[i], passwords[i]);
			} catch (InvalidInputException e) {
				// Check that no error occurred
				fail();
			}
		}

		List<User> registeredParticipants = tbs.getAllUsers();

		// check number of registered participants
		assertEquals(2, registeredParticipants.size());

		// check each participant
		for (int i = 0; i < names.length; i++) {
			assertEquals(names[i], registeredParticipants.get(i).getName());
		}

		// Make sure the user is found correctly from its username.
		assertEquals(registeredParticipants.get(1), tbs.findUserByUsername("Julianna"));

	}

	// Test case 19: Test cutting down an already cut down tree.
	@Test
	public void testCutDownTreeAlreadyCutDown() throws InvalidInputException {
		TreeBook tb = new TreeBook();
		assertEquals(0, tb.getTrees().size());

		Location l1 = new Location(12, 27);
		SustainabilityAttributes th1 = new SustainabilityAttributes(12, 25);

		Calendar setPlant = Calendar.getInstance();
		setPlant.set(2007, Calendar.JANUARY, 12, 1, 27, 0);
		Date treePlanted = new Date(setPlant.getTimeInMillis());

		Calendar setCutDown = Calendar.getInstance();
		setCutDown.set(2018, Calendar.DECEMBER, 10, 10, 15, 0);
		Date cutDown = new Date(setCutDown.getTimeInMillis());

		Calendar setLastSurvey = Calendar.getInstance();
		setLastSurvey.set(2018, Calendar.DECEMBER, 10, 10, 15, 0);
		Date lastSurvey = new Date(setLastSurvey.getTimeInMillis());

		// test model in memory
		TreeBookService tbs = new TreeBookService(tb);
		String password = "Abc123";
		User u1 = tbs.createUser("0123456789", "Tudor", password, true);
		Tree t = tbs.plantTree("Oak", false, Status.Planted, 72, 12, "Ahunsic", 1, KindOfLandUse.Residential,
				treePlanted, 1, cutDown, lastSurvey, l1, u1, th1);
		tbs.removeTree(t, u1);
		String error = null;
		try {
			t = tbs.plantTree("Oak", false, Status.CutDown, 72, 12, "Ahunsic", 1, KindOfLandUse.Residential,
					treePlanted, 1, cutDown, lastSurvey, l1, u1, th1);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			tbs.cutDownTree(tbs.findTreeByID(t.getId()), u1);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		assertEquals("Tree is already cut down!", error);

		checkResultTree("Oak", false, Status.CutDown, 72, 12, "Ahunsic", 1, KindOfLandUse.Residential, treePlanted, 1,
				cutDown, lastSurvey, l1, u1, th1, tb);
		// test file
		TreeBook tb2 = (TreeBook) PersistenceXStream.loadFromXMLwithXStream();
		checkResultTree("Oak", false, Status.CutDown, 72, 12, "Ahunsic", 1, KindOfLandUse.Residential, treePlanted, 1,
				cutDown, lastSurvey, l1, u1, th1, tb2);
		tb2.delete();

	}

	// Test case 20: Test cut down tree(ALL IS OKAY TEST).
	@Test
	public void testCutDownTree() throws InvalidInputException {
		TreeBook tb = new TreeBook();
		assertEquals(0, tb.getTrees().size());

		Location l1 = new Location(12, 27);
		SustainabilityAttributes th1 = new SustainabilityAttributes(12, 25);

		Calendar setPlant = Calendar.getInstance();
		setPlant.set(2007, Calendar.JANUARY, 12, 1, 27, 0);
		Date treePlanted = new Date(setPlant.getTimeInMillis());

		Calendar setCutDown = Calendar.getInstance();
		setCutDown.set(2018, Calendar.DECEMBER, 10, 10, 15, 0);
		Date cutDown = new Date(setCutDown.getTimeInMillis());

		Calendar setLastSurvey = Calendar.getInstance();
		setLastSurvey.set(2018, Calendar.DECEMBER, 10, 10, 15, 0);
		Date lastSurvey = new Date(setLastSurvey.getTimeInMillis());

		// test model in memory
		TreeBookService tbs = new TreeBookService(tb);
		User u1 = tbs.createUser("0123456789", "Tudor", "Abc123", true);

		Tree t = tbs.plantTree("Oak", false, Status.Planted, 72, 12, "Ahunsic", 1, KindOfLandUse.Residential,
				treePlanted, 1, cutDown, lastSurvey, l1, u1, th1);
		tbs.removeTree(t, u1);
		try {
			t = tbs.plantTree("Oak", false, Status.Planted, 72, 12, "Ahunsic", 1, KindOfLandUse.Residential,
					treePlanted, 1, cutDown, lastSurvey, l1, u1, th1);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			tbs.cutDownTree(tbs.findTreeByID(t.getId()), u1);
		} catch (InvalidInputException e) {
			System.out.println(e.getMessage());
			fail();
		}
		checkResultTree("Oak", false, Status.CutDown, 72, 12, "Ahunsic", 1, KindOfLandUse.Residential, treePlanted, 1,
				tbs.findTreeByID(t.getId()).getWhenCutDown(), lastSurvey, l1, u1, th1, tb);
		// test file
		TreeBook tb2 = (TreeBook) PersistenceXStream.loadFromXMLwithXStream();
		checkResultTree("Oak", false, Status.CutDown, 72, 12, "Ahunsic", 1, KindOfLandUse.Residential, treePlanted, 1,
				tbs.findTreeByID(t.getId()).getWhenCutDown(), lastSurvey, l1, u1, th1, tb2);
		tb2.delete();
	}

	@Test
	public void testCutDownTreeNotExpert() throws InvalidInputException {
		TreeBook tb = new TreeBook();
		assertEquals(0, tb.getTrees().size());

		Location l1 = new Location(12, 27);
		SustainabilityAttributes th1 = new SustainabilityAttributes(12, 25);

		Calendar setPlant = Calendar.getInstance();
		setPlant.set(2007, Calendar.JANUARY, 12, 1, 27, 0);
		Date treePlanted = new Date(setPlant.getTimeInMillis());

		Calendar setCutDown = Calendar.getInstance();
		setCutDown.set(2018, Calendar.DECEMBER, 10, 10, 15, 0);
		Date cutDown = new Date(setCutDown.getTimeInMillis());

		Calendar setLastSurvey = Calendar.getInstance();
		setLastSurvey.set(2018, Calendar.DECEMBER, 10, 10, 15, 0);
		Date lastSurvey = new Date(setLastSurvey.getTimeInMillis());

		// test model in memory
		TreeBookService tbs = new TreeBookService(tb);
		User u1 = tbs.createUser("0123456789", "Tudor", "Abc123", true);
		User u2 = tbs.createUser("0123456780", "Tudo", "Abc123");

		Tree t = tbs.plantTree("Oak", false, Status.Planted, 72, 12, "Ahunsic", 1, KindOfLandUse.Residential,
				treePlanted, 1, cutDown, lastSurvey, l1, u1, th1);
		tbs.removeTree(t, u1);
		try {
			t = tbs.plantTree("Oak", false, Status.Planted, 72, 12, "Ahunsic", 1, KindOfLandUse.Residential,
					treePlanted, 1, cutDown, lastSurvey, l1, u1, th1);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			tbs.cutDownTree(tbs.findTreeByID(t.getId()), u2);
		} catch (InvalidInputException e) {
			assertEquals(e.getMessage(), "User must be expert to cut down a tree!");
		}
		checkResultTree("Oak", false, Status.Planted, 72, 12, "Ahunsic", 1, KindOfLandUse.Residential, treePlanted, 1,
				tbs.findTreeByID(t.getId()).getWhenCutDown(), lastSurvey, l1, u1, th1, tb);
		// test file
		TreeBook tb2 = (TreeBook) PersistenceXStream.loadFromXMLwithXStream();
		checkResultTree("Oak", false, Status.Planted, 72, 12, "Ahunsic", 1, KindOfLandUse.Residential, treePlanted, 1,
				tbs.findTreeByID(t.getId()).getWhenCutDown(), lastSurvey, l1, u1, th1, tb2);
		tb2.delete();
	}

	// Test case 21: Test the method that gets all users.
	@Test
	public void testGetAllUsers() {
		assertEquals(0, tb.getUsers().size());

		String[] names = { "Charles", "Julianna" };
		String[] phoneNumbers = { "1234567890", "1234567891" };
		String[] passwords = { "Abc123", "Def456" };

		TreeBookService tbs = new TreeBookService(tb);

		for (int i = 0; i < names.length; i++) {
			try {
				tbs.createUser(phoneNumbers[i], names[i], passwords[i]);
			} catch (InvalidInputException e) {
				// Check that no error occurred
				fail();
			}
		}

		List<User> registeredParticipants = tbs.getAllUsers();

		// check number of registered participants
		assertEquals(2, registeredParticipants.size());

		// check each participant
		for (int i = 0; i < names.length; i++) {
			assertEquals(names[i], registeredParticipants.get(i).getName());
		}

	}

	// Test Case 22: Test Login with null username:
	@Test
	public void testLoginNullUsername() throws InvalidInputException {

		assertEquals(0, tb.getUsers().size());
		String error = "";
		String name = "Aly";
		String phoneNumber = "1234567890";
		String password = "Abc123";
		TreeBookService tbs = new TreeBookService(tb);
		try {
			tbs.createUser(phoneNumber, name, password);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			tbs.login(null, "Abc123");
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}

		assertEquals("Username cannot be empty!", error);

	}

	// Test Case 23: Test Login with empty username:
	@Test
	public void testLoginEmptyUsername() throws InvalidInputException {

		assertEquals(0, tb.getUsers().size());
		String error = "";
		String name = "Aly";
		String phoneNumber = "1234567890";
		String password = "Abc123";
		TreeBookService tbs = new TreeBookService(tb);
		try {
			tbs.createUser(phoneNumber, name, password);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			tbs.login("", "Abc123");
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}

		assertEquals("Username cannot be empty!", error);

	}

	// Test Case 24: Test Login with null password:
	@Test
	public void testLoginNullPassword() throws InvalidInputException {

		assertEquals(0, tb.getUsers().size());
		String error = "";
		String name = "Aly";
		String phoneNumber = "1234567890";
		String password = "Abc123";
		TreeBookService tbs = new TreeBookService(tb);
		try {
			tbs.createUser(phoneNumber, name, password);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			tbs.login("Aly", null);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		assertEquals("Password cannot be empty!", error);
	}

	// Test Case 25: Test Login with empty password:
	@Test
	public void testLoginEmptyPassword() throws InvalidInputException {

		assertEquals(0, tb.getUsers().size());
		String error = "";
		String name = "Aly";
		String phoneNumber = "1234567890";
		String password = "Abc123";
		TreeBookService tbs = new TreeBookService(tb);
		try {
			tbs.createUser(phoneNumber, name, password);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			tbs.login("Aly", "");
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		assertEquals("Password cannot be empty!", error);
	}

	// Test Case 26: Test Login with incorrect username:
	@Test
	public void testLoginIncorrectUsername() throws InvalidInputException {

		assertEquals(0, tb.getUsers().size());
		String name = "Aly";
		String phoneNumber = "1234567890";
		String password = "Abc123";
		TreeBookService tbs = new TreeBookService(tb);
		boolean loginSuccess = true;
		try {
			tbs.createUser(phoneNumber, name, password);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			loginSuccess = tbs.login("John", "Abc123");
		} catch (InvalidInputException e) {
			fail();
		}

		assertEquals(false, loginSuccess);
	}

	// Test Case 27: Test Login with incorrect password:
	@Test
	public void testLoginIncorrectPassword() throws InvalidInputException {

		assertEquals(0, tb.getUsers().size());
		String error = "";
		String name = "Aly";
		String phoneNumber = "1234567890";
		String password = "Abc123";
		boolean loginSuccess = true;
		TreeBookService tbs = new TreeBookService(tb);
		try {
			tbs.createUser(phoneNumber, name, password);
		} catch (InvalidInputException e) {
			fail();
		}

		try {
			loginSuccess = tbs.login("Aly", "123Abc");
		} catch (InvalidInputException e) {
			fail();
		}

		assertEquals(false, loginSuccess);
	}

	// Test case 29: Test creating user with password that doesn't contain a
	// lowercase.
	@Test
	public void testCreatePasswordNoLowercase() {

		assertEquals(0, tb.getUsers().size());

		String name = "Tudor";
		String phoneNumber = "1234567890";
		String password = "ABC123";
		String error = null;

		TreeBookService tbs = new TreeBookService(tb);
		try {
			tbs.createUser(phoneNumber, name, password);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		assertEquals("Password must contain at least one lowercase letter!", error);
		assertEquals(0, tb.getUsers().size());
	}

	// Test case 30: Test creating user with password that contains a symbol.
	@Test
	public void testCreatePasswordWithSymbol() {

		assertEquals(0, tb.getUsers().size());

		String name = "Tudor";
		String phoneNumber = "1234567890";
		String password = "!Abcde1";
		String error = null;

		TreeBookService tbs = new TreeBookService(tb);
		try {
			tbs.createUser(phoneNumber, name, password);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		assertEquals("Password cannot contain a symbol!", error);
		assertEquals(0, tb.getUsers().size());
	}

	// Test case 33: Test creating user with a space as its username.

	@Test
	public void testCreateUserUsernameSpaces() {

		assertEquals(0, tb.getUsers().size());
		String name = " ";
		String phoneNumber = "1234567890";
		String password = "Abc123";
		String error = null;

		TreeBookService tbs = new TreeBookService(tb);
		try {
			tbs.createUser(phoneNumber, name, password);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		// check error
		assertEquals("Username cannot be empty!", error);

		// check no change in memory
		assertEquals(0, tb.getUsers().size());
	}

	// Test case 34: Test cut down null tree.
	@Test
	public void testCutDownNullTree() throws InvalidInputException {
		TreeBook tb = new TreeBook();
		assertEquals(0, tb.getTrees().size());
		String error = "";
		Location l1 = new Location(12, 27);
		SustainabilityAttributes th1 = new SustainabilityAttributes(12, 25);

		// test model in memory
		TreeBookService tbs = new TreeBookService(tb);
		User u1 = tbs.createUser("0123456789", "Tudor", "Abc123", true);

		try {
			tbs.cutDownTree(null, u1);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		// check error
		assertEquals("Tree has to exist!", error);
	}

	// Test case 35: Test cut down tree with null user.
	@Test
	public void testCutDownTreeNullUser() throws InvalidInputException {
		TreeBook tb = new TreeBook();
		String error = "";
		assertEquals(0, tb.getTrees().size());

		Location l1 = new Location(12, 27);
		SustainabilityAttributes th1 = new SustainabilityAttributes(12, 25);

		Calendar setPlant = Calendar.getInstance();
		setPlant.set(2007, Calendar.JANUARY, 12, 1, 27, 0);
		Date treePlanted = new Date(setPlant.getTimeInMillis());

		Calendar setCutDown = Calendar.getInstance();
		setCutDown.set(2018, Calendar.DECEMBER, 10, 10, 15, 0);
		Date cutDown = new Date(setCutDown.getTimeInMillis());

		Calendar setLastSurvey = Calendar.getInstance();
		setLastSurvey.set(2018, Calendar.DECEMBER, 10, 10, 15, 0);
		Date lastSurvey = new Date(setLastSurvey.getTimeInMillis());

		// test model in memory
		TreeBookService tbs = new TreeBookService(tb);
		User u1 = tbs.createUser("0123456789", "Tudor", "Abc123", true);

		Tree t = tbs.plantTree("Oak", false, Status.Planted, 72, 12, "Ahunsic", 1, KindOfLandUse.Residential,
				treePlanted, 1, cutDown, lastSurvey, l1, u1, th1);
		tbs.removeTree(t, u1);
		try {
			t = tbs.plantTree("Oak", false, Status.Planted, 72, 12, "Ahunsic", 1, KindOfLandUse.Residential,
					treePlanted, 1, cutDown, lastSurvey, l1, u1, th1);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			tbs.cutDownTree(tbs.findTreeByID(t.getId()), null);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}

		assertEquals("User has to exist!", error);
		// assertEquals ("User has to exist! User must be expert to cut down a
		// tree!",error);
	}

	// below is the testing sequence for forecasting

	@Test
	public void testInitializeForecastTrees() throws InvalidInputException {

		TreeBook tb1 = new TreeBook();
		String error = "";
		assertEquals(0, tb1.getTrees().size());

		Location l1 = new Location(12, 27);
		SustainabilityAttributes th1 = new SustainabilityAttributes(12, 25);

		Calendar setPlant = Calendar.getInstance();
		setPlant.set(2007, Calendar.JANUARY, 12, 1, 27, 0);
		Date treePlanted = new Date(setPlant.getTimeInMillis());

		Calendar setCutDown = Calendar.getInstance();
		setCutDown.set(2018, Calendar.DECEMBER, 10, 10, 15, 0);
		Date cutDown = new Date(setCutDown.getTimeInMillis());

		Calendar setLastSurvey = Calendar.getInstance();
		setLastSurvey.set(2018, Calendar.DECEMBER, 10, 10, 15, 0);
		Date lastSurvey = new Date(setLastSurvey.getTimeInMillis());

		TreeBookService tbs = new TreeBookService(tb1);
		User u1 = tbs.createUser("0123456789", "Charles", "Abc123");

		Tree t = tbs.plantTree("Oak", false, Status.Planted, 72, 12, "Ahunsic", 1, KindOfLandUse.Residential,
				treePlanted, 1, cutDown, lastSurvey, l1, u1, th1);

		u1.setIsSuper(true);

		assertFalse(u1.hasForecastTrees());

		try {
			tbs.initializeForecastTrees(u1);
		} catch (InvalidInputException e) {
			e.printStackTrace();
		}
		assertTrue(u1.hasForecastTrees());
	}

	@Test
	public void testPlantForecastTree() throws InvalidInputException {

		TreeBook tb1 = new TreeBook();
		String error = "";
		assertEquals(0, tb1.getTrees().size());

		Location l1 = new Location(12, 27);
		SustainabilityAttributes th1 = new SustainabilityAttributes(12, 25);

		Calendar setPlant = Calendar.getInstance();
		setPlant.set(2007, Calendar.JANUARY, 12, 1, 27, 0);
		Date treePlanted = new Date(setPlant.getTimeInMillis());

		Calendar setCutDown = Calendar.getInstance();
		setCutDown.set(2018, Calendar.DECEMBER, 10, 10, 15, 0);
		Date cutDown = new Date(setCutDown.getTimeInMillis());

		Calendar setLastSurvey = Calendar.getInstance();
		setLastSurvey.set(2018, Calendar.DECEMBER, 10, 10, 15, 0);
		Date lastSurvey = new Date(setLastSurvey.getTimeInMillis());

		TreeBookService tbs = new TreeBookService(tb1);
		User u1 = tbs.createUser("0123456789", "Charles", "Abc123");
		u1.setIsSuper(true);

		try {
			tbs.initializeForecastTrees(u1);
		} catch (InvalidInputException e) {
			e.printStackTrace();
		}

		assertFalse(u1.hasForecastTrees());
		assertFalse(tb1.hasTrees());

		ForecastTree t = tbs.plantForecastTree("Oak", false, Status.Planted, 72, 12, "Ahunsic", 1,
				KindOfLandUse.Residential, treePlanted, 1, cutDown, lastSurvey, l1, u1, th1);

		assertTrue(u1.hasForecastTrees());
		assertFalse(tb1.hasTrees());
	}

	@Test
	public void testUpdateForecastBiodiversity() throws InvalidInputException {

		TreeBook tb1 = new TreeBook();
		String error = "";
		assertEquals(0, tb1.getTrees().size());

		Location l1 = new Location(12, 27);
		SustainabilityAttributes th1 = new SustainabilityAttributes(12, 25);

		Calendar setPlant = Calendar.getInstance();
		setPlant.set(2007, Calendar.JANUARY, 12, 1, 27, 0);
		Date treePlanted = new Date(setPlant.getTimeInMillis());

		Calendar setCutDown = Calendar.getInstance();
		setCutDown.set(2018, Calendar.DECEMBER, 10, 10, 15, 0);
		Date cutDown = new Date(setCutDown.getTimeInMillis());

		Calendar setLastSurvey = Calendar.getInstance();
		setLastSurvey.set(2018, Calendar.DECEMBER, 10, 10, 15, 0);
		Date lastSurvey = new Date(setLastSurvey.getTimeInMillis());

		TreeBookService tbs = new TreeBookService(tb1);
		User u1 = tbs.createUser("0123456789", "Charles", "Abc123");
		u1.setIsSuper(true);

		assertFalse(tb1.hasTrees());
		Tree t = tbs.plantTree("Oak", false, Status.Planted, 72, 12, "Ahunsic", 1, KindOfLandUse.Residential,
				treePlanted, 1, cutDown, lastSurvey, l1, u1, th1);

		assertTrue(tb1.hasTrees());
		assertFalse(u1.hasForecastTrees());

		try {
			tbs.initializeForecastTrees(u1);
		} catch (InvalidInputException e) {
			e.printStackTrace();
		}

		assertTrue(u1.hasForecastTrees());
		assertEquals(1, u1.getForecastTree(0).getTreeSustainability().getBiodiversityIndex(), 0.1);
		assertEquals(1, tb1.getTree(0).getTreeSustainability().getBiodiversityIndex(), 0.1);

		ForecastTree t1 = tbs.plantForecastTree("Oak", false, Status.Planted, 70, 12, "Ahunsic", 1,
				KindOfLandUse.Residential, treePlanted, 1, cutDown, lastSurvey, l1, u1, th1);

		assertEquals(2, u1.getForecastTrees().size());
		assertEquals(0.5, u1.getForecastTree(0).getTreeSustainability().getBiodiversityIndex(), 0.1);
		assertEquals(0.5, u1.getForecastTree(1).getTreeSustainability().getBiodiversityIndex(), 0.1);
		assertEquals(1, tb1.getTrees().size());
		assertEquals(1, tb1.getTree(0).getTreeSustainability().getBiodiversityIndex(), 0.1);
	}

	@Test
	public void testCutDownForecastTree() throws InvalidInputException {
		TreeBook tb1 = new TreeBook();
		String error = "";
		assertEquals(0, tb1.getTrees().size());

		Location l1 = new Location(12, 27);
		SustainabilityAttributes th1 = new SustainabilityAttributes(12, 25);

		Calendar setPlant = Calendar.getInstance();
		setPlant.set(2007, Calendar.JANUARY, 12, 1, 27, 0);
		Date treePlanted = new Date(setPlant.getTimeInMillis());

		Calendar setCutDown = Calendar.getInstance();
		setCutDown.set(2018, Calendar.DECEMBER, 10, 10, 15, 0);
		Date cutDown = new Date(setCutDown.getTimeInMillis());

		Calendar setLastSurvey = Calendar.getInstance();
		setLastSurvey.set(2018, Calendar.DECEMBER, 10, 10, 15, 0);
		Date lastSurvey = new Date(setLastSurvey.getTimeInMillis());

		TreeBookService tbs = new TreeBookService(tb1);
		User u1 = tbs.createUser("0123456789", "Charles", "Abc123");
		u1.setIsSuper(true);

		assertFalse(tb1.hasTrees());
		Tree t = tbs.plantTree("Oak", false, Status.Planted, 72, 12, "Ahunsic", 1, KindOfLandUse.Residential,
				treePlanted, 1, cutDown, lastSurvey, l1, u1, th1);

		assertTrue(tb1.hasTrees());
		assertEquals(1, tb1.getTrees().size());
		assertFalse(u1.hasForecastTrees());

		try {
			tbs.initializeForecastTrees(u1);
		} catch (InvalidInputException e) {
			e.printStackTrace();
		}

		assertTrue(u1.hasForecastTrees());
		assertEquals(1, u1.getForecastTrees().size());

		tbs.cutDownForecastTree(u1.getForecastTree(0), u1);

		assertTrue(u1.getForecastTree(0).getStatus().toString().contentEquals("CutDown"));
		assertTrue(tb1.getTree(0).getStatus().toString().contentEquals("Planted"));
	}

	// above is the testing sequence for forecasting
	@Test
	public void requestTreeDuplicateLocationInRequested() {
		User u1 = new User("12345", "Tudor", "abcdefg", true);
		TreeBook tb = new TreeBook();
		TreeBookService tbs = new TreeBookService(tb);
		try {
			tbs.requestTree(11, 11, u1);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			tbs.requestTree(11, 11, u1);
		} catch (InvalidInputException e) {
			assertEquals("Tree already exists within requested trees! ", e.getMessage());
		}

	}

	@Test
	public void requestTreeDuplicateLocationInPlanted() {
		TreeBook tb = new TreeBook();
		assertEquals(0, tb.getTrees().size());

		User u1 = new User("1234567891", "Tudor", "Abc123", true);
		Location l1 = new Location(12, 27);
		SustainabilityAttributes th1 = new SustainabilityAttributes(12, 25);

		Calendar setPlant = Calendar.getInstance();
		setPlant.set(2007, Calendar.JANUARY, 12, 1, 27, 0);
		Date treePlanted = new Date(setPlant.getTimeInMillis());

		Calendar setCutDown = Calendar.getInstance();
		setCutDown.set(2018, Calendar.DECEMBER, 10, 1, 15, 0);
		Date cutDown = new Date(setCutDown.getTimeInMillis());

		Calendar setLastSurvey = Calendar.getInstance();
		setLastSurvey.set(2018, Calendar.DECEMBER, 10, 10, 15, 0);
		Date lastSurvey = new Date(setLastSurvey.getTimeInMillis());

		// test model in memory
		TreeBookService tbs = new TreeBookService(tb);

		try {
			tbs.plantTree("Oak", false, Status.Planted, 72, 12, "Ahunsic", 1, KindOfLandUse.Residential, treePlanted, 1,
					cutDown, lastSurvey, l1, u1, th1);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			tbs.requestTree(12, 27, u1);
		} catch (InvalidInputException e) {
			assertEquals(e.getMessage(), "Tree already exists in planted trees");
		}

	}

	@Test
	public void requestTreeTrophy1Test() {
		User u1 = new User("", "", "", false);
		TreeBook tb = new TreeBook();
		TreeBookService tbs = new TreeBookService(tb);
		try {
			u1 = tbs.createUser("1234567890", "Tudor", "aB1cdefg");
		} catch (InvalidInputException e) {
			System.out.print(e.getMessage());
			fail();
		}
		try {
			tbs.requestTree(11, 12, u1);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			tbs.requestTree(11, 13, u1);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			tbs.requestTree(11, 14, u1);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			tbs.requestTree(11, 15, u1);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			tbs.requestTree(11, 16, u1);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			tbs.requestTree(11, 17, u1);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			tbs.requestTree(11, 18, u1);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			tbs.requestTree(11, 19, u1);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			tbs.requestTree(11, 20, u1);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			tbs.requestTree(11, 21, u1);
		} catch (InvalidInputException e) {
			fail();
		}
		boolean check = false;
		for (int i = 0; i < u1.getTrophies().size(); i++) {
			if (u1.getTrophy(i).getName().contentEquals("TreesRequestedLv1"))
				check = true;
		}
		if (!check)
			fail();
	}

	@Test
	public void requestTreeTrophy2Test() {
		User u1 = new User("", "", "", false);
		TreeBook tb = new TreeBook();
		TreeBookService tbs = new TreeBookService(tb);
		try {
			u1 = tbs.createUser("1234567890", "Tudor", "aB1cdefg");
		} catch (InvalidInputException e) {
			System.out.print(e.getMessage());
			fail();
		}
		for (int i = 0; i < 25; i++) {
			try {
				tbs.requestTree(11, 12 + i, u1);
			} catch (InvalidInputException e) {
				fail();
			}
		}
		boolean check = false;
		for (int i = 0; i < u1.getTrophies().size(); i++) {
			if (u1.getTrophy(i).getName().contentEquals("TreesRequestedLv2"))
				check = true;
		}

		if (!check)
			fail();
	}

	@Test
	public void requestTreeTrophy3Test() {
		User u1 = new User("", "", "", false);
		TreeBook tb = new TreeBook();
		TreeBookService tbs = new TreeBookService(tb);
		try {
			u1 = tbs.createUser("1234567890", "Tudor", "aB1cdefg");
		} catch (InvalidInputException e) {
			System.out.print(e.getMessage());
			fail();
		}
		for (int i = 0; i < 50; i++) {
			try {
				tbs.requestTree(11, 12 + i, u1);
			} catch (InvalidInputException e) {
				fail();
			}
		}
		boolean check = false;
		for (int i = 0; i < u1.getTrophies().size(); i++) {
			if (u1.getTrophy(i).getName().contentEquals("TreesRequestedLv2"))
				check = true;
		}

		if (!check)
			fail();
	}

	@Test
	public void testRequestTree() {
		User u1 = new User("12345", "Tudor", "abcdefg", true);
		TreeBook tb = new TreeBook();
		TreeBookService tbs = new TreeBookService(tb);
		try {
			tbs.requestTree(11, 11, u1);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			tbs.requestTree(11, 12, u1);
		} catch (InvalidInputException e) {
			fail();
		}

	}

	@Test
	public void testConfirmTree() {
		User u1 = new User("12345", "Tudor", "abcdefg", true);
		TreeBook tb = new TreeBook();
		TreeBookService tbs = new TreeBookService(tb);
		Location l1 = new Location(11, 11);
		SustainabilityAttributes th1 = new SustainabilityAttributes(12, 25);

		Calendar setPlant = Calendar.getInstance();
		setPlant.set(2007, Calendar.JANUARY, 12, 1, 27, 0);
		Date treePlanted = new Date(setPlant.getTimeInMillis());

		Calendar setCutDown = Calendar.getInstance();
		setCutDown.set(2018, Calendar.DECEMBER, 10, 1, 15, 0);
		Date cutDown = new Date(setCutDown.getTimeInMillis());

		Calendar setLastSurvey = Calendar.getInstance();
		setLastSurvey.set(2018, Calendar.DECEMBER, 10, 10, 15, 0);
		Date lastSurvey = new Date(setLastSurvey.getTimeInMillis());
		try {
			tbs.requestTree(11, 11, u1);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			tbs.requestTree(11, 12, u1);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			tbs.confirmTree("Oak", false, Status.Planted, 72, 12, "Ahunsic", 1, KindOfLandUse.Residential, treePlanted,
					1, cutDown, lastSurvey, l1, u1, th1);
		} catch (InvalidInputException e) {
			assertEquals(e.getMessage(), "Tree must exist in requested trees! ");
		}

	}

	@Test
	public void testConfirmTreeNotInReqTrees() {
		User u1 = new User("12345", "Tudor", "abcdefg", true);
		TreeBook tb = new TreeBook();
		TreeBookService tbs = new TreeBookService(tb);
		Location l1 = new Location(12, 27);
		SustainabilityAttributes th1 = new SustainabilityAttributes(12, 25);

		Calendar setPlant = Calendar.getInstance();
		setPlant.set(2007, Calendar.JANUARY, 12, 1, 27, 0);
		Date treePlanted = new Date(setPlant.getTimeInMillis());

		Calendar setCutDown = Calendar.getInstance();
		setCutDown.set(2018, Calendar.DECEMBER, 10, 1, 15, 0);
		Date cutDown = new Date(setCutDown.getTimeInMillis());

		Calendar setLastSurvey = Calendar.getInstance();
		setLastSurvey.set(2018, Calendar.DECEMBER, 10, 10, 15, 0);
		Date lastSurvey = new Date(setLastSurvey.getTimeInMillis());
		try {
			tbs.requestTree(11, 11, u1);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			tbs.requestTree(11, 12, u1);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			tbs.confirmTree("Oak", false, Status.Planted, 72, 12, "Ahunsic", 1, KindOfLandUse.Residential, treePlanted,
					1, cutDown, lastSurvey, l1, u1, th1);
		} catch (InvalidInputException e) {
			assertEquals(e.getMessage(), "Tree must exist in requested trees! ");
		}

	}

	@Test
	public void testConfirmTreeNotExpert() {
		User u1 = new User("12345", "Tudor", "abcdefg", false);
		TreeBook tb = new TreeBook();
		TreeBookService tbs = new TreeBookService(tb);
		Location l1 = new Location(11, 11);
		SustainabilityAttributes th1 = new SustainabilityAttributes(12, 25);

		Calendar setPlant = Calendar.getInstance();
		setPlant.set(2007, Calendar.JANUARY, 12, 1, 27, 0);
		Date treePlanted = new Date(setPlant.getTimeInMillis());

		Calendar setCutDown = Calendar.getInstance();
		setCutDown.set(2018, Calendar.DECEMBER, 10, 1, 15, 0);
		Date cutDown = new Date(setCutDown.getTimeInMillis());

		Calendar setLastSurvey = Calendar.getInstance();
		setLastSurvey.set(2018, Calendar.DECEMBER, 10, 10, 15, 0);
		Date lastSurvey = new Date(setLastSurvey.getTimeInMillis());
		try {
			tbs.requestTree(11, 11, u1);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			tbs.requestTree(11, 12, u1);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			tbs.confirmTree("Oak", false, Status.Planted, 72, 12, "Ahunsic", 1, KindOfLandUse.Residential, treePlanted,
					1, cutDown, lastSurvey, l1, u1, th1);
		} catch (InvalidInputException e) {
			assertEquals(e.getMessage(), "User must be expert to confirm a tree!");
		}

	}

	@Test
	public void testConfirmTreeNullSpecies() {
		User u1 = new User("12345", "Tudor", "abcdefg", true);
		TreeBook tb = new TreeBook();
		TreeBookService tbs = new TreeBookService(tb);
		Location l1 = new Location(11, 11);
		SustainabilityAttributes th1 = new SustainabilityAttributes(12, 25);

		Calendar setPlant = Calendar.getInstance();
		setPlant.set(2007, Calendar.JANUARY, 12, 1, 27, 0);
		Date treePlanted = new Date(setPlant.getTimeInMillis());

		Calendar setCutDown = Calendar.getInstance();
		setCutDown.set(2018, Calendar.DECEMBER, 10, 1, 15, 0);
		Date cutDown = new Date(setCutDown.getTimeInMillis());

		Calendar setLastSurvey = Calendar.getInstance();
		setLastSurvey.set(2018, Calendar.DECEMBER, 10, 10, 15, 0);
		Date lastSurvey = new Date(setLastSurvey.getTimeInMillis());
		try {
			tbs.requestTree(11, 11, u1);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			tbs.requestTree(11, 12, u1);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			tbs.confirmTree(null, false, Status.Planted, 72, 12, "Ahunsic", 1, KindOfLandUse.Residential, treePlanted,
					1, cutDown, lastSurvey, l1, u1, th1);
		} catch (InvalidInputException e) {
			assertEquals(e.getMessage(), "The tree species must be defined.");
		}

	}

	@Test
	public void testConfirmTreeSpacesSpecies() {
		User u1 = new User("12345", "Tudor", "abcdefg", true);
		TreeBook tb = new TreeBook();
		TreeBookService tbs = new TreeBookService(tb);
		Location l1 = new Location(11, 11);
		SustainabilityAttributes th1 = new SustainabilityAttributes(12, 25);

		Calendar setPlant = Calendar.getInstance();
		setPlant.set(2007, Calendar.JANUARY, 12, 1, 27, 0);
		Date treePlanted = new Date(setPlant.getTimeInMillis());

		Calendar setCutDown = Calendar.getInstance();
		setCutDown.set(2018, Calendar.DECEMBER, 10, 1, 15, 0);
		Date cutDown = new Date(setCutDown.getTimeInMillis());

		Calendar setLastSurvey = Calendar.getInstance();
		setLastSurvey.set(2018, Calendar.DECEMBER, 10, 10, 15, 0);
		Date lastSurvey = new Date(setLastSurvey.getTimeInMillis());
		try {
			tbs.requestTree(11, 11, u1);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			tbs.requestTree(11, 12, u1);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			tbs.confirmTree("  ", false, Status.Planted, 72, 12, "Ahunsic", 1, KindOfLandUse.Residential, treePlanted,
					1, cutDown, lastSurvey, l1, u1, th1);
		} catch (InvalidInputException e) {
			assertEquals(e.getMessage(), "The tree species must be defined.");
		}

	}

	@Test
	public void testConfirmTreeNullMunicipality() {
		User u1 = new User("12345", "Tudor", "abcdefg", true);
		TreeBook tb = new TreeBook();
		TreeBookService tbs = new TreeBookService(tb);
		Location l1 = new Location(11, 11);
		SustainabilityAttributes th1 = new SustainabilityAttributes(12, 25);

		Calendar setPlant = Calendar.getInstance();
		setPlant.set(2007, Calendar.JANUARY, 12, 1, 27, 0);
		Date treePlanted = new Date(setPlant.getTimeInMillis());

		Calendar setCutDown = Calendar.getInstance();
		setCutDown.set(2018, Calendar.DECEMBER, 10, 1, 15, 0);
		Date cutDown = new Date(setCutDown.getTimeInMillis());

		Calendar setLastSurvey = Calendar.getInstance();
		setLastSurvey.set(2018, Calendar.DECEMBER, 10, 10, 15, 0);
		Date lastSurvey = new Date(setLastSurvey.getTimeInMillis());
		try {
			tbs.requestTree(11, 11, u1);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			tbs.requestTree(11, 12, u1);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			tbs.confirmTree("Oak", false, Status.Planted, 72, 12, null, 1, KindOfLandUse.Residential, treePlanted, 1,
					cutDown, lastSurvey, l1, u1, th1);
		} catch (InvalidInputException e) {
			assertEquals(e.getMessage(), "The municipality must be defined.");
		}

	}

	@Test
	public void testConfirmTreeSpacesMunicipality() {
		User u1 = new User("12345", "Tudor", "abcdefg", true);
		TreeBook tb = new TreeBook();
		TreeBookService tbs = new TreeBookService(tb);
		Location l1 = new Location(11, 11);
		SustainabilityAttributes th1 = new SustainabilityAttributes(12, 25);

		Calendar setPlant = Calendar.getInstance();
		setPlant.set(2007, Calendar.JANUARY, 12, 1, 27, 0);
		Date treePlanted = new Date(setPlant.getTimeInMillis());

		Calendar setCutDown = Calendar.getInstance();
		setCutDown.set(2018, Calendar.DECEMBER, 10, 1, 15, 0);
		Date cutDown = new Date(setCutDown.getTimeInMillis());

		Calendar setLastSurvey = Calendar.getInstance();
		setLastSurvey.set(2018, Calendar.DECEMBER, 10, 10, 15, 0);
		Date lastSurvey = new Date(setLastSurvey.getTimeInMillis());
		try {
			tbs.requestTree(11, 11, u1);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			tbs.requestTree(11, 12, u1);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			tbs.confirmTree("Oak", false, Status.Planted, 72, 12, "  ", 1, KindOfLandUse.Residential, treePlanted, 1,
					cutDown, lastSurvey, l1, u1, th1);
		} catch (InvalidInputException e) {
			assertEquals(e.getMessage(), "The municipality must be defined.");
		}

	}

	@Test
	public void testConfirmTreeNullMunicipalityAndSpecies() {
		User u1 = new User("12345", "Tudor", "abcdefg", true);
		TreeBook tb = new TreeBook();
		TreeBookService tbs = new TreeBookService(tb);
		Location l1 = new Location(11, 11);
		SustainabilityAttributes th1 = new SustainabilityAttributes(12, 25);

		Calendar setPlant = Calendar.getInstance();
		setPlant.set(2007, Calendar.JANUARY, 12, 1, 27, 0);
		Date treePlanted = new Date(setPlant.getTimeInMillis());

		Calendar setCutDown = Calendar.getInstance();
		setCutDown.set(2018, Calendar.DECEMBER, 10, 1, 15, 0);
		Date cutDown = new Date(setCutDown.getTimeInMillis());

		Calendar setLastSurvey = Calendar.getInstance();
		setLastSurvey.set(2018, Calendar.DECEMBER, 10, 10, 15, 0);
		Date lastSurvey = new Date(setLastSurvey.getTimeInMillis());
		try {
			tbs.requestTree(11, 11, u1);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			tbs.requestTree(11, 12, u1);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			tbs.confirmTree(null, false, Status.Planted, 72, 12, null, 1, KindOfLandUse.Residential, treePlanted, 1,
					cutDown, lastSurvey, l1, u1, th1);
		} catch (InvalidInputException e) {
			assertEquals(e.getMessage(), "The tree species must be defined.The municipality must be defined.");
		}

	}

	@Test
	public void testConfirmTreeSpacesMunicipalityAndSpecies() {
		User u1 = new User("12345", "Tudor", "abcdefg", true);
		TreeBook tb = new TreeBook();
		TreeBookService tbs = new TreeBookService(tb);
		Location l1 = new Location(11, 11);
		SustainabilityAttributes th1 = new SustainabilityAttributes(12, 25);

		Calendar setPlant = Calendar.getInstance();
		setPlant.set(2007, Calendar.JANUARY, 12, 1, 27, 0);
		Date treePlanted = new Date(setPlant.getTimeInMillis());

		Calendar setCutDown = Calendar.getInstance();
		setCutDown.set(2018, Calendar.DECEMBER, 10, 1, 15, 0);
		Date cutDown = new Date(setCutDown.getTimeInMillis());

		Calendar setLastSurvey = Calendar.getInstance();
		setLastSurvey.set(2018, Calendar.DECEMBER, 10, 10, 15, 0);
		Date lastSurvey = new Date(setLastSurvey.getTimeInMillis());
		try {
			tbs.requestTree(11, 11, u1);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			tbs.requestTree(11, 12, u1);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			tbs.confirmTree(" ", false, Status.Planted, 72, 12, " ", 1, KindOfLandUse.Residential, treePlanted, 1,
					cutDown, lastSurvey, l1, u1, th1);
		} catch (InvalidInputException e) {
			assertEquals(e.getMessage(), "The tree species must be defined.The municipality must be defined.");
		}

	}

	@Test
	public void testConfirmTreeNullStatus() {
		User u1 = new User("12345", "Tudor", "abcdefg", true);
		TreeBook tb = new TreeBook();
		TreeBookService tbs = new TreeBookService(tb);
		Location l1 = new Location(11, 11);
		SustainabilityAttributes th1 = new SustainabilityAttributes(12, 25);

		Calendar setPlant = Calendar.getInstance();
		setPlant.set(2007, Calendar.JANUARY, 12, 1, 27, 0);
		Date treePlanted = new Date(setPlant.getTimeInMillis());

		Calendar setCutDown = Calendar.getInstance();
		setCutDown.set(2018, Calendar.DECEMBER, 10, 1, 15, 0);
		Date cutDown = new Date(setCutDown.getTimeInMillis());

		Calendar setLastSurvey = Calendar.getInstance();
		setLastSurvey.set(2018, Calendar.DECEMBER, 10, 10, 15, 0);
		Date lastSurvey = new Date(setLastSurvey.getTimeInMillis());
		try {
			tbs.requestTree(11, 11, u1);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			tbs.requestTree(11, 12, u1);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			tbs.confirmTree("Oak", false, null, 72, 12, "Ahunsic", 1, KindOfLandUse.Residential, treePlanted, 1,
					cutDown, lastSurvey, l1, u1, th1);
		} catch (InvalidInputException e) {
			assertEquals(e.getMessage(), "Please select the tree status.");
		}

	}

	@Test
	public void testConfirmTreeNullLandUse() {
		User u1 = new User("12345", "Tudor", "abcdefg", true);
		TreeBook tb = new TreeBook();
		TreeBookService tbs = new TreeBookService(tb);
		Location l1 = new Location(11, 11);
		SustainabilityAttributes th1 = new SustainabilityAttributes(12, 25);

		Calendar setPlant = Calendar.getInstance();
		setPlant.set(2007, Calendar.JANUARY, 12, 1, 27, 0);
		Date treePlanted = new Date(setPlant.getTimeInMillis());

		Calendar setCutDown = Calendar.getInstance();
		setCutDown.set(2018, Calendar.DECEMBER, 10, 1, 15, 0);
		Date cutDown = new Date(setCutDown.getTimeInMillis());

		Calendar setLastSurvey = Calendar.getInstance();
		setLastSurvey.set(2018, Calendar.DECEMBER, 10, 10, 15, 0);
		Date lastSurvey = new Date(setLastSurvey.getTimeInMillis());
		try {
			tbs.requestTree(11, 11, u1);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			tbs.requestTree(11, 12, u1);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			tbs.confirmTree("Oak", false, Status.Planted, 72, 12, "Ahunsic", 1, null, null, 1, cutDown, lastSurvey, l1,
					u1, th1);
		} catch (InvalidInputException e) {
			assertEquals(e.getMessage(), "Please select the land being used.");
		}

	}

	@Test
	public void testConfirmTreeNullLandUseAndStatus() {
		User u1 = new User("12345", "Tudor", "abcdefg", true);
		TreeBook tb = new TreeBook();
		TreeBookService tbs = new TreeBookService(tb);
		Location l1 = new Location(11, 11);
		SustainabilityAttributes th1 = new SustainabilityAttributes(12, 25);

		Calendar setPlant = Calendar.getInstance();
		setPlant.set(2007, Calendar.JANUARY, 12, 1, 27, 0);
		Date treePlanted = new Date(setPlant.getTimeInMillis());

		Calendar setCutDown = Calendar.getInstance();
		setCutDown.set(2018, Calendar.DECEMBER, 10, 1, 15, 0);
		Date cutDown = new Date(setCutDown.getTimeInMillis());

		Calendar setLastSurvey = Calendar.getInstance();
		setLastSurvey.set(2018, Calendar.DECEMBER, 10, 10, 15, 0);
		Date lastSurvey = new Date(setLastSurvey.getTimeInMillis());
		try {
			tbs.requestTree(11, 11, u1);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			tbs.requestTree(11, 12, u1);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			tbs.confirmTree("Oak", false, null, 72, 12, "Ahunsic", 1, null, null, 1, cutDown, lastSurvey, l1, u1, th1);
		} catch (InvalidInputException e) {
			assertEquals(e.getMessage(), "Please select the tree status.Please select the land being used.");
		}

	}

	@Test
	public void testConfirmTreeNullLandUseAndStatusSpeciesAndMunicipality() {
		User u1 = new User("12345", "Tudor", "abcdefg", true);
		TreeBook tb = new TreeBook();
		TreeBookService tbs = new TreeBookService(tb);
		Location l1 = new Location(11, 11);
		SustainabilityAttributes th1 = new SustainabilityAttributes(12, 25);

		Calendar setPlant = Calendar.getInstance();
		setPlant.set(2007, Calendar.JANUARY, 12, 1, 27, 0);
		Date treePlanted = new Date(setPlant.getTimeInMillis());

		Calendar setCutDown = Calendar.getInstance();
		setCutDown.set(2018, Calendar.DECEMBER, 10, 1, 15, 0);
		Date cutDown = new Date(setCutDown.getTimeInMillis());

		Calendar setLastSurvey = Calendar.getInstance();
		setLastSurvey.set(2018, Calendar.DECEMBER, 10, 10, 15, 0);
		Date lastSurvey = new Date(setLastSurvey.getTimeInMillis());
		try {
			tbs.requestTree(11, 11, u1);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			tbs.requestTree(11, 12, u1);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			tbs.confirmTree(null, false, null, 72, 12, null, 1, null, null, 1, cutDown, lastSurvey, l1, u1, th1);
		} catch (InvalidInputException e) {
			assertEquals(e.getMessage(),
					"The tree species must be defined.The municipality must be defined.Please select the tree status.Please select the land being used.");
		}

	}

	@Test
	public void testRejectTree() {
		User u1 = new User("12345", "Tudor", "abcdefg", true);
		TreeBook tb = new TreeBook();
		TreeBookService tbs = new TreeBookService(tb);
		try {
			tbs.requestTree(11, 11, u1);
		} catch (InvalidInputException e) {
			fail();
		}
		Location L = new Location(11, 11);
		try {
			tbs.rejectTree(L, u1);
		} catch (InvalidInputException e) {
			fail();
		}

	}

	@Test
	public void testRejectTreeDoesntExist() {
		User u1 = new User("12345", "Tudor", "abcdefg", true);
		TreeBook tb = new TreeBook();
		TreeBookService tbs = new TreeBookService(tb);
		try {
			tbs.requestTree(11, 11, u1);
		} catch (InvalidInputException e) {
			fail();
		}
		Location L = new Location(11, 12);
		try {
			tbs.rejectTree(L, u1);
		} catch (InvalidInputException e) {
			assertEquals(e.getMessage(), "Tree must exist in requested trees! ");
		}

	}

	@Test
	public void testRejectTreeNotExpert() {
		User u1 = new User("12345", "Tudor", "abcdefg", false);
		TreeBook tb = new TreeBook();
		TreeBookService tbs = new TreeBookService(tb);
		try {
			tbs.requestTree(11, 11, u1);
		} catch (InvalidInputException e) {
			fail();
		}
		Location L = new Location(11, 11);
		try {
			tbs.rejectTree(L, u1);
		} catch (InvalidInputException e) {
			assertEquals(e.getMessage(), "User must be expert to reject a tree!");
		}

	}

	@Test
	public void testGetRequestedTrees() {
		User u1 = new User("12345", "Tudor", "abcdefg", true);
		TreeBook tb = new TreeBook();
		TreeBookService tbs = new TreeBookService(tb);
		Location[] a = new Location[4];
		try {
			a[0] = tbs.requestTree(11, 11, u1);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			a[1] = tbs.requestTree(11, 12, u1);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			a[2] = tbs.requestTree(11, 13, u1);
		} catch (InvalidInputException e) {
			fail();
		}
		boolean check = true;
		for (int i = 0; i < 3; i++)
			if (tb.getRequestedTree(i) != a[i])
				check = false;
		if (!check)
			fail();

	}

	@Test
	public void testGetForecastTreeByID() {
		User u1 = new User("12345", "Tudor", "abcdefg", true);
		TreeBook tb = new TreeBook();
		TreeBookService tbs = new TreeBookService(tb);
		Location l1 = new Location(12, 27);
		SustainabilityAttributes th1 = new SustainabilityAttributes(12, 25);

		Calendar setPlant = Calendar.getInstance();
		setPlant.set(2007, Calendar.JANUARY, 12, 1, 27, 0);
		Date treePlanted = new Date(setPlant.getTimeInMillis());

		Calendar setCutDown = Calendar.getInstance();
		setCutDown.set(2018, Calendar.DECEMBER, 10, 10, 15, 0);
		Date cutDown = new Date(setCutDown.getTimeInMillis());

		Calendar setLastSurvey = Calendar.getInstance();
		setLastSurvey.set(2018, Calendar.DECEMBER, 10, 10, 15, 0);
		Date lastSurvey = new Date(setLastSurvey.getTimeInMillis());
		ForecastTree t = null;
		try {
			t = tbs.plantForecastTree("Oakabab", false, Status.Planted, 72, 12, "Ahunsic", 1, KindOfLandUse.Residential,
					treePlanted, 1, cutDown, lastSurvey, l1, u1, th1);
		} catch (InvalidInputException e) {
			fail();
		}
		if (!tbs.findForecastTreeByID(t.getId(), u1).equals(t))
			fail();
	}

	@Test
	public void testRemoveTree() {
		User u1 = new User("12345", "Tudor", "abcdefg", true);
		TreeBook tb = new TreeBook();
		TreeBookService tbs = new TreeBookService(tb);
		Location l1 = new Location(12, 27);
		SustainabilityAttributes th1 = new SustainabilityAttributes(12, 25);

		Calendar setPlant = Calendar.getInstance();
		setPlant.set(2007, Calendar.JANUARY, 12, 1, 27, 0);
		Date treePlanted = new Date(setPlant.getTimeInMillis());

		Calendar setCutDown = Calendar.getInstance();
		setCutDown.set(2018, Calendar.DECEMBER, 10, 10, 15, 0);
		Date cutDown = new Date(setCutDown.getTimeInMillis());

		Calendar setLastSurvey = Calendar.getInstance();
		setLastSurvey.set(2018, Calendar.DECEMBER, 10, 10, 15, 0);
		Date lastSurvey = new Date(setLastSurvey.getTimeInMillis());
		Tree t = null;
		try {
			t = tbs.plantTree("Oak", false, Status.Planted, 72, 12, "Ahunsic", 1, KindOfLandUse.Residential,
					treePlanted, 1, cutDown, lastSurvey, l1, u1, th1);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			tbs.removeTree(t, u1);
		} catch (InvalidInputException e) {
			fail();
		}
		assertEquals(tbs.getAllTrees().size(), 0);
	}

	@Test
	public void testRemoveTreeNotExpert() {
		User u1 = new User("12345", "Tudor", "abcdefg", false);
		TreeBook tb = new TreeBook();
		TreeBookService tbs = new TreeBookService(tb);
		Location l1 = new Location(12, 27);
		SustainabilityAttributes th1 = new SustainabilityAttributes(12, 25);

		Calendar setPlant = Calendar.getInstance();
		setPlant.set(2007, Calendar.JANUARY, 12, 1, 27, 0);
		Date treePlanted = new Date(setPlant.getTimeInMillis());

		Calendar setCutDown = Calendar.getInstance();
		setCutDown.set(2018, Calendar.DECEMBER, 10, 10, 15, 0);
		Date cutDown = new Date(setCutDown.getTimeInMillis());

		Calendar setLastSurvey = Calendar.getInstance();
		setLastSurvey.set(2018, Calendar.DECEMBER, 10, 10, 15, 0);
		Date lastSurvey = new Date(setLastSurvey.getTimeInMillis());
		Tree t = null;
		try {
			t = tbs.plantTree("Oak", false, Status.Planted, 72, 12, "Ahunsic", 1, KindOfLandUse.Residential,
					treePlanted, 1, cutDown, lastSurvey, l1, u1, th1);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			tbs.removeTree(t, u1);
		} catch (InvalidInputException e) {
			assertEquals(e.getMessage(), "User must be expert to remove a tree!");
			assertEquals(tbs.getAllTrees().size(), 1);
		}

	}

	@Test
	public void testRemoveTreeDoesntExist() {
		User u1 = new User("12345", "Tudor", "abcdefg", true);
		TreeBook tb = new TreeBook();
		TreeBookService tbs = new TreeBookService(tb);
		Location l1 = new Location(12, 27);
		SustainabilityAttributes th1 = new SustainabilityAttributes(12, 25);

		Calendar setPlant = Calendar.getInstance();
		setPlant.set(2007, Calendar.JANUARY, 12, 1, 27, 0);
		Date treePlanted = new Date(setPlant.getTimeInMillis());

		Calendar setCutDown = Calendar.getInstance();
		setCutDown.set(2018, Calendar.DECEMBER, 10, 10, 15, 0);
		Date cutDown = new Date(setCutDown.getTimeInMillis());

		Calendar setLastSurvey = Calendar.getInstance();
		setLastSurvey.set(2018, Calendar.DECEMBER, 10, 10, 15, 0);
		Date lastSurvey = new Date(setLastSurvey.getTimeInMillis());
		Tree t = null;
		try {
			t = tbs.plantTree("Oak", false, Status.Planted, 72, 12, "Ahunsic", 1, KindOfLandUse.Residential,
					treePlanted, 1, cutDown, lastSurvey, l1, u1, th1);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			tbs.removeTree(t, u1);
		} catch (InvalidInputException e) {
			fail();
		}
		assertEquals(tbs.getAllTrees().size(), 0);
		try {
			tbs.removeTree(t, u1);
		} catch (InvalidInputException e) {
			assertEquals(e.getMessage(), "Tree has to exist to be removed!");
			assertEquals(tbs.getAllTrees().size(), 0);
		}

	}

	@Test
	public void testRemoveAllTrees() {
		User u1 = new User("12345", "Tudor", "abcdefg", true);
		TreeBook tb = new TreeBook();
		TreeBookService tbs = new TreeBookService(tb);
		Location l1 = new Location(12, 27);
		SustainabilityAttributes th1 = new SustainabilityAttributes(12, 25);

		Calendar setPlant = Calendar.getInstance();
		setPlant.set(2007, Calendar.JANUARY, 12, 1, 27, 0);
		Date treePlanted = new Date(setPlant.getTimeInMillis());

		Calendar setCutDown = Calendar.getInstance();
		setCutDown.set(2018, Calendar.DECEMBER, 10, 10, 15, 0);
		Date cutDown = new Date(setCutDown.getTimeInMillis());

		Calendar setLastSurvey = Calendar.getInstance();
		setLastSurvey.set(2018, Calendar.DECEMBER, 10, 10, 15, 0);
		Date lastSurvey = new Date(setLastSurvey.getTimeInMillis());
		Tree t = null;
		try {
			t = tbs.plantTree("Oak", false, Status.Planted, 72, 12, "Ahunsic", 1, KindOfLandUse.Residential,
					treePlanted, 1, cutDown, lastSurvey, l1, u1, th1);
		} catch (InvalidInputException e) {
			fail();
		}
		Location l2 = new Location(12, 13);
		try {

			t = tbs.plantTree("Oak", false, Status.Planted, 72, 12, "Ahunsic", 1, KindOfLandUse.Residential,
					treePlanted, 1, cutDown, lastSurvey, l2, u1, th1);
		} catch (InvalidInputException e) {
			System.out.print(e.getMessage());
			fail();
		}
		tbs.removeAllTrees();

		assertEquals(tbs.getAllTrees().size(), 0);

	}
	@Test
	public void testFilterTrees() {
		User u1 = new User("12345", "Tudor", "abcdefg", true);
		TreeBook tb = new TreeBook();
		TreeBookService tbs = new TreeBookService(tb);
		Location l1 = new Location(12, 27);
		Location l2 = new Location(12, 28);
		Location l3 = new Location(12, 29);
		Location l4 = new Location(12, 22);
		Location l5 = new Location(12, 7);
		SustainabilityAttributes th1 = new SustainabilityAttributes(12, 25);

		Calendar setPlant = Calendar.getInstance();
		setPlant.set(2007, Calendar.JANUARY, 12, 1, 27, 0);
		Date treePlanted = new Date(setPlant.getTimeInMillis());

		Calendar setCutDown = Calendar.getInstance();
		setCutDown.set(2018, Calendar.DECEMBER, 10, 10, 15, 0);
		Date cutDown = new Date(setCutDown.getTimeInMillis());

		Calendar setLastSurvey = Calendar.getInstance();
		setLastSurvey.set(2018, Calendar.DECEMBER, 10, 10, 15, 0);
		Date lastSurvey = new Date(setLastSurvey.getTimeInMillis());
		Tree t = null;
		Tree t1 = null;
		Tree t2 = null;
		Tree t3 = null;
		Tree t4 = null;
		try {
			t = tbs.plantTree("Oak", false, Status.Planted, 10, 12, "Ahunsic", 1, KindOfLandUse.Residential,
					treePlanted, 1, cutDown, lastSurvey, l1, u1, th1);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			t1 = tbs.plantTree("Oak", false, Status.Planted, 15, 12, "Ahunsic", 1, KindOfLandUse.Residential,
					treePlanted, 1, cutDown, lastSurvey, l2, u1, th1);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			t2 = tbs.plantTree("Oak", false, Status.Planted, 5, 12, "Ahunsic", 1, KindOfLandUse.Residential,
					treePlanted, 1, cutDown, lastSurvey, l3, u1, th1);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			t3 = tbs.plantTree("Oak", false, Status.Planted, 72, 12, "NotAhunsic", 1, KindOfLandUse.Residential,
					treePlanted, 1, cutDown, lastSurvey, l4, u1, th1);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			t4 = tbs.plantTree("NotOak", false, Status.Planted, 72, 12, "Ahunsic", 1, KindOfLandUse.Residential,
					treePlanted, 1, cutDown, lastSurvey, l5, u1, th1);
		} catch (InvalidInputException e) {
			fail();
		}
		List<Tree> testFiltered = tbs.filterTrees("Oak", false, false, null, "Ahunsic", 7, 16, -1, -1, null);
		if(!(testFiltered.contains(t) && testFiltered.contains(t1) && !testFiltered.contains(t2) && !testFiltered.contains(t3)
				&& !testFiltered.contains(t4)))
			fail();
	}
	@Test
	public void testFilterTrees2() {
		User u1 = new User("12345", "Tudor", "abcdefg", true);
		TreeBook tb = new TreeBook();
		TreeBookService tbs = new TreeBookService(tb);
		Location l1 = new Location(12, 27);
		Location l2 = new Location(12, 28);
		Location l3 = new Location(12, 29);
		Location l4 = new Location(12, 22);
		Location l5 = new Location(12, 7);
		SustainabilityAttributes th1 = new SustainabilityAttributes(12, 25);

		Calendar setPlant = Calendar.getInstance();
		setPlant.set(2007, Calendar.JANUARY, 12, 1, 27, 0);
		Date treePlanted = new Date(setPlant.getTimeInMillis());

		Calendar setCutDown = Calendar.getInstance();
		setCutDown.set(2018, Calendar.DECEMBER, 10, 10, 15, 0);
		Date cutDown = new Date(setCutDown.getTimeInMillis());

		Calendar setLastSurvey = Calendar.getInstance();
		setLastSurvey.set(2018, Calendar.DECEMBER, 10, 10, 15, 0);
		Date lastSurvey = new Date(setLastSurvey.getTimeInMillis());
		Tree t = null;
		Tree t1 = null;
		Tree t2 = null;
		Tree t3 = null;
		Tree t4 = null;
		try {
			t = tbs.plantTree("Oak", false, Status.Planted, 10, 12, "Ahunsic", 1, KindOfLandUse.Residential,
					treePlanted, 1, cutDown, lastSurvey, l1, u1, th1);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			t1 = tbs.plantTree("Oak", false, Status.Planted, 15, 12, "Ahunsic", 1, KindOfLandUse.Residential,
					treePlanted, 1, cutDown, lastSurvey, l2, u1, th1);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			t2 = tbs.plantTree("Oak", false, Status.Planted, 10, 12, "Ahunsic", 1, KindOfLandUse.Municipal,
					treePlanted, 1, cutDown, lastSurvey, l3, u1, th1);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			t3 = tbs.plantTree("Oak", false, Status.Planted, 72, 12, "NotAhunsic", 1, KindOfLandUse.Residential,
					treePlanted, 1, cutDown, lastSurvey, l4, u1, th1);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			t4 = tbs.plantTree("NotOak", false, Status.Planted, 72, 12, "Ahunsic", 1, KindOfLandUse.Residential,
					treePlanted, 1, cutDown, lastSurvey, l5, u1, th1);
		} catch (InvalidInputException e) {
			fail();
		}
		List<Tree> testFiltered = tbs.filterTrees("Oak", false, false, null, "Ahunsic", 7, 16, -1, -1, KindOfLandUse.Residential);
		if(!(testFiltered.contains(t) && testFiltered.contains(t1) && !testFiltered.contains(t2) && !testFiltered.contains(t3)
				&& !testFiltered.contains(t4)))
			fail();
	}

	@Test
	public void testSortSpecies() {
		User u1 = new User("12345", "Tudor", "abcdefg", true);
		TreeBook tb = new TreeBook();
		TreeBookService tbs = new TreeBookService(tb);
		Location l1 = new Location(12, 27);
		Location l2 = new Location(12, 28);
		Location l3 = new Location(12, 29);
		Location l4 = new Location(12, 22);
		Location l5 = new Location(12, 7);
		SustainabilityAttributes th1 = new SustainabilityAttributes(12, 25);

		Calendar setPlant = Calendar.getInstance();
		setPlant.set(2007, Calendar.JANUARY, 12, 1, 27, 0);
		Date treePlanted = new Date(setPlant.getTimeInMillis());

		Calendar setCutDown = Calendar.getInstance();
		setCutDown.set(2018, Calendar.DECEMBER, 10, 10, 15, 0);
		Date cutDown = new Date(setCutDown.getTimeInMillis());

		Calendar setLastSurvey = Calendar.getInstance();
		setLastSurvey.set(2018, Calendar.DECEMBER, 10, 10, 15, 0);
		Date lastSurvey = new Date(setLastSurvey.getTimeInMillis());
		Tree t = null;
		Tree t1 = null;
		Tree t2 = null;
		Tree t3 = null;
		Tree t4 = null;
		try {
			t = tbs.plantTree("Sak", false, Status.Planted, 10, 12, "Ahunsic", 1, KindOfLandUse.Residential,
					treePlanted, 1, cutDown, lastSurvey, l1, u1, th1);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			t1 = tbs.plantTree("Pak", false, Status.Planted, 15, 12, "Ahunsic", 1, KindOfLandUse.Residential,
					treePlanted, 1, cutDown, lastSurvey, l2, u1, th1);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			t2 = tbs.plantTree("Rak", false, Status.Planted, 10, 12, "Ahunsic", 1, KindOfLandUse.Municipal,
					treePlanted, 1, cutDown, lastSurvey, l3, u1, th1);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			t3 = tbs.plantTree("Qak", false, Status.Planted, 72, 12, "NotAhunsic", 1, KindOfLandUse.Residential,
					treePlanted, 1, cutDown, lastSurvey, l4, u1, th1);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			t4 = tbs.plantTree("Oak", false, Status.Planted, 72, 12, "Ahunsic", 1, KindOfLandUse.Residential,
					treePlanted, 1, cutDown, lastSurvey, l5, u1, th1);
		} catch (InvalidInputException e) {
			fail();
		}
		List<Tree> testFiltered = tbs.sortBySpecies(false);
		if(!(testFiltered.get(0).equals(t4) && testFiltered.get(1).equals(t1) && testFiltered.get(2).equals(t3) && testFiltered.get(3).equals(t2) && testFiltered.get(4).equals(t)))
			fail();
	}

	@Test
	public void testSortMunicipality() {
		User u1 = new User("12345", "Tudor", "abcdefg", true);
		TreeBook tb = new TreeBook();
		TreeBookService tbs = new TreeBookService(tb);
		Location l1 = new Location(12, 27);
		Location l2 = new Location(12, 28);
		Location l3 = new Location(12, 29);
		Location l4 = new Location(12, 22);
		Location l5 = new Location(12, 7);
		SustainabilityAttributes th1 = new SustainabilityAttributes(12, 25);

		Calendar setPlant = Calendar.getInstance();
		setPlant.set(2007, Calendar.JANUARY, 12, 1, 27, 0);
		Date treePlanted = new Date(setPlant.getTimeInMillis());

		Calendar setCutDown = Calendar.getInstance();
		setCutDown.set(2018, Calendar.DECEMBER, 10, 10, 15, 0);
		Date cutDown = new Date(setCutDown.getTimeInMillis());

		Calendar setLastSurvey = Calendar.getInstance();
		setLastSurvey.set(2018, Calendar.DECEMBER, 10, 10, 15, 0);
		Date lastSurvey = new Date(setLastSurvey.getTimeInMillis());
		Tree t = null;
		Tree t1 = null;
		Tree t2 = null;
		Tree t3 = null;
		Tree t4 = null;
		try {
			t = tbs.plantTree("Sak", false, Status.Planted, 10, 12, "E", 1, KindOfLandUse.Residential,
					treePlanted, 1, cutDown, lastSurvey, l1, u1, th1);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			t1 = tbs.plantTree("Pak", false, Status.Planted, 15, 12, "B", 1, KindOfLandUse.Residential,
					treePlanted, 1, cutDown, lastSurvey, l2, u1, th1);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			t2 = tbs.plantTree("Rak", false, Status.Planted, 10, 12, "D", 1, KindOfLandUse.Municipal,
					treePlanted, 1, cutDown, lastSurvey, l3, u1, th1);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			t3 = tbs.plantTree("Qak", false, Status.Planted, 72, 12, "C", 1, KindOfLandUse.Residential,
					treePlanted, 1, cutDown, lastSurvey, l4, u1, th1);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			t4 = tbs.plantTree("Oak", false, Status.Planted, 72, 12, "A", 1, KindOfLandUse.Residential,
					treePlanted, 1, cutDown, lastSurvey, l5, u1, th1);
		} catch (InvalidInputException e) {
			fail();
		}
		List<Tree> testFiltered = tbs.sortByMunicipality(false);
		if(!(testFiltered.get(0).equals(t4) && testFiltered.get(1).equals(t1) && testFiltered.get(2).equals(t3) && testFiltered.get(3).equals(t2) && testFiltered.get(4).equals(t)))
			fail();
	}

	@Test
	public void testSortHeight() {
		User u1 = new User("12345", "Tudor", "abcdefg", true);
		TreeBook tb = new TreeBook();
		TreeBookService tbs = new TreeBookService(tb);
		Location l1 = new Location(12, 27);
		Location l2 = new Location(12, 28);
		Location l3 = new Location(12, 29);
		Location l4 = new Location(12, 22);
		Location l5 = new Location(12, 7);
		SustainabilityAttributes th1 = new SustainabilityAttributes(12, 25);

		Calendar setPlant = Calendar.getInstance();
		setPlant.set(2007, Calendar.JANUARY, 12, 1, 27, 0);
		Date treePlanted = new Date(setPlant.getTimeInMillis());

		Calendar setCutDown = Calendar.getInstance();
		setCutDown.set(2018, Calendar.DECEMBER, 10, 10, 15, 0);
		Date cutDown = new Date(setCutDown.getTimeInMillis());

		Calendar setLastSurvey = Calendar.getInstance();
		setLastSurvey.set(2018, Calendar.DECEMBER, 10, 10, 15, 0);
		Date lastSurvey = new Date(setLastSurvey.getTimeInMillis());
		Tree t = null;
		Tree t1 = null;
		Tree t2 = null;
		Tree t3 = null;
		Tree t4 = null;
		try {
			t = tbs.plantTree("Sak", false, Status.Planted, 100, 12, "E", 1, KindOfLandUse.Residential,
					treePlanted, 1, cutDown, lastSurvey, l1, u1, th1);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			t1 = tbs.plantTree("Pak", false, Status.Planted, 2, 12, "B", 1, KindOfLandUse.Residential,
					treePlanted, 1, cutDown, lastSurvey, l2, u1, th1);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			t2 = tbs.plantTree("Rak", false, Status.Planted, 4, 12, "D", 1, KindOfLandUse.Municipal,
					treePlanted, 1, cutDown, lastSurvey, l3, u1, th1);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			t3 = tbs.plantTree("Qak", false, Status.Planted, 3, 12, "C", 1, KindOfLandUse.Residential,
					treePlanted, 1, cutDown, lastSurvey, l4, u1, th1);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			t4 = tbs.plantTree("Oak", false, Status.Planted, 1, 12, "A", 1, KindOfLandUse.Residential,
					treePlanted, 1, cutDown, lastSurvey, l5, u1, th1);
		} catch (InvalidInputException e) {
			fail();
		}
		List<Tree> testFiltered = tbs.sortByHeight(false);
		if(!(testFiltered.get(0).equals(t4) && testFiltered.get(1).equals(t1) && testFiltered.get(2).equals(t3) && testFiltered.get(3).equals(t2) && testFiltered.get(4).equals(t)))
			fail();
	}

	@Test
	public void testSortDiameterOfCanopy() {
		User u1 = new User("12345", "Tudor", "abcdefg", true);
		TreeBook tb = new TreeBook();
		TreeBookService tbs = new TreeBookService(tb);
		Location l1 = new Location(12, 27);
		Location l2 = new Location(12, 28);
		Location l3 = new Location(12, 29);
		Location l4 = new Location(12, 22);
		Location l5 = new Location(12, 7);
		SustainabilityAttributes th1 = new SustainabilityAttributes(12, 25);

		Calendar setPlant = Calendar.getInstance();
		setPlant.set(2007, Calendar.JANUARY, 12, 1, 27, 0);
		Date treePlanted = new Date(setPlant.getTimeInMillis());

		Calendar setCutDown = Calendar.getInstance();
		setCutDown.set(2018, Calendar.DECEMBER, 10, 10, 15, 0);
		Date cutDown = new Date(setCutDown.getTimeInMillis());

		Calendar setLastSurvey = Calendar.getInstance();
		setLastSurvey.set(2018, Calendar.DECEMBER, 10, 10, 15, 0);
		Date lastSurvey = new Date(setLastSurvey.getTimeInMillis());
		Tree t = null;
		Tree t1 = null;
		Tree t2 = null;
		Tree t3 = null;
		Tree t4 = null;
		try {
			t = tbs.plantTree("Sak", false, Status.Planted, 100, 12, "E", 1, KindOfLandUse.Residential,
					treePlanted, 1, cutDown, lastSurvey, l1, u1, th1);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			t1 = tbs.plantTree("Pak", false, Status.Planted, 2, 2, "B", 1, KindOfLandUse.Residential,
					treePlanted, 1, cutDown, lastSurvey, l2, u1, th1);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			t2 = tbs.plantTree("Rak", false, Status.Planted, 4, 4, "D", 1, KindOfLandUse.Municipal,
					treePlanted, 1, cutDown, lastSurvey, l3, u1, th1);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			t3 = tbs.plantTree("Qak", false, Status.Planted, 3, 3, "C", 1, KindOfLandUse.Residential,
					treePlanted, 1, cutDown, lastSurvey, l4, u1, th1);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			t4 = tbs.plantTree("Oak", false, Status.Planted, 1, 1, "A", 1, KindOfLandUse.Residential,
					treePlanted, 1, cutDown, lastSurvey, l5, u1, th1);
		} catch (InvalidInputException e) {
			fail();
		}
		List<Tree> testFiltered = tbs.sortByDiameterOfCanopy(false);
		if(!(testFiltered.get(0).equals(t4) && testFiltered.get(1).equals(t1) && testFiltered.get(2).equals(t3) && testFiltered.get(3).equals(t2) && testFiltered.get(4).equals(t)))
			fail();
	}

	@Test
	public void testSortLandUse() {
		User u1 = new User("12345", "Tudor", "abcdefg", true);
		TreeBook tb = new TreeBook();
		TreeBookService tbs = new TreeBookService(tb);
		Location l1 = new Location(12, 27);
		Location l2 = new Location(12, 28);
		Location l3 = new Location(12, 29);
		Location l4 = new Location(12, 22);
		Location l5 = new Location(12, 7);
		SustainabilityAttributes th1 = new SustainabilityAttributes(12, 25);

		Calendar setPlant = Calendar.getInstance();
		setPlant.set(2007, Calendar.JANUARY, 12, 1, 27, 0);
		Date treePlanted = new Date(setPlant.getTimeInMillis());

		Calendar setCutDown = Calendar.getInstance();
		setCutDown.set(2018, Calendar.DECEMBER, 10, 10, 15, 0);
		Date cutDown = new Date(setCutDown.getTimeInMillis());

		Calendar setLastSurvey = Calendar.getInstance();
		setLastSurvey.set(2018, Calendar.DECEMBER, 10, 10, 15, 0);
		Date lastSurvey = new Date(setLastSurvey.getTimeInMillis());
		Tree t = null;
		Tree t1 = null;
		Tree t2 = null;
		Tree t3 = null;
		Tree t4 = null;
		try {
			t = tbs.plantTree("Sak", true, Status.Planted, 100, 12, "E", 1, KindOfLandUse.Residential,
					treePlanted, 1, cutDown, lastSurvey, l1, u1, th1);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			t1 = tbs.plantTree("Pak", false, Status.CutDown, 2, 12, "B", 1, KindOfLandUse.Municipal,
					treePlanted, 1, cutDown, lastSurvey, l2, u1, th1);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			t2 = tbs.plantTree("Rak", true, Status.Planted, 4, 12, "D", 1, KindOfLandUse.Residential,
					treePlanted, 1, cutDown, lastSurvey, l3, u1, th1);
		} catch (InvalidInputException e) {
			fail();
		}

		List<Tree> testFiltered = tbs.sortByLandUse(false);
		if(!testFiltered.get(0).equals(t1))
			fail();
	}

	@Test
	public void testSortStatus() {
		User u1 = new User("12345", "Tudor", "abcdefg", true);
		TreeBook tb = new TreeBook();
		TreeBookService tbs = new TreeBookService(tb);
		Location l1 = new Location(12, 27);
		Location l2 = new Location(12, 28);
		Location l3 = new Location(12, 29);
		Location l4 = new Location(12, 22);
		Location l5 = new Location(12, 7);
		SustainabilityAttributes th1 = new SustainabilityAttributes(12, 25);

		Calendar setPlant = Calendar.getInstance();
		setPlant.set(2007, Calendar.JANUARY, 12, 1, 27, 0);
		Date treePlanted = new Date(setPlant.getTimeInMillis());

		Calendar setCutDown = Calendar.getInstance();
		setCutDown.set(2018, Calendar.DECEMBER, 10, 10, 15, 0);
		Date cutDown = new Date(setCutDown.getTimeInMillis());

		Calendar setLastSurvey = Calendar.getInstance();
		setLastSurvey.set(2018, Calendar.DECEMBER, 10, 10, 15, 0);
		Date lastSurvey = new Date(setLastSurvey.getTimeInMillis());
		Tree t = null;
		Tree t1 = null;
		Tree t2 = null;
		Tree t3 = null;
		Tree t4 = null;
		try {
			t = tbs.plantTree("Sak", true, Status.Planted, 100, 12, "E", 1, KindOfLandUse.Residential,
					treePlanted, 1, cutDown, lastSurvey, l1, u1, th1);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			t1 = tbs.plantTree("Pak", false, Status.CutDown, 2, 12, "B", 1, KindOfLandUse.Residential,
					treePlanted, 1, cutDown, lastSurvey, l2, u1, th1);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			t2 = tbs.plantTree("Rak", true, Status.Planted, 4, 12, "D", 1, KindOfLandUse.Municipal,
					treePlanted, 1, cutDown, lastSurvey, l3, u1, th1);
		} catch (InvalidInputException e) {
			fail();
		}

		List<Tree> testFiltered = tbs.sortByStatus(false);
		if(!testFiltered.get(0).equals(t1))
			fail();

	}

	@Test
	public void testSortToBeChopped() {
		User u1 = new User("12345", "Tudor", "abcdefg", true);
		TreeBook tb = new TreeBook();
		TreeBookService tbs = new TreeBookService(tb);
		Location l1 = new Location(12, 27);
		Location l2 = new Location(12, 28);
		Location l3 = new Location(12, 29);
		Location l4 = new Location(12, 22);
		Location l5 = new Location(12, 7);
		SustainabilityAttributes th1 = new SustainabilityAttributes(12, 25);

		Calendar setPlant = Calendar.getInstance();
		setPlant.set(2007, Calendar.JANUARY, 12, 1, 27, 0);
		Date treePlanted = new Date(setPlant.getTimeInMillis());

		Calendar setCutDown = Calendar.getInstance();
		setCutDown.set(2018, Calendar.DECEMBER, 10, 10, 15, 0);
		Date cutDown = new Date(setCutDown.getTimeInMillis());

		Calendar setLastSurvey = Calendar.getInstance();
		setLastSurvey.set(2018, Calendar.DECEMBER, 10, 10, 15, 0);
		Date lastSurvey = new Date(setLastSurvey.getTimeInMillis());
		Tree t = null;
		Tree t1 = null;
		Tree t2 = null;
		Tree t3 = null;
		Tree t4 = null;
		try {
			t = tbs.plantTree("Sak", true, Status.Planted, 100, 12, "E", 1, KindOfLandUse.Residential,
					treePlanted, 1, cutDown, lastSurvey, l1, u1, th1);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			t1 = tbs.plantTree("Pak", false, Status.Diseased, 2, 12, "B", 1, KindOfLandUse.Residential,
					treePlanted, 1, cutDown, lastSurvey, l2, u1, th1);
		} catch (InvalidInputException e) {
			fail();
		}
		try {
			t2 = tbs.plantTree("Rak", true, Status.Planted, 4, 12, "D", 1, KindOfLandUse.Municipal,
					treePlanted, 1, cutDown, lastSurvey, l3, u1, th1);
		} catch (InvalidInputException e) {
			fail();
		}

		List<Tree> testFiltered = tbs.sortByToBeChopped(false);
		if(!testFiltered.get(0).equals(t1))
			fail();
	}

	@Test
	public void testInitializeNotExpert() {
		User u1 = new User("12345", "Tudor", "abcdefg", false);
		TreeBook tb = new TreeBook();
		TreeBookService tbs = new TreeBookService(tb);

		try {
			tbs.initializeForecastTrees(u1);
		} catch (InvalidInputException e) {
			assertEquals(e.getMessage(),"Error: User must be expert for forecasts");
		}

	}

	@Test
	public void testEditTree() {
		User u1 = new User("12345", "Tudor", "abcdefg", true);
		TreeBook tb = new TreeBook();
		TreeBookService tbs = new TreeBookService(tb);
		Location l1 = new Location(11, 11);
		SustainabilityAttributes th1 = new SustainabilityAttributes(12, 25);

		Calendar setPlant = Calendar.getInstance();
		setPlant.set(2007, Calendar.JANUARY, 12, 1, 27, 0);
		Date treePlanted = new Date(setPlant.getTimeInMillis());

		Calendar setCutDown = Calendar.getInstance();
		setCutDown.set(2018, Calendar.DECEMBER, 10, 1, 15, 0);
		Date cutDown = new Date(setCutDown.getTimeInMillis());

		Calendar setLastSurvey = Calendar.getInstance();
		setLastSurvey.set(2018, Calendar.DECEMBER, 10, 10, 15, 0);
		Date lastSurvey = new Date(setLastSurvey.getTimeInMillis());
		Tree t = null;
		try {
			t = tbs.plantTree("Oak", false, Status.Planted, 72, 12, "Ahunsic", 1, KindOfLandUse.Residential, treePlanted, 1,
					cutDown, lastSurvey, l1, u1, th1);
		} catch (InvalidInputException e) {
			fail();

			try {
				tbs.editTree(t,"NotOak", false, Status.Planted, 72, 12, "Ahunsic", 1, KindOfLandUse.Residential, treePlanted, 1,
						cutDown, lastSurvey, l1, u1, th1);
			} catch (InvalidInputException e1) {
				fail();
			}
			assertEquals(t.getSpecies(),"NotOak");
		}
	}
	
	@Test
	public void testEditTreeNullSpecies() {
		User u1 = new User("12345", "Tudor", "abcdefg", true);
		TreeBook tb = new TreeBook();
		TreeBookService tbs = new TreeBookService(tb);
		Location l1 = new Location(11, 11);
		SustainabilityAttributes th1 = new SustainabilityAttributes(12, 25);

		Calendar setPlant = Calendar.getInstance();
		setPlant.set(2007, Calendar.JANUARY, 12, 1, 27, 0);
		Date treePlanted = new Date(setPlant.getTimeInMillis());

		Calendar setCutDown = Calendar.getInstance();
		setCutDown.set(2018, Calendar.DECEMBER, 10, 1, 15, 0);
		Date cutDown = new Date(setCutDown.getTimeInMillis());

		Calendar setLastSurvey = Calendar.getInstance();
		setLastSurvey.set(2018, Calendar.DECEMBER, 10, 10, 15, 0);
		Date lastSurvey = new Date(setLastSurvey.getTimeInMillis());
		Tree t = null;
		try {
			t = tbs.plantTree("Oak", false, Status.Planted, 72, 12, "Ahunsic", 1, KindOfLandUse.Residential, treePlanted, 1,
					cutDown, lastSurvey, l1, u1, th1);
		} catch (InvalidInputException e) {
			fail();

			try {
				tbs.editTree(t,null, false, Status.Planted, 72, 12, "Ahunsic", 1, KindOfLandUse.Residential, treePlanted, 1,
						cutDown, lastSurvey, l1, u1, th1);
			} catch (InvalidInputException e1) {
				assertEquals(e1.getMessage(),"The tree species must be defined.");
			}

		}
	}
	
	@Test
	public void testEditTreeSpacesSpecies() {
		User u1 = new User("12345", "Tudor", "abcdefg", true);
		TreeBook tb = new TreeBook();
		TreeBookService tbs = new TreeBookService(tb);
		Location l1 = new Location(11, 11);
		SustainabilityAttributes th1 = new SustainabilityAttributes(12, 25);

		Calendar setPlant = Calendar.getInstance();
		setPlant.set(2007, Calendar.JANUARY, 12, 1, 27, 0);
		Date treePlanted = new Date(setPlant.getTimeInMillis());

		Calendar setCutDown = Calendar.getInstance();
		setCutDown.set(2018, Calendar.DECEMBER, 10, 1, 15, 0);
		Date cutDown = new Date(setCutDown.getTimeInMillis());

		Calendar setLastSurvey = Calendar.getInstance();
		setLastSurvey.set(2018, Calendar.DECEMBER, 10, 10, 15, 0);
		Date lastSurvey = new Date(setLastSurvey.getTimeInMillis());
		Tree t = null;
		try {
			t = tbs.plantTree("Oak", false, Status.Planted, 72, 12, "Ahunsic", 1, KindOfLandUse.Residential, treePlanted, 1,
					cutDown, lastSurvey, l1, u1, th1);
		} catch (InvalidInputException e) {
			fail();

			try {
				tbs.editTree(t,"  ", false, Status.Planted, 72, 12, "Ahunsic", 1, KindOfLandUse.Residential, treePlanted, 1,
						cutDown, lastSurvey, l1, u1, th1);
			} catch (InvalidInputException e1) {
				assertEquals(e1.getMessage(),"The tree species must be defined.");
			}

		}
	}
	
	@Test
	public void testEditTreeNullMunicipality() {
		User u1 = new User("12345", "Tudor", "abcdefg", true);
		TreeBook tb = new TreeBook();
		TreeBookService tbs = new TreeBookService(tb);
		Location l1 = new Location(11, 11);
		SustainabilityAttributes th1 = new SustainabilityAttributes(12, 25);

		Calendar setPlant = Calendar.getInstance();
		setPlant.set(2007, Calendar.JANUARY, 12, 1, 27, 0);
		Date treePlanted = new Date(setPlant.getTimeInMillis());

		Calendar setCutDown = Calendar.getInstance();
		setCutDown.set(2018, Calendar.DECEMBER, 10, 1, 15, 0);
		Date cutDown = new Date(setCutDown.getTimeInMillis());

		Calendar setLastSurvey = Calendar.getInstance();
		setLastSurvey.set(2018, Calendar.DECEMBER, 10, 10, 15, 0);
		Date lastSurvey = new Date(setLastSurvey.getTimeInMillis());
		Tree t = null;
		try {
			t = tbs.plantTree("Oak", false, Status.Planted, 72, 12, "Ahunsic", 1, KindOfLandUse.Residential, treePlanted, 1,
					cutDown, lastSurvey, l1, u1, th1);
		} catch (InvalidInputException e) {
			fail();

			try {
				tbs.editTree(t,"Oak", false, Status.Planted, 72, 12, null, 1, KindOfLandUse.Residential, treePlanted, 1,
						cutDown, lastSurvey, l1, u1, th1);
			} catch (InvalidInputException e1) {
				assertEquals(e1.getMessage(),"The municipality must be defined.");
			}

		}

	}
	@Test
	public void testEditTreeSpacesMunicipality() {
		User u1 = new User("12345", "Tudor", "abcdefg", true);
		TreeBook tb = new TreeBook();
		TreeBookService tbs = new TreeBookService(tb);
		Location l1 = new Location(11, 11);
		SustainabilityAttributes th1 = new SustainabilityAttributes(12, 25);

		Calendar setPlant = Calendar.getInstance();
		setPlant.set(2007, Calendar.JANUARY, 12, 1, 27, 0);
		Date treePlanted = new Date(setPlant.getTimeInMillis());

		Calendar setCutDown = Calendar.getInstance();
		setCutDown.set(2018, Calendar.DECEMBER, 10, 1, 15, 0);
		Date cutDown = new Date(setCutDown.getTimeInMillis());

		Calendar setLastSurvey = Calendar.getInstance();
		setLastSurvey.set(2018, Calendar.DECEMBER, 10, 10, 15, 0);
		Date lastSurvey = new Date(setLastSurvey.getTimeInMillis());
		Tree t = null;
		try {
			t = tbs.plantTree("Oak", false, Status.Planted, 72, 12, "Ahunsic", 1, KindOfLandUse.Residential, treePlanted, 1,
					cutDown, lastSurvey, l1, u1, th1);
		} catch (InvalidInputException e) {
			fail();

			try {
				tbs.editTree(t,"Oak", false, Status.Planted, 72, 12, "   ", 1, KindOfLandUse.Residential, treePlanted, 1,
						cutDown, lastSurvey, l1, u1, th1);
			} catch (InvalidInputException e1) {
				assertEquals(e1.getMessage(),"The municipality must be defined.");
			}

		}
	}
	@Test
	public void testEditTreeNullLandUse() {
		User u1 = new User("12345", "Tudor", "abcdefg", true);
		TreeBook tb = new TreeBook();
		TreeBookService tbs = new TreeBookService(tb);
		Location l1 = new Location(11, 11);
		SustainabilityAttributes th1 = new SustainabilityAttributes(12, 25);

		Calendar setPlant = Calendar.getInstance();
		setPlant.set(2007, Calendar.JANUARY, 12, 1, 27, 0);
		Date treePlanted = new Date(setPlant.getTimeInMillis());

		Calendar setCutDown = Calendar.getInstance();
		setCutDown.set(2018, Calendar.DECEMBER, 10, 1, 15, 0);
		Date cutDown = new Date(setCutDown.getTimeInMillis());

		Calendar setLastSurvey = Calendar.getInstance();
		setLastSurvey.set(2018, Calendar.DECEMBER, 10, 10, 15, 0);
		Date lastSurvey = new Date(setLastSurvey.getTimeInMillis());
		Tree t = null;
		try {
			t = tbs.plantTree("Oak", false, Status.Planted, 72, 12, "Ahunsic", 1, KindOfLandUse.Residential, treePlanted, 1,
					cutDown, lastSurvey, l1, u1, th1);
		} catch (InvalidInputException e) {
			fail();

			try {
				tbs.editTree(t,"Oak", false, Status.Planted, 72, 12, "Ahunsic", 1, null, treePlanted, 1,
						cutDown, lastSurvey, l1, u1, th1);
			} catch (InvalidInputException e1) {
				assertEquals(e1.getMessage(),"Please select the land being used.");
			}

		}

	}
	@Test
	public void testEditTreeNullStatus() {
		User u1 = new User("12345", "Tudor", "abcdefg", true);
		TreeBook tb = new TreeBook();
		TreeBookService tbs = new TreeBookService(tb);
		Location l1 = new Location(11, 11);
		SustainabilityAttributes th1 = new SustainabilityAttributes(12, 25);

		Calendar setPlant = Calendar.getInstance();
		setPlant.set(2007, Calendar.JANUARY, 12, 1, 27, 0);
		Date treePlanted = new Date(setPlant.getTimeInMillis());

		Calendar setCutDown = Calendar.getInstance();
		setCutDown.set(2018, Calendar.DECEMBER, 10, 1, 15, 0);
		Date cutDown = new Date(setCutDown.getTimeInMillis());

		Calendar setLastSurvey = Calendar.getInstance();
		setLastSurvey.set(2018, Calendar.DECEMBER, 10, 10, 15, 0);
		Date lastSurvey = new Date(setLastSurvey.getTimeInMillis());
		Tree t = null;
		try {
			t = tbs.plantTree("Oak", false, Status.Planted, 72, 12, "Ahunsic", 1, KindOfLandUse.Residential, treePlanted, 1,
					cutDown, lastSurvey, l1, u1, th1);
		} catch (InvalidInputException e) {
			fail();

			try {
				tbs.editTree(t,"Oak", false, null, 72, 12, "Ahunsic", 1, KindOfLandUse.Residential, treePlanted, 1,
						cutDown, lastSurvey, l1, u1, th1);
			} catch (InvalidInputException e1) {
				assertEquals(e1.getMessage(),"Please select the tree status.");
			}

		}
	}
	private void checkResultUser(String name, TreeBook tb2) {
		assertEquals(1, tb2.getUsers().size());
		assertEquals(name, tb2.getUser(0).getName());
		assertEquals(0, tb2.getTrees().size());
	}

	private void checkResultTree(String aSpecies, boolean aToBeChopped, Status aStatus, float aHeight,
			float aDiameterOfCanopy, String aMunicipality, int i, KindOfLandUse aLandUse, Date aWhenPlanted,
			int aReports, Date aWhenCutDown, Date aLastSurvey, Location aCoordinates, User aLastReportingPerson,
			SustainabilityAttributes aTreeSustainability, TreeBook tb2) {

		assertEquals(1, tb2.getTrees().size());
		assertEquals(aSpecies, tb2.getTree(0).getSpecies());
		assertEquals(aToBeChopped, tb2.getTree(0).getToBeChopped());
		assertEquals(aStatus, tb2.getTree(0).getStatus());
		assertEquals(aHeight, tb2.getTree(0).getHeight(), 0.01);
		assertEquals(aDiameterOfCanopy, tb2.getTree(0).getDiameterOfCanopy(), 0.01);
		assertEquals(i, tb2.getTree(0).getDiameterOfTrunk(), 0.1);
		assertEquals(aLandUse, tb2.getTree(0).getLandUse());
		assertEquals(aWhenPlanted.toString(), tb2.getTree(0).getWhenPlanted().toString());
		assertEquals(aReports, tb2.getTree(0).getReports());
		assertEquals(aWhenCutDown.toString(), tb2.getTree(0).getWhenCutDown().toString());
		assertEquals(aLastSurvey.toString(), tb2.getTree(0).getLastSurvey().toString());

		// assertEquals(aCoordinates.toString(),
		// tb2.getTree(0).getCoordinates().toString());
		// assertEquals(aLastReportingPerson.toString(),
		// tb2.getTree(0).getLastReportingPerson().toString());
		// assertEquals(aTreeSustainability.toString(),
		// tb2.getTree(0).getTreeSustainability().toString());
	}
}
