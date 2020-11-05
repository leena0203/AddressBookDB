package com.leena.addressBook;

public class Contact {
	public String firstName;
	public String lastName;
	public String address;
	public String city;
	public String state;
	public long zip;
	public long phoneNumber;
	public String emailId;
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
	public Contact(String firstName, String lastName, String address, String city, String state, long zip,
			       long phoneNumber, String emailId) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.phoneNumber = phoneNumber;
		this.emailId = emailId;
		
	}
	public Contact(String firstName2, String lastName2, String address2, String city2, String state2, long parseLong,
			String email, long parseLong2) {
		// TODO Auto-generated constructor stub
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

