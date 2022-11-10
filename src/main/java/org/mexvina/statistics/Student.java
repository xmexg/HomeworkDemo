package org.mexvina.statistics;

import java.io.*;

public class Student {
	public static String[] TOTALSTU;//总计
	public static String[] REALITYSTU;//实到
	public static String[][] VACATESTU;//请假;姓名,时间,保存的文件名,请假原由
	public static int VACATESTU_NUM;//请假人数
	
	Student(){
		char[] ch = {'\u6C34','\u5370','\u003A','\n','\u8FD9','\u662F','\u0020','\u0032','\u0030','\u0032','\u0031','\u7EA7','\u8F6F','\u4E00','\u0078','\u0067','\u0078','\u0020','\u0062','\u0079','\u0078','\u0020','\u0063','\u0077','\u0062','\u0020','\u006C','\u007A','\u0063','\u0020','\u0079','\u0070','\u0066','\u0020','\u007A','\u0062','\u0020','\u7684','\u8F6F','\u4EF6','\u5DE5','\u7A0B','\u4F5C','\u4E1A','\u0021','\n','\u65E5','\u671F','\u003A','\u0032','\u0030','\u0032','\u0032','\u5E74','\u0031','\u0030','\u6708','\u0031','\u0034','\u65E5'};
		if(App.logo == null)
			System.out.println(new String(ch));
		try {
			File file = new File("total.txt");
			if(!file.exists()) {
				file.createNewFile();
				String tip = new String(new char[]{'\u8BF7','\u5411','\u0074','\u006F','\u0074','\u0061','\u006C','\u002E','\u0074','\u0078','\u0074','\u6587','\u4EF6','\u4E2D','\u5199','\u5165','\u5168','\u4F53','\u4EBA','\u540D','\u002C','\u6BCF','\u884C','\u4E00','\u4E2A','\u4EBA','\u540D'});
				//打印日志
				System.out.println(tip);
				//写入日志
				FileWriter fw = new FileWriter(file);
				fw.write(tip);
				fw.close();

				System.exit(0);
			}
			BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
			String strLine = null , strTotal = "";
			while(null != (strLine = bufferedReader.readLine())){
				strTotal += strLine + ",";
			}
			TOTALSTU = strTotal.split(",");
			System.out.print("已录入的成员：");
			for(int i = 0; i < TOTALSTU.length; i++) {
				System.out.print(TOTALSTU[i] + " ");
			}
			if(TOTALSTU.length <= 0) {
				System.out.println("[!] 当前没有录入任何成员，请编辑total.txt");
			}
			System.out.println();
			VACATESTU = new String[TOTALSTU.length][4];
			bufferedReader.close();
		}catch(Exception e){
            e.printStackTrace();
        }
	}
	
	public static void addVACATESTU(String name, String time, String savename, String reason){
		VACATESTU[VACATESTU_NUM][0] = name;
		VACATESTU[VACATESTU_NUM][1] = time;
		VACATESTU[VACATESTU_NUM][2] = savename;//保存的文件名
		VACATESTU[VACATESTU_NUM][3] = reason;//请假原由
		VACATESTU_NUM++;
	}
	
	public static String[] delVACATESTU(String name) {//同时访问时可能会出错
		int delLoc = -1;
		
		//寻找位置
		for(int i = 0; i < VACATESTU_NUM; i++) {
			if(VACATESTU[i][0].equals(name)) {
				delLoc = i;
				break;
			}
		}
		
		if(delLoc == -1) {
			return new String[]{"false"};
		}
		
		//请假人数减一
		VACATESTU_NUM--;
		
		//把后面的内容前移
		for(int j = delLoc; j < VACATESTU_NUM; j++) {
			VACATESTU[j][0] = VACATESTU[j+1][0];
			VACATESTU[j][1] = VACATESTU[j+1][1];
			VACATESTU[j][2] = VACATESTU[j+1][2];
			VACATESTU[j][3] = VACATESTU[j+1][3];
		}
		
		//保存一下要删除的学生信息
		String[] info = new String[] {"true", VACATESTU[VACATESTU_NUM][0], VACATESTU[VACATESTU_NUM][1], VACATESTU[VACATESTU_NUM][2]};
		
		//删除最后一个节点的内容
		VACATESTU[VACATESTU_NUM][0] = "";
		VACATESTU[VACATESTU_NUM][1] = "";
		VACATESTU[VACATESTU_NUM][2] = "";
		VACATESTU[VACATESTU_NUM][3] = "";
		
		return info;
	}

}
