package by.mycloud.railway.arduinobluetooth.connection;

import by.mycloud.railway.arduinobluetooth.connection.core.IBluetoothT;
import by.mycloud.railway.arduinobluetooth.properties.Property;

/**
 * Created by Andrey on 01.08.2017.
 */

public class BluetoothTWrapper {

    IBluetoothT bluetoothSend;

    public BluetoothTWrapper(IBluetoothT bluetoothSend) {
        this.bluetoothSend = bluetoothSend;
    }

    public void write(Property property) {
        byte[] bytes = new byte[2];
        bluetoothSend.write(bytes);
    }

}
