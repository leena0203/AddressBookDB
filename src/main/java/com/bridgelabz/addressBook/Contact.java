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
	private int contactId;
	private String addbookName;
	private String type;
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
	public Contact(int contId, String firstName, String lastName, String address, String city, String state, long phoneNumber,
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
	
	
	@Override
	public String toString() {
		String details = "First Name : " + firstName + "\nLast Name : " + lastName + "\nAddress : " + address + "\nCity : " + city 
							+ "\nState : " + state + "\nZIP : " + zip + "\nPhone Number : " + phoneNumber + "\nEmail ID : " + emailId + "\n";
		return details;
	}
	
	@Override
	public boolean equals(Object object){
	    boolean result = false;
	    if((object == null) || (getClass() != object.getClass())){
	        result = false;
	    }
	    else{
	        Contact contactObj = (Contact)object;
	        String name = this.firstName + this.lastName;
	        result = (name).equals(contactObj.firstName + contactObj.lastName);
	    }

	    return result;
	}
}

