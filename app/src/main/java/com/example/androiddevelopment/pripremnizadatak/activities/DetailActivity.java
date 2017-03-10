package com.example.androiddevelopment.pripremnizadatak.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

/**
 * Created by androiddevelopment on 10.3.17..
 */

public class DetailActivity  extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    private Glumac glumac;
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


            TextView ime = (TextView) this.findViewById(R.id.tv_ime);
            ime.setText(glumac.getmIme());
            TextView biografija = (TextView) this.findViewById(R.id.tv_biografija);
            biografija.setText(glumac.getmBiografija());

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_detail_add:
                //dodavanje filma
                Toast.makeText(this, "Action Add executed.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_detail_delete:
                try {
                    getDatabaseHelper().getGlumacDao().deleteById(glumac.getmId());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                Toast.makeText(this, "Action Delete executed.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_detail_edit:

                Toast.makeText(this, "Action Edit executed.", Toast.LENGTH_SHORT).show();
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
}
