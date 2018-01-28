package nio.channel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map.Entry;
import java.util.SortedMap;

import org.junit.Test;

/**
 * 
 * 一、通道（Channel）：用于源节点与目标节点的连接。
 *  在Java NIO中负责缓冲区中数据的传输。
 *  Channel本身不存储数据，因此需要配合缓冲区进行传输（就像铁路必须配合火车才能完成乘客的运输）
 * 
 * 二、通道的主要实现类 
 * java.nio.channels.Channel接口： 
 * 		|-- FileChannel 用于本地文件数据传输 
 * 		|-- SocketChannel 用于网络，TCP 
 * 		|-- ServerSocketChannel 用于网络，TCP 
 * 		|-- DatagramChannel 用于网络，UDP
 * 
 * 三、获取通道（jdk1.7之后有三种方式）
 * 
 *  1、Java只针对支持通道的类提供了getChannel()方法 本地IO：
 * FileInputStream/FileOutputStream 
 * RandomAccessFile 
 * 
 * 网络IO： 
 * Socket 
 * ServerSocket
 * DatagramSocket 
 * 
 * 2、在JDK1.7中的NIO.2提供了针对各个通道提供了静态方法open()
 * 
 * 3、在JDK1.7中的NIO.2的Files工具类的newByteChannel()
 * 
 * 
 * 四、通道之间的数据传输
 * transferForm()
 * transferTo()
 * 
 * @author happyBKs
 *
 */
public class TestChannel {

	/**
	 * 1、利用通道完成文件复制（非直接缓冲区）
	 * @throws IOException
	 */
	@Test
	public void test1() throws IOException {
		long start=System.currentTimeMillis();
		FileInputStream fis = null;
		FileOutputStream fos = null;
		// 1、获取通道
		FileChannel inChannel = null;
		FileChannel outChannel = null;
		try {
			fis = new FileInputStream("D:/Test/NIO/1.jpg");
			fos = new FileOutputStream("D:/Test/NIO/2.jpg");
			
			inChannel = fis.getChannel();
			outChannel = fos.getChannel();

			// 2、分配指定大小的缓冲区
			ByteBuffer buf = ByteBuffer.allocate(1024);

			// 3、将通道中的数据存入缓冲区
			while (inChannel.read(buf) != -1) {
				buf.flip();
				// 4、将缓冲区中的数据写入通道中
				outChannel.write(buf);
				buf.clear();// 清空缓冲区
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (outChannel != null) {
				outChannel.close();
			}
			if (inChannel != null) {
				inChannel.close();
			}
			if (fos != null) {
				fos.close();
			}
			if (fis != null) {
				fis.close();
			}
			long end=System.currentTimeMillis();
			System.out.println(end-start);
		}

	}
	
	//2、使用直接缓冲区完成文件的复制（内存映射文件的方式）
	@Test
	public void test2() throws IOException{
		long start=System.currentTimeMillis();
		
		final Path path=Paths.get("D:/", "Test/","NIO/","1.jpg");
		final OpenOption options=StandardOpenOption.READ;
		FileChannel inChannel = FileChannel.open(path, options);
		//注意：StandardOpenOption的CREATE_NEW代表如果已存在则创建失败；CREATE代表如果已存在则覆盖
		//FileChannel outChannel = FileChannel.open(Paths.get("D:/Test/NIO/", "3.jpg"), StandardOpenOption.WRITE, StandardOpenOption.CREATE_NEW);
		FileChannel outChannel = FileChannel.open(Paths.get("D:/Test/NIO/", "3.jpg"), StandardOpenOption.WRITE,StandardOpenOption.READ, StandardOpenOption.CREATE_NEW);
		//注意：因为下面从通道得到的映射文件缓冲区的映射模式是读写模式，而这个outChannel只有写的打开选项，所以是不够，还要加入读配置。
		
		final MapMode mode=MapMode.READ_ONLY;
		final long position=0;
		final long size=inChannel.size();
		//这种利用通道通过映射文件建立直接缓冲区的方式和用缓冲区allocateDirect(int)的方式，两者的原理是一模一样的！
		//只是申请直接缓冲区的方式不同。
		MappedByteBuffer inMappedBuf = inChannel.map(mode, position, size);
		MappedByteBuffer outMappedBuf = outChannel.map(MapMode.READ_WRITE, 0, inChannel.size());
		
		//申请的空间都在物理内存中。
		//注意：申请直接缓冲区，仅仅适用于ByteBuffer缓冲区类型，其他缓冲区类型不支持。
		//与之前的通过流获得的通道不同，这种通过映射文件的方式是直接把数据通过映射文件放到物理内存中，还需要通道进行传输吗？是不是就不用了吧。我现在只需要直接向直接缓冲区中放就可以了，不需要通道。
		//所以与之前相比，获取通道的操作都省去了，直接操作缓冲区即可。
		
		//直接对缓冲区进行数据的读写操作
		byte[] dst=new byte[inMappedBuf.limit()];
		inMappedBuf.get(dst);
		outMappedBuf.put(dst);
		
		inChannel.close();
		outChannel.close();
		long end=System.currentTimeMillis();
		System.out.println(end-start);
	}
	
	/**
	 * 通道之间的数据传输（直接缓冲区）
	 * @throws IOException
	 */
	@Test
	public void test3() throws IOException{
		FileChannel inChannel = FileChannel.open(Paths.get("D:/Test/NIO/", "1.jpg"), StandardOpenOption.READ);
		FileChannel outChannel = FileChannel.open(Paths.get("D:/Test/NIO/", "4.jpg"), StandardOpenOption.WRITE,StandardOpenOption.READ, StandardOpenOption.CREATE_NEW);

		inChannel.transferTo(0, inChannel.size(), outChannel);
		//outChannel.transferFrom(inChannel, 0, inChannel.size());
		inChannel.close();
		outChannel.close();
	}
	
	/* 
	 * 五、分散（Scatter）与聚集（Gather）
	 * 分散读取（Scattering Reads）：将通道中的数据分散到多个缓冲区中
	 * 聚集写入（Gathering Writes）：将多个缓冲区中的数据聚集到通道中
	 * 
	 * 
	 * @author happyBKs
	 *
	 */
	//分散与聚集
	@Test
	public void test4() throws IOException{
		RandomAccessFile raf1=new RandomAccessFile("D:/Test/NIO/t1.txt", "rw");
		
		//1、获取通道
		FileChannel channel1 = raf1.getChannel();
		
		//2、分配指定大小的缓冲区
		ByteBuffer buf1=ByteBuffer.allocate(100);
		ByteBuffer buf2=ByteBuffer.allocate(1024);
		
		//3、分散读取
		ByteBuffer[] bufs={buf1,buf2};
		channel1.read(bufs);
		
		for(ByteBuffer byteBuffer:bufs){
			byteBuffer.flip();
		}
		
		System.out.println(new String(bufs[0].array(),0,bufs[0].limit()));
		System.out.println("-----------------------------------------");
		System.out.println(new String(bufs[1].array(),0,bufs[1].limit()));
		
		
		//4、聚集写入
		RandomAccessFile raf2=new RandomAccessFile("D:/Test/NIO/t2.txt", "rw");
		FileChannel channel2 = raf2.getChannel();
		
		channel2.write(bufs);
		
		channel2.close();
		raf2.close();
		channel1.close();
		raf1.close();

	}
	
	/*
	 *  六、字符集：CharSet
	 * 编码：字符串 -> 字节数组
	 * 解码：字节数组 -> 字符串
	 */
	@Test
	public void test5(){
		SortedMap<String, Charset> availableCharsets = Charset.availableCharsets();
		for(Entry<String, Charset> entry:availableCharsets.entrySet()){
			System.out.println(String.format("%s: %s", entry.getKey(), entry.getValue()));
		}
	}
	
	@Test
	public void test6() throws CharacterCodingException{
		Charset charset1 = Charset.forName("GBK");
		//获取编码器
		CharsetEncoder encoder = charset1.newEncoder();
		
		//获取解码器
		CharsetDecoder decoder = charset1.newDecoder();
		
		CharBuffer charBuffer = CharBuffer.allocate(1024);
		charBuffer.put("happyBKs的博客");
		
		
		//编码
		charBuffer.flip();//因为编码要读取charBuffer，所以要先切到度模式
		ByteBuffer byteBuffer=encoder.encode(charBuffer);
		
		//byteBuffer.limit()为14，英文字符一个1 byte，中文字符一个2 byte
		for(int i_byteBuffer=0;i_byteBuffer<byteBuffer.limit();i_byteBuffer++){
			System.out.println(byteBuffer.get());
			//104,97,112,112,121,66,75,115,-75,-60,-78,-87,-65,-51
		}
		
		//解码
		byteBuffer.flip();//因为解码要读取byteBuffer，所以要先切到度模式，不然下面一行什么也不输出
		CharBuffer charBufferDecoded=decoder.decode(byteBuffer);
		System.out.println(charBufferDecoded.toString());
		
		System.out.println("---------------------------------------------------");
		
		Charset utf8Chatset = Charset.forName("UTF-8");
		byteBuffer.flip();//byteBuffer刚才读过了，现在需要从头再读一遍，需要先调用flip()
		CharBuffer charBufferDecodedByUTF8=utf8Chatset.decode(byteBuffer);
		System.out.println(charBufferDecodedByUTF8.toString());
		
		
		System.out.println("---------------------------------------------------");
		Charset gbkChatset = Charset.forName("GBK");
		byteBuffer.flip();//byteBuffer刚才读过了，现在需要从头再读一遍，需要先调用flip()
		CharBuffer charBufferDecodedByGBK=gbkChatset.decode(byteBuffer);
		System.out.println(charBufferDecodedByGBK.toString());
		
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		byte[] bys = {104,97,112,112,121,66,75,115,-75,-60,-78,-87,-65,-51};
		System.out.println(new String(bys,"GBK"));//happyBKs的博客
	}
}
