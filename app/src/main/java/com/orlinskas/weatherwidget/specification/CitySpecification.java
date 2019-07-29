package com.orlinskas.weatherwidget.specification;

import com.orlinskas.weatherwidget.City;

public class CitySpecification implements Specification<City>, SqlSpecification {
    @Override
    public boolean specified(City object) {
        return false;
    }

    @Override
    public String toSqlQuery() {
        return null;
    }
}
