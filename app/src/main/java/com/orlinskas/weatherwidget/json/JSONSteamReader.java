package com.orlinskas.weatherwidget.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class JSONSteamReader {
    public String read(InputStream is) {
        StringBuilder json = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
            int c;
            while ((c = bufferedReader.read()) != -1) {
                char charValue = (char) c;
                json.append(charValue);
            }
            bufferedReader.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return json.toString();
    }
}
