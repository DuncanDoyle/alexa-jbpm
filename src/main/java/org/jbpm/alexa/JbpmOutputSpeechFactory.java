package org.jbpm.alexa;

import java.util.List;

import org.kie.server.api.model.instance.TaskSummary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.redhat.bpms.examples.mortgage.model.ShoppingCartItem;

public class JbpmOutputSpeechFactory implements OutputSpeechFactory<PlainTextOutputSpeech> {

	private static final Logger LOGGER = LoggerFactory.getLogger(JbpmOutputSpeechFactory.class);
	
	private final List<TaskSummary> taskSummaries;
	
	private String speechText;
	
	public JbpmOutputSpeechFactory(List<TaskSummary> taskSummaries) {
		this.taskSummaries = taskSummaries;
	}
	
	@Override
	public PlainTextOutputSpeech getOutputSpeech() {
		PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
		StringBuilder speechBuilder = new StringBuilder();
		
		/*
		//Items
		if (shoppingCart.shoppingCartItemList.isEmpty()) {
			speechBuilder.append("no items.");
		} else {
			for (ShoppingCartItem nextItem: shoppingCart.shoppingCartItemList) {
				speechBuilder.append(nextItem.quantity).append(" ").append(nextItem.product.getName());
			}
		}
		speechBuilder.append(". ");
		
		//Total value
		speechBuilder.append(TOTAL_ORDER_AMOUNT_TEXT).append(shoppingCart.cartTotal);
		String speechText = speechBuilder.toString();
		LOGGER.debug("SpeechText for ShoppingCart: " +  speechText);
		*/
		String speechText = speechBuilder.toString();
		this.speechText = speechText;
		speech.setText(speechText);
		return speech;
	}
	
	public String getSpeechText() {
		return speechText;
	}

}
