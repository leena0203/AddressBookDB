package AddressBookDB;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AddressBook {
	Scanner sc = new Scanner(System.in);
	public List<Contact> addressBook = new ArrayList<Contact>();

	public void setAddressBook(ArrayList<Contact> addressBook) {
		this.addressBook = addressBook;
	}

	public List<Contact> getAddressBook() {
		return addressBook;
	}

	public String city;

	public AddressBook() {
		this.city = city;
	}

	public void addContact(Contact contactObj) {
		boolean duplicate = addressBook.stream().anyMatch(n -> n.equals(contactObj));
		if(!duplicate) {
			addressBook.add(contactObj);
		}
		else {
			System.out.println("Already a added contact");
		}
	}

	public void editContact(String name) {
		Scanner sc = new Scanner(System.in);
		String x = "";
		for (Contact contactObj : addressBook) {
			x = contactObj.getFirstName() + contactObj.getLastName();
			if (name.equals(x)) {
				System.out.println("1.Change the address");
				System.out.println("2.Change the city");
				System.out.println("3.Change the state");
				System.out.println("4.Change the ZIP code");
				System.out.println("5.Change the phone number");
				System.out.println("6.Change the Email id");
				int choice = sc.nextInt();
				sc.nextLine();

				switch (choice) {
				case 1:
					System.out.println("Enter new address");
					String address = sc.nextLine();
					contactObj.setAddress(address);
					break;
				case 2:
					System.out.println("Enter new city");
					String city = sc.nextLine();
					contactObj.setCity(city);
					break;
				case 3:
					System.out.println("Enter new state");
					String state = sc.nextLine();
					contactObj.setAddress(state);
					break;
				case 4:
					System.out.println("Enter new ZIP code");
					int zip = sc.nextInt();
					contactObj.setZip(zip);
					sc.nextLine();
					break;
				case 5:
					System.out.println("Enter new phone number");
					long phone = sc.nextLong();
					sc.nextLine();
					contactObj.setPhoneNumber(phone);
					break;
				case 6:
					System.out.println("Enter new Email id");
					String email = sc.nextLine();
					contactObj.setEmailId(email);
					break;
				}
			}
		}

	}

	public void deleteContact(String name) {
		String x = "";
		for (Contact contactObj : addressBook) {
			x = contactObj.getFirstName() + contactObj.getLastName();
			if (name.equals(x)) {
				addressBook.remove(contactObj);
			}
		}
	}

	public void displayAllContacts() {

		for (Contact contactObj : addressBook) {
			System.out.println("First Name : " + contactObj.getFirstName() + "Last Name : " + contactObj.getLastName()
			+ " Address : " + contactObj.getAddress() + " City : " + contactObj.getCity() + " State : "
			+ contactObj.getState() + " ZIP : " + contactObj.getZip() + " Phone Number : "
			+ contactObj.getPhoneNumber() + " Email ID : " + contactObj.getEmailId() + "\n");
		}
	}
}
