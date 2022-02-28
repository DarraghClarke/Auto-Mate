package com.autoMate.Actions;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.autoMate.objects.Options;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpRequestAction implements Action {

    public String name;
    public JsonObject response;
    public Options options;

    public HttpRequestAction(String name) {
        this.name = name;
    }

    /**
     * This method carries out http requests
     *
     * @param url a correctly formatted url String
     * @return JsonObject containing the response of the method
     * @throws Exception if the response is 2xx throws and error that will ultimately close the program
     */
    public JsonObject fetch(String url) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        JsonObject toReturn = null;

        //if any brackets weren't cleaned up, translate to url friendly formatting
        url = url.replace("}","%7D");
        url = url.replace("{","%7B");

        //Make a HttpRequest
        try {
            HttpGet request = new HttpGet(url);
            CloseableHttpResponse httpResponse = httpClient.execute(request);

            //Get the Status code and check if its 2xx
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (!((statusCode >= 200) && (statusCode <= 299))) {
                //this will bubble up to end the program as per the instruction notes
                throw new Exception();
            }

            try {
                //Get the Response, if its not null, parse it to JsonObject and save
                HttpEntity entity = httpResponse.getEntity();
                if (entity != null) {
                    toReturn = JsonParser.parseString(EntityUtils.toString(entity)).getAsJsonObject();
                }

            } finally {
                httpResponse.close();
            }
        } finally {
            httpClient.close();
        }

        return toReturn;
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
