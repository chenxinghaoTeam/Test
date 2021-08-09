window.onload = function() {
	$("#login").click(function() {
		var user = $("#user").val();
		if ("WebSocket" in window) {
			webSocket = new WebSocket("ws://localhost:8081/cs/" + user);
			// 连通之后的回调事件
			webSocket.onopen = function() {
				// webSocket.send(
				// document.getElementById('username').value+"已经上线了");
				console.log("已经连通了websocket");
				setMessageInnerHTML("欢迎来到VIP聊天室！");
			};
			// 接收后台服务端的消息
			webSocket.onmessage = function(evt) {
				var received_msg = evt.data;
				console.log("数据已接收:" + received_msg);
				var obj = JSON.parse(received_msg);
				console.log("可以解析成json:" + obj.messageType);
				// 1代表上线 2代表下线 3代表在线名单 4代表普通消息
				if (obj.messageType == 1) {
					// 把名称放入到selection当中供选择
					var onlineName = obj.username;
					var option = "<option>" + onlineName + "</option>";
					$("#onLineUser").append(option);
					setMessageInnerHTML(onlineName + "上线了");
				} else if (obj.messageType == 2) {
					$("#onLineUser").empty();
					var onlineName = obj.onlineUsers;
					var offlineName = obj.username;
					var option = "<option>" + "--所有--" + "</option>";
					for (var i = 0; i < onlineName.length; i++) {
						if (!(onlineName[i] == document.getElementById('user').value)) {
							option += "<option>" + onlineName[i] + "</option>"
						}
					}
					$("#onLineUser").append(option);

					setMessageInnerHTML(offlineName + "下线了");
				} else if (obj.messageType == 3) {
					var onlineName = obj.onlineUsers;
					var option = null;
					for (var i = 0; i < onlineName.length; i++) {
						if (!(onlineName[i] == document.getElementById('user').value)) {
							option += "<option>" + onlineName[i] + "</option>"
						}
					}
					$("#onLineUser").append(option);
					console.log("获取了在线的名单" + onlineName.toString());
				} else {
					setMessageInnerHTML(obj.fromusername + "对" + obj.tousername + "说：" + obj.textMessage);
				}
			};

			// 连接关闭的回调事件
			webSocket.onclose = function() {
				console.log("连接已关闭...");
				setMessageInnerHTML("连接已经关闭....");
			};
		} else {
			// 浏览器不支持 WebSocket
			alert("您的浏览器不支持 WebSocket!");
		}

	})

	// 将消息显示在网页上
	function setMessageInnerHTML(innerHTML) {
		document.getElementById('result').innerHTML += innerHTML + '<br/>';
	}

	$("#send").click(function() {
		var message = $("input[name=tenantIds]").val();
		if (message == "") {
			displayMsg("访问消息不能为空")
		} else {
			var selectText = $("#onLineUser").find("option:selected").text();
			if (selectText == "--所有--") {
				selectText = "All";
			} else {
				setMessageInnerHTML(document.getElementById('user').value + "对" + selectText + "说：" + $("#saytext").val());
			}
			var message = {
				"message" : document.getElementById('saytext').value,
				"username" : document.getElementById('user').value,
				"to" : selectText
			};
			webSocket.send(JSON.stringify(message));
		}
	})
}
// 关闭连接
function closeWebSocket() {
	websocket.close();
}

function displayMsg(innerHTML) {
	document.getElementById('result').innerHTML += innerHTML + '<br/>';
}
