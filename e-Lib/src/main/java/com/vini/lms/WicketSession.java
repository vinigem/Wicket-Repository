package com.vini.lms;

import java.util.List;
import java.util.Set;

import org.apache.wicket.Request;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebSession;

import com.vini.lms.core.ec.User;
import com.vini.lms.web.pages.util.BookDataProvider;

public class WicketSession extends WebSession {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private User user;
	private Set<Long> bookId;
	private List<String> searchBookCriteria;
	private BookDataProvider bookDataProvider;

	public Set<Long> getBook() {
		return bookId;
	}

	public void setBook(Set<Long> selectedBookId) {
		this.bookId = selectedBookId;
	}

	public List<String> getSearchBookCriteria() {
		return searchBookCriteria;
	}

	public void setSearchBookCriteria(List<String> searchBookCriteria) {
		this.searchBookCriteria = searchBookCriteria;
	}
	
	public void replaceSearchBookCriteria(List<String> searchBookCriteria) {
		this.searchBookCriteria = null;
		this.searchBookCriteria=searchBookCriteria;
	}

	public WicketSession(Request request) {
		super(request);
	}
	
	public static WicketSession get(){
		return (WicketSession)Session.get();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public BookDataProvider getBookDataProvider() {
		return this.bookDataProvider;
	}

	public void setBookDataProvider(BookDataProvider bookDataProvider) {
		this.bookDataProvider = bookDataProvider;
	}

}
