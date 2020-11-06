package com.bridgelabz.addressBook;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
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
	@Test
	public void givenAddressBookInDB_WhenRetrieved_ShouldMatchContactCount() {
		assertEquals(8, contactList.size());
	}
	@Test
	public void givenUpdateInfo_WhenAddedInAddressBook_ShouldSyncWithDB() throws SQLException {
		addressBookService.updateContactAddress("Mill", "1234567652");
		boolean result = addressBookService.checkContactDataSync("Mill");
		assertTrue(result);
	}
	@Test
	public void givenContactInDB_WhenRetrievedForDateRange_ShouldMatchContactCount() {
		LocalDate start = LocalDate.of(2019, 07, 01);
	    LocalDate end = LocalDate.now();
	    contactList = addressBookService.readContactForDateRange(start, end);
	    assertEquals(8, contactList.size());
	}
	@Test
	public void givenAddressBookInDB_WhenRetrievedForCityAndState_ShouldMatchContactCount() {
		List<Contact> resultList = addressBookService.getContactForCity("Panji");
		assertEquals(8, resultList.size());
	}
	@Test
	public void givenContactInDB_WhenAdded_ShouldBeAddedInSingleTransaction() throws SQLException {
		addressBookService.addContactInDatabase("Leena", "Sarode", "Kalamboli", "NaviMumbai", "Maharashtra",
				1928736527 , "leena@in.com", 410218,LocalDate.of(2020, 04, 3), Arrays.asList("professional"));
		contactList = addressBookService.readContactData(IOService.DB_IO);
		boolean expected = addressBookService.checkContactDataSync("Leena");
		assertTrue(expected);
	}
}
