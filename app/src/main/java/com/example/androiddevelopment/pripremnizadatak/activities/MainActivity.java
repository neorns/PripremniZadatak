package com.example.androiddevelopment.pripremnizadatak.activities;

import android.app.Dialog;
import android.app.FragmentTransaction;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.androiddevelopment.pripremnizadatak.R;
import com.example.androiddevelopment.pripremnizadatak.model.DatabaseHelper;
import com.example.androiddevelopment.pripremnizadatak.model.Glumac;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by androiddevelopment on 10.3.17..
 */

public class MainActivity extends AppCompatActivity {
    private DatabaseHelper databaseHelper;

    // Container Activity must implement this interface
    public interface OnGlumacSelectedListener {
        void onGlumacSelected(int id);
    }

    OnGlumacSelectedListener listener;
    ListAdapter adapter;
    public DatabaseHelper getDatabaseHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        }
        return databaseHelper;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

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
            List<Glumac> list = getDatabaseHelper().getGlumacDao().queryForAll();

            adapter = new ArrayAdapter<Glumac>(this, R.layout.list_item, list);

            final ListView listView = (ListView)this.findViewById(R.id.listaGlumci);

            // Assign adapter to ListView
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // Posto radimo sa bazom podataka, svaki element ima jedinstven id
                    // pa je potrebno da vidimo na koji tacno element smo kliknuli.
                    // To mozemo uraditi tako sto izvucemo proizvod iz liste i dobijemo njegov id
                    Glumac p = (Glumac) listView.getItemAtPosition(position);

                    Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                    intent.putExtra("position", p.getmId());
                    startActivity(intent);

                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    private void addItem() throws SQLException {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_glumac);



        final EditText glumacIme = (EditText) dialog.findViewById(R.id.glumac_ime);


        Button ok = (Button) dialog.findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ime = glumacIme.getText().toString();


                    Glumac glumac = new Glumac();
                    glumac.setmIme(ime);


                    try {
                        getDatabaseHelper().getGlumacDao().create(glumac);
                        refresh();
                        Toast.makeText(MainActivity.this, "Product inserted", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                        //reset();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

            }
        });

        Button cancel = (Button) dialog.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    private void refresh() {
        ListView listview = (ListView) findViewById(R.id.listaGlumci);

        if (listview != null){
            ArrayAdapter<Glumac> adapter = (ArrayAdapter<Glumac>) listview.getAdapter();

            if(adapter!= null)
            {
                try {
                    adapter.clear();
                    List<Glumac> list = getDatabaseHelper().getGlumacDao().queryForAll();

                    adapter.addAll(list);

                    adapter.notifyDataSetChanged();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_main_add:
                //Toast.makeText(this, "Action " + getString(R.string.fragment_master_action_create) + " executed.", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "Action Dodaj executed.", Toast.LENGTH_SHORT).show();
                try {
                    addItem();
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
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }



}
