package org.jbpm.alexa.util;

public class EnvironmentVariablesEnvironment implements Environment {
	 
	private static final String CONTAINER_ID = System.getenv("CONTAINER_ID");
	
	private static final String KIE_SERVER_URL = System.getenv("KIE_SERVER_URL");
	
	private static final String TASK_USER = System.getenv("TASK_USER");
	
	@Override
	public String getContainerId() {
		//TODO: Using a cart-id set via an env variable until we've sorted out authentication.
		return CONTAINER_ID;
	}

	@Override
	public String getKieServerUrl() {
		return KIE_SERVER_URL;
	}

	@Override
	public String getTaskUser() {
		// TODO Auto-generated method stub
		return TASK_USER;
	}

}
