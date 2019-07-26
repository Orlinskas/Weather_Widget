package com.orlinskas.weatherwidget.json;

import android.support.test.runner.AndroidJUnit4;

import com.orlinskas.weatherwidget.City;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

@RunWith(AndroidJUnit4.class)
public class JSONLineParserTest {
    private ArrayList<City> cities = new ArrayList<>();
    private City city;
    private String json;

    @Before
    public void setUp() {
        city = new City(707860, "Hurzuf", "UA", 34.283333, 44.549999);

        json = "\uFEFF[\n" +
                "  {\n" +
                "    \"id\": 707860,\n" +
                "    \"name\": \"Hurzuf\",\n" +
                "    \"country\": \"UA\",\n" +
                "    \"coord\": {\n" +
                "      \"lon\": 34.283333,\n" +
                "      \"lat\": 44.549999\n" +
                "    }\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 519188,\n" +
                "    \"name\": \"Novinki\",\n" +
                "    \"country\": \"RU\",\n" +
                "    \"coord\": {\n" +
                "      \"lon\": 37.666668,\n" +
                "      \"lat\": 55.683334\n" +
                "    }\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 1283378,\n" +
                "    \"name\": \"Gorkhā\",\n" +
                "    \"country\": \"NP\",\n" +
                "    \"coord\": {\n" +
                "      \"lon\": 84.633331,\n" +
                "      \"lat\": 28\n" +
                "    }\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 1270260,\n" +
                "    \"name\": \"State of Haryāna\",\n" +
                "    \"country\": \"IN\",\n" +
                "    \"coord\": {\n" +
                "      \"lon\": 76,\n" +
                "      \"lat\": 29\n" +
                "    }\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 708546,\n" +
                "    \"name\": \"Holubynka\",\n" +
                "    \"country\": \"UA\",\n" +
                "    \"coord\": {\n" +
                "      \"lon\": 33.900002,\n" +
                "      \"lat\": 44.599998\n" +
                "    }\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 1283710,\n" +
                "    \"name\": \"Bāgmatī Zone\",\n" +
                "    \"country\": \"NP\",\n" +
                "    \"coord\": {\n" +
                "      \"lon\": 85.416664,\n" +
                "      \"lat\": 28\n" +
                "    }\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 529334,\n" +
                "    \"name\": \"Mar’ina Roshcha\",\n" +
                "    \"country\": \"RU\",\n" +
                "    \"coord\": {\n" +
                "      \"lon\": 37.611111,\n" +
                "      \"lat\": 55.796391\n" +
                "    }\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 1269750,\n" +
                "    \"name\": \"Republic of India\",\n" +
                "    \"country\": \"IN\",\n" +
                "    \"coord\": {\n" +
                "      \"lon\": 77,\n" +
                "      \"lat\": 20\n" +
                "    }\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 1283240,\n" +
                "    \"name\": \"Kathmandu\",\n" +
                "    \"country\": \"NP\",\n" +
                "    \"coord\": {\n" +
                "      \"lon\": 85.316666,\n" +
                "      \"lat\": 27.716667\n" +
                "    }\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 703363,\n" +
                "    \"name\": \"Laspi\",\n" +
                "    \"country\": \"UA\",\n" +
                "    \"coord\": {\n" +
                "      \"lon\": 33.733334,\n" +
                "      \"lat\": 44.416668\n" +
                "    }\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 3632308,\n" +
                "    \"name\": \"Merida\",\n" +
                "    \"country\": \"VE\",\n" +
                "    \"coord\": {\n" +
                "      \"lon\": -71.144997,\n" +
                "      \"lat\": 8.598333\n" +
                "    }\n" +
                "  }\n" +
                "]";
    }

    @Test
    public void parse() {
        JSONLineParser jsonLineParser = new JSONLineParser();
        cities = jsonLineParser.parse(json);
        Assert.assertTrue(isCorrectParse(cities));
    }

    private boolean isCorrectParse(ArrayList<City> cities) {
        return cities.size() == 11 & isFirstCityCorrect(cities.get(0));
    }

    private boolean isFirstCityCorrect(City parsingCity) {
        return parsingCity.equals(city);
    }
}