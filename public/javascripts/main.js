function DoPost(id){
	$.post("/upVoteDica/"+id);
	window.location.reload(true);
}