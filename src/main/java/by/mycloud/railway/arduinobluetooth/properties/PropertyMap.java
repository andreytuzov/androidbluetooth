package by.mycloud.railway.arduinobluetooth.properties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Andrey on 01.08.2017.
 */

public class PropertyMap {

    private Map<Integer, Property> propertyMap = new HashMap<>();

    public void addProperty(Property property) {
        propertyMap.put(property.getId(), property);
    }

    public Property getProperty(Integer id) {
        return propertyMap.get(id);
    }

    public Map<Integer, Property> getPropertyMap() {
        return propertyMap;
    }

    public void setPropertyMap(Map<Integer, Property> propertyMap) {
        this.propertyMap = propertyMap;
    }

}
