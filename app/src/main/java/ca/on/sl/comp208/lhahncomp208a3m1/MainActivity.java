package ca.on.sl.comp208.lhahncomp208a3m1;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


public class MainActivity extends AppCompatActivity {
    private static Context mContext;

    public static void setmContext(Context mContext) {
        MainActivity.mContext = mContext;
    }

    public static Context getContext() {
        return mContext;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setmContext(this.getApplicationContext());


        if(isConnected()) {
            Log.i("Status", "Connected");
            WebAsyncTask task = new WebAsyncTask();
            // this is the URL that we are connecting to to retrieve the JSON data.
            task.execute(ProviderContract.Data.CONTENT_URI);
        }else {
            Log.i("Status", "Not Connected");
        }

    }

    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

}
// Instead of String,String,Void - Will change to String,Cursor,Void to accomodate the Matrix Cursor being passed
class WebAsyncTask extends AsyncTask<Uri,Cursor,Void>{


    @Override
    protected Void doInBackground(Uri... uri) { // takes the URL as a string Array type thing
        Context c = MainActivity.getContext();

        String[] projection = {
                ProviderContract.Data._ID,
                ProviderContract.Data.name,
                ProviderContract.Data.alpha2,
                ProviderContract.Data.alpha3
        };

        Cursor cursor;
        cursor = c.getContentResolver().query(uri[0], projection, null, null,null);

        while (cursor.moveToNext()) {
            String output =
                    String.format("%-5s %s\n", cursor.getString(0) + "   " +cursor.getString(1), cursor.getString(2));
            onProgressUpdate(output);
        }
        return null;
    }

    //@Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate();
        // finally - Prints to "LOGCAT" (Like printing to console)
        Log.i("values", values[0] + "\n");
    }

}
