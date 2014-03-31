package org.eclipse.smarthome.config.setup.internal.handler;

import org.eclipse.smarthome.config.setup.SetupStepProcess;
import org.eclipse.smarthome.config.setup.handler.ConfigurationStepHandler;
import org.eclipse.smarthome.config.setup.handler.ExternalEventStepHandler;
import org.eclipse.smarthome.config.setup.handler.SearchStepHandler;
import org.eclipse.smarthome.config.setup.handler.SetupStepHandler;


public class SetupStepProcessBuilder {

    private SetupStepProcessBuilder() {
        // nothing to do
    }

    public static SetupStepProcess createSetupStepProcess(SetupStepHandler stepHandler) {
        return createSetupStepProcess(stepHandler);
    }

    private static SetupStepProcess createSetupStepProcess(ConfigurationStepHandler stepHandler) {
        return new ConfigurationStepHandlerProcess(stepHandler);
    }

    private static SetupStepProcess createSetupStepProcess(ExternalEventStepHandler stepHandler) {
        return new ExternalEventStepHandlerProcess(stepHandler);
    }

    private static SetupStepProcess createSetupStepProcess(SearchStepHandler stepHandler) {
        return new SearchStepHandlerProcess(stepHandler);
    }

}
