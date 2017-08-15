package by.mycloud.railway.arduinobluetooth.enable;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.AsyncTaskLoader;
import android.content.Context;

import java.io.IOException;
import java.util.UUID;



/**
 * Created by SQL on 23.01.2017.
 */

public class SocketEnable extends AsyncTaskLoader<BluetoothSocket> {

    private BluetoothSocket socket;
    private BluetoothDevice device;
    private BluetoothAdapter bluetooth;
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    public SocketEnable(Context context, BluetoothDevice device, BluetoothAdapter bluetooth) {
        super(context);
        this.device = device;
        this.bluetooth = bluetooth;
    }

    @Override
    public BluetoothSocket loadInBackground() {
        // Получаем сокет
        try {
            socket = device.createRfcommSocketToServiceRecord(MY_UUID);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Выполняем соединение с устройством
        bluetooth.cancelDiscovery();
        try {
            socket.connect();
        } catch (IOException e) {
            e.printStackTrace();
            // Если нельзя соединиться
            try {
                socket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return null;
        }

        return socket;
    }

    public void cancel() {
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
