package com.vini.lms;

import org.apache.wicket.Request;
import org.apache.wicket.Response;
import org.apache.wicket.Session;
import org.apache.wicket.injection.web.InjectorHolder;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.https.HttpsConfig;
import org.apache.wicket.protocol.https.HttpsRequestCycleProcessor;
import org.apache.wicket.request.IRequestCycleProcessor;
import org.apache.wicket.request.target.coding.QueryStringUrlCodingStrategy;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

import com.vini.lms.web.pages.BasePage;
import com.vini.lms.web.pages.ContactPage;
import com.vini.lms.web.pages.LoginPage;
import com.vini.lms.web.pages.RegisterPage;
import com.vini.lms.web.pages.SearchPage;

public class WicketApplication extends WebApplication {

	@Override
	public Class<BasePage> getHomePage() {

		return BasePage.class; // return default page
	}
	
	@Override
	protected void init() {
		
		super.init();
		addComponentInstantiationListener(new SpringComponentInjector(this));
		InjectorHolder.getInjector().inject(this);
		
		mount(new QueryStringUrlCodingStrategy("home", BasePage.class));
		mount(new QueryStringUrlCodingStrategy("login", LoginPage.class));
		mount(new QueryStringUrlCodingStrategy("register", RegisterPage.class));
		mount(new QueryStringUrlCodingStrategy("search", SearchPage.class)); 
		mount(new QueryStringUrlCodingStrategy("contact", ContactPage.class));
		
	}
	
	@Override
	protected IRequestCycleProcessor newRequestCycleProcessor() {
		HttpsConfig config = new HttpsConfig();
        //Port 8080 is used for HTTP
        config.setHttpPort(8080);
        //Port 8443 is used for HTTPS
        config.setHttpsPort(8443);

        return new HttpsRequestCycleProcessor(config);
	}
	
	@Override
	public Session newSession(Request request, Response response) {
		return new WicketSession(request);
	}

}
