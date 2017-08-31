package com.sistemantrianonline.antrianonline.models;

/**
 * Created by made saguna on 8/29/2017.
 */

public class KeyVal {
    private String id;
    private String label;

    public KeyVal(){}

    public KeyVal(String id, String label)
    {
        this.id = id;
        this.label = label;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String toString() {
        return label;
    }
}

