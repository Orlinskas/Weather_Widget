package com.orlinskas.weatherwidget.repository;

import com.orlinskas.weatherwidget.specification.SqlSpecification;

import java.util.ArrayList;

public interface Repository<T> {

        void add(T object);

        void update(T object);

        void remove(T object);

        ArrayList<T> query(SqlSpecification sqlSpecification);
}
