package com.orlinskas.weatherwidget.repository;

import com.orlinskas.weatherwidget.specification.Specification;

import java.util.List;

public interface Repository<T> {

        void add(T object);

        void update(T object);

        void remove(T object);

        List<T> query(Specification specification);

}
