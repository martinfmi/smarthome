package org.eclipse.smarthome.core.thing;

import java.util.List;

import org.eclipse.smarthome.core.thing.binding.ThingHandler;

public interface Thing extends Identifiable {

    String getBindingId();

    List<Channel> getChannels();

    ThingStatus getStatus();

    String getFactoryPid();

    String getConfigurationPid();

    void setStatus(ThingStatus status);

    void setHandler(ThingHandler<?> thingHandler);

    ThingHandler<?> getHandler();

    Bridge getBridge();

}
