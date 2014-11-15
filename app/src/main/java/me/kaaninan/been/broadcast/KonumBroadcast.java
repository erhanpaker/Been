package me.kaaninan.been.broadcast;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.Toast;

import me.kaaninan.been.Kayitlar;
import me.kaaninan.been.MainActivity;

public class KonumBroadcast extends Activity {


    public KonumBroadcast(Context context) {

        IntentFilter intentFilter = new IntentFilter("me.kaaninan.been.Konum");
        BroadcastReceiver receiver = new konumBroadcast();
        context.registerReceiver(receiver,intentFilter);

    }

    public class konumBroadcast extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {

            Toast.makeText(context, "Broadcast Geldi", Toast.LENGTH_LONG).show();

            Kayitlar kayitlar = (Kayitlar) ((MainActivity)context).getFragmentManager().findFragmentByTag("kayitlar");
            kayitlar.setViewKonum(intent.getStringExtra("KONUM"));

        }
    }


}
