window.onload=function(){
	$("#login").click(function(){
		param = {
			"username":$("#username").val(),
			"password":$("#password").val()
		}
		var data = invokeAjax("/controller/login",param);
		if(data.successful){
			$("#textnr").text("登陆成功。。。").css("color","#38e60d");
			setTimeout(function(){
				location.replace("/testView/index.html?name="+$("#username").val());
			},1000)
			
		}else{
			$("#textnr").text("用户名或密码错误！！！").css("color","red");
		}
	})
}


//ajax 同步调用
function invokeAjax(pathUrl,param){
	var dateValue = null;
	$.ajax({
        type: "GET",
        url: pathUrl,
        async: false,
        data: {
        	//把参数序列化成字符串 jsonParm需要和controller中接受参数对应
        	jsonParm : JSON.stringify(param)
        },
        dataType: "json",
        success: function(data){
        	dateValue = data;
        },
     });
	return dateValue;
}

