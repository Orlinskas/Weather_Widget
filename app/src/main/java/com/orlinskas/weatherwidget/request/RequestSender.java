package com.orlinskas.weatherwidget.request;

import java.net.HttpURLConnection;

public class RequestSender {
   // public String request(Request request) {
   //         HttpURLConnection httpURLConnection = null;
//
   //         try {
   //             httpURLConnection = (HttpURLConnection) generateURL(locationID).openConnection();
   //             InputStream in = httpURLConnection.getInputStream();
   //             Scanner scanner = new Scanner(in);
   //             scanner.useDelimiter("\\A");
//
   //             boolean hasInput = scanner.hasNext();
//
   //             if (hasInput) {
   //                 return scanner.next();
   //             } else {
   //                 return null;
   //             }
   //         } catch (IOException e) {
   //             e.printStackTrace();
   //             database.open();
   //             database.insertLastErrorText(Constants.ERROR_CONNECTION, WeatherRequestSenderAccuWeather.class);
   //             database.close();
   //         } finally {
   //             httpURLConnection.disconnect();
   //         }
//
   //         return null;
   //     }
//
   //     private static URL generateURL(String locationID){
   //         Uri buildRequest = Uri.parse(Constants.OPENWEATHERMAP_COM + Constants.OPENWEATHERMAP_FORECAST_5day)
   //                 .buildUpon()
   //                 .appendQueryParameter("id", locationID)
   //                 .appendQueryParameter("APPID", Constants.OPENWEATHERMAP_API_KEY)
   //                 .appendQueryParameter("units", "metric")
   //                 .build();
//
   //         URL requestOpenWeather = null;
   //         try {
   //             requestOpenWeather = new URL(buildRequest.toString());
   //         } catch (MalformedURLException e) {
   //             e.printStackTrace();
   //             DatabaseAdapter database = new DatabaseAdapter(AppContext.getContext());
   //             database.open();
   //             database.insertLastErrorText(Constants.ERROR_URL, WeatherRequestSenderOpenWeather.class);
   //             database.close();
   //         }
//
   //         return requestOpenWeather;
   //     }
}

