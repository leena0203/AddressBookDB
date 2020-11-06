package com.bridgelabz.addressBook;

import java.util.Comparator;

public class SortByZip implements Comparator<Contact> {

	@Override
	public int compare(Contact a, Contact b) {
		return (int)(a.getZip() - b.getZip());
	}
}
