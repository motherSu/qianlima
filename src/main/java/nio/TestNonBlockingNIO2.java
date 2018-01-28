package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

import org.junit.Test;

public class TestNonBlockingNIO2 {

	@Test
	public void send() throws IOException{
		DatagramChannel datagramChannel = DatagramChannel.open();
		datagramChannel.configureBlocking(false);
		
		ByteBuffer buf = ByteBuffer.allocate(1024);
		
		Scanner scanner = new Scanner(System.in);
		while(scanner.hasNext()){
			String inputStr=scanner.next();
			buf.put((new Date().toString()+":\n"+inputStr).getBytes());
			buf.flip();
			datagramChannel.send(buf, new InetSocketAddress("127.0.0.1", 8888));
			buf.clear();
		}
		
		scanner.close();
		datagramChannel.close();
	}
	
	@Test
	public void receive() throws IOException{
		DatagramChannel datagramChannel = DatagramChannel.open();
		datagramChannel.configureBlocking(false);

		datagramChannel.bind(new InetSocketAddress(8888));
		
		Selector selector = Selector.open();
		datagramChannel.register(selector, SelectionKey.OP_READ);
		
		while(selector.select()>0){
			Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
			
			while(iterator.hasNext()){
				SelectionKey selectionKey=iterator.next();
				if(selectionKey.isReadable()){
					ByteBuffer buf = ByteBuffer.allocate(1024);
					datagramChannel.receive(buf);
					buf.flip();
					System.out.println(new String(buf.array(), 0, buf.limit()));
					buf.clear();
				}
			}
			iterator.remove();
			
		}
		
		
	}
}
