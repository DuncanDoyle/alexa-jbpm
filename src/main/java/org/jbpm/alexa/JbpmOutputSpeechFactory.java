package org.jbpm.alexa;

import java.util.List;

import org.kie.server.api.model.instance.TaskSummary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazon.speech.ui.PlainTextOutputSpeech;

public class JbpmOutputSpeechFactory implements OutputSpeechFactory<PlainTextOutputSpeech> {

	private static final Logger LOGGER = LoggerFactory.getLogger(JbpmOutputSpeechFactory.class);

	private final static int taskPageSize = 4;

	private final List<TaskSummary> taskSummaries;

	private String speechText;

	public JbpmOutputSpeechFactory(List<TaskSummary> taskSummaries) {
		this.taskSummaries = taskSummaries;
	}

	@Override
	public PlainTextOutputSpeech getOutputSpeech() {
		PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
		StringBuilder speechBuilder = new StringBuilder("You have " + taskSummaries.size() + " tasks in your inbox.");

		speechBuilder.append("These are the first ")
				.append((taskSummaries.size() <= taskPageSize) ? taskSummaries.size() : taskPageSize).append(" tasks.");

		taskSummaries.stream().limit(taskPageSize).forEach(t -> {
			speechBuilder.append("Task with id ").append(t.getId());
			speechBuilder.append("has Name ").append(t.getName());
			speechBuilder.append("has Process id ").append(t.getProcessId());
			speechBuilder.append("has Priority ").append(t.getPriority());
		});

		String speechText = speechBuilder.toString();
		this.speechText = speechText;
		speech.setText(speechText);
		return speech;
	}

	public String getSpeechText() {
		return speechText;
	}

}
