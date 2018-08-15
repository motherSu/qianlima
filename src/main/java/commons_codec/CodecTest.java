/**
 * @Title555: 
*/

package commons_codec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.Sha2Crypt;
import org.apache.commons.codec.net.URLCodec;
import org.junit.Test;

/**
 * @Title: 
 * @Description:
 * @author: 苏腾
 * @date: 2018年3月9日 上午9:21:24
*/
public class CodecTest {
	@Test
   public void testBase64()
   {
      System.out.println("==============Base64================");
      byte[] data = "jianggujin".getBytes();
      Base64 base64 = new Base64();
      String encode = base64.encodeAsString(data);
      System.out.println(encode);
      System.out.println(new String(base64.decode(encode)));
   }

   @Test
   public void testMD5()
   {
      System.out.println("==============MD5================");
      String result = DigestUtils.md5Hex("jianggujin");
      System.out.println(result);
   }
   
   @Test
   public void testSHA()
   {
      System.out.println("==============SHA================");
      byte[] data = "jianggujin".getBytes();
      String shaStr = Sha2Crypt.sha256Crypt(data);
      //String result = DigestUtils.md5Hex("jianggujin");
      System.out.println(shaStr);
   }

   @Test
   public void testURLCodec() throws Exception
   {
      System.out.println("==============URLCodec================");
      URLCodec codec = new URLCodec();
      String data = "蒋固金";
      String encode = codec.encode(data, "UTF-8");
      System.out.println(encode);
      System.out.println(codec.decode(encode, "UTF-8"));
   }
}
