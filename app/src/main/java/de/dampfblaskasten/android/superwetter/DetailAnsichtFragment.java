package de.dampfblaskasten.android.superwetter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailAnsichtFragment extends Fragment {

    private Integer images[] = {R.drawable.sunny, R.drawable.snow, R.drawable.regen, R.drawable.fewclouds};


    public DetailAnsichtFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail_ansicht, container, false);
        Intent intent = getActivity().getIntent();

        String forecastStr;
        String[] teile;

        if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
            forecastStr = intent.getStringExtra(Intent.EXTRA_TEXT);

            teile= forecastStr.split("-", 3);
            ((TextView) rootView.findViewById(R.id.tfDatum))
                    .setText(teile[0]);
            ((TextView) rootView.findViewById(R.id.tfStatus))
                    .setText(teile[1]);

            String[] temps = teile[2].toString().split("/");
            ((TextView) rootView.findViewById(R.id.tfmin))
                    .setText("Min Temp:" + temps[1]);
            ((TextView) rootView.findViewById(R.id.tfmax)).setText("Max Temp:" + temps[0]);

             ImageView imgVw =(ImageView)rootView.findViewById(R.id.imgVorschau);


            if (teile[1].contains("Clear")) {
                imgVw.setImageResource(images[0]); }
            else if (teile[1].contains("Rain")) {
                imgVw.setImageResource(images[2]); }
            else if (teile[1].contains("Clouds")) {
                imgVw.setImageResource(images[3]); }
            else if (teile[1].contains("Snow")) {
                imgVw.setImageResource(images[1]); }




        }








        return rootView;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.detail, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }







}
