package ca.on.sl.comp208.lhahncomp208a3m1;

import android.content.ContentResolver;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.List;


public class MainActivity extends AppCompatActivity {

//    private static Context mContext;
//
//    public static void setmContext(Context mContext) {
//        MainActivity.mContext = mContext;
//    }
//
//    public static Context getContext() {
//        return mContext;
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        setmContext(this.getApplicationContext());
            Button btn = (Button) findViewById(R.id.sbtn);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isConnected()) {
                        Log.i("Status", "Connected");
                        WebAsyncTask task = new WebAsyncTask();
                        // this is the URL that we are connecting to to retrieve the JSON data.
                        task.execute(Uri.withAppendedPath(ProviderContract.CONTENT_URI,"all"));
                    } else {
                        Log.i("Status", "Not Connected");
                    }

                }
            });




    }

    public boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }


    class WebAsyncTask extends AsyncTask<Uri, Cursor, Void> {


        @Override
        protected Void doInBackground(Uri... uri) { // takes the URL as a string Array type thing
           // Context c = MainActivity.getContext();

            String[] projection = {
                    ProviderContract.Data._ID,
                    ProviderContract.Data.name,
                    ProviderContract.Data.alpha2,
                    ProviderContract.Data.alpha3
            };

            Cursor cursor;
            cursor = getContentResolver().query(uri[0], projection, null, null, null);

            publishProgress(cursor);
            return null;
        }

        @Override
        protected void onProgressUpdate(Cursor... cursors) {
            super.onProgressUpdate(cursors);
//            Looper.prepare();
              final Cursor cursor=cursors[0];

//                String output =
//                        String.format("%-5s %s\n", cursor.getString(0) + "   " + cursor.getString(1), cursor.getString(2));

                ListView lv = findViewById(R.id.simpleListView);
//                Log.i("values", output + "\n");
                final String[] from = {
                        ProviderContract.Data._ID,
                        ProviderContract.Data.name,
                        ProviderContract.Data.alpha2,
                        ProviderContract.Data.alpha3};
                final String[] column = {
                        ProviderContract.Data._ID,
                        ProviderContract.Data.name,
                        ProviderContract.Data.alpha2,
                        ProviderContract.Data.alpha3};

                final int[] to = {R.id.ID, R.id.name, R.id.Alpha2, R.id.Alpha3};

                SimpleCursorAdapter adapter = new SimpleCursorAdapter(getApplicationContext(),
                        R.layout.country_list, cursor, from, to);

                lv.setAdapter(adapter);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                        cursor.moveToPosition(pos);
                        Uri uri = Uri.withAppendedPath(ProviderContract.CONTENT_URI,"alpha3");
                        uri = uri.buildUpon().appendQueryParameter("alpha3",cursor.getString(3)).build();
                        Toast.makeText(getApplicationContext(), cursor.getString(3), Toast.LENGTH_SHORT).show();
                        WebAsyncTask task= new WebAsyncTask();
                        task.execute(uri);
                    }
                });




        }
        }
    }

