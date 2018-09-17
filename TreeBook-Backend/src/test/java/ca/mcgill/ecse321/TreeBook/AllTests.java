package ca.mcgill.ecse321.TreeBook;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import ca.mcgill.ecse321.TreeBook.persistence.TestPersistence;
import ca.mcgill.ecse321.TreeBook.service.IntegrationTests;
import ca.mcgill.ecse321.TreeBook.service.TestTreeBookService;

@RunWith(Suite.class)
@SuiteClasses({ TestTreeBookService.class, TestPersistence.class, IntegrationTests.class })
public class AllTests {

}
