package com.example.androiddevelopment.pripremnizadatak.activities;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androiddevelopment.pripremnizadatak.R;
import com.example.androiddevelopment.pripremnizadatak.model.DatabaseHelper;
import com.example.androiddevelopment.pripremnizadatak.model.Glumac;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.List;

import static android.R.attr.id;
import static android.R.attr.name;
import static com.example.androiddevelopment.pripremnizadatak.activities.MainActivity.NOTIF_STATUS;
import static com.example.androiddevelopment.pripremnizadatak.activities.MainActivity.NOTIF_TOAST;

/**
 * Created by androiddevelopment on 10.3.17..
 */

public class DetailActivity  extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    private Glumac glumac;
    private EditText ime;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //actionBar.setDisplayHomeAsUpEnabled(true);
            //actionBar.setHomeAsUpIndicator(R.drawable.ic_drawer);
            //actionBar.setHomeButtonEnabled(true);
            actionBar.show();
        }
        try {
            glumac = getDatabaseHelper().getGlumacDao().queryForId(getIntent().getIntExtra("position",-1));


            //TextView ime = (TextView) this.findViewById(R.id.tv_ime);
            ime =(EditText) this.findViewById(R.id.et_ime);
            ime.setText(glumac.getmIme());
            TextView biografija = (TextView) this.findViewById(R.id.tv_biografija);
            biografija.setText(glumac.getmBiografija());

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void showStatusMesage(String message){
        NotificationManager mNotificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.ic_notifikacija);
        mBuilder.setContentTitle("Pripremni test");
        mBuilder.setContentText(message);

        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_add);

        mBuilder.setLargeIcon(bm);
        // notificationID allows you to update the notification later on.
        mNotificationManager.notify(1, mBuilder.build());
    }

    private void showMessage(String message){
        //provera podesenja
        boolean toast = prefs.getBoolean(NOTIF_TOAST, false);
        boolean status = prefs.getBoolean(NOTIF_STATUS, false);

        if (toast){
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }

        if (status){
            showStatusMesage(message);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_detail_add:
                //dodavanje filma
                showMessage("Action Add executed.");
                break;
            case R.id.action_detail_delete:
                try {
                    getDatabaseHelper().getGlumacDao().deleteById(glumac.getmId());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                showMessage("Action Delete executed.");
                break;
            case R.id.action_detail_edit:

                //POKUPITE INFORMACIJE SA EDIT POLJA
                glumac.setmIme(ime.getText().toString());


                try {
                    getDatabaseHelper().getGlumacDao().update(glumac);

                    showMessage("Action Edit executed.");

                } catch (SQLException e) {
                    e.printStackTrace();
                }

                break;
        }

        return super.onOptionsItemSelected(item);
    }
    // onCreateOptionsMenu method initialize the contents of the Activity's Toolbar.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public DatabaseHelper getDatabaseHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        }
        return databaseHelper;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // nakon rada sa bazo podataka potrebno je obavezno
        //osloboditi resurse!
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }
}
