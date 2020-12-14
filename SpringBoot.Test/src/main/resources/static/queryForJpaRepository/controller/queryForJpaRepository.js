window.onload=function(){
	   var oTable = new TableInit();
	    oTable.Init();

	    
	    $("#btn_query").click(function(){
	    	var name =  $("#txt_search_departmentname").val();
	    	var address =  $("#txt_search_statu").val();
	    	param = {
	    			"name" : name,
	    			"address":address
	    	}
	    	var data = invokeAjax("/jpaRepository/getResult",param);
	    	$("#tb_departments").bootstrapTable('load', data);// 查询重新刷新表格
	    })
	    
	    
	    
	    
	     // 注册新增按钮的事件
	     $("#btn_add").click(function () {
	         $('#myModal').modal();
	         // 保存点击事件
	         $("#btn_submit").click(function(){
 	 	    	var name =  $("#txt_name").val();
 	 	    	var firstName =  $("#txt_firstName").val();
 	 	    	var address =  $("#txt_address").val();
 	 	    	var city =  $("#txt_city").val();
	    	 	   	param = {
	    	    			"name" : name,
	    	    			"address":address,
	    	    			"firstName" : firstName,
	    	    			"city" : city
	    	    	}
	    	 	   	var data = invokeAjax("/jpaRepository/add",param);
	    	 	   	if(data.addSussful == 1){
	    	 		  $("#tb_departments").bootstrapTable('refresh', {url:'/jpaRepository/getResult'});// 查询重新刷新表格
	    	 	   	}
 	 	    })	        	    
	     });
	    
	    
	    // 修改点击事件
	    $("#btn_edit").click(function(){
	    	var rows = $("#tb_departments").bootstrapTable("getSelections");
	    	if(rows.length == 0){
	    		alert("请先选择要修改的记录!")
	    		return ;
	    	}else{
		         $('#myModal1').modal();
		         var id = rows[0].id;
		         var lastName = rows[0].lastName;
		         var firstName = rows[0].firstName;
		         var address = rows[0].address;
		         var city = rows[0].city;
		         $("#txt_name1").val(firstName);
		         $("#txt_firstName1").val(lastName);
		         $("#txt_address1").val(address);
		         $("#txt_city1").val(city);
		        	  $("#btn_submit1").click(function(){
				        	 $.ajax({
						            type: "POST",
						            url: "/jpaRepository/update",
						            data: {
						            	"lastName":$("#txt_firstName1").val(),
						            	"address":$("#txt_address1").val(),
						            	"firstName": $("#txt_name1").val(),
						            	"city": $("#txt_city1").val(),
						            	"id":id
						            	},
						            dataType: "json",
						            success: function(data){
						            	alert("修改成功!")
						            	$("#tb_departments").bootstrapTable('refresh', {url:'/jpaRepository/getResult'});// 查询重新刷新表格
						            },
						         });     
				         })
	    	}
	    })
	    
	    // 删除点击事件
	    $("#btn_delete").click(function(){
	    	var rows = $("#tb_departments").bootstrapTable("getSelections");
	    	if(rows.length == 0){
	    		alert("请先选择要删除的记录!")
	    		return ;
	    	}else{
	    		$('#delcfmModel').modal();
	    		$("#deleteHaulBtn").click(function(){
	    			var id = "";
	    			for (var i = 0; i < rows.length; i++) {
	    				if(i == rows.length-1){
	    					id += rows[i].id
	    				}else{
	    					id += rows[i].id+","
	    				}
					}
	    			param = {
	    	    			"id" : id
	    	    	}
	    			var data = invokeAjax("/jpaRepository/delete",param);
	    			if(data.deleteSussful >= 1){
		    	 		 $("#tb_departments").bootstrapTable('refresh', {url:'/jpaRepository/getResult'});// 查询重新刷新表格
		    	 	   	}
	    		})
	    	}
	    })
}











		var TableInit = function () {
		    var oTableInit = new Object();
		    // 初始化Table
		    oTableInit.Init = function () {
		        $('#tb_departments').bootstrapTable({
		            url: '/jpaRepository/getResult',         // 请求后台的URL（*）
		            method: 'get',                      // 请求方式（*）
		            toolbar: '#toolbar',                // 工具按钮用哪个容器
		            striped: true,                      // 是否显示行间隔色
		            cache: false,                       // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
		            pagination: true,                   // 是否显示分页（*）
		            sortable: false,                     // 是否启用排序
		            sortOrder: "asc",                   // 排序方式
		            queryParams: "",// 传递参数（*）
		            sidePagination: "server",           // 分页方式：client客户端分页，server服务端分页（*）
		            pageNumber:1,                       // 初始化加载第一页，默认第一页
		            pageSize: 10,                       // 每页的记录行数（*）
		            pageList: [10, 25, 50, 100],        // 可供选择的每页的行数（*）
		            // search: true, //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
		            strictSearch: true,
		            showColumns: true,                  // 是否显示所有的列
		            showRefresh: true,                  // 是否显示刷新按钮
		            minimumCountColumns: 0,             // 最少允许的列数
		            clickToSelect: true,                // 是否启用点击选中行
		            height: 0,                        // 行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
		            uniqueId: "ID",                     // 每一行的唯一标识，一般为主键列
		            showToggle:true,                    // 是否显示详细视图和列表视图的切换按钮
		            cardView: false,                    // 是否显示详细视图
		            detailView: false,                   // 是否显示父子表
		            columns: [{
		                checkbox: true
		            }, {
		                field: 'firstName',
		                title: '小名'
		            }, {
		                field: 'lastName',
		                title: '初始姓名'
		            }, {
		                field: 'address',
		                title: '地区'
		            }, {
		                field: 'city',
		                title: '城市'
		            }, ]
		        });
		    };
		
		    return oTableInit;
		};



			// ajax 同步调用
			function invokeAjax(pathUrl,param){
				var dateValue = null;
				$.ajax({
			        type: "POST",
			        url: pathUrl,
			        async: false,
			        data: {
			        	//把参数序列化成字符串
			        	jsonParm : JSON.stringify({ param })
			        },
			        dataType: "json",
			        success: function(data){
			        	dateValue = data;
			        },
			        error:function(e){
			            layer.msg('error');
			        }
			     });
				return dateValue;
}

