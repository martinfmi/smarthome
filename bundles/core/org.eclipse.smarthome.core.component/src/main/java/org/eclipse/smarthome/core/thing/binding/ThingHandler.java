package org.eclipse.smarthome.core.thing.binding;

import org.eclipse.smarthome.core.thing.Channel;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.types.Command;
import org.eclipse.smarthome.core.types.State;

public interface ThingHandler<C extends ThingConfiguration> {

    void setThing(Thing thing);

    Thing getThing();

    void handleCommand(Channel channel, Command command);

    void handleUpdate(Channel channel, State newState);

    void updated(C configuration);

    C getConfiguration();

    ThingHandler<?> getBridgeHandler();
}
