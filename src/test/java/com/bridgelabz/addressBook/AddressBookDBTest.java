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
import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

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
		addressBookService.updateContactPhone("Mill", 1234567652, IOService.REST_IO);
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
		addressBookService.addContactInDatabase("Leena", "Sarode", "Kalamboli", "NaviMumbai", "Maharashtra", 1928736527,
				"leena@in.com", 410218, LocalDate.of(2020, 04, 3), Arrays.asList("professional"));
		contactList = addressBookService.readContactData(IOService.DB_IO);
		boolean expected = addressBookService.checkContactDataSync("Leena");
		assertTrue(expected);
	}

	@Test
	public void givenMultipleEntries_WhenAddedUsingThreads_ShouldSyncDB() throws SQLException, DatabaseException {
		List<Contact> multipleContacts = Arrays.asList(
				new Contact(0, "Alex", "Zuster", "Andheri", "Mumbai", "Maharashtra", 1145632786, "alex@in.co", 392092,
						LocalDate.of(2020, 03, 03), "", "friends"),
				new Contact(0, "Peter", "Albert", "MarcusStreet", "Cochin", "Kerala", 1432789043, "peter@in.co", 265092,
						LocalDate.of(2020, 05, 9), "", "family"),
				new Contact(0, "Samuel", "Marcus", "PetaLane", "Cochin", "Kerala", 1134567321, "samuel@in.co", 365092,
						LocalDate.of(2019, 11, 03), "", "professional"));

		contactList = addressBookService.readContactData(IOService.DB_IO);
		Instant threadstart = Instant.now();
		addressBookService.addMultipleContacts(multipleContacts);
		Instant threadend = Instant.now();
		System.out.println("Duration with thread: " + Duration.between(threadstart, threadend));
		boolean expected = addressBookService.checkMultipleContactDataSync(Arrays.asList("Alex", "Peter", "Samuel"));
		assertTrue(expected);
	}

	@Before
	public void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 3000;
	}

	private Contact[] getContactList() {
		Response response = RestAssured.get("/Contact");
		System.out.println("Contact entries in JSONServer:\n" + response.asString());
		Contact[] arrayOfContacts = new Gson().fromJson(response.asString(), Contact[].class);
		return arrayOfContacts;
	}

	@Test
	public void givenContactDataInJsonServer_whenRetrieved_ShouldMactCount() {
		Contact[] arrayOfContacts = getContactList();
		AddressBookService addressBookService = new AddressBookService(Arrays.asList(arrayOfContacts));
		long entries = addressBookService.countEntries(IOService.REST_IO);
		assertEquals(7, entries);
	}

	@Test
	public void givenMultipleNewContacts_whenAdded_shouldSyncMemory() {
		Contact[] arrayOfContacts = getContactList();
		addressBookService = new AddressBookService(Arrays.asList(arrayOfContacts));
		Contact[] arrayOfContactEntries = {
				new Contact("Alex", "Zuster", "Andheri", "Mumbai", "Maharashtra", 1145632786, "alex@in.co", 392092,
						LocalDate.of(2020, 03, 03), 2),
				new Contact("Peter", "Albert", "MarcusStreet", "Cochin", "Kerala", 1432789043, "peter@in.co", 265092,
						LocalDate.of(2020, 05, 9), 2),
				new Contact("Samuel", "Marcus", "PetaLane", "Cochin", "Kerala", 1134567321, "samuel@in.co", 365092,
						LocalDate.of(2019, 11, 03), 2) };
		List<Contact> contactList = Arrays.asList(arrayOfContactEntries);
		contactList.forEach(contact -> {
			Runnable task = () -> {
				Response response = addContactToJsonServer(contact);
				int statusCode = response.getStatusCode();
				assertEquals(201, statusCode);
				Contact newContact = new Gson().fromJson(response.asString(), Contact.class);
				addressBookService.addContactToAddressBook(newContact);
			};
			Thread thread = new Thread(task, contact.firstName);
			thread.start();
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		long count = addressBookService.countEntries(IOService.REST_IO);
		assertEquals(4, count);
	}

	private Response addContactToJsonServer(Contact contact) {
		String contactJson = new Gson().toJson(contact);
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		request.body(contactJson);
		return request.post("/Contact");
	}

	@Test
	public void givenNewPhoneNoForContact_whenUpdated_ShouldMatch200Response() throws SQLException {
		Contact[] arrayOfContacts = getContactList();
		addressBookService = new AddressBookService(Arrays.asList(arrayOfContacts));
		addressBookService.updateContactPhone("Alex", 1984039275, IOService.REST_IO);
		Contact contact = addressBookService.getContactData("Alex");
		String contactJson = new Gson().toJson(contact);
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		request.body(contactJson);
		Response response = request.put("/Contact/" + contact.id);
		int statusCode = response.getStatusCode();
		assertEquals(200, statusCode);
	}
}
