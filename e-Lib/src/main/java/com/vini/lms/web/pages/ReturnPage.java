package com.vini.lms.web.pages;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow.CloseButtonCallback;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.vini.lms.core.dao.BookDao;
import com.vini.lms.core.ec.Book;

public class ReturnPage extends BasePage {
	private Form<Void> returnForm;
	private TextField<String> userId;
	private DropDownChoice<Book> books;
	private ModalWindow modalWindow;
	private List<Book> bookList;
	@SpringBean
	private BookDao bookDao;
	
	public ReturnPage() {
		
		returnForm = new Form<Void>("returnForm");
		userId = new TextField<String>("userId",new Model<String>());
		
		IChoiceRenderer<Book> bookRenderer = new IChoiceRenderer<Book>() {
			private static final long serialVersionUID = 1L;

			@Override
			public String getIdValue(Book object, int index) {
				return object.getId().toString();
			}
			
			@Override
			public Object getDisplayValue(Book object) {
				return object.getName();
			}
		};
		books = new DropDownChoice<Book>("books", getList(), bookRenderer);
		books.setOutputMarkupId(true);
		
		userId.add(new AjaxFormComponentUpdatingBehavior("onKeyUp") {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				Long uId = Long.parseLong(userId.getModelObject());
				bookList = bookDao.getIssuedBooks(uId);
				books.setModel(new  Model<Book>());
				books.setChoices(bookList);
				target.addComponent(books);
			}
		});
		
				
		
		AjaxSubmitLink returnLink = new AjaxSubmitLink("return") {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				target.appendJavascript( "Wicket.Window.unloadConfirmation = false;" ); 
				if(bookDao.returnBook(userId.getModelObject(), books.getModelObject().getId())){
					modalWindow.setCloseButtonCallback(new CloseButtonCallback() {
						
						/**
						 * 
						 */
						private static final long serialVersionUID = 1L;

						@Override
						public boolean onCloseButtonClicked(AjaxRequestTarget target) {
							setResponsePage(ReturnPage.class);
							return true;
						}
					});
					modalWindow.show(target);
					info(books.getModelObject().getName()+" Returned Successfully!!");
										
				}else{
					modalWindow.show(target);
					info(books.getModelObject().getName()+" Return Unsuccessfull. Try Again... ");
					modalWindow.setCloseButtonCallback(new CloseButtonCallback() {

						/**
						 * 
						 */
						private static final long serialVersionUID = 1L;

						@Override
						public boolean onCloseButtonClicked(AjaxRequestTarget target) {
							setResponsePage(ReturnPage.class);
							return true;
						}
					});
					}
			}
			
			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				modalWindow.show(target);
				super.onError(target, form);
			}
		};
		
		returnForm.add(userId);
		returnForm.add(books);
		returnForm.add(returnLink);
		add(getErrorWindow());
		add(returnForm);
	}
	
	private List<? extends Book> getList() {
		return new ArrayList<Book>();
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
