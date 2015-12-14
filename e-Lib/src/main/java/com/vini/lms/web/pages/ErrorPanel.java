package com.vini.lms.web.pages;

import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;

public class ErrorPanel extends Panel {

	private static final long serialVersionUID = 1L;
	private FeedbackPanel feedbackPanel;	

	
	public ErrorPanel(String id) {
		super(id);
		feedbackPanel = new FeedbackPanel("feedBackPanel");
		feedbackPanel.setOutputMarkupId(true);
		add(feedbackPanel);
		
		/*add(new AjaxLink<String>("ok"){
			*//**
			 * 
			 *//*
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
			     ModalWindow modalWindow = (ModalWindow)this.getParent().getParent();
			     if(modalWindow != null){
			    	 modalWindow.close(target);
			     }
			}
		});*/
		 
	}
	
	/**
	 * @return the feedbackPanel
	 */
	public FeedbackPanel getFeedbackPanel() {
		return feedbackPanel;
	}

	/**
	 * @param feedbackPanel the feedbackPanel to set
	 */
	public void setFeedbackPanel(FeedbackPanel feedbackPanel) {
		this.feedbackPanel = feedbackPanel;
	}
	
	
}
