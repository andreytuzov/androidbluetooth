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
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import by.mycloud.railway.arduinobluetooth.enable.SocketEnable;
import by.mycloud.railway.arduinobluetooth.properties.IPropertyHandler;
import by.mycloud.railway.arduinobluetooth.properties.Property;
import by.mycloud.railway.arduinobluetooth.view.AdapterHelper;

public class HomeActivity extends AppCompatActivity {

    private static final Logger logger = Logger.getLogger(HomeActivity.class.getName());

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_layout);

        Intent intent = new Intent(this, MainActivity.class);
        startActivityForResult(intent, 1);

        textView = (TextView) findViewById(R.id.tv);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        textView.setText("statusCode=" + String.valueOf(data.getIntExtra("statusCode", -1)));


        addText(BluetoothManager.getInstance().getBluetoothContext().getBluetooth().isEnabled() + "");
        Property property = new Property(new Character('1'), null, new IPropertyHandler() {
            @Override
            public void handle(Object obj) {
                addText(obj.toString());
            }
        });
        BluetoothManager.getInstance().createProperty(property);
    }

    private void addText(String text) {
        textView.setText(textView.getText() + "\n" + text);
    }
}
