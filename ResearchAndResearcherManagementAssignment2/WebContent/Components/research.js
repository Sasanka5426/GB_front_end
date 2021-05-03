/**
 * 
 */
 $(document).ready(function(){
	$("#alertSuccess").hide();
	$("#alertError").hide();
});

$(document).on("click", "#btnSave", function(event){
	
	//clear alerts..................................
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();
	
	//form validation
	var status = validateResForm();
	if(status != true){
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}
	
	//if valid......................................
	var type = ($("#hidresIDSave").val() == "") ? "POST" : "PUT";
	
	$.ajax({
		url : "ResearchAPI",
		type : type,
		data : $("#formResearch").serialize(),
		dataType : "text",
		complete : function(response, status){
			onResearchSaveComplete(response.responseText, status)
		}
	});
	
});


/////////////////////////////////////////////////////////////////////////////////////////////////////////////
function validateResForm(){
	//TOPIC
	if($("#resTopic").val().trim() == ""){
		return "Insert research topic.";
	}
	
	//AREA
	if($("#area").val().trim() == ""){
		return "Insert research area.";
	}
	
	//STATUS
	if($("#status").val().trim() == ""){
		return "Insert research status.";
	}
	
	
	
	//resID
	if($("#rescherID").val().trim() == ""){
		return "Insert Researcher ID.";
	}
	
	return true;
}


function onResearchSaveComplete(response, status){
	if(status == "success"){
		var resultSet = JSON.parse(response);
		
		if(resultSet.status.trim() == "success"){
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			
			$("#divResearchGrid").html(resultSet.data);
		}
		else if(resultSet.status.trim() == "error"){
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	}
	else if(status == "error"){
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	}
	else{
		$("#alertError").text("Unknown error while saving...");
		$("#alertError").show();
	}
	
	$("#hidItemIDSave").val("");
	$("#formResearch")[0].reset();
}
