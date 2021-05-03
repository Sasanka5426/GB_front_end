package model;
import java.sql.*; 
public class Researcher {
	
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
	
	public String insertResearcher(String name, String email, String address, String phone, String interest, String type) {
		
		String output = "";
		
		try {
			Connection con = connect();
			
			if(con == null) {
				return "Error while connecting to the database to add a Researcher";
			}
			
			//creating a prepared statement
			String query = "INSERT INTO researcher(stakeholder_ID,name,email,address,phone_no,interest_area,researcher_type)"
						+ " VALUES(?,?,?,?,?,?,?)";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			//binding values
			preparedStmt.setInt(1,0);
			preparedStmt.setString(2, name);
			preparedStmt.setString(3, email);
			preparedStmt.setString(4, address);
			preparedStmt.setString(5, phone);
			preparedStmt.setString(6, interest);
			preparedStmt.setString(7, type);
			
			//execute the statement
			
			preparedStmt.execute();
			con.close();
			
			output = "Reaseachr added successfully";
		}
		catch(Exception e) {
			output = "Error while adding new researcher";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	public String readResearchers() {
		String output = "";
		try {
			Connection con = connect();
			
			if(con == null) {
				return "Error while connecting to the database for reading"; 
			}
			//prepare the html table
			output = "<table border='1'>"
					+ "<tr>"
					+ "<th>Researcher ID</th>"
					+ "<th>Name</th>"
					+ "<th>Email</th>"
					+ "<th>Address</th>"
					+ "<th>Phone</th>"
					+ "<th>Interest Area</th>"
					+ "<th>Researcher Type</th>";
			
			String query = "SELECT * FROM researcher";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			//iterating through the rows in rs
			while(rs.next()) {
				String resID = Integer.toString(rs.getInt("stakeholder_ID"));
				String name = rs.getString("name");
				String email = rs.getString("email");
				String address = rs.getString("address");
				String phone = rs.getString("phone_no");
				String interest = rs.getString("interest_area");
				String type = rs.getString("researcher_type");
				
				//add into the table
				output += "<tr><td>" +resID+ "</td>";
				output += "<td>" +name+ "</td>";
				output += "<td>" +email+ "</td>";
				output += "<td>" +address+ "</td>";
				output += "<td>" +phone+ "</td>";
				output += "<td>" +interest+ "</td>";
				output += "<td>" +type+ "</td></tr>";
				
			}
			con.close();
			output += "</table>";
		}
		catch(Exception e) {
			output = "Error while reading data";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	public String updateResearcher(String ID, String name, String email, String address, String phone, String interest, String type ) {
		String output = "";
		
		try {
			Connection con = connect();
			
			if (con == null) {
				return "Error while connecting to the database for updating";
				
			}
			//create prepared statement
			String query = "UPDATE researcher SET name=?, email=?, address=?, phone_no=?, interest_area=?, researcher_type=? WHERE stakeholder_ID=?";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			//binding values
			
			preparedStmt.setString(1, name);
			preparedStmt.setString(2, email);
			preparedStmt.setString(3, address);
			preparedStmt.setString(4, phone);
			preparedStmt.setString(5, interest);
			preparedStmt.setString(6, type);
			preparedStmt.setInt(7, Integer.parseInt(ID));
			
			preparedStmt.execute();
			con.close();
			
			output = "Updated successfully";
			
		}
		catch(Exception e) {
			output = "Error while updating the record";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	public String deleteResearcher(String resID) {
		String output = "";
		
		try {
			Connection con = connect();
			
			if(con == null) {
				return "Error while connecting to the dtabase for deleting";
			}
			
			//create a prepared statement 
			//String query = "DELETE FROM researcher WHERE stakeholder_ID=?";
			
			//PreparedStatement preparedStmt = con.prepareStatement(query);
			
			//preparedStmt.setInt(1, Integer.parseInt(resID));
			
			//preparedStmt.execute();
			
			////////////////////////////////////////////////////////////
			
			/*query1 = delete from proposal where proposal_ID = (select proposal_ID from proposal
			  where research_ID = (select research_ID from research
								   where stakeholder_ID = ?));*/
			
			String query1 = "delete from proposal where proposal_ID = (select proposal_ID from proposal where research_ID = (select research_ID from research  where stakeholder_ID = ?));";
															  
			PreparedStatement preparedStmt1 = con.prepareStatement(query1);	
			preparedStmt1.setInt(1, Integer.parseInt(resID));
			
			
			/*query2 = delete from research where research_ID = (select research_ID from research
			  where stakeholder_ID = ?);*/
			
			String query2 = "delete from research where research_ID = (select research_ID from research where stakeholder_ID = ?);";
															  
			PreparedStatement preparedStmt2 = con.prepareStatement(query2);
			preparedStmt2.setInt(1, Integer.parseInt(resID));
			
			
			String query3 = "delete from researcher where stakeholder_ID = ?";
			
			PreparedStatement preparedStmt3 = con.prepareStatement(query3);
			preparedStmt3.setInt(1, Integer.parseInt(resID));
			
			preparedStmt1.execute();
			preparedStmt2.execute();
			preparedStmt3.execute();
			
			///////////////////////////////////////////////////////////
			con.close();
			
			output = "Deleted successfully";
		}
		catch(Exception e) {
			output = "Error while deleting the record";
			System.err.println(e.getMessage());
		}
		return output;
	}
	

}
