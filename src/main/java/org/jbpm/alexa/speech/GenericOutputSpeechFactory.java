package org.jbpm.alexa.speech;

import com.amazon.speech.ui.PlainTextOutputSpeech;

public class GenericOutputSpeechFactory implements OutputSpeechFactory<PlainTextOutputSpeech> {

	private final String speechText;
	
	public GenericOutputSpeechFactory(String speechText) {
		this.speechText = speechText;
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
