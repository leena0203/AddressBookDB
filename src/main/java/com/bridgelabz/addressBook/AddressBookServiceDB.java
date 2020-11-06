package com.bridgelabz.addressBook;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	private Connection getConnection() throws SQLException {
		String jdbcURL = "jdbc:mysql://localhost:3306/addressbook__service?useSSL=false";
		String userName = "root";
		String password = "Sql@2020sql";
		Connection connection;
		System.out.println("Connecting to database:" + jdbcURL);
		connection = DriverManager.getConnection(jdbcURL, userName, password);
		System.out.println("Connection is successful: " + connection);
		return connection;
	}

	/**
	 * Read contact from database
	 * 
	 * @return
	 */
	public List<Contact> readData() {
		String sql = "select c.first_Name, c.last_Name, c.address, c.city, c.state, c.phone_No,"
				+ " c.email, c.zip, c.date, b.bookname,b.type,b.book_Id, c.contact_Id from contacts"
				+ " c INNER JOIN addressbook_contact a on c.contact_Id = a.contact_Id "
				+ "INNER JOIN addressbook b on b.book_Id = a.book_Id;";
		return this.getContactData(sql);
	}

	/**
	 * get a contact
	 * 
	 * @param sql
	 * @return
	 */
	List<Contact> getContactData(String sql) {
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

	/**
	 * private method specifying fields of retrieving contact
	 * 
	 * @param resultSet
	 * @return
	 */
	private List<Contact> getContactData(ResultSet resultSet) {
		List<Contact> contactList = new ArrayList<>();
		try (Connection connection = this.getConnection()) {
			while (resultSet.next()) {
				String fname = resultSet.getString("first_Name");
				String lname = resultSet.getString("last_Name");
				String address = resultSet.getString("address");
				long zip = resultSet.getLong("zip");
				String city = resultSet.getString("city");
				String state = resultSet.getString("state");
				long phoneNumber = resultSet.getLong("phone_No");
				String email = resultSet.getString("email");
				int contactId = resultSet.getInt("contact_Id");
				LocalDate date = resultSet.getDate("date").toLocalDate();
				String addbookname = resultSet.getString("bookname");
				String type = resultSet.getString("type");
				contactList.add(new Contact(contactId, fname, lname, address, state, city, phoneNumber, email, zip,
						date, addbookname, type));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return contactList;
	}

	/**
	 * Update contact
	 * 
	 * @param firstName
	 * @param phoneNumber
	 * @return
	 * @throws SQLException
	 */
	public int updateContactData(String firstName, String phoneNumber) throws SQLException {
		return this.updatePersonsDataUsingPreparedStatement(firstName, phoneNumber);
	}

	/**
	 * Prepared statement to update contact
	 * 
	 * @param firstName
	 * @param phoneNumber
	 * @return
	 * @throws SQLException
	 */
	private int updatePersonsDataUsingPreparedStatement(String firstName, String phoneNumber) throws SQLException {
		String sql = "Update contacts set phone_No = ? where first_Name = ?";
		int result = 0;
		List<Contact> contactList = null;
		if (this.contactStatement == null) {
			Connection connection = this.getConnection();
			contactStatement = connection.prepareStatement(sql);
		}
		try {
			contactStatement.setLong(1, Long.parseLong(phoneNumber));
			contactStatement.setString(2, firstName);
			result = contactStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * get contact inputed specific date range
	 * 
	 * @param firstName
	 * @return
	 */
	public List<Contact> getContactFromData(String firstName) {
		String sql = String.format("select c.first_Name, c.last_Name, c.address, c.city, c.state, c.phone_No,"
				+ " c.email, c.zip, c.date, b.bookname,b.type,b.book_Id, c.contact_Id "
				+ "from contacts c INNER JOIN addressbook_contact a on c.contact_Id = a.contact_Id "
				+ "INNER JOIN addressbook b on b.book_Id = a.book_Id" + " WHERE first_Name = '%s';", firstName);
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

	/**
	 * get contacts added in specific range
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public List<Contact> getContactForDateRange(LocalDate start, LocalDate end) {
		String sql = String.format(
				"select c.first_Name, c.last_Name, c.address, c.city, c.state, c.phone_No, "
						+ "c.email, c.zip, c.date, b.bookname,b.type,b.book_Id, c.contact_Id "
						+ "from contacts c INNER JOIN addressbook_contact a on c.contact_Id = a.contact_Id "
						+ "INNER JOIN addressbook b on b.book_Id = a.book_Id" + " where date between '%s' and '%s'",
				Date.valueOf(start), Date.valueOf(end));
		return this.getContactData(sql);
	}

	/**
	 * Retrive contacts inputed in given city
	 * 
	 * @param city
	 * @param state
	 * @return
	 */
	public List<Contact> getContactForCity(String city) {
		String sql = String.format(
				"select c.first_Name, c.last_Name, c.address, c.city, c.state, c.phone_No,c.email, c.zip, c.date, b.bookname,b.type,b.book_Id, c.contact_Id from contacts c INNER JOIN addressbook_contact a on c.contact_Id = a.contact_Id INNER JOIN addressbook b on b.book_Id = a.book_Id WHERE city= '%s'",
				city);
		return this.getContactData(sql);
	}

	/**
	 * add a new contact in database
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
	 * @param bookNames
	 * @throws SQLException
	 */
	public void addNewContact(String firstName, String lastName, String address, String city, String state,
			long phoneNumber, String email, long zip, LocalDate date, List<String> bookNames) throws SQLException {

		try (Connection connection = getConnection()) {
			try {
				connection.setAutoCommit(false);
				// String sql = "INSERT INTO contacts (first_Name)VALUES (?)";
				String sql = "INSERT INTO contacts (first_Name,last_Name,address,city,state,phone_No,email,zip,"
						+ "date) VALUES	 (?,?,?,?,?,?,?,?,?);";
				PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

				preparedStatement.setString(1, firstName);
				preparedStatement.setString(2, lastName);
				preparedStatement.setString(3, address);
				preparedStatement.setString(4, city);
				preparedStatement.setString(5, state);
				preparedStatement.setInt(6, (int) phoneNumber);
				preparedStatement.setString(7, email);
				preparedStatement.setInt(8, (int) zip);
				preparedStatement.setDate(9, java.sql.Date.valueOf(date));
				int rowAffected = preparedStatement.executeUpdate();
				if (rowAffected == 1) {
					ResultSet resultSet = preparedStatement.getGeneratedKeys();
					int contactId = -1;
					resultSet.next();
					contactId = resultSet.getInt(1);
					for (String id : bookNames) {
						String getBookId = "select book_Id from addressbook where type = ?";
						PreparedStatement getBookIdPs = connection.prepareStatement(getBookId);
						getBookIdPs.setString(1, id);
						ResultSet result = getBookIdPs.executeQuery();
						// if (result.isBeforeFirst()) {
						result.next();
						int bookId = result.getInt(1);
						String insertAddressBookId = "insert into addressbook_contact(book_Id,contact_Id) VALUES (?, ?)";
						PreparedStatement insertAddressBookIdPs = connection.prepareStatement(insertAddressBookId);
						insertAddressBookIdPs.setInt(1, bookId);
						insertAddressBookIdPs.setInt(2, contactId);
						insertAddressBookIdPs.executeUpdate();

					}
				}
				connection.commit();
				;
				// }
			} catch (SQLException e) {
				e.printStackTrace();
				connection.rollback();
			}
		}
	}
}
