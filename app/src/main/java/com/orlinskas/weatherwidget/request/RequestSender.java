package com.orlinskas.weatherwidget.request;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class RequestSender {
    public String send(Request request) {
    HttpURLConnection httpURLConnection = null;
    URL url = RequestURLGenerator.generate(request);

    try {
        httpURLConnection = (HttpURLConnection) url.openConnection();
        InputStream in = httpURLConnection.getInputStream();
        Scanner scanner = new Scanner(in);
        scanner.useDelimiter("\\A");

        boolean hasInput = scanner.hasNext();
        if (hasInput) {
            return scanner.next();
        } else {
            return null;
        }
    } catch (IOException e) {
        e.printStackTrace();
    } finally { if (httpURLConnection != null) {
        httpURLConnection.disconnect();
    }
    }
    return null;
  }
}

