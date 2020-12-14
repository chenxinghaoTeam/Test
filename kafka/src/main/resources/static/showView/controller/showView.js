window.onload=function(){
	
	//获得传过来的login与在数据库中对应的表单
	var paras = location.search;            //search获得地址中的参数，内容为'?itemId=12'
	var result = paras.match(/[^\?&]*=[^&]*/g);     //match是字符串中符合的字段一个一个取出来，result中的值为['login=xx','table=admin']
	paras = {};                    //让paras变成没有内容的json对象
	for(i in result){
	    var temp = result[i].split('=');    //split()将一个字符串分解成一个数组,两次遍历result中的值分别为['itemId','xx']
	    paras[temp[0]] = temp[1];
	}
	var itemId = paras.id; 
	
	
	$("#upload").click(function(){
		 var formData = new FormData($("#file"));
		var path = $("#file").val();
		param={
				"path":path
		}
		var data = invokeAjax("/controller/importOrcale",param)
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


			

