package com.tinyTines;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.tinyTines.Actions.HttpRequestAction;
import com.tinyTines.Actions.PrintAction;
import com.tinyTines.Helpers.Parser;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Entry point for the Tiny Tines app,
 *
 * This App takes in a tinyTines story in json format, parses it,
 * splits it into actions and then executes those actions.
 *
 * [Doesn't tell jokes, sometimes tells error logs]
 * Darragh Clarke
 */
public class TinyTinesApp {

    public static void main(String[] args) {
        //if no values are passed in, exit
        if (args == null || args[0]==null) {
            return;
        }

        try {
            //parse the expected json
            Gson gson = new Gson();
            Reader reader = Files.newBufferedReader(Paths.get(args[0]));

            //create an array of actionsJson
            JsonArray actionsJson = gson.fromJson(reader, JsonObject.class).get("actions").getAsJsonArray();
            reader.close();

            //create the event object that will be passed from action to action
            Map<String, JsonObject> eventLog = new HashMap<>();

            //iterate over all actionsJson
            for (int i = 0; i < actionsJson.size(); i++) {

                //based on action type, create either HttpRequest or Print actionsJson
                switch (actionsJson.get(i).getAsJsonObject().get("type").getAsString()) {
                    case "HTTPRequestAction":
                        //Turn the current action into a httpRequest action
                        HttpRequestAction httpRequestAction = gson.fromJson(actionsJson.get(i), HttpRequestAction.class);
                        //format it's url by assigning any variables
                        String formattedUrl = Parser.format(eventLog,httpRequestAction.getOptions().getUrl());
                        //Send the httpRequest and log any response
                        JsonObject response = httpRequestAction.fetch(formattedUrl);
                        eventLog.put(httpRequestAction.getName(), response);

                        break;

                    case "PrintAction":
                        //Turn the current action into a print action, then parse its message and assign variables
                        PrintAction printAction = gson.fromJson(actionsJson.get(i), PrintAction.class);
                        String output = Parser.format(eventLog, printAction.getOptions().getMessage());
                        //finally print
                        System.out.println(output);
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
