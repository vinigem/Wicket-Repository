package com.vini.lms.core.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vini.lms.core.ec.Book;
import com.vini.lms.core.ec.History;
import com.vini.lms.core.ec.Issue;
import com.vini.lms.core.ec.User;


@Repository
@Transactional
public class BookDaoImpl implements BookDao {

	@Autowired
	private SessionFactory sessionFactory;
	private Session session;


	@SuppressWarnings({ "unchecked" })
	@Override
	public List<Book> getBooks(int first, int last, List<String> critList, boolean order) {
		try{
			session=sessionFactory.openSession();

			Criteria criteria=session.createCriteria(Book.class);
			criteria.add(Restrictions.gt("qty", 0));
			
			if(!critList.get(0).equals("")){
				criteria.add(Restrictions.like("name", critList.get(0).toString(),MatchMode.ANYWHERE).ignoreCase());
			}

			if(!critList.get(1).equals("")){
				criteria.add(Restrictions.like("author", critList.get(1).toString(),MatchMode.ANYWHERE).ignoreCase());
			}

			if(!critList.get(2).equals("")){
				criteria.add(Restrictions.like("publisher", critList.get(2).toString(),MatchMode.ANYWHERE).ignoreCase());
			}

			if(!critList.get(3).equals("")){
				criteria.add(Restrictions.eq("category", critList.get(3)));
			}

			if(order){
				criteria.addOrder(Order.asc("id"));
			}
			criteria.setFirstResult(first);
			criteria.setMaxResults(last);

			return criteria.list();

		}catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}finally{
			session.close();
		}
	}

	@Override
	public int getCount(List<String> critList) {
		try{
			session=sessionFactory.openSession();

			Criteria criteria=session.createCriteria(Book.class);
			criteria.add(Restrictions.gt("qty", 0));

			if(!critList.get(0).equals("")){
				criteria.add(Restrictions.like("name", critList.get(0).toString(),MatchMode.ANYWHERE).ignoreCase());
			}

			if(!critList.get(1).equals("")){
				criteria.add(Restrictions.like("author", critList.get(1).toString(),MatchMode.ANYWHERE).ignoreCase());
			}

			if(!critList.get(2).equals("")){
				criteria.add(Restrictions.like("publisher", critList.get(2).toString(),MatchMode.ANYWHERE).ignoreCase());
			}

			if(!critList.get(3).equals("")){
				criteria.add(Restrictions.eq("category", critList.get(3)));
			}

			return criteria.list().size();

		}catch (Exception e) {
			System.out.println(e.getMessage());
			return 0;

		}finally{
			session.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Book> getBooks(int first, int last, List<String> critList,
			String property, boolean ascending) {
		try{
			session=sessionFactory.openSession();

			Criteria criteria=session.createCriteria(Book.class);
			criteria.add(Restrictions.gt("qty", 0));

			if(!critList.get(0).equals("")){
				criteria.add(Restrictions.like("name", critList.get(0).toString(),MatchMode.ANYWHERE).ignoreCase());
			}

			if(!critList.get(1).equals("")){
				criteria.add(Restrictions.like("author", critList.get(1).toString(),MatchMode.ANYWHERE).ignoreCase());
			}

			if(!critList.get(2).equals("")){
				criteria.add(Restrictions.like("publisher", critList.get(2).toString(),MatchMode.ANYWHERE).ignoreCase());
			}

			if(!critList.get(3).equals("")){
				criteria.add(Restrictions.eq("category", critList.get(3)));
			}

			if(ascending){
				criteria.addOrder(Order.asc(property).ignoreCase());
			}else{
				criteria.addOrder(Order.desc(property).ignoreCase());
			}

			criteria.setFirstResult(first);
			criteria.setMaxResults(last);
			return criteria.list();

		}catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}finally{
			session.close();
		}
	}

	@Override
	public boolean saveBooks(List<Book> bookList) {
		try{
			session=sessionFactory.openSession();
			for(Book book:bookList){
				session.persist(book);
			}
			session.flush();
			return true;
		}catch (Exception e) {
			return false;
		}finally{
			session.close();
		}
	}

	@Override
	public List<Book> getSelectedBooks(Set<Long> selectedBooks) {
		List<Book> bookList = new ArrayList<Book>();
		for(Long id:selectedBooks){
			bookList.add(getBookById(id));
		}
		return bookList;
	}

	private Book getBookById(Long id) {
		try{
			session=sessionFactory.openSession();
			return (Book) session.get(Book.class, id);
		}catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}finally{
			session.close();
		}
	}

	@Override
	public void issueBooks(List<Book> bookList, User user) {
		try{
			session=sessionFactory.openSession();
			Issue issue=null;
			History history=null;

			for(Book book:bookList){
				issue=new Issue();
				history=new History();
				System.out.println("BookDaoImpl.issueBooks()"+book.getId()+"-->"+book.getQty());
				int qty = book.getQty()-1;
				book.setQty(qty);
				session.update(book);
				issue.setUserId(user.getId());
				issue.setBookId(book.getId());
				issue.setIssueDate(new Date());

				history.setBook(book);
				history.setUser(user);

				System.out.println("BookDaoImpl.issueBooks()"+issue);

				session.persist(issue);
				history.setIssue(issue);
				session.persist(history);
			}
			session.flush();

		}catch (Exception e) {
			System.out.println(e.getMessage());

		}finally{
			session.close();
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Book> getIssuedBooks(Long uId) {
		List<Book> bookList = new ArrayList<Book>();
		try{
			session = sessionFactory.openSession();
			Query query = session.createQuery("From Issue WHERE userId =:userId and actReturnDate IS NULL");
			query.setParameter("userId", uId);
			List<Issue> issuedBooks = query.list();

			for(Issue book: issuedBooks){
				query = session.createQuery("From Book WHERE bookId =:bookId");
				query.setParameter("bookId", book.getBookId());
				bookList.add((Book) query.list().get(0));
			}

			return bookList;	


		}catch (Exception e) {
			e.printStackTrace();
			return null;

		}finally{
			session.close();
		}
	}

	@Transactional
	public boolean returnBook(String uId, Long bookId) {
		try{
			session = sessionFactory.openSession();
			Query query = session.createQuery("FROM Issue WHERE userId =:userId and bookId =:bookId and actReturnDate is null");
			query.setParameter("userId", Long.parseLong(uId));
			query.setParameter("bookId", bookId);
			Issue issue= (Issue) query.list().get(0);
			issue.setActReturnDate(new Date());
			session.update(issue);
			session.flush();
					
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		
		}finally{
			session.close();
		}
		return true;	
	}


}
