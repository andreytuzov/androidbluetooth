package by.mycloud.railway.arduinobluetooth.properties;

import java.io.Serializable;

/**
 * Created by Andrey on 01.08.2017.
 */

public class Property implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private IPropertyHandler handler;
    private String value;

    public Property(int id) {
        this.id = id;
    }

    public Property(int id, String value) {
        this.id = id;
        this.value = value;
    }

    public Property(IPropertyHandler handler) {
        this.handler = handler;
    }

    public Property(int id, String value, IPropertyHandler handler) {
        this.id = id;
        this.value = value;
        this.handler = handler;
    }

    public IPropertyHandler getHandler() {
        return handler;
    }

    public void setHandler(IPropertyHandler handler) {
        this.handler = handler;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
        if (handler != null) {
            handler.handle(value);
        }
    }

    public int getId() {
        return id;
    }


}
