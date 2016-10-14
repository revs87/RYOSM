
package com.ryosm.core.com.ryosm.objects;

import java.io.Serializable;

public class KeyValueObject implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 6865693095155269883L;

    public String key;

    public String value;

    public KeyValueObject() {
        super();
    }

    public KeyValueObject(String key, String value) {
        super();
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String title) {
        this.key = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
