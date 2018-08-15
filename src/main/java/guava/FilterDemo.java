/**
 * @Title555: 
*/

package guava;

import java.util.Collection;
import java.util.List;

import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

/**
 * @Title: 
 * @Description:
 * @author: 苏腾
 * @date: 2018年3月8日 下午4:55:12
*/
public class FilterDemo {
	public static void main(String[] args) {
       List<String> list= Lists.newArrayList("moon","dad","refer","son");
       Collection<String> palindromeList= Collections2.filter(list, input -> {
           return new StringBuilder(input).reverse().toString().equals(input); //找回文串
       });
       System.out.println(palindromeList);
   }
}
