package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

import org.junit.Test;

public class TestNonBlockingNIO {

	//客户端
		@Test
		public void client() throws IOException{
			//1、获取通道（open这种方法是jdk1.7之后才引入的）
			SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8888));
			//切换非阻塞模式
			socketChannel.configureBlocking(false);
			
			//2、分配指定大小的缓冲区
			ByteBuffer buf=ByteBuffer.allocate(1024);
			
			//输入信息
			Scanner scanner = new Scanner(System.in);
			if(scanner.hasNext()){
				String next = scanner.next();
				buf.put((new Date().toString()+"\n"+next).getBytes());
				buf.flip();
				socketChannel.write(buf);
				buf.clear();
			}
			
			scanner.close();
			
			//3、发送数据到服务端
//			buf.put(new Date().toString().getBytes());
//			buf.flip();
//			socketChannel.write(buf);
//			buf.clear();
			
//			FileChannel inFileChannel=FileChannel.open(Paths.get("H:/图片/tara/Ji-Yeon-desktopsky-21439.jpg"), StandardOpenOption.READ);
//			while(inFileChannel.read(buf)!=-1){
//				buf.flip();
//				socketChannel.write(buf);
//				buf.clear();
//			}
			
//			//在阻塞IO下，如果关闭socketChannel，那么服务端不知道客户端是否已经把所有数据发完，所以会一直阻塞。
//			socketChannel.shutdownOutput();
//			
//			//接收服务端反馈
//			int len = 0;
//			while((len = socketChannel.read(buf)) !=-1){
//				buf.flip();
//				System.out.println(new String(buf.array(),0,len));
//			}
//			
//			//4、关闭通道
//			inFileChannel.close();
			socketChannel.close();
		}
		
		//服务端
		@Test
		public void server() throws IOException{
			//1、获取端口
			ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		
			serverSocketChannel.configureBlocking(false);
			
			//2、绑定连接
			serverSocketChannel.bind(new InetSocketAddress(8888));
			//获取选择器
			Selector selector = Selector.open();
			//将通道注册到选择器上，先监控客户端的接收状态
			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
			
			while(selector.select() > 0){
				
				Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
				
				while(iterator.hasNext()){
					SelectionKey selectionKey = iterator.next();
					if(selectionKey.isAcceptable()){
						SocketChannel socketChannel = serverSocketChannel.accept();
						socketChannel.configureBlocking(false);
						socketChannel.register(selector, SelectionKey.OP_READ);
					}else if(selectionKey.isReadable()){
						SocketChannel socketChannel = (SocketChannel)selectionKey.channel();
						
						ByteBuffer buf = ByteBuffer.allocate(1024);
						
						int len = 0;
						while((len=socketChannel.read(buf))>0){
							buf.flip();
							System.out.println(new String(buf.array(),0,len));
							buf.clear();
						}
						
					}
					
					iterator.remove();
				}
			}
			
			
//			//3、获取客户端连接的通道
//			SocketChannel socketChannel = serverSocketChannel.accept();
//			
//			//4、接收客户端的数据，保存到本地。（提到本地，就要弄个FileChannel）
//			FileChannel outFileChannel = FileChannel.open(Paths.get("H:/图片/Ji-Yeon-desktopsky-21439.jpg"), StandardOpenOption.WRITE,StandardOpenOption.CREATE);
//			ByteBuffer buf=ByteBuffer.allocate(1024);
//			while(socketChannel.read(buf)!=-1){
//				buf.flip();
//				outFileChannel.write(buf);
//				buf.clear();
//			}
//				
			//发送反馈给客户端
//			buf.put("服务端接收数据成功！".getBytes());
//			buf.flip();
//			socketChannel.write(buf);
//			
//			socketChannel.close();
//			outFileChannel.close();
//			serverSocketChannel.close();
			
		}
}
