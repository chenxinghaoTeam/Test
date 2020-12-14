var name = "";
var address = "";
setInterval(function() {
	var date = new Date();
	var year = date.getFullYear(); // 获取当前年份
	var mon = date.getMonth() + 1; // 获取当前月份
	var da = date.getDate(); // 获取当前日
	var day = date.getDay(); // 获取当前星期几
	var h = date.getHours(); // 获取小时
	var m = date.getMinutes(); // 获取分钟
	var s = date.getSeconds(); // 获取秒
	$(".aa").text(
			'当前时间:' + year + '年' + mon + '月' + da + '日' + '星期' + day + ' ' + h
					+ ':' + m + ':' + s);
}, 1000)

window.onload = function() {
	_getUrlPath();// 获取上级页面传过来的参数
	var oTable = new TableInit();
	oTable.Init();
	// 初始化加载
	initPage();
	// 点击事件
	clickBtn();
}

function _getUrlPath() {
	// 获得传过来的login与在数据库中对应的表单
	var paras = location.search; // search获得地址中的参数，内容为'?itemId=12'
	var result = paras.match(/[^\?&]*=[^&]*/g); // match是字符串中符合的字段一个一个取出来，result中的值为['login=xx','table=admin']
	paras = {}; // 让paras变成没有内容的json对象
	for (i in result) {
		var temp = result[i].split('='); // split()将一个字符串分解成一个数组,两次遍历result中的值分别为['itemId','xx']
		paras[temp[0]] = decodeURI(temp[1], "utf-8");// 中文乱码使用decodeURI()
	}
	if (paras.name) {
		$("#user").text(paras.name)
	}
}

// 列表数据
function initPage() {
	param = {
		"name" : "",
		"firstName" : name,
		"address" : address
	}
	var data = invokeAjax("/controller/initPage", param);
	if (data.successful) {
		if (data) {
			$("#tb_departments").bootstrapTable('load', data);// 查询重新刷新表格
		}
	}
}

function clickBtn() {
	// 注册新增按钮的事件
	$("#btn_add").click(function() {
		$('#myModal').modal();
		// 保存点击事件
		var flag = true;
		$("#btn_submit").click(function() {
			if (flag) {
				var name = $("#txt_name").val();
				var firstName = $("#txt_firstName").val();
				var address = $("#txt_address").val();
				var city = $("#txt_city").val();
				param = {
					"lastName" : name,
					"address" : address,
					"firstName" : firstName,
					"city" : city
				}
				var data = invokeAjax("/controller/add", param);
				if (data.successful) {
					initPage();// 查询重新刷新表格
					flag = false;
				}
			}
		})
	});

	// 删除点击事件
	$("#btn_delete").click(function() {
		var rows = $("#tb_departments").bootstrapTable("getSelections");
		if (rows.length == 0) {
			alert("请先选择要删除的记录!")
			return;
		} else {
			$('#delcfmModel').modal();
			$("#deleteHaulBtn").click(function() {
				var id = "";
				for (var i = 0; i < rows.length; i++) {
					if (i == rows.length - 1) {
						id += rows[i].ID_P
					} else {
						id += rows[i].ID_P + ","
					}
				}
				param = {
					"ids" : id
				}
				var data = invokeAjax("/controller/delete", param);
				if (data.successful) {
					initPage();// 查询重新刷新表格
				}
			})
		}
	})

	// 修改点击事件
	$("#btn_edit").click(function() {
		var rows = $("#tb_departments").bootstrapTable("getSelections");
		if (rows.length == 0) {
			alert("请先选择要修改的记录!")
			return;
		} else if (rows.length > 1) {
			alert("请选择一条数据修改!")
			return;
		} else {
			$('#myModal1').modal();
			var id = rows[0].ID_P;
			var lastName = rows[0].LASTNAME;
			var firstName = rows[0].FIRSTNAME;
			var address = rows[0].ADDRESS;
			var city = rows[0].CITY;
			$("#txt_name1").val(firstName);
			$("#txt_firstName1").val(lastName);
			$("#txt_address1").val(address);
			$("#txt_city1").val(city);
			$("#btn_submit1").click(function() {
				param = {
					"lastName" : $("#txt_firstName1").val(),
					"address" : $("#txt_address1").val(),
					"firstName" : $("#txt_name1").val(),
					"city" : $("#txt_city1").val(),
					"id" : id
				}
				var data = invokeAjax("/controller/update", param);
				if (data.successful) {
					initPage();// 查询重新刷新表格
				}
			})
		}
	})

	// 查询事件
	$("#btn_query").click(function() {
		name = $("#txt_search_departmentname").val();
		address = $("#txt_search_statu").val();
		initPage();
	})

	// 重置
	$("#btn_cz").click(function() {
		$("#txt_search_departmentname").val("");
		$("#txt_search_statu").val("");
	})

	// 导出
	$("#btn_dc").click(function() {
		//批量导出id
		var rows = $("#tb_departments").bootstrapTable("getSelections");
		var queryId = "";
		for (var i = 0; i < rows.length; i++) {
			if(i == 0){
				queryId = rows[i].ID_P;
			}else{
				queryId+=","+rows[i].ID_P
			}
		}
		window.location.href="/ExcelController/downloadAllClassmate?queryId="+queryId+"&name="+name+"&address="+address;
	})
	
	//退出操作
	$("#tc").click(function() {
		window.location.href="/controller/goOut";
	})
}

/** *****************************以下为公共方法*************************************** */
var TableInit = function() {
	var oTableInit = new Object();
	// 初始化Table
	oTableInit.Init = function() {
		$('#tb_departments').bootstrapTable({
			url : '', // 请求后台的URL（*）
			method : 'get', // 请求方式（*）
			toolbar : '#toolbar', // 工具按钮用哪个容器
			striped : true, // 是否显示行间隔色
			cache : false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
			pagination : true, // 是否显示分页（*）
			sortable : false, // 是否启用排序
			sortOrder : "asc", // 排序方式
			queryParams : "",// 传递参数（*）
			sidePagination : "client", // 分页方式：client客户端分页，server服务端分页（*）
			pageNumber : 1, // 初始化加载第一页，默认第一页
			pageSize : 10, // 每页的记录行数（*）
			pageList : [ 10, 25, 50, 100 ], // 可供选择的每页的行数（*）
			// search: true, //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
			strictSearch : true,
			showColumns : true, // 是否显示所有的列
			showRefresh : true, // 是否显示刷新按钮
			minimumCountColumns : 0, // 最少允许的列数
			clickToSelect : true, // 是否启用点击选中行
			height : 0, // 行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
			uniqueId : "ID", // 每一行的唯一标识，一般为主键列
			showToggle : true, // 是否显示详细视图和列表视图的切换按钮
			cardView : false, // 是否显示详细视图
			detailView : false, // 是否显示父子表
			columns : [ {
				checkbox : true
			}, {

				field : 'LASTNAME',
				title : '初始姓名'
			}, {
				field : 'FIRSTNAME',
				title : '小名'
			}, {
				field : 'ADDRESS',
				title : '地区'
			}, {
				field : 'CITY',
				title : '城市'
			} ]
		});
	};

	return oTableInit;
};

/**
 * ajax请求
 * 
 * @param pathUrl
 * @param param
 * @returns
 */
function invokeAjax(pathUrl, param) {
	var dateValue = null;
	$.ajax({
		type : "POST",
		url : pathUrl,
		async : false,
		data : {
			// 把参数序列化成字符串
			jsonParm : JSON.stringify(param)
		},
		dataType : "json",
		success : function(data) {
			dateValue = data;
		},
		error : function(e) {
			layer.msg('error');
		}
	});
	return dateValue;
}
