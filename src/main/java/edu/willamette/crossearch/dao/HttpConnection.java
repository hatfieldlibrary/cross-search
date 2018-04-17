package edu.willamette.crossearch.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpConnection {

    public StringBuffer request(URL url) {

        StringBuffer content = new StringBuffer();
        try {
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            con.disconnect();
            return content;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
