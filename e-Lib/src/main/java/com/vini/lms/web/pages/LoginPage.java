package com.vini.lms.web.pages;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow.CloseButtonCallback;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.EmailAddressValidator;

import com.vini.lms.WicketSession;
import com.vini.lms.core.dao.UserDao;
import com.vini.lms.core.ec.User;

public class LoginPage extends BasePage {
	
	private ModalWindow modalWindow;
	//private FeedbackPanel feedbackPanel;
	
	@SpringBean
	private UserDao userDao;

	public LoginPage() {
		//feedbackPanel=new FeedbackPanel("feedback");
		//feedbackPanel.setOutputMarkupId(true);
		
		CompoundPropertyModel<User>userModel=new CompoundPropertyModel<User>(new User());
		
		Form<User> form=new Form<User>("loginForm",userModel);
		
		final TextField<String>email=new TextField<String>("emailId",new Model<String>());
		final PasswordTextField password=new PasswordTextField("password",new Model<String>());
				
		email.setRequired(true);
		email.add(EmailAddressValidator.getInstance());
		password.setRequired(true);
		
				
		AjaxSubmitLink submit=new AjaxSubmitLink("loginButton") {
			
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				target.appendJavascript( "Wicket.Window.unloadConfirmation = false;" ); 
				User user=userDao.validate(email.getModelObject(), password.getModelObject());
				if(null!=user){
					WicketSession.get().setUser(user);
					setResponsePage(new BasePage());
				}else{
					modalWindow.show(target);
					error("Invalid Login Credentials..!!");
					modalWindow.setCloseButtonCallback(new CloseButtonCallback() {

						/**
						 * 
						 */
						private static final long serialVersionUID = 1L;

						@Override
						public boolean onCloseButtonClicked(AjaxRequestTarget target) {
							setResponsePage(LoginPage.class);
							return true;
						}
					});
				}
				
			}
			
			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				super.onError(target, form);
				modalWindow.show(target);
				//target.addComponent(feedbackPanel);
								
			}
		};
		
		
		
		add(getErrorWindow());
		add(form);
	//	add(feedbackPanel);
		form.add(email);
		form.add(password);
		form.add(submit);
		//form.add(register);
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
