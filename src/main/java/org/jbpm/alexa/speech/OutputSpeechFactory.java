package org.jbpm.alexa.speech;

import com.amazon.speech.ui.OutputSpeech;

public interface OutputSpeechFactory<T extends OutputSpeech> {

	T getOutputSpeech();
	
	String getSpeechText();
	
}
