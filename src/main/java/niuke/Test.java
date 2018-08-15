/**
 * @Title555: 
*/

package niuke;

/**
 * @Title: 
 * @Description:
 * @author: 苏腾
 * @date: 2018年4月24日 下午3:58:43
*/
public class Test {
	void waitForSignal(){
		Object obj = new Object();
		synchronized (Thread.currentThread()) {
			obj.wait();
			obj.notify();
		}
	}
	
	public static void main(String[] args) {
		Test t = new Test();
		t.waitForSignal();
	}
	
}

