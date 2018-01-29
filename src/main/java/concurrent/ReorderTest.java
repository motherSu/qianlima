package concurrent;
/*
 * 假设有两个线程A、B分别要执行write和read方法，A先进去执行、B随后执行，
 * 先抛开a、b线程可见性问题，假设a、b对线程立即可见。最后c值是多少？可能是1，可能是2，甚至可能是0。
 * 
 * 接下来具体分析一下为什么。
 *  站在B的视角看，它看不清a=1和b=1谁先执行，由于指令重排序，很可能b=1先执行，请看下表：
 *  时间	|	线程A		|	线程B
 *  t1	|	b=1		|
 *  t2	|			|	if(b==1)
 *  t3	|			|	c=a
 *  t4	|	a=1		|
 *  
 *  
 *  站在A线程的视角，B线程中read方法里的代码是否会重排序呢，虽然这个方法的两句话存在依赖关系，JMM支持不改变结果的指令重排，
 *  JMM无法预先判断是否有其他线程在修改a的值，所以可能会重排，并且处理器会用猜测执行来重排。请看下表：
 *  时间	|	线程A		|	线程B
 *  t1	|			|	temp=a
 *  t2	|	a=1		|
 *  t3	|	b=1		|
 *  t4	|			|	if(b==1)
 *  t5	|			|	c=temp
 *  
 *  指令重排序让线程看不清对方线程的执行顺序，也就是乱序的，那么会有哪些级别的指令重排序呢？
 *  有三种：编译器重排序、指令级重排序、内存级重排序。
 *  
 *  
 *  内存屏障
 *  
 *  指令重排序会导致多线程执行的无序，那么JMM会禁止特定类型的指令重排序，JMM通过内存屏障来禁止某些指令重排序，那么有哪些内存屏障呢？总共4类
 *  
 *  LoadLoad：前面的load会先于后面的load装载
 *  
 *  StoreStore：前面的store会先于后面的store执行，也就是保证内存可见性
 *  
 *  LoadStore：前面的load先于后面的store执行
 *  
 *  StoreLoad：前面的store先于后面的Load执行
 *  
 *  接下来分别看volatile、final、锁，都有哪些内存语义，加了哪些内存屏障。
 *  
 *  （1）、volatile
 *  
 *  对volatile变量的写操作，前面插入StoreStore屏障，防止和上面的写发生重排序；后面插入StoreLoad屏障，防止和后面的读写发生重排序。
 *  对volatile变量的读操作，后面会插入两个屏障，分别是LoadLoad、LoadStore，说白了就是，我是volatile变量，不管你下面的变量是读或者写，我都要先于你读。
 *  
 *  （2）、final   
 *  
 *  final本质上定义是final域与构造对象的引用之间的内存屏障。
 *  
 *  在构造函数对final变量的写人，与对构造函数对象引用的读，不能重排序，本质上是插入了storeStore屏障，
 *  保证对象引用被读之前，已经对final变量进行了写人。这里特别注意指针逃逸。
 *  
 *  读含有final变量的对象的引用，与读final变量不能指令重排序，插入loadload屏障，保证先读到对象引用，在读final变量的值，也就是只要对象构造完成，
 *  并且在构造函数中将final值写入，另外一个线程肯定可以读到，这是JMM的保证。
 *  
 *  （3）、锁
 *  ReentrantLock中 有个private volatile int state，本质上是用的volatile的内存语义
 *  
 *  
 *  
 *  as-if-serial、happens-before
 *  
 *  as-if-serial：用通俗的话来解释一下，单线中，程序逻辑的以我们看到的顺序执行，这里只是可以逻辑的认为顺序执行，其实也会有不影响结果的指令重排，例如：
 *  int i=1;
 *  int j=2;
 *  int a=i*j;
 *  这里i=1，j=1重排不影响结果，那么实际上JMM是允许的。  有了as-if-serial，在单线程中，程序员不用担心指令重排和内存可见性问题。
 *  
 *  happens-before
 *  happens-before保证如果A、B两个操作存在happens before关系，那么A操作的结果一定对B可见，有了可见性的保证，在加上正确的同步，就能写出线程安全的代码。
 *  JSR133定义了哪些天然的happens-before关系呢？请看下面：
 *  （1）、一个线程内，每个操作happens-before后面的操作
 *  （2）、unlock操作happens-before对这个这个锁的lock操作
 *  （3）、volatile写操作happens-before读操作
 *  （4）、线程的start方法happens-before此线程的所有其他操作
 *  （5）、线程所有操作happens-before对此线程的终止监测，例如，A线程调用B线程的join方法，
 *  如果join返回，那么B线程的所有操作必定完成，且B线程的所有操作的数据必定对A线程可见。
 *  （6）、传递性，A happens-before B、B happens-before C，那么A happens-before C
 *  
 *  happens-before是JMM对Java程序员的承诺，记住这些规则，配合锁，必定线程安全。
 *  
 *  注：
 *  1、在本线程内看，所有的操作都是有序的，这是as-if-serial的保证。
 *  2、一个线程看另一个线程，所有的操作都是无序的，主要是两方面所致，一方面是指令重排序，另一方面是不知道工作内存的值什么时候同步到主内存。
 */
public class ReorderTest {

	private int a = 0;
	private int b = 0;
	private int c = 2;

	public void write() {
		a = 1;
		b = 1;
	}

	public void read() {
		if (b == 1) {
			c = a;
		}
	}
}
