package qianlima.test2.example3;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
/**

找到一组数据中，求和最大值的组合

@author ko

@version [版本号, 2016年11月23日]
*/
public class MaxUtil {
	public static HashMap<String, Integer> getMaxGroup(int max, HashMap<Integer, Integer> datas) {
	    // 按照数据个数，找出从1-总数之间所有个数中，+起来不超过指定值的最大值
	    // 即1个数中的最大值，2个数中的最大值，依次类推，
	    // 最后比较这些最大值，找到其中最大的一个即为答案
	    int length = datas.size();
	    Integer[] keys = new Integer[length];
	    keys = datas.keySet().toArray(keys);

	    HashMap<String, Integer> resultTemp = new HashMap<String, Integer>();
	    // 计算每个数的排列组合
	    for (int i = 0; i < length; i++) {
	        // 计算当前个数的排列组合
	        List<Integer[]> result = GrouUtil.combine(keys, i + 1);
	        // 对每种组合求和
	        HashMap<String, Integer> dataTemp = new HashMap<String, Integer>();
	        for (Integer[] item : result) {
	            String line = "";
	            int count = 0;
	            for (int item1 : item) {
	                line += item1 + "-";
	                count += datas.get(item1);
	            }
	            dataTemp.put(line, count);
	        }
	        // 找到当前个数排列组合所有组合中最大值
	        // 将当前个数排列中的最大值保存
	        resultTemp.putAll(getMax(max, dataTemp));
	    }
	    // 计算所有排列组合中最大值
	    return getMax(max, resultTemp);
	}

	private static HashMap<String, Integer> getMax(int max, HashMap<String, Integer> dataTemp) {
	    HashMap<String, Integer> resultTemp = new HashMap<String, Integer>();
	    int maxTemp = 0;
	    String keyTemp = null;
	    for (Entry<String, Integer> entry : dataTemp.entrySet()) {
	        String appFieldDefId = entry.getKey();
	        Integer values = entry.getValue();
	        if (values > maxTemp && values <= max) {
	            maxTemp = values;
	            keyTemp = appFieldDefId;
	        }
	    }
	    resultTemp.put(keyTemp, maxTemp);
	    return resultTemp;
	}

	public static void main(String[] args) {
	    int max = 5000;
	    // 读取解析原始数据
	    List<String> list = FileManagerUtil.getContentFromSystemByLine("e:\\新建文本文档 (5).txt");
	    HashMap<Integer, Integer> datas = new HashMap<Integer, Integer>();
	    for (String item : list) {
	        item = item.trim();
	        String[] values = item.split(" ");
	        datas.put(Integer.parseInt(values[0]), Integer.parseInt(values[values.length - 1]));
	    }
	    //求得最
	}
}
