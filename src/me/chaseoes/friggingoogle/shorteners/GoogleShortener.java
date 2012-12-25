package me.chaseoes.friggingoogle.shorteners;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class GoogleShortener {

    private String googUrl = "https://www.googleapis.com/urlshortener/v1/url?shortUrl=http://goo.gl/fbsS&key=AIzaSyAyL_2Nf11eKdWjGWSqix5QbO-cPjKJwYE";
    String longUrl;

    public GoogleShortener(String u) throws MalformedURLException {
        longUrl = u;
    }

    public String shorten() {
        String shortURL = null;

        try {
            URLConnection conn = new URL(googUrl).openConnection();
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json");
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write("{\"longUrl\":\"" + longUrl + "\"}");
            wr.flush();

            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;

            while ((line = rd.readLine()) != null) {
                if (line.indexOf("id") > -1) {
                    shortURL = line.substring(8, line.length() - 2);
                    break;
                }
            }

            wr.close();
            rd.close();
        } catch (MalformedURLException ex) {

        } catch (IOException ex) {

        }

        return shortURL;
    }

}
