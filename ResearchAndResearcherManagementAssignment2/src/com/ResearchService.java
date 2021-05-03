package com;


import model.Research;

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

@Path("/Research")
public class ResearchService {
	
	Research researchObj = new Research();
	
	
	
	@GET 
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readResearches() {
		return researchObj.readResearches();
	}
	
	
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertResearch(String researchData) {
		
		//convert the input string to a JSON object
		JsonObject resObj = new JsonParser().parse(researchData).getAsJsonObject();
		
		//read the values from the JSON object
		String res_topic = resObj.get("research_topic").getAsString();
		String res_area = resObj.get("research_area").getAsString();
		String status = resObj.get("status").getAsString();
		String progress = resObj.get("progress").getAsString();
		String res_ID = resObj.get("res_ID").getAsString();
		
		String output = researchObj.insertResearch(res_topic, res_area, status, progress, res_ID);
		
		
		
		
		return output;
	}
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateResearch(String resData) {
		
		//convert the input string to a JSON object
		JsonObject resObject = new JsonParser().parse(resData).getAsJsonObject();
		
		//read the values from the JSON object
		String ID = resObject.get("researchID").getAsString();
		String topic = resObject.get("research_topic").getAsString();
		String area = resObject.get("research_area").getAsString();
		String status = resObject.get("status").getAsString();
		String progress = resObject.get("progress").getAsString();
		
		
		String output = researchObj.updateResearch(ID, topic, area, status, progress);
		
		
		
		return output;
	}
	
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteResearch(String resData) {
		
		JsonObject resObject = new JsonParser().parse(resData).getAsJsonObject();
		
		String ID = resObject.get("researchID").getAsString();
		
		String output = researchObj.deleteResearch(ID);
		
		return output;
		
	}
	
	
	
//INTERCOMMUNICATION	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	//send research progress to funding body service
	@GET
	@Path("/getProgress/{research_ID}")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_HTML)
	public String sendProposals(@PathParam(value = "research_ID")String research_ID) {
		
		//return proposalObj.sendProposals(research_ID);
		return researchObj.sendResearchProgress(research_ID);
	}
	
	//request funded stage from funding body service
	@PUT
	@Path("/getFundingStage")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String getProp(String ResearchID) {
		JsonObject resObj = new JsonParser().parse(ResearchID).getAsJsonObject();
		String researchID = resObj.get("researchID").getAsString();
		
		Client client = Client.create();
		//System.out.println(researchID);
		WebResource webResource = client.resource("http://localhost:8090/FundsAndFundingbodyManagement/FundingBodyService/FundingBoady/readCurrentStage/" +researchID);
		ClientResponse response = webResource.type("application/xml").get(ClientResponse.class);
		String queryResponse = response.getEntity(String.class);
		//System.out.println(interestArea);
		System.out.println(queryResponse);
		
		//return researchObj.updateResearchStatus(queryResponse, researchID);
		
		if(queryResponse.equals("2")) {
			return researchObj.updateResearchStatus("Stage 2 in progress", researchID);
		}
		else if(queryResponse.equals("3")) {
			return researchObj.updateResearchStatus("Stage 3 in progress", researchID);
		}
		else if(queryResponse.equals("4")) {
			return researchObj.updateResearchStatus("Stage 4 in progress", researchID);
		}
		else
			return "Invalid stage...";
		
		
		
		
		
		//return queryResponse;
		
		
	}
	//http://localhost:8090/FundsAndFundingbodyManagement/FundingBodyService/FundingBoady/readCurrentStage/{researchID
	

}
