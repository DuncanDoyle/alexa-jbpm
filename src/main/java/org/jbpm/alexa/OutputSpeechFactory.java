package org.jbpm.alexa;

import com.amazon.speech.ui.OutputSpeech;

public interface OutputSpeechFactory<T extends OutputSpeech> {

	T getOutputSpeech();
	
	String getSpeechText();
	
}
