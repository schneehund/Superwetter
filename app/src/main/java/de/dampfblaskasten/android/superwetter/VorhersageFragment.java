package de.dampfblaskasten.android.superwetter;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class VorhersageFragment extends Fragment {

    public VorhersageFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater) {

        inflater.inflate(R.menu.vorhersagefragment, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_refresh:
                HolWetterData hwd = new HolWetterData();
                hwd.execute("2890473");
               return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ArrayList<String> bspData = new ArrayList();
        bspData.add("Heute- Kalt - -3");
        bspData.add("Morgen- NochKälter - -5");
        bspData.add("23.1- Kalt - -3");
        bspData.add("24.1- Schnee - 2");
        bspData.add("25.1- Kalt - -3");
        bspData.add("26.1- Sonnig - 5");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item_forecast,R.id.list_item_forecast_textview, bspData);

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        ListView lw = (ListView)rootView.findViewById(R.id.listview_forecast);
        lw.setAdapter(adapter);



        return rootView;

    }

    public class HolWetterData extends AsyncTask<String, Void, Void>
    {

        private final String LOG_TAG = HolWetterData.class.getSimpleName();

        @Override
        protected Void doInBackground(String... params) {


            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;


            String forecastJsonStr = null;

            String format = "json";
            String units = "metric";
            int numDays = 7;
            String apikey = "3b4776f495153d82df92958acaef772a";


            try {

             //  URL url = new URL("http://api.openweathermap.org/data/2.5/forecast/daily?id=2890473&units=metric&cnt=7&APPID=3b4776f495153d82df92958acaef772a");

                final String FORECAST_BASE_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?";
                final String QUERY_PARAM = "id";
                final String FORMAT_PARAM = "mode";
                final String UNITS_PARAM = "units";
                final String DAYS_PARAM = "cnt";
                final String APPID_PARAM = "APPID";

                Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                                  .appendQueryParameter(QUERY_PARAM, params[0])
                                  .appendQueryParameter(FORMAT_PARAM, format)
                                  .appendQueryParameter(UNITS_PARAM, units)
                                  .appendQueryParameter(DAYS_PARAM, Integer.toString(numDays))
                                  .appendQueryParameter(APPID_PARAM, apikey)
                                  .build();

                            URL url = new URL(builtUri.toString());
                Log.v(LOG_TAG, "Built URI " + builtUri.toString());


                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();


                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {

                    forecastJsonStr = null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {

                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {

                    forecastJsonStr = null;
                }
                forecastJsonStr = buffer.toString();
                System.out.println(forecastJsonStr);
            } catch (IOException e) {
                Log.e("VorhersageFragment", "Error ", e);

                forecastJsonStr = null;
            } finally{
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("VorhersageFragment", "Error closing stream", e);
                    }
                }
            }

            return null;

        }
    }

}
