package org.jbpm.alexa.util;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class EnvironmentProducer {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EnvironmentProducer.class);

	private static final String ENVIRONMENT_PROPERTY_NAME = "org.jbpm.alexa.environment";
	
	private static final String ENVIRONMENT = System.getProperty(ENVIRONMENT_PROPERTY_NAME, "EnvironmentVariables");
	
	@Produces @ApplicationScoped
	public Environment getEnvironment() {
		
		switch (ENVIRONMENT) {
			case "EnvironmentVariables": 
				LOGGER.info("Setting EnvironmentVariables environment.");
				return new EnvironmentVariablesEnvironment();
			case "SystemProperties": 
				LOGGER.info("Setting SystemProperties environment.");
				return new SystemPropertiesEnvironment();
			default:
				LOGGER.warn("No enviroment set. Returning null.");
				return null;
		}
	}
}
