package org.eclipse.smarthome.core.thing.internal.console;

import java.util.List;

import org.eclipse.smarthome.core.thing.Bridge;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingRegistry;
import org.eclipse.smarthome.core.thing.ThingStatus;
import org.eclipse.smarthome.io.console.Console;
import org.eclipse.smarthome.io.console.extensions.ConsoleCommandExtension;


public class ThingConsoleCommandExtension implements ConsoleCommandExtension {

    private ThingRegistry thingRegistry;

    @Override
    public boolean canHandle(String[] args) {
        return args[0].equals("things");
    }

    @Override
    public void execute(String[] args, Console console) {
        if (args[0].equals("things")) {
            List<Thing> things = thingRegistry.getThings();

            if (things.isEmpty()) {
                console.println("No things found.");
            }

            for (Thing thing : things) {

                String id = thing.getId();
                String thingType = thing instanceof Bridge ? "Bridge" : "Thing";
                ThingStatus status = thing.getStatus();

                console.println(String.format("%s (Type=%s, Status=%s)", id, thingType, status));
            }
        }
    }

    @Override
    public String getUsage() {
        return "lists all things";
    }

    protected void setThingRegistry(ThingRegistry thingRegistry) {
        this.thingRegistry = thingRegistry;
    }

    protected void unsetThingRegistry(ThingRegistry thingRegistry) {
        this.thingRegistry = null;
    }

}
