package com.vini.lms.core.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vini.lms.core.ec.History;
import com.vini.lms.core.ec.User;


@Repository
@Transactional
public class UserDaoImpl implements UserDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	private Session session;
	
	

	@Override
	public User getUser(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User saveOrUpdate(User user) {
		try{
			session=sessionFactory.openSession();
			session.saveOrUpdate(user);
			session.flush();
			
			Query query=session.createQuery("FROM User user WHERE user.emailId: emailId");
			query.setParameter("emailId", user.getEmailId());
			return (User) query.list().get(0);
			
		}catch(Exception he){
			System.out.println(he.getMessage());
			return null;
		}finally{
			session.close();
		}
	}

	@Override
	public User validate(String email, String password) {
		try{
			session=sessionFactory.openSession();
			Query query = session.createQuery("FROM User user WHERE user.emailId = :emailId AND user.password = :password");
			query.setParameter("emailId", email);
			query.setParameter("password", password);
			
			return (User) query.list().get(0);
			
		}catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}finally{
			session.close();
		}
	}

	@Override
	public List<User> searchUser(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	public boolean register(User user) {
		session=sessionFactory.openSession();
		
		user.setRole("Member");
		user.setActiveDate(getActiveDate(user.getMembership()));
		try{
			session.save(user);
			session.flush();
			return true;
		}catch (Exception e) {
			return false;
		}finally{
			session.close();
		}
		
	}

	private Date getActiveDate(String membership) {
		Date date=new Date();
		
		Calendar cal = Calendar.getInstance();  
		cal.setTime(date);  
							
		if(membership.equals("1 Month")){
			cal.add(Calendar.MONTH, 1);
		
		}else if(membership.equals("2 Months")){
			cal.add(Calendar.MONTH, 2);
		
		}else if(membership.equals("3 Months")){
			cal.add(Calendar.MONTH, 3);
		
		}else if(membership.equals("4 Months")){
			cal.add(Calendar.MONTH, 4);
		
		}else if(membership.equals("5 Months")){
			cal.add(Calendar.MONTH, 5);
		
		}else if(membership.equals("6 Months")){
			cal.add(Calendar.MONTH, 6);
		
		}else if(membership.equals("1 Year")){
			cal.add(Calendar.YEAR, 1);
		}
		
		date = cal.getTime();
		return date;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<History> getHistory(User user) {
		try{
			session=sessionFactory.openSession();
			Query query = session.createQuery("From History WHERE userId=:userId");
			query.setParameter("userId", user.getId());
			return query.list();
		
		}catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}finally{
			session.close();
		}
	}

}
