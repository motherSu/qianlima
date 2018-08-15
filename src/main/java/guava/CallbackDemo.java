/**
 * @Title555: 
*/

package guava;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

/**
 * @Title: 
 * @Description:
 * @author: 苏腾
 * @date: 2018年3月8日 下午5:15:23
*/
public class CallbackDemo {
	public static void main(String[] args) {
       Cache<String,String> cache= CacheBuilder.newBuilder()
               .maximumSize(100)
               .expireAfterAccess(1, TimeUnit.SECONDS)
               .build();
       try {
    	   cache.put("java", "");
           String result=cache.get("java", () -> "hello java");
           System.out.println(result);
       } catch (ExecutionException e) {
           e.printStackTrace();
       }
   }
}
