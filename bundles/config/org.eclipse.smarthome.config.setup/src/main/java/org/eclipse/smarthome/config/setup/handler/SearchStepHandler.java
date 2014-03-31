package org.eclipse.smarthome.config.setup.handler;


public interface SearchStepHandler extends SetupStepHandler {

    void search(SearchStepHandlerCallback callback) throws Exception;

    void abort() throws Exception;

}
