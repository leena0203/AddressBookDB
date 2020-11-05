package com.leena.addressBook;

import java.util.Comparator;

public class SortByName implements Comparator<Contact> {

	@Override
	public int compare(Contact a, Contact b) {
			return (a.getFirstName()+" "+a.getLastName()).compareTo(b.getFirstName()+" "+b.getLastName());
		}
	}
