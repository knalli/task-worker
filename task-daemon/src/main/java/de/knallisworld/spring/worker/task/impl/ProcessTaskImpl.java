package de.knallisworld.spring.worker.task.impl;

import de.knallisworld.spring.worker.mapping.Workspace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class ProcessTaskImpl extends AbstractProcessTaskImpl {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessTaskImpl.class);

	public ProcessTaskImpl(ApplicationContext ctx, Workspace workspace) {
		super(ctx, workspace);
	}

	@Override
	protected ProcessBuilder buildProcessBuilder() {
		ProcessBuilder builder = new ProcessBuilder();

		List<String> commands = new ArrayList<String>();
		commands.addAll(buildCommandArguments());

		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("Commands: " + StringUtils.collectionToCommaDelimitedString(commands));
		}

		builder.command(commands);

		return builder;
	}

	private Collection<? extends String> buildCommandArguments() {
		List<String> commands = new ArrayList<String>();

		final Map<String, String> params = workspace.getParams();

		final String executable = params.get("executable");
		Assert.hasLength(executable, "No executable was defined.");

		commands.add(executable);
		applyArguments(commands, params);

		return commands;
	}


}
