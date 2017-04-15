package org.jbpm.alexa.client.rest;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.jbpm.alexa.util.Environment;
import org.kie.server.api.model.instance.TaskInstanceList;
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

	private static final Logger LOGGER = LoggerFactory.getLogger(KieServerClient.class);
	
	@Inject
	private Environment environment;
	
	private Client client = ClientBuilder.newClient();
	
	private KieServicesClient kieServicesClient;
	
	private UserTaskServicesClient taskClient;
	
	private QueryServicesClient queryClient;
	
	public KieServerClient() {
	}
	
	@PostConstruct
	public void init() {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Creating KieServerClient for Kie-Server at '" + environment.getKieServerUrl() + "' and container '" + environment.getContainerId() + "'.");
		}
		
		//Create the config
        CredentialsProvider credentialsProvider = new EnteredCredentialsProvider("kieserver1", "kieserver1!");
        KieServicesConfiguration kieServicesConfig = KieServicesFactory.newRestConfiguration(environment.getKieServerUrl(), credentialsProvider);
        
        //Create the client.
        kieServicesClient = KieServicesFactory.newKieServicesClient(kieServicesConfig);
        taskClient = kieServicesClient.getServicesClient(UserTaskServicesClient.class);
        queryClient = kieServicesClient.getServicesClient(QueryServicesClient.class);
	}
	
	public List<TaskSummary> getTasks() throws UnexpectedKieServerResponseException {
		try {
			List<TaskSummary> taskSummary = taskClient.findTasksAssignedAsPotentialOwner(environment.getTaskUser(), 0, 10);
			return taskSummary;
		} catch (Exception e) {
			throw new UnexpectedKieServerResponseException("Unexpected reponse while retrieving tasks from KieServer.", e);
		}
	}
	
	
	/*
	public TaskInstanceList getTasks() {
		
		WebTarget target = client.target(environment.getKieServerUrl() + "/api/cart/{cart-id}").resolveTemplate("cart-id", cartId);
		Invocation.Builder builder = target.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		//Can use the builder to add additional headers. For now we just fire a simple Get request.
		Response response = builder.get();
		
		ShoppingCart shoppingCart = null;
		//If we don't get a 200, we return an Alexa Error message.
		if (response.getStatus() == 200) {
			shoppingCart = response.readEntity(ShoppingCart.class);
		} else {
			throw new RuntimeException("Unexpected reponse from the Coolstore Gateway.");
		}
		return shoppingCart;
	}
	*/
}
