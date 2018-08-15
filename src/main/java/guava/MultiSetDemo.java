/**
 * @Title555: 
*/

package guava;

import com.google.common.collect.LinkedHashMultiset;
import com.google.common.collect.Multiset;

/**
 * @Title: 
 * @Description:
 * @author: 苏腾
 * @date: 2018年3月8日 下午4:12:35
*/
public class MultiSetDemo {
	public static void main(String[] args) {
       Multiset<String> set=LinkedHashMultiset.create();
       set.add("a");
       set.add("a");
       set.add("a");
       set.add("a");
       set.setCount("a",5); //添加或删除指定元素使其在集合中的数量是count
       System.out.println(set.count("a")); //给定元素在Multiset中的计数
       System.out.println(set);
       System.out.println(set.size()); //所有元素计数的总和,包括重复元素
       System.out.println(set.elementSet().size()); //所有元素计数的总和,不包括重复元素
       set.clear(); //清空集合
       System.out.println(set);

   }
}
