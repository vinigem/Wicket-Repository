package com.vini.util;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.LoopItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.markup.html.navigation.paging.IPageable;
import org.apache.wicket.markup.html.navigation.paging.IPagingLabelProvider;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigation;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigationIncrementLink;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigationLink;
import org.apache.wicket.markup.html.panel.Panel;

public class CustomPagingNavigator extends Panel{
    private static final long serialVersionUID = 1L;
    
    public static final String NAVIGATION_ID = "navigation";
    public static final List<Integer> DEFAULT_ITEMS_PER_PAGE_VALUES = Arrays.asList(5, 25, 50);
    
    private PagingNavigation pagingNavigation;
    private final PageableListView<?> listView;
    private final IPagingLabelProvider labelProvider;
    private final List<Integer> itemsPerPageValues;
    private WebMarkupContainer pagingLinksContainer;
    private boolean showItemsPerPage;
    
    public CustomPagingNavigator(final String id, final PageableListView<?> listView, boolean showItemsPerPage) {
        this(id, listView, null, DEFAULT_ITEMS_PER_PAGE_VALUES, showItemsPerPage);
    }
    
    public CustomPagingNavigator(final String id, final PageableListView<?> listView, List<Integer> itemsPerPageValues, boolean showItemsPerPage) {
        this(id, listView, null, itemsPerPageValues, showItemsPerPage);
    }
    
    public CustomPagingNavigator(final String id, final PageableListView<?> listView, final IPagingLabelProvider labelProvider, boolean showItemsPerPage) {
        this(id, listView, labelProvider, DEFAULT_ITEMS_PER_PAGE_VALUES, showItemsPerPage);
    }
    
    public CustomPagingNavigator(final String id, final PageableListView<?> listView, final IPagingLabelProvider labelProvider,
            List<Integer> itemsPerPageValues, boolean showItemsPerPage) {
        super(id);
        this.listView = listView;
        this.labelProvider = labelProvider;
        this.itemsPerPageValues = itemsPerPageValues;
        this.showItemsPerPage = showItemsPerPage;
        
        addContainerWithPagingLinks();
        addLinksChangingItemsPerPageNumber();
    }
    
    @Override
    public boolean isVisible() {
        return listView.getViewSize() > 0;
    }
    
    private void addContainerWithPagingLinks() {
        
        pagingLinksContainer = new WebMarkupContainer("pagingLinksContainer") {
            private static final long serialVersionUID = 1L;
            
            @Override
            public boolean isVisible() {
                return listView.getPageCount() > 1;
            }
        };
        
        pagingNavigation = newNavigation(listView, labelProvider);
        pagingLinksContainer.add(pagingNavigation);
        
        // Add additional page links
        pagingLinksContainer.add(newPagingNavigationLink("first", listView, 0).add(new TitleAppender("PagingNavigator.first")));
        pagingLinksContainer.add(newPagingNavigationIncrementLink("prev", listView, -1).add(new TitleAppender("PagingNavigator.previous")));
        pagingLinksContainer.add(newPagingNavigationIncrementLink("next", listView, 1).add(new TitleAppender("PagingNavigator.next")));
        pagingLinksContainer.add(newPagingNavigationLink("last", listView, -1).add(new TitleAppender("PagingNavigator.last")));
        
        add(pagingLinksContainer);
    }
    
    protected PagingNavigation newNavigation(final IPageable pageable, final IPagingLabelProvider labelProvider) {
        return new PagingNavigation(NAVIGATION_ID, pageable, labelProvider){
            private static final long serialVersionUID = 1L;
            
            @Override
            protected LoopItem newItem(int iteration) {
                LoopItem item = super.newItem(iteration);
                // add css for enable/disable link
                long pageIndex = getStartIndex() + iteration;
                item.add(new AttributeModifier("class", new PageLinkCssModel(pageable, pageIndex, "disabled")));
                return item;
            }
        };
    }
    
    protected AbstractLink newPagingNavigationIncrementLink(String id, IPageable pageable, int increment) {
        return new PagingNavigationIncrementLink<Void>(id, pageable, increment){
            private static final long serialVersionUID = 1L;
            
            @Override
            protected void onConfigure() {
                if(isLinkEnabled()){
                    add(new AttributeModifier("class", "active"));
                }else{
                    add(new AttributeModifier("class", "disabled"));
                }
                super.onConfigure();
            }
        };
    }
    
    protected AbstractLink newPagingNavigationLink(String id, IPageable pageable, int pageNumber) {
        return new PagingNavigationLink<Void>(id, pageable, pageNumber){
            private static final long serialVersionUID = 1L;
            
            @Override
            protected void onConfigure() {
                if(isLinkEnabled()){
                    add(new AttributeModifier("class", "active"));
                }else{
                    add(new AttributeModifier("class", "disabled"));
                }
                super.onConfigure();
            }
        };
    }
    
    private void addLinksChangingItemsPerPageNumber() {
        ListView<Integer> itemsPerPageList = new ListView<Integer>("itemsPerPage", itemsPerPageValues) {
            private static final long serialVersionUID = 1L;
            
            @Override
            protected void populateItem(ListItem<Integer> item) {
                Link<Void> itemPerPageLink = new ItemPerPageLink<Void>("itemPerPageLink", listView, pagingLinksContainer, item.getModelObject());
                itemPerPageLink.add(new Label("itemsValue", item.getModel()));
                item.add(itemPerPageLink);
            }
        };
        itemsPerPageList.setVisible(this.showItemsPerPage);
        add(new Label("itemPerPageLabel", "Items per page: ").setVisible(this.showItemsPerPage));
        add(itemsPerPageList);
    }
    
    public final PagingNavigation getPagingNavigation() {
        return pagingNavigation;
    }
    
    private final class TitleAppender extends Behavior {
        private static final long serialVersionUID = 1L;
        
        private final String resourceKey;
        
        public TitleAppender(String resourceKey) {
            this.resourceKey = resourceKey;
        }
        
        @Override
        public void onComponentTag(Component component, ComponentTag tag) {
            tag.put("title", CustomPagingNavigator.this.getString(resourceKey));
        }
    }
}
