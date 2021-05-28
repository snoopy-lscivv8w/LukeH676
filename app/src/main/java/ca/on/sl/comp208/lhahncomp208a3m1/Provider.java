package ca.on.sl.comp208.lhahncomp208a3m1;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.util.Log;


import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;


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
        // TODO: Implement this to handle query requests from clients.
        //throw new UnsupportedOperationException("Not yet implemented");
        //  Log.i("URL",url.toString());
        try {
            URL url = new URL("http://services.groupkt.com/country/get/all");
            //  Log.i("URL",url.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            InputStream inputStream = conn.getInputStream();
            // the above (I think) is supposed to be moved to Provider.java
            // Scanner gets replaced by a "MatrixCursor" in the Provider.java class
            // once all is said and done - the Query method from Provider.java is called here.

            Scanner scanner = new Scanner(inputStream);
            StringBuilder builder = new StringBuilder();
            while(scanner.hasNext()){
                builder.append(scanner.nextLine());
            }

            String data = builder.toString();
            Gson gson = new Gson();
            Response msg = gson.fromJson(data,Response.class);
//            String[] messages = msg.getRestResponse().getMessages();
//            for (int i=0; i < messages.length; i++){
//                //  publishProgress(msg[i].getMessage()+"\n");
//                Log.i("value", messages[i] + "\n");
//            }

            Country[] countries = msg.getRestResponse().getResult();
            for (Country country : countries) {
                rb = mc.newRow();
                rb.add(country.getName() );
                rb.add(country.getAlpha2_code());
                rb.add(country.getAlpha3_code());
            }
        }catch(MalformedURLException e){
            e.printStackTrace();

        }catch (IOException e){
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
