package ca.on.sl.comp208.lhahncomp208a3m1;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.util.Log;


import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import static ca.on.sl.comp208.lhahncomp208a3m1.ProviderContract.AUTHORITY;


public class Provider extends ContentProvider {

    public Provider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        MatrixCursor mc = new MatrixCursor(projection);
        MatrixCursor.RowBuilder rb;


        try {
            int uriType = ProviderContract.matcher.match(uri);
            URL url;
            HttpURLConnection conn;
            InputStream inputStream;
            Scanner scanner;
            StringBuilder builder;
            String data;
            Gson gson;
            Country[] countries;
            Response msg;
            switch (uriType) {
                case 1:
                    url = new URL("http://services.groupkt.com/country/get/all");
                    conn = (HttpURLConnection) url.openConnection();
                    inputStream = conn.getInputStream();

                    scanner = new Scanner(inputStream);
                    builder = new StringBuilder();
                    while (scanner.hasNext()) {
                        builder.append(scanner.nextLine());
                    }

                    data = builder.toString();
                    gson = new Gson();
                     msg = gson.fromJson(data, Response.class);

                     countries = msg.getRestResponse().getResult();
                    int ctr = 0;
                    for (Country country : countries) {
                        ctr++;
                        rb = mc.newRow();
                        rb.add(ctr);
                        rb.add(country.getName());
                        rb.add(country.getAlpha2_code());
                        rb.add(country.getAlpha3_code());
                    }

                    break;
                case 2:
                    String alpha3 = uri.getQueryParameter("alpha3");
                    url = new URL("https://restcountries.eu/rest/v2/alpha/"+alpha3);
                    conn = (HttpURLConnection) url.openConnection();
                    inputStream = conn.getInputStream();

                    scanner = new Scanner(inputStream);
                    builder = new StringBuilder();
                    while (scanner.hasNext()) {
                        builder.append(scanner.nextLine());
                    }

                     data = builder.toString();

                    JSONObject jsonStr = new JSONObject(data);

                    Log.i("value", jsonStr.toString());

                    url = new URL("http://services.groupkt.com/country/get/all");
                    conn = (HttpURLConnection) url.openConnection();
                    inputStream = conn.getInputStream();

                    scanner = new Scanner(inputStream);
                    builder = new StringBuilder();
                    while (scanner.hasNext()) {
                        builder.append(scanner.nextLine());
                    }

                    data = builder.toString();
                    gson = new Gson();
                     msg = gson.fromJson(data, Response.class);

                     countries = msg.getRestResponse().getResult();
                     ctr = 0;
                    for (Country country : countries) {
                        ctr++;
                        rb = mc.newRow();
                        rb.add(ctr);
                        rb.add(country.getName());
                        rb.add(country.getAlpha2_code());
                        rb.add(country.getAlpha3_code());
                    }

                    break;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mc;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
