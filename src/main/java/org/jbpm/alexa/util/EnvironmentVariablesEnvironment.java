package org.jbpm.alexa.util;

public class EnvironmentVariablesEnvironment implements Environment {
	 
	private static final String CONTAINER_ID = System.getenv("CONTAINER_ID");
	
	private static final String KIE_SERVER_URL = System.getenv("KIE_SERVER_URL");
	
	private static final String TASK_USER = System.getenv("TASK_USER");
	
	private static final String KIE_SERVER_USER = System.getenv("KIE_SERVER_USER");
	
	private static final String KIE_SERVER_PASSWORD = System.getenv("KIE_SERVER_PASSWORD");
	
	
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
		return TASK_USER;
	}

	@Override
	public String getKieServerUser() {
		if (KIE_SERVER_USER == null || "".equals(KIE_SERVER_USER)) {
			return "kieserver";
		}
		return KIE_SERVER_USER;
	}

	@Override
	public String getKieServerPassword() {
		if (KIE_SERVER_PASSWORD == null || "".equals(KIE_SERVER_PASSWORD)) {
			return "kieserver1!";
		}
		return KIE_SERVER_PASSWORD;
	}
	
	

}
