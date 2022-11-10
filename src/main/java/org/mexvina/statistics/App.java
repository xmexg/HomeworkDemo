package org.mexvina.statistics;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */
@SpringBootApplication

public class App 
{

	public static String version = "v0.1.1";
	public static String logo = "      ,\r\n"
			+ "  /\\^/`\\   该程序是软件工程4组作业\r\n"
			+ " | \\/   |  小组成员:徐光鑫、白宇轩、陈文博\t版本:"+version+"\r\n"
			+ " | |    |         张彬、陈文博、杨鹏飞\t\t日期:2022年10月30日\r\n"
			+ " \\ \\    /                                                     _ _\r\n"
			+ "  '\\\\//'                                                    _{ ' }_\r\n"
			+ "    ||                                                     { `.!.` }\r\n"
			+ "    ||                                                     ',_/Y\\_,'\r\n"
			+ "    ||  ,                                                    {_,_}\r\n"
			+ "|\\  ||  |\\                                                     |\r\n"
			+ "| | ||  | |                                                  (\\|  /)\r\n"
			+ "| | || / /                                                    \\| //\r\n"
			+ " \\ \\||/ /                                                      |//\r\n"
			+ "  `\\\\//`   \\   \\./    \\\\   \\./    \\\\   \\./    \\\\   \\./    \\ \\\\ |/ /\r\n"
			+ " ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^\r\n"
			+ "";


    static File directory = new File("");//设定为当前文件夹
    //获取当前路径
    public static String parentDir= directory.getAbsolutePath()+File.separator+"upload"+File.separator;
    public static String passwd = "ABC123demo";
    //获取当前服务器的ip地址
    public static String ip = "http://mexvina.top:9900/";
	
    public static void main( String[] args )
    {
    	System.out.println(logo);
    	new Student();
    	new Mange();
        SpringApplication.run(App.class,args);
        System.out.println("[#] 启动成功,请浏览器访问 http://127.0.0.1:8080 查看演示");
        System.out.println("[#] 当前版本只有请假功能");
        
    }
}
