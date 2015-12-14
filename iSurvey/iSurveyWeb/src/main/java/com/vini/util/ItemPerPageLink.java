package com.vini.util;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.PageableListView;

public class ItemPerPageLink<T> extends Link<T> {
    private static final long serialVersionUID = 1L;
    
    private final int itemsPerPage;
    private final PageableListView<?> listView;
    private final WebMarkupContainer pagingLinksContainer;
    
    public ItemPerPageLink(final String id, final PageableListView<?> listView, WebMarkupContainer pagingLinksContainer, int itemsPerPageValue) {
        super(id);
        this.listView = listView;
        this.pagingLinksContainer = pagingLinksContainer;
        this.itemsPerPage = itemsPerPageValue;
        setEnabled(itemsPerPageValue != listView.getItemsPerPage());
        
    }
    
    @Override
    public void onClick() {
        listView.setItemsPerPage(itemsPerPage);
        pagingLinksContainer.setVisible(listView.getPageCount() > 1);
    }
    
    @Override
    protected void onComponentTag(ComponentTag tag) {
        super.onComponentTag(tag);
        tag.put("title", itemsPerPage);
    }
    
}