package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.junit.Test;

/**
 * 
 * 一、使用NIO完成网络通信的三个核心：
 * 1. 通道（Channel）：负责连接
 * 	java.nio.channels.Channel
 * 		|-- SelectableChannel
 * 			|-- SocketChannel
 * 			|-- ServerSocketChannel
 * 			|-- DatagramChannel
 * 
 * 			|-- Pipe.SinkChannel
 * 			|-- Pipe.SourceChannel
 * （注意：FileChannel不能使用非阻塞模式！！！选择其主要监控网络Channel）
 * 
 * 2. 缓冲区（Buffer）：负责数据的存取
 * 3. 选择器（Selector）：是SelectableChannel的多路复用器。用于监控SelectableChannel的IO状况
 * 
 * @author happyBKs
 *
 */
public class TestBlockingNIO {

	//客户端
	@Test
	public void client() throws IOException{
		//1、获取通道（open这种方法是jdk1.7之后才引入的）
		SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8888));
		
		//2、分配指定大小的缓冲区
		ByteBuffer buf=ByteBuffer.allocate(1024);
		
		//3、从本地读取文件，并发送到服务端
		FileChannel inFileChannel=FileChannel.open(Paths.get("H:/图片/tara/Ji-Yeon-desktopsky-21439.jpg"), StandardOpenOption.READ);
		while(inFileChannel.read(buf)!=-1){
			buf.flip();
			socketChannel.write(buf);
			buf.clear();
		}
		
		//在阻塞IO下，如果关闭socketChannel，那么服务端不知道客户端是否已经把所有数据发完，所以会一直阻塞。
		socketChannel.shutdownOutput();
		
		//接收服务端反馈
		int len = 0;
		while((len = socketChannel.read(buf)) !=-1){
			buf.flip();
			System.out.println(new String(buf.array(),0,len));
		}
		
		//4、关闭通道
		inFileChannel.close();
		socketChannel.close();
	}
	
	//服务端
	@Test
	public void server() throws IOException{
		//1、获取端口
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
	
		//2、绑定连接
		serverSocketChannel.bind(new InetSocketAddress(8888));
		
		//3、获取客户端连接的通道
		SocketChannel socketChannel = serverSocketChannel.accept();
		
		//4、接收客户端的数据，保存到本地。（提到本地，就要弄个FileChannel）
		FileChannel outFileChannel = FileChannel.open(Paths.get("H:/图片/Ji-Yeon-desktopsky-21439.jpg"), StandardOpenOption.WRITE,StandardOpenOption.CREATE);
		ByteBuffer buf=ByteBuffer.allocate(1024);
		while(socketChannel.read(buf)!=-1){
			buf.flip();
			outFileChannel.write(buf);
			buf.clear();
		}
			
		//发送反馈给客户端
		buf.put("服务端接收数据成功！".getBytes());
		buf.flip();
		socketChannel.write(buf);
		
		socketChannel.close();
		outFileChannel.close();
		serverSocketChannel.close();
		
	}
}
