package by.mycloud.railway.arduinobluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;

import by.mycloud.railway.arduinobluetooth.properties.PropertyMap;

/**
 * Created by Andrey on 01.08.2017.
 */

public class BluetoothContext {

    private BluetoothAdapter bluetooth;
    private BluetoothDevice device;
    private BluetoothSocket socket;
    private PropertyMap data = new PropertyMap();

    private static BluetoothContext bContext = new BluetoothContext();

    private BluetoothContext() {
    }

    public BluetoothAdapter getBluetooth() {
        return bluetooth;
    }

    public BluetoothDevice getDevice() {
        return device;
    }

    public BluetoothSocket getSocket() {
        return socket;
    }

    public PropertyMap getData() {
        return data;
    }

    public static BluetoothContext getInstance() {
        return bContext;
    }

}
