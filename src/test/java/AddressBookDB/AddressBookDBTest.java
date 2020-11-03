package AddressBookDB;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import AddressBookDB.AddressBookService.IOService;


public class AddressBookDBTest {
	public static AddressBookService test;
	public static List<Contact> testData;
	@Before
	public void setUp() {
		test = new AddressBookService();
	    testData = test.readContactData(IOService.DB_IO);
	}
	@Test
	public void givenAddressBookInDB_WhenRetrieved_ShouldMatchContactCount() {
		assertEquals(7, testData.size());
	}

}
