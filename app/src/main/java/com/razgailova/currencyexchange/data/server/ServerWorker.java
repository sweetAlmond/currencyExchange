package com.razgailova.currencyexchange.data.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Катерина on 22.11.2017.
 */

public class ServerWorker {

    public String getXMLFromUrl(String url, String charset) throws Exception {
        BufferedReader br = null;
        try {
            HttpURLConnection conn = (HttpURLConnection)(new URL(url)).openConnection();
            conn.setConnectTimeout(60 * 1000);
            br = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));

            String line;
            final StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if (br != null) br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
