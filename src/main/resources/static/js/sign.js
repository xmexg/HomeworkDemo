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

function subform() {
	
	$.ajax({
		url: '/manage',
		type: 'POST',
		cache: false,
		data: new FormData($('#checkform')[0]),
		processData: false,
		contentType: false,
		success:function (msg) {
			if(msg.lenght!=0){
				$("#checkdiv").html(msg);
			}
		}
	});
}