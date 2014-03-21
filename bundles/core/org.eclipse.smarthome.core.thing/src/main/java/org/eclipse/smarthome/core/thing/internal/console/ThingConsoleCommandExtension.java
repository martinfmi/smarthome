package org.eclipse.smarthome.core.thing.internal.console;

import org.eclipse.smarthome.io.console.Console;
import org.eclipse.smarthome.io.console.extensions.ConsoleCommandExtension;


public class ThingConsoleCommandExtension implements ConsoleCommandExtension {

    @Override
    public boolean canHandle(String[] args) {
        return args[0].equals("things");
    }

    @Override
    public void execute(String[] args, Console console) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public String getUsage() {
        return "lists all things";
    }

}
