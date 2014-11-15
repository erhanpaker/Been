package me.kaaninan.been.location;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class KordinatService extends Service implements LocationListener {

    public KordinatService() { }


    private LocationManager manager;


    @Override
    public void onCreate() {
        super.onCreate();
        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        manager.requestLocationUpdates(manager.GPS_PROVIDER, 0, 0, this);

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.i("Kordinat Service", "Destroy");
    }


    // Location

    @Override
    public void onLocationChanged(android.location.Location location) {

        KayitTask task = new KayitTask(this);
        task.execute(new Location[] {location});

        manager.removeUpdates(this);

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) { }

    @Override
    public void onProviderEnabled(String provider) { }

    @Override
    public void onProviderDisabled(String provider) { }

}
