package by.mycloud.railway.arduinobluetooth.connection;

import java.util.Map;

import by.mycloud.railway.arduinobluetooth.properties.PropertyMap;
import by.mycloud.railway.arduinobluetooth.properties.Property;

/**
 * Created by Andrey on 01.08.2017.
 */

public class BluetoothRWrapper implements IBluetoothRWrapper {

    private Map<Integer, Property> propertyMap;

    public BluetoothRWrapper(PropertyMap bData) {
        this.propertyMap = bData.getPropertyMap();
    }

    @Override
    public void handleReceiverData(String str) {
        // If bytes is not correct
        if (str == null || str.length() == 0) {
            return;
        }
        // Create or get property component
        int id = str.charAt(0);
        Property property;
        if (propertyMap.containsValue(id)) {
            property = propertyMap.get(id);
        } else {
            property = new Property(id);
        }
        // Set a value of property
        if (str.length() > 1) {
            property.setValue(str.substring(1));
        }
    }
}
