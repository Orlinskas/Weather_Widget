package com.orlinskas.weatherwidget.response;

import android.support.test.runner.AndroidJUnit4;

import com.orlinskas.weatherwidget.Weather;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class JSONResponseParserToWeatherTest {
    private String json;

    @Before
    public void setUp() {
        json = "{\"cod\":\"200\",\"message\":0.007,\"cnt\":40,\"list\":[{\"dt\":1564887600,\"main\":{\"temp\":15.41,\"temp_min\":14.97,\"temp_max\":15.41,\"pressure\":1003.76,\"sea_level\":1003.76,\"grnd_level\":984.35,\"humidity\":71,\"temp_kf\":0.44},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04d\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":2.72,\"deg\":25.351},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2019-08-04 03:00:00\"},{\"dt\":1564898400,\"main\":{\"temp\":17.18,\"temp_min\":16.85,\"temp_max\":17.18,\"pressure\":1002.61,\"sea_level\":1002.61,\"grnd_level\":983.51,\"humidity\":64,\"temp_kf\":0.33},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04d\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":3.99,\"deg\":25.66},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2019-08-04 06:00:00\"},{\"dt\":1564909200,\"main\":{\"temp\":16.72,\"temp_min\":16.5,\"temp_max\":16.72,\"pressure\":1000.81,\"sea_level\":1000.81,\"grnd_level\":982.33,\"humidity\":77,\"temp_kf\":0.22},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":3.67,\"deg\":41.652},\"rain\":{\"3h\":1.938},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2019-08-04 09:00:00\"},{\"dt\":1564920000,\"main\":{\"temp\":15.42,\"temp_min\":15.31,\"temp_max\":15.42,\"pressure\":1000.02,\"sea_level\":1000.02,\"grnd_level\":981.72,\"humidity\":82,\"temp_kf\":0.11},\"weather\":[{\"id\":501,\"main\":\"Rain\",\"description\":\"moderate rain\",\"icon\":\"10d\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":6.35,\"deg\":11.007},\"rain\":{\"3h\":5.812},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2019-08-04 12:00:00\"},{\"dt\":1564930800,\"main\":{\"temp\":16.85,\"temp_min\":16.85,\"temp_max\":16.85,\"pressure\":999.42,\"sea_level\":999.42,\"grnd_level\":980.5,\"humidity\":71,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":4.3,\"deg\":351.47},\"rain\":{\"3h\":0.5},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2019-08-04 15:00:00\"},{\"dt\":1564941600,\"main\":{\"temp\":14.25,\"temp_min\":14.25,\"temp_max\":14.25,\"pressure\":1001.83,\"sea_level\":1001.83,\"grnd_level\":982.24,\"humidity\":77,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10n\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":6.87,\"deg\":340.639},\"rain\":{\"3h\":0.062},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2019-08-04 18:00:00\"},{\"dt\":1564952400,\"main\":{\"temp\":12.59,\"temp_min\":12.59,\"temp_max\":12.59,\"pressure\":1004.06,\"sea_level\":1004.06,\"grnd_level\":984.43,\"humidity\":76,\"temp_kf\":0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04n\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":5.42,\"deg\":330.519},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2019-08-04 21:00:00\"},{\"dt\":1564963200,\"main\":{\"temp\":10.45,\"temp_min\":10.45,\"temp_max\":10.45,\"pressure\":1006.18,\"sea_level\":1006.18,\"grnd_level\":986.55,\"humidity\":81,\"temp_kf\":0},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04n\"}],\"clouds\":{\"all\":84},\"wind\":{\"speed\":4.64,\"deg\":312.363},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2019-08-05 00:00:00\"},{\"dt\":1564974000,\"main\":{\"temp\":9.15,\"temp_min\":9.15,\"temp_max\":9.15,\"pressure\":1007.82,\"sea_level\":1007.82,\"grnd_level\":988.34,\"humidity\":84,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":3.67,\"deg\":307.192},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2019-08-05 03:00:00\"},{\"dt\":1564984800,\"main\":{\"temp\":14.65,\"temp_min\":14.65,\"temp_max\":14.65,\"pressure\":1008.61,\"sea_level\":1008.61,\"grnd_level\":989.49,\"humidity\":61,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":{\"all\":1},\"wind\":{\"speed\":3.95,\"deg\":313.155},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2019-08-05 06:00:00\"},{\"dt\":1564995600,\"main\":{\"temp\":18.46,\"temp_min\":18.46,\"temp_max\":18.46,\"pressure\":1008.81,\"sea_level\":1008.81,\"grnd_level\":989.68,\"humidity\":47,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":{\"all\":4},\"wind\":{\"speed\":4.97,\"deg\":286.156},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2019-08-05 09:00:00\"},{\"dt\":1565006400,\"main\":{\"temp\":18.68,\"temp_min\":18.68,\"temp_max\":18.68,\"pressure\":1007.82,\"sea_level\":1007.82,\"grnd_level\":988.88,\"humidity\":50,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"clouds\":{\"all\":16},\"wind\":{\"speed\":5.63,\"deg\":281.124},\"rain\":{\"3h\":0.75},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2019-08-05 12:00:00\"},{\"dt\":1565017200,\"main\":{\"temp\":17.15,\"temp_min\":17.15,\"temp_max\":17.15,\"pressure\":1008.04,\"sea_level\":1008.04,\"grnd_level\":989.21,\"humidity\":58,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"clouds\":{\"all\":99},\"wind\":{\"speed\":4.78,\"deg\":292.387},\"rain\":{\"3h\":0.5},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2019-08-05 15:00:00\"},{\"dt\":1565028000,\"main\":{\"temp\":13.26,\"temp_min\":13.26,\"temp_max\":13.26,\"pressure\":1009.9,\"sea_level\":1009.9,\"grnd_level\":990.64,\"humidity\":70,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10n\"}],\"clouds\":{\"all\":88},\"wind\":{\"speed\":3.83,\"deg\":282.118},\"rain\":{\"3h\":0.062},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2019-08-05 18:00:00\"},{\"dt\":1565038800,\"main\":{\"temp\":11.15,\"temp_min\":11.15,\"temp_max\":11.15,\"pressure\":1011.09,\"sea_level\":1011.09,\"grnd_level\":991.86,\"humidity\":80,\"temp_kf\":0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04n\"}],\"clouds\":{\"all\":92},\"wind\":{\"speed\":2.92,\"deg\":299.436},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2019-08-05 21:00:00\"},{\"dt\":1565049600,\"main\":{\"temp\":10.56,\"temp_min\":10.56,\"temp_max\":10.56,\"pressure\":1012.06,\"sea_level\":1012.06,\"grnd_level\":992.7,\"humidity\":82,\"temp_kf\":0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04n\"}],\"clouds\":{\"all\":96},\"wind\":{\"speed\":2.54,\"deg\":282.517},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2019-08-06 00:00:00\"},{\"dt\":1565060400,\"main\":{\"temp\":10.06,\"temp_min\":10.06,\"temp_max\":10.06,\"pressure\":1013.19,\"sea_level\":1013.19,\"grnd_level\":993.71,\"humidity\":84,\"temp_kf\":0},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04d\"}],\"clouds\":{\"all\":59},\"wind\":{\"speed\":2.35,\"deg\":285.563},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2019-08-06 03:00:00\"},{\"dt\":1565071200,\"main\":{\"temp\":16.05,\"temp_min\":16.05,\"temp_max\":16.05,\"pressure\":1014.06,\"sea_level\":1014.06,\"grnd_level\":994.83,\"humidity\":60,\"temp_kf\":0},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04d\"}],\"clouds\":{\"all\":57},\"wind\":{\"speed\":3.82,\"deg\":306.024},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2019-08-06 06:00:00\"},{\"dt\":1565082000,\"main\":{\"temp\":19.35,\"temp_min\":19.35,\"temp_max\":19.35,\"pressure\":1014.45,\"sea_level\":1014.45,\"grnd_level\":995.51,\"humidity\":44,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":3.82,\"deg\":304.522},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2019-08-06 09:00:00\"},{\"dt\":1565092800,\"main\":{\"temp\":19.36,\"temp_min\":19.36,\"temp_max\":19.36,\"pressure\":1014.55,\"sea_level\":1014.55,\"grnd_level\":995.84,\"humidity\":46,\"temp_kf\":0},\"weather\":[{\"id\":802,\"main\":\"Clouds\",\"description\":\"scattered clouds\",\"icon\":\"03d\"}],\"clouds\":{\"all\":31},\"wind\":{\"speed\":4.08,\"deg\":288.861},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2019-08-06 12:00:00\"},{\"dt\":1565103600,\"main\":{\"temp\":19.65,\"temp_min\":19.65,\"temp_max\":19.65,\"pressure\":1014.93,\"sea_level\":1014.93,\"grnd_level\":996.06,\"humidity\":48,\"temp_kf\":0},\"weather\":[{\"id\":802,\"main\":\"Clouds\",\"description\":\"scattered clouds\",\"icon\":\"03d\"}],\"clouds\":{\"all\":38},\"wind\":{\"speed\":3.83,\"deg\":285.607},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2019-08-06 15:00:00\"},{\"dt\":1565114400,\"main\":{\"temp\":14.58,\"temp_min\":14.58,\"temp_max\":14.58,\"pressure\":1016.45,\"sea_level\":1016.45,\"grnd_level\":997.38,\"humidity\":65,\"temp_kf\":0},\"weather\":[{\"id\":801,\"main\":\"Clouds\",\"description\":\"few clouds\",\"icon\":\"02n\"}],\"clouds\":{\"all\":19},\"wind\":{\"speed\":2.26,\"deg\":291.637},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2019-08-06 18:00:00\"},{\"dt\":1565125200,\"main\":{\"temp\":13.02,\"temp_min\":13.02,\"temp_max\":13.02,\"pressure\":1017.2,\"sea_level\":1017.2,\"grnd_level\":997.89,\"humidity\":68,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":1.2,\"deg\":251.746},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2019-08-06 21:00:00\"},{\"dt\":1565136000,\"main\":{\"temp\":11.75,\"temp_min\":11.75,\"temp_max\":11.75,\"pressure\":1017.19,\"sea_level\":1017.19,\"grnd_level\":997.93,\"humidity\":71,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":1.92,\"deg\":220.721},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2019-08-07 00:00:00\"},{\"dt\":1565146800,\"main\":{\"temp\":11.92,\"temp_min\":11.92,\"temp_max\":11.92,\"pressure\":1017.54,\"sea_level\":1017.54,\"grnd_level\":998.14,\"humidity\":71,\"temp_kf\":0},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04d\"}],\"clouds\":{\"all\":66},\"wind\":{\"speed\":2.36,\"deg\":205.372},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2019-08-07 03:00:00\"},{\"dt\":1565157600,\"main\":{\"temp\":17.99,\"temp_min\":17.99,\"temp_max\":17.99,\"pressure\":1017.3,\"sea_level\":1017.3,\"grnd_level\":998.16,\"humidity\":54,\"temp_kf\":0},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04d\"}],\"clouds\":{\"all\":80},\"wind\":{\"speed\":3.27,\"deg\":205.091},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2019-08-07 06:00:00\"},{\"dt\":1565168400,\"main\":{\"temp\":22.94,\"temp_min\":22.94,\"temp_max\":22.94,\"pressure\":1016.85,\"sea_level\":1016.85,\"grnd_level\":997.77,\"humidity\":41,\"temp_kf\":0},\"weather\":[{\"id\":802,\"main\":\"Clouds\",\"description\":\"scattered clouds\",\"icon\":\"03d\"}],\"clouds\":{\"all\":39},\"wind\":{\"speed\":4.15,\"deg\":199.099},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2019-08-07 09:00:00\"},{\"dt\":1565179200,\"main\":{\"temp\":19.92,\"temp_min\":19.92,\"temp_max\":19.92,\"pressure\":1015.78,\"sea_level\":1015.78,\"grnd_level\":996.63,\"humidity\":59,\"temp_kf\":0},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04d\"}],\"clouds\":{\"all\":68},\"wind\":{\"speed\":3.65,\"deg\":179.497},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2019-08-07 12:00:00\"},{\"dt\":1565190000,\"main\":{\"temp\":21.96,\"temp_min\":21.96,\"temp_max\":21.96,\"pressure\":1014.58,\"sea_level\":1014.58,\"grnd_level\":995.41,\"humidity\":66,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"clouds\":{\"all\":68},\"wind\":{\"speed\":4,\"deg\":185.384},\"rain\":{\"3h\":1.125},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2019-08-07 15:00:00\"},{\"dt\":1565200800,\"main\":{\"temp\":16.92,\"temp_min\":16.92,\"temp_max\":16.92,\"pressure\":1014.22,\"sea_level\":1014.22,\"grnd_level\":995.39,\"humidity\":91,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10n\"}],\"clouds\":{\"all\":40},\"wind\":{\"speed\":4.19,\"deg\":185.119},\"rain\":{\"3h\":1.313},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2019-08-07 18:00:00\"},{\"dt\":1565211600,\"main\":{\"temp\":16.22,\"temp_min\":16.22,\"temp_max\":16.22,\"pressure\":1014.43,\"sea_level\":1014.43,\"grnd_level\":995.41,\"humidity\":91,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":3.55,\"deg\":211.575},\"rain\":{\"3h\":0.375},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2019-08-07 21:00:00\"},{\"dt\":1565222400,\"main\":{\"temp\":16.47,\"temp_min\":16.47,\"temp_max\":16.47,\"pressure\":1014.19,\"sea_level\":1014.19,\"grnd_level\":995.25,\"humidity\":90,\"temp_kf\":0},\"weather\":[{\"id\":801,\"main\":\"Clouds\",\"description\":\"few clouds\",\"icon\":\"02n\"}],\"clouds\":{\"all\":11},\"wind\":{\"speed\":2.27,\"deg\":247.669},\"rain\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2019-08-08 00:00:00\"},{\"dt\":1565233200,\"main\":{\"temp\":16.82,\"temp_min\":16.82,\"temp_max\":16.82,\"pressure\":1014.53,\"sea_level\":1014.53,\"grnd_level\":995.72,\"humidity\":89,\"temp_kf\":0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04d\"}],\"clouds\":{\"all\":91},\"wind\":{\"speed\":2.24,\"deg\":247.38},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2019-08-08 03:00:00\"},{\"dt\":1565244000,\"main\":{\"temp\":22.47,\"temp_min\":22.47,\"temp_max\":22.47,\"pressure\":1014.79,\"sea_level\":1014.79,\"grnd_level\":995.88,\"humidity\":71,\"temp_kf\":0},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04d\"}],\"clouds\":{\"all\":71},\"wind\":{\"speed\":2,\"deg\":230.183},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2019-08-08 06:00:00\"},{\"dt\":1565254800,\"main\":{\"temp\":27.42,\"temp_min\":27.42,\"temp_max\":27.42,\"pressure\":1014.64,\"sea_level\":1014.64,\"grnd_level\":995.81,\"humidity\":51,\"temp_kf\":0},\"weather\":[{\"id\":802,\"main\":\"Clouds\",\"description\":\"scattered clouds\",\"icon\":\"03d\"}],\"clouds\":{\"all\":30},\"wind\":{\"speed\":3.98,\"deg\":234.489},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2019-08-08 09:00:00\"},{\"dt\":1565265600,\"main\":{\"temp\":29.07,\"temp_min\":29.07,\"temp_max\":29.07,\"pressure\":1013.32,\"sea_level\":1013.32,\"grnd_level\":994.64,\"humidity\":46,\"temp_kf\":0},\"weather\":[{\"id\":801,\"main\":\"Clouds\",\"description\":\"few clouds\",\"icon\":\"02d\"}],\"clouds\":{\"all\":15},\"wind\":{\"speed\":4.32,\"deg\":240.621},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2019-08-08 12:00:00\"},{\"dt\":1565276400,\"main\":{\"temp\":28.86,\"temp_min\":28.86,\"temp_max\":28.86,\"pressure\":1012.1,\"sea_level\":1012.1,\"grnd_level\":993.42,\"humidity\":52,\"temp_kf\":0},\"weather\":[{\"id\":802,\"main\":\"Clouds\",\"description\":\"scattered clouds\",\"icon\":\"03d\"}],\"clouds\":{\"all\":26},\"wind\":{\"speed\":3.03,\"deg\":219.847},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2019-08-08 15:00:00\"},{\"dt\":1565287200,\"main\":{\"temp\":23.4,\"temp_min\":23.4,\"temp_max\":23.4,\"pressure\":1011.93,\"sea_level\":1011.93,\"grnd_level\":993.24,\"humidity\":65,\"temp_kf\":0},\"weather\":[{\"id\":802,\"main\":\"Clouds\",\"description\":\"scattered clouds\",\"icon\":\"03n\"}],\"clouds\":{\"all\":33},\"wind\":{\"speed\":3.78,\"deg\":202.249},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2019-08-08 18:00:00\"},{\"dt\":1565298000,\"main\":{\"temp\":23.16,\"temp_min\":23.16,\"temp_max\":23.16,\"pressure\":1012.43,\"sea_level\":1012.43,\"grnd_level\":993.4,\"humidity\":62,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":3},\"wind\":{\"speed\":6.43,\"deg\":221.433},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2019-08-08 21:00:00\"},{\"dt\":1565308800,\"main\":{\"temp\":21.84,\"temp_min\":21.84,\"temp_max\":21.84,\"pressure\":1011.87,\"sea_level\":1011.87,\"grnd_level\":992.96,\"humidity\":62,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":2},\"wind\":{\"speed\":5.94,\"deg\":229.031},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2019-08-09 00:00:00\"}],\"city\":{\"id\":706483,\"name\":\"Kharkiv\",\"coord\":{\"lat\":50,\"lon\":36.25},\"country\":\"UA\",\"timezone\":10800}}";
    }

    @Test
    public void parse() {
        ArrayList<Weather> weathers;
        JSONResponseParserToWeather parser = new JSONResponseParserToWeather();
        weathers = parser.parse(json);
        assertEquals(40, weathers.size());
    }
}