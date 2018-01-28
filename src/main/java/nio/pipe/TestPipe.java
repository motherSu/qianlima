package nio.pipe;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;
import java.nio.channels.Pipe.SinkChannel;
import java.nio.channels.Pipe.SourceChannel;
import java.util.Date;

import org.junit.Test;
/*
 * NIO2中引入的几个实用类

	随着 JDK 7 的发布， Java对NIO进行了极大的扩展，增强了对文件处理和文件系统特性的支持，以至于我们称他们为 NIO.2。因为 NIO 提供的一些功能， NIO已经成为文件处理中越来越重要的部分。
	Path 与 Paths
	
	java.nio.file.Path 接口代表一个平台无关的平台路径，描述了目录结构中文件的位置。
	Paths 提供的 get() 方法用来获取 Path 对象
	
	     Path get(String first, String … more) : 用于将多个字符串串连成路径。
	Path 常用方法：
	
	boolean endsWith(String path) : 判断是否以 path 路径结束
	boolean startsWith(String path) : 判断是否以 path 路径开始
	boolean isAbsolute() : 判断是否是绝对路径
	Path getFileName() : 返回与调用 Path 对象关联的文件名
	Path getName(int idx) : 返回的指定索引位置 idx 的路径名称
	int getNameCount() : 返回Path 根目录后面元素的数量
	Path getParent() ：返回Path对象包含整个路径，不包含 Path 对象指定的文件路径
	Path getRoot() ：返回调用 Path 对象的根路径
	Path resolve(Path p) :将相对路径解析为绝对路径
	Path toAbsolutePath() : 作为绝对路径返回调用 Path 对象
	String toString() ： 返回调用 Path 对象的字符串表示形式
	
	 
	Files 类
	
	java.nio.file.Files 用于操作文件或目录的工具类。
	Files常用方法：
	
	Path copy(Path src, Path dest, CopyOption … how) : 文件的复制
	Path createDirectory(Path path, FileAttribute<?> … attr) : 创建一个目录
	Path createFile(Path path, FileAttribute<?> … arr) : 创建一个文件
	void delete(Path path) : 删除一个文件
	Path move(Path src, Path dest, CopyOption…how) : 将 src 移动到 dest 位置
	long size(Path path) : 返回 path 指定文件的大小
	
	 
	Files 类
	Files常用方法：用于判断
	
	boolean exists(Path path, LinkOption … opts) : 判断文件是否存在
	boolean isDirectory(Path path, LinkOption … opts) : 判断是否是目录
	boolean isExecutable(Path path) : 判断是否是可执行文件
	boolean isHidden(Path path) : 判断是否是隐藏文件
	boolean isReadable(Path path) : 判断文件是否可读
	boolean isWritable(Path path) : 判断文件是否可写
	boolean notExists(Path path, LinkOption … opts) : 判断文件是否不存在
	public static <A extends BasicFileAttributes> A readAttributes(Path path,Class<A> type,LinkOption...options) : 获取与 path 指定的文件相关联的属性。
	Files常用方法： 用于操作内容
	
	eekableByteChannel newByteChannel(Path path, OpenOption…how) : 获取与指定文件的连接，how 指定打开方式。
	DirectoryStream newDirectoryStream(Path path) : 打开 path 指定的目录
	InputStream newInputStream(Path path, OpenOption…how):获取 InputStream 对象
	OutputStream newOutputStream(Path path, OpenOption…how) : 获取 OutputStream 对象
 */
public class TestPipe {

	@Test
	public void test1() throws IOException{
		//1、获取管道
		Pipe pipe = Pipe.open();
		
		//2、向管道写数据，将缓冲区的数据写入管道
		SinkChannel sinkChannel = pipe.sink();
		
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		buffer.put(new Date().toString().getBytes());
		
		buffer.flip();
		sinkChannel.write(buffer);
		buffer.clear();
		
		//3、读取缓冲区的数据（可以是另一个线程）
		SourceChannel sourceChannel = pipe.source();
		ByteBuffer dst = ByteBuffer.allocate(1024);
		sourceChannel.read(dst);
		
		dst.flip();
		System.out.println(new String(dst.array()));
		dst.clear();
		
		sinkChannel.close();
		sourceChannel.close();
		
	}
	
	
}
