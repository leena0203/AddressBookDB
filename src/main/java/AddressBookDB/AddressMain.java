package AddressBookDB;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class AddressMain extends AddressBook {
	public static Map<String,AddressBook> addressBookMap;

	public AddressMain() {
		addressBookMap =new HashMap<>();
	}
	public void addAddressBook(String city) {
		AddressBook addaddressBook = new AddressBook();
		addressBookMap.put(city,addaddressBook);
	}
	public List<Contact> addressBook = new ArrayList<Contact>();

	public void searchContactByCity(String name, String city) {
		List<Contact> list = new ArrayList<Contact>();
		for(Map.Entry<String,AddressBook> entry : addressBookMap.entrySet()) {
			list = entry.getValue().getAddressBook().stream().filter(contactObj-> contactObj.getCity().equals(city))
					.filter(contactObj->(contactObj.getFirstName()+" "+contactObj.getLastName())
							.equals(name)).collect(Collectors.toList());
		}
		for(Contact contactObj : list) {
			System.out.println(contactObj);
		}
	}	
	public void searchConatctByState(String name, String state) {
		List<Contact> list = new ArrayList<Contact>();
		for(Map.Entry<String,AddressBook> entry : addressBookMap.entrySet()) {
			list = entry.getValue().getAddressBook().stream().filter(contactObj-> contactObj.getState().equals(state))
					.filter(contactObj->(contactObj.getFirstName()+" "+ contactObj.getLastName())
							.equals(name)).collect(Collectors.toList());
		}
		for(Contact contactObj : list) {
			System.out.println(contactObj);
		}
	}
	public void viewDataByCity(String city) {
		List<Contact> list = new ArrayList<Contact>();
		for(Map.Entry<String,AddressBook> entry : addressBookMap.entrySet()) {
			list = entry.getValue().getAddressBook().stream().filter(contactObj-> contactObj.getState().equals(city))
					.collect(Collectors.toList());
		}
		for(Contact contactObj : list) {
			System.out.println(contactObj);
		}
	}
	public void viewDataByState(String state) {
		List<Contact> list = new ArrayList<Contact>();
		for(Map.Entry<String,AddressBook> entry : addressBookMap.entrySet()) {
			list = entry.getValue().getAddressBook().stream().filter(contactObj-> contactObj.getState().equals(state))
					.collect(Collectors.toList());
		}
		for(Contact contactObj : list) {
			System.out.println(contactObj);
		}
	}
	public void countByCity(String city) {
		long count = 0;
		for(Map.Entry<String,AddressBook> entry : addressBookMap.entrySet()) {
			count = entry.getValue().getAddressBook().stream().filter(contactObj-> contactObj.getCity().equals(city))
					.count();
		}
		System.out.println("count is "+count);
	}
	public void countByState(String state) {
		long count = 0;
		for(Map.Entry<String,AddressBook> entry : addressBookMap.entrySet()) {
			count = entry.getValue().getAddressBook().stream().filter(contactObj-> contactObj.getState().equals(state))
					.count();
		}
		System.out.println("count is "+count);
	}
	public void sortByName() {
		for(Map.Entry<String,AddressBook> entry : addressBookMap.entrySet()) {
			Collections.sort(entry.getValue().getAddressBook(),new SortByName());
		}

	}
	public void sortByZip() {
		for(Map.Entry<String,AddressBook> entry : addressBookMap.entrySet()) {
			Collections.sort(entry.getValue().getAddressBook(),new SortByZip());
		}
	}
	public static void main(String[] args) {

		AddressMain addBook = new AddressMain();
		System.out.println("Welcome to Address Book :Press Enter key to proceed further");
		Scanner sc = new Scanner(System.in);
		int v;
		while(true) {
			System.out.println("1.Add a new Address Book");
			System.out.println("2.Add a new Contact");
			System.out.println("3.Edit the contact details");
			System.out.println("4.Delete the contact");
			System.out.println("5.View All Contacts");
			System.out.println("6.Search contact by City");
			System.out.println("7.Search contact by state");
			System.out.println("8.View contact by city");
			System.out.println("9.View contact by state");
			System.out.println("10.Count contact by city");
			System.out.println("11.Count contact by state");
			System.out.println("12.Sort contact by name");
			System.out.println("13.Sort By Zip");

			v = sc.nextInt();
			sc.nextLine();
			switch (v) {
			//Create new Address book
			case 1:
				System.out.println("Enter the City name to create new Address Book");
				String City = sc.nextLine();
				addBook.addAddressBook(City);
				break;
				//Add a contact
			case 2:
				System.out.println("Enter the details of person");
				System.out.println("Enter the first name");
				String firstName = sc.nextLine();
				System.out.println("Enter the last name");
				String lastName = sc.nextLine();
				System.out.println("Enter the address");
				String address = sc.nextLine();
				System.out.println("Enter the city name");
				String city = sc.nextLine();
				System.out.println("Enter the state name");
				String state = sc.nextLine();
				System.out.println("Enter the ZIP code");
				int zip = sc.nextInt();
				sc.nextLine();
				System.out.println("Enter the phone number");
				long phoneNumber = sc.nextLong();
				sc.nextLine();
				System.out.println("Enter the email");
				String email = sc.nextLine();
				Contact contactObj = new Contact(firstName, lastName, address, city, state, zip, phoneNumber, email);
				for (Map.Entry<String,AddressBook> entry : addressBookMap.entrySet()) {   
					if(entry.getKey().equalsIgnoreCase(city)) {
						entry.getValue().addContact(contactObj);
					}
					else {
						System.out.println("The addressbook does not exist, please create addressbook for that city");
					}
				}
				break;
				//Edit a contact
			case 3:
				System.out.println("Enter the contact name");
				String name = sc.nextLine();
				System.out.println("Enter the city name");
				String city1 = sc.nextLine();
				for (Map.Entry<String,AddressBook> entry : addressBookMap.entrySet()) {   
					if(entry.getKey().equalsIgnoreCase(city1)) {
						entry.getValue().editContact(name);
					}
				}
				break;
				//Delete a contact
			case 4:
				System.out.println("Enter the contact name");
				String contactName = sc.nextLine();
				System.out.println("Enter the city name");
				String city11 = sc.nextLine();
				for (Map.Entry<String,AddressBook> entry : addressBookMap.entrySet()) {   
					if(entry.getKey().equalsIgnoreCase(city11)) {
						entry.getValue().deleteContact(contactName);
					}
				}
				break;
				//View all contacts in specific address book
			case 5:
				for (Map.Entry<String,AddressBook> entry : addressBookMap.entrySet()) {   
					System.out.println("All contacts for city: "+entry.getKey()+" is: ");
					entry.getValue().displayAllContacts();
				}
			case 6:
				System.out.println("Enter conatct name to search ");
				String cName = sc.nextLine();
				System.out.println("Enter the city");
				String c = sc.nextLine();
				addBook.searchContactByCity(cName,c);
				break;
			case 7:
				System.out.println("Enter contact name to search");
				String person = sc.nextLine();
				System.out.println("Enter the state");
				String stat = sc.nextLine();
				addBook.searchContactByCity(person,stat);
				break;
			case 8:
				System.out.println("Enter the city");
				String s = sc.nextLine();
				addBook.viewDataByCity(s);
				break;
			case 9:
				System.out.println("Enter the state");
				String stats = sc.nextLine();
				addBook.viewDataByState(stats);
				break;
			case 10:
				System.out.println("Enter the city");
				String citi1 = sc.nextLine();
				addBook.countByCity(citi1);
				break;
			case 11:
				System.out.println("Enter the city");
				String stat1 = sc.nextLine();
				addBook.countByState(stat1);
				break;
			case 12:
				addBook.sortByName();
				break;
			case 13:
				addBook.sortByZip();
				break;

			default:
				break;
			}

			System.out.println("Thank You");

		}
	}

}
