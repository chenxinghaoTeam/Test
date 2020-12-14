window.onload=function(){
	initPage();
	
	$("#login").click(function(){
		param = {
			"username":$("#username").val(),
			"password":$("#password").val()
		}
		var data = invokeAjax("/controller/login",param);
		if(data == 1){
			location.replace("/showView/index.html?id=123");
		}else{
			$('#element').popover('show')
		}
	})
}





function initPage (){
	param = {
		"cs":"南京",
		"dq":""
	}
	var data = invokeAjax("/controller/listu",param);
	if(data){
		var html = "";
		for (var i = 0; i < data.length; i++) {
			html += "城市:"+data[i].CITY+"地区:"+data[i].ADDRESS+"姓名:"+data[i].FIRSTNAME+"小名:"+data[i].LASTNAME;
		}
		$("#tetx").html(html)
	}
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


			

