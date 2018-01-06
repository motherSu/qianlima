package qianlima.test2;

import java.io.FileWriter;
import java.io.IOException;

public class Example2 {
	  static int good[] = {509, 838, 924, 650, 604, 793, 564, 651, 697, 649, 747, 787, 701, 605, 644};
	  static int max = 5000; //老王的要求
	  
	    public static void main(String[] args) {
	        //01问题
	        int maxWeight = 0;
	        String maxMethod = "";

	        String filePath = "F:\\1.txt";
	        FileWriter writer = null;
	        try {
	        	writer = new FileWriter(filePath);
	        	for (int i = 0; i < 1 << good.length; i++) {
	        		String method = Integer.toBinaryString(i);
	        		System.out.println("i:"+i+",method:"+method.toString());
	        		writer.write("i:"+i+",method:"+method.toString()+"\r\n");
	        		while (method.length() < good.length) {
	        			method = "0" + method;
	        		}
	        		int sum = 0;
	        		for (int j = 0; j < method.length(); j++) {
	        			if (method.charAt(j) == '1') {
	        				sum += good[j];
	        			}
	        			if (sum > max) {
	        				break;
	        			}
	        		}
	        		if (sum <= max && sum > maxWeight) {
	        			maxWeight = sum;
	        			maxMethod = method;
	        		}
	        	}
	        	//判断好01问题后就开始对于数组中元素的提取和计算
	        	String result = ""; //组成的元素有哪些
	        	int resultWeight = 0;//组成元素的总值
	        	for (int j = 0; j < maxMethod.length(); j++) {
	        		if (maxMethod.charAt(j) == '1') {
	        			result += (j + 1) + "-";
	        			resultWeight += good[j];
	        		}
	        	}
	        	if (result.length() > 0) {
	        		result = result.substring(0, result.length() - 1);
	        	}
	        	System.out.println(result);
	        	System.out.println(resultWeight);
	        } catch (IOException e) {
	        	// TODO Auto-generated catch block
	        	e.printStackTrace();
	        } finally {
	        	try {
	        		writer.flush();
	        		writer.write("first_test");
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	    }
}
