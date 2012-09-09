package de.knallisworld.spring.worker.mapping;

public class Job {

	private String type;

	private Workspace workspace;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Workspace getWorkspace() {
		return workspace;
	}

	public void setWorkspace(Workspace workspace) {
		this.workspace = workspace;
	}

}
