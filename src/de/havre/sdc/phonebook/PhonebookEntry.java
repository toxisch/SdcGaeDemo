package de.havre.sdc.phonebook;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Key;

/**
 * JPA entity bean to hold the data of a phonebook entry.
 */
@Entity
public class PhonebookEntry {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;

	private String name;

	private String phonenumber;

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

}
