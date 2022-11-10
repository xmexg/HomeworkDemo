package org.mexvina.statistics;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

@RestController
public class Acquire {

	@RequestMapping("/test")
	public String hello() {
		System.out.println("进入了测试页面");
		return "这是一个测试页面";
	}

	@RequestMapping("/cancelvacate")//取消请假
	public String cancelvacate(String name, HttpServletRequest request) {

		// 空名字检查
		if (name == null || name.length() == 0) {
			return "没有名字";
		}

		if (!Arrays.asList(Student.TOTALSTU).contains(name)) {
			return "社团成员中没有这个姓名";
		}

		String[] info = Student.delVACATESTU(name);

		if (info[0].equals("true")) {
			// 这里以后删除请假条
			System.out.println("删除文件:" + App.parentDir + info[3]);
			new File(App.parentDir + info[3]).delete();
			return name + " 已取消请假";
		} else {
			for (int j = 0; j < Student.VACATESTU_NUM; j++) {
				if (Student.VACATESTU[j][0].equals(name)) {
					return name + " 取消请假失败,请稍后重试";
				}
			}
			return name + " 没有请假";
		}
	}

	@RequestMapping("/tovacate")//请假
	public String tovacate(String name, @RequestParam("file") MultipartFile file, String reason, HttpServletRequest request)
			throws IllegalStateException, IOException {

		// 空名字检查
		if (name == null || name.length() == 0) {
			return "没有名字";
		}

		if (!Arrays.asList(Student.TOTALSTU).contains(name)) {
			return "社团成员中没有这个姓名";
		}

		for (int j = 0; j < Student.VACATESTU_NUM; j++) {
			if (Student.VACATESTU[j][0].equals(name)) {
				return "你已请假";
			}
		}

		// 判断上传的文件是否为空
		boolean isEmpty = file.isEmpty();
		System.out.println("\tisEmpty=" + isEmpty);
		if (isEmpty) {
			return "没有请假条";
		}
		
		//检查请假理由
		if(reason == null) {
			return "没有请假原因";
		}
		reason = reason.replaceAll(" ", "");//删除所有空格
		reason = reason.replaceAll("\n", "");//删除所有换行
		if(reason.length() == 0) {
			return "请认真填写请假原因";
		}

		// 检查文件大小
		long fileSize = file.getSize();
		System.out.println("\tsize=" + fileSize);
		if (fileSize > 20 * 1024 * 1024) {
			return "上传失败！上传的文件大小超出了限制！";
		}

		// 检查文件MIME类型
		String contentType = file.getContentType();
		System.out.println("\tcontentType=" + contentType);

		// 限定上传文件的类型为图片格式
		List<String> types = new ArrayList<String>();
		types.add("image/jpeg");
		types.add("image/png");
		types.add("image/jpg");
		if (!types.contains(contentType)) {
			return "上传失败！不允许上传此类型的文件！";
		}

		// 准备文件夹,获取项目中upload文件夹的路径
//	        String parentDir = request.getServletContext().getRealPath("upload");
//	        File directory = new File("");//设定为当前文件夹
//	        String parentDir=directory.getAbsolutePath()+"\\src\\main\\resources\\static\\uploadFile";

		String parentDir = App.parentDir;

		System.out.println("\tpath=" + parentDir);
		File parent = new File(parentDir);
		if (!parent.exists()) {
			parent.mkdirs();
		}

		// 获取原始文件名
		String originalFilename = file.getOriginalFilename();
		System.out.println("\toriginalFilename=" + originalFilename);

		// 确定最终保存时使用的文件
//	        String filename = UUID.randomUUID().toString();
//	        String suffix = "";
//	        int beginIndex = originalFilename.lastIndexOf(".");
//	        if (beginIndex != -1) {
//	            suffix = originalFilename.substring(beginIndex);
//	        }

		// 执行保存文件
//	        File dest = new File(parent, filename + suffix);
//	        file.transferTo(dest);

		// 获取当前时间
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy~MM~dd_HH-mm-ss");
		String time = sdf.format(date);

		// 获取文件名后缀
		String suffix = "";
		int beginIndex = originalFilename.lastIndexOf(".");
		if (beginIndex != -1) {
			suffix = originalFilename.substring(beginIndex);
		}

		String savename = name + time + suffix;

		Student.addVACATESTU(name, time, savename, reason);

		// 保存文件
		File dest = new File(parent, savename);
		file.transferTo(dest);

		return name + " 请假成功";
	}

	@RequestMapping("/getSimpleStuInfo") // 简易的信息,只有总计,实到,请假人数
	public String getStuSimpleInfo(HttpServletRequest request) {
		return Student.TOTALSTU.length + "," + "0" + "," + Student.VACATESTU_NUM;
	}

	@RequestMapping("/manage")
	public String manage(String password, @CookieValue(value = "token", required = false) String token,
			HttpServletResponse response) throws IOException {
		if (token != null) {
			if (Mange.checkToken(token)) {
				System.out.println("通过cookie");
				// 这里返回控制页面
				response.sendRedirect("/signctrl");
			}
			if (Mange.checkDisToken(token)) {
				return "密码冷却中,请稍后重试";
			}
		}

		if (password != null) {
			if (password.equals(App.passwd)) {
				String newToken = Function.getRandomString(10);// 生成随机字符
				Mange.addToken(newToken);// 添加随机字符
				Cookie tokenCookie = new Cookie("token", newToken);
				response.addCookie(tokenCookie);// 向客户端设置cookie
				// 这里返回控制页面
				response.sendRedirect("/signctrl");
			} else {
				String newToken = Function.getRandomString(10);// 生成随机字符
				Mange.addDisToken(newToken);// 添加随机字符
				Cookie tokenCookie = new Cookie("token", newToken);
				response.addCookie(tokenCookie);// 向客户端设置cookie
				return "密码错误";
			}
		}

		return "";
	}

	@RequestMapping("/signcontrolstart") // 开始签到控制
	public String signcontrolstart(@CookieValue(value = "token", required = false) String token,
			HttpServletResponse response) {
		if (token == null || (!Mange.checkToken(token))) {
			return "identity";
		}
		
		String che = "";
		
		if(Mange.numChe()==0) {
			che = Function.getRandomString(5);
			Mange.addChe(che);
		}else {
			che = Mange.getLastChe();
		}

		byte[] codeQR = Function.createQrCode(App.ip + che);
        char[] imageString = Base64Coder.encode(codeQR);
        return new String(imageString);
	}
	
	@RequestMapping("/signcontrolstop") // 停止签到
	public String signcontrolstop(@CookieValue(value = "token", required = false) String token,
			HttpServletResponse response) {
		if (token == null || (!Mange.checkToken(token))) {
			return "identity";
		}
		Mange.clearChe();
		return "success";
	}

	@RequestMapping("/getvacatestu") // 获取请假学生
	public String getvacatestu(@CookieValue(value = "token", required = false) String token,
			HttpServletResponse response) {
//		if (token == null || (!Mange.checkToken(token))) {
//			return "identity";
//		}
		String[][] vacatestu = Student.VACATESTU;
		int vacatestu_num = Student.VACATESTU_NUM;
		StringBuffer result = new StringBuffer();

		if(vacatestu_num==0) {
			return "<center>当前没有请假学生<center>";
		}

		result.append("<tr><td>姓名</td><td>请假原因</td><td>请假时间</td><tb>请假条</tb></tr>");
		for (int i = 0; i < vacatestu_num; i++) {
			System.out.println("文件位置:"+vacatestu[i][2]);
			result.append("<tr><td>"+vacatestu[i][0] + "</td><td>" + vacatestu[i][3] + "</td><td>" + vacatestu[i][1].replaceAll("-",":").replaceAll("~","-")+"</td></tr>");
		}

		return result.toString();
	}

}
