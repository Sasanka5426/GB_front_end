package model;
import java.sql.*; 

public class Research {
	
	//CREATE DATABASE CONNECTION
	private Connection connect() {
		
		Connection con = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/researchandresearcher", "root", "");
				
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return con;
	}
	
	//INSERT NEW RESEARCH
	public String insertResearch(String res_topic, String res_area, String status, String progress, String res_ID) {
		
		String output = "";
		
		try {
			Connection con = connect();
			
			if(con == null) {
				return "Error while connecting to the database to add a Research";
			}
			
			//creating a prepared statement
			String query = "INSERT INTO research(research_ID,research_topic,research_area,status,progress,stakeholder_ID)"
						+ " VALUES(?,?,?,?,?,?)";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			//binding values
			preparedStmt.setInt(1,0);
			preparedStmt.setString(2, res_topic);
			preparedStmt.setString(3, res_area);
			preparedStmt.setString(4, status);
			preparedStmt.setString(5, progress);
			preparedStmt.setInt(6, Integer.parseInt(res_ID));
			
			
			//execute the statement
			
			preparedStmt.execute();
			con.close();
			
			//output = "Reasearch added successfully";
			String newItems = readResearches();
			output = "{\"status\":\"success\", \"data\": \"" +
					newItems + "\"}";
		
		
		}
		catch(Exception e) {
			//output = "Error while adding new research";
			output = "{\"status\":\"error\", \"data\": \"Error while inserting.\"}"; 
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	//READ RESEARCHES
	public String readResearches() {
		String output = "";
		try {
			Connection con = connect();
			
			if(con == null) {
				return "Error while connecting to the database for reading"; 
			}
			//prepare the html table
			output = "<table border='1'>"
					+ "<tr>"
					+ "<th>Research ID</th>"
					+ "<th>Research Topic</th>"
					+ "<th>Research Area</th>"
					+ "<th>Status</th>"
					+ "<th>Progress</th>"
					+ "<th>Stakeholder ID</th>"
					+ "<th>Name</th>"
					+ "<th>Email</th>"
					+ "<th>Phone</th>"
					+ "<th>Update</th>"
					+ "<th>Remove</th>"
					+ "</tr>";
					
			
			//String query = "SELECT * FROM research";
			String query = "SELECT * FROM research research, researcher res WHERE research.stakeholder_ID = res.stakeholder_ID";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			//iterating through the rows in rs
			while(rs.next()) {
				String resID = Integer.toString(rs.getInt("research_ID"));
				String topic = rs.getString("research_topic");
				String area = rs.getString("research_area");
				String status = rs.getString("status");
				String progress = rs.getString("progress");
				String stkID = rs.getString("stakeholder_ID");
				String name = rs.getString("name");
				String email = rs.getString("email");
				String phone = rs.getString("phone_no");
				
				
				//add into the table
				output += "<tr><td>" +resID+ "</td>";
				output += "<td>" +topic+ "</td>";
				output += "<td>" +area+ "</td>";
				output += "<td>" +status+ "</td>";
				output += "<td>" +progress+ "</td>";
				output += "<td>" +stkID+ "</td>";
				output += "<td>" +name+ "</td>";
				output += "<td>" +email+ "</td>";
				output += "<td>" +phone+ "</td>";
				
				//buttons
				output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secendary' data-itemid='"+resID+"'></td>"
						+ "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-itemid='"+resID+"'>"+ "</td></tr>";
				
				
			}
			con.close();
			output += "</table>";
		}
		catch(Exception e) {
			output = "Error while reading research data";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	//UPDATE RESEARCH DETAILS
	public String updateResearch(String ID, String topic, String area, String status, String progress) {
		String output = "";
		
		try {
			Connection con = connect();
			
			if (con == null) {
				return "Error while connecting to the database for updating";
				
			}
			//create prepared statement
			String query = "UPDATE research SET research_topic=?, research_area=?, status=?, progress=? WHERE research_ID=?";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			//binding values
			
			preparedStmt.setString(1, topic);
			preparedStmt.setString(2, area);
			preparedStmt.setString(3, status);
			preparedStmt.setString(4, progress);
			preparedStmt.setInt(5, Integer.parseInt(ID));
			
			preparedStmt.execute();
			con.close();
			
			//output = "Updated successfully";
			String newItems = readResearches();
			output = "{\"status\":\"success\", \"data\": \"" +
					newItems + "\"}";
		}
		catch(Exception e) {
			//output = "Error while updating the record";
			output = "{\"status\":\"error\", \"data\": \"Error while updating.\"}"; 

			System.err.println(e.getMessage());
		}
		return output;
	}
	
	//DELETE RESEARCHES
	public String deleteResearch(String ID) {
		String output = "";
		
		try {
			Connection con = connect();
			
			if(con == null) {
				return "Error while connecting to the dtabase for deleting";
			}
			
			//create a prepared statement 
			//String query = "DELETE FROM research WHERE research_ID=?";
			
			//PreparedStatement preparedStmt = con.prepareStatement(query);
			
			//preparedStmt.setInt(1, Integer.parseInt(ID));
			
			//preparedStmt.execute();
			///////////////////////////////////////////////////////////////////////////////////////////////////
			String query1 = "delete from proposal where proposal_ID = (select proposal_ID from proposal"
					+ "										   where research_ID = ?);";
			
			PreparedStatement preparedStmt1 = con.prepareStatement(query1);
			preparedStmt1.setInt(1, Integer.parseInt(ID));
			
			
			String query2 = "delete from research where research_ID = ?;";
			PreparedStatement preparedStmt2 = con.prepareStatement(query2);
			preparedStmt2.setInt(1, Integer.parseInt(ID));
			
			preparedStmt1.execute();
			preparedStmt2.execute();
			
			//////////////////////////////////////////////////////////////////////////////////////////////////
			
			
			
			con.close();
			
			//output = "Deleted successfully";
			String newItems = readResearches();
			output = "{\"status\":\"success\", \"data\": \"" +
					newItems + "\"}";
		}
		catch(Exception e) {
			//output = "Error while deleting the record";
			output = "{\"status\":\"error\", \"data\": \"Error while deleting.\"}"; 

			System.err.println(e.getMessage());
		}
		return output;
	}
	
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//SEND RESEARCH PROGRESS TO FUNDING BODIES
	public String sendResearchProgress(String resID) {
		String output = "";
		try {
			Connection con = connect();
			if(con == null) {
				return "Error while connecting to the database for sending research progress";
			}
			
			String query = "SELECT * FROM research WHERE research_ID = ?";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setInt(1, Integer.parseInt(resID));
			ResultSet rs = preparedStmt.executeQuery();
			
			//iterate through the rows in rs
			while(rs.next()) {
				String progress = rs.getString("progress");
				output = progress;
			}
			con.close();
		}
		catch(Exception e) {
			output = "Error while reading the progress stage ."; 
			 System.err.println(e.getMessage()); 
		}
		
		return output;
		
	}
	
	//UPDATE RESEARCH STATUS AFTER RECIEVING FUNDS
	public String updateResearchStatus(String status, String ID) {
		//String status = "";
		String output = "";
		
		try {
			Connection con = connect();
			/*if(con == null) {
				return "Error while connecting to the database for updating status";
			}
			if(stage == "2")
				status = "Stage 2 in progress.";
			
			else if(stage == "3")
				status = "Stage 3 in progress.";
			
			else if(stage == "4")
				status = "Stage 4 in progress.";	*/			
		
			String query = "UPDATE research SET status=? WHERE research_ID=?";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			preparedStmt.setString(1, status);
			preparedStmt.setInt(2, Integer.parseInt(ID));
			
			preparedStmt.execute();
			con.close();
			
			output = "Status updated successfully";
		
		
		}
		catch(Exception e) {
			output = "Error while updating the status";
			System.err.println(e.getMessage());
		}
		return output;
		
	}

}
