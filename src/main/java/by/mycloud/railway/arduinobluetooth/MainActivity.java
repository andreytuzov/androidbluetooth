package by.mycloud.railway.arduinobluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.Loader;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import by.mycloud.railway.arduinobluetooth.enable.SocketEnable;
import by.mycloud.railway.arduinobluetooth.view.AdapterHelper;

public class MainActivity extends AppCompatActivity
        implements android.app.LoaderManager.LoaderCallbacks<BluetoothSocket> {

    private static final Logger logger = Logger.getLogger(MainActivity.class.getName());

    private static final int REQUEST_ENABLE_BT = 1;
    private static final int TASK_CONNECT_DEVICE = 2;

    private AdapterHelper adapterHelper;

    private BluetoothAdapter bluetooth;
    private BluetoothDevice device;
    private BluetoothSocket socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        adapterHelper = new AdapterHelper();

        // Настраиваем компоненты для отображения списка подключенных устройств
        final ListView lvFound = (ListView) findViewById(R.id.lvFound);
        lvFound.setAdapter(adapterHelper.createAdapter(this));
        lvFound.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, String> map = (Map<String, String>)parent.getAdapter().getItem(position);
                String bluetoothAddress = map.get(AdapterHelper.ATTR_ATTR2);
                device = bluetooth.getRemoteDevice(bluetoothAddress);
                createBluetoothSocket();
            }
        });

        bluetooth = getBluetoothAdapter();
        searchBluetoothDevice();
    }


    // =========================== ПОЛУЧЕНИЕ BLUETOOTH =============================================

    private BluetoothAdapter getBluetoothAdapter() {
        BluetoothAdapter bluetooth = BluetoothAdapter.getDefaultAdapter();
        if (!bluetooth.isEnabled()) {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, REQUEST_ENABLE_BT);
        }
        return bluetooth;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == RESULT_OK) {
                logger.log(Level.INFO, "Блютуз включен");
                searchBluetoothDevice();
            }
        }
    }

    // =========================== ПОЛУЧЕНИЕ DEVICE ================================================

    public void searchBluetoothDevice() {

        if (bluetooth == null || !bluetooth.isEnabled()) {
            return;
        }

        logger.log(Level.INFO, "Поиск устройств запущен");

        // Настраиваем приемник и получаем девайс
        IntentFilter intentFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, intentFilter);


        if (bluetooth.isDiscovering()) {
            bluetooth.cancelDiscovery();
        }
        bluetooth.startDiscovery();
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                adapterHelper.addItem(device.getName(), device.getAddress());
                logger.log(Level.INFO, "Найдено новое устройство: " + device.getName());
            }
        }
    };

    // =========================== ПОЛУЧЕНИЕ SOCKET ================================================

    public void createBluetoothSocket() {
        getLoaderManager().initLoader(TASK_CONNECT_DEVICE, null, this);
        getLoaderManager().getLoader(TASK_CONNECT_DEVICE).forceLoad();
    }

    @Override
    public Loader<BluetoothSocket> onCreateLoader(int id, Bundle args) {
        return new SocketEnable(MainActivity.this, device, bluetooth);
    }

    @Override
    public void onLoadFinished(Loader<BluetoothSocket> loader, BluetoothSocket data) {
        socket = data;
        createBluetoothManager();
        complete();
    }

    // Создаем объекты с использованием рефлексии
    public void createBluetoothManager() {
        try {
            Class bContextClass = BluetoothContext.class;
            Class bManagerClass = BluetoothManager.class;

            // Создаем bContext
            BluetoothContext bContext = BluetoothContext.getInstance();

            Field bluetoothField = bContextClass.getDeclaredField("bluetooth");
            bluetoothField.setAccessible(true);
            bluetoothField.set(bContext, bluetooth);

            Field deviceField = bContextClass.getDeclaredField("device");
            deviceField.setAccessible(true);
            deviceField.set(bContext, device);

            Field socketField = bContextClass.getDeclaredField("socket");
            socketField.setAccessible(true);
            socketField.set(bContext, socket);

            // Создаем bManager
            BluetoothManager bManager = BluetoothManager.getInstance();

            Field bContextField = bManagerClass.getDeclaredField("bluetoothContext");
            bContextField.setAccessible(true);
            bContextField.set(bManager, bContext);

            Method initMethod = bManagerClass.getDeclaredMethod("init", null);
            initMethod.setAccessible(true);
            initMethod.invoke(bManager, null);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onLoaderReset(Loader<BluetoothSocket> loader) {

    }


    private void complete() {
        Intent intent = new Intent();
        intent.putExtra("statusCode", 1);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(mReceiver);
    }



}
