package ca.on.sl.comp208.lhahncomp208a3m1;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(isConnected()) {
            Log.i("Status", "Connected");
            WebAsyncTask task = new WebAsyncTask();

            task.execute("http://services.groupkt.com/country/get/all");
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

class WebAsyncTask extends AsyncTask<String,String,Void>{

    @Override
    protected Void doInBackground(String... strings) {
        try {
            URL url = new URL(strings[0]);
          //  Log.i("URL",url.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            InputStream inputStream = conn.getInputStream();

            Scanner scanner = new Scanner(inputStream);
            StringBuilder builder = new StringBuilder();
            while(scanner.hasNext()){
                builder.append(scanner.nextLine());
            }
            String data = builder.toString();
            Gson gson = new Gson();
            Message[] msg = gson.fromJson(data,Message[].class);
            for (int i=1;i<msg.length;i++){
              //  publishProgress(msg[i].getMessage()+"\n");
                Log.i("values", msg[i].getMessage()+"\n");
        }
        }catch(MalformedURLException e){
            e.printStackTrace();
            publishProgress("URL PROBLEM");
        }catch (IOException e){
            e.printStackTrace();
            publishProgress("Input Problem");

        }
        return null;
    }
    @Override
    protected void onProgressUpdate(String... values){
        super.onProgressUpdate();
        Log.i("values", values[0]+"\n");
    }
}
class Message{
    int status;
    String message;
    int junk;

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public int getJunk() {
        return junk;
    }
}


