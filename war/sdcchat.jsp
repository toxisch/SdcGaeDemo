<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>

<html>
	<body>
	
		<!-- Receive messages : GAE ChannelClient implementation -->
		<script type="text/javascript" src="/_ah/channel/jsapi"></script>
		<script>
			channel = new goog.appengine.Channel('${token}');
			socket = channel.open();
			socket.onopen = onOpened;
			socket.onmessage = onMessage;
			socket.onerror = onError;
			socket.onclose = onClose;
	
			
			function sendMessage(path, message) {
				path = "/sdcchat";
				message = document.getElementById("text").value;
				path += '?message=' + message;
				var xhr = new XMLHttpRequest();
				xhr.open('POST', path, true);
				xhr.send();
			};
	
			function onOpened() {
				//alert("Channel opened!");
			}
	
			function onMessage(msg) {
				//alert("data: " + msg.data);
				document.getElementById("content").innerHTML = "<li>" + msg.data
						+ "</li>" + document.getElementById("content").innerHTML;
			}
	
			function onError(err) {
				alert("error: " + err.data);
			}
	
			function onClose() {
				// alert("Channel closed!");
			}
		</script>
		
		<!-- Send messages : simple ajax post -->
		<script>
			function sendMessage(path, message) {
				path = "/sdcchat";
				message = document.getElementById("text").value;
				path += '?message=' + message;
				var xhr = new XMLHttpRequest();
				xhr.open('POST', path, true);
				xhr.send();
			};
		</script>
	
	    <!-- Account handling -->
		<div>
			${userName}, <a href="<c:url value="${userLogout}"/>">logout</a>
		</div>
	
	    <!-- Message form -->
		<div>
			<form>
				<textarea id="text"></textarea><br/>
				<input onclick="javascript:sendMessage()" value="Send">
			</form>
		</div>
	
	    <!-- Area to display all messages -->
		<div id="content"></div>
	
	</body>
</html>