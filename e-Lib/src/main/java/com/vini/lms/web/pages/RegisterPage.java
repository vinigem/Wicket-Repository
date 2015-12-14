package com.vini.lms.web.pages;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow.WindowClosedCallback;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.EmailAddressValidator;
import org.wicketstuff.jwicket.ui.datepicker.DatePicker;
import com.vini.lms.core.dao.UserDao;
import com.vini.lms.core.ec.User;
import com.vini.lms.core.si.MailService;

public class RegisterPage extends BasePage {
	
	
	@SpringBean
	private UserDao userDao;
	@SpringBean
	private MailService mailService;
	
	private ModalWindow modalWindow;
	private RequiredTextField<String> name;
	private RequiredTextField<String> email;
	private PasswordTextField password;
	private DateTextField dob;
	private RadioChoice<String> gender;
	private TextArea<String> address;
	private DropDownChoice<String> membership;
		
	public RegisterPage() {
		CompoundPropertyModel<User> userModel=new CompoundPropertyModel<User>(new User());
		
		final Form<User> regForm= new Form<User>("registerForm", userModel);
		
		name= new RequiredTextField<String>("name");
		
		email= new RequiredTextField<String>("emailId");
		email.add(EmailAddressValidator.getInstance());
		
		password= new PasswordTextField("password");
		
		//DateTextField dob= new DateTextField("dob",new SimpleDateFormat("yyyy-mm-dd").toPattern());
		dob= new DateTextField("dob",new SimpleDateFormat("dd-MM-yyyy").toPattern());
		dob.add(getDatePicker());
										
		gender= new RadioChoice<String>("gender", Arrays.asList(new String[]{"Male","Female"})).setSuffix("");
		
		address= new TextArea<String>("address");
		
		membership=new DropDownChoice<String>("membership", getSubChoices());
		membership.setRequired(true);
		
		AjaxSubmitLink register= new AjaxSubmitLink("register") {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				regForm.setEnabled(false);								
				if(userDao.register((User) regForm.getModelObject())){
					
					mailService.sendRegisterMail(regForm.getModelObject().getEmailId());
					
					modalWindow.show(target);
					
					error("You have been successfully Registered on e-Library!!" +
							"\n An Email has been sent to your Registered email address." +
							"\n Please Login to your account to use the e-Library services...");
					
					modalWindow.close(target);
					modalWindow.setWindowClosedCallback(new WindowClosedCallback() {
						
						/**
						 * 
						 */
						private static final long serialVersionUID = 1L;

						@Override
						public void onClose(AjaxRequestTarget target) {
							setResponsePage(new LoginPage());
							
						}
					});
					
				}
				
			}
			
			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				modalWindow.show(target);
				
			}
		};
		
		regForm.add(name);
		regForm.add(email);
		regForm.add(password);
		regForm.add(dob);
		regForm.add(gender);
		regForm.add(address);
		regForm.add(membership);
		regForm.add(register);
		add(getErrorWindow());
		add(regForm);
		
	}
	
	
	private List<? extends String> getSubChoices() {
		List<String>subList=Arrays.asList(new String[]{"1 Month","2 Months","3 Months","4 Months","5 Months","6 Months","1 Year"});
		return subList;
	}

	private DatePicker getDatePicker() {
		DatePicker datePicker= new DatePicker();
		datePicker.setDateFormat("dd-mm-yy");
		datePicker.setChangeYear(true);
		datePicker.setChangeMonth(true);
		//datePicker.setShowAnim(ShowAnim.SLIDE_DOWN);
		
		return datePicker;
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
