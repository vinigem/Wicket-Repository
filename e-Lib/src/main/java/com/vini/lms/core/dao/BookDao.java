package com.vini.lms.core.dao;

import java.util.List;
import java.util.Set;

import com.vini.lms.core.ec.Book;
import com.vini.lms.core.ec.User;

public interface BookDao {
	
	public List<Book> getBooks(int first, int last,List<String> critList, boolean order);

	public int getCount(List<String> critList);

	public List<Book> getBooks(int first, int last, List<String> critList,
			String property, boolean ascending);

	public boolean saveBooks(List<Book> modelObject);

	public List<Book> getSelectedBooks(Set<Long> selectedBooks);

	public void issueBooks(List<Book> modelObject, User user);

	public List<Book> getIssuedBooks(Long uId);

	public boolean returnBook(String uId, Long bookId);

}
