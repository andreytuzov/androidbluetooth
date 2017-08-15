package by.mycloud.railway.arduinobluetooth;

import android.content.Context;

import by.mycloud.railway.arduinobluetooth.connection.core.BluetoothTR;
import by.mycloud.railway.arduinobluetooth.properties.Property;
import by.mycloud.railway.arduinobluetooth.connection.BluetoothRWrapper;
import by.mycloud.railway.arduinobluetooth.connection.BluetoothTWrapper;

/**
 * Created by Andrey on 01.08.2017.
 */

public class BluetoothManager {

    private BluetoothRWrapper receiverDataHandler;
    private BluetoothTWrapper transmitterDataHandler;
    private BluetoothTR bluetoothTR;
    private BluetoothContext bluetoothContext;

    private static BluetoothManager bManager = new BluetoothManager();

    public static BluetoothManager getInstance() {
        return bManager;
    }

    private BluetoothManager() {
    }

    private void init() {
        receiverDataHandler = new BluetoothRWrapper(bluetoothContext.getData());
        transmitterDataHandler = new BluetoothTWrapper(new BluetoothTR(bluetoothContext.getSocket(),
                receiverDataHandler));
        bluetoothTR = new BluetoothTR(bluetoothContext.getSocket(), receiverDataHandler);
        bluetoothTR.start();
    }

    public BluetoothContext getBluetoothContext() {
        return bluetoothContext;
    }

    // Create a property with handler
    public void createProperty(Property property) {
        bluetoothContext.getData().addProperty(property);
    }

    // Set and send new-value of property
    public void setProperty(Property property) {
        transmitterDataHandler.write(property);
    }

    // Get old-value of property
    public Object getProperty(int id) {
        return bluetoothContext.getData().getProperty(id);
    }
}
