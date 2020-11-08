package com.bridgelabz.addressBook;

import java.time.LocalDate;

public class Contact {
	public String firstName;
	public String lastName;
	public String address;
	public String city;
	public String state;
	public long zip;
	public long phoneNumber;
	public String emailId;
	int contactId;
	private String addbookName;
	private String type;
	private int addId;
	public String id;
	private static LocalDate date;
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public long getZip() {
		return zip;
	}
	public void setZip(long zip) {
		this.zip = zip;
	}
	public long getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public Contact(String firstName, String lastName, String address, String city, String state, long phoneNumber,
		       String email, long zip) {
	super();
	this.firstName = firstName;
	this.lastName = lastName;
	this.address = address;
	this.city = city;
	this.state = state;
	this.zip = zip;
	this.phoneNumber = phoneNumber;
	this.emailId = email;
}
	public Contact(int contId, String firstName, String lastName, String address, String city, String state, long phoneNumber,
			       String email, long zip) {
		this(firstName, lastName, address, city, state, phoneNumber, email, zip);
		this.contactId = contId;
		
	}
	public Contact(int contId, String firstName, String lastName, String address, String state, String city,
			 long phoneNumber, String email, long zip, LocalDate date) {
		this(contId, firstName, lastName, address, city, state, phoneNumber, email, zip);
		this.date = date;
	}
	public Contact(int contId, String firstName, String lastName, String address, String state, String city,
			long phoneNumber, String email, long zip,LocalDate date, String addbookName, String type) {
		this(contId, firstName, lastName, address, city, state, phoneNumber, email, zip, date);
		this.addbookName = addbookName;
		this.type = type;
		
	}
	
	public Contact(String firstName, String lastName, String address, String state, String city,
			 long phoneNumber, String email, long zip, LocalDate date, int addId) {
		this(firstName, lastName, address, city, state, phoneNumber, email, zip);
		this.date = date;
		this.addId = addId;
	}
		
	@Override
	public String toString() {
		String details = "First Name : " + firstName + "\nLast Name : " + lastName + "\nAddress : " + address + "\nCity : " + city 
							+ "\nState : " + state + "\nZIP : " + zip + "\nPhone Number : " + phoneNumber + "\nEmail ID : " + emailId + "\n";
		return details;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((addbookName == null) ? 0 : addbookName.hashCode());
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + contactId;
		result = prime * result + ((emailId == null) ? 0 : emailId.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + (int) (phoneNumber ^ (phoneNumber >>> 32));
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + (int) (zip ^ (zip >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Contact other = (Contact) obj;
		if (addbookName == null) {
			if (other.addbookName != null)
				return false;
		} else if (!addbookName.equals(other.addbookName))
			return false;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (contactId != other.contactId)
			return false;
		if (emailId == null) {
			if (other.emailId != null)
				return false;
		} else if (!emailId.equals(other.emailId))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (phoneNumber != other.phoneNumber)
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (zip != other.zip)
			return false;
		return true;
	}
	public int getContactId() {
		return contactId;
	}
	public void setContactId(int contactId) {
		this.contactId = contactId;
	}
	public String getAddbookName() {
		return addbookName;
	}
	public void setAddbookName(String addbookName) {
		this.addbookName = addbookName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public static LocalDate getDate() {
		return date;
	}
	public static void setDate(LocalDate date) {
		Contact.date = date;
	}
}

