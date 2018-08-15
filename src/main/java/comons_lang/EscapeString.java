/**
 * @Title555: 
*/

package comons_lang;

import org.apache.commons.lang.StringEscapeUtils;

/**
 * @Title: 
 * @Description:
 * @author: 苏腾
 * @date: 2018年3月9日 下午12:45:19
*/
public class EscapeString {
	public static void main(String[] args) throws Exception {  

        String str = "APEC召开时不让点柴火做饭";  

        System.out.println("用escapeJava方法转义之后的字符串为:"+StringEscapeUtils.escapeJava(str));  

        System.out.println("用unescapeJava方法反转义之后的字符串为:"+StringEscapeUtils.unescapeJava(StringEscapeUtils.escapeJava(str)));  

          

        System.out.println("用escapeHtml方法转义之后的字符串为:"+StringEscapeUtils.escapeHtml(str));  

        System.out.println("用unescapeHtml方法反转义之后的字符串为:"+StringEscapeUtils.unescapeHtml(StringEscapeUtils.escapeHtml(str)));  

          

        System.out.println("用escapeXml方法转义之后的字符串为:"+StringEscapeUtils.escapeXml(str));  

        System.out.println("用unescapeXml方法反转义之后的字符串为:"+StringEscapeUtils.unescapeXml(StringEscapeUtils.escapeXml(str)));  

          

        System.out.println("用escapeJavaScript方法转义之后的字符串为:"+StringEscapeUtils.escapeJavaScript(str));  

        System.out.println("用unescapeJavaScript方法反转义之后的字符串为:"+StringEscapeUtils.unescapeJavaScript(StringEscapeUtils.escapeJavaScript(str)));  

        /**输出结果如下： 

     用escapeJava方法转义之后的字符串为:APEC\u53EC\u5F00\u65F6\u4E0D\u8BA9\u70B9\u67F4\u706B\u505A\u996D

用unescapeJava方法反转义之后的字符串为:APEC召开时不让点柴火做饭

用escapeHtml方法转义之后的字符串为:APEC&#21484;&#24320;&#26102;&#19981;&#35753;&#28857;&#26612;&#28779;&#20570;&#39277;

用unescapeHtml方法反转义之后的字符串为:APEC召开时不让点柴火做饭

用escapeXml方法转义之后的字符串为:APEC&#21484;&#24320;&#26102;&#19981;&#35753;&#28857;&#26612;&#28779;&#20570;&#39277;

用unescapeXml方法反转义之后的字符串为:APEC召开时不让点柴火做饭

用escapeJavaScript方法转义之后的字符串为:APEC\u53EC\u5F00\u65F6\u4E0D\u8BA9\u70B9\u67F4\u706B\u505A\u996D

用unescapeJavaScript方法反转义之后的字符串为:APEC召开时不让点柴火做饭*/

    }  
}
