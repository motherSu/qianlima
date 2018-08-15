/**
 * @Title555: 
*/

package guava;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * @Title: 
 * @Description:
 * @author: 苏腾
 * @date: 2018年3月8日 下午4:57:21
*/
public class TransformDemo {

	/*public static void main(String[] args) {
       Set<Long> times= Sets.newHashSet();
       times.add(91299990701L);
       times.add(9320001010L);
       times.add(9920170621L);
       Collection<String> timeStrCol= Collections2.transform(times, new Function<Long, String>() {
           @Nullable
           @Override
           public String apply(@Nullable Long input) {
               return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(input);
           }
       });
       System.out.println(timeStrCol);
   }*/
	
	public static void main(String[] args) {
       List<String> list= Lists.newArrayList("abcde","good","happiness");
       //确保容器中的字符串长度不超过5
       Function<String,String> f1=new Function<String, String>() {
           @Nullable
           @Override
           public String apply(@Nullable String input) {
               return input.length()>5?input.substring(0,5):input;
           }
       };
       //转成大写
       Function<String,String> f2=new Function<String, String>() {
           @Nullable
           @Override
           public String apply(@Nullable String input) {
               return input.toUpperCase();
           }
       };
       Function<String,String> function=Functions.compose(f1,f2);
       Collection<String> results=Collections2.transform(list,function);
       System.out.println(results);
   }
}
