package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;

public class NewMessage implements Serializable {
    Object object;
    String message;
    public NewMessage(Object object, String message) {
        this.object = object;
        this.message = message;
    }

    public NewMessage(String message) {
        this.message = message;
    }

    public Object getObject() {
        return object;
    }
    public String getMessage() {
        return message;
    }
    public void setObject(Object object) {
        this.object = object;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}