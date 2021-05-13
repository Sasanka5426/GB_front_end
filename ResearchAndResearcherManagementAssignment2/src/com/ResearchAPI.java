package com;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import model.Research;


@WebServlet("/ResearchAPI")
public class ResearchAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	Research resObj = new Research();
       
    
    public ResearchAPI() {
        super();
        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		String output = resObj.insertResearch(request.getParameter("resTopic"), request.getParameter("area"), request.getParameter("status"), request.getParameter("progress"), request.getParameter("rescherID"));
		response.getWriter().write(output);
		
		//doGet(request, response);
	}

	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Map paras = getParasmap(request);
		
		String output = resObj.updateResearch(paras.get("hidresIDSave").toString(),
											paras.get("resTopic").toString(),
											paras.get("area").toString(),
											paras.get("status").toString(),
											paras.get("progress").toString());
		
		response.getWriter().write(output);
		
	}

	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Map paras = getParasmap(request);
		
		String output = resObj.deleteResearch(paras.get("resID").toString());
		response.getWriter().write(output);
		
	}
	
	private static Map getParasmap(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			Scanner scanner = new Scanner(request.getInputStream(), "UTF-8");
			String queryString = scanner.hasNext() ?
									scanner.useDelimiter("\\A").next() : "";
			scanner.close();
			
			String[] params = queryString.split("&");
			for (String param:params) {
				String[] p = param.split("=");
				map.put(p[0], p[1]);
			}
		}catch(Exception e) {
			
		}
		return map;
	}

}
