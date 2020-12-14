window.onload=function(){
	$("#cxh").click(function(){
		window.location.href="/test/exportExcel";//重定向导出

	})
}

/**
 * ajax调用
 */
function getUrl(){
	$.ajax({     
    type: "Post",     
    url: "/test/service",     
    dataType: "json", 
    data:{},
    success: function(data) {
    	if(data.length > 0){
    		$("#aaa").html(data[0].cxh)
    	}
    },     
    error: function(err) {     
        alert("失敗")     
    }     
});  
}



