package com.example.owner.hanieum_project.Activity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.owner.hanieum_project.R;

/**
 * Created by Owner on 2016-08-04.
 */
public class Next_MainActivity extends ActionBarActivity {

    Button Weather;
    Button Machine;
    Button Settings;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_next_mainactivity);
        Weather = (Button) findViewById(R.id.B_weather);
        Machine = (Button) findViewById(R.id.B_Machine);
        Settings = (Button) findViewById(R.id.B_settings);

        Weather.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(Next_MainActivity.this, weatherActivity.class);
                startActivity(intent);
            }
        });

        Machine.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent_machine = new Intent(Next_MainActivity.this, Machine.class);
                startActivity(intent_machine);
            }
        });

        Settings.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent_settings = new Intent(Next_MainActivity.this, settings.class);
                startActivity(intent_settings);
            }
        });
    }


        ;
 /*   public void ensureDiscoverable() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(discoverableIntent);
            }*/

}
