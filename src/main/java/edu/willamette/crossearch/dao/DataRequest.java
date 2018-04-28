package edu.willamette.crossearch.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.willamette.crossearch.model.existdb.CombinedResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DataRequest {

    public StringBuffer getData(String queryUrl) {

        URL url = null;
        try {
            url = new URL(queryUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        StringBuffer response = request(url);
        return response;
    }

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
