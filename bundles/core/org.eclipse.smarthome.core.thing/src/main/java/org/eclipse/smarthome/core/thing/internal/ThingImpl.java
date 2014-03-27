package org.eclipse.smarthome.core.thing.internal;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.eclipse.smarthome.core.thing.Bridge;
import org.eclipse.smarthome.core.thing.Channel;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingStatus;
import org.eclipse.smarthome.core.thing.binding.ThingConfiguration;
import org.eclipse.smarthome.core.thing.binding.ThingHandler;
import org.eclipse.smarthome.core.types.State;

public class ThingImpl implements Thing {

    public interface ChannelUpdateListener {
        void channelUpdated(String channelId, State state);
    }

    private String bindingId;

    private BridgeImpl bridge;

    private List<Channel> channels;

    private List<ChannelUpdateListener> channelUpdateListeners = new CopyOnWriteArrayList<>();

    private ThingConfiguration configuation;

    private String configurationPid;

    private String factoryPid;

    private String id;

    private ThingStatus status;

    private ThingHandler<?> thingHandler;

    public ThingImpl(String factoryPid, String id) {
        this.factoryPid = factoryPid;
        this.id = id;
    }

    public void addChannelUpdateListener(ChannelUpdateListener channelUpdateListener) {
        this.channelUpdateListeners.add(channelUpdateListener);
    }

    @Override
    public void channelUpdated(String channelId, State state) {
        for (ChannelUpdateListener channelUpdateListener : channelUpdateListeners) {
            channelUpdateListener.channelUpdated(channelId, state);
        }
    }

    public String getBindingId() {
        return bindingId;
    }

    @Override
    public Bridge getBridge() {
        return this.bridge;
    }

    public List<Channel> getChannels() {
        return channels;
    }

    public String getConfigurationPid() {
        return configurationPid;
    }

    public String getFactoryPid() {
        return factoryPid;
    }

    @Override
    public ThingHandler<?> getHandler() {
        return this.thingHandler;
    }

    public String getId() {
        return id;
    }

    public ThingStatus getStatus() {
        return status;
    }

    public void removeChannelUpdateListener(ChannelUpdateListener channelUpdateListener) {
        this.channelUpdateListeners.remove(channelUpdateListener);
    }

    public void setBindingId(String bindingId) {
        this.bindingId = bindingId;
    }

    @Override
    public void setBridge(Bridge bridge) {
        this.bridge = (BridgeImpl) bridge;
        if (bridge != null) {
            this.bridge.addThing(this);
        } else {
            this.bridge.removeThing(this);
        }
    }

    public void setChannels(List<Channel> channels) {
        this.channels = channels;
    }

    public void setConfiguration(ThingConfiguration configuration) {
        this.configuation = configuration;
    }

    public void setConfigurationPid(String configurationPid) {
        this.configurationPid = configurationPid;
    }

    public void setFactoryPid(String factoryPid) {
        this.factoryPid = factoryPid;
    }

    @Override
    public void setHandler(ThingHandler<?> thingHandler) {
        this.thingHandler = thingHandler;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setStatus(ThingStatus status) {
        this.status = status;
    }

}
