package com.vini.lms.core.dao;

import java.util.List;

import com.vini.lms.core.ec.History;
import com.vini.lms.core.ec.User;

public interface UserDao {
	
	public User getUser(final Long id);
	public List<User> getUsers();
	public User saveOrUpdate(final User user);
	public User validate(final String email, final String password);
	public List<User> searchUser(final String name);
	public boolean register(User user);
	public List<History> getHistory(User user);

}
