package com.autoMate.Actions;

import com.google.gson.JsonObject;
import com.autoMate.objects.Options;

public class TriggerAction implements Action{

    private String name;
    private Options options;
    private JsonObject response;


    public TriggerAction(String name){
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
