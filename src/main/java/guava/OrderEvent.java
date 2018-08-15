/**
 * @Title555: 
*/

package guava;

/**
 * @Title: 
 * @Description:
 * @author: 苏腾
 * @date: 2018年3月8日 下午3:56:14
*/
//Guava 发布-订阅模式中传递的事件,是一个普通的POJO类
public class OrderEvent {  //事件
 private String message;

 public OrderEvent(String message) {
     this.message = message;
 }

 public String getMessage() {
     return message;
 }
}
