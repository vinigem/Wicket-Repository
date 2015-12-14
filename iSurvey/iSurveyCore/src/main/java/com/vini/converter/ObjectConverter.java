package com.vini.converter;

import java.util.List;

public interface ObjectConverter<S, T> {
    
    T convertSource(S object);
    
    S convertTarget(T object);
    
    List<T> convertSources(List<S> object);
    
}
