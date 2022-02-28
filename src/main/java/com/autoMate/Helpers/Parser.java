package com.autoMate.Helpers;

import com.google.gson.JsonObject;
import com.autoMate.objects.Parameter;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This Class holds some methods that are used as part of parsing parameter paths
 * It is shared by both Actions
 */
public class Parser {
    static final String REGEX = "\\{(\\{(.+?)})}";

    /**
     * This method takes in a string that may contain variables,
     * it then attempts to swap in the real values, or error handle a misconfigured string
     *
     * @param eventLog the event containing the output of all previous actions
     * @param inputString the url or message that may have variables to be replaced
     */
    public static String format(Map<String, JsonObject> eventLog, String inputString) {
        //This Matcher searches for parameter place holders in the input string
        Matcher matcher = Pattern.compile(REGEX).matcher(inputString);
        while(matcher.find()) {
            Parameter parameter = new Parameter(matcher.group(0));

            //if a parameter is found, the name of the action it references is checked against the eventLog
            //if no match is found, an empty string is placed, otherwise find the real value
            if (eventLog == null || eventLog.get(parameter.getActionName()) == null) {
                inputString = matcher.replaceFirst("");
            } else {
                //Get the relevant actions JsonObject from the eventLog
                JsonObject response = eventLog.get(parameter.getActionName());
                //For space & clarity, searching for variables was moved to its own method
                inputString = searchForVariable(parameter, response, matcher);
            }
            //pass the updated string to the matcher to continue looping
            matcher = Pattern.compile(REGEX).matcher(inputString);
        }

        //Once all values are swapped, return to be used
        return inputString;
    }

    /**
     * Given a parameter and a jsonObject of the corresponding action's response
     * search within the object for the right value and return it
     *
     * @param parameter this is an object representing the variables found in the input Json, i.e. Location.Variable
     * @param response this holds the information of any action referenced by parameter
     * @param m The matcher used to find and replace the variable
     * @return string value for parameter
     */
    public static String searchForVariable(Parameter parameter, JsonObject response, Matcher m){//todo rename relavent action data
        String result = null;
        String[] path = parameter.getPath();

        //If the parameter doesn't have a nested path the check is straightforward,
        //else the layers of the json are stripped back until the value is found
        if (path.length == 1) {
            result = m.replaceFirst(response.get(path[0]).getAsString());
        } else {
            JsonObject innerResponse = response;

            //take each step of the path one by one
            for (int j = 0; j < path.length; j++) {

                //The expected object during the dive is JsonObject, until the final layer where a JsonElement is return
                //This is why the loop splits into an else branch for its last run
                if (j < path.length - 1) {
                    //search the json, if an error occurs replace the parameter with empty string
                    try {
                        innerResponse = innerResponse.get(path[j]).getAsJsonObject();
                    } catch (Exception e) {
                        //if this was production this would be logged somewhere
                    } finally {
                        result = m.replaceFirst("");
                    }

                } else {
                    //similar to above, a null result means no value was found and gets replaced by an empty string
                    //otherwise the result is placed in the string
                    if (innerResponse.get(path[j]) != null) {
                        result = m.replaceFirst(innerResponse.get(path[j]).getAsString());
                    } else {
                        result = m.replaceFirst("");
                    }
                }
            }
        }

        return result;
    }
}
