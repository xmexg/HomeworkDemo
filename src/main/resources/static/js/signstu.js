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


function rotate(){//未完成的转向功能
	var form = document.querySelector('#drawtable');
	var div = document.querySelector('#drawdiv');
	var formwidth = form.offsetWidth;
	var formheight = form.offsetHeight;
	form.style.width=formwidth+"px";
	form.style.height=formheight+"px";
	form.style.transform="rotate(90deg)";
	div.style.width=formheight+"px";
	div.style.height=formwidth+"px";
	console.log("表格宽度："+formwidth);
	console.log(formheight);
}