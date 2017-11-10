package org.jbpm.alexa.client.rest;

import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.jbpm.alexa.util.Environment;
import org.kie.server.api.model.instance.TaskInstance;
import org.kie.server.api.model.instance.TaskSummary;
import org.kie.server.client.CredentialsProvider;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;
import org.kie.server.client.QueryServicesClient;
import org.kie.server.client.UserTaskServicesClient;
import org.kie.server.client.credentials.EnteredCredentialsProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * KIE-Server client that allows Alexa jBPM to communicate with KIE-Server.
 * 
 * @author <a href="mailto:duncan.doyle@redhat.com">Duncan Doyle</a>
 */
@ApplicationScoped
public class KieServerClient {

	public enum TaskCommand {
		CLAIM("claimed"),
		START("started"),
		RELEASE("released"),
		COMPLETE("completed");
		
		private final String speechText;
		
		private TaskCommand(String speechText) {
			this.speechText = speechText;
		}
		
		public String getSpeechText() {
			return speechText;
		}
	}
	
	private static final Logger LOGGER = LoggerFactory.getLogger(KieServerClient.class);

	@Inject
	private Environment environment;

	private KieServicesClient kieServicesClient;

	private UserTaskServicesClient taskClient;

	private QueryServicesClient queryClient;

	public KieServerClient() {
	}

	@PostConstruct
	public void init() {
		String user = environment.getKieServerUser();
		String password = environment.getKieServerPassword();
		
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Creating KieServerClient for Kie-Server at '" + environment.getKieServerUrl()
					+ "' and container '" + environment.getContainerId() + "'.");
			LOGGER.info("Connecting with KIE-Server using user: '" + user + "'.");
		}

		CredentialsProvider credentialsProvider = new EnteredCredentialsProvider(user, password);
		KieServicesConfiguration kieServicesConfig = KieServicesFactory
				.newRestConfiguration(environment.getKieServerUrl(), credentialsProvider);

		// Create the client.
		kieServicesClient = KieServicesFactory.newKieServicesClient(kieServicesConfig);
		taskClient = kieServicesClient.getServicesClient(UserTaskServicesClient.class);
		queryClient = kieServicesClient.getServicesClient(QueryServicesClient.class);
	}

	public List<TaskSummary> getTasks() throws UnexpectedKieServerResponseException {
		try {
			return callKieServer(() -> taskClient.findTasksAssignedAsPotentialOwner(environment.getTaskUser(), 0, 10));
		} catch (Exception e) {
			throw new UnexpectedKieServerResponseException("Unexpected reponse while retrieving tasks from KieServer.",
					e);
		}
	}

	public TaskInstance getTasksInfo(Long taskId) throws UnexpectedKieServerResponseException {
		try {
			return callKieServer(() -> taskClient.findTaskById(taskId));
		} catch (Exception e) {
			throw new UnexpectedKieServerResponseException(
					"Unexpected reponse while retrieving task instance with id " + taskId + " from KieServer.", e);
		}
	}
	
	public void processTask(Long taskId, TaskCommand command) throws UnexpectedKieServerResponseException {
		//We first need to retrieve the container-id
		TaskInstance instance = getTasksInfo(taskId);
		String containerId = instance.getContainerId();
		
		switch (command) {
		case CLAIM:
			callKieServer(() -> {
				taskClient.claimTask(containerId, taskId, environment.getTaskUser());
				return "Task claimed.";
			});
			break;
		case RELEASE:
			callKieServer(() -> {
				taskClient.releaseTask(containerId, taskId, environment.getTaskUser());
				return "Task released.";
			});
			break;
		case START:
			callKieServer(() -> {
				taskClient.startTask(containerId, taskId, environment.getTaskUser());
				return "Task started.";
			});
			break;
		case COMPLETE:
			//TODO: complete task with params. How should we pass the params?
			callKieServer(() -> {
				taskClient.completeTask(containerId, taskId, environment.getTaskUser(), new HashMap<String, Object>());
				return "Task completed.";
			});
			break;
		default:
			throw new UnsupportedOperationException("Unsupported command.");
		}
	}
	
	/**
	 * Runs the supplier function with some retry logic to accommodate for KIE-Server failure.
	 * 
	 * @param supplier
	 * @return
	 * @throws UnexpectedKieServerResponseException
	 */
	private <T> T callKieServer(Supplier<T> supplier) throws UnexpectedKieServerResponseException {
		boolean retry = true;
		int retryCount = 0;
		final int retryLimit = 5;

		T value = null;
		while (retry) {
			try {
				value = supplier.get();
				retry = false;
			} catch (Exception e) {
				if (retryCount >= retryLimit) {
					throw new UnexpectedKieServerResponseException("Retry limit of " + retryLimit + " exceeded while calling KIE Server.");
				}
				retryCount++;
			}
		}
		return value;
	}

	/*
	 * public TaskInstanceList getTasks() {
	 * 
	 * WebTarget target = client.target(environment.getKieServerUrl() +
	 * "/api/cart/{cart-id}").resolveTemplate("cart-id", cartId);
	 * Invocation.Builder builder =
	 * target.request(MediaType.APPLICATION_JSON).accept(MediaType.
	 * APPLICATION_JSON) ; //Can use the builder to add additional headers. For
	 * now we just fire a simple Get request. Response response = builder.get();
	 * 
	 * ShoppingCart shoppingCart = null; //If we don't get a 200, we return an
	 * Alexa Error message. if (response.getStatus() == 200) { shoppingCart =
	 * response.readEntity(ShoppingCart.class); } else { throw new
	 * RuntimeException("Unexpected reponse from the Coolstore Gateway."); }
	 * return shoppingCart; }
	 */
}
