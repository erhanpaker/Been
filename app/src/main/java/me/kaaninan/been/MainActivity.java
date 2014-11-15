package me.kaaninan.been;

import android.app.ActionBar;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import me.kaaninan.been.broadcast.KonumBroadcast;


public class MainActivity extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;

    private FragmentTransaction tx;

    private Kayitlar kayitlar;

    public final static int KONUM_SECENEK = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        setContentView(R.layout.activity_main);

        getActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar));

        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));


        KonumBroadcast konumBroadcast = new KonumBroadcast(this);

    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        String[] Pages = getResources().getStringArray(R.array.pages);
        tx = getFragmentManager().beginTransaction();
        tx.replace(R.id.container, Fragment.instantiate(MainActivity.this, Pages[position]), "kayitlar");
        tx.commit();
    }



    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    // DIALOG

    @Override
    protected Dialog onCreateDialog(int id){

        Dialog dialog;

        kayitlar = (Kayitlar) getFragmentManager().findFragmentByTag("kayitlar");

        switch (id){
            case KONUM_SECENEK:
                dialog = kayitlar.konumSecenekDialog();
                break;
            default:
                dialog = null;
        }

        return dialog;
    }


}
