package com.adventurekit.node_coyote.booklistr;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by node_coyote on 4/2/17.
 */

public final class QueryUtility {

    // A log helper
    private static final String LOG_TAG = QueryUtility.class.getSimpleName();

    /**
     * This class is meant to hold static variables and methods,
     * accessed directly from {@link QueryUtility} class itself
     * and not an instance. Constructor is not to be used
     */
    private QueryUtility() {
        // Remain empty
    }

    /**
     * Helper method checks if a URL is legit
     * @param stringUrl
     * @return
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error creating URL", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        // An empty String to hold a response, most likely JSON
        String response  = "";

        // If empty, leave early
        if (url == null){
            return response;
        }

        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(15000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            /** Check if Request is successful indicated by 200 responseCode.
             * If successful, get the input stream and begin to read from it.
             */
            if (httpURLConnection.getResponseCode() == 200){
                inputStream = httpURLConnection.getInputStream();
                response = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code " + httpURLConnection.getResponseCode());
            }
        } catch (IOException e){
            Log.e(LOG_TAG, "Problem parsing response");
        } finally {
            if (httpURLConnection != null){
                httpURLConnection.disconnect();
            }
            if (inputStream != null){
                inputStream.close();
            }
        }
        return response;
    }

    /**
     * Use a StringBuilder to assembled the output into usable data
     * @param inputStream
     * @return
     * @throws IOException
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null){
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
}
