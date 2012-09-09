package de.knallisworld.spring.worker.task.impl;

import de.knallisworld.spring.worker.mapping.Workspace;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class PhantomJsTaskImpl extends ProcessTaskImpl {

	public PhantomJsTaskImpl(ApplicationContext ctx, Workspace workspace) {
		super(ctx, workspace);
	}

	@Override
	protected ProcessBuilder buildProcessBuilder() {
		if (!workspace.getParams().containsKey("executable")) {
			workspace.getParams().put("executable", "phantomjs");
		}
		return super.buildProcessBuilder();
	}

	@Override
	protected void applyArguments(List<String> commands, Map<String, String> params) {
		String script = params.get("script");
		File scriptFile = null;

		if (StringUtils.hasText(script)) {
			// Try to find the script assuming as a path.
			if (scriptFile == null) {
				scriptFile = findFilePath(script);
			}
			// Try to find in internal script repository w/o extension.
			if (scriptFile == null) {
				final URL resource = getClass().getClassLoader().getResource("phantomjs-scripts/" + script);
				if (resource != null) {
					scriptFile = findFilePath(resource.getFile());
				}
			}
			// Try to find in internal script repository w/ extension.
			if (scriptFile == null) {
				final URL resource = getClass().getClassLoader().getResource("phantomjs-scripts/" + script + ".js");
				if (resource != null) {
					scriptFile = findFilePath(resource.getFile());
				}
			}
		}

		// Apply script argument.
		if (scriptFile != null) {
			commands.add(scriptFile.getAbsolutePath());
		}

		super.applyArguments(commands, params);
	}

}
