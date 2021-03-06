package com.bridgelabz.addressBook;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonStreamParser;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class AddressBookService {

	public static String FILE = "AddressBook.txt";
	public static String CSVFILE = "AddressBook.csv";
	public static String GSONFILE = "AddressBook.gson";

	public enum IOService {
		CONSOLE_IO, FILE_IO, DB_IO, REST_IO
	};

	List<Contact> contactList = new ArrayList<>();
	private AddressBookServiceDB addressBookDB;

	public AddressBookService() {
		addressBookDB = AddressBookServiceDB.getInstance();
	}

	public AddressBookService(List<Contact> list) {
		this.contactList = new ArrayList<Contact>(list);
	}

	public void writeData(Map<String, AddressBook> addressBookMap) {
		StringBuffer employeeBuffer = new StringBuffer();
		for (Map.Entry<String, AddressBook> entry : addressBookMap.entrySet()) {
			entry.getValue().getAddressBook().forEach(contact -> {
				String empString = contact.toString().concat("\n");
				employeeBuffer.append(empString);
			});
		}
		try {
			Files.write(Paths.get(FILE), employeeBuffer.toString().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void readData() {
		try {
			Files.lines(new File(FILE).toPath()).forEach(System.out::println);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * UC14 For Writing the data to CSV File
	 * 
	 * @param cityBookMap
	 */
	public void writeDataToCSV(Map<String, AddressBook> addressBookMap) {
		Path path = Paths.get(CSVFILE);
		try {
			FileWriter outputfile = new FileWriter(path.toFile());
			CSVWriter writer = new CSVWriter(outputfile);
			List<String[]> data = new ArrayList<String[]>();
			String[] header = { "First Name", "Last Name", "Address", "City", "State", "ZIP", "Phone Number",
			"Email ID" };
			data.add(header);
			addressBookMap.values().stream().map(entry -> entry.getAddressBook())
			.forEach(entryt -> entryt.forEach(person -> {
				String[] personData = { person.getFirstName(), person.getLastName(), person.getAddress(),
						person.getCity(), person.getState(), Long.toString(person.getZip()),
						Long.toString(person.getPhoneNumber()), person.getEmailId() };
				data.add(personData);
			}));

			writer.writeAll(data);
			writer.close();
			System.out.println("Data entered successfully to addressbook.csv file.");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Reading data from the CSV file
	 */
	public void readDataFromCSV() {
		try {
			Reader fileReader = Files.newBufferedReader(Paths.get(CSVFILE));
			@SuppressWarnings("resource")
			CSVReader csvReader = new CSVReader(fileReader);
			String[] data;
			while ((data = csvReader.readNext()) != null) {
				for (String cell : data) {
					System.out.print(cell + "\t");
				}
				System.out.println();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeDataGSON(Map<String, AddressBook> addressBookMap) {
		try {
			Gson gson = new Gson();
			FileWriter writer = new FileWriter(GSONFILE);
			addressBookMap.values().stream().map(entry -> entry.getAddressBook())
			.forEach(listEntry -> listEntry.forEach(contact -> {
				String json = gson.toJson(contact);
				try {
					writer.write(json);
				} catch (IOException exception) {
					exception.printStackTrace();
				}
			}));
			writer.close();
			System.out.println("Data entered successfully to addressbook.json file.");
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}

	public void readDataGSON() {
		Gson gson = new Gson();
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(GSONFILE));
			JsonStreamParser parser = new JsonStreamParser(bufferedReader);
			while (parser.hasNext()) {
				JsonElement json = parser.next();
				if (json.isJsonObject()) {
					Contact person = gson.fromJson(json, Contact.class);
					System.out.println(person);
				}
			}
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}

	public List<Contact> readContactData(IOService ioService) {
		if (ioService.equals(IOService.DB_IO)) {
			this.contactList = addressBookDB.readData();
		}
		return this.contactList;
	}

	/**
	 * Update contact info
	 * 
	 * @param firstName
	 * @param address
	 * @throws SQLException
	 */
	public void updateContactPhone(String firstName, long phoneNumber, IOService ioService) throws SQLException {
		if (ioService.equals(IOService.DB_IO)) {
			int result = addressBookDB.updateContactData(firstName, phoneNumber);
			if (result == 0)
				return;
		}
		Contact contact = this.getContactData(firstName);
		if (contact != null)
			contact.phoneNumber = phoneNumber;
	}

	public Contact getContactData(String firstName) {
		return this.contactList.stream().filter(contactData -> contactData.firstName.equals(firstName)).findFirst()
				.orElse(null);
	}

	/**
	 * check contact to sync with DB
	 * 
	 * @param firstName
	 * @return
	 */
	public boolean checkContactDataSync(String firstName) {
		List<Contact> list = addressBookDB.getContactFromData(firstName);
		System.out.println(list);
		return list.get(0).equals(getContactData(firstName));

	}

	/**
	 * Find contact registered in specific period
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public List<Contact> readContactForDateRange(LocalDate start, LocalDate end) {
		return addressBookDB.getContactForDateRange(start, end);
	}

	/**
	 * Retrive contact based on city and state
	 * 
	 * @param city
	 * @param state
	 * @return
	 */
	public List<Contact> getContactForCity(String city) {
		return addressBookDB.getContactForCity(city);
	}

	/**
	 * Add new contact in database
	 * 
	 * @param firstName
	 * @param lastName
	 * @param address
	 * @param city
	 * @param state
	 * @param phoneNumber
	 * @param email
	 * @param zip
	 * @param date
	 * @param bookName
	 * @throws SQLException
	 */
	public void addContactInDatabase(String firstName, String lastName, String address, String city, String state,
			long phoneNumber, String email, long zip, LocalDate date, List<String> bookName) throws SQLException {
		addressBookDB.addNewContact(firstName, lastName, address, city, state, phoneNumber, email, zip, date, bookName);
	}

	/**
	 * adding multiple contacts using threads
	 * 
	 * @param multipleContacts
	 * @throws DatabaseException
	 */
	public void addMultipleContacts(List<Contact> multipleContacts) throws DatabaseException {
		Map<Integer, Boolean> contactAddedStatus = new HashedMap<>();
		multipleContacts.forEach(contact -> {
			Runnable task = () -> {
				contactAddedStatus.put(contact.hashCode(), false);
				System.out.println("Contact Added: " + Thread.currentThread().getName());
				try {
					addContactInDatabase(contact.getFirstName(), contact.getLastName(), contact.getAddress(),
							contact.getCity(), contact.getState(), contact.getPhoneNumber(), contact.getEmailId(),
							contact.getZip(), contact.getDate(), Arrays.asList(contact.getType()));
				} catch (SQLException e) {
					e.printStackTrace();
				}
				contactAddedStatus.put(contact.hashCode(), true);
				System.out.println("Contact Added: " + Thread.currentThread().getName());
			};
			Thread thread = new Thread(task, contact.firstName);
			thread.start();
		});
		while (contactAddedStatus.containsValue(false)) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * to check multiple contacts sync in DB
	 * 
	 * @param fnamelist
	 * @return
	 */
	public boolean checkMultipleContactDataSync(List<String> fnamelist) {
		List<Boolean> resultList = new ArrayList<>();
		fnamelist.forEach(firstName -> {
			resultList.add(checkContactDataSync(firstName));
		});
		if (resultList.contains(false)) {
			return false;
		}
		return true;
	}

	/**
	 * Countries entries in json file
	 * 
	 * @param ioService
	 * @return
	 */
	public long countEntries(IOService ioService) {
		return contactList.size();
	}

	/**
	 * Add multiple contacts in json file
	 * 
	 * @param newContact
	 */
	public void addContactToAddressBook(Contact newContact) {
		contactList.add(newContact);

	}

	/**
	 * deleteContactFromTable
	 * 
	 * @param firstName
	 * @param ioService
	 */
	public void deleteContact(String firstName, IOService ioService) {
		if (ioService.equals(IOService.REST_IO)) {
			Contact contact = this.getContactData(firstName);
			contactList.remove(contact);
		}
	}
}
