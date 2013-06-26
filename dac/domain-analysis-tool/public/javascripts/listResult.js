function goodToBuy(site){		
$.ajax({
	type: "POST",
	url: "goodToBuy",
	data: { site: site}
	}).done(function( msg ) {
	});
}

function setInGoogle(site, inGoogle){		
	$.ajax({
		type: "POST",
		url: "setInGoogle",
		data: { site: site, inGoogle: inGoogle}
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

function deleteItem(site){		
	$.ajax({
		type: "POST",
		url: "deleteItem",
		data: { site: site}
		}).done(function( msg ) {
		});
}

function deleteAll() {
	$('.deleted').each(function(){
		 $(this).prop('checked', $('#superDelete').prop('checked'));
		 deleteItem($(this)[0].id);
	});
}