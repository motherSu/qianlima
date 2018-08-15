/**
 * @Title555: 
*/

package comons_lang;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringEscapeUtils;

/**
 * @Title: 
 * @Description:
 * @author: 苏腾
 * @date: 2018年3月9日 下午4:13:43
*/
public class RegTest {

	public static void main(String[] args) {
		String content = "<agile:social_infogoods id=\"a1\" goodsId=\"4602\" />&lt;agile:social_infogoods id=&quot;a1&quot; goodsId=&quot;4602&quot; /&gt;&lt;agile:social_infogoods id=&quot;a2&quot; goodsId=&quot;4603&quot; /&gt;";
		Pattern p = Pattern.compile("&lt;agile:(.*?)/&gt;|&gt;</a>|&gt;");
		Matcher m = p.matcher(content);
		//System.out.println(m.matches());
		//System.out.println(m.find());
		while (m.find()) {  
			String s = m.group();
            System.out.println(s);  
            //s = StringEscapeUtils.unescapeHtml(s);
            
            content = content.replace(s, StringEscapeUtils.unescapeHtml(s));
            System.out.println(content);
        } 
	}
}
