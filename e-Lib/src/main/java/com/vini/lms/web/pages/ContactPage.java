package com.vini.lms.web.pages;

import org.apache.wicket.markup.html.basic.Label;

public class ContactPage extends BasePage {

	public ContactPage() {
		
		add(new Label("name", "Name   :Vinit Kumar"));
		add(new Label("mob", " Mob    :7899173146"));
		add(new Label("email", "Email  :vinit_se@hotmail.com"));
	}

}
