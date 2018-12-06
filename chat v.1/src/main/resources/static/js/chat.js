$.ajaxSetup({contentType:'application/json'})


//$.get('/hello/say',function(helloMessage){
//	$('h1').append(helloMessage);
// });

//$.get('/api/message', function (message){
//	for(var i=0;i<message.length;i++){
//		appendMessage(message[i]);
//	}
//		
//			
////	setTimeout(aaa,500);
//});



function appendMessage(message){
	$('#messages').append('<li class="list-group-item">'+message.senderId+" "+message.receiverId+" "+message.body+" "+message.created+'</li>');
}

$(function(){
	
	$('#sendBtn').click(function(){
		createMessage();
	})
	
})



function createMessage(){
	var message={
			senderId:$('#SenderId').val(),
			receiverId:$('#ReceiverId').val(),
			body:$('#Body').val()
	}
		$.post('/api/message',JSON.stringify(message),function(data){
			appendMessage(data);
		},'json');
	
}