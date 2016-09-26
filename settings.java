package com.example.owner.hanieum_project.Activity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.owner.hanieum_project.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;


/**
 * Created by Owner on 2016-09-14.
 */
public class settings extends ActionBarActivity {
    private BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private static BluetoothConnect mBluetoothConnect;
    private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
    private static final int REQUEST_ENABLE_BT = 3;
    private setting_frag setFrag;
    private Device_settings deviceFrag;
    private Device_settings_2 deviceFrag2;
    private Device_settings_3 deviceFrag3;
    private Device_settings_4 deviceFrag4;
    private Device_settings_5 deviceFrag5;
    private Device_settings_6 deviceFrag6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_settings);

        setFrag = (setting_frag) getSupportFragmentManager().findFragmentById(R.id.settings);
        deviceFrag = new Device_settings();
        deviceFrag2 = new Device_settings_2();
        deviceFrag3 = new Device_settings_3();
        deviceFrag4 = new Device_settings_4();
        deviceFrag5 = new Device_settings_5();
        deviceFrag6 = new Device_settings_6();
    }

    public void onFragmentChanged(int index){

        if(index == 0)
            getSupportFragmentManager().beginTransaction().replace(R.id.container, setFrag).commit();
        else {
            switch (index) {
                case 1:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, deviceFrag).commit();
                    break;
                case 2:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, deviceFrag2).commit();
                    break;
                case 3:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, deviceFrag3).commit();
                    break;
                case 4:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, deviceFrag4).commit();
                    break;
                case 5:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, deviceFrag5).commit();
                    break;
                case 6:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, deviceFrag6).commit();
                    break;
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        }else{
            mBluetoothConnect = new BluetoothConnect();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mBluetoothConnect != null) {
            mBluetoothConnect.stop();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mBluetoothConnect != null) {
            if (mBluetoothConnect.getState() == mBluetoothConnect.STATE_NONE) {
                mBluetoothConnect.start();
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE_SECURE:
                if (resultCode != Activity.RESULT_CANCELED) {
                    connectDevice(data);
                    break;
                }
            case REQUEST_ENABLE_BT:
                if (resultCode == Activity.RESULT_OK) {
                    mBluetoothConnect = new BluetoothConnect();
                }else if(resultCode == Activity.RESULT_CANCELED){
                        Toast.makeText(settings.this, "Bluetooth was not enable, turn on the bluetooth.",Toast.LENGTH_SHORT).show();
                        settings.this.finish();
                    }
                    break;
            }

    }
    private void connectDevice(Intent data) {
        String address = data.getExtras().getString(BLListActivity.EXTRA_DEVICE_ADDRESS);
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        Log.e("connectDevice", address);
        mBluetoothConnect.connect(device);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.secure_Bluetooth: {
                Intent enableIntent = new Intent(settings.this, BLListActivity.class);
                startActivityForResult(enableIntent, REQUEST_CONNECT_DEVICE_SECURE);
                return true;
            }

        }
        return false;
    }

    public void send_data(String send){
        byte[] sending_data = send.getBytes();

        mBluetoothConnect.write(sending_data);
    }

    public static class BluetoothConnect {

        private static final String TAG = "BluetoothConnect";
        private final String NAME_SECURE = "BluetoothChatInsecure";
        private final UUID MY_UUID_SECURE =
                UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

        private final BluetoothAdapter mAdapter;
        private final Handler mHandler = new Handler();
        private AcceptThread mAcceptThread;
        private ConnectThread mConnectThread;
        private ConnectedThread mConnectedThread;
        public int mState;

        public static final int STATE_NONE = 0;
        public static final int STATE_LISTEN = 1;
        public static final int STATE_CONNECTING = 2;
        public static final int STATE_CONNECTED = 3;

        public BluetoothConnect() {
            mAdapter = BluetoothAdapter.getDefaultAdapter();
            mState = STATE_NONE;
            Log.e("1"," ");
            Log.e("Connected?",String.valueOf(mState));
        }

        private synchronized void setState(int state) {
            mState = state;

            mHandler.obtainMessage(Constants.MESSAGE_STATE_CHANGE, state, -1).sendToTarget();
            Log.e("2"," ");
            Log.e("Connected?",String.valueOf(mState));
        }

        public synchronized int getState() {
            return mState;
        }

        public synchronized void start() {

            if (mConnectThread != null) {
                mConnectThread.cancel();
                mConnectThread = null;
            }

            if (mConnectedThread != null) {
                mConnectedThread.cancel();
                mConnectedThread = null;
            }

            setState(STATE_LISTEN);

            if (mAcceptThread == null) {
                mAcceptThread = new AcceptThread(true);
                mAcceptThread.start();
            }

            Log.e("3"," ");
            Log.e("Connected?", String.valueOf(mState));
        }

        public synchronized void connect(BluetoothDevice device) {
            Log.e("connect to: ", String.valueOf(device));
            if (mState == STATE_CONNECTING) {
                if (mConnectThread != null) {
                    mConnectThread.cancel();
                    mConnectThread = null;
                }
            }

            if (mConnectedThread != null) {
                mConnectedThread.cancel();
                mConnectedThread = null;
            }

            mConnectThread = new ConnectThread(device);
            mConnectThread.start();
            setState(STATE_CONNECTING);
            Log.e("4"," ");
            Log.e("Connected?", String.valueOf(mState));
        }

        public synchronized void connected(BluetoothSocket socket, BluetoothDevice
                device, final String socketType) {
            Log.d(TAG, "connected, Socket Type:" + socketType);
            if (mConnectThread != null) {
                mConnectThread.cancel();
                mConnectThread = null;
            }

            if (mConnectedThread != null) {
                mConnectedThread.cancel();
                mConnectedThread = null;
            }

            if (mAcceptThread != null) {
                mAcceptThread.cancel();
                mAcceptThread = null;
            }
            Log.e("initialize","success");
            mConnectedThread = new ConnectedThread(socket, socketType);
            mConnectedThread.start();

            Message msg = mHandler.obtainMessage(Constants.MESSAGE_DEVICE_NAME);
            Bundle bundle = new Bundle();
            bundle.putString(Constants.DEVICE_NAME, device.getName());
            msg.setData(bundle);
            mHandler.sendMessage(msg);

            setState(STATE_CONNECTED);
            Log.e("5"," ");
            Log.e("Connected?",String.valueOf(mState));
        }

        public synchronized void stop() {

            if (mConnectThread != null) {
                mConnectThread.cancel();
                mConnectThread = null;
            }

            if (mConnectedThread != null) {
                mConnectedThread.cancel();
                mConnectedThread = null;
            }

            if (mAcceptThread != null) {
                mAcceptThread.cancel();
                mAcceptThread = null;
            }
            setState(STATE_NONE);
            Log.e("6"," ");
            Log.e("Connected?",String.valueOf(mState));
        }

        public void write(byte[] out) {
            ConnectedThread r;
            Log.e("Connected?",String.valueOf(mState));
            synchronized (this) {
                if(mState != STATE_CONNECTED) return;
                Log.e("Copy","Success");
                r = mConnectedThread;
            }
            Log.e("7"," ");
            Log.e("Connected?",String.valueOf(mState));
            r.write(out);
        }

        private void connectionFailed() {
            Message msg = mHandler.obtainMessage(Constants.MESSAGE_TOAST);
            Bundle bundle = new Bundle();
            bundle.putString(Constants.TOAST, "Unable to connect device");
            msg.setData(bundle);
            mHandler.sendMessage(msg);
            Log.e("8"," ");
            Log.e("Connected?",String.valueOf(mState));
            BluetoothConnect.this.start();
        }

        private void connectionLost() {

            Message msg = mHandler.obtainMessage(Constants.MESSAGE_TOAST);
            Bundle bundle = new Bundle();
            bundle.putString(Constants.TOAST, "Device connection was lost");
            msg.setData(bundle);
            mHandler.sendMessage(msg);

            Log.e("9"," ");
            Log.e("Connected?",String.valueOf(mState));
            BluetoothConnect.this.start();
        }

        private class AcceptThread extends Thread {
            private final BluetoothServerSocket mmServerSocket;
            private String mSocketType;


            public AcceptThread(boolean secure) {
                BluetoothServerSocket tmp = null;
                try {
                    tmp = mAdapter.listenUsingRfcommWithServiceRecord(NAME_SECURE, MY_UUID_SECURE);
                } catch (IOException e) {
                }
                mmServerSocket = tmp;
                Log.e("10"," ");
                Log.e("Connected?",String.valueOf(mState));
            }

            public void run() {
                BluetoothSocket socket = null;
                while (mState != STATE_CONNECTED) {
                    try {
                        socket = mmServerSocket.accept();
                    } catch (IOException e) {
                        Log.e(TAG, "Socket Type: " + mSocketType + " accept() failed", e);
                        break;
                    }

                    if (socket != null) {
                        synchronized (BluetoothConnect.this) {
                            switch (mState) {
                                case STATE_LISTEN:
                                case STATE_CONNECTING:
                                    connected(socket, socket.getRemoteDevice(), mSocketType);
                                    break;
                                case STATE_NONE:
                                case STATE_CONNECTED:
                                    try {
                                        mmServerSocket.close();
                                        socket.close();
                                    } catch (IOException e) {
                                        Log.e(TAG, "Could not close unwanted socket", e);
                                    }
                                    break;
                            }
                        }
                    }
                }
                Log.i(TAG, "END mAcceptThread, socket Type: " + mSocketType);
                Log.e("11"," ");
                Log.e("Connected?",String.valueOf(mState));
            }

            public void cancel() {
                try {

                } catch (Exception e) {
                    Log.e(TAG, "Socket Type" + mSocketType + "close() of server failed", e);
                }
                Log.e("12"," ");
                Log.e("Connected?",String.valueOf(mState));
            }


        }

        private class ConnectThread extends Thread {
            private BluetoothSocket mmSocket;
            private final BluetoothDevice mmDevice;
            private String mSocketType;

            public ConnectThread(BluetoothDevice device) {
                mmDevice = device;
                BluetoothSocket tmp = null;
                mSocketType = "Secure";
                try {
                    tmp = mmDevice.createRfcommSocketToServiceRecord(MY_UUID_SECURE);
                } catch (IOException e) {
                    Log.e(TAG, "Printing - IOException", e);
                }
                mmSocket = tmp;
                Log.e("13"," ");
                Log.e("Connected?",String.valueOf(mState));
            }

            public void run() {
                Log.i(TAG, "BEGIN mConnectThread SocketType:" + mSocketType);
                setName("ConnectThread" + mSocketType);
                //BluetoothDevice device = mAdapter.getRemoteDevice(Address);

                mAdapter.cancelDiscovery();
                try {
                    mmSocket.connect();
                } catch (IOException e) {
                    try {
                        //mmSocket =(BluetoothSocket) device.getClass().getMethod("createRfcommSocket", new Class[] {int.class}).invoke(device,1);
                        mmSocket.close();
                    } catch (Exception e2) {
                        Log.e(TAG, "unable to close() " + mSocketType +
                                " socket during connection failure", e2);
                    }
                    connectionFailed();
                    return;
                }

                synchronized (BluetoothConnect.this) {
                    mConnectThread = null;
                }

                connected(mmSocket, mmDevice, mSocketType);
                Log.e("14"," ");
                Log.e("Connected?",String.valueOf(mState));
            }

            public void cancel() {
                try {
                    mmSocket.close();
                } catch (IOException e) {
                    Log.e(TAG, "close() of connect " + mSocketType + " socket failed", e);
                }
                Log.e("15"," ");
                Log.e("Connected?",String.valueOf(mState));
            }
        }

        private class ConnectedThread extends Thread {
            private final BluetoothSocket mmSocket;
            private final InputStream mmInStream;
            private final OutputStream mmOutStream;

            public ConnectedThread(BluetoothSocket socket, String socketType) {
                Log.d(TAG, "create ConnectedThread: " + socketType);
                mmSocket = socket;
                InputStream tmpIn = null;
                OutputStream tmpOut = null;

                try {
                        tmpIn = socket.getInputStream();
                        tmpOut = socket.getOutputStream();
                } catch (IOException e) {
                    Log.e(TAG, "temp sockets not created", e);
                }

                mmInStream = tmpIn;
                mmOutStream = tmpOut;
                Log.e("16"," ");
                Log.e("Connected?",String.valueOf(mState));
            }

            public void run() {
                Log.i(TAG, "BEGIN mConnectedThread");
                byte[] buffer = new byte[1024];
                int bytes;

                while (mState == STATE_CONNECTED) {
                    try {
                        bytes = mmInStream.read(buffer);
                        mHandler.obtainMessage(Constants.MESSAGE_READ, bytes, -1, buffer)
                                .sendToTarget();
                    } catch (IOException e) {
                        Log.e(TAG, "disconnected", e);
                        connectionLost();
                        BluetoothConnect.this.start();
                        break;
                    }
                }
                Log.e("17"," ");
                Log.e("Connected?",String.valueOf(mState));
            }

            public void write(byte[] buffer) {
                try {
                    Log.e("제대로 도착",String.valueOf(buffer));
                    Log.e("Connected?",String.valueOf(mState));
                    mmOutStream.write(buffer);
                    mHandler.obtainMessage(Constants.MESSAGE_WRITE, -1, -1, buffer)
                            .sendToTarget();
                } catch (IOException e) {
                    Log.e(TAG, "Exception during write", e);
                }
                Log.e("18"," ");
                Log.e("Connected?",String.valueOf(mState));
            }

            public void cancel() {
                try {
                    mmSocket.close();
                } catch (IOException e) {
                    Log.e(TAG, "close() of connect socket failed", e);
                }
                Log.e("19"," ");
                Log.e("Connected?",String.valueOf(mState));
            }
        }
    }
}
