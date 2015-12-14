package com.vini.lms.web.pages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.vini.lms.core.dao.BookDao;
import com.vini.lms.core.ec.Book;

public class AddBooksPage extends BasePage {

	private ModalWindow modalWindow;
	private List<Book> bookList;
	private int bookCount=1;
	private ListView<Book> listView;
	@SpringBean
	private BookDao bookDao;

	public AddBooksPage() {

		final WebMarkupContainer bookContainer = new WebMarkupContainer("bookContainer");
		bookContainer.setOutputMarkupId(true);

		bookList=new ArrayList<Book>();
		bookList.add(new Book());

		Form<Book> bookForm = new Form<Book>("bookForm");
		bookForm.add(bookContainer);

		listView =new ListView<Book>("listView",bookList) {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(final ListItem<Book> item) {
				
				Book book = item.getModelObject();
				final RequiredTextField<String> name = new RequiredTextField<String>("name",new PropertyModel<String>(book, "name"));
				
				final RequiredTextField<String> author = new RequiredTextField<String>("author",new PropertyModel<String>(book, "author"));
				
				final RequiredTextField<String> publisher = new RequiredTextField<String>("publisher",new PropertyModel<String>(book, "publisher"));
				
				final RequiredTextField<Double> price = new RequiredTextField<Double>("price",new PropertyModel<Double>(book, "price"));
				
				final RequiredTextField<Integer> qty = new RequiredTextField<Integer>("qty",new PropertyModel<Integer>(book, "qty"));
				
				final DropDownChoice<String> category = new DropDownChoice<String>("category",new PropertyModel<String>(book, "category"),getChoices());
					
				item.add(new Label("index", item.getIndex()+1 + "."));
				item.add(name);
				item.add(author);
				item.add(publisher);
				item.add(price);
				item.add(qty);
				item.add(category);
				
			}
		};
		
		bookContainer.add(new AjaxSubmitLink("add"){

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				if(bookCount<10){
					listView.getModelObject().add(new Book());
					if (target != null){
						bookCount++;
						target.addComponent(bookContainer);
					}
				}else {
					modalWindow.show(target);
					error("You can only add 10 Books at a time!!");
				}
			}
			
			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				super.onError(target, form);
				modalWindow.show(target);
			}
			
		}.setDefaultFormProcessing(false));
		
		bookContainer.add(new AjaxSubmitLink("delete"){

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				if(bookCount>1){ 
					listView.getModelObject().remove(bookCount-1);
					if (target != null){
						bookCount--;
						target.addComponent(bookContainer);
					}
				}else {
					modalWindow.show(target);
					error("You cannot delete the last row!!");
				}
			}
			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				super.onError(target, form);
				modalWindow.show(target);
			}
			
		}.setDefaultFormProcessing(false));

		
		listView.setReuseItems(true);
		bookContainer.add(listView);

		AjaxSubmitLink saveLink= new AjaxSubmitLink("save") {

			
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				
				if(listView.getModelObject().size()>0){ 
				   if(bookDao.saveBooks(listView.getModelObject())){
					   modalWindow.show(target);
					   info("All Books Saved Successfully!!");
				   }else{
					   modalWindow.show(target);
					   error("Error in Saving Book Details!!");
				   }
				   
				}else {
					modalWindow.show(target);
					error("No Book Information Added!!");
				}
			}
			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				super.onError(target, form);
				modalWindow.show(target);
			}

		};
		//saveLink.setDefaultFormProcessing(false);
		bookForm.add(saveLink);
		add(bookForm);
		add(getErrorWindow());
	}

	private List<? extends String> getChoices() {
		List<String>categories=new ArrayList<String>();
		categories.add("Arts");
		categories.add("Science");
		categories.add("General");
		categories.add("Fictional");
		categories.add("Magazines");
		categories.add("Computers");
		categories.add("Literature");
		categories.add("AutoBiography");
		Collections.sort(categories);
		return categories;
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
