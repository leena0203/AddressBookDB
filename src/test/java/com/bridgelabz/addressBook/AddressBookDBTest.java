package com.bridgelabz.addressBook;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.bridgelabz.addressBook.AddressBookService.IOService;


public class AddressBookDBTest {
	public static AddressBookService addressBookService;
	public static List<Contact> contactList;
	@Before
	public void setUp() {
		addressBookService = new AddressBookService();
	    contactList = addressBookService.readContactData(IOService.DB_IO);
	}
//	@Test
//	public void givenAddressBookInDB_WhenRetrieved_ShouldMatchContactCount() {
//		assertEquals(8, contactList.size());
//	}
//	@Test
//	public void givenUpdateInfo_WhenAddedInAddressBook_ShouldSyncWithDB() throws SQLException {
//		addressBookService.updateContactAddress("Mill", "1234567652");
//		boolean result = addressBookService.checkContactDataSync("Mill");
//		assertTrue(result);
//	}
//	@Test
//	public void givenContactInDB_WhenRetrievedForDateRange_ShouldMatchContactCount() {
//		LocalDate start = LocalDate.of(2019, 07, 01);
//	    LocalDate end = LocalDate.now();
//	    contactList = addressBookService.readContactForDateRange(start, end);
//	    assertEquals(8, contactList.size());
//	}
//	@Test
//	public void givenAddressBookInDB_WhenRetrievedForCityAndState_ShouldMatchContactCount() {
//		List<Contact> resultList = addressBookService.getContactForCity("Panji");
//		assertEquals(8, resultList.size());
//	}
//	@Test
//	public void givenContactInDB_WhenAdded_ShouldBeAddedInSingleTransaction() throws SQLException {
//		addressBookService.addContactInDatabase("Leena", "Sarode", "Kalamboli", "NaviMumbai", "Maharashtra",
//				1928736527 , "leena@in.com", 410218,LocalDate.of(2020, 04, 3), Arrays.asList("professional"));
//		contactList = addressBookService.readContactData(IOService.DB_IO);
//		boolean expected = addressBookService.checkContactDataSync("Leena");
//		assertTrue(expected);
//	}
	@Test
	public void givenMultipleEntries_WhenAddedUsingThreads_ShouldSyncDB() throws SQLException, DatabaseException {
		List<Contact> multipleContacts = Arrays.asList(new Contact(0, "Alex", "Zuster", "Andheri", "Mumbai", "Maharashtra",
				1145632786, "alex@in.co", 392092, LocalDate.of(2020, 03, 03),"", "friends"),
				new Contact(0, "Peter", "Albert", "MarcusStreet", "Cochin", "Kerala",
						1432789043, "peter@in.co", 265092, LocalDate.of(2020, 05, 9),"", "family"),
						new Contact(0, "Samuel", "Marcus", "PetaLane", "Cochin", "Kerala",
								1134567321, "samuel@in.co", 365092, LocalDate.of(2019, 11, 03),"", "professional"));
		
		contactList = addressBookService.readContactData(IOService.DB_IO);
		Instant threadstart = Instant.now();
		addressBookService.addMultipleContacts(multipleContacts);
		Instant threadend = Instant.now();
		System.out.println("Duration with thread: "+Duration.between(threadstart, threadend));
		boolean expected = addressBookService.checkMultipleContactDataSync(Arrays.asList("Alex","Peter","Samuel"));
		assertTrue(expected);
	}
	
}
