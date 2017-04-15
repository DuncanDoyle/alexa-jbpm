package org.jbpm.alexa;

import org.jbpm.alexa.client.rest.UnexpectedKieServerResponseException;

import com.amazon.speech.ui.PlainTextOutputSpeech;

public class KieServerErrorOutputSpeechFactory implements OutputSpeechFactory<PlainTextOutputSpeech> {

	private final UnexpectedKieServerResponseException exception;
	
	private final String speechText;
	
	public KieServerErrorOutputSpeechFactory(UnexpectedKieServerResponseException exception) {
		this.exception = exception;
		StringBuilder speechBuilder = new StringBuilder("There was a problem retrieving data from the jBPM KIE Server. ");
		speechBuilder.append(exception.getMessage());
		this.speechText = speechBuilder.toString();
	}

	@Override
	public PlainTextOutputSpeech getOutputSpeech() {
		PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
		outputSpeech.setText(getSpeechText());
		return outputSpeech;
	}

	@Override
	public String getSpeechText() {
		return speechText;
	}
	
}
