package com.vini.lms.web.pages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxFallbackDefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.extensions.model.AbstractCheckBoxModel;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import com.vini.lms.WicketSession;
import com.vini.lms.core.ec.Book;
import com.vini.lms.core.ec.User;
import com.vini.lms.web.pages.util.BookDataProvider;
import com.vini.lms.web.pages.util.CheckBoxColumn;
import com.vini.lms.web.pages.util.LinkColumn;


public class SearchPage extends BasePage {

	private BookDataProvider dataProvider;
	private AjaxFallbackDefaultDataTable<Book> dataTable;
	private List<String> criteria;
	private Set<Long> selectedBooksId;
	private ModalWindow modalWindow;
	private User user;

	public SearchPage() {
		setup();

		Form<Book>form=new Form<Book>("searchForm",new CompoundPropertyModel<Book>(new Book()));
		
		final WebMarkupContainer tableContainer=new WebMarkupContainer("tableContainer");
		tableContainer.setOutputMarkupId(true);
		final WebMarkupContainer buttonContainer=new WebMarkupContainer("buttonContainer");
		buttonContainer.setOutputMarkupId(true);

		final TextField<String>name=new TextField<String>("name", new Model<String>(criteria.get(0).toString()));
		final TextField<String>author=new TextField<String>("author", new Model<String>(criteria.get(1).toString()));
		final TextField<String>publisher=new TextField<String>("publisher", new Model<String>(criteria.get(2).toString()));
		final DropDownChoice<String>category=new DropDownChoice<String>("category", new Model<String>(criteria.get(3).toString()),getChoices());
		
		final AjaxSubmitLink issueLink= new AjaxSubmitLink("issue"){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit(AjaxRequestTarget target, Form<?> form) {
				super.onSubmit();
				WicketSession.get().setBook(selectedBooksId);
				if(WicketSession.get().getUser()!=null){
					if(selectedBooksId.size()>0){
					System.out.println("Ready to Redirect-->"+selectedBooksId);
					setResponsePage(new IssuePage());
				}else{
					modalWindow.show(target);
					error("No Book is selected to issue!!");
				}
				}else {
					setResponsePage(LoginPage.class);
				}
			}
		};
		

		name.add(new AjaxFormComponentUpdatingBehavior("onkeyup") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget arg0) {
				if(name.getModelObject()!=null){
					criteria.set(0, name.getModelObject());
					WicketSession.get().replaceSearchBookCriteria(criteria);
					arg0.addComponent(tableContainer);
				}else{
					criteria.set(0, "");
					WicketSession.get().replaceSearchBookCriteria(criteria);
					arg0.addComponent(tableContainer);
				}
			}
		});

		author.add(new AjaxFormComponentUpdatingBehavior("onkeyup") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget arg0) {
				if(author.getModelObject()!=null){
					criteria.set(1, author.getModelObject());
					WicketSession.get().replaceSearchBookCriteria(criteria);
					arg0.addComponent(tableContainer);
				}else{
					criteria.set(1, "");
					WicketSession.get().replaceSearchBookCriteria(criteria);
					arg0.addComponent(tableContainer);
				}
			}
		});

		publisher.add(new AjaxFormComponentUpdatingBehavior("onkeyup") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget arg0) {
				if(publisher.getModelObject()!=null){
					criteria.set(2, publisher.getModelObject());
					WicketSession.get().replaceSearchBookCriteria(criteria);
					arg0.addComponent(tableContainer);
				}else{
					criteria.set(2, "");
					WicketSession.get().replaceSearchBookCriteria(criteria);
					arg0.addComponent(tableContainer);
				}
			}
		});

		category.add(new AjaxFormComponentUpdatingBehavior("onchange") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget arg0) {
				if(category.getModelObject()!=null){
					criteria.set(3, category.getModelObject());
					WicketSession.get().replaceSearchBookCriteria(criteria);
					arg0.addComponent(tableContainer);
				}else{
					criteria.set(3, "");
					WicketSession.get().replaceSearchBookCriteria(criteria);
					arg0.addComponent(tableContainer);
				}
			}
		});

		tableContainer.add(dataTable);
		buttonContainer.add(issueLink);
		issueLink.setVisible(true);
		if(user!=null && user.getRole().equals("Admin")){
			issueLink.setVisible(false);
		}

		add(form);
		form.add(name);
		form.add(author);
		form.add(publisher);
		form.add(category);
		form.add(buttonContainer);
		form.add(getErrorWindow());
		form.add(tableContainer);


	}

	private void setup() {
		criteria = WicketSession.get().getSearchBookCriteria();
		if(criteria==null){
			this.criteria = Arrays.asList(new String[]{"","","",""});
			WicketSession.get().setSearchBookCriteria(criteria);
		}
		this.dataProvider=WicketSession.get().getBookDataProvider();
		if(dataProvider==null){
			dataProvider=new BookDataProvider(criteria);
			WicketSession.get().setBookDataProvider(dataProvider);
		}
		selectedBooksId = WicketSession.get().getBook();
		if(selectedBooksId==null){
			this.selectedBooksId = new HashSet<Long>();
		}
		List<IColumn<Book>> columns= createColumns();
		dataTable=new AjaxFallbackDefaultDataTable<Book>("bookTable",columns,dataProvider,5);
		//dataTable.setOutputMarkupId(true);
		dataTable.setCurrentPage(dataProvider.getCurrentPage());

	}

	private List<IColumn<Book>> createColumns() {

		user=WicketSession.get().getUser();

		List<IColumn<Book>>columns =new ArrayList<IColumn<Book>>();
		columns.add(new PropertyColumn<Book>(new Model<String>("ID"), "id","id"));
		columns.add(new PropertyColumn<Book>(new Model<String>("Author"), "author", "author"));
		columns.add(new PropertyColumn<Book>(new Model<String>("Publisher"), "publisher", "publisher"));
		columns.add(new PropertyColumn<Book>(new Model<String>("Category"), "category", "category"));
		columns.add(new PropertyColumn<Book>(new Model<String>("Price"), "price", "price"));

		if(user!=null && user.getRole().equals("Admin")){
			columns.add(1,new LinkColumn<Book>(new Model<String>("Name"), "name", "name"){

				private static final long serialVersionUID = 1L;

				@Override
				protected void onClick(IModel<Book> clicked) {
					System.out.println("Clicked!!"+clicked.getObject().getId());

				}
			}
					);

		}else{
			columns.add(0,new CheckBoxColumn<Book>(new Model<String>("")) {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				protected IModel<Boolean> newCheckBoxModel(final IModel<Book> rowModel) {
					return new AbstractCheckBoxModel() {
			               /**
						 * 
						 */
						private static final long serialVersionUID = 1L;
						@Override
			               public void unselect() {
			                  selectedBooksId.remove(rowModel.getObject().getId());
			               }
			               @Override
			               public void select() {
			            	   if(selectedBooksId.size()<5){
			            		   selectedBooksId.add(rowModel.getObject().getId());
			            	   } else {
			            		   error("You can issue only 5 books at a time!!");
			            	   }
			               }
			               @Override
			               public boolean isSelected() {
			                  return selectedBooksId.contains(rowModel.getObject().getId());
			               }
			               @Override
			               public void detach() {
			                  rowModel.detach();
			               }
			            };
			         }
			});
			columns.add(2,new PropertyColumn<Book>(new Model<String>("Name"), "name", "name"));
		}


		return columns;

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
		modalWindow.setTitle("Error");
		modalWindow.setInitialHeight(150);
		modalWindow.setInitialWidth(450);
		modalWindow.setOutputMarkupId(true);
		modalWindow.setResizable(false);
		return modalWindow;
	}


}
