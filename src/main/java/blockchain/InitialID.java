/**
 * @Title555: 
*/

package blockchain;

import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @Title: 
 * @Description:
 * @author: 苏腾
 * @date: 2018年3月30日 下午3:04:25
*/
public class InitialID implements ServletContextListener{
	public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        servletContext.setAttribute("uuid", uuid);
    }

    public void contextDestroyed(ServletContextEvent sce) {
    }
}
