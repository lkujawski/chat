var loggedUser;
$.ajaxSetup({
	contentType : 'application/json'
})

$(document).ready(function() {
	getNotificationFromRemoteServer();
	displayAllMessages();
	loadUserList();
	displayAuthUser();
});

function getNotificationFromRemoteServer(){
	$.get('/api/event');
}

function displayTime() {

	// $('h1').append('<br>+counter');
	var time = new Date();
	$('#time')
			.html(
					time.getHours() + ":" + time.getMinutes() + ":"
							+ time.getSeconds());
	// setTimeout(test, 100);
	// alert('Your book is overdue.');
}

// function userDiv(user){
// $('#' + user).click(function(){
// alert(user)});
// }

function userDiv(user) {
	$('#' + user).click(function() {
		// alert(user)
		var url = '/api/singleuser/' + user;
		// alert(url);
		displayMessagesFromSingleUser(url, user);
	});
}
function displayMessagesFromSingleUser(url, user) {
	// $.get('/api/singleuser', function (message){
	$.get(url, function(message) {
		$('#MessageSingleUser').html("");
		// console.log('url '+url );
		// console.log('user '+user );
		// console.log('dl' +message.length);
		$('#MessageSingleUser').html(
				"Correspondence with user: " + user + "<br>");
		for (var i = 0; i < message.length; i++) {
			var div ='<div id="' + 'SU-' + message[i].messageId + '">'
			+ formatTime2(message[i].created) + " "
			+ message[i].senderId + "->"
			+ message[i].receiverId + ": " + message[i].body;
			if((message[i].confirmationOfReceived=="YES")&&(message[i].confirmationOfRead=="YES")){
				div = div +", (received & read by receiever)";
			}
			else if (message[i].confirmationOfReceived=="YES"){
				div = div +", (received by receiever)";
			}
			else if(message[i].confirmationOfRead=="YES"){
				div = div +", (read by receiever)";
			}
			div = div + "</div>";
			$('#MessageSingleUser').append(div);
			messageDivSingleUser(message[i].messageId);
		}

	});
}

function messageDivSingleUser(mesId) {
	$('#SU-' + mesId).click(function() {

		var strconfirm = confirm("Would you like to delete message?");
		if (strconfirm == true) {
			$.post('/api/remove', mesId);
			$('#SU-' + mesId).remove();
			$('#' + mesId).remove();
		}

	});
}

function loadUserList() {
	$.get('/api/user', function(userList) {
		for (var i = 0; i < userList.length; i++) {
			var txt = userList[i].id;

			$('#divLeftBottom').append(
					'<div class="list-group-item" id="' + userList[i].id + '">'
							+ userList[i].id + '</div>');

			userDiv(userList[i].id);
			// $('#' + userList[i].id).click(function(){
			// console.log(txt);
			// userDiv(txt);
			// });
			$('#SenderSelectList').append($('<option>', {
				value : userList[i].id,
				text : userList[i].id
			}));
			$('#ReceiverSelectList').append($('<option>', {
				value : userList[i].id,
				text : userList[i].id
			}));
		}
		$('#SenderSelectList').val("lukaszku");
		$('#ReceiverSelectList').val("lukaszk");
	});
}

function displayAuthUser() {
	$.get('/api/auth', function(user) {
		loggedUser=user;
		$('#authUser').append("You are logged as: " + user);
	});

}

function messageDiv(mesId) {
	$('#' + mesId).click(function() {

		var strconfirm = confirm("Would you like to delete message?");
		if (strconfirm == true) {

			$.post('/api/remove', mesId);
			$('#' + mesId).remove();
			if ($('#SU-' + mesId).length == 0) {
				// alert('false');
			} else {
				$('#SU-' + mesId).remove();
			}

		}

	});
}

function displayAllMessages() {
	$.get('/api/message', function(message) {

		$('#DivdisplayAllMessages').html("");
		for (var i = 0; i < message.length; i++) {
			var div='<div id="' + message[i].messageId + '">'
			+ formatTime2(message[i].created) + " "
			+ message[i].senderId + "->"
			+ message[i].receiverId + ": " + message[i].body;
			if((message[i].confirmationOfReceived=="YES")&&(message[i].confirmationOfRead=="YES")){
				div = div +", (received & read by receiever)";
			}
			else if (message[i].confirmationOfReceived=="YES"){
				div = div +", (received by receiever)";
			}
			else if(message[i].confirmationOfRead=="YES"){
				div = div +", (read by receiever)";
			}
			
			div=div+ "</div>";

			$('#DivdisplayAllMessages').append(div);
			messageDiv(message[i].messageId);
			if((message[i].readNotification!='SENT') && (message[i].senderId)!=loggedUser){
			sendReadNotificationToRemoteServer(message[i]);
			}
		}
		$('#DivdisplayAllMessages').append("<br>");
		displayTime();
//		setTimeout(displayAllMessages, 10000);
	});
}


//function displayAllMessages() {
//	$.get('/api/message', function(message) {
//
//		$('#DivdisplayAllMessages').html("");
//		for (var i = 0; i < message.length; i++) {
//
//			$('#DivdisplayAllMessages').append(
//					'<div id="' + message[i].messageId + '">'
//							+ formatTime2(message[i].created) + " "
//							+ message[i].senderId + "->"
//							+ message[i].receiverId + ": " + message[i].body
//							+ "</div>");
//			messageDiv(message[i].messageId);
//			if((message[i].readNotification!='SENT') && (message[i].senderId)!=loggedUser){
//			sendReadNotificationToRemoteServer(message[i]);
//			}
//		}
//		$('#DivdisplayAllMessages').append("<br>");
//		displayTime();
////		setTimeout(displayAllMessages, 10000);
//	});
//}

function sendReadNotificationToRemoteServer(message){
	$.post('/api/read-notification', JSON.stringify(message),'json');
}

function formatTime2(seconds) {
	date = new Date(seconds);
	month = date.getMonth() + 1;
	if (month < 10) {
		month = '0' + month;
	}
	day = date.getDate();
	if (day < 10) {
		day = '0' + day;
	}

	hour = date.getHours();
	if (hour < 10) {
		hour = '0' + hour;
	}

	minute = date.getMinutes();
	if (minute < 10) {
		minute = '0' + minute;
	}

	return day + '.' + month + '.' + date.getFullYear() + " " + hour + ":"
			+ minute;// date.getDate() + '.'+date.getMonth();//+"
	// "+date.getHours()+":"+date.getMinutes();
}

$(function() {

	$('#sendBtn').click(function() {
		// cm();
		createMessage();
		$(this).blur();
		$('#SenderSelectList').val("pkornelak");
		$('#ReceiverSelectList').val("pkornelak2");
		$('#Body').val("");
	})

})
//
//
//
//function cm() {
//	alert($('#SenderSelectList').val() + '->' + $('#ReceiverSelectList').val());
//
//}
function createMessage() {
	var message = {
		senderId : $('#SenderSelectList').val(),
		receiverId : $('#ReceiverSelectList').val(),
		body : $('#Body').val()
	}
	// alert($('#SenderSelectList').val()+'->'+$('#ReceiverSelectList').val());
	$.post('/api/message', JSON.stringify(message), function(data) {
		// appendMessage(data);
	}, 'json');

}