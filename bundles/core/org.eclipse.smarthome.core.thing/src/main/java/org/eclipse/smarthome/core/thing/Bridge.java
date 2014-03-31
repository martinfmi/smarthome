package org.eclipse.smarthome.core.thing;

import java.util.List;

/**
 * A {@link Bridge} is a {@link Thing} that connects other {@link Thing}s.
 * 
 * @author Dennis Nobel - Initial contribution and API
 */
public interface Bridge extends Thing {

    List<Thing> getThings();

}
