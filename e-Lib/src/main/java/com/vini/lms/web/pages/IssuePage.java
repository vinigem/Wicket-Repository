package com.vini.lms.web.pages;

import java.util.List;
import java.util.Set;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow.CloseButtonCallback;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.vini.lms.WicketSession;
import com.vini.lms.core.dao.BookDao;
import com.vini.lms.core.ec.Book;

public class IssuePage extends BasePage {
	
	@SpringBean
	private BookDao bookDao;
	private List<Book> bookList;
	private ModalWindow modalWindow;
	
	public IssuePage() {
		setUp();
		
		final WebMarkupContainer bookContainer = new WebMarkupContainer("bookContainer");
		bookContainer.setOutputMarkupId(true);
		
		Form<Book> issueForm = new Form<Book>("issueForm");
		add(issueForm);
		issueForm.add(bookContainer);
		
		final ListView<Book> listView = new ListView<Book>("listView", bookList) {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(final ListItem<Book> item) {
			Book book=item.getModelObject();
				item.add(new Label("name", book.getName()));
				item.add(new Label("author", book.getAuthor()));
				item.add(new Label("publisher", book.getPublisher()));
				item.add(new Label("category", book.getCategory()));
				item.add(new AjaxSubmitLink("remove"){

					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
					protected void onSubmit(AjaxRequestTarget target,
							Form<?> form) {
						if(getList().size()>1){
							WicketSession.get().getBook().remove(item.getModelObject().getId());
							getList().remove(item.getModelObject());
             				target.addComponent(bookContainer);
						}else{
							modalWindow.show(target);
							error("You cannot delete the last book!!");
						}
					}
					
				});			
			}
		};
		
		bookContainer.add(listView);
		
		issueForm.add(new AjaxSubmitLink("issue"){

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				target.appendJavascript( "Wicket.Window.unloadConfirmation = false;" ); 
				bookDao.issueBooks(listView.getModelObject(), WicketSession.get().getUser());
				WicketSession.get().setBook(null);
				modalWindow.show(target);
				info(listView.getModelObject().size()+" Books have been issued on your account!!");
				modalWindow.setCloseButtonCallback(new CloseButtonCallback() {
					
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
					public boolean onCloseButtonClicked(AjaxRequestTarget target) {
						setResponsePage(BasePage.class);
						return true;
					}
				});						
			}
			
		});

		add(getErrorWindow());
	}

	private void setUp() {
		Set<Long> selectedBooks=WicketSession.get().getBook();
		if(selectedBooks!=null && selectedBooks.size()>0){
			bookList=bookDao.getSelectedBooks(selectedBooks);
		}
		
	}
	
	private ModalWindow getErrorWindow() {
		modalWindow=new ModalWindow("error");
		ErrorPanel errorPanel = new ErrorPanel(modalWindow.getContentId());
		errorPanel.setOutputMarkupId(true);
		modalWindow.setContent(errorPanel);
		modalWindow.setTitle("Message");
		modalWindow.setInitialHeight(150);
		modalWindow.setInitialWidth(450);
		modalWindow.setOutputMarkupId(true);
		modalWindow.setResizable(false);
		return modalWindow;
	}

}
