package com.fjord.podrozni;


import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class Splash extends AppCompatActivity {

    String JSON_STRING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);

        new JsonGetter().execute();

        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(1200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent intent = new Intent("android.intent.action.MAINACTIVITY");
                    startActivity(intent);
                }
            }
        };

        timer.start();
    }


    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }


    class JsonGetter extends AsyncTask<Void, Void, String> {


        String json_url;


        @Override
        protected void onPreExecute() {
            json_url = "http://festiwalpodrozni.pl/app/jevents.php";
        }

        @Override
        protected String doInBackground(Void... voids) {


            try {
                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();

                while ((JSON_STRING = bufferedReader.readLine()) != null) {

                    stringBuilder.append(JSON_STRING + "\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {

            JSON_STRING = result;

            ObjectOutput out = null;
            if(JSON_STRING != null){
            try {
                out = new ObjectOutputStream(new FileOutputStream(new File(getCacheDir(),"json")+"cacheFile.srl"));
                out.writeObject( JSON_STRING );

            out.close();

        } catch (IOException e) {
                e.printStackTrace();
            }}
        }
    }
}