package com.tinyTines.objects;

/**
 * This object is used by the Action interface to represent the different options field that Actions can have
 * Beyond being used to get the values, this object is also used by Gson as part of its parsing of Actions
 */
public class Options {
    public String url;
    public String message;

    public String getMessage() {
        return message;
    }

    public String getUrl() {
        return url;
    }
}