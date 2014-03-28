package org.eclipse.smarthome.config.setup.handler;


public interface ExternalEventStepHandler {

    void execute(Object thing);   // TODO

    void abort() throws Exception;

}
