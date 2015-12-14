package com.vini.lms.web.pages;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;

import com.vini.lms.WicketSession;

public class BasePage extends WebPage {



	public BasePage() {
		WicketSession session=WicketSession.get();

		Form<Void>form=new Form<Void>("form");

		WebMarkupContainer menuContainer=new WebMarkupContainer("menu");

		AjaxSubmitLink home=new AjaxSubmitLink("home") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget arg0, Form<?> arg1) {
				setResponsePage(BasePage.class);

			}
		};

		AjaxSubmitLink search=new AjaxSubmitLink("search") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget arg0, Form<?> arg1) {
				setResponsePage(SearchPage.class);

			}
		};

		AjaxSubmitLink login=new AjaxSubmitLink("login") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget arg0, Form<?> arg1) {
				setResponsePage(LoginPage.class);

			}
		};

		AjaxSubmitLink register=new AjaxSubmitLink("registration") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget arg0, Form<?> arg1) {
				setResponsePage(RegisterPage.class);

			}
		};

		AjaxSubmitLink contact=new AjaxSubmitLink("contact") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget arg0, Form<?> arg1) {
				setResponsePage(ContactPage.class);

			}
		};

		AjaxSubmitLink profile=new AjaxSubmitLink("profile") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget arg0, Form<?> arg1) {
				setResponsePage(new ProfilePage());

			}
		};

		AjaxSubmitLink history=new AjaxSubmitLink("history") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget arg0, Form<?> arg1) {
				setResponsePage(HistoryPage.class);

			}
		};

		AjaxSubmitLink logout=new AjaxSubmitLink("logout") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget arg0, Form<?> arg1) {
				WicketSession.get().setUser(null);
				setResponsePage(BasePage.class);

			}
		};

		AjaxSubmitLink users=new AjaxSubmitLink("users") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget arg0, Form<?> arg1) {
				setResponsePage(ContactPage.class);

			}
		};

		AjaxSubmitLink books=new AjaxSubmitLink("books") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget arg0, Form<?> arg1) {
				setResponsePage(new AddBooksPage());

			}
		};

		AjaxSubmitLink returnBooks =new AjaxSubmitLink("return") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget arg0, Form<?> arg1) {
				setResponsePage(ReturnPage.class);

			}
		};

		menuContainer.add(home);
		menuContainer.add(search);
		menuContainer.add(contact);
		menuContainer.add(login);
		menuContainer.add(register);
		menuContainer.add(logout.setVisible(false));
		menuContainer.add(books.setVisible(false));
		menuContainer.add(users.setVisible(false));
		menuContainer.add(returnBooks.setVisible(false));
		menuContainer.add(profile.setVisible(false));
		menuContainer.add(history.setVisible(false));

		if(null!=session.getUser()){
			login.setVisible(false);
			register.setVisible(false);
			logout.setVisible(true);

			if(session.getUser().getRole().equals("Admin")){
				users.setVisible(true);
				returnBooks.setVisible(true);
				books.setVisible(true);

			}else if(session.getUser().getRole().equals("Member")){
				profile.setVisible(true);
				history.setVisible(true);

			}
		}


		add(form);
		form.add(menuContainer);

	}

}
