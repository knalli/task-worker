package de.knallisworld.spring.worker.mapping;

import java.util.HashMap;
import java.util.Map;

public class Workspace {

	private Map<String, String> files;

	private Map<String, String> params;

	public Map<String, String> getFiles() {
		if (files == null) {
			setFiles(new HashMap<String, String>());
		}
		return files;
	}

	protected void setFiles(Map<String, String> files) {
		this.files = files;
	}

	public Map<String, String> getParams() {
		if (params == null) {
			setParams(new HashMap<String, String>());
		}
		return params;
	}

	protected void setParams(Map<String, String> params) {
		this.params = params;
	}

	public void addFile(String key, String file) {
		getFiles().put(key, file);
	}

	public void addParam(String key, String file) {
		getParams().put(key, file);
	}
}
