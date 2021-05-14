<%@page import="model.Research" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Research Management</title>
<link rel="stylesheet" href="Views/bootstrap.min.css"> 
<script src="Components/jquery-3.5.1.min.js"></script> 
<script src="Components/research.js"></script> 
</head>
<body>

<div class="cintainer-fluide">
	<div class="row">
		<div class="col-md-2">
		
		</div>
		
		<div class="col-8">
			<center><h1>Research Management</h1></center>
			
			<form id="formResearch" name="formResearch">
				Research Topic:
				<input id="resTopic" name="resTopic" type="text" class="form-control form-control-sm">
				
				<br>
				Research Area:
				<input id="area" name="area" type="text" class="form-control form-control-sm">
				
				<br>
				Status:
				<input id="status" name="status" type="text" class="form-control form-control-sm">
				
				<br>
				Progress:
				<input id="progress" name="progress" type="text" class="form-control form-control-sm">
				
				<br>
				Researcher ID:
				<input id="rescherID" name="rescherID" type="text" class="form-control form-control-sm">
				<br>
				<input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-primary">
				<input type="hidden" id="hidresIDSave" name="hidresIDSave" value="">
				
			</form>
			<br><br>
			<div id="alertSuccess" class="alert alert-success"></div>
			
			<div id="alertError" class="alert alert-danger"></div>
			<br>
		</div>
		<div class="col-2">
		
		</div>
		
		
		
	</div>
	
	<div class="row">
		
		<div class="col-md-12">
		<center>
		<div id="divResearchGrid">
			<%
					Research resObj = new Research();
					out.print(resObj.readResearches());
			%>
		</div>	
		</center>
		</div>
		
	</div>
</div>

</body>
</html>