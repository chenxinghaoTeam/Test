//当前活动的方块, 它可以左右下移动, 变型。当它触底后, 将会更新area; 
var activeBlock; 
window.onload=function(){
	/**
	 * 开始游戏点击事件
	 */
	$("#btn-start").click(function(){
		$('.start-container').css('display','none');
		$(".game-container").show();
		generateBlock();
		drawGrid();
	})
}



/**
 * 绘制网格
 * @returns
 */
function drawGrid(){
	var canvas = document.getElementById("canvas");
	var context = canvas.getContext("2d");
    context.strokeStyle = 'green';
    context.lineWidth = 0.5;
    for(var i = 30+0.5;i<context.canvas.width;i+=30){
        context.beginPath();
        context.moveTo(i,0);
        context.lineTo(i,context.canvas.height);
        context.stroke();
    }

    for(var i = 10+0.5;i<context.canvas.height;i+=10){
        context.beginPath();
        context.moveTo(0,i);
        context.lineTo(context.canvas.width,i);
        context.stroke();
    }
}







//生产方块形状, 有7种基本形状。 
function generateBlock(){ 
  activeBlock = null; 
  activeBlock = new Array(4); 
  //随机产生0-6数组，代表7种形态。
//  var t = (Math.floor(Math.random()*20)+1)%7; 
  var t = 0; 
  switch(t){ 
    case 0:{ 
      activeBlock[0] = {x:0, y:4}; 
      activeBlock[1] = {x:1, y:4}; 
      activeBlock[2] = {x:0, y:5}; 
      activeBlock[3] = {x:1, y:5}; 
 
      break; 
    } 
    case 6:{ 
      activeBlock[0] = {x:0, y:5}; 
      activeBlock[1] = {x:1, y:4}; 
      activeBlock[2] = {x:1, y:5}; 
      activeBlock[3] = {x:1, y:6}; 
      break; 
    } 
  } 
//  //检查刚生产的四个小方格是否可以放在初始化的位置. 
//  for(var i=0; i<4; i++){ 
//    if(!isCellValid(activeBlock[i].x, activeBlock[i].y)){ 
//        return false; 
//      } 
//    } 
  return true; 
}