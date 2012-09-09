package de.knallisworld.spring.worker.task.impl;

import de.knallisworld.spring.worker.mapping.Result;
import de.knallisworld.spring.worker.mapping.Workspace;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.Map;

public class AbstractTaskImpl {

	protected Workspace workspace;
	protected ApplicationContext ctx;

	public AbstractTaskImpl(ApplicationContext ctx, Workspace workspace) {
		this.ctx = ctx;
		this.workspace = workspace;
	}

	protected Result buildResult() {
		return buildResult(false);
	}

	protected Result buildResult(boolean successful) {
		Result result = new Result();
		result.setSuccess(successful);
		return result;
	}

	protected Map<String, String> filterEntriesByPrefix(Map<String, String> map, String prefixKey) {
		Map<String, String> result = null;

		if (map != null) {
			result = new HashMap<String, String>();
			final String prefix = prefixKey + ".";
			for (Map.Entry<String, String> entry : map.entrySet()) {
				if (entry.getKey().startsWith(prefix)) {
					final String key = entry.getKey().substring(prefix.length());
					final String value = entry.getValue();
					result.put(key, value);
				}
			}
		}

		return result;
	}
}
