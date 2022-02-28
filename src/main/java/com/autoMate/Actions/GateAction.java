package com.autoMate.Actions;

import com.autoMate.Helpers.Parser;
import com.google.gson.JsonObject;
import com.autoMate.objects.Options;

import java.util.Map;

public class GateAction implements Action{

    private String name;
    private Options options;
    private JsonObject response;

    public GateAction(String name){
        this.name = name;
    }

    public boolean compare(Map<String, JsonObject> eventLog){
        String valueName = Parser.format(eventLog, getOptions().getValue());
        String expectedValue = Parser.format(eventLog, getOptions().getExpectedResult());

        return valueName.equals(expectedValue);
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
