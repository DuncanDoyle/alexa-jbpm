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

		StringBuilder speechBuilder = new StringBuilder("You have " + taskSummaries.size() + " tasks in your inbox. ");

		speechBuilder.append("These are the first ")
				.append((taskSummaries.size() <= taskPageSize) ? taskSummaries.size() : taskPageSize)
				.append(" tasks. ");

		taskSummaries.stream().limit(taskPageSize).forEach(t -> {
			speechBuilder.append("Task with i.d. ").append(t.getId()).append(", ");
			speechBuilder.append("has Name ").append(t.getName()).append(", ");

			String processId = t.getProcessId();
			String trimmedProcessId = processId.substring(processId.lastIndexOf(".") + 1);
			speechBuilder.append("has Process i.d. ").append(trimmedProcessId).append(", ");

			speechBuilder.append("has Priority ").append(t.getPriority()).append(". ");
		});
		this.speechText = speechBuilder.toString();

	}

	@Override
	public PlainTextOutputSpeech getOutputSpeech() {
		PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
		outputSpeech.setText(this.getSpeechText());
		return outputSpeech;
	}

	public String getSpeechText() {
		return speechText;
	}

}
