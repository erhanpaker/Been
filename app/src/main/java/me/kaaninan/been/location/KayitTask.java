package me.kaaninan.been.location;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;

import java.io.IOException;
import java.util.List;


public class KayitTask extends AsyncTask<Location, String, String> {

    private Context context;


    public KayitTask(Context context) {
        super();
        this.context = context;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    private List<Address> address;
    private Address adres;
    private String line;

    @Override
    protected String  doInBackground(Location... params) {

        try {
            double enlem = params[0].getLatitude();
            double boylam = params[0].getLongitude();

            Geocoder geocoder = new Geocoder(context);
            address = geocoder.getFromLocation(enlem, boylam, 1);
            adres = address.get(0);

            for (int i = 0; i <= adres.getMaxAddressLineIndex(); i++){
                line = line + "  " + adres.getAddressLine(i);
            }


        } catch (IOException e) {
            e.printStackTrace();

            line = "İnternet bağlantısı yok.";
        }

        return line;
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        ((KordinatService)context).stopSelf();

        Intent intent = new Intent("me.kaaninan.been.Konum");
        intent.putExtra("KONUM",s);
        context.sendBroadcast(intent);

    }
}
