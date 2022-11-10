function subform() {
	
	$.ajax({
		url: '/tovacate',
		type: 'POST',
		cache: false,
		data: new FormData($('#uploadForm')[0]),
		//ajax2.0可以不用设置请求头，但是jq帮我们自动设置了，这样的话需要我们自己取消掉
		processData: false,
		//取消帮我们格式化数据，是什么就是什么
		contentType: false,
		before:function (){
			$("#result").text("正在上传...");
		},
		success:function (msg) {
			$("#result").text(msg);
			alert(msg);
		}
	});
}

function subform2() {
	
	$.ajax({
		url: '/cancelvacate',
		type: 'POST',
		cache: false,
		data: new FormData($('#uploadForm2')[0]),
		processData: false,
		contentType: false,
		success:function (msg) {
			$("#result2").text(msg);
			alert(msg);
		}
	});
}

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