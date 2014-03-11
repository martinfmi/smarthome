package org.eclipse.smarthome.sample.hue.handler;

import java.io.IOException;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.eclipse.smarthome.config.core.annotation.Default;
import org.eclipse.smarthome.core.thing.Channel;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandler;
import org.eclipse.smarthome.core.thing.binding.ThingConfiguration;
import org.eclipse.smarthome.core.types.Command;
import org.eclipse.smarthome.sample.hue.handler.HueBridgeHandler.HueBridgeConfiguration;

public class HueBridgeHandler extends BaseThingHandler<HueBridgeConfiguration> {

    HttpClient httpClient;

    public HueBridgeHandler(HueBridgeConfiguration configuration) {
        super(configuration);
    }

    public static class HueBridgeConfiguration extends ThingConfiguration {
        public String ipAddress;
        @Default("ESH")
        public String secret;
        public String id;
    }

    @Override
    protected void initialize(Thing component, HueBridgeConfiguration configuration) {
        httpClient = new DefaultHttpClient();
    }

    @Override
    public void handleCommand(Channel componentPort, Command command) {
        // not needed
    }

    public void setLampBrightness(int lampId, int brightness) {
        try {
            final HttpPut httpRequest = new HttpPut(getLampStateUrl(lampId));
            String body = "{\"bri\":" + brightness + ",\"on\":true}";
            StringEntity requestEntity = new StringEntity(body, "application/json", "UTF-8");
            httpRequest.setEntity(requestEntity);
            httpClient.execute(httpRequest);
        } catch (IOException ex) {
            // TODO Auto-generated catch block
            ex.printStackTrace();
        }
    }

    private String getLampStateUrl(int lampId) {
        return getBridgeRestApiUrl() + "lights/" + lampId + "/state";
    }

    private String getBridgeRestApiUrl() {
        return "http://" + getConfiguration().ipAddress + "/api/" + getConfiguration().secret + "/";
    }
}
