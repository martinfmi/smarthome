package org.eclipse.smarthome.core.thing;

import java.util.List;

import org.eclipse.smarthome.core.items.Item;
import org.eclipse.smarthome.core.thing.binding.ThingHandler;

/**
 * A {@link Thing} is a representation of a connected part (e.g. physical device
 * or cloud service) from the real world. It contains a list of {@link Channel}
 * s, which can be bound to {@link Item}s. A {@link Thing} might be connected
 * through a {@link Bridge}.
 * 
 * @author Dennis Nobel - Initial contribution and API
 */
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

    void setBridge(Bridge bridge);

}
