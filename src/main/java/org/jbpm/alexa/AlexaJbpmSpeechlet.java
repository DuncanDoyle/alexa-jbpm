package org.jbpm.alexa;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.jbpm.alexa.client.rest.KieServerClient;
import org.jbpm.alexa.util.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SessionEndedRequest;
import com.amazon.speech.speechlet.SessionStartedRequest;
import com.amazon.speech.speechlet.Speechlet;
import com.amazon.speech.speechlet.SpeechletException;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SimpleCard;


/**
 * Alexa {@link Speechlet} which implements the interaction patterns for our Alexa jBPM skill.
 * 
 * @author <a href="mailto:duncan.doyle@redhat.com">Duncan Doyle</a>
 *
 */
@ApplicationScoped
public class AlexaJbpmSpeechlet implements Speechlet {

	private static final Logger LOGGER = LoggerFactory.getLogger(AlexaJbpmSpeechlet.class);
	
	@Inject
	private Environment environment;
	
	@Inject
	private KieServerClient kieServerClient;
	

	@Override
	public void onSessionStarted(SessionStartedRequest request, Session session) throws SpeechletException {
		LOGGER.info("onSessionStarted requestId={}, sessionId={}", request.getRequestId(), session.getSessionId());
		// Any additional initialization logic here.
	}

	@Override
	public SpeechletResponse onLaunch(LaunchRequest request, Session session) throws SpeechletException {
		LOGGER.info("onLaunch requestId={}, sessionId={}", request.getRequestId(), session.getSessionId());
		return getWelcomeResponse();
	}

	@Override
	public SpeechletResponse onIntent(IntentRequest request, Session session) throws SpeechletException {
		LOGGER.info("onIntent requestId={}, sessionId={}", request.getRequestId(), session.getSessionId());

		Intent intent = request.getIntent();
		String intentName = (intent != null) ? intent.getName() : null;

		switch (intentName) {
		case "GetTasks":
			LOGGER.debug("GetTasks intent received.");
			return getGetTasksResponse();
		case "GetTaskInfo":
			LOGGER.debug("GetTaskInfo intent received.");
			return getGetTaskInfoResponse();
		case "ProcessTask":
			LOGGER.debug("ProcessTask intent received.");
			return getProcessTaskResponse();
		case "AMAZON.HelpIntent": 
			LOGGER.debug("HelpIntent");
			return getHelpResponse();
		default:
			throw new SpeechletException("Invalid Intent.");
		}
		
	}

	@Override
	public void onSessionEnded(SessionEndedRequest request, Session session) throws SpeechletException {
		LOGGER.info("onSessionEnded requestId={}, sessionId={}", request.getRequestId(), session.getSessionId());
		// any cleanup logic goes here
	}

	/**
	 * Creates and returns a {@code SpeechletResponse} with a welcome message.
	 *
	 * @return SpeechletResponse spoken and visual response for the given intent
	 */
	private SpeechletResponse getWelcomeResponse() {
		
		String speechText = "Welcome to the Alexa jBPM skill. You can say: get my tasks";

		// Create the Simple card content.
		// Card is displayed in the application.
		SimpleCard card = new SimpleCard();
		card.setTitle("Welcome");
		card.setContent(speechText);

		// Create the plain text output.
		PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
		speech.setText(speechText);

		// Create reprompt
		// TODO: Reprompt seems to be a prompt that is resend when the session is kept open (like in an AskResponse).
		Reprompt reprompt = new Reprompt();
		reprompt.setOutputSpeech(speech);

		return SpeechletResponse.newAskResponse(speech, reprompt, card);
	}
	
	/**
	 * Creates a {@code SpeechletResponse} for the <code>GetTasks</code>
	 *
	 * @return SpeechletResponse spoken and visual response for the given intent
	 */
	private SpeechletResponse getGetTasksResponse() {
		LOGGER.debug("Building GetTasks response.");
		
		OutputSpeechFactory<PlainTextOutputSpeech> osFactory = new JbpmOutputSpeechFactory(kieServerClient.getTasks());
		

		// Create the Simple card content.
		SimpleCard card = new SimpleCard();
		card.setTitle("GetTasks");
		card.setContent(osFactory.getSpeechText());

		return SpeechletResponse.newTellResponse(osFactory.getOutputSpeech(), card);
	}

	/**
	 * Creates a {@code SpeechletResponse} for the <code>GetTaskInfo</code>.
	 *
	 * @return SpeechletResponse spoken and visual response for the given intent
	 */
	private SpeechletResponse getGetTaskInfoResponse() {
		LOGGER.debug("Building GetTaskInfo response.");
		/*
		String speechText = "Your shopping cart is empty.";

		// Create the Simple card content.
		SimpleCard card = new SimpleCard();
		card.setTitle("Empty shopping cart.");
		card.setContent(speechText);

		//Get the shoppingCart. The ID is fixed and set to 1 (until we can somehow links someone's Alexa account to a given ID of the cart).
		ShoppingCart shoppingCart = kieServerClient.getShoppingCart(environment.getContainerId());
		
		// Create the plain text output.
		OutputSpeech outputSpeech = new JbpmOutputSpeechFactory(shoppingCart).getOutputSpeech();
		
		return SpeechletResponse.newTellResponse(outputSpeech, card);
		*/
		return null;
	}
	
	/**
	 * Creates a {@code SpeechletResponse} for the <code>GetTaskInfo</code>.
	 *
	 * @return SpeechletResponse spoken and visual response for the given intent
	 */
	private SpeechletResponse getProcessTaskResponse() {
		/*
		LOGGER.debug("Building ShoppingCart response.");
		String speechText = "Your shopping cart is empty.";

		// Create the Simple card content.
		SimpleCard card = new SimpleCard();
		card.setTitle("Empty shopping cart.");
		card.setContent(speechText);

		//Get the shoppingCart. The ID is fixed and set to 1 (until we can somehow links someone's Alexa account to a given ID of the cart).
		ShoppingCart shoppingCart = kieServerClient.getShoppingCart(environment.getContainerId());
		
		// Create the plain text output.
		OutputSpeech outputSpeech = new JbpmOutputSpeechFactory(shoppingCart).getOutputSpeech();
		
		return SpeechletResponse.newTellResponse(outputSpeech, card);
		*/
		return null;
	}
	

	/**
	 * Creates a {@code SpeechletResponse} for the help intent.
	 *
	 * @return SpeechletResponse spoken and visual response for the given intent
	 */
	private SpeechletResponse getHelpResponse() {
		String speechText = "You can say hello to me!";

		// Create the Simple card content.
		SimpleCard card = new SimpleCard();
		card.setTitle("HelloWorld");
		card.setContent(speechText);

		// Create the plain text output.
		PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
		speech.setText(speechText);

		// Create reprompt
		Reprompt reprompt = new Reprompt();
		reprompt.setOutputSpeech(speech);

		return SpeechletResponse.newAskResponse(speech, reprompt, card);
	}
}
