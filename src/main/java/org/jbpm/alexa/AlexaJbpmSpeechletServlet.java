package org.jbpm.alexa;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;

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
	}

}
