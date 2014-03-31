package org.eclipse.smarthome.config.setup.handler;


public interface ExternalEventStepHandler {

    void execute(Object thing) throws Exception;   // TODO

    void abort() throws Exception;

}
