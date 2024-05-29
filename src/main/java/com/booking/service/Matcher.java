package com.booking.service;

@FunctionalInterface
public interface Matcher <T>{
    boolean matches(T item, String searchValue);
}