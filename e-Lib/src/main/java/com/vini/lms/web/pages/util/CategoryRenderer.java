package com.vini.lms.web.pages.util;

import org.apache.wicket.markup.html.form.IChoiceRenderer;

public class CategoryRenderer implements IChoiceRenderer<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Object getDisplayValue(String object) {
		return object;
	}

	@Override
	public String getIdValue(String object, int index) {
		return object.toUpperCase();
	}

}
