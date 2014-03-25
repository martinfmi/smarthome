package org.eclipse.smarthome.core.thing.internal;

import java.util.List;

import org.eclipse.smarthome.core.thing.Bridge;
import org.eclipse.smarthome.core.thing.Channel;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingStatus;
import org.eclipse.smarthome.core.thing.binding.ThingConfiguration;
import org.eclipse.smarthome.core.thing.binding.ThingHandler;

public class ThingImpl implements Thing {

    private String bindingId;

    private String configurationPid;

    private String factoryPid;

    private String id;

    private List<Channel> channels;

    private ThingStatus status;

    private ThingHandler<?> thingHandler;

    private Bridge bridge;

    private ThingConfiguration configuation;

    public ThingImpl(String factoryPid, String id) {
        this.factoryPid = factoryPid;
        this.id = id;
    }

    public String getBindingId() {
        return bindingId;
    }

    public String getConfigurationPid() {
        return configurationPid;
    }

    public String getFactoryPid() {
        return factoryPid;
    }

    public String getId() {
        return id;
    }

    public List<Channel> getChannels() {
        return channels;
    }

    public ThingStatus getStatus() {
        return status;
    }

    public void setBindingId(String bindingId) {
        this.bindingId = bindingId;
    }

    public void setConfigurationPid(String configurationPid) {
        this.configurationPid = configurationPid;
    }

    public void setConfiguration(ThingConfiguration configuration) {
        this.configuation = configuration;
    }

    public void setFactoryPid(String factoryPid) {
        this.factoryPid = factoryPid;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setChannels(List<Channel> channels) {
        this.channels = channels;
    }

    public void setStatus(ThingStatus status) {
        this.status = status;
    }

    @Override
    public void setHandler(ThingHandler<?> thingHandler) {
        this.thingHandler = thingHandler;
    }

    @Override
    public ThingHandler<?> getHandler() {
        return this.thingHandler;
    }

    @Override
    public Bridge getBridge() {
        return this.bridge;
    }

    @Override
    public void setBridge(Bridge bridge) {
        this.bridge = bridge;
    }

}
