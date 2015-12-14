package com.vini.lms.web.pages;


import java.util.List;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.vini.lms.WicketSession;
import com.vini.lms.core.dao.UserDao;
import com.vini.lms.core.ec.History;
import com.vini.lms.core.ec.User;

public class HistoryPage extends BasePage {
	
	@SpringBean
	private UserDao userDao;
	private List<History> historyList;
	
	public HistoryPage() {
		setup();
		
		WebMarkupContainer historyContainer = new WebMarkupContainer("historyContainer");
		historyContainer.setOutputMarkupId(true);
		add(historyContainer);
		
		ListView<History> historyView = new ListView<History>("listView",historyList) {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<History> item) {
				History history=item.getModelObject();
				
				item.add(new Label("index", item.getIndex()+1 + "."));
				item.add(new Label("book", history.getBook().getName()));
				item.add(new Label("author", history.getBook().getAuthor()));
				item.add(new Label("issue", history.getIssue().getIssueDate().toString()));
				String returnDate = history.getIssue().getActReturnDate()==null ? "Not Yet Returned": history.getIssue().getActReturnDate().toString();
				item.add(new Label("return", returnDate));
				
			}
		};
		historyContainer.add(historyView);
		
	}

	private void setup() {
		User user=WicketSession.get().getUser();
		if(user!=null){
			historyList=userDao.getHistory(user);
		}
		
	}

}
