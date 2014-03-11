package org.eclipse.smarthome.core.thing;


/**
 * {@link Identifiable} can be implemented by classes that can be identified by
 * a string.
 * 
 * @author Dennis Nobel - Initial contribution and API
 */
public interface Identifiable {

    /**
     * Returns the identifier of the identifiable object
     * 
     * @return identifier
     */
    String getId();
}
