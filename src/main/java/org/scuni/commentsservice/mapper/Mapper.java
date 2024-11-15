package org.scuni.commentsservice.mapper;

public interface Mapper<F, T> {

    T map(F object);

}
