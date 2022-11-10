package org.mexvina.statistics;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestThymeleafController  {
	@RequestMapping("")//主页
	public String index() {
		return "index";
	}
	
	@RequestMapping("index")//主页
	public String index1() {
		return "index";
	}
	
	@RequestMapping("vacate")//主页
	public String vacate() {
		return "vacate";
	}
	
	@RequestMapping("sign")//开始签到
	public String sign() {
		return "sign";
	}
	
	@RequestMapping("detail")//签到详情
	public String detail() {
		return "detail";
	}
	
	@RequestMapping("signctrl")//开始签到页面
	public String signcontrol() {
		return "signctrl";
	}
	
	@RequestMapping("signstu")//学生签到
	public String signstu() {
		return "signstu";
	}
	
	@RequestMapping("htmltest")//页面测试
	public String htmltest() {
		return "htmltest";
	}

	@RequestMapping("/vacateinfo")//请假信息
	public String vacateinfo() {return "vacateinfo";}
	
}
