package com;

import model.Proposal;

//rest
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

//json
import com.google.gson.*;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

//xml
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/Proposal")
public class ProposalService {
	
	Proposal proposalObj = new Proposal();
	
	
	
	@GET 
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readResearches() {
		return proposalObj.readProposals();
	}
	
	
	
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertResearch(String propData) {
		
		//convert the input string to a JSON object
		JsonObject propObj = new JsonParser().parse(propData).getAsJsonObject();
		
		//read the values from the JSON object
		String desc = propObj.get("description").getAsString();
		//String date = propObj.get("date").getAsString();
		String res_ID = propObj.get("research_ID").getAsString();
		
		
		//String output = proposalObj.insertProposal(desc, date, res_ID);
		String output = proposalObj.insertProposal(desc, res_ID);
		
		return output;
	}

	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//SEND RESEARCH PROPOSALS TO FUNDING BODIES
	@GET
	@Path("/getProposal/{interest}")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_HTML)
	public String sendProposals(@PathParam(value = "interest")String interest) {
		
		return proposalObj.sendProposals(interest);
	}
}




/*
@Path("/getProposal")
public class getProposalService {
	
	
	@GET
	@Path("/proposal")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String getProp(String Interest) {
		JsonObject propObj = new JsonParser().parse(Interest).getAsJsonObject();
		String interestArea = propObj.get("interest").getAsString();
		
		Client client = Client.create();
		System.out.println(interestArea);
		WebResource webResource = client.resource("http://localhost:8090/ResearchAndResearcherManagement/ProposalService/Proposal/getProposal/" +interestArea);
		ClientResponse response = webResource.type("application/xml").get(ClientResponse.class);
		String queryResponse = response.getEntity(String.class);
		System.out.println(interestArea);
		System.out.println(queryResponse);
		return queryResponse;
		
		
	}
	
	
	
}*/





