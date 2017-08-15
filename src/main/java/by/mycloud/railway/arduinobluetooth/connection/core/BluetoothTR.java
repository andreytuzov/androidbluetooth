package by.mycloud.railway.arduinobluetooth.connection.core;

import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Logger;

import by.mycloud.railway.arduinobluetooth.connection.IBluetoothRWrapper;

/**
 * Created by Andrey on 01.08.2017.
 */

public class BluetoothTR extends Thread implements IBluetoothT {

    private static final Logger logger = Logger.getLogger(BluetoothTR.class.getName());

    private final InputStream inStream;
    private final OutputStream outStream;
    private IBluetoothRWrapper handler;

    public BluetoothTR(BluetoothSocket socket, IBluetoothRWrapper handler) {
        this.handler = handler;
        InputStream tmpInStream = null;
        OutputStream tmpOutStream = null;
        try {
            tmpInStream = socket.getInputStream();
            tmpOutStream = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        inStream = tmpInStream;
        outStream = tmpOutStream;
    }


    // Чтение из потока
    @Override
    public void run() {
        byte buffer[];
        int bytes;

        logger.info("Ожидание приема данных");

        while (true) {
            try {
                try {
                    Thread.currentThread().sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                buffer = new byte[1024];
                bytes = inStream.read(buffer);

                logger.info("Принято " + bytes + " байт: " +  new String(buffer, 0, bytes));

                handler.handleReceiverData(new String(buffer, 0, bytes));
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    // Запись в поток
    public void write(byte[] bytes) {
        try {
            outStream.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
