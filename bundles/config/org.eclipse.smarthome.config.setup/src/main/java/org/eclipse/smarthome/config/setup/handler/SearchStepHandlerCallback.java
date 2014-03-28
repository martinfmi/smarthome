package org.eclipse.smarthome.config.setup.handler;


public interface SearchStepHandlerCallback {

    void thingFound(Object thing);  // TODO

    void searchCompleted();

    void errorOccurred(Exception exception);

}
