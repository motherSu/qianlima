/**
 * @Title555: 
*/

package guava;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.Subscribe;

/**
 * @Title: 
 * @Description:
 * @author: 苏腾
 * @date: 2018年3月8日 下午3:59:37
*/
public class DeadEventListener {
	boolean isDelivered=true;

   @Subscribe
   public void listen(DeadEvent event){
       isDelivered=false;
       System.out.println(event.getSource().getClass()+"  "+event.getEvent()); //source通常是EventBus
   }

   public boolean isDelivered() {
       return isDelivered;
   }
}
