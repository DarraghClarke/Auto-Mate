package com.tinyTines.Actions;

import com.google.gson.JsonObject;
import com.tinyTines.objects.Options;

public class PrintAction implements Action {

    public JsonObject response;
    public String name;
    public Options options;

    public PrintAction(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Options getOptions() {
        return options;
    }

    @Override
    public JsonObject getResponse() {
        return response;
    }
}
