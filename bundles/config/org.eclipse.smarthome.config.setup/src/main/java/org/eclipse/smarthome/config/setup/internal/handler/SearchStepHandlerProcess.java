package org.eclipse.smarthome.config.setup.internal.handler;

import org.eclipse.smarthome.config.setup.handler.SearchStepHandler;
import org.eclipse.smarthome.config.setup.handler.SearchStepHandlerCallback;


public class SearchStepHandlerProcess extends GenericSetupStepProcess {

    private SearchStepHandler stepHandler;
    private SearchStepHandlerCallback searchStepHandlerCallback;


    public SearchStepHandlerProcess(SearchStepHandler stepHandler)
            throws IllegalArgumentException {

        if (stepHandler == null) {
            throw new IllegalArgumentException("The SearchStepHandler must not be null!");
        }

        this.stepHandler = stepHandler;
        this.searchStepHandlerCallback = new DefaultSearchStepHandlerCallback();
    }

    @Override
    protected final void startProcess() throws Exception {
        this.stepHandler.search(this.searchStepHandlerCallback);
    }

    @Override
    protected final void abortProcess() throws Exception {
        this.stepHandler.abort();
    }

}
