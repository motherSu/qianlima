package nio.buffer;

import java.nio.ByteBuffer;

import org.junit.Test;

/**
 * 一、缓冲区（Buffer）：在java NIO中负责数据的存取。缓冲区就是数据。用于存储不同数据类型的数据
 * 
 * 跟局数据类型的不同（boolean除外，其他7种基本类型全有），提供相应类型的缓冲区：
 * ByteBuffer
 * CharBuffer
 * ShortBuffer
 * IntBuffer
 * LongBuffer
 * FloatBuffer
 * DoubleBuffer
 *  
 * 上述缓冲区的管理方式几乎一致，通过allocate()获取缓冲区
 * 最常用的是ByteBuffer，因为无论磁盘还是网络传输的都是Byte。
 * 
 * 有了缓冲区，接下来要往缓冲区存取数据
 * 
 * 二、缓冲区存取数据的两个核心方法：
 * put()：存入数据到缓冲区中
 * get()：获取缓冲区中的数据
 * 
 * 要想对缓冲区的数据进行正确的存取的话，必须姚先了解几个缓冲区的核心属性
 * 
 * 
 * 三、缓冲区中的四个核心属性：
 * capacity：容量，表示缓冲区中最大存储数据的容量。一旦声明不能改变。（因为缓冲区就是数组，数组大小不可改变）
 * limit：界限，表示缓冲区中可以操作数据的大小。（limit后面的数据不能进行读写）
 * position：位置，表示缓冲区正在操作数据的位置。
 * 
 * position <= limit <= capacity
 * 
 * 
 * mark：标记，表示记录当前position的位置。可以用过reset()恢复到mark的位置。
 * 
 * 0 <= mark <= position <= limit <= capacity
 * 
 * mark初始化为-1，表示没有标记过
 * 
 * 
 * @author HappyBks
 *
 */
public class TestBuffer {

	
	@Test
	public void test1(){
		//1、分配一个是指定大小的缓冲区
		ByteBuffer buf=ByteBuffer.allocate(1024);//指定缓冲区大小capacity为1024
		printBufferStatus(buf, "allocate()");
		//position:0
		//limit:1024
		//capacity:1024
		
		//2、利用put()方法存入数据到缓冲区中
		final String dataStr="abcde";
		buf.put(dataStr.getBytes());
		printBufferStatus(buf, "put()");
		//position:5
		//limit:1024
		//capacity:1024
		
		
		//3、切换读取数据模式
		buf.flip();
		printBufferStatus(buf, "flip()");
		//position:0
		//limit:5
		//capacity:1024
		
		//4、利用get()读取缓冲区中的数据
		byte[] dst=new byte[buf.limit()];
		buf.get(dst);
		System.out.println(new String(dst, 0 , dst.length));
		printBufferStatus(buf, "get()");
		//position:5
		//limit:5
		//capacity:1024
		
		
		//5、调用rewind()，恢复读模式到读之前的初始状态，用于可重复再读一次
		buf.rewind();
		printBufferStatus(buf, "rewind()");
		//position:0
		//limit:5
		//capacity:1024
		
		//6、clear()清空缓冲区，回到allocate()申请缓冲区的最初状态。
		//但是请注意，这个恢复到最初状态，指定是buffer的几个核心属性恢复到最初状态，
		//里面的数据依然还在，只不过这些数据处于“被遗忘”状态。
		//所谓被遗忘状态，是指buffer的几个指针都恢复到了初始位置，里面有几个数据，从哪读到哪，已经都不知道了。
		buf.clear();
		printBufferStatus(buf, "clear()");
		//position:0
		//limit:1024
		//capacity:1024
		
		//尝试读取被clear()清空之后的缓冲区，数据依然在。（因为clear之后，我们无法之后原来有几个数据，所以读一个）
		System.out.println("clear()之后试着get()一个byte: "+(char)buf.get());
		
	}

	private void printBufferStatus(ByteBuffer buf, String info) {
		System.out.println("---------"+info+"----------");
		System.out.println("position: "+buf.position());
		System.out.println("limit: "+buf.limit());
		System.out.println("capacity: "+buf.capacity());
		System.out.println();
	}
	
	
	@Test
	public void test2(){
		final String dataStr="abcde";
		ByteBuffer buf=ByteBuffer.allocate(1024);
		buf.put(dataStr.getBytes());
		buf.flip();
		byte[] dst=new byte[buf.limit()];
		buf.get(dst, 0, 2);
		System.out.println("get(dst, 0, 2): "+new String(dst, 0, 2));
		//ab
		System.out.println("get(dst, 0, 2) position: "+buf.position());
		//2
		
		//mark()：标记
		buf.mark();
		System.out.println("mark() position: "+buf.position());
		//2

		
		buf.get(dst, 2, 2);
		System.out.println("get(dst, 2, 2): "+new String(dst, 2, 2));
		//cd
		System.out.println("get(dst, 2, 2) position: "+buf.position());
		//4
		
		//reset()：恢复到mark的位置
		buf.reset();
		System.out.println("reset() position: "+buf.position());
		//2

		//判断缓冲区中是否还有剩余数据
		if(buf.hasRemaining()){
			//获取缓冲区中农剩余的可以操作的数据量
			System.out.println("buf.remaining(): "+buf.remaining());
			//3
		}
		
	}
	
	@Test
	public void test3(){
		ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
		if(buffer.isDirect()){
			System.out.println("buffer是直接缓冲区");
		}else{
			System.out.println("buffer是非直接缓冲区");
		}
		
	}
}