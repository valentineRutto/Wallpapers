package com.example.valentine.wallpapers;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONException;
import org.json.JSONObject;

public class Splash extends AppCompatActivity {

        String now_playing, earned;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_splash);

            /**
             * Showing splashscreen while making network calls to download necessary
             * data before launching the app Will use AsyncTask to make http call
             */
            new PrefetchData().execute();

        }

        /**
         * Async Task to make http call
         */
        private class PrefetchData extends AsyncTask<Void, Void, Void> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                // before making http calls

            }

            @Override
            protected Void doInBackground(Void... arg0) {
                JsonParser jsonParser = new JsonParser();
                String json = jsonParser
                        .getJSONFromUrl("http://api.androidhive.info/game/game_stats.json");

                Log.e("Response: ", "> " + json);

                if (json != null) {
                    try {
                        JSONObject jObj = new JSONObject(json)
                                .getJSONObject("game_stat");
                        now_playing = jObj.getString("now_playing");
                        earned = jObj.getString("earned");

                        Log.e("JSON", "> " + now_playing + earned);

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }

                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);
                // After completing http call
                // will close this activity and lauch main activity
                Intent i = new Intent(Splash.this, MainActivity.class);
                i.putExtra("now_playing", now_playing);
                i.putExtra("earned", earned);
                startActivity(i);

                // close this activity
                finish();
            }

            private class JsonParser {
                public String getJSONFromUrl(String s) {
                    return s;
                }
            }
        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
