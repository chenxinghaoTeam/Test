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
	if(s<10){
		s="0"+s
	}
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
		var date = new Date();
		var text = "";
		if(date.getHours()>=0 && date.getHours()<12){
			text = ",早上好！"
		}else if(date.getHours()>12 && date.getHours()<17){
			text = ",中午好！"
		}else if(date.getHours()>17 && date.getHours()<24){
			text = ",晚上好！"
		}
		$("#user").text("尊敬的"+(paras.name)+text);
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


// echarts图标请求
function getecharts(){
	// 柱图
	getBar();
	// 饼图
	getPie();
	// 折现图
	getLine();
	// 环形图
	getRing();
}


function getBar(){
	// 基于准备好的dom，初始化echarts实例
	var myChart = echarts.init(document.getElementById('bar'));
	// 绘制图表
	myChart.setOption({
	    title: {
	        text: '柱图'
	    },
	    tooltip: {},
	    xAxis: {
	        data: ['衬衫', '羊毛衫', '雪纺衫', '裤子', '高跟鞋', '袜子']
	    },
	    yAxis: {},
	    series: [{
	        name: '销量',
	        type: 'bar',
	        data: [5, 20, 36, 10, 10, 20]
	    }]
	});
}


function getPie(){
	// 基于准备好的dom，初始化echarts实例
	var myChart = echarts.init(document.getElementById('pie'));
	// 绘制图表
	myChart.setOption({
		 title: {
		        text: '饼图'
		    },
		    tooltip: {
		        trigger: 'item',
		        formatter: '{a} <br/>{b} : {c} ({d}%)'
		    },
		    legend: {
		        orient: 'vertical',
		        left: 'right',
		        data: ['直接访问', '邮件营销', '联盟广告', '视频广告', '搜索引擎']
		    },
		    series: [
		        {
		            name: '访问来源',
		            type: 'pie',
		            radius: '55%',
		            center: ['50%', '60%'],
		            data: [
		                {value: 335, name: '直接访问'},
		                {value: 310, name: '邮件营销'},
		                {value: 234, name: '联盟广告'},
		                {value: 135, name: '视频广告'},
		                {value: 1548, name: '搜索引擎'}
		            ],
		            emphasis: {
		                itemStyle: {
		                    shadowBlur: 10,
		                    shadowOffsetX: 0,
		                    shadowColor: 'rgba(0, 0, 0, 0.5)'
		                }
		            }
		        }
		    ]
	});
}


function getLine(){
	// 基于准备好的dom，初始化echarts实例
	var myChart = echarts.init(document.getElementById('line'));
	// 绘制图表
	myChart.setOption({
		 title: {
		        text: '折线图'
		    },
	    xAxis: {
	        type: 'category',
	        data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
	    },
	    yAxis: {
	        type: 'value'
	    },
	    series: [{
	        data: [820, 932, 901, 934, 1290, 1330, 1320],
	        type: 'line'
	    }]
	});
}


function getRing(){
	// 基于准备好的dom，初始化echarts实例
	var myChart = echarts.init(document.getElementById('ring'));
	// 绘制图表
	myChart.setOption({
		 title: {
		        text: '环形图'
		    },
		 tooltip: {
		        trigger: 'item',
		        formatter: '{a} <br/>{b}: {c} ({d}%)'
		    },
		    legend: {
		        orient: 'vertical',
		        left: 'right',
		        data: ['直接访问', '邮件营销', '联盟广告', '视频广告', '搜索引擎']
		    },
		    series: [
		        {
		            name: '访问来源',
		            type: 'pie',
		            radius: ['50%', '70%'],
		            avoidLabelOverlap: false,
		            label: {
		                show: false,
		                position: 'center'
		            },
		            emphasis: {
		                label: {
		                    show: true,
		                    fontSize: '30',
		                    fontWeight: 'bold'
		                }
		            },
		            labelLine: {
		                show: false
		            },
		            data: [
		                {value: 335, name: '直接访问'},
		                {value: 310, name: '邮件营销'},
		                {value: 234, name: '联盟广告'},
		                {value: 135, name: '视频广告'},
		                {value: 1548, name: '搜索引擎'}
		            ]
		        }
		    ]
	});
}


// 提示框
function showMessage(message,type,time) {
    let str = ''
    switch (type) {
        case 'success':
            str = '<div class="success-message" style="width: 300px;height: 40px;text-align: center;background-color:#daf5eb;;color: rgba(59,128,58,0.7);position: fixed;left: 43%;top: 35%;line-height: 40px;border-radius: 5px;z-index: 9999">\n' +
                '    <span class="mes-text">'+message+'</span></div>'
            break;
        case 'error':
            str = '<div class="error-message" style="width: 300px;height: 40px;text-align: center;background-color: #f5f0e5;color: rgba(238,99,99,0.8);position: fixed;left: 43%;top: 35%;line-height: 40px;border-radius: 5px;;z-index: 9999">\n' +
                '    <span class="mes-text">'+message+'</span></div>'
    }
    $('body').append(str)
    setTimeout(function () {
        $('.'+type+'-message').remove()
    },time)
}



function clickBtn() {
	// 导航树
	$("ul li").click(function() {
		if(this.innerText == "echarts展示"){
			getecharts();
		}else if(this.innerText == "system"){
			
		}else if(this.innerText == "人员信息"){
			initPage();
		}
	})
	
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
					showMessage("新增成功!","success","1000")
					flag = false;
				}
			}
		})
	});

	// 删除点击事件
	$("#btn_delete").click(function() {
		var rows = $("#tb_departments").bootstrapTable("getSelections");
		if (rows.length == 0) {
			showMessage("请选择再删除!",'error',"1000");
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
					showMessage("删除成功!","success","1000")
				}
			})
		}
	})

	// 修改点击事件
	$("#btn_edit").click(function() {
		var rows = $("#tb_departments").bootstrapTable("getSelections");
		if (rows.length == 0) {
			showMessage("请先选择要修改的记录!",'error',"1000");
			return;
		} else if (rows.length > 1) {
			showMessage("请选择一条数据修改!",'error',"1000");
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
					showMessage("修改成功!","success","1000")
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
		// 批量导出id
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
	
	// 退出操作
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
