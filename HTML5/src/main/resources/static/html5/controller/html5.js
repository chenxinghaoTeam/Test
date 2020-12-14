window.onload=function(){
	showText();
	var c=document.getElementById("myCanvas");
	var ctx=c.getContext("2d");
	// 创建渐变
	var grd=ctx.createLinearGradient(0,0,200,0);
	grd.addColorStop(0,"red");
	grd.addColorStop(1,"white");
	 
	// 填充渐变
	ctx.fillStyle=grd;
	ctx.fillRect(10,10,150,80);
	
	/**
	 * 画横
	 */
	ctx.moveTo(0,0); //起点
	ctx.lineTo(200,100); //终点
	ctx.stroke(); //画横
}


function showText(){
	//每个 Vue 应用都需要通过实例化 Vue 来实现。
	 var vm = new Vue({
		 	//它是 DOM 元素中的 id
	        el: '#vue_det', 
	        //data 用于定义属性，实例中有三个属性分别为：site、url、alexa。
	        data: { 
	            sit: "菜鸟教程",
	            url: "www.runoob.com",
	            alexa: "10000"
	        },
	        //methods 用于定义的函数，可以通过 return 来返回函数值。
	        methods: {
	            details: function() {
	                return  this.sit + " - 学的不仅是技术，更是梦想！";
	            }
	        }
	    })
}