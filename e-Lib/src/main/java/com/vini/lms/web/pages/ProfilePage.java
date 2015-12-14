package com.vini.lms.web.pages;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.CompoundPropertyModel;

import com.vini.lms.WicketSession;
import com.vini.lms.core.ec.User;

public class ProfilePage extends BasePage {
	
	public ProfilePage() {
		User user=WicketSession.get().getUser();
		CompoundPropertyModel<User> userModel= new CompoundPropertyModel<User>(user);
		Form<User> form = new Form<User>("profileForm",userModel);
		form.add(new Label("id"));
		form.add(new Label("name"));
		form.add(new Label("emailId"));
		form.add(new Label("dob"));
		form.add(new Label("gender"));
		form.add(new Label("membership"));
		form.add(new Label("address"));
		add(form);
	}

}
