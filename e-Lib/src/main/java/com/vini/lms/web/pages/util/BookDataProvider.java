package com.vini.lms.web.pages.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.injection.web.InjectorHolder;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import com.vini.lms.core.dao.BookDao;
import com.vini.lms.core.ec.Book;


public class BookDataProvider extends SortableDataProvider<Book> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SpringBean
	private BookDao bookDao;
	private List<String> critList;
	private int currentPage;
	
	public BookDataProvider() {
		this(Arrays.asList(new String[]{"","","",""}));
	}

	public BookDataProvider(List<String> criteria) {
		this.critList=criteria;
		// The default sorting
		setSort("id", true);
		InjectorHolder.getInjector().inject(this);
		
	}

	@Override
	public Iterator<? extends Book> iterator(int first, int count) {
		boolean order=false;
		List<Book> newList;
		
		if(getSort()==null){
			order=true;
			newList = bookDao.getBooks(first, count, critList, order);
		
		} else {
		newList = bookDao.getBooks(first, count, critList, getSort().getProperty(), getSort().isAscending());
		}
		
		if(newList!=null){
			return newList.iterator();
		
		}else{
			return new ArrayList<Book>().iterator();
		}


	}

	@Override
	public IModel<Book> model(Book arg0) {
		return new CompoundPropertyModel<Book>(arg0);
	}

	@Override
	public int size() {
		if(getSort()==null){
			return bookDao.getCount(critList );
		
		} else {
		return bookDao.getCount(critList);
		}
		
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	
}
