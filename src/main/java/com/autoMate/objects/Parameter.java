package com.autoMate.objects;

import java.util.Arrays;

/**
 * This object represents the Parameters passed in through Urls and Messages
 *
 * In its constructor it cleans the parameters of formatting, it then splits by .'s
 * saving the first value as the name of the action it references and the rest as the path to the real value
 */
public class Parameter {
    String[] path;
    String actionName;

    public Parameter(String parameter) {
        //remove formatting
        parameter = parameter.substring(2, parameter.length() - 2);
        String[] splitParam = parameter.split("\\.");
        //assign values
        this.actionName = splitParam[0];
        this.path = Arrays.copyOfRange(splitParam, 1, splitParam.length);
    }

    public String getActionName() {
        return actionName;
    }

    public String[] getPath() {
        return path;
    }
}
