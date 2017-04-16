package org.jbpm.alexa;

import java.util.ArrayList;
import java.util.List;

import org.jbpm.alexa.speech.OutputSpeechFactory;
import org.jbpm.alexa.speech.TaskSummaryListOutputSpeechFactory;
import org.junit.Test;
import org.kie.server.api.model.instance.TaskSummary;

import com.amazon.speech.ui.PlainTextOutputSpeech;

public class TaskInstanceOutputSpeechFactoryTest {

	@Test
	public void testGetOutputSpeech() {
		
		List<TaskSummary> taskSummaries = new ArrayList<>();
		
		TaskSummary task1 = new TaskSummary();
		task1.setId(42L);
		task1.setName("TestTask");
		task1.setProcessId("TestProcessId");
		task1.setPriority(0);
		taskSummaries.add(task1);
		
		TaskSummary task2 = new TaskSummary();
		task2.setId(42L);
		task2.setName("TestTaskTwo");
		task2.setProcessId("TestProcessIdTwo");
		task2.setPriority(1);
		taskSummaries.add(task2);
		
		OutputSpeechFactory<PlainTextOutputSpeech> osFactory = new TaskSummaryListOutputSpeechFactory(taskSummaries);
		
		String speechText = osFactory.getSpeechText();
		System.out.println(speechText);
	}
	
	
}
