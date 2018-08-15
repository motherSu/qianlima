/**
 * @Title555: 
*/

package httpclient;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang.StringUtils;

/**
 * @Title: 
 * @Description:
 * @author: 苏腾
 * @date: 2018年3月12日 上午10:47:22
*/
public class HttpClientUtil {

    public static String get(String url, Map<String, String> queryParams) {  
        if (StringUtils.isBlank(url)) {  
            return null;  
        }  
        HttpClient httpclient = new HttpClient();  
        GetMethod getMethod = new GetMethod(url);  
        //组装参数  
        if (queryParams != null && !queryParams.isEmpty()) {  
            NameValuePair[] pairs = new NameValuePair[queryParams.size()];  
            int i = 0;  
            for (Map.Entry<String, String> entry : queryParams.entrySet()) {  
                pairs[i] = new NameValuePair(entry.getKey(), entry.getValue());  
                i++;  
            }  
            getMethod.setQueryString(pairs);  
        }  
  
        String resultString = null;  
        try {  
            int statusCode = httpclient.executeMethod(getMethod);  
            if (statusCode == HttpStatus.SC_OK) {  
                //获取请求响应内容  
                resultString = getMethod.getResponseBodyAsString();  
            } else {  
                throw new RuntimeException("网络访问异常,错误状态：" + statusCode);  
            }  
        } catch (HttpException e) {  
            throw new RuntimeException("网络访问异常", e);  
        } catch (IOException e) {  
            throw new RuntimeException("网络访问异常", e);  
        }  
        return resultString;  
    }  
}
