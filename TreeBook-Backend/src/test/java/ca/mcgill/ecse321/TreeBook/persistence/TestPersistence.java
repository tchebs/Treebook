package ca.mcgill.ecse321.TreeBook.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.sql.Date;
import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.mcgill.ecse321.TreeBook.model.Location;
import ca.mcgill.ecse321.TreeBook.model.Tree;
import ca.mcgill.ecse321.TreeBook.model.Tree.KindOfLandUse;
import ca.mcgill.ecse321.TreeBook.model.Tree.Status;
import ca.mcgill.ecse321.TreeBook.model.TreeBook;
import ca.mcgill.ecse321.TreeBook.model.SustainabilityAttributes;
import ca.mcgill.ecse321.TreeBook.model.User;

public class TestPersistence {

	private TreeBook tb;
	
	@Before
	public void setUp() throws Exception {
		
		tb = new TreeBook();

	    // create tree
		User u1 = new User("12", "Tudor", "Abc123",false);

		Location l1 = new Location(12,27);
		SustainabilityAttributes th1 = new SustainabilityAttributes ( 12, 25);

		Calendar setPlant = Calendar.getInstance();
		setPlant.set(2007,Calendar.JANUARY,12,1,27,0);
		Date treePlanted = new Date(setPlant.getTimeInMillis());

		Calendar setCutDown = Calendar.getInstance();
		setCutDown.set(2018,Calendar.DECEMBER,10,10,15,0);
		Date cutDown = new Date(setCutDown.getTimeInMillis());

		Calendar setLastSurvey = Calendar .getInstance();
		setLastSurvey.set(2018,Calendar.DECEMBER,10,10,15,0);
		Date lastSurvey = new Date(setLastSurvey.getTimeInMillis());

		Tree tree1 = new Tree("Oak", false, Status.Planted, 72,12, "Ahunsic", 5, KindOfLandUse.Residential, treePlanted, 1, cutDown, lastSurvey, l1, u1, th1);
		
	    // add tree to the tree book.
	   
	tb.addTree(tree1);
		tb.addUser(u1);
		}

	@After
	public void tearDown() throws Exception {
		tb.delete();
	}

	@Test
	public void test(){
		    // initialize model file
		    PersistenceXStream.initializeModelManager("output"+File.separator+"treepleworkspace"+File.separator+"data.xml");
		    // save model that is loaded during test setup
		    if (!PersistenceXStream.saveToXMLwithXStream(tb))
		        fail("Could not save file.");

		    // clear the model in memory
		    tb.delete();
		    assertEquals(0, tb.getTrees().size());
		    assertEquals(0, tb.getUsers().size());

		    // load model
		    tb = (TreeBook) PersistenceXStream.loadFromXMLwithXStream();
		    if (tb == null)
		        fail("Could not load file.");

		    // check Users
		    assertEquals(1, tb.getUsers().size());
		    assertEquals("Tudor", tb.getUser(0).getName());
		    
		    
		    // check event
		    assertEquals(1, tb.getTrees().size());
		    assertEquals("Oak", tb.getTree(0).getSpecies());
		    
		  
		}
	}

