package de.havre.sdc.phonebook;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * The EmfSingleton holds an instance of EntityManagerFactory because the
 * creation of an new instance needs to much time. Google recommends this way of
 * implementation.
 */
public final class EmfSingleton {

	private static final EntityManagerFactory emfInstance = Persistence
			.createEntityManagerFactory("transactions-optional");

	private EmfSingleton() {
	}

	public static EntityManagerFactory getInstance() {
		return emfInstance;
	}
}