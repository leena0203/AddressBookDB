package com.leena.addressBook;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.leena.addressBook.AddressBookService;
import com.leena.addressBook.Contact;
import com.leena.addressBook.AddressBookService.IOService;


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
	@Test
	public void givenUpdateInfo_WhenAddedInAddressBook_ShouldSyncWithDB() throws SQLException {
		test.updateContactAddress("Alex", "9430298574");
		boolean result = test.checkContactDataSync("Alex");
		assertTrue(result);
	}
	@Test
	public void givenContactInDB_WhenRetrievedForDateRange_ShouldMatchContactCount() {
		LocalDate start = LocalDate.of(2020, 01, 01);
	    LocalDate end = LocalDate.now();
	    testData = test.readContactForDateRange(start, end);
	    assertEquals(3, testData.size());
	}
	@Test
	public void givenAddressBookInDB_WhenRetrievedForCityAndState_ShouldMatchContactCount() {
		List<Contact> resultList = test.getContactForCityAndState("Cochin","Kerala");
		assertEquals(2, resultList.size());
	}
	@Test
	public void givenContactInDB_WhenAdded_ShouldBeAddedInSingleTransaction() throws SQLException {
		test.addContactInDatabase("Mill", "Zack", "Candolim", "Panji", "Goa",
                                               "9456321178", "mill@in.com", "4328879",LocalDate.of(2019, 07, 8), "Book1", "Professional");
		boolean result = test.checkContactDataSync("Mill");
		assertTrue(result);
	}
}
