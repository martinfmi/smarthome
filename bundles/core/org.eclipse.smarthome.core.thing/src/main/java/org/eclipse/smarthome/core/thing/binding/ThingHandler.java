package org.eclipse.smarthome.core.thing.binding;

import org.eclipse.smarthome.config.core.Configuration;
import org.eclipse.smarthome.core.items.Item;
import org.eclipse.smarthome.core.thing.Channel;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.types.Command;
import org.eclipse.smarthome.core.types.State;

/**
 * A {@link ThingHandler} can 'handle' a {@link Thing}. It must be registered as
 * OSGi service with the PID of the corresponding {@link Thing} as service
 * property 'handler.pid'. When a {@link Command} is sent to an {@link Item} and
 * the item is bound to a channel, the handler of the corresponding thing will
 * receive the command via the
 * {@link ThingHandler#handleCommand(Channel, Command)} method.
 * 
 * @param <C>
 *            Concrete {@link Configuration} type
 * 
 * @author Dennis Nobel - Initial contribution and API
 */
public interface ThingHandler<C extends ThingConfiguration> {

    void setThing(Thing thing);

    Thing getThing();

    void handleCommand(Channel channel, Command command);

    void handleUpdate(Channel channel, State newState);

    void updated(C configuration);

    C getConfiguration();

    ThingHandler<?> getBridgeHandler();
}
