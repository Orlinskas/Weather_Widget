package com.orlinskas.weatherwidget.specification;

import com.orlinskas.weatherwidget.Country;

public class CountrySpecification implements Specification<Country>, SqlSpecification {
    private String countryName;
    private String countryCode;

    public CountrySpecification(String countryName, String countryCode) {
        this.countryName = countryName;
        this.countryCode = countryCode;
    }

    @Override
    public boolean specified(Country object) {
        return object.equals(new Country(countryCode, countryName));
    }

    @Override
    public String toSqlQuery() {
        return String.format(
                "SELECT * FROM %1$s WHERE `%2$s` = %3$d';"//,
                //NewsTable.TABLE_NAME,
                //NewsTable.Fields.ID,
                //id
        );
    }
}
