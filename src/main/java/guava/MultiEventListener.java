/**
 * @Title555: 
*/

package guava;

import com.google.common.eventbus.Subscribe;

/**
 * @Title: 
 * @Description:
 * @author: 苏腾
 * @date: 2018年3月8日 下午3:57:11
*/
public class MultiEventListener {
	@Subscribe
   public void listen(OrderEvent event){
       System.out.println("receive msg: "+event.getMessage());
   }

   @Subscribe
   public void listen(String message){
       System.out.println("receive msg: "+message);
   }
}
