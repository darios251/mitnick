function goodToBuy(site){		
$.ajax({
	type: "POST",
	url: "goodToBuy",
	data: { site: site}
	}).done(function( msg ) {
	});
}

function exportResult(){		
$.ajax({
	type: "POST",
	url: "createXLSFile",
	data: {}
	}).done(function( msg ) {
	});
}