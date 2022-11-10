var timer = setInterval(function(){
	$.ajax({
		url: '/getSimpleStuInfo',
		type: 'POST',
		cache: false,
		data: "",
		processData: false,
		contentType: false,
		success:function (mas) {
			var text = mas.split(",");
			$("#info_total").text("总计:"+text[0]);
			$("#info_reality").text("实到:"+text[1]);
			$("#info_vacate").text("请假:"+text[2]);
//			console.log(text);
		}
	});
  }, 5000);

function startsign() {
	
	$.ajax({
		url: '/signcontrolstart',
		type: 'POST',
		cache: false,
		data: "",
		processData: false,
		contentType: false,
		success:function (msg) {
			if(msg=="identity"){
				//重定向到新页面
				window.location.href="/sign";
			}else{
				$("#btnstart").css("visibility","hidden");
				$("#btnstop").css("visibility","visible");
				$("#show").css("display","flex");
				//base64转图片
				var img = new Image();
				img.src = "data:image/png;base64,"+msg;
				$("#img").attr("src",img.src);
				$("#img").css("display","block");
				$("#img").css("margin","0 auto");
				$("#img").css("border-radius","10px");
				$("#img").css("box-shadow","0 0 10px #000");
			}			
		}
	});
}

function stopsign() {
	
	$.ajax({
		url: '/signcontrolstop',
		type: 'POST',
		cache: false,
		data: "",
		processData: false,
		contentType: false,
		success:function (msg) {
			if(msg=="success"){
				$("#btnstart").css("visibility","visible");
				$("#btnstop").css("visibility","collapse");
				$("#show").css("display","none");
			}
			if(msg=="identity"){
				//重定向
				window.location.href="/sign";
			}
		}
	});
}