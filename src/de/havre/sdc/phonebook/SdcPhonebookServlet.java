package de.havre.sdc.phonebook;

import java.io.IOException;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * SdcPhonebookServlet implements an simple phonebook to demonstrate the usage
 * of GAE DataStorage and JPA.
 * 
 */
@SuppressWarnings("serial")
public class SdcPhonebookServlet extends HttpServlet {

	@SuppressWarnings("unchecked")
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {

		/* Select all PhonebookEnties from GaeDataStore */
		EntityManager em = EmfSingleton.getInstance().createEntityManager();
		Query query = em.createQuery("SELECT e FROM PhonebookEntry e");
		Collection<PhonebookEntry> cc = (Collection<PhonebookEntry>) query
				.getResultList();

		/* Make all entries available for JSP template */
		req.setAttribute("phonebookEntryList", cc);

		/* render response */
		RequestDispatcher rd = this.getServletContext().getRequestDispatcher(
				"/sdcphonebook.jsp");
		rd.forward(req, resp);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		/* Create new JPA data bean and fill it with post parameters */
		PhonebookEntry m = new PhonebookEntry();
		m.setName(req.getParameter("name"));
		m.setPhonenumber(req.getParameter("phonenumber"));

		/* Make the new bean persistent */
		EntityManager em = EmfSingleton.getInstance().createEntityManager();
		em.getTransaction().begin();
		em.persist(m);
		em.getTransaction().commit();

		/* Forward to display the hole phonebook */
		resp.sendRedirect("/sdcphonebook");
	}

}
