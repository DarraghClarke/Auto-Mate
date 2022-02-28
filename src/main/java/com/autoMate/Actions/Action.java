package com.autoMate.Actions;

import com.google.gson.JsonObject;
import com.autoMate.objects.Options;

/**
 * This Action object represents the basic concept of an action in Tiny Tines,
 * Every Action has a type, options and a name:
 * - Name is used for identifying a specific action
 * - Response is usually placed into the event that is passed from Action to Action
 * - Options are data that every action should have, representing Url and Messages for the two existing types
 */
public interface Action {
    String getName();

    Options getOptions();

    JsonObject getResponse();
}
