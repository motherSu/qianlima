package concurrent;

import java.util.concurrent.CountDownLatch;
/*
 * NetworkHealthChecker.java：这个类继承了BaseHealthChecker，实现了verifyService()方法。
 * DatabaseHealthChecker.java和CacheHealthChecker.java除了服务名和休眠时间外，与NetworkHealthChecker.java是一样的。
 */
public class NetworkHealthChecker extends BaseHealthChecker {

	/*public NetworkHealthChecker(String serviceName, CountDownLatch latch) {
		super(serviceName, latch);
		// TODO Auto-generated constructor stub
	}*/
	
	public NetworkHealthChecker (CountDownLatch latch)  {
        super("Network Service", latch);
    }

	@Override
	public void verifyService() {
		System.out.println("Checking " + this.getServiceName());
        try
        {
            Thread.sleep(7000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        System.out.println(this.getServiceName() + " is UP");

	}

}
