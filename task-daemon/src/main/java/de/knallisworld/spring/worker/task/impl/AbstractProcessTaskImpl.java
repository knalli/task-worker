package de.knallisworld.spring.worker.task.impl;

import de.knallisworld.spring.worker.mapping.Result;
import de.knallisworld.spring.worker.mapping.Workspace;
import de.knallisworld.spring.worker.task.Task;
import org.apache.commons.io.IOUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

abstract public class AbstractProcessTaskImpl extends AbstractTaskImpl implements Task {

	public AbstractProcessTaskImpl(ApplicationContext ctx, Workspace workspace) {
		super(ctx, workspace);
	}

	@Override
	public Result call() throws Exception {
		ProcessBuilder processBuilder = buildProcessBuilder();

		Process process = processBuilder.start();
		process.waitFor();

		List<String> lines = IOUtils.readLines(new InputStreamReader(process.getInputStream()));

		Result result = new Result();
		result.setSuccess(process.exitValue() == 0);
		result.setMessage(StringUtils.collectionToDelimitedString(lines, "\n"));

		return result;
	}

	protected abstract ProcessBuilder buildProcessBuilder();

	/**
	 * Return the found file only if it exist and is readable.
	 *
	 * @param filePath
	 *
	 * @return
	 */
	protected static File findFilePath(String filePath) {
		File scriptFile;
		scriptFile = new File(filePath);
		if (!scriptFile.canRead()) {
			scriptFile = null;
		}
		return scriptFile;
	}

	protected void applyArguments(List<String> commands, Map<String, String> params) {
		// Apply the arguments. Well, the first 99 bottles only.
		for (int i = 1; i < 99; i++) {
			String key = "arg" + i;
			if (params.containsKey(key + "@randomFile")) {
				String[] parts = params.get(key + "@randomFile").split("\\.", 2);
				String prefix = parts[0];
				String suffix = (parts[1] != null) ? parts[1] : "pdf";
				try {
					commands.add(File.createTempFile(prefix, "." + suffix).getAbsolutePath());
				} catch (IOException e) {
					commands.add(params.get(key));
				}
			} else if (params.containsKey(key)) {
				commands.add(params.get(key));
			} else {
				break;
			}
		}
	}
}
