package com.vini.lms.web.pages.util;

import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.HeadersToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.extensions.markup.html.repeater.data.table.NavigationToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.NoRecordsToolbar;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.OddEvenItem;
import org.apache.wicket.model.IModel;

public class BookDataTable<T> extends DataTable<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BookDataTable(String id, IColumn<T>[] columns, ISortableDataProvider<T> dataProvider, int rowsPerPage) {
		super(id, columns, dataProvider, rowsPerPage);
		
		addTopToolbar(new NavigationToolbar(this) {
			private static final long serialVersionUID = 1L;

			@Override
			protected PagingNavigator newPagingNavigator(String navigatorId,
					DataTable<?> table) {
				PagingNavigator pagingNavigator = super.newPagingNavigator(
						navigatorId, table);
				pagingNavigator.add(new SimpleAttributeModifier("style",
						"float:left;"));
				return pagingNavigator;
			}
		});
		addTopToolbar(new HeadersToolbar(this, dataProvider));
		addBottomToolbar(new NoRecordsToolbar(this));
	}
	
	@Override
	protected Item<T> newRowItem(String id, int index, IModel<T> model) {
		return new OddEvenItem<T>(id, index, model);
	}

}
