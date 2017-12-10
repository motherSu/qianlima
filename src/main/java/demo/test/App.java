package demo.test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Hello world!
 *
 */
public class App 
{
	private static final String reg_url = "";
	private static final String login_url = "";
	private static final String go_url = "";
	private static final String email = "15015497782@163.com";
	private static final String password = "suteng224853";
	
	//进入邮箱点击注册激活链接
	/**网易邮箱*/
	private static final String login_url_163 = "http://mail.163.com/";//登录地址
	private static final String login_frame_163 = "x-URS-iframe";//登录frameid
	private static final String login_normal_id_163 = "lbNormal";//邮箱帐号登录 
	private static final String email_input_name_163 = "email";//邮箱输入框name
	private static final String password_input_name_163 = "password";//密码输入框name
	private static final String do_login_click_163 = "dologin";//点击登录
	private static final String inbox_id_163 = "_mail_component_76_76";//收件箱
	
	/**qq邮箱*/
	private static final String email_url_qq = "https://mail.qq.com/";
	private static final String login_frame_qq = "login_frame";
	private static final String email_input_name_qq = "u";
	private static final String password_input_name_qq = "p";
	/**sina邮箱*/
	private static final String login_url_sina = "http://mail.sina.com.cn/";
//	private static final String login
    public static void main( String[] args ) throws InterruptedException
    {
//        System.out.println( "Hello World!" );
    	System.setProperty("webdriver.chrome.driver", "C:\\Users\\suteng\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
//    	driver.get("https://www.oschina.net/home/login?goto_page=https%3A%2F%2Fwww.oschina.net%2F");
//    	 WebElement username = driver.findElement(By.xpath("//*[@id=\"account_login\"]/form/div/div[1]/div[1]/input"));
//        username.sendKeys("15015497782@163.com");
//        WebElement passwd = driver.findElement(By.xpath("//*[@id=\"userPassword\"]"));
//        passwd.sendKeys("suteng224853");
//        driver.findElement(By.xpath("//*[@id=\"account_login\"]/form/div/div[5]/button")).click();
        driver.get(login_url_163);
        Thread.sleep(3000);
        driver.findElement(By.id(login_normal_id_163)).click();
        driver.switchTo().frame(login_frame_163); 
        WebElement username1 = driver.findElement(By.name(email_input_name_163));
        username1.sendKeys(email);
        WebElement passwd1 = driver.findElement(By.name(password_input_name_163));
        passwd1.sendKeys(password);
        driver.findElement(By.id(do_login_click_163)).click();
        Thread.sleep(3000);
        driver.findElement(By.id(inbox_id_163)).click();
    }
}
