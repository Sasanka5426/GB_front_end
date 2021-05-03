package com;

import model.Researcher;

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


@Path("/Researcher")
public class ResearcherService {

	
	Researcher researcherobj = new Researcher();
	
	@GET 
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readResearchers() {
		return researcherobj.readResearchers();
	}
	
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertResearcher(@FormParam("name") String name,
									@FormParam("email") String email,
									@FormParam("address") String address,
									@FormParam("phone") String phone,
									@FormParam("interestArea") String interest,
									@FormParam("type") String type)
	{
		String output = researcherobj.insertResearcher(name, email, address, phone, interest, type);
		return output;
				
	}
	
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateResearcher(String resData) {
		
		//convert the input string to a JSON object
		JsonObject resObject = new JsonParser().parse(resData).getAsJsonObject();
		
		//read the values from the JSON object
		String resID = resObject.get("researcherID").getAsString();
		String name = resObject.get("name").getAsString();
		String email = resObject.get("email").getAsString();
		String address = resObject.get("address").getAsString();
		String phone = resObject.get("phone").getAsString();
		String interest = resObject.get("interestArea").getAsString();
		String type = resObject.get("type").getAsString();
		
		String output = researcherobj.updateResearcher(resID, name, email, address, phone, interest, type);
		
		return output;
	}
	
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteResearcher(String resData) {
		//convert the input string to an XML document
		Document doc = Jsoup.parse(resData, "", Parser.xmlParser());
		
		String resID = doc.select("Researcher_ID").text();
		
		String output = researcherobj.deleteResearcher(resID);
		return output;
	}
	
	
//INTERCOMMUNICATION	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	//requesting funding bodies from fundsAndFundingBody Service
	//passing Interest Area as parameters
	@GET
	@Path("/fundingBody")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String getProp(String Interest) {
		JsonObject propObj = new JsonParser().parse(Interest).getAsJsonObject();
		String interestArea = propObj.get("interest").getAsString();
		
		Client client = Client.create();
		//System.out.println(interestArea);
		WebResource webResource = client.resource("http://localhost:8090/FundsAndFundingbodyManagement/FundingBodyService/FundingBoady/readFbody/" +interestArea);
		ClientResponse response = webResource.type("application/xml").get(ClientResponse.class);
		String queryResponse = response.getEntity(String.class);
		//System.out.println(interestArea);
		System.out.println(queryResponse);
		return queryResponse;
		
		
	}
	
	
}
