package AddressBookDB;

import java.sql.Connection;
import java.sql.Date;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;


public class AddressBookServiceDB {
	private static AddressBookServiceDB addressBookDB;
	private static PreparedStatement contactStatement;
	public AddressBookServiceDB() {

	}
	public static AddressBookServiceDB getInstance() {
		if (addressBookDB == null) {
			addressBookDB = new AddressBookServiceDB();
		}
		return addressBookDB;
	}
	private Connection getConnection() throws SQLException{
		String jdbcURL = "jdbc:mysql://localhost:3306/address_book_service?useSSL=false";
		String userName = "root";
		String password = "Sql@2020sql";
		Connection connection;
		System.out.println("Connecting to database:"+jdbcURL);
		connection = DriverManager.getConnection(jdbcURL, userName, password);
		System.out.println("Connection is successful: " + connection);
		return connection;
	}
	public List<Contact> readData()  {
		String sql = "select Contact.ContactId, Contact.First_Name, Contact.Last_Name, Contact.Phone_No, Contact.Email, Contact.ZIP, Contact.CITY, Contact.STATE, Contact.Address, Address_Book.BookId, Address_Book.AddressBookName, Address_Book.Type from Contact inner join Address_Book on Contact.ContactId = Address_Book.ContactId;" ;
		return this.getContactData(sql);
	}
	List<Contact> getContactData(String sql)  {
		List<Contact> contactList = new ArrayList<>();
		try (Connection connection = this.getConnection()) {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			contactList = this.getContactData(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return contactList;			
	}

	private List<Contact> getContactData(ResultSet resultSet) {
		List<Contact> contactList = new ArrayList<>();
		try (Connection connection = this.getConnection()) {
			while (resultSet.next()) {
				String fname = resultSet.getString("First_Name");
				String lname = resultSet.getString("Last_Name");
				String address = resultSet.getString("Address");
				long zip = resultSet.getLong("ZIP");
				String city = resultSet.getString("CITY");
				String state = resultSet.getString("STATE");
				long phoneNumber = resultSet.getLong("Phone_No");
				String email = resultSet.getString("Email");
				contactList.add(new Contact(fname,lname,address,state,city,zip,phoneNumber,email));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return contactList;		
	}
	public int updateContactData(String firstName, String phoneNumber) throws SQLException {
		return this.updatePersonsDataUsingPreparedStatement(firstName, phoneNumber);
	}
	private int updatePersonsDataUsingPreparedStatement(String firstName, String phoneNumber) throws SQLException {
		String sql = "Update contact set Phone_No = ? where First_Name = ?";
		int result = 0;
		List<Contact> contactList = null;
		if (this.contactStatement == null) {
			Connection connection = this.getConnection();
			contactStatement = connection.prepareStatement(sql);
		}
		try {
			contactStatement.setLong(1, Long.parseLong(phoneNumber));
			contactStatement.setString(2,firstName);
			result = contactStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public List<Contact> getContactFromData(String firstName) {
		String sql = String.format("SELECT * FROM contact WHERE First_Name = '%s'", firstName);
		List<Contact> contactList = new ArrayList<>();
		try (Connection connection = this.getConnection()) {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			contactList = this.getContactData(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return contactList;	
	}
	public List<Contact> getContactForDateRange(LocalDate start, LocalDate end) {
		String sql = String.format(
				"select Contact.ContactId, Contact.First_Name, Contact.Last_Name, Contact.Phone_No, Contact.Email, Contact.ZIP, Contact.CITY, Contact.STATE, Contact.Address, Contact.date, Address_Book.BookId, Address_Book.AddressBookName, Address_Book.Type from Contact inner join Address_Book on Contact.ContactId = Address_Book.ContactId where date between '%s' and '%s'",
				Date.valueOf(start), Date.valueOf(end));
		return this.getContactData(sql);
	}
	public List<Contact> getContactForCityAndState(String city, String state) {
		String sql = String.format("select * from contact where CITY = 'Cochin' order by First_Name,Last_name;",city, state);
		return this.getContactData(sql);
	}

}
