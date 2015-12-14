package com.vini.util;

import java.io.Serializable;

import org.apache.wicket.markup.html.navigation.paging.IPageable;
import org.apache.wicket.model.IModel;

public class PageLinkIncrementCssModel implements IModel<String>, Serializable {
    private static final long serialVersionUID = 1L;
    
    protected final IPageable pageable;
    private final long pageNumber;
    
    public PageLinkIncrementCssModel(IPageable pageable, long pageNumber) {
        this.pageable = pageable;
        this.pageNumber = pageNumber;
    }
    
    @Override
    public String getObject() {
        return isEnabled() ? "" : "disabled";
    }
    
    @Override
    public void setObject(String object) {
        // Empty methods
    }
    
    @Override
    public void detach() {
        // Empty methods
    }
    
    public boolean isEnabled() {
        if (pageNumber < 0) {
            return !isFirst();
        } else {
            return !isLast();
        }
    }
    
    public boolean isFirst() {
        return pageable.getCurrentPage() <= 0;
    }
    
    public boolean isLast() {
        return pageable.getCurrentPage() >=
                (pageable.getPageCount() - 1);
    }
}
