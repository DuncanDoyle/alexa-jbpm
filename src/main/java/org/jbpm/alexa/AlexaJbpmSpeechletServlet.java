package org.jbpm.alexa;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;

import org.jbpm.alexa.util.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazon.speech.speechlet.servlet.SpeechletServlet;

/**
 * Main servlet for the jBPM Alexa interface.
 * 
 * @author <a href="mailto:duncan.doyle@redhat.com">Duncan Doyle</a>
 */
@WebServlet("/speech")
public class AlexaJbpmSpeechletServlet extends SpeechletServlet {

	private static final Logger LOGGER = LoggerFactory.getLogger(AlexaJbpmSpeechletServlet.class);

	@Inject
	private AlexaJbpmSpeechlet speechlet;

	@Inject
	private Environment environment;

	/**
	 * SerialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public AlexaJbpmSpeechletServlet() {
		LOGGER.info("Bootstrapping jBPM Alexa Skill.");
	}

	@PostConstruct
	public void init() {
		LOGGER.info("Post constructing skill. Setting speechlet: " + speechlet);
		this.setSpeechlet(speechlet);

		// Bit of debugging.
		LOGGER.warn("Environment config: \n" + "KIE-Server URL: " + environment.getKieServerUrl() + "\n"
				+ "Container-ID: " + environment.getContainerId() + "\n" + "KIE-Server User: "
				+ environment.getKieServerUser() + "\n" + "KIE-Server Password: " + environment.getKieServerPassword()
				+ "\n" + "Task User: " + environment.getTaskUser());

	}

}
