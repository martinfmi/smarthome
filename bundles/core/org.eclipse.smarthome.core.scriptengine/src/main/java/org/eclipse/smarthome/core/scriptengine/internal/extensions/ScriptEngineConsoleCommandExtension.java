package org.eclipse.smarthome.core.scriptengine.internal.extensions;

import org.apache.commons.lang.ArrayUtils;
import org.eclipse.smarthome.core.scriptengine.Script;
import org.eclipse.smarthome.core.scriptengine.ScriptEngine;
import org.eclipse.smarthome.core.scriptengine.ScriptExecutionException;
import org.eclipse.smarthome.core.scriptengine.ScriptParsingException;
import org.eclipse.smarthome.io.console.Console;
import org.eclipse.smarthome.io.console.extensions.ConsoleCommandExtension;

import com.google.common.base.Joiner;

public class ScriptEngineConsoleCommandExtension implements
		ConsoleCommandExtension {

	
	private ScriptEngine scriptEngine;
	
	@Override
	public boolean canHandle(String[] args) {
		String firstArg = args[0];
		return ">".equals(firstArg);	
	}

	@Override
	public void execute(String[] args, Console console) {
		// remove first argument
		args = (String[]) ArrayUtils.remove(args, 0);
		if(scriptEngine!=null) {
			String scriptString = Joiner.on(" ").join(args);
			Script script;
			try {
				script = scriptEngine.newScriptFromString(scriptString);
				Object result = script.execute();
				
				if(result!=null) {
					console.println(result.toString());
				} else {
					console.println("OK");
				}
			} catch (ScriptParsingException e) {
				console.println(e.getMessage());
			} catch (ScriptExecutionException e) {
				console.println(e.getMessage());
			}
		} else {
			console.println("Script engine is not available.");
		}
	}

	@Override
	public String getUsage() {
		return "> <script to execute> - Executes a script";
	}
	
	public void setScriptEngine(ScriptEngine scriptEngine) {
		this.scriptEngine = scriptEngine;
	}
	
	public void unsetScriptEngine(ScriptEngine scriptEngine) {
		this.scriptEngine = null;
	}
}
